package com.module.product.web;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.github.kevinsawicki.http.HttpRequest;
import com.module.product.orm.mapper.StudentMapper;
import com.module.product.orm.model.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by admin on 2017/6/15.
 * jsonp 解决js跨域问题
 */
@RestController
public class GeneralController {
    @Resource
    private StudentMapper studentMapper;

//    @GetMapping(value = "/jsonp/get/info")
    @RequestMapping(value = "/jsonp/get/info",method = { RequestMethod.GET })
    @ResponseBody
    public Object testJsonp(String callback, HttpServletRequest request)  throws IOException {
        String domain = request.getScheme()+"://"+ request.getServerName()+":"+request.getServerPort();
        String url = domain + "/jsonp/redirect/view";
        String result = HttpRequest.get(url)
                .contentType("application/json")
                .body();
        Student student = studentMapper.selectByPrimaryKey(1);
        JSONPObject jsonpObject = new JSONPObject(callback,student);
        return jsonpObject.getValue();
    }
}
