package cn.bupt.smartyagl.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import cn.bupt.smartyagl.entity.autogenerate.AdminUser;
import cn.bupt.smartyagl.entity.autogenerate.Auth;
import cn.bupt.smartyagl.entity.autogenerate.UserAuthView;

/** 
 * 用户权限
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-8-30 上午9:43:58 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
public interface IAdminUserService {
	/**
	 * 获取用户状态列表
	 * @param AdminUserServiceImpl 用户筛选列表
	 * @return
	 */
	public List<AdminUser> getAdminList(AdminUser adminSelective,List<Integer> authList);
	
	/**
	 * 添加用户
	 * @param admin
	 * @return
	 */
	public boolean addAdmin(AdminUser admin,List<Integer> authList);
	
	/**
	 * 更新用户
	 * @param admin
	 * @return
	 */
	public boolean updateAdmin(AdminUser admin,List<Integer> authList);
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	public boolean deleteAdmin(Integer id);
	
	/**
	 * 根据用户名查找用户
	 */
	public AdminUser fidnAdminUser(String name);
	
	/**
	 * 根据用户id查找对应权限
	 * @param userId
	 * @return
	 */
	public List<UserAuthView> getUserAuthViewName(Integer userId);
	/**
	 * 获取所有权限列表
	 * @param userId
	 * @return
	 */
	public List<Auth> getUserAuthViewName();
	
	/**
	 * 根据id获取用户信息
	 * @param id
	 * @return
	 */
	public AdminUser findAdminUser(Integer id);
}
