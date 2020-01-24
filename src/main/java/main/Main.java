package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String DESTINATION = "src/main/resources/data";
    private static final String PATH_FIRST_JSON = "json/json1.json";
    private static final String PATH_SECOND_JSON = "json/json2.json";
    private static final String PATH_THIRD_JSON = "json/json3.json";


    private static Map<String, Integer> firstJson = new HashMap<>();
    private static Map<String, Integer> secondJson = new HashMap<>();
    private static Map<String, List<Integer>> thirdJson = new HashMap<>();

    public static void main(String[] args) {
        Logger logger1 = LogManager.getLogger("ParseFile");
        Logger logger2 = LogManager.getLogger("ExceptionsFile");

        ParseCsv parseCsv = new ParseCsv();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите путь к архиву. Например C:/archive.zip");
        try {
            initSecondJson();
            String path = bufferedReader.readLine();
            parseCsv.unZip(path, DESTINATION);
            File[] files = new File(DESTINATION).listFiles();
            if (files != null) {
                for (File file : files) {
                    logger1.info("Парсинг файла " + file.getName());
                    parseCsv.firstMethodParse(file, firstJson);
                    parseCsv.secondMethodParse(file, secondJson);
                    parseCsv.thirdMethodParse(file, thirdJson);
                }
            }
            parseCsv.toJsonFile(firstJson, PATH_FIRST_JSON);
            parseCsv.toJsonFile(secondJson, PATH_SECOND_JSON);
            parseCsv.toJsonFileWithList(thirdJson, PATH_THIRD_JSON);
        } catch (IOException e) {
            e.printStackTrace();
            logger2.error(e.getMessage());
        }
    }

    public static void initSecondJson() {
        secondJson.put("mark01", null);
        secondJson.put("mark17", null);
        secondJson.put("mark23", null);
        secondJson.put("mark35", null);
        secondJson.put("markfv", null);
        secondJson.put("markfx", null);
        secondJson.put("markft", null);
    }
}
