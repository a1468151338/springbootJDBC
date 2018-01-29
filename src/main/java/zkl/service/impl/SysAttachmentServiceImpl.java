package zkl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zkl.common.service.BaseServiceImpl;
import zkl.common.util.CommonUtils;
import zkl.dao.SysAttachmentDao;
import zkl.entity.SysAttachment;
import zkl.service.SysAttachmentService;

import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2017/12/27.
 */
@Service
public class SysAttachmentServiceImpl extends BaseServiceImpl<SysAttachment> implements SysAttachmentService{
    @Autowired
    private SysAttachmentDao sysAttachmentDao;

    @Autowired
    private MultipartConfigElement config;

    public SysAttachment doSavePath(MultipartFile file,String type) throws NoSuchFieldException, SQLException {
        SysAttachment sysAttachment = new SysAttachment();
        sysAttachment.setName(file.getOriginalFilename());
        sysAttachment.setSize(file.getSize()+"");
        sysAttachment.setType(type);
        sysAttachment.setCreateTime(new Timestamp(System.currentTimeMillis()));
        String path = downPath()+"upload/"+ CommonUtils.getUUID()+"."+file.getOriginalFilename().replaceAll(".*?\\.","");
        sysAttachment.setPath(path);
        try {
            File outfile = new File(path);
            if (!outfile.exists()){
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
    public Boolean doDeleteFile(Object ids) throws IllegalAccessException {
        try{
            List<SysAttachment> attachmentList = sysAttachmentDao.findByFilter("id,path"," where id in("+ids+")");
            for(SysAttachment sysAttachment : attachmentList){
                sysAttachmentDao.deleteById(sysAttachment.getId());
                File file = new File(downPath()+sysAttachment.getPath());
                if(file.exists()){
                    file.delete();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
