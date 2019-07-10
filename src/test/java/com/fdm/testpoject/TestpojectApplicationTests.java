package com.fdm.testpoject;

import com.fdm.testpoject.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestpojectApplicationTests {

    @Autowired
    TestService testService;

    @Test
    public void contextLoads() {
        testService.getall();
    }

}
