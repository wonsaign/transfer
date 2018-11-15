package com.wangxiao.api.polymorphic;

import com.wangxiao.api.MsgContent;
import com.wangxiao.entity.RecordContent;

public class TIMFaceElem extends MsgContent{
	
	@Override
	public void setFields(MsgContent mc,RecordContent rc) {
		rc.setIndex(mc.getIndex().intValue());
		rc.setData(mc.getData());
	}
	@Override
	public void doFilterWords(MsgContent mc) {
		super.doFilterWords(mc);
	}
}
