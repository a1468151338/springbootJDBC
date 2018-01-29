package zkl.web.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zkl.common.util.LogUtils;
import zkl.entity.SysResource;
import zkl.entity.SysRole;
import zkl.entity.SysUser;
import zkl.service.SysResourceService;
import zkl.service.SysRoleService;
import zkl.service.SysUserService;
import zkl.util.ResponseTem;
import zkl.web.BaseCtrl;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Administrator on 2017/12/28.
 */
@RestController
@RequestMapping("/api/sys/main/")
public class MainCtrl extends BaseCtrl {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysResourceService sysResourceService;

    @RequestMapping("doLogin")
    public String doLogin(@RequestBody SysUser sysUser){
        try {
            SysUser resSysUser = sysUserService.doLogin(sysUser);
            if (resSysUser!=null && resSysUser.getId()!=null){
                saveToken(resSysUser.getId());
                return ResponseTem.successTem(token).toJSONString();
            }else {
                return ResponseTem.errorTem("用户不存在").toJSONString();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("doLogout")
    public String doLogout(){
        try {
            removeToken();
            return ResponseTem.successTem("登出成功").toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("登出失败").toJSONString();
        }
    }

    @RequestMapping("userInfo")
    public String userInfo(){
        try {
            token = request.getHeader("token");
            System.out.println(session.getAttribute(token));
            return userId==null?null:sysUserService.findById(userId).toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ResponseTem.errorTem("").toJSONString();
    }

    @RequestMapping("userResource")
    public String UserResource(){
        try {
            JSONObject jsonObject = ResponseTem.successTem("");

            List<SysRole> roleList = sysRoleService.findRoleListByUserId(userId);
            for (SysRole sysRole:roleList){
                if (sysRole.getName().equals("超级管理员")){
                    //超级管理员拥有所有角色
                    jsonObject.put("resourceList",sysResourceService.findAll());
                }else {
                    jsonObject.put("resourceList",sysResourceService.findListByRoleId(sysRole.getId()));
                }
                return jsonObject.toJSONString();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return ResponseTem.errorTem();
    }


}
