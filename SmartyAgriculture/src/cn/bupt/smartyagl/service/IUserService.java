package cn.bupt.smartyagl.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.entity.autogenerate.User;
import cn.bupt.smartyagl.entity.autogenerate.UserExample;

/**
 * 会员管理服务接口
 * @author waiting
 *
 */
public interface IUserService {
	/**
	 * 获取用户列表
	 * 
	 * 
	 */
	public List<User> getUserList(String userName,String phoneNumber);//根据用户名和电话号查询@wlz
	/**
	 * 
	 * @return
	 */
	public List<User> getUserByUserExample(UserExample UserExample);
	/**
	 *  根据id删除用户
	 * @return
	 */
	public boolean deleteUserById(Integer userId);
	/**
	 * 根据id返回用户信息
	 */
	public User getUserInfoById(Integer userId);
	
	public boolean adduserInfo(User user);//添加用户信息
	
	public boolean updateMyuserInfo(User user);//更改用户信息@zxy
	/**
	 * 向数据库中插入一条数据记录
	 * @param User
	 * @return
	 */
	public int insertUser(User User);
	
	public boolean changepasswd(User user);//忘记密码修改密码@zxy
	/**
	 * 修改用户状态
	 * @param id
	 * @param status
	 * @return
	 * @author waiting
	 */
	public boolean UpdateUserStatus(Integer id,Integer status);
	
	public boolean updateLoginTime(Integer id,Date loginTiem);
	
	/**
	 * 取手机前6位+6位随机随机生成用户昵称
	 */
	public String createNickName(String phone);
	/**
	 * 校验用户表中是否存在某个昵称
	 * @param nickName 昵称
	 * @return 不存在相同的昵称时，返回true,否则false;
	 */
	public boolean checkName(String nickName);
	/**
	 * 检查手机号是否已经存在
	 * @param phone
	 * @return 手机号存在 :true 不存在：false
	 */
	public boolean phoneExisted(String phone);
	
	/**
	 * 根据电话号码获取用户信息
	 */
	public User getUserByPhone(String phone);
	/**
	 * 获取用户统计列表
	 * @param mav
	 * @param pageNumber
	 * @param pageSize
	 * @param orderBy
	 * @return
	 */
	public void getUserStatistics(ModelAndView modelAndView, int pageNumber, int pageSize,
			int status,String  startTime,String endTime);
	/**
	 * 导出用户统计信息
	 * @param status
	 * @param timeFlag
	 * @param response
	 * @return
	 */
	public boolean exportExcelUserStatistics(int status,String startime,String endtime,HttpServletResponse response);
}