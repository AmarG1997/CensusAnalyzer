package csvbuilderException;

import censusanalyser.CensusAnalyserException;

public class CsvBuilderException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,INCORRECT_FILE_DATA;

    }
    public ExceptionType type;

    public CsvBuilderException(String message, ExceptionType type)
    {
        super(message);
        this.type = type;
    }



}
