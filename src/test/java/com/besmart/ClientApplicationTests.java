package com.besmart;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClientApplicationTests {
    @Autowired
    private Injector injector;

    @Test
    public void contextLoads() {
    }


    public Injector getInjector() {
        return injector;
    }
}
