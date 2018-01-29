package zkl.common.service;

import com.alibaba.fastjson.JSONArray;
import zkl.common.entity.BaseEntity;
import zkl.common.entity.PageBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
public interface BaseService<T extends BaseEntity> {
    //添加数据,返回最后添加的主键
    public Object add(T t) throws NoSuchFieldException, SQLException;

    //单表查询by ID
    public T findById(Object id) throws IllegalAccessException;

    //单表查询全部
    public List<T> findAll() throws IllegalAccessException;

    //条件查询
    public List<T> findByFilter(String filter) throws IllegalAccessException;

    //条件和字段查询
    public List<T> findByFilter(String filed, String filter) throws IllegalAccessException;

    public Integer update(T t) throws NoSuchFieldException;

    //更新字段ByID
    public Integer updateFields(String field, String[] values, Object id);

    //单个或多个删除
    public Integer  deleteById(Object id);

    //查询分页
    public PageBean<T> findPage(Integer pageSize,Integer pageNum) throws IllegalAccessException;

    //条件查询分页
    public PageBean<T> findPage(Integer pageSize,Integer pageNum, String fields, String fileter) throws IllegalAccessException;

    //返回JsonArray
    public JSONArray queryForJson(String sql, Object[] objects);

    //自己封装
    public T queryForObjByME(String sql,Object[] objects) throws IllegalAccessException;
    public List<T> queryForListByME(String sql,Object[] objects) throws IllegalAccessException;
    public PageBean<T> queryForPageByME(Integer pageNum,Integer pageSize,String sql,Object[] objects) throws IllegalAccessException;
}
