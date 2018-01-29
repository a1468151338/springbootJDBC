package zkl.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import zkl.common.util.LogUtils;
import zkl.common.util.ReflectUtils;
import zkl.util.ResponseTem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;

/**
 * Created by Administrator on 2018/1/24.
 */
@Component
@Order(2)
@Aspect
public class UploadAop {

    //匹配包及其子包下的所有类的所有方法
    @Pointcut("execution(* zkl.web.sys..SysUploadCtrl.*(..))")
    public void executeService(){

    }
}
