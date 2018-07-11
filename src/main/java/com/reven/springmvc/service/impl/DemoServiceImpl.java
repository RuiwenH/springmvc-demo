package com.reven.springmvc.service.impl;

import com.reven.springmvc.mapper.DemoMapper;
import com.reven.springmvc.pojo.Demo;
import com.reven.springmvc.service.DemoService;
import com.reven.springmvc.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/11.
 */
@Service
@Transactional
public class DemoServiceImpl extends AbstractService<Demo> implements DemoService {
    @Resource
    private DemoMapper tDemoMapper;

}
