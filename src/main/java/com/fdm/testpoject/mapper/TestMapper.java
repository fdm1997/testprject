package com.fdm.testpoject.mapper;

import com.fdm.testpoject.pojo.TestPojo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author 付道明
 * @date 2019/7/8 17:16
 * @Description:
 */
@Mapper
public interface TestMapper {

    @Select({"select * from TEST"})
    TestPojo getAll();
}
