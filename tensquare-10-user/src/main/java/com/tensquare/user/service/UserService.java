package com.tensquare.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.apache.commons.lang.NumberUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;

/**
 * user服务层
 * 
 * @author Administrator
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Autowired
	private RabbitMessagingTemplate mqTemplate;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user) {
		//根据手机号查询用户
		User dbUser = userDao.findByMobile(user.getMobile());
		//如果用户不为空而且密码匹配正确就登录成功
		if(dbUser!=null && encoder.matches(user.getPassword(), dbUser.getPassword())) {
			return dbUser;
		}
		//其他的都是登录失败
		return null;
	}
	


	/**
	 * 用户注册
	 * @param code
	 * @param user
	 */
	public void register(String code, User user) {
		if(code==null||"".equals(code)) {
			throw new RuntimeException("验证码不能为空");
		}
		
		if(user==null || user.getMobile()==null) {
			throw new RuntimeException("手机号不能为空");
		}
		
		//判断验证码是否一致
		String flag = (String) redisTemplate.opsForValue().get("sendsms_"+user.getMobile()+"_"+code);
		if(flag==null || !"1".equals(flag)) {
			throw new RuntimeException("手机验证码错误，请重新获取短信验证码！");
		}
		
		//可以注册
		user.setId(idWorker.nextId()+"");
		//对密码加密
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRegdate(new Date());
		user.setOnline(0L);
		user.setFanscount(0);
		user.setFollowcount(0);
		userDao.save(user);
		
		
	}
	
	
	/**
	 * 发送短信
	 * @param mobile
	 */
	public void sendsms(String mobile) {
		//判断手机号是否为空
		if(mobile==null||"".equals(mobile)) {
			throw new RuntimeException("用户手机号码不能为空！");
		}
		//生成随机数的手机验证码
		String num = RandomStringUtils.randomNumeric(4);
		System.out.println("生成的手机验证码："+num);
		
		//往redis发送一个标记
		redisTemplate.opsForValue().set("sendsms_"+mobile+"_"+num, "1", 1, TimeUnit.MINUTES);
		
		//往RabbitMQ发送一个发短信的消息
		Map<String, String> msg = new HashMap<String, String>();
		msg.put("mobile",mobile);
		msg.put("num",num);
		mqTemplate.convertAndSend("tensquare",msg);
	}
	
	
	
	

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );
		//对密码加密
		if(user.getPassword()!=null) {
			user.setPassword(encoder.encode(user.getPassword()));
		}
		//初始化值
		user.setRegdate(new Date());
		user.setOnline(0L);
		user.setFanscount(0);
		user.setFollowcount(0);
		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // loginname
                if (searchMap.get("loginname")!=null && !"".equals(searchMap.get("loginname"))) {
                	predicateList.add(cb.like(root.get("loginname").as(String.class), "%"+(String)searchMap.get("loginname")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}





}
