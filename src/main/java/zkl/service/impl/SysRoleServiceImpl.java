package zkl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zkl.common.service.BaseServiceImpl;
import zkl.dao.SysRoleDao;
import zkl.entity.SysRole;
import zkl.service.SysRoleService;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2018/1/1.
 */
@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService{

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    public List<SysRole> findRoleListByUserId(Integer userid) throws IllegalAccessException {
        return sysRoleDao.queryForListByME("SELECT r.id,r.name,r.state FROM sys_role r,sys_user_role ur WHERE ur.userid=? AND ur.roleid=r.id",new Object[]{userid});
    }

    @Transactional
    @Override
    public void saveUserRole(Integer userid, Object[] roleid) {
        //先删除用户有的角色
        jdbcTemplate.update("DELETE FROM sys_user_role WHERE userid=?",new Object[]{userid});
        //后设置
        jdbcTemplate.batchUpdate("INSERT INTO sys_user_role (userid,roleid) VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1,userid);
                ps.setObject(2,roleid[i]);
            }

            @Override
            public int getBatchSize() {
                return roleid.length;
            }
        });
    }

	@Override
	public List<SysRole> roleByResourceId(Integer resourceId) throws IllegalAccessException {
		return sysRoleDao.queryForListByME("SELECT r.id  FROM sys_role r,sys_role_resource rr WHERE rr.resourceid=? AND r.id=rr.roleid",new Object[]{resourceId});
	}
}
