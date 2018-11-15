package com.wangxiao.api.polymorphic;

import com.wangxiao.api.MsgContent;
import com.wangxiao.entity.RecordContent;

public class TIMCustomElem extends MsgContent{

	@Override
	public void setFields(MsgContent mc,RecordContent rc) {
		rc.setData(mc.getData());
		rc.setDesc(mc.getData());
		rc.setExt(mc.getExt());
		rc.setSound(mc.getSound());
	}
	
	@Override
	public void doFilterWords(MsgContent mc) {
		super.doFilterWords(mc);
	}
}
