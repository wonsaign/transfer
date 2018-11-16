package com.wangxiao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.wangxiao.api.MsgBody;
import com.wangxiao.api.reqbean.GroupReq;
import com.wangxiao.dao.RecordBodyMapper;
import com.wangxiao.entity.RecordBody;
import com.wangxiao.service.MsgBodyService;
import com.wangxiao.service.MsgContentService;
import com.wangxiao.utils.IdGenerator;

@Service
public class MsgBodyServiceImpl implements MsgBodyService{

	@Autowired
	private RecordBodyMapper msgBodyMapper;
	
	@Autowired
	private MsgContentService msgContentService;
	
	@Override
	@org.springframework.transaction.annotation.
	Transactional(propagation = Propagation.REQUIRED)
	public void saveBody(GroupReq gr) throws Exception {
		// FIXME: 使用BeanDup 方法
		RecordBody record = new RecordBody();
		Long pk_id = IdGenerator.getInstance().nextId();
		record.setId(pk_id);
		record.setCallbackcommand(gr.getCallBackCommand());
		record.setFromAccount(gr.getFrom_Account());
		record.setGroupid(gr.getGroupId());
		record.setOperatorAccount(gr.getOperator_Account());
		record.setRandom(gr.getRandom());
		record.setType(gr.getType());
		record.setCreatetime(new Date());
		List<MsgBody> msgBodies = gr.getMsgBody();
		msgContentService.saveContent(msgBodies, pk_id);
		msgBodyMapper.insertSelective(record);
	}
}
