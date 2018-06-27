package com.inspur.docker;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class Hello {
	@Autowired
	private RedisService redisService;

	@RequestMapping("/")
	String home(HttpServletRequest request, HttpSession session) {
		StringBuffer buf = printInfo(request, session);
		buf.append("<br>\\n你可以访问啦!");
		return buf.toString();
	}

	private StringBuffer printInfo(HttpServletRequest request, HttpSession session) {
		Object hello = session.getAttribute("hello");
		StringBuffer buf = new StringBuffer();
		buf.append("<br>\\nredis的时间：");
		buf.append(redisService.get("abc"));
		buf.append("<br>\\nsession id：");
		buf.append(session.getId());
		buf.append("<br>\\nsession中的hello：");
		buf.append(hello);
		buf.append("<br>\\nserver name:");
		buf.append(request.getServerName());
		buf.append("<br>\\nserver info:");
		buf.append(request.getServletContext().getServerInfo());
		buf.append("<br>\\nhost ip:");
		try {
			buf.append(InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
		}
		System.out.println(buf.toString());
		return buf;
	}

	@RequestMapping("/hello")
	String hello(HttpServletRequest request,HttpSession session) {
		redisService.put("abc", new Date());
		StringBuffer buf = printInfo(request, session);
		buf.append("<br>\n你好，请说中文!");
		return buf.toString();
	}

	@RequestMapping("/session")
	String session(String hello,HttpServletRequest request,HttpSession session) {
		redisService.put("abc", new Date());
		session.setAttribute("hello", hello);
		StringBuffer buf = printInfo(request, session);
		buf.append("<br>\n您已经设置hello!");
		return buf.toString();
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(Hello.class, args);
	}
}
