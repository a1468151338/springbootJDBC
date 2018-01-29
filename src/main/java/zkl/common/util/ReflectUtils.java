package zkl.common.util;

import zkl.common.annotation.FDFilter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by whh on 2016/12/9.
 */
public class ReflectUtils {

    //Map字段和字段值
    public static Map<String,Object> getFiledArray(Object obj) throws IllegalAccessException {
        Field[] f = obj.getClass().getDeclaredFields();
        int len = f.length;
        if (len<1){
            return null;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        for(int i=0; i<len; i++){
            f[i].setAccessible(true);
            if (f[i].getAnnotation(FDFilter.class)==null){
                map.put(f[i].getName(),f[i].get(obj));
            }
        }
        return map;
    }

    //Map字段和字段值，过滤空字段
    public static Map<String,Object> getFiledArrayClearNull(Object obj) throws IllegalAccessException {
        Field[] f = obj.getClass().getDeclaredFields();
        int len = f.length;
        if (len<1){
            return null;
        }
        Map<String,Object> map = new HashMap<String,Object>();
        for(int i=0; i<len; i++){
            f[i].setAccessible(true);
            //字段上没有注解忽略，并且字段值不为空
            if (f[i].getAnnotation(FDFilter.class)==null && f[i].get(obj)!=null){
                map.put(f[i].getName(),f[i].get(obj));
            }
        }
        return map;
    }


    //反射字段名称数组
    public static String[] getFiledArray(Class t){
        Field[] f = t.getDeclaredFields();
        int len = f.length;
        if (len<1){
            return null;
        }
        String[] os = new String[len];
        for(int i=0; i<len; i++){
                os[i] = f[i].getName();
        }
        return os;
    }

    //Map字段和字段类型
    public static Map<String,String> getFiedMap(Class t){
        Map map = new HashMap();
        Field[] f = t.getDeclaredFields();
        for(Field fd : f){
                map.put(fd.getName(), fd.getType().getName());
        }
        return map;
    }

    /**
     * 设置字段值
     * @param field 字段
     * @param object 设置到哪个对象
     * @param value 设置的值
     * @throws IllegalAccessException
     */
    public static void doSetField(Field field,Object object,Object value) throws IllegalAccessException {
        field.setAccessible(true);
        field.set(object,value);
    }

    /**
     * 获取类的字段值
     * @param fieldName
     * @param object
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static Object getValueForObj(String fieldName,Object object) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(object);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz The class to introspect
     * @return the first generic declaration, or <code>Object.class</code> if cannot be determined
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型.
     * 如public BookManager extends GenricManager<Book>
     *
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
