package cn.bupt.smartyagl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.converter.BlockManager2BlockManagerModel;
import cn.bupt.smartyagl.converter.BlockManagerMenu2BlockManagerMenuModel;
import cn.bupt.smartyagl.dao.autogenerate.BlockManagerMenuMapper;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerMenu;
import cn.bupt.smartyagl.entity.autogenerate.BlockManagerMenuExample;
import cn.bupt.smartyagl.entity.autogenerate.Menu;
import cn.bupt.smartyagl.entity.autogenerate.MenuExample;
import cn.bupt.smartyagl.entity.autogenerate.MenuExample.Criteria;
import cn.bupt.smartyagl.model.BlockManagerMenuModel;
import cn.bupt.smartyagl.model.MenuModel;
import cn.bupt.smartyagl.service.IBlockManagerMenuService;

@Service
public class BlockManagerMenuService implements IBlockManagerMenuService {

	@Autowired
	BlockManagerMenuMapper blockManagerMenuMapper;
	
	@Override
	public List<BlockManagerMenuModel> getDisplayMenu() {
		List<BlockManagerMenuModel> blockManagerMenuModelList = null;
		short parentId=0;
		blockManagerMenuModelList=this.getSubMenu(parentId);//得到一级菜单
		List<BlockManagerMenuModel> subBlockManagerMenuModelList = null;
		for (BlockManagerMenuModel object:blockManagerMenuModelList) {
			parentId=object.getId();
			subBlockManagerMenuModelList=this.getSubMenu(parentId);
			object.setSubMenus(subBlockManagerMenuModelList);
		}
		//this.printList(menuList);
		return blockManagerMenuModelList;
	}

	@Override
	public List<BlockManagerMenuModel> getSubMenu(short parentId) {
		BlockManagerMenuExample blockManagerMenuExample=new BlockManagerMenuExample();
		cn.bupt.smartyagl.entity.autogenerate.BlockManagerMenuExample.Criteria criteria=blockManagerMenuExample.createCriteria();
		criteria.andParentidEqualTo(parentId);
		criteria.andStatusEqualTo(ConstantsSql.MENU_STATUS_SHOW);
		List<BlockManagerMenu> blockManagerMenuList = blockManagerMenuMapper.selectByExample(blockManagerMenuExample);
		List<BlockManagerMenuModel> returnMenuModel=new ArrayList<>();
		for(BlockManagerMenu object:blockManagerMenuList){
			BlockManagerMenuModel blockManagerMenuModel = BlockManagerMenu2BlockManagerMenuModel.converter(object);
			returnMenuModel.add(blockManagerMenuModel);
		}
		return returnMenuModel; 
	}
	
	

}
