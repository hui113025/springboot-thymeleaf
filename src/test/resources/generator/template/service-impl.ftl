package com.ruhang.hf.service.impl;

import com.ruhang.hf.orm.mapper.${modelNameUpperCamel}Mapper;
import com.ruhang.hf.orm.model.${modelNameUpperCamel};
import com.ruhang.hf.service.${modelNameUpperCamel}Service;
import com.ruhang.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by ${author} on ${date}.
 */
@Service
@Transactional
public class ${modelNameUpperCamel}ServiceImpl extends AbstractService<${modelNameUpperCamel}> implements ${modelNameUpperCamel}Service {
    @Resource
    private ${modelNameUpperCamel}Mapper ${modelNameLowerCamel}Mapper;

}
