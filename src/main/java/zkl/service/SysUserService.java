package zkl.service;

import zkl.common.service.BaseService;
import zkl.entity.SysUser;

/**
 * Created by Administrator on 2017/12/27.
 */
public interface SysUserService extends BaseService<SysUser>{
    //登录校验
    public SysUser doLogin(SysUser sysUser) throws IllegalAccessException;
}
