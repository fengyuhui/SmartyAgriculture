package cn.bupt.smartyagl.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.dao.autogenerate.FarmVisitOrderViewMapper;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderView;
import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderViewExample;
import cn.bupt.smartyagl.model.FarmVisitOrderViewModel;
import cn.bupt.smartyagl.service.FarmVisitOrderViewService;
import cn.bupt.smartyagl.util.excel.ExportExcel;
import cn.bupt.smartyagl.util.excel.ExportExcelUtil;
@Service
public class FarmVisitOrderViewServiceImpl implements FarmVisitOrderViewService {

	@Autowired
	FarmVisitOrderViewMapper farmVisitOrderViewMapper;
	@Override
	public List<FarmVisitOrderView> getOrderListByUserId(Integer userId) {
		FarmVisitOrderViewExample fvove = new FarmVisitOrderViewExample();
		fvove.createCriteria().andUserIdEqualTo(userId).andIsDeleteEqualTo(false);
		return farmVisitOrderViewMapper.selectByExample(fvove);
	}
	@Override
	public void getOrderList(ModelAndView mv, int pageNumber, int pageSize, String orderBy, Date starttime,
			Date endtime) {
		// TODO Auto-generated method stub
		FarmVisitOrderViewExample orderViewExample = new FarmVisitOrderViewExample();
		cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderViewExample.Criteria criteria = orderViewExample
				.createCriteria();
		if (starttime != null && endtime != null) {
			criteria.andVisitTimeBetween(starttime, endtime);
		}

		// 分页
		Page page = PageHelper.startPage(pageNumber, pageSize, orderBy);
		List<FarmVisitOrderView> orderView = farmVisitOrderViewMapper
				.selectByExample(orderViewExample);
		mv.addObject("orderView", orderView);
		// 总页数
		int allPages = page.getPages();
		mv.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		mv.addObject("currentPage", currentPage);		
	}
	
	@Override
	public FarmVisitOrderView getOrderDetail(Integer orderId) {
		List<FarmVisitOrderView> orderList = null;
		try{
			 FarmVisitOrderViewExample ge = new FarmVisitOrderViewExample();
			 ge.createCriteria().andIdEqualTo(orderId);
			 orderList = farmVisitOrderViewMapper.selectByExample(ge);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		if(orderList.size() <= 0){
			return null;
		}
		
		return orderList.get(0);
	}
	
	@Override
	public void exportExcelOrder(Date starttime, Date endtime, HttpServletResponse response) {
		ExportExcel<FarmVisitOrderViewModel> excel = new ExportExcel<FarmVisitOrderViewModel>();
		String[] headers = { "订单号", "价格", "数量", "参观时间", "参观项目名称", "参观人姓名",
				"联系方式", " 参观留言" ,"订单状态"};
		FarmVisitOrderViewExample orderViewExample = new FarmVisitOrderViewExample();
		cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderViewExample.Criteria criteria = orderViewExample
				.createCriteria();
		if (starttime != null && endtime != null) {
			criteria.andVisitTimeBetween(starttime, endtime);
		}
//        Long st = System.currentTimeMillis();
		List<FarmVisitOrderView> orderView = farmVisitOrderViewMapper
				.selectByExample(orderViewExample);
//		long end = System.currentTimeMillis();
//		System.out.println("查询耗时："+(end-st)/1000 +"s");
		List<FarmVisitOrderViewModel> model = new ArrayList<FarmVisitOrderViewModel>(orderView.size());
		for(FarmVisitOrderView fvov:orderView) {
			FarmVisitOrderViewModel fvovm = new FarmVisitOrderViewModel();
			fvovm.setId(fvov.getId());
			fvovm.setMoney(fvov.getMoney());
			fvovm.setNum(fvov.getNum());
			fvovm.setVisitTime(fvov.getVisitTime());
			fvovm.setTitle(fvov.getTitle());
			fvovm.setName(fvov.getName());
			fvovm.setPhone(fvov.getPhone());
			fvovm.setMessage(fvov.getMessage());
			int sta = fvov.getOrderStatus();
			switch(sta) {
			case 1:
				fvovm.setOrderStatus("未付款");
					break;
			case 2:
				fvovm.setOrderStatus("已付款");
				break;
			case 3:
				fvovm.setOrderStatus("已完成体验");
				break;
			case 4:
				fvovm.setOrderStatus("已退款");
			}
			model.add(fvovm);
		}
		try {
			String deskPath = ExportExcelUtil.getDeskPath();
			System.out.println(deskPath);
			String filePath = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd");
			if(starttime != null) {
				filePath = deskPath + File.separator+sdf.format(starttime) + "-" +sdf.format(endtime)+ "订单统计.xls";
			}
			else
				filePath = deskPath + File.separator +  "所有参观农场订单.xls";
			OutputStream out = new FileOutputStream(filePath);
//	        Long st1 = System.currentTimeMillis();
	        excel.exportExcel(headers, model, out);
//			long end1 = System.currentTimeMillis();
//			System.out.println("导出excel耗时："+(end1-st1)/1000 +"s");
			
			//System.out.println("aaa");
			out.close();
//	        Long st2 = System.currentTimeMillis();
			ExportExcelUtil.download(filePath, response);
//			long end2 = System.currentTimeMillis();
//			System.out.println("传输excel耗时："+(end2-st2)/1000 +"s");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
