package com.module.product;

import com.alibaba.fastjson.JSONObject;
import com.module.product.common.shiro.ShiroUtils;
import com.module.product.orm.mapper.StudentMapper;
import com.module.product.orm.model.Student;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by zhenghui on 2017/7/21.
 */
public class TestExample extends ApplicationTest {
    private final static Logger logger = LoggerFactory.getLogger(ShiroUtils.class);
    @Resource
    private StudentMapper studentMapper;

    @Test
    public void testTkMapper() {
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
        logger.info("testMock返回json{} ", responseString);
    }

    //@ResponseBody标识的参数
    @Test
    public void testMockByResponseBodyParam() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "zhangsan");
        jsonObject.put("age", "40");
        String requestJson = jsonObject.toJSONString();
        String responseString =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/testMockByResponseBodyParam")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .content(requestJson)
                )
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();
        logger.info("testMockByResponseBodyParam返回json{} ", responseString);
    }

    //@ResponseBody标识的参数
    @Test
    public void testMockForRequestParam() throws Exception {
        String responseString =
                mockMvc.perform(
                        MockMvcRequestBuilders.post("/testMockForRequestParam")
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .param("name", "zhangsan")
                )
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();
        logger.info("testMockForRequestParam返回json{} ", responseString);
    }
}
