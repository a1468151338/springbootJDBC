package zkl.dao.impl;

import org.springframework.stereotype.Repository;
import zkl.common.dao.BaseDaoImpl;
import zkl.dao.SysResourceDao;
import zkl.dao.SysRoleDao;
import zkl.entity.SysResource;
import zkl.entity.SysRole;

/**
 * Created by Administrator on 2018/1/1.
 */
@Repository
public class SysResourceDaoImpl extends BaseDaoImpl<SysResource> implements SysResourceDao{
    public SysResourceDaoImpl(){
        this.tablename = "sys_resource";
    }
}
