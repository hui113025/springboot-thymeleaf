package com.module.product;

import com.module.product.orm.mapper.StudentMapper;
import com.module.product.orm.model.Student;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * Created by zhenghui on 2017/7/21.
 */
public class TestExample extends ApplicationTest {

    @Resource
    private StudentMapper studentMapper;
    @Test
    public void testTkMapper(){
        Student student = studentMapper.selectByPrimaryKey(1);
        System.out.println(student.getNickName());
    }
}
