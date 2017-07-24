package com.module.product.service;


import com.module.product.orm.model.Student;
import com.module.core.service.Service;

/**
 * Created by CodeGenerator on 2017/06/05.
 */
public interface StudentService extends Service<Student> {

    Student findByMobile(String mobile);

    boolean validatePwdIsOk(Student student, String password);

    boolean nickNameIsExist(String nickName);

    boolean mobileIsExist(String mobile);

    void modifyPwd(Student student, String password, String oldPassword);

    Student detail(Integer id);
}
