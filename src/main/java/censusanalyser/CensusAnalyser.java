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
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
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
            List<CSVStatesCode> censusCSVIterator = csvBuilder.getCSVFileList(reader, CSVStatesCode.class);
            return censusCSVIterator.size();
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

    public String getSortIndiaStateCensusData(String csvFilePath) throws IOException, CsvBuilderException {
        Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
        Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
        List<IndiaCensusCSV> list = new ArrayList<>();
        while (censusCSVIterator.hasNext()){
            list.add(censusCSVIterator.next());
        }
        List<IndiaCensusCSV> list1 = list.stream().sorted(Comparator.comparing(IndiaCensusCSV::getState)).collect(Collectors.toList());
        String sortedData = new Gson().toJson(list1);
        System.out.println(sortedData);
        return sortedData;


    }

        public String getSortIndiaStateCode(String csvFilePath) throws IOException, CsvBuilderException {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStatesCode> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, CSVStatesCode.class);
            List<CSVStatesCode> list = new ArrayList<>();
            while (censusCSVIterator.hasNext()){
                list.add(censusCSVIterator.next());
            }
            List<CSVStatesCode> list1 = list.stream().sorted(Comparator.comparing(CSVStatesCode::getStateCode)).collect(Collectors.toList());
            String sortedData = new Gson().toJson(list1);
            System.out.println(sortedData);
            return sortedData;
        }

    }


