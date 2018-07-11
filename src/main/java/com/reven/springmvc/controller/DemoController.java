package com.reven.springmvc.controller;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.reven.springmvc.core.ApiResult;
import com.reven.springmvc.core.ResultGenerator;
import com.reven.springmvc.pojo.Demo;
import com.reven.springmvc.service.DemoService;

/**
* Created by CodeGenerator on 2018/07/11.
*/
@RestController
@RequestMapping("/demo")
public class DemoController {
    @Resource
    private DemoService demoService;

    @PostMapping("/add")
    public ApiResult add(Demo demo) {
        demoService.save(demo);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public ApiResult delete(@RequestParam Integer id) {
        demoService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public ApiResult update(Demo demo) {
        demoService.update(demo);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public ApiResult detail(@RequestParam Integer id) {
        Demo demo = demoService.findById(id);
        return ResultGenerator.genSuccessResult(demo);
    }

    @GetMapping("/list")
    public ApiResult list(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer size) {
        PageHelper.startPage(page, size);
        List<Demo> list = demoService.findAll();
        PageInfo<Demo> pageInfo = new PageInfo<Demo>(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
