package com.module.product.web;

import com.module.product.service.StudentService;
import com.module.product.common.Constants;
import com.module.product.common.shiro.ShiroUtils;
import com.google.common.collect.Maps;
import com.module.product.orm.model.Student;
import com.module.core.common.Result;
import com.module.core.common.ResultGenerator;
import com.module.core.common.ResultState;
import com.module.product.service.CaptchaService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * Created by CodeGenerator on 2017/06/05.
 */
@RestController
@RequestMapping("/user")
public class StudentController {
    @Resource
    private CaptchaService captchaService;
    @Resource
    private StudentService studentService;

    @PostMapping("/login/auth")
    public Result loginAuth(String mobile, String password) {

        Result result = ResultGenerator.genFailResult("");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(mobile, DigestUtils.md5Hex(password));
        try {
            subject.login(token);
            result.setState(ResultState.SUCCESS);
            Student user = ShiroUtils.getSessionUser();
            result = ResultGenerator.genSuccessResult(user);
        } catch (IncorrectCredentialsException e) {
            result =  ResultGenerator.genFailResult("账号或密码错误");
        } catch (AuthenticationException e) {
            result = ResultGenerator.genFailResult("登录失败，请稍后再试");
        }
        return result;
    }

    @PostMapping("/register")
    public Result register(String mobile, String password, String captcha) {
        //检查短信验证码
//        captchaService.check(mobile, captcha);
        //注册
        Student student = new Student();
        student.setMobile(mobile);
        student.setPassword(DigestUtils.md5Hex(password));
        student.setRegisterType(0);
        student.setRegTime(new Date());
        student.setSex(2);
        studentService.save(student);
        //注册完成后直接登录
        return loginAuth(mobile, password);
    }

    @PostMapping("/password/modify")
    public Result passwordModify(Integer userId, String password, String oldPassword) {
        Student student = studentService.findById(userId);
        if (student == null) {
            return ResultGenerator.genFailResult("该用户不存在");
        }
        studentService.modifyPwd(student, password, oldPassword);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/password/rest")
    public Result passwordModify(String mobile, String password) {
        boolean pass = captchaService.checkFindPasswordToken(mobile);
        if (pass) {
            Student student = studentService.findByMobile(mobile);
            student.setPassword(DigestUtils.md5Hex(password));
            studentService.update(student);
            //重置密码后直接登录
            return loginAuth(mobile, password);
        } else {
            return ResultGenerator.genFailResult("非法请求");
        }
    }


    @PostMapping("/captcha/sms")
    public Result sendSmsCaptcha(HttpSession session, String mobile, String imageCaptcha, int type) {
        captchaService.checkImageCaptcha(session, imageCaptcha);
        String captcha = captchaService.send(mobile, type);
        captchaService.cleanImageCaptcha(session);
        Map<String, Object> data = Maps.newLinkedHashMap();
        data.put("captcha", captcha);
        return ResultGenerator.genSuccessResult(data);
    }

    @PostMapping("/validate/captcha")
    public Result smsValidate(String mobile, String captcha) {
        captchaService.check(mobile, captcha);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/validate/password")
    public Result validatePwd(Integer userId, String password) {
        Student student = studentService.findById(userId);
        if (student == null) {
            return ResultGenerator.genFailResult("该用户不存在");
        }
        Map<String, Object> data = Maps.newLinkedHashMap();
        data.put("yes", studentService.validatePwdIsOk(student, password));
        return ResultGenerator.genSuccessResult(data);
    }

    @PostMapping("/validate/exists/nickname")
    public Result nickNameIsExist(String nickName) {
        Map<String, Object> data = Maps.newLinkedHashMap();
        data.put("exists", studentService.nickNameIsExist(nickName));
        return ResultGenerator.genSuccessResult(data);
    }


    @PostMapping("/validate/exists/mobile")
    public Result mobileIsExist(String mobile) {
        Map<String, Object> data = Maps.newLinkedHashMap();
        data.put("exists", studentService.mobileIsExist(mobile));
        return ResultGenerator.genSuccessResult(data);
    }
    @PostMapping("/detail")
    public Result detail(Integer id) {
        Integer userId = ShiroUtils.getSessionUser().getId();
        return ResultGenerator.genSuccessResult(studentService.detail(userId));
    }
    @PostMapping("/modify")
    public Result modify(Student userStu, String province, String address, String education) {
        Integer user = ShiroUtils.getSessionUser().getId();
        Student stu = studentService.findById(user);
        if (!StringUtils.isEmpty(province)){
            stu.setProvince(province);
        }
        if (!StringUtils.isEmpty(address)){
            stu.setAddress(address);
        }
        if (!StringUtils.isEmpty(education)){
            stu.setEducation(Integer.valueOf(education));
        }

        stu.setId(user);
        stu.setNickName(userStu.getNickName());
        stu.setSex(userStu.getSex());
        stu.setPicture(userStu.getPicture());
        stu.setProfession(userStu.getProfession());
        stu.setQq(userStu.getQq());
        studentService.update(stu);

        String nickName = stu.getMobile().substring(0, 3) + "****" + stu.getMobile().substring(7);
        nickName = StringUtil.isNotEmpty(stu.getNickName()) ? stu.getNickName() : nickName;
        stu.setNickName(nickName);
        ShiroUtils.getSession().setAttribute(Constants.SESSION_USER_KEY,stu);
        return ResultGenerator.genSuccessResult(stu);
    }

}
