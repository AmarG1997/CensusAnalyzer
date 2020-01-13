package censusanalyser;

public class CensusDAO {
    private int areaInSqKm;
    private String state;
    private int densityPerSqKm;
    private int population;
    private String State_Id;
    private int Housing_units;
    private double Water_area;
    private double Land_area;
    private double Housing_Density;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }


    public CensusDAO(USCensus usCensus) {
        Housing_units=usCensus.Housing_units;
        Land_area=usCensus.Land_area;
        population=usCensus.Population;
        densityPerSqKm= (int) usCensus.Population_Density;
        state=usCensus.State;
        areaInSqKm = (int) usCensus.Total_area;
        State_Id=usCensus.State_Id;
        Water_area=usCensus.Water_area;
    }

    public int getAreaInSqKm() {
        return areaInSqKm;
    }

    public String getState() {
        return state;
    }

    public int getDensityPerSqKm() {
        return densityPerSqKm;
    }

    public int getPopulation() {
        return population;
    }
}
