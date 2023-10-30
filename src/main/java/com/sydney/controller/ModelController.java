package com.sydney.controller;

import com.sydney.common.Output;
import com.sydney.pojo.Model;
import com.sydney.service.impl.ModelService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@CrossOrigin
@RestController
@RequestMapping("/details")
public class ModelController {

    @Resource
    private ModelService modelService;

    @GetMapping
    public Output getAll(){
        List<Model> list = modelService.getAll();
//        System.out.println(list.get(0).getInsertions());
        return Output.success(list);
    }
}
