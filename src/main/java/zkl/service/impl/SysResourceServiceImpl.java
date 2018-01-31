package zkl.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zkl.common.service.BaseServiceImpl;
import zkl.dao.SysResourceDao;
import zkl.entity.SysResource;
import zkl.entity.SysRole;
import zkl.service.SysResourceService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2018/1/1.
 */
@Service
public class SysResourceServiceImpl extends BaseServiceImpl<SysResource> implements SysResourceService{

    @Autowired
    private SysResourceDao sysResourceDao;

    @Override
    public List<SysResource> findListByRoleId(Integer roleId) throws IllegalAccessException {
        return sysResourceDao.queryForListByME("SELECT r.id,r.name,r.parentId,r.path,r.icon FROM sys_resource r,sys_role_resource rr WHERE rr.roleid=? AND rr.resourceid=r.id"
                ,new Object[]{roleId});
    }

    @Transactional
    @Override
    public void saveRoleResource(Integer roleId, Object[] resourceId) {
        //先删除角色有的资源
        jdbcTemplate.update("DELETE FROM sys_role_resource WHERE roleid=?",new Object[]{roleId});
        //后设置
        jdbcTemplate.batchUpdate("INSERT INTO sys_role_resource (roleid,resourceid) VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1,roleId);
                ps.setObject(2,resourceId[i]);
            }

            @Override
            public int getBatchSize() {
                return resourceId.length;
            }
        });
    }

    @Override
    public void move(Object[] ids,Integer parentId) {
        String s = "";
        for(Object obj:ids){
            s = s+",?";
        }
        jdbcTemplate.update("UPDATE sys_resource SET parentId="+parentId+" WHERE id IN ("+s.replaceFirst(",","")+")",ids);
    }
}
