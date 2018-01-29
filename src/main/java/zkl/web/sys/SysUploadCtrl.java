package zkl.web.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import zkl.common.util.CommonUtils;
import zkl.entity.SysAttachment;
import zkl.enums.UploadType;
import zkl.service.SysAttachmentService;
import zkl.util.ResponseTem;
import zkl.web.BaseCtrl;

import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2018/1/21.
 */
@RestController
@RequestMapping("/api/sys/attachment/")
public class SysUploadCtrl extends BaseCtrl{

    @Autowired
    private SysAttachmentService sysAttachmentService;

    @RequestMapping("uploadImg")
    public String uploadImg(MultipartFile file){
        //List<MultipartFile> fileList = ((MultipartHttpServletRequest)request).getFiles("file");
        try {
            sysAttachmentService.doSavePath(file);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseTem.successTem();
    }

    @RequestMapping("delete")
    public String delete(@RequestBody JSONObject jsonObject){
        try{
            Object id = jsonObject.get("id");
            sysAttachmentService.doDeleteFile(new Integer(id.toString()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseTem.errorTem();
        }
        return ResponseTem.successTem();
    }

    @RequestMapping("list")
    public String findUserList(@RequestBody JSONObject page){
        Integer pageNum;
        Integer pageSize;
        String searchKey = CommonUtils.filterStr(page.get("searchKey")==null?"":page.get("searchKey").toString().trim());
        try{
            pageNum = new Integer(page.get("pageNum").toString());
            pageSize = new Integer(page.get("pageSize").toString());
        }catch (Exception e){
            pageNum =1;
            pageSize = 12;
        }
        pageNum = (pageNum<1)?1:pageNum;
        pageSize = (pageSize<1||pageSize>500)?12:pageSize;
        try {
            return JSONArray.toJSONString(sysAttachmentService.findPage(pageSize,pageNum,"id,name,createTime",
                    searchKey.equals("")?"":"where name like '%"+searchKey+"%' or type like '%"+searchKey+"%' "));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("uploadAttach")
    public String uploadAttach(MultipartFile file){
        return "";
    }

    @RequestMapping("download")
    public void download(Integer id){
        SysAttachment sysAttachment =null;
        BufferedInputStream bis = null;
        OutputStream os = null;
        byte[] buff = new byte[1024];
        try {
            sysAttachment = sysAttachmentService.findById(id);
            if (sysAttachment!=null){
                File file = new File(sysAttachmentService.downPath()+sysAttachment.getPath());
                if(file.exists()){
                    response.setHeader("content-type", "application/octet-stream");
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(sysAttachment.getName(),"UTF-8"));
                    bis = new BufferedInputStream(new FileInputStream(file));
                    os = response.getOutputStream();
                    int i = bis.read(buff);
                    while (i!=-1){
                        os.write(buff,0,buff.length);
                        os.flush();
                        i = bis.read(buff);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(bis!=null){
                    bis.close();
                }
                if(os!=null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean fileType(MultipartFile file){
        String imgReg = ".+\\.(jpg|jpeg|png|gif|bmp)$";
        String fileReg = ".+\\.(doc|docx|xls|xlsx|ppt|pptx|txt|zip|rar|xml)$";
        return true;
    }

}
