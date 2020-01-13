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

    List<USCensusDAO> censusDAOS = null;

    public CensusAnalyser() {
        this.censusCSVList = new ArrayList<IndiaCensusCsvDAO>();
        this.censusStateCodeCSVList = new ArrayList<IndiaStateCodeDAO>();
        this.censusDAOS = new ArrayList<>();
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

    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator <USCensus>USCensusIterator = csvBuilder.getCSVFileIterator(reader, USCensus.class);
            while (USCensusIterator.hasNext())
            {
                this.censusDAOS.add(new USCensusDAO(USCensusIterator.next()));
            }
            return censusDAOS.size();
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

    public String getMostPopulationState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        censusCSVList= censusCSVList.stream().sorted(Comparator.comparing(IndiaCensusCsvDAO :: getPopulation).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(this.censusCSVList);
        return sortedData;
    }

    public String getMostDensityPopulationState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        censusCSVList= censusCSVList.stream().sorted(Comparator.comparing(IndiaCensusCsvDAO :: getDensityPerSqKm).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(this.censusCSVList);
        return sortedData;
    }


    public String getLargestState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        censusCSVList= censusCSVList.stream().sorted(Comparator.comparing(IndiaCensusCsvDAO :: getAreaInSqKm).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(this.censusCSVList);
        return sortedData;
    }
}


