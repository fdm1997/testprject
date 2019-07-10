package com.fdm.testpoject.service;

import com.fdm.testpoject.mapper.TestMapper;
import com.fdm.testpoject.pojo.TestPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 付道明
 * @date 2019/7/8 17:25
 * @Description:
 */
@Service
public class TestService implements se{

    @Autowired
    private TestMapper testMapper;

    public void getall(){
        TestPojo testPojo = testMapper.getAll();
        System.out.println("- -------"+testPojo);
    }

}
