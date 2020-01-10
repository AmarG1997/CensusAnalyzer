package censusanalyser;

public class USCensusDAO {
    private String State_Id;
    private String State;
    private int Population;
    private int Housing_units;
    private double Total_area;
    private double Water_area;
    private double Land_area;
    private double Population_Density;
    private double Housing_Density;

    public USCensusDAO(USCensus usCensus) {
        Housing_Density=usCensus.Housing_Density;
        Housing_units=usCensus.Housing_units;
        Land_area=usCensus.Land_area;
        Population=usCensus.Population;
        Population_Density=usCensus.Population_Density;
        State=usCensus.State;
        Total_area=usCensus.Total_area;
        State_Id=usCensus.State_Id;
        Water_area=usCensus.Water_area;
    }

    public String getState_Id() {
        return State_Id;
    }

    public String getState() {
        return State;
    }

    public int getPopulation() {
        return Population;
    }

    public int getHousing_units() {
        return Housing_units;
    }

    public double getTotal_area() {
        return Total_area;
    }

    public double getWater_area() {
        return Water_area;
    }

    public double getLand_area() {
        return Land_area;
    }

    public double getPopulation_Density() {
        return Population_Density;
    }

    public double getHousing_Density() {
        return Housing_Density;
    }
}
