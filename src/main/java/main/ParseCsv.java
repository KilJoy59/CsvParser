package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ParseCsv {
    private static Logger logger2 = LogManager.getLogger("ExceptionsFile");

    public String unZip(String source, String destination) {
        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            logger2.error(e.getMessage());
        }
        return "Done";
    }

    public Map<String, Integer> firstMethodParse(File file, Map<String, Integer> map) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        for (CSVRecord record : csvParser) {
            String mark = record.get(0).toLowerCase();
            int number = Integer.parseInt(record.get(1).toLowerCase());
            if (map.containsKey(mark)) {
                if (map.containsValue(null)) {
                    map.replace(mark, null, 0);
                }
                int value = map.get(mark);
                number = value + number;
            }
            map.put(mark, number);
        }
        return map;
    }

    public Map<String, Integer> secondMethodParse(File file, Map<String, Integer> map) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        for (CSVRecord record : csvParser) {
            String mark = record.get(0).toLowerCase();
            int number = Integer.parseInt(record.get(1).toLowerCase());
            if (map.containsKey(mark)) {
                if (map.containsValue(null)) {
                    map.replace(mark, null, 0);
                }
                int value = map.get(mark);
                number = value + number;
            }
            map.put(mark, number);
        }
        return map;
    }

    public Map<String, List<Integer>> thirdMethodParse(File file, Map<String, List<Integer>> map) throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        for (CSVRecord record : csvParser) {
            List<Integer> list;
            String mark = record.get(0).toLowerCase();
            int number = Integer.parseInt(record.get(1).toLowerCase());
            if (map.containsKey(mark)) {
                list = map.get(mark);
            } else {
                list = new ArrayList<>();
                map.put(mark, list);
            }
            list.add(number);
            Collections.sort(list);
        }
        return map;
    }

    public String toJsonFile(Map<String, Integer> map, String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        FileWriter file = new FileWriter(path);
        file.write(gson.toJson(map));
        file.flush();
        file.close();
        return gson.toJson(map);
    }

    public String toJsonFileWithList(Map<String, List<Integer>> map, String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        FileWriter file = new FileWriter(path);
        file.write(gson.toJson(map));
        file.flush();
        file.close();

        return gson.toJson(map);
    }

}
