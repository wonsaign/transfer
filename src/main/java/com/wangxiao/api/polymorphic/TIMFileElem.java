package com.wangxiao.api.polymorphic;

import com.wangxiao.api.MsgContent;
import com.wangxiao.entity.RecordContent;

public class TIMFileElem extends MsgContent{

	@Override
	public void setFields(MsgContent mc,RecordContent rc) {
		rc.setUuid(mc.getUUID());
		rc.setFilesize(mc.getFileSize().intValue());
		rc.setFilename(mc.getFileName());
	}
	@Override
	public void doFilterWords(MsgContent mc) {
		super.doFilterWords(mc);
	}
}
