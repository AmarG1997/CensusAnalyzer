package censusanalyser;

import csvBuilder.CSVBuilderFactory;
import csvBuilder.CsvBuilderException;
import csvBuilder.ICSVBuilder;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            List<IndiaCensusCSV> censusCSVList = csvBuilder.getCSVFileList(reader, IndiaCensusCSV.class);
            return censusCSVList.size();

        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA);
        } catch (CsvBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateCodeData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<CSVStatesCode> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, CSVStatesCode.class);
            return getCount(censusCSVIterator);
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.INCORRECT_FILE_DATA);
        } catch (CsvBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private <E> int getCount(Iterator<E> censusCSVIterator) {
        Iterable<E> csvIterable = () -> censusCSVIterator;
        int numOfEnteries = (int) StreamSupport.stream(csvIterable.spliterator(), true).count();
        return numOfEnteries;
    }

    public JSONArray getSortIndiaStateCensusData(String csvFilePath) throws IOException, CsvBuilderException {
        Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
        ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
        Iterator<IndiaCensusCSV> censusCSVIterator = csvBuilder.getCSVFileIterator(reader, IndiaCensusCSV.class);
        List<IndiaCensusCSV> list = new ArrayList<>();
        while (censusCSVIterator.hasNext()){
            list.add(censusCSVIterator.next());
        }
        List<IndiaCensusCSV> list1 = list.stream().sorted(Comparator.comparing(IndiaCensusCSV::getState)).collect(Collectors.toList());
        JSONArray jsonArray = new JSONArray();
        for (int i=0;i<list1.size();i++)
        {
            jsonArray.put(list1.get(i));
        }
        System.out.println("list sorted--->"+list1);
        return jsonArray;
    }

        public JSONArray getSortIndiaStateCode(String csvFilePath) throws IOException {
            BufferedReader reader1 = Files.newBufferedReader(Paths.get(csvFilePath));
            JSONArray json = new JSONArray();
            Map<String,List<String>> map= new TreeMap<>();
            String  line;
            while ((line = reader1.readLine())!=null){
                String fields = line.split(",")[3];
                List<String> list = map.get(fields);
                if (list == null){
                    list=new LinkedList<>();
                    map.put(fields,list);
                }
                list.add(line);
                json.put(map.get(fields));
            }
            System.out.println("list sorted--->"+json);
            return json;
        }

    }


