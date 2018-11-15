package com.wangxiao.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.wangxiao.entity.DirtyWords;

public interface DirtyWordsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DirtyWords record);

    int insertSelective(DirtyWords record);
    
    @Select(value="select id,dirty_words as dirtyWords,sort_index as sortIndex from dic_dirtywords;")
    List<DirtyWords> findAll();

    DirtyWords selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DirtyWords record);

    int updateByPrimaryKey(DirtyWords record);
}