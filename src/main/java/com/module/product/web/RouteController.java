package com.module.product.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.common.collect.Lists;
import com.module.product.orm.model.Student;
import com.module.product.service.CaptchaService;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 路由控制器，所有页面的路由写在该类下，该控制器只允许返回View，且HttpRequest方法必须为Get。
 */
@Controller
public class RouteController {

    @Resource
    private DefaultKaptcha captchaProducer;
    @Resource
    private CaptchaService captchaService;

    @GetMapping("/")
    public ModelAndView home(ModelAndView mv) {
        mv.setViewName("index");
        return  mv;
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        SavedRequest savedRequest = WebUtils.getSavedRequest(request);
        String viewName = "redirect:/";
        if (savedRequest != null) {
            String url = savedRequest.getRequestUrl();
            if (!"/logout".equals(url))
                viewName = "redirect:/?login=" + url;
        }
        return viewName;
    }

    @GetMapping("/captcha/image")
    void imageCaptcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = captchaProducer.createText();
        captchaService.setImageCaptcha(request.getSession(), capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping(value = "/jsonp/redirect/view")
    public String getJsonpBody() {
        return "jsonp-redirect";
    }

    @GetMapping("/thymeleaf/sample")
    public ModelAndView thymeleaf(ModelAndView mv) {

        Student student = new Student();
        student.setId(1);
        student.setNickName("小王");
        student.setRegTime(new Date());

        List list = Lists.newArrayList();
        list.add(student);
        mv.addObject("stus",list);
        mv.addObject("user",student);
        mv.setViewName("thymeleaf-sample");
        return  mv;
    }
}
