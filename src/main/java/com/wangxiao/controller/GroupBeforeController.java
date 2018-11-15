package com.wangxiao.controller;

import static com.wangxiao.api.resbean.GroupRes.CODE_ALLOW;
import static com.wangxiao.api.resbean.GroupRes.CODE_DEFAULT_DROP;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

	
	@Autowired
	private MsgBodyService msgBodyService;
	
	@PostMapping("api")
	public GroupRes getMsg(HttpServletRequest request){
		try {
			List<MsgBody> dealed = new ArrayList<>();
			
			JSONObject json = JSONUtil.handleData(request);
			// TODO:根据请求头中的参数不同,选择不同的业务方法
			GroupReq gr = json.toJavaObject(GroupReq.class);
			// FIXME: 高并发的时候,需要重新考虑
			// step1 : 插入数据库聊天记录 ,原内容入库
			msgBodyService.saveBody(gr);
			// step2 : 脏字过滤并切换,只处理文本类型的内容
			// 使用多态代替switch case
			List<MsgBody> msgs = gr.getMsgBody();
			for (MsgBody msgBody : msgs) {
				MsgContent http_mc = (MsgContent) msgBody.getMsgContent();
				String clazzName = MsgContent.PACKAGE_NAME + msgBody.getMsgType(); 
				Class<?> clazz = Class.forName(clazzName);
				MsgContent msgContent = (MsgContent)clazz.newInstance();// this is init , must be null
				msgContent.doFilterWords(http_mc);
				
				dealed.add(msgBody);
			}
			// step3 : 返回正确结果
			return new GroupRes("OK", "success", CODE_ALLOW, dealed);
		} catch (Exception e) {
			e.printStackTrace();
			return new GroupRes("Fail", "fail", CODE_DEFAULT_DROP);
		}
	}
}
