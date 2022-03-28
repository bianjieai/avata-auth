import ai.bianjie.avata.auth.AvataUtils;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
public class AvataUtilsTest {
    /**
     * 请求参数
     */
    public static final String PATH = "/v1beta1/accounts";
    public static final String QUERY = "name1=value1&name2=value2";
    public static final String DATA = "{\"count\": 1, \"operation_id\": \"string\"}";
    public static final String APISECRET = "AKIDz8krbsJ5yKBZQpn74WFkmLPx4ab";

    /**
     * 测试类
     */
    @Test
    public void test() {
        testPost();
//        testGet();
    }

    /**
     * 测试get请求,打印signature和timestamp参数
     */
    public static void testGet() {
        Map<String,String> queryMap = new HashMap<>();
        for (String str : QUERY.split("&")) {
            String[] querys = str.split("=");
            queryMap.put(querys[0], querys[1]);
        }
        Map<String,String> res = AvataUtils.SignRequest(PATH, queryMap, null, APISECRET);
        for (Map.Entry<String,String> m : res.entrySet()) {
            System.out.println(m.getKey()+"------"+m.getValue());
        }
    }

    /**
     * 测试post请求,打印signature和timestamp参数
     */
    public static void testPost() {
        Map<String,Object> parse = JSONObject.parseObject(DATA);
        Map<String,String> res = AvataUtils.SignRequest(PATH, null, parse, APISECRET);
        for (Map.Entry<String,String> m : res.entrySet()) {
            System.out.println(m.getKey()+"------"+m.getValue());
        }
    }
}