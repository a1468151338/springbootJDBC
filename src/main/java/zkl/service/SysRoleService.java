package zkl.service;

import zkl.common.service.BaseService;
import zkl.entity.SysRole;
import zkl.entity.SysUser;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
public interface SysRoleService extends BaseService<SysRole>{
    /**
     * 用户角色列表
     * @param userid
     * @return
     */
    public List<SysRole> findRoleListByUserId(Integer userid) throws IllegalAccessException;

    /**
     * 为一个用户设置角色
     * @param userid
     * @param roleid
     */
    public void saveUserRole(Integer userid,Object[] roleid);

	/**
	 * 资源对应角色
	 * @return
	 */
	public List<SysRole> roleByResourceId(Integer resourceId) throws IllegalAccessException;
}
