package cn.bupt.smartyagl.service.impl;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.OrderViewMapper;
import cn.bupt.smartyagl.dao.autogenerate.UserMapper;
import cn.bupt.smartyagl.dao.individuation.OrderQueryVo;
import cn.bupt.smartyagl.dao.individuation.OrderStatisticsMapper;
import cn.bupt.smartyagl.entity.GoodsTypeParent;
import cn.bupt.smartyagl.entity.autogenerate.OrderView;
import cn.bupt.smartyagl.entity.autogenerate.OrderViewExample;
import cn.bupt.smartyagl.entity.autogenerate.Address;
import cn.bupt.smartyagl.entity.autogenerate.User;
import cn.bupt.smartyagl.entity.autogenerate.UserExample;
import cn.bupt.smartyagl.entity.autogenerate.UserExample.Criteria;
import cn.bupt.smartyagl.model.OrderViewModel;
import cn.bupt.smartyagl.model.UserModel;
import cn.bupt.smartyagl.model.UserStatisticsModel;
import cn.bupt.smartyagl.model.totalSalesModel;
import cn.bupt.smartyagl.service.IUserService;
import cn.bupt.smartyagl.util.MD5Util;
import cn.bupt.smartyagl.util.TimeUtil;
import cn.bupt.smartyagl.util.excel.ExportExcel;
import cn.bupt.smartyagl.util.excel.ExportExcelUtil;
/**
 * 
 *<p>Title:UserService</p>
 *<p>Description:</p>
 * @author waiting
 *@date 2016年5月17日 上午9:52:17
 */
@Service
public class UserServiceImpl implements IUserService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	OrderStatisticsMapper orderStatisticsMapper;
	/**
	 * pageNumber 第几页
	 * pageSize 每一页的大小,
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<User> getUserList(String userName,String phoneNumber) {
		// TODO Auto-generated method stub
		UserExample userExample=new UserExample();
		Criteria criteria = userExample.createCriteria();
		
        if(userName!=""&&userName!=null&&(!userName.equals("1"))){
        	criteria.andNameLike("%"+userName+"%");
        }
        if(phoneNumber!=""&&phoneNumber!=null&&(!phoneNumber.equals("1"))){
        	criteria.andPhoneLike("%"+phoneNumber+"%");
        }
        
		List<User> userList=this.getUserByUserExample(userExample);

		return userList;
	}

	@Override
	public List<User> getUserByUserExample(UserExample UserExample) {
		
		// TODO Auto-generated method stub
		return userMapper.selectByExample(UserExample);
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		// TODO Auto-generated method stub
		int i=userMapper.deleteByPrimaryKey(userId);
		if(i>0){
			return true;
		}
		else{
			return false;
		}
		
	}

	@Override
	public User getUserInfoById(Integer userId) {
		// TODO Auto-generated method stub
		User user=userMapper.selectByPrimaryKey(userId);
		return user;
	}
	
	
	
	@Override
	public int insertUser(User User) {
		// TODO Auto-generated method stub
		return userMapper.insert(User);
	}
	
	/** 
	 *@author zxy 
	 *@date  2016年6月15日 
	 */

	@Override
	public boolean updateMyuserInfo(User user) {
		try{
			if(user.getPasswd() != null){
				user.setPasswd( MD5Util.MD5(user.getPasswd()) );
			}
			
			int rs = userMapper.updateByPrimaryKeySelective(user);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	@Override
	public boolean changepasswd(User user) {
		try{
			if(UserModel.getNewPasswd1()!=null&&UserModel.getNewPasswd2().equals(UserModel.getNewPasswd1())){
//				user.setPasswd( user.getNewPasswd1() );
//				System.out.println("========="+user.getPasswd());
				user.setPasswd( MD5Util.MD5(UserModel.getNewPasswd1()) );
				UserExample ueExample = new UserExample();
				Criteria ctr= ueExample.createCriteria();
				ctr.andPhoneEqualTo(user.getPhone());
				int i = userMapper.updateByExampleSelective(user, ueExample);
				if(i<=0){
					return false;
				}
			}
			else
				return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}		
		return true;
	}
	
	@Override
	public boolean adduserInfo(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean UpdateUserStatus(Integer id, Integer status) {
		// TODO Auto-generated method stub
		User userInfo=userMapper.selectByPrimaryKey(id);
		userInfo.setStatus(status);
		int i=userMapper.updateByPrimaryKeySelective(userInfo);
		if(i==1){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * 更新用户登录时间
	 */
	@Override
	public boolean updateLoginTime(Integer id, Date loginTiem) {
		// TODO Auto-generated method stub
		User userInfo=userMapper.selectByPrimaryKey(id);
		userInfo.setLastLoginTime(loginTiem);
		int res=userMapper.updateByPrimaryKeySelective(userInfo);
		if(res==1){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public String createNickName(String phone) {
		// TODO Auto-generated method stub
		String nickName=phone.substring(0,6);
		Integer number = (int) ((Math.random()*9+1)*100000);
		nickName+=number;	
		nickName = Constants.PRE_NICK_NAME+nickName;
		return nickName;
	}

	@Override
	public boolean checkName(String nickName) {
		// TODO Auto-generated method stub
		UserExample userExample=new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andNameEqualTo(nickName);
		List<User> userList=this.getUserByUserExample(userExample);
		if(userList.size()>0){
			//存在相同的昵称
			return false;
		}
		return true;
	}

	@Override
	public boolean phoneExisted(String phone) {
		// TODO Auto-generated method stub
		UserExample userExample=new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<User> userList=this.getUserByUserExample(userExample);
		if(userList.size()>0){
			//存在手机号
			return true;
		}
		return false;
	}
	/**
	 * 根据用户手机号获取用户信息
	 * 存在返回user对象，否则返回null
	 */
	@Override
	public User getUserByPhone(String phone) {
		// TODO Auto-generated method stub
		UserExample userExample=new UserExample();
		Criteria criteria = userExample.createCriteria();
		criteria.andPhoneEqualTo(phone);
		List<User> userList = this.getUserByUserExample(userExample);
		if(!userList.isEmpty()){
			return userList.get(0);
		}
		return null;
	}

	@Override
	public void getUserStatistics(ModelAndView modelAndView,
			int pageNumber, int pageSize, int status,String  startTime,String endTime) {
		// TODO Auto-generated method stub
		OrderQueryVo queryVo=new OrderQueryVo();
		queryVo.setStatus(status);
		queryVo.setStarttime(startTime);
		queryVo.setEndtime(endTime);
		//分页
		Page page = PageHelper.startPage(pageNumber, pageSize);
		
		List<UserStatisticsModel> userStatisticsList=orderStatisticsMapper.UserStatistics(queryVo);
		modelAndView.addObject("userStatisticsList",userStatisticsList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages",allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage",currentPage);
	}

	@Override
	public boolean exportExcelUserStatistics(int status, String startime,String endtime,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		ExportExcel<UserStatisticsModel> excel=new ExportExcel<UserStatisticsModel>();
		String[] headers={"用户编号","用户昵称","消费金额","购买次数"};
		if (startime.equals("0")) {
			startime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		OrderQueryVo queryVo=new OrderQueryVo();
		queryVo.setStatus(status);
		queryVo.setStarttime(startime);
		queryVo.setEndtime(endtime);
		List<UserStatisticsModel> userStatisticsModels=orderStatisticsMapper.UserStatistics(queryVo);
		try {
			String deskPath=ExportExcelUtil.getDeskPath();
			String filePath=deskPath+"//会员销售额统计-"+TimeUtil.getYMD()+".xls";
			OutputStream out = new FileOutputStream(filePath);	
			excel.exportExcel(headers, userStatisticsModels, out);
			out.close();
			ExportExcelUtil.download(filePath, response);
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void writeFile(String strUrl,String fileName){
	        URL url = null;
	        try {
	            url = new URL(strUrl);
	        } catch (MalformedURLException e2) {
	            e2.printStackTrace();
	        }
	        InputStream is = null;
	        try {
	            is = url.openStream();
	        } catch (IOException e1) {
	            e1.printStackTrace();
	        }
	        OutputStream os = null;
	        File f = new File("d:\\webimg\\");
	        f.mkdirs();
	        try{
	            os = new FileOutputStream("d:\\webimg\\"+fileName);
	            int bytesRead = 0;
	            byte[] buffer = new byte[8192];
	            while((bytesRead = is.read(buffer,0,8192))!=-1){
	                os.write(buffer,0,bytesRead);}
	            }catch(FileNotFoundException e){ 
	                
	            }catch (IOException e) {
                  e.printStackTrace();
                  }
	  }
	
}
