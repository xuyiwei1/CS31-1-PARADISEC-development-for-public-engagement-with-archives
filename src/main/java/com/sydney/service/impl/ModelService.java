package com.sydney.service.impl;

import com.sydney.dao.ModelDao;
import com.sydney.pojo.Model;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ModelService {

    @Resource
    private ModelDao modelDao;

    public List<Model> getAll(){
        return modelDao.getAll();
    }

}
