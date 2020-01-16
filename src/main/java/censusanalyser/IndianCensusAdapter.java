package censusanalyser;

import csvBuilder.CSVBuilderFactory;
import csvBuilder.CsvBuilderException;
import csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.StreamSupport;

public class IndianCensusAdapter extends CensusAdapter {

    List<CensusDAO> censusCSVList = new ArrayList<>();
    Map<String,CensusDAO>censusStateMap = new HashMap<>();

    @Override
    public <E> Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String,CensusDAO> censusStateMap = super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
        this.loadIndiaStateCodeData(censusStateMap,csvFilePath[1]);
        return censusStateMap;
    }


    public Map<String, CensusDAO> loadIndiaStateCodeData(Map<String,CensusDAO>censusStateMap,String csvFilePath) throws CensusAnalyserException {
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
