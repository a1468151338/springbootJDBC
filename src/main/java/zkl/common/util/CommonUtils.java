package zkl.common.util;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by Administrator on 2017/12/27.
 */
public class CommonUtils {

    public static String filterStr(String str){
        return str.replaceAll(",|\\.|\"|'|=|\\?|<|>","");
    }

    //过滤HTML
    public static String filter(String message) {
        if (message == null)
            return (null);

        char content[] = new char[message.length()];
        message.getChars(0, message.length(), content, 0);
        StringBuilder result = new StringBuilder(content.length + 50);
        for (int i = 0; i < content.length; i++) {
            switch (content[i]) {
                case '<':
                    result.append("&lt;");
                    break;
                case '>':
                    result.append("&gt;");
                    break;
                case '&':
                    result.append("&amp;");
                    break;
                case '"':
                    result.append("&quot;");
                    break;
                default:
                    result.append(content[i]);
            }
        }
        return (result.toString());
    }


    //随机
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    //MD5
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5=MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        return base64en.encode(md5.digest(str.getBytes("utf-8")));
    }

    /**生成一个不在该数组里的随机数，随机数范围range
     * @param obj	数组,先排序
     * @param range		生成随机数范围0~(range-1)
     * @return
     */
    public static int random(int[] obj,int range){
        int r_radio = (int)(Math.random()*range);
        if (Arrays.binarySearch(obj,r_radio)>=0){
            r_radio = random(obj,range);
        }else{
            return r_radio;
        }
        return r_radio;
    }
}
