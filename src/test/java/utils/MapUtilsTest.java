package utils;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;


@SpringBootTest
public class MapUtilsTest {
    JSONObject json;

    @Before
    public void init() {
        json = new JSONObject()
                .put("test", "test");
    }

    @Test
    public void getValueByKeyTest() {
        String test = MapUtils.getValueByKey(json.toMap(), "test");
        assertEquals("test", test);
    }
}