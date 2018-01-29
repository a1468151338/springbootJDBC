package zkl.common.annotation;

import java.lang.annotation.*;

/**
 * 主键,设置TRUE为自增
 * Created by Administrator on 2017/6/1 0001.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface FDKey {
    boolean value() default true;
}
