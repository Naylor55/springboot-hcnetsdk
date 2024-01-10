package com.ramble.springboothcnetsdk;

import com.ramble.springboothcnetsdk.support.HikvisionSupport;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringbootHcnetsdkApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(SpringbootHcnetsdkApplication.class, args);

        //HikvisionSupport bean = run.getBean(HikvisionSupport.class);
    }

}
