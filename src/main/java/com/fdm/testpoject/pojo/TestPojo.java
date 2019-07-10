package com.fdm.testpoject.pojo;

import java.util.Date;

/**
 * @author 付道明
 * @date 2019/7/8 17:17
 * @Description:
 */
public class TestPojo {

    private Integer id;
    private String name;
    private Integer age;
    private Integer ifDelete;
    private Date createDate;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public Integer getIfDelete() {
        return ifDelete;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setIfDelete(Integer ifDelete) {
        this.ifDelete = ifDelete;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public TestPojo(Integer id, String name, Integer age, Integer ifDelete, Date createDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.ifDelete = ifDelete;
        this.createDate = createDate;
    }

    public TestPojo() {
    }

    @Override
    public String toString() {
        return "TestPojo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", ifDelete=" + ifDelete +
                ", createDate=" + createDate +
                '}';
    }
}
