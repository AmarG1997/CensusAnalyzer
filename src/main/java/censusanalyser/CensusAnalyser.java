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
    List<IndiaCensusCsvDAO> censusCSVList = null;

    List<IndiaStateCodeDAO> censusStateCodeCSVList = null;

    public CensusAnalyser() {
        this.censusCSVList = new ArrayList<IndiaCensusCsvDAO>();
        this.censusStateCodeCSVList = new ArrayList<IndiaStateCodeDAO>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator <IndiaCensusCSV>csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
            while (csvFileIterator.hasNext())
            {
                this.censusCSVList.add(new IndiaCensusCsvDAO(csvFileIterator.next()));
            }
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
            Iterator <CSVStatesCode>statesCodeIterator = csvBuilder.getCSVFileIterator(reader, CSVStatesCode.class);
            while (statesCodeIterator.hasNext())
            {
                this.censusStateCodeCSVList.add(new IndiaStateCodeDAO(statesCodeIterator.next()));
            }
            return censusStateCodeCSVList.size();
//            censusStateCodeCSVList = csvBuilder.getCSVFileList(reader, CSVStatesCode.class);
//            return censusStateCodeCSVList.size();
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

    public String getSortIndiaStateCensusData() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }

        censusCSVList= censusCSVList.stream().sorted(Comparator.comparing(IndiaCensusCsvDAO :: getStateDAO)).collect(Collectors.toList());
        String sortedData = new Gson().toJson(this.censusCSVList);
        System.out.println(sortedData);
        return sortedData;
    }

    public String getSortIndiaStateCode() throws CensusAnalyserException {
        if (censusStateCodeCSVList.size()==0){
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        censusStateCodeCSVList = censusStateCodeCSVList.stream().sorted(Comparator.comparing(IndiaStateCodeDAO::getStateCode)).collect(Collectors.toList());
        String sortedData = new Gson().toJson(this.censusStateCodeCSVList);
        System.out.println(sortedData);
        return sortedData;
        }
    }


