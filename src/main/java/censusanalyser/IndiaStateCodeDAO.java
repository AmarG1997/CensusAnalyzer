package censusanalyser;

public class IndiaStateCodeDAO {
    private int SrNo;
    private String StateCode;
    private String StateName;
    private int TIN;


    public IndiaStateCodeDAO(CSVStatesCode csvStatesCode) {
        SrNo = csvStatesCode.SrNo;
        StateCode = csvStatesCode.StateCode;
        StateName = csvStatesCode.StateName;
        TIN=csvStatesCode.TIN;
    }

    public String getStateCode() {
        return StateCode;
    }
}
