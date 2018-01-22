package cn.bupt.smartyagl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.dao.autogenerate.UsersMapper;
import cn.bupt.smartyagl.entity.autogenerate.Users;
import cn.bupt.smartyagl.entity.autogenerate.UsersExample;
import cn.bupt.smartyagl.service.IUsersService;

@Service
public class UsersServiceImpl implements IUsersService{
	
	@Resource
	UsersMapper usersMapper;

	@Override
	public List<Users> getUsersByUsersExample(UsersExample usersEx) {
		//usersMapper usersMapper=new usersExample();
		// TODO Auto-generated method stub
		if (usersMapper==null) {
			System.out.println("usersMapper是空");
		}
		if(usersMapper.selectByExample(usersEx)!=null){
			return usersMapper.selectByExample(usersEx);
		}
		else {
			return null;
		}
		
	}

	


}
