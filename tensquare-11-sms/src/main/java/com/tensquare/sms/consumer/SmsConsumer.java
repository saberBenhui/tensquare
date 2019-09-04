package com.tensquare.sms.consumer;

import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.util.SmsUtil;

@Component
@RabbitListener(queues="tensquare")
public class SmsConsumer {
	
	@Autowired
	private SmsUtil smsUtil;
	
	@Value("${aliyun.sms.template_code}")
	private String template_code;
	
	@Value("${aliyun.sms.sign_name}")
	private String sign_name;
	
	
	@RabbitHandler
	public void sms(Map<String, String> msg) {
		//发送短信
		System.out.println("手机号码："+msg.get("mobile"));
		System.out.println("手机验证码："+msg.get("num"));
		
		//调用阿里大于短信发送短信
		try {
			smsUtil.sendSms(msg.get("mobile"), template_code, sign_name, "{\"code\":\""+msg.get("num")+"\"}");
		} catch (ClientException e) {
			System.out.println("短信发送异常");
			e.printStackTrace();
		}
		
	}

}
