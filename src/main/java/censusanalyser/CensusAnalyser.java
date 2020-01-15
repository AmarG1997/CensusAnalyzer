package censusanalyser;

import com.google.gson.Gson;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    Map<String,CensusDAO> censusCSVList = new HashMap<>();
    List<IndiaStateCodeDAO> censusStateCodeCSVList = null;

    public CensusAnalyser() {
        this.censusCSVList = new HashMap<>();
        this.censusStateCodeCSVList = new ArrayList<>();
    }

    public enum Country{ INDIA,US}
    Map<String ,CensusDAO> censusStateMap = null;


    public int loadCensusData(Country country,String... csvFilePath) throws CensusAnalyserException {
        censusStateMap = CensusAdapterFactory.getCensusData(country,csvFilePath);
        return censusStateMap.size();
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
        List<CensusDAO> list = censusCSVList.values().stream().collect(Collectors.toList());
        list= list.stream().sorted(Comparator.comparing(CensusDAO::getState)).collect(Collectors.toList());
        String sortedData = new Gson().toJson(list);
        System.out.println(sortedData);
        return sortedData;
    }

    public String getSortIndiaStateCode() throws CensusAnalyserException {
        if (censusCSVList.size()==0){
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        List<CensusDAO> list = censusCSVList.values().stream().collect(Collectors.toList());
        list = list.stream().sorted(Comparator.comparing(CensusDAO::getState_Id)).collect(Collectors.toList());
        String sortedData = new Gson().toJson(list);
        System.out.println(sortedData);
        return sortedData;
        }

    public String getMostPopulationState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        List<CensusDAO> list = censusCSVList.values().stream().collect(Collectors.toList());
        list= list.stream().sorted(Comparator.comparing(CensusDAO::getPopulation).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(list);
        return sortedData;
    }

    public String getMostDensityPopulationState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        List<CensusDAO> list = censusCSVList.values().stream().collect(Collectors.toList());
        list= list.stream().sorted(Comparator.comparing(CensusDAO:: getDensityPerSqKm).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(list);
        return sortedData;
    }


    public String getLargestState() throws CensusAnalyserException {
        if (censusCSVList.size()==0 || censusCSVList == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        List<CensusDAO> list = censusCSVList.values().stream().collect(Collectors.toList());
        list= list.stream().sorted(Comparator.comparing(CensusDAO:: getAreaInSqKm).reversed()).collect(Collectors.toList());
        String sortedData = new Gson().toJson(list);
        return sortedData;
    }
}


