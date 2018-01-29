package zkl.common.entity.handle;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Service;
import zkl.common.annotation.FDFilter;
import zkl.common.util.ReflectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/12/27.
 */
public class QueryResHandle<T> {

    private Class<T> tClass;

    public QueryResHandle(Class<T> tClass){
        this.tClass =tClass;
    }

    /**
     * 单行封装成对象
     * @param sqlRowSet
     * @return
     * @throws IllegalAccessException
     */
    public T parseObject(SqlRowSet sqlRowSet) throws IllegalAccessException {
        T t = null;
        //列名集合
        SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
        //列数
        int col = metaData.getColumnCount();
        //列别名集合
        ArrayList<String> colNameList = new ArrayList<String>();
        //设置别名
        for (int i=1;i<=col;i++){
            colNameList.add(metaData.getColumnLabel(i));//别名
        }
        //只有1行，所以IF
        if (sqlRowSet.next()){
            t = setObjValue(t,colNameList,sqlRowSet);
        }
        return t;
    }

    /**
     * 多行封装成对象
     * @param sqlRowSet
     * @return
     * @throws IllegalAccessException
     */
    public List<T> parseListObject(SqlRowSet sqlRowSet) throws IllegalAccessException {
        List<T> resList = new ArrayList<T>();
        T t = null;
        //列名集合
        SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
        //列数
        int col = metaData.getColumnCount();
        //列别名集合
        ArrayList<String> colNameList = new ArrayList<String>();
        //设置别名
        for (int i=1;i<=col;i++){
            colNameList.add(metaData.getColumnLabel(i));//别名
        }
        while (sqlRowSet.next()){
            t = setObjValue(t,colNameList,sqlRowSet);
            resList.add(t);
        }
        return resList;
    }

    /**
     * 一行数据值设置到对象中
     * @param t
     * @param colNameList
     * @param sqlRowSet
     * @return
     * @throws IllegalAccessException
     */
    private T setObjValue(T t,List<String> colNameList,SqlRowSet sqlRowSet) throws IllegalAccessException {
        t = BeanUtils.instantiate(tClass);
        for (String colName : colNameList){
            Field field = null;
            Object obj = sqlRowSet.getObject(colName);
            if (obj==null){
                continue;
            }
            try {
                field = tClass.getDeclaredField(colName);
                ReflectUtils.doSetField(field,t,obj);
            } catch (NoSuchFieldException e) {
                try {
                    if(colName.contains(".")){
                        String fieldName = colName.split("\\.")[0];
                        field = tClass.getDeclaredField(fieldName);
                        if (field!=null && field.getAnnotation(FDFilter.class)!=null && !field.getType().getName().matches("^(java|int|float|double|char).*$")){
                            //有自己的注解，而且是自定义类
                            Class inclass = field.getType();
                            Object inBean = null;
                            String inFieldName = colName.split("\\.")[1];
                            Field inField = inclass.getDeclaredField(inFieldName);
                            if (inField!=null){
                                inField.setAccessible(true);
                                inBean = BeanUtils.instantiate(inclass);
                                ReflectUtils.doSetField(inField,inBean,obj);
                                ReflectUtils.doSetField(field,t,inBean);
                            }
                        }
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
        return t;
    }
}
