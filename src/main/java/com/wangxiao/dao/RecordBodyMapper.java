package com.wangxiao.dao;

import com.wangxiao.entity.RecordBody;

public interface RecordBodyMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RecordBody record);

    int insertSelective(RecordBody record);

    RecordBody selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecordBody record);

    int updateByPrimaryKey(RecordBody record);
}