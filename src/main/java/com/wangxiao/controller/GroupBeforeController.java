package com.wangxiao.controller;

import static com.wangxiao.api.resbean.GroupRes.CODE_ALLOW;
import static com.wangxiao.api.resbean.GroupRes.CODE_DEFAULT_DROP;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangxiao.api.MsgBody;
import com.wangxiao.api.MsgContent;
import com.wangxiao.api.reqbean.GroupReq;
import com.wangxiao.api.resbean.GroupRes;
import com.wangxiao.service.MsgBodyService;
import com.wangxiao.utils.JSONUtil;

@RestController
@RequestMapping("/")
public class GroupBeforeController {

	static Logger log = LoggerFactory.getLogger(GroupBeforeController.class);
	
	@Autowired
	private MsgBodyService msgBodyService;
	
	/**
	 * 
	 * step1 : 插入数据库聊天记录 ,原内容入库
	 * step2 : 脏字过滤并切换,只处理文本类型的内容
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("api")
	public GroupRes getMsg(HttpServletRequest request){
		try {
			List<MsgBody> dealedMsg = new ArrayList<>();
			
			JSONObject json = JSONUtil.handleData(request);
			// TODO:根据请求头中的参数不同,选择不同的业务方法
			GroupReq gr = json.toJavaObject(GroupReq.class);
			// FIXME: 高并发的时候,需要重新考虑
			msgBodyService.saveBody(gr);
			
			List<MsgBody> msgs = gr.getMsgBody();
			for (MsgBody msgBody : msgs) {
				MsgContent http_mc = (MsgContent) msgBody.getMsgContent();
				http_mc.getPolyContent(msgBody.getMsgType()).doFilterWords(http_mc);
				dealedMsg.add(msgBody);
			}
			return new GroupRes("OK", "success", CODE_ALLOW, dealedMsg);
		} catch (Exception e) {
			log.error("Exception:", e);
			return new GroupRes("Fail", "fail", CODE_DEFAULT_DROP);
		}
	}
}
