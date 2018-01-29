package zkl.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
