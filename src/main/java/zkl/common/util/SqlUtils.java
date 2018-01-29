package zkl.common.util;

import com.sun.deploy.util.ArrayUtil;
import zkl.common.annotation.FDKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

public class SqlUtils {
    /**
     * 生成插入语句（单表）
     * @param tablename 表名
     * @param object 对象
     * @return 返回数组第一个值是sql语句，第二个值是params[]
     */
    public static Object[] createSql(String tablename, Object object) throws NoSuchFieldException {
        String field = "";
        String param ="";
        ArrayList os = new ArrayList();
        try {
            //拿到字段和字段值的map
            Map<String,Object> map = ReflectUtils.getFiledArrayClearNull(object);
            Set set = map.keySet();
            for(Object obj : set){
                //主键自增
                if (object.getClass().getDeclaredField(obj.toString()).getAnnotation(FDKey.class)!=null && object.getClass().getDeclaredField(obj.toString()).getAnnotation(FDKey.class).value()){
                    continue;
                }
                field = field + "," + obj.toString();
                param = param+",?";
                os.add(map.get(obj));
            }
            //替换第一个逗号
            field = field.replaceFirst(",","");
            param = param.replaceFirst(",","");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new Object[]{"insert into " + tablename + "(" + field + ") values(" + param + ")", os.toArray()};
    }

    /**
     * 生成更新语句（单表）
     * @param tablename 表名
     * @param object 对象
     * @return 返回数组第一个值是sql语句，第二个值是params[]
     */
    public static Object[] createUpdateSql(String tablename, Object object) throws NoSuchFieldException {
        String str = "";
        String primaryKey = "id";
        Object id = null;
        try {
            id = ReflectUtils.getValueForObj("id",object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Object[] os = null;
        try {
            //拿到字段和字段值的map，过滤空字段
            Map<String,Object> map = ReflectUtils.getFiledArrayClearNull(object);
            Set set = map.keySet();
            os = new Object[set.size()+1];
            int i =0;
            for(Object obj : set){
/*                if (object.getClass().getDeclaredField(obj.toString()).getAnnotation(FDKey.class)!=null){
                    id = map.get(obj);
                    primaryKey = obj.toString();
                    continue;
                }*/
                str = str+","+obj+"=?";
                os[i] =map.get(obj);
                i++;
            }
            os[set.size()] = id;
            str = str.replaceFirst(",","");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new Object[]{"update "+tablename+" set "+str+" where "+primaryKey+"=?", os};
    }
}
