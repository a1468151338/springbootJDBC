package zkl.web.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zkl.common.util.CommonUtils;
import zkl.entity.SysResource;
import zkl.service.SysResourceService;
import zkl.service.SysRoleService;
import zkl.util.ResponseTem;
import zkl.web.BaseCtrl;

/**
 * Created by Administrator on 2018/1/7.
 */
@RestController
@RequestMapping("/api/sys/resource/")
public class SysResourceCtrl extends BaseCtrl {
    @Autowired
    SysResourceService sysResourceService;

    @Autowired
    SysRoleService sysRoleService;

    @RequestMapping("list")
    public String List(){
        try {
            JSONObject jsonObject  = ResponseTem.successTem("");
            jsonObject.put("list",sysResourceService.findAll());
            return jsonObject.toJSONString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("childList")
    public String childList(@RequestBody JSONObject jsonObject){
        try {
            JSONObject object = ResponseTem.successTem("");
            object.put("children",sysResourceService.findByFilter(" WHERE parentId="+ CommonUtils.filterStr(jsonObject.get("id").toString())));
            return object.toJSONString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("info")
    public String info(@RequestBody JSONObject json){
        try {
            SysResource sysResource = sysResourceService.findById(json.get("id"));
            JSONObject jsonObject = ResponseTem.successTem("");
            jsonObject.put("sysResource",sysResource);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("move")
    public String move(@RequestBody JSONObject json){
        try {
            sysResourceService.move(json.getJSONArray("ids").toArray(),json.getInteger("parentId"));
            return ResponseTem.successTem("").toJSONString();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("update")
    public String update(@RequestBody SysResource sysResource){
        try {
            Object object = null;
            if (sysResource==null || sysResource.getId()==null || sysResource.getId()<1){
                object = sysResourceService.add(sysResource);
            }else{
                object = sysResourceService.update(sysResource);
            }
            return ResponseTem.successTem("").toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

    @RequestMapping("delete")
    public String delete(@RequestBody JSONObject jsonObject){
        try {
            sysResourceService.deleteById(jsonObject.get("id"));
            return ResponseTem.successTem("").toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseTem.errorTem("").toJSONString();
        }
    }

}
