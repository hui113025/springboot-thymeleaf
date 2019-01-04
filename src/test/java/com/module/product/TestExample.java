package com.module.product;

import com.module.product.orm.mapper.StudentMapper;
import com.module.product.orm.model.Student;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testMock() throws Exception {
        String responseString =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/testMock")
                                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();
        System.out.println("-----返回的json = " + responseString);
    }
}
