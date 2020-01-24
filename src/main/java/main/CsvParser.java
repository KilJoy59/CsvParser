package main;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class CsvParser {

    public CsvParser() {
        initSecondJson();
    }

    private Map<String, Integer> firstJson = new HashMap<>();
    private Map<String, Integer> secondJson = new HashMap<>();
    private Map<String, List<Integer>> thirdJson = new HashMap<>();


    public void parse(String path) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        for (CSVRecord record : csvParser) {
            List<Integer> list = new ArrayList<>();
            String mark = record.get(0).toLowerCase();
            int number = Integer.parseInt(record.get(1).toLowerCase());
            fillMap(firstJson, mark, number);
            fillMap(secondJson, mark, number);
            fillThirdMap(thirdJson, mark, number, list);
        }
    }

    private void fillMap(Map<String, Integer> map, String mark, int number) {
        if (map.containsKey(mark)) {
            if (map.containsValue(null)) {
                map.replace(mark, null, 0);
            }
            int value = map.get(mark);
            number = value + number;
        }
        map.put(mark, number);
    }

    private void initSecondJson() {
        secondJson.put("mark01", null);
        secondJson.put("mark17", null);
        secondJson.put("mark23", null);
        secondJson.put("mark35", null);
        secondJson.put("markfv", null);
        secondJson.put("markfx", null);
        secondJson.put("markft", null);
    }

    private void fillThirdMap(Map<String, List<Integer>> map, String mark, int number, List<Integer> list) {
        if (map.containsKey(mark)) {
            list = map.get(mark);
        } else {
            list = new ArrayList<>();
            map.put(mark, list);
        }
        list.add(number);
        Collections.sort(list);
    }

    public Map<String, Integer> getFirstJson() {
        return firstJson;
    }

    public Map<String, Integer> getSecondJson() {
        return secondJson;
    }

    public Map<String, List<Integer>> getThirdJson() {
        return thirdJson;
    }
}
