package com.wangxiao.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = JsonInclude.Include.NON_NULL) // null属性,不显示
public class MsgBody {
	
	@JsonProperty(value="MsgType")
	private String msgType;
	@JsonProperty(value="MsgContent")
	private MsgContent msgContent;
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public Object getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(MsgContent msgContent) {
		this.msgContent = msgContent;
	}
}
