package main;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import java.io.File;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseCsvTest extends Assert {

    private static ParseCsv parseCsv = new ParseCsv();

    @Before
    public void setUp() throws Exception {
        List<List<String>> rows = Arrays.asList(
                Arrays.asList("mark1", "2"),
                Arrays.asList("mark2", "3"),
                Arrays.asList("mark3", "2"),
                Arrays.asList("mark2", "2"),
                Arrays.asList("mark1", "3")
        );
        FileWriter csvWriter = new FileWriter("new.csv");
        csvWriter.append("mark");
        csvWriter.append(",");
        csvWriter.append("number");
        csvWriter.append("\n");
        for (List<String> rowData : rows) {
            csvWriter.append(String.join(",", rowData));
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
    }

    @Test
    public void firstMethodParse() throws Exception {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("mark2", 5);
        expected.put("mark1", 5);
        expected.put("mark3", 2);

        Map<String, Integer> actual = new HashMap<>();
        File file = new File("new.csv");
        parseCsv.firstMethodParse(file, actual);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void secondMethodParse() throws Exception {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("mark2", 5);
        expected.put("mark1", 5);
        expected.put("mark3", 2);

        Map<String, Integer> actual = new HashMap<>();
        File file = new File("new.csv");
        parseCsv.secondMethodParse(file, actual);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void thirdMethodParse() throws Exception {
        Map<String, List<Integer>> expected = new HashMap<>();
        expected.put("mark2", Arrays.asList(2, 3));
        expected.put("mark1", Arrays.asList(2, 3));
        expected.put("mark3", Arrays.asList(2));

        Map<String, List<Integer>> actual = new HashMap<>();
        File file = new File("new.csv");
        parseCsv.thirdMethodParse(file, actual);
        Assert.assertNotNull(actual);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void toJsonFile() throws Exception {
        JSONObject actual = new JSONObject();
        actual.put("mark2", 5);
        actual.put("mark1", 5);
        actual.put("mark3", 2);

        Map<String, Integer> map = new HashMap<>();
        map.put("mark2", 5);
        map.put("mark1", 5);
        map.put("mark3", 2);
        String expected = parseCsv.toJsonFile(map, "new.json");

        JSONAssert.assertEquals(expected, actual, true);
    }

    @Test
    public void toJsonFileWithList() throws Exception {
        String actual = "{\"mark2\":[2,3], \"mark1\":[2,3],\"mark3\":[2]}";

        Map<String, List<Integer>> map = new HashMap<>();
        map.put("mark2", Arrays.asList(2, 3));
        map.put("mark1", Arrays.asList(2, 3));
        map.put("mark3", Arrays.asList(2));
        String expected = parseCsv.toJsonFileWithList(map, "new.json");
        JSONAssert.assertEquals(expected, actual, true);
    }
}