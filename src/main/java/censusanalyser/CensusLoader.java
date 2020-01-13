package censusanalyser;

import csvBuilder.CSVBuilderFactory;
import csvBuilder.CsvBuilderException;
import csvBuilder.ICSVBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CensusLoader {

    List<CensusDAO> censusCSVList = new ArrayList<>();

    public  <E> List<CensusDAO> loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<E> censusList = csvBuilder.getCSVFileList(reader,censusCSVClass);
            if (censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")){
                censusList.stream().map(censusCSVClass::cast)
                        .filter(censusData -> censusCSVList.add(new CensusDAO((IndiaCensusCSV) censusData)))
                        .collect(Collectors.toList());
            }
            else if (censusCSVClass.getName().equals("censusanalyser.USCensus")) {
                censusList.stream().map(censusCSVClass::cast)
                        .filter(censusData -> censusCSVList.add(new CensusDAO((USCensus) censusData)))
                        .collect(Collectors.toList());
            }
            return censusCSVList;

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
