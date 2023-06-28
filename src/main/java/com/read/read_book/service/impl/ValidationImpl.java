package com.read.read_book.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.Mapper.ValidationMapper;
import com.read.read_book.pojo.User;
import com.read.read_book.pojo.Validation;
import com.read.read_book.service.IUserService;
import com.read.read_book.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ValidationImpl extends ServiceImpl <ValidationMapper, Validation> implements ValidationService {
    @Autowired
    ValidationMapper validationMapper;

    @Transactional
    @Override
    public Integer saveCode(String email, String code, Integer type,  Date offsetMillisecond) {
            Validation validation=new Validation();
            validation.setCode(code);
            validation.setEmail(email);
            validation.setTime(offsetMillisecond);
            validation.setType(type);
        // 删除同类型的验证
        UpdateWrapper<Validation> validationUpdateWrapper = new UpdateWrapper<>();
        validationUpdateWrapper.eq("email", email);
        validationUpdateWrapper.eq("type", type);
        validationMapper.delete(validationUpdateWrapper);
       return validationMapper.insert(validation);

    }
}
