package zkl.common.service;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import zkl.common.dao.BaseDao;
import zkl.common.entity.BaseEntity;
import zkl.common.entity.PageBean;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T>{

    @Autowired
    protected BaseDao<T> baseDao;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public Object add(T t) throws NoSuchFieldException, SQLException {
        return baseDao.add(t);
    }

    @Override
    public T findById(Object id) throws IllegalAccessException {
        return baseDao.findById(id);
    }

    @Override
    public List<T> findAll() throws IllegalAccessException {
        return baseDao.findAll();
    }

    @Override
    public List<T> findByFilter(String filter) throws IllegalAccessException {
        return baseDao.findByFilter(filter);
    }

    @Override
    public List<T> findByFilter(String filed, String filter) throws IllegalAccessException {
        return baseDao.findByFilter(filed,filter);
    }

    @Override
    public Integer update(T t) throws NoSuchFieldException {
        return baseDao.update(t);
    }

    @Override
    public Integer updateFields(String field, String[] values, Object id) {
        return baseDao.updateFields(field,values,id);
    }

    @Override
    public Integer deleteById(Object id) {
        return baseDao.deleteById(id);
    }

    @Override
    public PageBean<T> findPage(Integer pageSize,Integer pageNum) throws IllegalAccessException {
        Integer total = baseDao.findTotal();
        PageBean<T> pageBean = new PageBean<T>(pageNum,pageSize,total);
        pageBean.setDatas(baseDao.findPage(pageBean));
        return pageBean;
    }

    @Override
    public PageBean<T> findPage(Integer pageSize,Integer pageNum, String fields, String fileter) throws IllegalAccessException {
        Integer total = baseDao.findTotal(fileter,null);
        PageBean<T> pageBean = new PageBean<T>(pageNum,pageSize,total);
        pageBean.setDatas(baseDao.findPage(pageBean,fields,fileter));
        return pageBean;
    }

    @Override
    public JSONArray queryForJson(String sql, Object[] objects) {
        return baseDao.queryForJson(sql,objects);
    }

    @Override
    public T queryForObjByME(String sql, Object[] objects) throws IllegalAccessException {
        return baseDao.queryForObjByME(sql, objects);
    }

    @Override
    public List<T> queryForListByME(String sql, Object[] objects) throws IllegalAccessException {
        return baseDao.queryForListByME(sql, objects);
    }

    @Override
    public PageBean<T> queryForPageByME(Integer pageNum,Integer pageSize,String sql, Object[] objects) throws IllegalAccessException {
        Integer total = baseDao.findTotal();
        PageBean<T> pageBean = new PageBean<T>(pageNum,pageSize,total);
        pageBean.setDatas(baseDao.queryForListByME(sql, objects));
        return pageBean;
    }


}
