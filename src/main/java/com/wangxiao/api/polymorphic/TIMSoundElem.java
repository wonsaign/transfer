package com.wangxiao.api.polymorphic;

import com.wangxiao.api.MsgContent;
import com.wangxiao.entity.RecordContent;

public class TIMSoundElem extends MsgContent{

	@Override
	public void setFields(MsgContent mc,RecordContent rc) {
		rc.setUuid(mc.getUUID());
		rc.setSize(mc.getSize().intValue());
		rc.setSecond(mc.getSecond().intValue());
	}
	@Override
	public void doFilterWords(MsgContent mc) {
		super.doFilterWords(mc);
	}
}
