package censusanalyser;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter{
    @Override
    public <E> Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String,CensusDAO> censusStateMap = super.loadCensusData(USCensus.class,csvFilePath[0]);
        return censusStateMap;
    }
}
