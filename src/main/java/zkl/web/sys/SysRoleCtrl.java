package zkl.web.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zkl.common.util.CommonUtils;
import zkl.entity.SysResource;
import zkl.entity.SysRole;
import zkl.entity.SysUser;
import zkl.service.SysResourceService;
import zkl.service.SysRoleService;
import zkl.util.ResponseTem;
import zkl.web.BaseCtrl;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Administrator on 2018/1/7.
 */
@RestController
@RequestMapping("/api/sys/role/")
public class SysRoleCtrl extends BaseCtrl {
    @Autowired
    SysRoleService sysRoleService;

    @Autowired
    SysResourceService sysResourceService;

    @RequestMapping("list")
    public String findRoleList(@RequestBody JSONObject page){
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
            return JSONArray.toJSONString(sysRoleService.findPage(pageSize,pageNum,"id,name",
                    searchKey.equals("")?"":"where name like '%"+searchKey+"%'"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("getAll")
    public String allRole(){
        try {
            JSONObject jsonObject = ResponseTem.successTem("");
            jsonObject.put("roleList",sysRoleService.findAll());
            return jsonObject.toJSONString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("info")
    public String sysRoleInfo(@RequestBody JSONObject json){
        try {
            SysRole sysRole = sysRoleService.findById(json.get("id"));
            sysRole.setResourcesList(sysResourceService.findListByRoleId(sysRole.getId()));
            JSONObject jsonObject = ResponseTem.successTem("");
            jsonObject.put("sysRole",sysRole);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("update")
    public String updateSysRole(@RequestBody JSONObject jsonObject){
        try {
            System.out.println(JSONObject.toJSONString(jsonObject.get("sysRole")));
            SysRole sysRole = JSONObject.parseObject(JSONObject.toJSONString(jsonObject.get("sysRole")),SysRole.class);
            Object object = null;
            Integer roleId = 0;
            if (sysRole==null || sysRole.getId()==null || sysRole.getId()<1){
                object = sysRoleService.add(sysRole);
                roleId = new Integer(object.toString());
            }else{
                object = sysRoleService.update(sysRole);
                roleId = sysRole.getId();
            }
            sysResourceService.saveRoleResource(roleId,JSONArray.parseArray(jsonObject.get("resourceIds").toString()).toArray());
            return ResponseTem.successTem("").toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("delete")
    public String delRole(@RequestBody JSONObject jsonObject){
        try {
            sysRoleService.deleteById(jsonObject.get("id"));
            return ResponseTem.successTem("").toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("resourceList")
    public String resourceListByRoleId(@RequestBody JSONObject jsonObject){
        try {
            List<SysResource> list = sysResourceService.findListByRoleId(new Integer(jsonObject.get("roleId").toString()));
            JSONObject object = ResponseTem.successTem("");
            object.put("resourceList",list);
            return object.toJSONString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }
}
