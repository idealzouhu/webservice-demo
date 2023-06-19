package com.igeekhome.biz.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.igeekhome.biz.AdministratorService;
import com.igeekhome.dao.AdministratorMapper;
import com.igeekhome.pojo.Administrator;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl extends ServiceImpl<AdministratorMapper, Administrator> implements AdministratorService {
}
