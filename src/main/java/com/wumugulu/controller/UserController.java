package com.wumugulu.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wumugulu.entity.User;

@Controller
@RequestMapping("/users")
public class UserController {
	
	private Map<Long, User> mapDB = new HashMap<>();

	// 构造方法，在controller对象初始化的时候向mapDB中添加3个User对象用于测试
	// 有意没有设置user1.sex和user2.address，也就是null值
	public UserController() {
		User user1 = new User();
		user1.setId(1L);
		user1.setUsername("admin");
		user1.setPassword("123456");
//		user1.setSex(8);
		user1.setAddress("北京天坛");
		user1.setBirthday(new Date());

		User user2 = new User();
		user2.setId(2L);
		user2.setUsername("operator");
		user2.setPassword("xxyyzz");
		user2.setSex(6);
//		user2.setAddress("上海夫子庙");
		user2.setBirthday(new Date());

		User user3 = new User();
		user3.setId(3L);
		user3.setUsername("boss");
		user3.setPassword("888888");
		user3.setSex(9);
		user3.setAddress("深圳华强北");
		user3.setBirthday(new Date());
		
		mapDB.put(user1.getId(), user1);
		mapDB.put(user2.getId(), user2);
		mapDB.put(user3.getId(), user3);
	}

	// 获取所有User
	@GetMapping(value="")
	@ResponseBody
	public Collection<User> findAll(){
		Collection<User> usersAll = mapDB.values(); 
		return usersAll;
	}

	// 获取单个User
	@GetMapping(value="/{id}")
	@ResponseBody
	public User findOne(@PathVariable Long id){
		System.out.println("findOne id=" + id);
		return mapDB.get(id);
	}

	// 新增User
	@PostMapping(value="")
	@ResponseBody
	public String add(@RequestBody User user){
		System.out.println("add user=" + user.toString());
		mapDB.put(user.getId(), user);
		return "success";
	}

}
