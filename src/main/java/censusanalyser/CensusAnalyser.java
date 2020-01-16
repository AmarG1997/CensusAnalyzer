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

    public Country country;

    public enum Country{ INDIA,US}

    Map<String ,CensusDAO> censusStateMap = null;

    public CensusAnalyser(Country country)
    {
        this.country=country;
    }

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
        if (censusStateMap.size()==0 || censusStateMap == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        //List<CensusDAO> list = censusStateMap.values().stream().collect(Collectors.toList());
        List list1 = censusStateMap.values().stream()
                .sorted((census1,census2) -> census1.state.compareTo(census2.state))
                .map(census -> census.getCensusDTO(country))
                .collect(Collectors.toList());
        String sortedData = new Gson().toJson(list1);
        System.out.println(sortedData);
        return sortedData;
    }

    public String getSortIndiaStateCode() throws CensusAnalyserException {
        if (censusStateMap.size()==0){
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        //List<CensusDAO> list = censusStateMap.values().stream().collect(Collectors.toList());
        List list2 = censusStateMap.values().stream()
                .sorted((census1,census2) -> census1.State_Id.compareTo(census2.State_Id))
                .map(census -> census.getCensusDTO(country))
                .collect(Collectors.toList());
        String sortedData = new Gson().toJson(list2);
        System.out.println(sortedData);
        return sortedData;
        }

    public String getMostPopulationState() throws CensusAnalyserException {
        if (censusStateMap.size()==0 || censusStateMap == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        //List<CensusDAO> list = censusStateMap.values().stream().collect(Collectors.toList());
        List list3 = censusStateMap.values().stream()
                .sorted((census1,census2) -> census1.population - census2.population > 0 ? -1: 1)
                .map(census -> census.getCensusDTO(country))
                .collect(Collectors.toList());
        String sortedData = new Gson().toJson(list3);
        System.out.println(sortedData);
        return sortedData;
    }

    public String getMostDensityPopulationState() throws CensusAnalyserException {
        if (censusStateMap.size()==0 || censusStateMap == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        //List<CensusDAO> list = censusStateMap.values().stream().collect(Collectors.toList());
        List list3 = censusStateMap.values().stream()
                .sorted((census1,census2) -> census1.densityPerSqKm - census2.densityPerSqKm > 0 ? -1: 1)
                .map(census -> census.getCensusDTO(country))
                .collect(Collectors.toList());
        String sortedData = new Gson().toJson(list3);
        return sortedData;
    }


    public String getLargestState() throws CensusAnalyserException {
        if (censusStateMap.size()==0 || censusStateMap == null) {
            throw new CensusAnalyserException("Null pointer Exception",
                    CensusAnalyserException.ExceptionType.NULL_POINTER_EXCEPTION);
        }
        List list = censusStateMap.values().stream()
                .sorted((census1,census2) -> census1.areaInSqKm - census2.areaInSqKm > 0 ? -1: 1)
               .map(census -> census.getCensusDTO(country))
                .collect(Collectors.toList());
        String sortedData = new Gson().toJson(list);
        System.out.println(sortedData);
        return sortedData;
    }
}

