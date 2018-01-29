package zkl.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import zkl.common.dao.BaseDaoImpl;
import zkl.dao.SysUserDao;
import zkl.entity.SysUser;

/**
 * Created by Administrator on 2017/12/27.
 */
@Repository
public class SysUserDaoImpl extends BaseDaoImpl<SysUser> implements SysUserDao{

    public SysUserDaoImpl(){
        this.tablename = "sys_user";
    }
}
