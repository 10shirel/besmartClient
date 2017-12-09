package com.besmart;

import com.besmart.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ClientApplication.class, args);
        System.out.println(Constants.CLIENT_WAS_STARTED_IN_SUCCESSFULLY + applicationContext.toString());
    }
}
