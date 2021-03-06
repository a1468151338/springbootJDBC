package zkl.service;

import org.springframework.web.multipart.MultipartFile;
import zkl.common.service.BaseService;
import zkl.entity.SysAttachment;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/12/27.
 */
public interface SysAttachmentService extends BaseService<SysAttachment>{

    //保存文件
    public SysAttachment doSavePath(MultipartFile file, String type) throws NoSuchFieldException, SQLException;

    //下载路径
    public String downPath();

    //删除文件
    public Boolean doDeleteFile(Object id) throws IllegalAccessException;
}
