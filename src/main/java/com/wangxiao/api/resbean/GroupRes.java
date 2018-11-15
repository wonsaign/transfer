package com.wangxiao.api.resbean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wangxiao.api.MsgBody;
/**
 * time: 群内发言回调前response
 * 
 * @author wangs
 *
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL) // null属性,不显示
public class GroupRes {
	public final static int CODE_ALLOW = 0;
	public final static int CODE_FORBDEN = 1;
	public final static int CODE_DEFAULT_DROP = 2;
	// private final static int CODE_ALLOW = 0;
	@JsonProperty(value="ActionStatus")
	private String actionStatus;// 状态
	@JsonProperty(value="ErrorInfo")
	private String errorInfo;// 信息
	@JsonProperty(value="ErrorCode")
	private Integer errorCode;// 状态码
	@JsonProperty(value="MsgBody")
	private List<MsgBody> msgBody;
	
	public GroupRes(String actionStatus, String errorInfo, Integer errorCode) {
		super();
		this.actionStatus = actionStatus;
		this.errorInfo = errorInfo;
		this.errorCode = errorCode;
	}
	
	public GroupRes(String actionStatus, String errorInfo, Integer errorCode, List<MsgBody> msgBody) {
		super();
		this.actionStatus = actionStatus;
		this.errorInfo = errorInfo;
		this.errorCode = errorCode;
		this.msgBody = msgBody;
	}
	public String getActionStatus() {
		return actionStatus;
	}
	public void setActionStatus(String actionStatus) {
		this.actionStatus = actionStatus;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public List<MsgBody> getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(List<MsgBody> msgBody) {
		this.msgBody = msgBody;
	}
}
