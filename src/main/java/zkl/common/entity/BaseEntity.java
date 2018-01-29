package zkl.common.entity;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/27.
 */
public class BaseEntity implements Serializable{

    /**
     * JSON方式打印输出
     * @return
     */
    @Override
    public String toString() {
        return JSON.toJSON(this).toString();
    }

}
