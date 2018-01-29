package zkl.dao.impl;

import org.springframework.stereotype.Repository;
import zkl.common.dao.BaseDaoImpl;
import zkl.dao.SysAttachmentDao;
import zkl.dao.SysUserDao;
import zkl.entity.SysAttachment;
import zkl.entity.SysUser;

/**
 * Created by Administrator on 2017/12/27.
 */
@Repository
public class SysAttachmentDaoImpl extends BaseDaoImpl<SysAttachment> implements SysAttachmentDao{
    public SysAttachmentDaoImpl(){
        this.tablename = "sys_attachment";
    }
}
