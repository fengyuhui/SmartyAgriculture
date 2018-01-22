package cn.bupt.smartyagl.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.dao.autogenerate.MenuMapper;
import cn.bupt.smartyagl.entity.autogenerate.Menu;
import cn.bupt.smartyagl.entity.autogenerate.MenuExample;
import cn.bupt.smartyagl.entity.autogenerate.MenuExample.Criteria;
import cn.bupt.smartyagl.model.MenuModel;
import cn.bupt.smartyagl.service.IMenuService;

@Service
public class MenuServiceImpl implements IMenuService {
	
	@Autowired
	MenuMapper menuMapper;
	
	@Override
	public List<MenuModel> getDisplayMenu() {
		// TODO Auto-generated method stub
		List<MenuModel> menuList=null;
		short parentId=0;
		menuList=this.getSubMenu(parentId);//得到一级菜单
		List<MenuModel> subMenuList=null;
		for (MenuModel object:menuList) {
			parentId=object.getId();
			subMenuList=this.getSubMenu(parentId);
			object.setSubMenus(subMenuList);
		}
		//this.printList(menuList);
		return menuList;
	}

	@Override
	public List<MenuModel> getSubMenu(short parentId) {
		// TODO Auto-generated method stub
		MenuExample menuExample=new MenuExample();
		Criteria criteria=menuExample.createCriteria();
		criteria.andParentidEqualTo(parentId);
		criteria.andStatusEqualTo(ConstantsSql.MENU_STATUS_SHOW);
		List<Menu> menuList=menuMapper.selectByExample(menuExample);
		List<MenuModel> returnMenuModel=new ArrayList<MenuModel>();
		for(Menu object:menuList){
			MenuModel menuModel=new MenuModel();
			menuModel.setId(object.getId());
			menuModel.setParentid(object.getParentid());
			menuModel.setApp(object.getApp());
			menuModel.setController(object.getController());
			menuModel.setMethod(object.getMethod());
			menuModel.setName(object.getName());
			returnMenuModel.add(menuModel);
		}
		return returnMenuModel; 
	}
	/**
	 * 返回的菜单格式
	 */
	//public 
	/**
	 * 输出list
	 */
	public void printList(List<MenuModel> menuList){
		for (MenuModel object:menuList)
		{
			System.out.println("------------------------");
			System.out.println("id->"+object.getId());
			System.out.println("Parentid->"+object.getParentid());
			System.out.println("Controller->"+object.getController());
			System.out.println("Method->"+object.getMethod());
			System.out.println("Name->"+object.getName());
			if(object.getSubMenus()!=null){
				this.printList(object.getSubMenus());
			}
		}
	}

}
