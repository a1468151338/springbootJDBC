package zkl.web.pblic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zkl.service.SysAttachmentService;
import zkl.web.BaseCtrl;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Objects;

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
    public void download(Integer id){
        try {
            String s = "/"+sysAttachmentService.findById(id).getPath();
            System.out.println(request.getRemoteAddr());
            response.sendRedirect(s);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
