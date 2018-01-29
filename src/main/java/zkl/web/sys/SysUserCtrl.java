package zkl.web.sys;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zkl.common.entity.PageBean;
import zkl.common.util.CommonUtils;
import zkl.entity.SysUser;
import zkl.service.SysRoleService;
import zkl.service.SysUserService;
import zkl.util.ResponseTem;
import zkl.web.BaseCtrl;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2018/1/4.
 */
@RestController
@RequestMapping("/api/sys/user/")
public class SysUserCtrl extends BaseCtrl{
    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

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
            pageSize = 10;
        }
        pageNum = (pageNum<1)?1:pageNum;
        pageSize = (pageSize<1||pageSize>500)?10:pageSize;
        try {
            return JSONArray.toJSONString(sysUserService.findPage(pageSize,pageNum,"id,account,createTime",
                    searchKey.equals("")?"":"where name like '%"+searchKey+"%' or account like '%"+searchKey+"%' "));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("info")
    public String userInfo(@RequestBody JSONObject json){
        try {
            SysUser sysUser = sysUserService.findById(json.get("id"));
            sysUser.setPassword(null);
            JSONObject jsonObject = ResponseTem.successTem("");
            sysUser.setSysRoleList(sysRoleService.findRoleListByUserId(sysUser.getId()));
            jsonObject.put("sysUser",sysUser);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("update")
    public String updateUser(@RequestBody JSONObject jsonObject){
        try {
            Object[] objects = jsonObject.getJSONArray("sysRoleList").toArray();
            jsonObject.remove("sysRoleList");
            SysUser sysUser = JSONObject.parseObject(jsonObject.toJSONString(),SysUser.class);
            Object object = null;
            if (sysUser==null || sysUser.getId()==null || sysUser.getId()<1){
                sysUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
                object = sysUserService.add(sysUser);
            }else{
                sysUserService.update(sysUser);
                object = sysUser.getId();
            }
            sysRoleService.saveUserRole(new Integer(object.toString()),objects);
            return ResponseTem.successTem("").toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("delete")
    public String delUser(@RequestBody JSONObject jsonObject){
        try {
            sysUserService.deleteById(jsonObject.get("id"));
            return ResponseTem.successTem("").toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("userRole")
    public String userRole(Integer id){
        try {
            JSONObject jsonObject = ResponseTem.successTem("");
            jsonObject.put("roleList",sysRoleService.findRoleListByUserId(id));
            return jsonObject.toJSONString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

}
