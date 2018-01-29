package zkl.common.util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by Administrator on 2018/1/19.
 */
public class LogUtils {
    private static Logger logger = LogManager.getLogger();

    public static Logger getLogger(){
        return logger;
    }

}
