package com.wangxiao;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.wangxiao.dao.DirtyWordsMapper;
import com.wangxiao.entity.DirtyWords;
import com.wangxiao.utils.RegularUtil;
import com.wangxiao.utils.SensitivewordFilter;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringJUnitConfig(value=TransferApplication.class)
@WebAppConfiguration
public class DirtyTest {

	static Logger log = LoggerFactory.getLogger(DirtyTest.class);
	
	@Autowired
	private DirtyWordsMapper dirtyWordsMapper;
	
	@Test
	public void 脏字测试() {
		try {
			String text = "13087654321太多的伤感情怀也许只局限于饲养基地 荧幕中的情节,大爷,毛泽东，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"  
					+ "然后法.轮.功 我们的扮演的角色就是fuck跟随着主人公的喜红客联盟 ,草你妈怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"  
					+ "难过就躺在1261897660某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三.级.片 深人静的晚上，关上电话静静的发呆着。";
			Set<String> words = SensitivewordFilter.getSensitiveWord(text, 1);
			for (String string : words) {
				//System.out.println(string);
				log.info(string);
			}
			String result = SensitivewordFilter.replaceSensitiveWord(text, 1, "*");
			result = RegularUtil.replacePhone(result);
			result = RegularUtil.replaceQQ(result);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void 脏字测试1() {
		try {
			String text = "{\"level\":3,\"content\":\"欢迎ztk_95988429进入直播间\",\"name\":\"ztk_95988429\"}";
			Set<String> words = SensitivewordFilter.getSensitiveWord(text, 1);
			for (String string : words) {
				log.info(string);
			}
			String result = SensitivewordFilter.replaceSensitiveWord(text, 1, "*");
			result = RegularUtil.replacePhone(result);
			result = RegularUtil.replaceQQ(result);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void 脏字查询() {
		List<DirtyWords> ds = dirtyWordsMapper.findAll();
		for (DirtyWords dirtyWords : ds) {
			System.out.println(JSON.toJSONString(dirtyWords));
		}
	}


}
