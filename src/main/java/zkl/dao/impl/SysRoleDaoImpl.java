package zkl.dao.impl;

import org.springframework.stereotype.Repository;
import zkl.common.dao.BaseDaoImpl;
import zkl.dao.SysRoleDao;
import zkl.entity.SysRole;

/**
 * Created by Administrator on 2018/1/1.
 */
@Repository
public class SysRoleDaoImpl extends BaseDaoImpl<SysRole> implements SysRoleDao{
    public SysRoleDaoImpl(){
        this.tablename = "sys_role";
    }
}
