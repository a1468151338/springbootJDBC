package zkl.entity;

import zkl.common.annotation.FDKey;
import zkl.common.entity.BaseEntity;

/**
 * Created by Administrator on 2017/12/28.
 */
public class SysPermission extends BaseEntity{
    @FDKey
    private Integer id;
    private String name;

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
