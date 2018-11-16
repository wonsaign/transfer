package com.wangxiao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import com.wangxiao.api.MsgBody;
import com.wangxiao.api.MsgContent;
import com.wangxiao.dao.RecordContentMapper;
import com.wangxiao.entity.RecordContent;
import com.wangxiao.service.MsgContentService;
import com.wangxiao.utils.IdGenerator;

@Service
public class MsgContentServiceImpl implements MsgContentService{
	
	@Autowired
	private RecordContentMapper msgContentMapper;
	
	@org.springframework.transaction.annotation.
	Transactional(propagation = Propagation.REQUIRED)
	@Override
	public void saveContent(List<MsgBody> msgBodies, Long pid) throws Exception{
		for (MsgBody msgBody : msgBodies) {
			String msgType = msgBody.getMsgType();
			MsgContent http_mc = (MsgContent) msgBody.getMsgContent();
			
			RecordContent rc = new RecordContent();
			rc.setPid(pid);
			rc.setId(IdGenerator.getInstance().nextId());
			rc.setMsgtype(msgType);
			// use polymorhpic replace 'switch ... case ...'
			http_mc.getPolyContent(msgType).setFields(http_mc, rc);
			
			msgContentMapper.insertSelective(rc);
		}
	}
}
