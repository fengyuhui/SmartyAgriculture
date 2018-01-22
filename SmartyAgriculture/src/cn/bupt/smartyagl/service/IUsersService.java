package cn.bupt.smartyagl.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.entity.autogenerate.Users;
import cn.bupt.smartyagl.entity.autogenerate.UsersExample;

/**
 * 后台用户管理的service
 * @author waiting
 *
 */
public interface IUsersService {
 /**
  * 根据usersExample实例获取用户
  */
	public List<Users> getUsersByUsersExample(UsersExample usersEx) ;

	//public 	List<users> getUsers();

//public List<users> getUsersByUsersExample(userExample usersEx);
}
