package com.wangxiao.service;

import java.util.List;

import com.wangxiao.api.MsgBody;

public interface MsgContentService {

	void saveContent(List<MsgBody> msgBodies, Long pid) throws Exception;

}
