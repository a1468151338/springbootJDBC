package zkl.entity;

import zkl.common.annotation.FDFilter;
import zkl.common.annotation.FDKey;
import zkl.common.entity.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
public class SysRole extends BaseEntity{
    @FDKey
    private Integer id;
    private String name;
    @FDFilter
    private List<SysResource> resourcesList;

    public List<SysResource> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(List<SysResource> resourcesList) {
        this.resourcesList = resourcesList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
