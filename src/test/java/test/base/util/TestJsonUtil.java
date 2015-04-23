package test.base.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.jiucai.appframework.base.util.JsonUtil;

public class TestJsonUtil {

    public static void main(String[] args) throws Exception {

        Map<String, Object> m = new LinkedHashMap<String, Object>();
        m.put("1", "11");
        m.put("2", "22");
        m.put("5", "555");
        m.put("3", "33");
        m.put("7", "777");

        String json = JsonUtil.getString(m);

        System.out.println(json);

    }
}
