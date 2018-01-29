package zkl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zkl.common.service.BaseServiceImpl;
import zkl.common.util.CommonUtils;
import zkl.dao.SysAttachmentDao;
import zkl.dao.SysRoleDao;
import zkl.dao.SysUserDao;
import zkl.entity.SysAttachment;
import zkl.entity.SysUser;
import zkl.enums.UploadType;
import zkl.service.SysAttachmentService;
import zkl.service.SysUserService;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/12/27.
 */
@Service
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment> implements SysAttachmentService{
    @Autowired
    private SysAttachmentDao sysAttachmentDao;

    @Autowired
    private MultipartConfigElement config;

    public SysAttachment doSavePath(MultipartFile file) throws NoSuchFieldException, SQLException {
        SysAttachment sysAttachment = new SysAttachment();
        sysAttachment.setName(file.getOriginalFilename());
        sysAttachment.setSize(file.getSize()+"");
        sysAttachment.setType(UploadType.Img.getName());
        sysAttachment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        String path = "upload/"+ CommonUtils.getUUID()+"."+file.getOriginalFilename().replaceAll(".*?\\.","");
        sysAttachment.setPath(path);
        try {
            File outfile = new File(path);
            if (outfile.exists()){
                outfile.mkdirs();
                outfile.delete();
            }
            file.transferTo(outfile);
            sysAttachmentDao.add(sysAttachment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sysAttachment;
    }

    @Override
    public String downPath() {
        return config.getLocation();
    }

    @Override
    public Boolean doDeleteFile(Integer id) throws IllegalAccessException {
        try {
            SysAttachment sysAttachment = sysAttachmentDao.findById(id);
            sysAttachmentDao.deleteById(id);
            File file = new File(downPath()+sysAttachment.getPath());
            if(!file.exists()){
                file.delete();
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
