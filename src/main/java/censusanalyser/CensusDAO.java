package censusanalyser;

public class CensusDAO {
    public int areaInSqKm;
    public String state;
    public int densityPerSqKm;
    public int population;
    public String State_Id;
    public double Water_area;
    public double Land_area;
    public double Housing_Density;
    public int Housing_units;

    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        areaInSqKm=indiaCensusCSV.areaInSqKm;
        densityPerSqKm=indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
    }



    public CensusDAO(USCensus usCensus) {
        Housing_units=usCensus.Housing_units;
        Housing_Density=usCensus.Housing_Density;
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

    public String getState_Id() {
        return State_Id;
    }

    public Object getCensusDTO(CensusAnalyser.Country country){
        if (country.equals(CensusAnalyser.Country.US))
            return new USCensus(state,population,densityPerSqKm,State_Id,areaInSqKm);
        return new IndiaCensusCSV(state,population,densityPerSqKm,areaInSqKm);
    }
}
