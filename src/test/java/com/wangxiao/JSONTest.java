package com.wangxiao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSON;
import com.wangxiao.utils.IdGenerator;

@RunWith(SpringRunner.class)
@SpringBootTest
@SpringJUnitConfig(value=TransferApplication.class)
@WebAppConfiguration
public class JSONTest {
	
	@Test
	public void 测试() {
		List<String> ids = new ArrayList<>();
		ids.add("18091112");
		ids.add("18091112");
		ids.add("18091112");
		ids.add("18091112");
		System.out.println(JSON.toJSONString(ids));
		
	}
	
	@Test
	public void 主键生成器() {
		try {
			for (int i = 0; i < 30; i++) {
				System.out.println(IdGenerator.getInstance().nextId());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
