package zkl.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2017/12/26.
 */
public class ResponseTem {

    private static final Integer successCode = 100;
    private static final Integer errorCode = 0;
    private static final Integer noLoginCode = -1;
    private static final Integer noPermissionsCode = 403;
    private static final String successMsg = "成功";
    private static final String errorMsg = "出错了";

    public static String successTem(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",successCode);
        jsonObject.put("msg", successMsg);
        return jsonObject.toJSONString();
    }

    public static JSONObject successTem(String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",successCode);
        jsonObject.put("msg", StringUtils.isEmpty(msg)?successMsg:msg);
        return jsonObject;
    }

    public static String errorTem(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",errorCode);
        jsonObject.put("msg", errorMsg);
        return jsonObject.toJSONString();
    }

    public static JSONObject errorTem(String msg){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",errorCode);
        jsonObject.put("msg", StringUtils.isEmpty(msg)?errorMsg:msg);
        return jsonObject;
    }

    public static String noLogin(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",noLoginCode);
        jsonObject.put("msg", "您未登录");
        return jsonObject.toJSONString();
    }

    public static String noPermissions(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",noPermissionsCode);
        jsonObject.put("msg", "您没有权限操作");
        return jsonObject.toJSONString();
    }
}
