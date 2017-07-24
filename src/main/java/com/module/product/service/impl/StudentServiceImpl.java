package com.module.product.service.impl;

import com.module.core.service.AbstractService;
import com.module.product.orm.mapper.StudentMapper;
import com.module.product.orm.model.Student;
import com.module.product.service.StudentService;
import com.module.core.exception.ServiceException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;

@Service
@Transactional
public class StudentServiceImpl extends AbstractService<Student> implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    @Override
    public Student findByMobile(String mobile) {
        Student condition = new Student();
        condition.setMobile(mobile);
        Student student = studentMapper.selectOne(condition);
        if(student == null){
            throw new ServiceException("该用户不存在");
        }
        return student;
    }

    @Override
    public boolean validatePwdIsOk(Student student, String password) {
        return StringUtils.equals(DigestUtils.md5Hex(password), student.getPassword());
    }

    @Override
    public boolean nickNameIsExist(String nickName) {
        Condition condition = new Condition(Student.class);
        condition.createCriteria().andEqualTo("nickName", nickName);
        return !studentMapper.selectByCondition(condition).isEmpty();
    }

    @Override
    public boolean mobileIsExist(String mobile) {
        Condition condition = new Condition(Student.class);
        condition.createCriteria().andEqualTo("mobile", mobile);
        return !studentMapper.selectByCondition(condition).isEmpty();
    }

    @Override
    public void modifyPwd(Student student, String password, String oldPassword) {
        boolean pass = validatePwdIsOk(student, oldPassword);
        if (pass) {
            student.setPassword(DigestUtils.md5Hex(password));
            update(student);
        } else {
            throw new ServiceException("密码错误");
        }
    }

    @Override
    public Student detail(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }

}
