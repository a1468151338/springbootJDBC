package zkl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zkl.common.service.BaseServiceImpl;
import zkl.dao.SysUserDao;
import zkl.entity.SysUser;
import zkl.service.SysUserService;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/12/27.
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService{
    @Autowired
    private SysUserDao sysUserDao;

    @Override
    public SysUser doLogin(SysUser sysUser) throws IllegalAccessException {
        return sysUserDao.queryForObjByME("SELECT id FROM sys_user WHERE account=? AND password=?",new Object[]{sysUser.getAccount(),sysUser.getPassword()});
    }


}
