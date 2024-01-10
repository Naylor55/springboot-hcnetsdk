package com.ramble.springboothcnetsdk;

import com.ramble.springboothcnetsdk.support.HikvisionSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableOpenApi
@EnableWebMvc
@SpringBootApplication
public class SpringbootHcnetsdkApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootHcnetsdkApplication.class, args);

        //HikvisionSupport bean = run.getBean(HikvisionSupport.class);
    }

}
