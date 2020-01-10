package censusanalyser;

public class IndiaCensusCsvDAO {
    private int areaInSqKm;
    private String state;
    private int densityPerSqKm;
    private int population;
    public IndiaCensusCsvDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }
    public String getStateDAO() {
        return state;
    }

    public int getPopulation() {
        return population;
    }

    public int getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public int getAreaInSqKm() {
        return areaInSqKm;
    }



}
