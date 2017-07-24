package com.module.product.orm.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 密码
     */
    @JSONField(serialize = false)
    private  String password;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 头像URL
     */
    private String picture;

    /**
     * 0：男；1：女
     */
    private Integer sex;

    /**
     * 省份编码
     */
    private String province;

    /**
     * 城市编码
     */
    private String address;

    /**
     * 学历，1：硕士研究生及以上；2：本科；3：专科；4：高中及以下
     */
    private Integer education;

    /**
     * 职业
     */
    private String profession;

    /**
     * 	QQ号码
     */
    private String qq;

    /**
     * 最后一次付费时间
     */
    @JSONField(serialize = false)
    @Column(name = "last_pay_time")
    private Date lastPayTime;

    /**
     * 注册类型，0：Web端；1:安卓；2：IOS
     */
    @JSONField(serialize = false)
    @Column(name = "register_type")
    private Integer registerType;

    /**
     * 注册时间
     */
    @JSONField(serialize = false)
    @Column(name = "reg_time")
    private Date regTime;

    @Transient
    private Integer isActive;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取头像URL
     *
     * @return picture - 头像URL
     */
    public String getPicture() {
        return picture;
    }

    /**
     * 设置头像URL
     *
     * @param picture 头像URL
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    /**
     * 获取0：男；1：女
     *
     * @return sex - 0：男；1：女
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置0：男；1：女
     *
     * @param sex 0：男；1：女
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取省份编码
     *
     * @return province - 省份编码
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份编码
     *
     * @param province 省份编码
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取城市编码
     *
     * @return address - 城市编码
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置城市编码
     *
     * @param address 城市编码
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取学历，1：硕士研究生及以上；2：本科；3：专科；4：高中及以下
     *
     * @return education - 学历，1：硕士研究生及以上；2：本科；3：专科；4：高中及以下
     */
    public Integer getEducation() {
        return education;
    }

    /**
     * 设置学历，1：硕士研究生及以上；2：本科；3：专科；4：高中及以下
     *
     * @param education 学历，1：硕士研究生及以上；2：本科；3：专科；4：高中及以下
     */
    public void setEducation(Integer education) {
        this.education = education;
    }

    /**
     * 获取职业
     *
     * @return profession - 职业
     */
    public String getProfession() {
        return profession;
    }

    /**
     * 设置职业
     *
     * @param profession 职业
     */
    public void setProfession(String profession) {
        this.profession = profession;
    }

    /**
     * 获取	QQ号码
     *
     * @return qq - 	QQ号码
     */
    public String getQq() {
        return qq;
    }

    /**
     * 设置	QQ号码
     *
     * @param qq 	QQ号码
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * 获取最后一次付费时间
     *
     * @return last_pay_time - 最后一次付费时间
     */
    public Date getLastPayTime() {
        return lastPayTime;
    }

    /**
     * 设置最后一次付费时间
     *
     * @param lastPayTime 最后一次付费时间
     */
    public void setLastPayTime(Date lastPayTime) {
        this.lastPayTime = lastPayTime;
    }

    /**
     * 获取注册类型，0：Web端；1:安卓；2：IOS
     *
     * @return register_type - 注册类型，0：Web端；1:安卓；2：IOS
     */
    public Integer getRegisterType() {
        return registerType;
    }

    /**
     * 设置注册类型，0：Web端；1:安卓；2：IOS
     *
     * @param registerType 注册类型，0：Web端；1:安卓；2：IOS
     */
    public void setRegisterType(Integer registerType) {
        this.registerType = registerType;
    }

    /**
     * 获取注册时间
     *
     * @return reg_time - 注册时间
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * 设置注册时间
     *
     * @param regTime 注册时间
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }
}