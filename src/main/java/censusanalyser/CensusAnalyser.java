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
    List<CensusDAO> censusCSVList = null;

    List<IndiaStateCodeDAO> censusStateCodeCSVList = null;

    public CensusAnalyser() {
        this.censusCSVList = new ArrayList<>();
        this.censusStateCodeCSVList = new ArrayList<>();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
            return this.loadCensusData(csvFilePath,IndiaCensusCSV.class);
    }
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {

        return this.loadCensusData(csvFilePath,IndiaCensusCSV.class);

    }

    private <E> int loadCensusData(String csvFilePath, Class<E> censusCSVClass) throws CensusAnalyserException {
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
            List<CSVStatesCode> csvStatesCodeList = csvBuilder.getCSVFileList(reader,CSVStatesCode.class);
            csvStatesCodeList.stream().filter(stateData -> censusStateCodeCSVList.add(new IndiaStateCodeDAO(stateData))).collect(Collectors.toList());
            return csvStatesCodeList.size();
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
        censusCSVList= censusCSVList.stream().sorted(Comparator.comparing(CensusDAO::getState)).collect(Collectors.toList());
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

    public String getMostPopulationState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        censusCSVList= censusCSVList.stream().sorted(Comparator.comparing(CensusDAO::getPopulation).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(this.censusCSVList);
        return sortedData;
    }

    public String getMostDensityPopulationState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        censusCSVList= censusCSVList.stream().sorted(Comparator.comparing(CensusDAO:: getDensityPerSqKm).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(this.censusCSVList);
        return sortedData;
    }


    public String getLargestState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        censusCSVList= censusCSVList.stream().sorted(Comparator.comparing(CensusDAO:: getAreaInSqKm).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(this.censusCSVList);
        return sortedData;
    }
}


