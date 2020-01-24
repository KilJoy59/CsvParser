package main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.*;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String DESTINATION = "src/main/resources/data";
    private static final String PATH_FIRST_JSON = "json/json1.json";
    private static final String PATH_SECOND_JSON = "json/json2.json";
    private static final String PATH_THIRD_JSON = "json/json3.json";

    public static void main(String[] args) {
        CsvParser csvParser = new CsvParser();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Введите путь к архиву. Например C:/archive.zip");
        try {
            String path = bufferedReader.readLine();
            unZip(path, DESTINATION);
            File dir = new File(DESTINATION);
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    csvParser.parse(file.getPath());
                }
            }
            gsonToJsonFile(csvParser.getFirstJson(), PATH_FIRST_JSON);
            gsonToJsonFile(csvParser.getSecondJson(), PATH_SECOND_JSON);
            gsonToJsonFileWithSet(csvParser.getThirdJson(), PATH_THIRD_JSON);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void unZip(String source, String destination) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(source);
            zipFile.extractAll(destination);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    private static void gsonToJsonFile(Map<String, Integer> map, String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        System.out.println(new Gson().toJson(map));
        FileWriter file = new FileWriter(path);
        file.write(gson.toJson(map));
        file.flush();
        file.close();
    }

    private static void gsonToJsonFileWithSet(Map<String, List<Integer>> map, String path) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
        System.out.println(new Gson().toJson(map));
        FileWriter file = new FileWriter(path);
        file.write(gson.toJson(map));
        file.flush();
        file.close();
    }

}
