package zkl.common.annotation;

import java.lang.annotation.*;

/**
 * 忽略该字段
 * Created by Administrator on 2017/6/1 0001.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface FDFilter {
    boolean value() default true;
}
