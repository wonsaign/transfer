package com.wangxiao.entity;

public class DirtyWords {
    private Integer id;

    private String dirtyWords;

    private Integer sortIndex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDirtyWords() {
        return dirtyWords;
    }

    public void setDirtyWords(String dirtyWords) {
        this.dirtyWords = dirtyWords == null ? null : dirtyWords.trim();
    }

    public Integer getSortIndex() {
        return sortIndex;
    }

    public void setSortIndex(Integer sortIndex) {
        this.sortIndex = sortIndex;
    }
}