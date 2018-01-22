package cn.bupt.smartyagl.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.dao.autogenerate.GoodsRejectedMapper;
import cn.bupt.smartyagl.dao.autogenerate.GoodsRejectedViewMapper;
import cn.bupt.smartyagl.entity.autogenerate.GoodsRejectedView;
import cn.bupt.smartyagl.entity.autogenerate.GoodsRejectedViewExample;
import cn.bupt.smartyagl.service.IGoodsRejectedService;

@Service
public class GoodsRejectedServiceImpl implements IGoodsRejectedService {

	@Autowired
	GoodsRejectedViewMapper goodsRejectedViewMapper;
	@Autowired
	GoodsRejectedMapper goodsRejectedMapper;
	
	@Override
	public void goodsRejectedIndex(ModelAndView modelAndView, int currentPage,
			int pageSize) {
		// TODO Auto-generated method stub
		GoodsRejectedViewExample goodsRejectedViewExample=new GoodsRejectedViewExample();
		Page page = PageHelper.startPage(currentPage, pageSize);
		List<GoodsRejectedView> goodsRejectedViewsList=goodsRejectedViewMapper.selectByExample(goodsRejectedViewExample);
		modelAndView.addObject("goodsRejectedViewsList", goodsRejectedViewsList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
	}

	@Override
	public boolean deleteRejected(int id) {
		// TODO Auto-generated method stub
		int num=goodsRejectedMapper.deleteByPrimaryKey(id);
		if(num==1){
			return true;
		}
		else{
			return false;
		}	
	}

}
