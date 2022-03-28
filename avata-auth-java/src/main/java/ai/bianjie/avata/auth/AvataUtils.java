package ai.bianjie.avata.auth;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
public class AvataUtils {
    /**
     * 对请求参数进行签名处理
     * @param path
     * 请求路径,仅截取域名后参数前部分,例:"/v1beta1/accounts";
     * @param query
     * get请求携带的参数,例:"name1=value1&name2=value2",需要处理成map形式
     * @param body
     * post请求携带的参数,例:"{\"count\": 1, \"operation_id\": \"string\"}",需要处理成map形式
     * @param apiSecret
     * 应用方的api_secret,例:"AKIDz8krbsJ5yKBZQpn74WFkmLPc5ab"
     * @return
     * 返回时间戳和signature
     */
    public static Map<String, String> SignRequest(String path, Map<String, String> query, Map<String, Object> body, String apiSecret) {
        Map<String, Object> paramsMap = new HashMap();
        paramsMap.put("path_url", path);
        if (null != query && query.size() != 0) {
            for (Map.Entry<String, String> q : query.entrySet()) {
                paramsMap.put("query_" + q.getKey(), q.getValue());
            }
        }
        if (null != body && body.size() != 0) {
            for (Map.Entry<String, Object> b : body.entrySet()) {
                paramsMap.put("body_" + b.getKey(), b.getValue());
            }
        }
        String json = JSON.toJSONString(paramsMap, SerializerFeature.MapSortField);
        String timestamp = getTimestamp();
        String signature = getSHA256StrJava(json + timestamp + apiSecret);
        return new HashMap<String, String>() {
            {
                put("signature", signature);
                put("timestamp", timestamp);
            }
        };
    }
    public static String getTimestamp() {
        //获取当前时间戳(毫秒级)
        long l = System.currentTimeMillis();
        String timeStamp = String.valueOf(l);
        return timeStamp;
    }
    /**
     * 利用java原生的摘要实现SHA256加密
     *
     * @param str 加密后的报文
     * @return
     */
    public static String getSHA256StrJava(String str) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;
    }
    /**
     * 将byte转为16进制
     *
     * @param bytes
     * @return
     */
    public static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
}