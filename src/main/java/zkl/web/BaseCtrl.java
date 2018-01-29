package zkl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RestController;
import zkl.common.util.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/12/28.
 */
@RestController
public class BaseCtrl {

    public Integer userId;

    public String token;

    @Autowired
    public HttpServletRequest request;

    @Autowired
    public HttpServletResponse response;

    @Autowired
    public HttpSession session;

    protected String saveToken(Object id){
        token = CommonUtils.getUUID();
        session.setAttribute(token,new Integer(id.toString()));
        return token;
    }

    protected void removeToken() throws Exception{
        request.getSession().removeAttribute(token);
    }

    protected Object getIdByToken(){
        return token==null?null:request.getSession().getAttribute(token);
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
