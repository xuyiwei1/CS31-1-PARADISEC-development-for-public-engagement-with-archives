package com.sydney.dao;

import com.sydney.pojo.Model;
import com.sydney.pojo.ModelTransfer;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ModelDao extends Mapper<ModelTransfer> {

//    @Select("select * from ModelPerformance")
    List<Model> getAll();
}
