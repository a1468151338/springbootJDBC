package zkl.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import zkl.common.util.LogUtils;
import zkl.common.util.ReflectUtils;
import zkl.entity.SysResource;
import zkl.entity.SysRole;
import zkl.service.SysResourceService;
import zkl.service.SysRoleService;
import zkl.util.ResponseTem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Administrator on 2017/12/28.
 */
@Component
@Order(1)
@Aspect
public class WebControllerAop {

	@Autowired
	SysResourceService sysResourceService;

	@Autowired
	SysRoleService sysRoleService;

    /**
     * 前置通知，方法调用前被调用
     * @param joinPoint
     */
    //@Before("executeService()")
    public void doBeforeAdvice(JoinPoint joinPoint) throws NoSuchFieldException, IllegalAccessException {
        //System.out.println("我是前置通知!!!");
        //获取目标方法的参数信息
        //Object[] obj = joinPoint.getArgs();
        //AOP代理类的信息
        Object proxyObj = joinPoint.getThis();
        //代理的目标对象
        Object object = joinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
/*        //代理的是哪一个方法
        System.out.println(signature.getName());
        //AOP代理类的名字
        System.out.println(signature.getDeclaringTypeName());
        //AOP代理类的类（class）信息
        signature.getDeclaringType();*/
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        //不是去登录的都要获取token
        /*if (!signature.getName().equals("doLogin")){
            String token = request.getHeader("token");
            if (!StringUtils.isEmpty(token)){
                Class clazz = object.getClass();
                Field tokenField = clazz.getField("token");
                ReflectUtils.doSetField(tokenField,object,token);
                //根据token获取userId
                Field userIdField = clazz.getField("userId");
                ReflectUtils.doSetField(userIdField,object,request.getSession().getAttribute(token));
            }
        }*/
        //如果要获取Session信息的话，可以这样写：
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        /*Enumeration<String> enumeration = request.getParameterNames();
        Map<String,String> parameterMap = new HashMap();
        while (enumeration.hasMoreElements()){
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter,request.getParameter(parameter));
        }
        String str = JSON.toJSONString(parameterMap);
        if(obj.length > 0) {
            System.out.println("请求的参数信息为："+str);
        }*/
    }

    //匹配包及其子包下的所有类的所有方法
    @Pointcut("execution(* zkl.web..*.*(..))")
    public void executeService(){

    }

    /**
     * 后置最终通知（目标方法只要执行完了就会执行后置通知方法）
     * @param joinPoint
     */
    @After("executeService()")
    public void doAfterAdvice(JoinPoint joinPoint){
        //System.out.println("后置通知执行了!!!!");
    }

    /**
     * 后置返回通知
     * 这里需要注意的是:
     *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     * @param joinPoint
     * @param keys
     */
    @AfterReturning(value = "execution(* zkl.web..*.*(..))",returning = "keys")
    public void doAfterReturningAdvice1(JoinPoint joinPoint,Object keys){
        //System.out.println("第一个后置返回通知的返回值："+keys);
    }

    @AfterReturning(value = "execution(* zkl.web..*.*(..))",returning = "keys",argNames = "keys")
    public void doAfterReturningAdvice2(String keys){
       //System.out.println("第二个后置返回通知的返回值："+keys);
    }

    /**
     * 后置异常通知
     *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     *  throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *      对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     * @param joinPoint
     * @param exception
     */
    @AfterThrowing(value = "executeService()",throwing = "exception")
    public void doAfterThrowingAdvice(JoinPoint joinPoint,Throwable exception){
        LogUtils.getLogger().error(joinPoint.getSignature().getName()+"发生了异常"+exception.getMessage());
    }

    /**
     * 环绕通知：
     *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型
     */
    //@Around("execution(* zkl.web..*.testAround*(..))")
    @Around("execution(* zkl.web.sys..*.*(..))")
    public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){
        //代理的目标对象
        Object object = proceedingJoinPoint.getTarget();
        //用的最多 通知的签名
        Signature signature = proceedingJoinPoint.getSignature();
        //获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        try {
            //不是去登录的都要获取token
            if (!signature.getName().equals("doLogin")){
                String token = request.getHeader("token");
                String userid = StringUtils.isEmpty(token)?"":request.getSession().getAttribute(token)==null?"":request.getSession().getAttribute(token).toString();
                if (!StringUtils.isEmpty(token)  && !StringUtils.isEmpty(userid)){
                    Class clazz = object.getClass();
                    Field tokenField = clazz.getField("token");
                    ReflectUtils.doSetField(tokenField,object,token);
                    //根据token获取userId
                    Field userIdField = clazz.getField("userId");
                    ReflectUtils.doSetField(userIdField,object,new Integer(userid));

					//是否有访问权限
					String uri = request.getRequestURI();
					//主要的不需要权限
					if(!uri.startsWith("/api/sys/main/")){
						AntPathMatcher antPathMatcher = new AntPathMatcher();
						List<SysResource> sysResourceServices = sysResourceService.findByFilter(" order by urlPattern ASC");
						Boolean hasPermission = false;
						for(SysResource sysResource : sysResourceServices){
							//路径匹配到，判断权限
							if(antPathMatcher.match(sysResource.getUrlPattern(),uri)){
								List<SysRole> roleList = sysRoleService.roleByResourceId(sysResource.getId());
								List<SysRole> userRoleList = sysRoleService.findRoleListByUserId(new Integer(userid));
								for(SysRole sysRole : userRoleList){
									if("超级管理员".equals(sysRole.getName())){
										hasPermission=true;
										break;
									}
								}
								for(SysRole sysRole : roleList){
									if(hasPermission){
										break;
									}
									Integer roleId = sysRole.getId();
									for(SysRole userRole : userRoleList){
										if(userRole.getId()==roleId){
											hasPermission=true;
											break;
										}
									}
								}
								break;
							}
						}
						if(!hasPermission){
							return ResponseTem.noPermissions();
						}
					}
                }else{
                    //没有登录
                    return ResponseTem.noLogin();
                }
            }
            Object obj = proceedingJoinPoint.proceed();
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            LogUtils.getLogger().error(signature.getName()+"方法出错了："+throwable.getMessage());
        }
        return ResponseTem.errorTem("").toJSONString();
    }

}
