package com.wangxiao.api.polymorphic;

import com.alibaba.fastjson.JSON;
import com.wangxiao.api.MsgContent;
import com.wangxiao.entity.RecordContent;

public class TIMImageElem extends MsgContent{

	@Override
	public void setFields(MsgContent mc,RecordContent rc) {
		rc.setUuid(mc.getUUID());
		rc.setImageformat(mc.getImageFormat().intValue());
		rc.setImageinfoarray(JSON.toJSONString(mc.getImageInfoArray()));
	}
	@Override
	public void doFilterWords(MsgContent mc) {
		super.doFilterWords(mc);
	}
}
