package zkl.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * Created by Administrator on 2018/1/24.
 */
@Configuration
public class UploadConfig {
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("G:\\springbootJDBC\\target\\");
        //factory.setLocation("G:\\springbootJDBC\\target\\");
        factory.setMaxFileSize(1024*1024*50);
        return factory.createMultipartConfig();
    }
}
