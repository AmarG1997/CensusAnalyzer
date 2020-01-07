package censusanalyser;

public class CsvBuilderException extends Exception {

    enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,INCORRECT_FILE_DATA;
    }

    CsvBuilderException.ExceptionType type;

    public CsvBuilderException(String message,ExceptionType type)
    {
        super(message);
        this.type = type;
    }
}
