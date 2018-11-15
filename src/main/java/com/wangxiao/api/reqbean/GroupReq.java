package com.wangxiao.api.reqbean;

import java.util.List;

import com.wangxiao.api.MsgBody;
/**
 * time: 群内发言回调前request
 * 
 * @author wangs
 *
 */
public class GroupReq {
	private String callBackCommand;
	private String groupId;
	private String type;
	private String from_Account;
	private String operator_Account;
	private Integer random;
	private List<MsgBody> msgBody;
	public String getCallBackCommand() {
		return callBackCommand;
	}
	public void setCallBackCommand(String callBackCommand) {
		this.callBackCommand = callBackCommand;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getFrom_Account() {
		return from_Account;
	}
	public void setFrom_Account(String from_Account) {
		this.from_Account = from_Account;
	}
	public String getOperator_Account() {
		return operator_Account;
	}
	public void setOperator_Account(String operator_Account) {
		this.operator_Account = operator_Account;
	}
	public Integer getRandom() {
		return random;
	}
	public void setRandom(Integer random) {
		this.random = random;
	}
	public List<MsgBody> getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(List<MsgBody> msgBody) {
		this.msgBody = msgBody;
	}

}
