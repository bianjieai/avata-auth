import ai.bianjie.avata.auth.AvataUtils;
import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

public class AvataUtilsTest {

    @Test
    public void createAccounts() {
        // 示例参数
        String path = "/v1beta1/accounts";
        Map query = null;
        String body = "{\"count\": 1, \"operation_id\": \"1\"}";
        long timestamp = 1647751123703L;
        String apiSecret = "AKIDz8krbsJ5yKBZQpn74WFkmLPc5ab";

        // 构造签名
        String sig = AvataUtils.signRequest(path, query, JSON.parseObject(body), timestamp, apiSecret);

        // 校验签名
        Assert.assertEquals("471800759f2c7c98672c9a903c2765b37a97ac2edfafbb4abf0228b0e2347173", sig);
    }
}
