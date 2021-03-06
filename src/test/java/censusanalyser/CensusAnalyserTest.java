package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;


public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_TYPE="./src/main/resources/IndiaStateCensusData.txt";
    private static final String INDIA_CENSUS_CSV_FILE_WITH_WRONG_DELIMETER="/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/Delimeter.csv";
    private static final String INDIA_STATE_CODE="/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATECODE_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String WRONG_STATECODE_CSV_FILE_TYPE="./src/main/resources/IndiaStateCensusData.txt";
    private static final String STATECODE_CSV_FILE_WITH_WRONG_DELIMETER="/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/Delimeter.csv";
    private static final String USV_CENSUS_FILE_PATH="/home/admin1/Downloads/CensusAnalyser/CensusAnalyser/src/test/resources/USCensusData (1).csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
            Assert.assertEquals(29,numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_withWrongFileType_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_withWrongDelimeter_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_WITH_WRONG_DELIMETER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaCensusData_withWrongHeader_shouldThrowException() {
        try{
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_WITH_WRONG_DELIMETER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA,e.type);
        }
    }

    @Test
    public void givenIndianStateCodeCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void givenIndiaStateCodeData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATECODE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_withWrongFileType_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,WRONG_STATECODE_CSV_FILE_TYPE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_withWrongDelimeter_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,STATECODE_CSV_FILE_WITH_WRONG_DELIMETER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaStateCodeData_withWrongHeader_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,STATECODE_CSV_FILE_WITH_WRONG_DELIMETER);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA,e.type);
        }
    }

    @Test
    public void givenIndiaCensusFile_sortedList_atFirstPosition_isSortedData() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String list = censusAnalyser.getSortIndiaStateCensusData();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(list,IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh",indiaCensusCSVS[0].state);

    }

    @Test
    public void givenIndiaCensusFile_sortedList_atLastPosition_isSortedData() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String list = censusAnalyser.getSortIndiaStateCensusData();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(list,IndiaCensusCSV[].class);
        Assert.assertEquals("West Bengal",indiaCensusCSVS[28].state);
    }

    @Test
    public void givenIndiaStateCodeFile_sortedList_atFirstPosition_isSortedData() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String list = censusAnalyser.getSortIndiaStateCode();
        CensusDAO[] csvStatesCodes = new Gson().fromJson(list,CensusDAO[].class);
        Assert.assertEquals("Andhra Pradesh",csvStatesCodes[0].state);


    }

    @Test
    public void givenIndiaStateCodeFile_sortedList_atLastPosition_isSortedData() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String list = censusAnalyser.getSortIndiaStateCode();
        CensusDAO[] csvStatesCodes = new Gson().fromJson(list,CensusDAO[].class);
        Assert.assertEquals("West Bengal",csvStatesCodes[28].state);
    }

    @Test
    public void givenIndiaCensusData_shouldReturnSortedData() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String sortDataByStateName= censusAnalyser.getSortIndiaStateCensusData();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(sortDataByStateName,IndiaCensusCSV[].class);
        Assert.assertEquals("Andhra Pradesh",indiaCensusCSVS[0].state);
        Assert.assertEquals("West Bengal",indiaCensusCSVS[28].state);

    }

    @Test
    public void givenIndianCensusData_shouldReturnMostPopulationState() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String mostPopulationState = censusAnalyser.getMostPopulationState();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(mostPopulationState,IndiaCensusCSV[].class);
        Assert.assertEquals("Uttar Pradesh",indiaCensusCSVS[0].state);
    }

    @Test
    public void givenIndianCensusData_shouldReturnLowPopulationState() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String mostPopulationState = censusAnalyser.getMostPopulationState();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(mostPopulationState,IndiaCensusCSV[].class);
        Assert.assertEquals("Sikkim",indiaCensusCSVS[28].state);
        System.out.println(indiaCensusCSVS[28]);
    }

    @Test
    public void givenIndianCensusData_shouldReturnLowestPopulationDensityState() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String mostPopulationDensityState = censusAnalyser.getMostDensityPopulationState();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(mostPopulationDensityState,IndiaCensusCSV[].class);
        Assert.assertEquals("Bihar",indiaCensusCSVS[0].state);
        System.out.println(indiaCensusCSVS[0]);
    }

    @Test
    public void givenIndianCensusData_shouldReturnMostPopulationDensityState() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String mostPopulationDensityState = censusAnalyser.getMostDensityPopulationState();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(mostPopulationDensityState,IndiaCensusCSV[].class);
        Assert.assertEquals("Arunachal Pradesh",indiaCensusCSVS[28].state);
        System.out.println(indiaCensusCSVS[0]);
    }

    @Test
    public void givenIndianCensusData_shouldReturnLargestState() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String largestState = censusAnalyser.getLargestState();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(largestState,IndiaCensusCSV[].class);
        Assert.assertEquals("Rajasthan",indiaCensusCSVS[0].state);
        System.out.println(indiaCensusCSVS[0]);
    }

    @Test
    public void givenIndianCensusData_shouldReturnSmallestState() throws CensusAnalyserException {
        CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.INDIA);
        censusAnalyser.loadCensusData(CensusAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE);
        String largestState = censusAnalyser.getLargestState();
        IndiaCensusCSV[] indiaCensusCSVS = new Gson().fromJson(largestState,IndiaCensusCSV[].class);
        Assert.assertEquals("Goa",indiaCensusCSVS[28].state);
        System.out.println(indiaCensusCSVS[0]);
    }

    @Test
    public void givenUSVcensusFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,USV_CENSUS_FILE_PATH);
            Assert.assertEquals(51,numOfRecords);
        } catch (CensusAnalyserException e) { }
    }

}
