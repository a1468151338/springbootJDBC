package zkl.common.entity.handle;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.util.LinkedList;

/**
 * Created by Administrator on 2017/12/27.
 */
public class QueryResJsonHandle {

    private static QueryResJsonHandle queryResJsonHandle = new QueryResJsonHandle();

    public QueryResJsonHandle() {

    }

    public static QueryResJsonHandle getInstance(){
        return queryResJsonHandle;
    }

    public JSONArray handleRow(SqlRowSet sqlRowSet){
        /*先获取列名集合*/
        SqlRowSetMetaData metaData = sqlRowSet.getMetaData();
        int col = metaData.getColumnCount();//列数
        LinkedList<String> colNameList = new LinkedList<String>();//列别名集合
        for (int i=1;i<=col;i++){
            colNameList.addLast(metaData.getColumnLabel(i));//别名
        }
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = null;
        while (sqlRowSet.next()){
            jsonObject = new JSONObject();
            for (String colName : colNameList){
                jsonObject.put(colName,sqlRowSet.getObject(colName));
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public QueryResJsonHandle getQueryResJsonHandle() {
        return queryResJsonHandle;
    }

    public void setQueryResJsonHandle(QueryResJsonHandle queryResJsonHandle) {
        this.queryResJsonHandle = queryResJsonHandle;
    }
}
