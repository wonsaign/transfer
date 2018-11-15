package com.wangxiao.api.polymorphic;

import com.wangxiao.api.MsgContent;
import com.wangxiao.entity.RecordContent;
import com.wangxiao.utils.SensitivewordFilter;

public class TIMTextElem extends MsgContent{
	
	@Override
	public void setFields(MsgContent mc,RecordContent rc) {
		rc.setText(mc.getText());
	}
	
	@Override
	public void doFilterWords(MsgContent mc) {
		mc.setText(SensitivewordFilter.replaceSensitiveWord(mc.getText(), 1, "*"));
	}
}
