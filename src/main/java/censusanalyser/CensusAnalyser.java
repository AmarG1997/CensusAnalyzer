package censusanalyser;

import com.google.gson.Gson;
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

public class CensusAnalyser {
    List<IndiaCensusCSV> censusCSVList = null;
    List<CSVStatesCode> censusStateCodeCSVList = null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();

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

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusStateCodeCSVList = csvBuilder.getCSVFileList(reader, CSVStatesCode.class);
            return censusStateCodeCSVList.size();
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

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterable = () -> censusCSVIterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), true).count();
        return numOfEnteries;
    }

    public String getSortIndiaStateCensusData() {
        List<IndiaCensusCSV> list1 = censusCSVList.stream().sorted(Comparator.comparing(IndiaCensusCSV::getState)).collect(Collectors.toList());
        String sortedData = new Gson().toJson(list1);
        System.out.println(sortedData);
        return sortedData;
    }

    public String getSortIndiaStateCode() {
        List<CSVStatesCode> list1 = censusStateCodeCSVList.stream().sorted(Comparator.comparing(CSVStatesCode::getStateCode)).collect(Collectors.toList());
        String sortedData = new Gson().toJson(list1);
        System.out.println(sortedData);
        return sortedData;
        }
    }


