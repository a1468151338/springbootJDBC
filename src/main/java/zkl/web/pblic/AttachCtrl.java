package zkl.web.pblic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zkl.entity.SysAttachment;
import zkl.service.SysAttachmentService;
import zkl.web.BaseCtrl;

import java.io.*;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/1/26.
 */
@Controller
@RequestMapping("/pubApi/attach/")
public class AttachCtrl extends BaseCtrl{

    @Autowired
    SysAttachmentService sysAttachmentService;

    @RequestMapping("download")
    @ResponseBody
    public void download(Integer id,String type){
        BufferedInputStream bis = null;
        OutputStream out = null;
        byte[] buff = new byte[1024];
        try {
            SysAttachment sysAttachment = sysAttachmentService.findById(id);
            File file = new File(sysAttachmentService.downPath()+sysAttachment.getPath());
            if(file.exists()){
                if(type==null || type.equals("") || type.equals("img")){
                    response.setHeader("content-type", "image/*");
                }else{
                    response.setHeader("content-type", "application/octet-stream");
                    response.setContentType("application/octet-stream");
                    response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(sysAttachment.getName(),"UTF-8"));
                }
                //response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(sysAttachment.getName(),"UTF-8"));
                bis = new BufferedInputStream(new FileInputStream(file));
                out = response.getOutputStream();
                int i = bis.read(buff);
                while (i!=-1){
                    out.write(buff,0,buff.length);
                    out.flush();
                    i = bis.read(buff);
                }
            }
            /*String s = "/"+sysAttachmentService.findById(id).getPath();
            System.out.println(request.getRemoteAddr());
            response.sendRedirect(s);*/
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(bis!=null){
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
