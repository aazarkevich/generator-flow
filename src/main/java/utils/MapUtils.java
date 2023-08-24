package utils;

import java.util.Map;

public class MapUtils {
    public static String getValueByKey(Map<String, Object> map, String fieldName) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getKey().equals(fieldName)) {
                return entry.getValue().toString();
            } else if (entry.getValue() instanceof Map) {
                String result = getValueByKey((Map<String, Object>) entry.getValue(), fieldName);
                if (result == null) {
                    continue;
                } else {
                    return result;
                }
            }
        }
        return null;
    }
}
