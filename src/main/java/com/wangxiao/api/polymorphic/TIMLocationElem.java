package com.wangxiao.api.polymorphic;

import com.wangxiao.api.MsgContent;
import com.wangxiao.entity.RecordContent;

public class TIMLocationElem extends MsgContent{

	@Override
	public void setFields(MsgContent mc,RecordContent rc) {
		rc.setDesc(mc.getData());
		// TODO: 修改为double
		rc.setLatitude(mc.getLatitude().doubleValue());
		rc.setLongitude(mc.getLongitude().doubleValue());
	}
	@Override
	public void doFilterWords(MsgContent mc) {
		super.doFilterWords(mc);
	}
}
