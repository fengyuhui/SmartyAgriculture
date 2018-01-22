package cn.bupt.smartyagl.service.impl;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.AdminUserMapper;
import cn.bupt.smartyagl.dao.autogenerate.AuthMapper;
import cn.bupt.smartyagl.dao.autogenerate.UserAuthMapper;
import cn.bupt.smartyagl.dao.autogenerate.UserAuthViewMapper;
import cn.bupt.smartyagl.entity.autogenerate.AdminUser;
import cn.bupt.smartyagl.entity.autogenerate.AdminUserExample;
import cn.bupt.smartyagl.entity.autogenerate.Auth;
import cn.bupt.smartyagl.entity.autogenerate.AuthExample;
import cn.bupt.smartyagl.entity.autogenerate.UserAuth;
import cn.bupt.smartyagl.entity.autogenerate.UserAuthExample;
import cn.bupt.smartyagl.entity.autogenerate.UserAuthView;
import cn.bupt.smartyagl.entity.autogenerate.UserAuthViewExample;
import cn.bupt.smartyagl.entity.autogenerate.AddressExample.Criteria;
import cn.bupt.smartyagl.service.IAdminUserService;

/** 
 * @author  jm E-mail:740869614@qq.com 
 * @date 创建时间：2016-8-30 上午9:50:37 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
@Service
public class AdminUserServiceImpl implements IAdminUserService{
	@Autowired
	AdminUserMapper adminUserMapper;
	@Autowired
	UserAuthViewMapper userAuthViewMapper;
	@Autowired
	AuthMapper authMapper;
	@Autowired
	UserAuthMapper userAuthMapper;
	
	@Override
	public List<cn.bupt.smartyagl.entity.autogenerate.AdminUser> getAdminList(
			cn.bupt.smartyagl.entity.autogenerate.AdminUser adminSelective,List<Integer> authList) {
		AdminUserExample ae = new AdminUserExample();
		cn.bupt.smartyagl.entity.autogenerate.AdminUserExample.Criteria ca =  ae.createCriteria();
		
		//用户名模糊搜索
		if(adminSelective != null && adminSelective.getName() != null){
			ca.andNameLike("%"+adminSelective.getName()+"%");
		}
		
		ae.setOrderByClause("createTime desc");
		return adminUserMapper.selectByExample(ae);
	}

	@Override
	public boolean addAdmin(
			cn.bupt.smartyagl.entity.autogenerate.AdminUser admin,List<Integer> authList) {
		  admin.setCreateTime(new Date());
		  AdminUser adminUser = this.fidnAdminUser(admin.getName());
		  if(adminUser != null){
			  return false;
		  }
		  int i =adminUserMapper.insert(admin);
	        if(i!=0){
		       	 //添加新的权限
		   		 for(Integer authId: authList){
		   			 UserAuth ua = new UserAuth();
		   			 ua.setAuth_id(authId);
		   			 ua.setUser_id(admin.getId());
		   			 userAuthMapper.insertSelective(ua);
		   		 }
	            return true;
	        }else{
	            return false;
	        }
	}

	@Override
	public boolean updateAdmin(
			cn.bupt.smartyagl.entity.autogenerate.AdminUser admin,List<Integer> authList) {
		int i =adminUserMapper.updateByPrimaryKeySelective(admin);
		 //先清空权限
		 UserAuthExample ue = new UserAuthExample();
		 ue.createCriteria().andUser_idEqualTo(admin.getId());
		 userAuthMapper.deleteByExample( ue );
		 //添加新的权限
		 if(authList != null){
			 for(Integer authId: authList){
				 UserAuth ua = new UserAuth();
				 ua.setAuth_id(authId);
				 ua.setUser_id(admin.getId());
				 userAuthMapper.insertSelective(ua);
			 }
		 }
		 return true;
	}

	@Override
	public boolean deleteAdmin(Integer id) {
		int i =adminUserMapper.deleteByPrimaryKey(id);
        if(i!=0)
            return true;
        else
            return false;
	}

	@Override
	public AdminUser fidnAdminUser(String name) {
		AdminUserExample ae = new AdminUserExample();
		cn.bupt.smartyagl.entity.autogenerate.AdminUserExample.Criteria ca =  ae.createCriteria();
		ca.andNameEqualTo(name);
		List<AdminUser> adminList =  adminUserMapper.selectByExample(ae);
		if( adminList.size() > 0 ){
			return adminList.get(0);
		}
		return null;
	}
	
	@Override
	public AdminUser findAdminUser(Integer id) {
		AdminUser admin =  adminUserMapper.selectByPrimaryKey(id);
		return admin;
	}
	
	@Override
	public List<UserAuthView> getUserAuthViewName(Integer userId) {
		UserAuthViewExample ue = new UserAuthViewExample();
		ue.createCriteria().andIdEqualTo(userId);
		return userAuthViewMapper.selectByExample(ue);
	}

	@Override
	public List<Auth> getUserAuthViewName() {
		Map<String, List<Auth>> map = new HashMap<String, List<Auth>>();
		List<Auth> list = authMapper.selectByExample( new AuthExample());
		return list;
	}


}
