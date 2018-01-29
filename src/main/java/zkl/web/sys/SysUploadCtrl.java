package zkl.web.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import zkl.common.util.CommonUtils;
import zkl.entity.SysAttachment;
import zkl.enums.UploadType;
import zkl.service.SysAttachmentService;
import zkl.util.ResponseTem;
import zkl.web.BaseCtrl;

import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;

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
            System.out.println(file.getName());
            if(fileType(file,UploadType.Img.getName())){
                sysAttachmentService.doSavePath(file,UploadType.Img.getName());
            }else{
                return ResponseTem.errorTem("上传格式错误").toString();
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ResponseTem.successTem();
    }

    @RequestMapping("uploadAttach")
    public String uploadAttach(MultipartFile file){
        try {
            if(fileType(file,UploadType.Attach.getName())){
                sysAttachmentService.doSavePath(file,UploadType.Attach.getName());
            }else{
                return ResponseTem.errorTem("上传格式错误").toString();
            }
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
            sysAttachmentService.doDeleteFile(id);
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
        String type  = CommonUtils.filterStr(page.get("type").toString());
        String searchKey = CommonUtils.filterStr(page.get("searchKey")==null?"":page.get("searchKey").toString().trim());
        try{
            pageNum = new Integer(page.get("pageNum").toString());
            pageSize = new Integer(page.get("pageSize").toString());
        }catch (Exception e){
            pageNum =1;
            pageSize = 12;
        }
        pageNum = (pageNum<1)?1:pageNum;
        pageSize = (pageSize<1||pageSize>48)?12:pageSize;
        try {
            String filter = " where 1=1 ";
            if(!searchKey.equals("")){
                filter = filter+" and name like '%"+searchKey+"%' ";
            }
            if(!type.equals("") && (type.equals("图片") || type.equals("附件"))){
                filter = filter + " and type='"+type+"'";
            }
            filter = filter + " order by createTime DESC";
            return JSONArray.toJSONString(sysAttachmentService.findPage(pageSize,pageNum,"id,name,size,createTime",filter));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
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

    private Boolean fileType(MultipartFile file,String type){
        String imgReg = ".+\\.(jpg|jpeg|png|gif|bmp)$";
        String fileReg = ".+\\.(doc|docx|xls|xlsx|ppt|pptx|txt|zip|rar|pdf)$";
        if(type.equals(UploadType.Img.getName())){
            return file.getOriginalFilename().matches(imgReg);
        }else if(type.equals(UploadType.Attach.getName())){
            return file.getOriginalFilename().matches(fileReg);
        }else{
            return false;
        }
    }

}
