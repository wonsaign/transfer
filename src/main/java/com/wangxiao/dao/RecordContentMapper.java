package com.wangxiao.dao;

import com.wangxiao.entity.RecordContent;

public interface RecordContentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordContent record);

    int insertSelective(RecordContent record);

    RecordContent selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordContent record);

    int updateByPrimaryKey(RecordContent record);
}