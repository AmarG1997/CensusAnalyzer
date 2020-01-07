package censusanalyser;

import csvbuilderException.CsvBuilderException;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder<E> {
    public Iterator<E> getCSVFileIterator(Reader reader, Class<E> CSVClass) throws CsvBuilderException;

}
