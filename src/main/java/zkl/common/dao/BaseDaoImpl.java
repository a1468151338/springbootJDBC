package zkl.common.dao;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import zkl.common.entity.BaseEntity;
import zkl.common.entity.PageBean;
import zkl.common.entity.handle.QueryResHandle;
import zkl.common.entity.handle.QueryResJsonHandle;
import zkl.common.util.SqlUtils;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T>{

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    //表名
    protected String tablename;

    protected Class<T> clazz;

    protected QueryResHandle<T> queryResHandle;

    public BaseDaoImpl(){
        //获取T
        ParameterizedType pt =  (ParameterizedType)this.getClass().getGenericSuperclass();
        clazz = (Class<T>)pt.getActualTypeArguments()[0];
        queryResHandle = new QueryResHandle<T>(clazz);

    }

    @Override
    public Object add(T t) throws NoSuchFieldException{
        Object[] objects = SqlUtils.createSql(tablename,t);
        jdbcTemplate.update(objects[0].toString(),(Object[]) objects[1]);
        //返回主键
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT last_insert_id()");
        sqlRowSet.next();
        return sqlRowSet.getObject("last_insert_id()");
    }

    @Override
    public Integer findTotal() {
        return jdbcTemplate.queryForObject("SELECT count(id) FROM "+this.tablename+"",Integer.class);
    }

    @Override
    public Integer findTotal(String filter,Object[] objects) {
        return jdbcTemplate.queryForObject("SELECT count(id) FROM "+this.tablename+" "+filter,Integer.class,objects);
    }

    @Override
    public T findById(Object id) throws IllegalAccessException {
        return queryResHandle.parseObject(jdbcTemplate.queryForRowSet("SELECT * FROM "+tablename+" WHERE id=?",new Object[]{id}));
    }

    @Override
    public List<T> findAll() throws IllegalAccessException {
        return queryResHandle.parseListObject(jdbcTemplate.queryForRowSet("SELECT * FROM "+tablename));
    }

    @Override
    public List<T> findByFilter(String filter) throws IllegalAccessException {
        return queryResHandle.parseListObject(jdbcTemplate.queryForRowSet("SELECT * FROM "+tablename+" "+filter));
    }

    @Override
    public List<T> findByFilter(String filed, String filter) throws IllegalAccessException {
        return queryResHandle.parseListObject(jdbcTemplate.queryForRowSet("SELECT "+filed+" FROM "+tablename+" "+filter));
    }

    @Override
    public Integer update(T t) throws NoSuchFieldException {
        Object[] obs  = SqlUtils.createUpdateSql(this.tablename,t);
        return  jdbcTemplate.update(obs[0].toString(),(Object[]) obs[1]);
    }

    @Override
    public Integer updateFields(String field, String[] values, Object id) {
        Object[] obj = new Object[values.length+1];
        for (int x=0;x<values.length;x++){
            obj[x] = values[x];
        }
        obj[obj.length-1] = id;
        return jdbcTemplate.update("UPDATE "+tablename+" SET "+field+" WHERE id=?",(Object[])obj);
    }

    @Override
    public Integer deleteById(Object id) {
        String[] strs = id.toString().split(",");
        String delete = "";
        for (String s : strs){
            delete = delete + ",?";
        }
        return jdbcTemplate.update("DELETE FROM "+tablename+" WHERE id IN ("+delete.replaceFirst(",","")+")",strs);
    }

    @Override
    public List<T> findPage(PageBean pageBean) throws IllegalAccessException {
        return queryResHandle.parseListObject(jdbcTemplate.queryForRowSet("SELECT * FROM "+tablename+" limit ?,?",
                new Object[]{pageBean.getBegin(),pageBean.getPageSize()}));
    }

    @Override
    public List<T> findPage(PageBean pageBean,String fields, String fileter) throws IllegalAccessException {
        return queryResHandle.parseListObject(jdbcTemplate.queryForRowSet("SELECT "+fields+" FROM "+tablename+" "+fileter+" limit ?,?",
                new Object[]{pageBean.getBegin(),pageBean.getPageSize()}));
    }

    @Override
    public JSONArray queryForJson(String sql, Object[] objects) {
        return QueryResJsonHandle.getInstance().handleRow(jdbcTemplate.queryForRowSet(sql,objects));
    }

    @Override
    public T queryForObjByME(String sql, Object[] objects) throws IllegalAccessException {
        return queryResHandle.parseObject(jdbcTemplate.queryForRowSet(sql,objects));
    }

    @Override
    public List<T> queryForListByME(String sql, Object[] objects) throws IllegalAccessException {
        return queryResHandle.parseListObject(jdbcTemplate.queryForRowSet(sql,objects));
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
