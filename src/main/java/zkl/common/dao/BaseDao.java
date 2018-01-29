package zkl.common.dao;

import com.alibaba.fastjson.JSONArray;
import zkl.common.entity.BaseEntity;
import zkl.common.entity.PageBean;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
public interface BaseDao<T extends BaseEntity> {

    //添加数据,返回最后添加的主键
    public Object add(T t) throws NoSuchFieldException, SQLException;

    //查询个数
    public Integer findTotal();

    //条件查询个数
    public Integer findTotal(String filter,Object[] objects);

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
    public List<T> findPage(PageBean pageBean) throws IllegalAccessException;

    //条件查询分页
    public List<T> findPage(PageBean pageBean,String fields, String fileter) throws IllegalAccessException;

    //返回JsonArray
    public JSONArray queryForJson(String sql,Object[] objects);

    public T queryForObjByME(String sql,Object[] objects) throws IllegalAccessException;

    public List<T> queryForListByME(String sql,Object[] objects) throws IllegalAccessException;
}
