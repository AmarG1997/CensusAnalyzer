package censusanalyser;

import csvBuilder.CSVBuilderFactory;
import csvBuilder.CsvBuilderException;
import csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusLoader {

    List<CensusDAO> censusCSVList = new ArrayList<>();
    Map<String,CensusDAO>censusStateMap = new HashMap<>();

    public  <E> Map< String, CensusDAO> loadCensusData(Class<E> censusCSVClass , String... csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<E> censusList = csvBuilder.getCSVFileList(reader,censusCSVClass);
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")){
                StreamSupport.stream(censusList.spliterator(),false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.state,new CensusDAO(censusCSV)));
            }
            else if (censusCSVClass.getName().equals("censusanalyser.USCensus")) {
                StreamSupport.stream(censusList.spliterator(), false)
                        .map(USCensus.class::cast)
                        .forEach(censusCSV -> censusStateMap.put(censusCSV.State, new CensusDAO(censusCSV)));
            }
              if(csvFilePath.length == 1)
                  return censusStateMap;
              this.loadIndiaStateCodeData(csvFilePath[1]);
              return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA);
        } catch (CsvBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }

    }

    public Map<String, CensusDAO> loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<CSVStatesCode> csvStatesCodeList = csvBuilder.getCSVFileList(reader,CSVStatesCode.class);
            StreamSupport.stream(csvStatesCodeList.spliterator(),false)
                    .filter(csvStates -> censusStateMap.get(csvStates.StateName)!=null)
                    .forEach(csvStates -> censusStateMap.get(csvStates.StateName).State_Id = csvStates.StateCode);
            return censusStateMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA);
        } catch (CsvBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
}
