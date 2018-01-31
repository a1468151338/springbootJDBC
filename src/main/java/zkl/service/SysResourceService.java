package zkl.service;

import zkl.common.service.BaseService;
import zkl.entity.SysResource;
import zkl.entity.SysRole;

import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2017/12/27.
 */
public interface SysResourceService extends BaseService<SysResource>{
    /**
     * 角色拥有资源列表
     * @param roleId
     * @return
     */
    public List<SysResource> findListByRoleId(Integer roleId) throws IllegalAccessException;

    /**
     * 为一个角色设置资源
     * @param roleId
     * @param resourceId
     */
    public void saveRoleResource(Integer roleId, Object[] resourceId);

    /**
     * 转移
     * @param ids
     */
    public void move(Object[] ids,Integer parentId);

}
