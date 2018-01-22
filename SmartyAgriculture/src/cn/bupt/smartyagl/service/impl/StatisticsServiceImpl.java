package cn.bupt.smartyagl.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.Header;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.bupt.smartyagl.dao.individuation.OrderQueryVo;
import cn.bupt.smartyagl.dao.individuation.OrderStatisticsMapper;
import cn.bupt.smartyagl.model.inputStatisticsModel;
import cn.bupt.smartyagl.model.totalSalesModel;
import cn.bupt.smartyagl.service.IStatisticsService;
import cn.bupt.smartyagl.util.TimeUtil;
import cn.bupt.smartyagl.util.excel.ExportExcel;
import cn.bupt.smartyagl.util.excel.ExportExcelUtil;

@Service
public class StatisticsServiceImpl implements IStatisticsService {

	@Autowired
	OrderStatisticsMapper orderStatisticsMapper;

	@Override
	public void totalSalesStatistics(ModelAndView modelAndView, int pageNumber,
			int pageSize, int status, String starttime,String endtime) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		// 分页
		Page page = PageHelper.startPage(pageNumber, pageSize);
		List<totalSalesModel> totalSalesStatisticsList = orderStatisticsMapper
				.totalSalesStatistics(vo);
		modelAndView.addObject("totalSalesStatisticsList",
				totalSalesStatisticsList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
		//
		// int count=totalSalesStatisticsList.size();
		// Double sales[]=new Double[count];
		// String time[]=new String[count];
		//
		// this.formant(modelAndView, totalSalesStatisticsList, sales, time);
		// modelAndView.addObject("count", count);
		// modelAndView.addObject("sales", sales);
		// modelAndView.addObject("time", time);
	}

	public void formant(ModelAndView modelAndView,
			List<totalSalesModel> totalSalesStatisticsList, Double sales[],
			String time[]) {
		// List<Double> salesList=new ArrayList<Double>();//销售额
		// List<String> timeList=new ArrayList<String>();
		int i = 0;
		for (totalSalesModel oneSalesModel : totalSalesStatisticsList) {
			// salesList.add(oneSalesModel.getMoney());
			// timeList.add(oneSalesModel.getShowTime());
			sales[i] = oneSalesModel.getMoney();
			time[i] = oneSalesModel.getShowTime();
			i++;
		}
	}

	@Override
	public void totalSalesStatisticsNotDivide(int status, String starttime,String endtime,
			Map<String, List<String>> returnMap) {
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		// TODO Auto-generated method stub
		List<totalSalesModel> totalSalesStatisticsList = orderStatisticsMapper
				.totalSalesStatistics(vo);
		List<String> salesList = new ArrayList<String>();// 销售额
		List<String> timeList = new ArrayList<String>();
		for (totalSalesModel oneSalesModel : totalSalesStatisticsList) {
			salesList.add(String.valueOf(oneSalesModel.getMoney()));
			timeList.add(oneSalesModel.getShowTime());
		}
		returnMap.put("sales", salesList);
		returnMap.put("time", timeList);
	}
//未
	@Override
	public  void sourceStatictics(ModelAndView modelAndView,int pageNumber, 
	        int pageSize,int status,String starttime,String endtime,int goodsId){
	    
	}
    
	
	
	@Override
	public boolean exportExcelTotalSales(int goodsId,int status, String starttime,String endtime,
			String tempFilePath, HttpServletResponse response) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(goodsId);
		ExportExcel<totalSalesModel> excel = new ExportExcel<totalSalesModel>();
		String[] headers = { "时间", "消费金额", "购买次数" };
		List<totalSalesModel> totalSalesStatisticsList = orderStatisticsMapper
				.productStatistics(vo);
		try {
			String deskPath = ExportExcelUtil.getDeskPath();
			String filePath = deskPath + File.separator + tempFilePath + "-"
					+ TimeUtil.getYMD() + ".xls";
			OutputStream out = new FileOutputStream(filePath);
			excel.exportExcel(headers, totalSalesStatisticsList, out);
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

	@Override
	public void productStatictics(ModelAndView modelAndView, int pageNumber,
			int pageSize, int status, String starttime,String endtime, int goodsId) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(goodsId);
		// 分页
		Page page = PageHelper.startPage(pageNumber, pageSize);
		List<totalSalesModel> productStatisticsList = orderStatisticsMapper
				.productStatistics(vo);
		modelAndView.addObject("totalSalesStatisticsList",
				productStatisticsList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
	}

	@Override
	public void echartsProductStatistics(int status, String starttime,String endtime,
			Map<String, List<String>> returnMap, int goodsId) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(goodsId);
		List<totalSalesModel> productStatisticsList = orderStatisticsMapper
				.productStatistics(vo);
		List<String> salesList = new ArrayList<String>();// 销售额
		List<String> timeList = new ArrayList<String>();
		for (totalSalesModel oneSalesModel : productStatisticsList) {
			salesList.add(String.valueOf(oneSalesModel.getMoney()));
			timeList.add(oneSalesModel.getShowTime());
		}
		returnMap.put("sales", salesList);
		returnMap.put("time", timeList);
	}

	@Override
	public void typeStatistics(ModelAndView modelAndView, int pageNumber,
			int pageSize, int status, String starttime,String endtime, int typeIdParent) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(typeIdParent);
		// 分页
		Page page = PageHelper.startPage(pageNumber, pageSize);
		List<totalSalesModel> typeStatisticsList = orderStatisticsMapper
				.typeStatistics(vo);
		modelAndView.addObject("typeStatisticsList",
				typeStatisticsList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
	}

	@Override
	public void echartsTypeStatistics(int status, String starttime,String endtime,
			Map<String, List<String>> returnMap, int typeIdParent) {
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(typeIdParent);
		// TODO Auto-generated method stub
		List<totalSalesModel> typeStatisticsList = orderStatisticsMapper
				.typeStatistics(vo);
		List<String> salesList = new ArrayList<String>();// 销售额
		List<String> timeList = new ArrayList<String>();
		for (totalSalesModel oneSalesModel : typeStatisticsList) {
			salesList.add(String.valueOf(oneSalesModel.getMoney()));
			timeList.add(oneSalesModel.getShowTime());
		}
		returnMap.put("sales", salesList);
		returnMap.put("time", timeList);
	}

	@Override
	public void productInputStatics(ModelAndView modelAndView, int pageNumber,
			int pageSize, String starttime, String endtime,
			int goodsId) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(goodsId);
		// 分页
		Page page = PageHelper.startPage(pageNumber, pageSize);
		List<inputStatisticsModel> productInputStatisticsList = orderStatisticsMapper
				.productInputStatics(vo);
		modelAndView.addObject("totalSalesStatisticsList",
				productInputStatisticsList);
		// 总页数
		int allPages = page.getPages();
		modelAndView.addObject("allPages", allPages);
		// 当前页码
		int currentPage = page.getPageNum();
		modelAndView.addObject("currentPage", currentPage);
	}

	@Override
	public void echartsProductInputStatistics(String starttime, String endtime,
			Map<String, List<String>> returnMap, int goodsId) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(goodsId);
		List<inputStatisticsModel> productInputStatisticsList = orderStatisticsMapper
				.productInputStatics(vo);
		List<String> inputList = new ArrayList<String>();// 进货
		List<String> timeList = new ArrayList<String>();
		for (inputStatisticsModel oneInputStatisticsModel : productInputStatisticsList) {
			timeList.add(oneInputStatisticsModel.getShowTime());
			inputList.add(String.valueOf(oneInputStatisticsModel.getNum()));
		}
		returnMap.put("input", inputList);
		returnMap.put("time", timeList);
	}

	@Override
	public boolean exportExcelProductInput(String starttime, String endtime,int goodsID,
			String tempFilePath, HttpServletResponse response) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(goodsID);
		ExportExcel<inputStatisticsModel> excel = new ExportExcel<inputStatisticsModel>();
		String[] headers = { "时间", "进货数量", "进货次数" };
		List<inputStatisticsModel> productInputStatisticsList = orderStatisticsMapper
				.productInputStatics(vo);
		try {
			String deskPath = ExportExcelUtil.getDeskPath();
			String filePath = deskPath + "//" + tempFilePath + "-"
					+ TimeUtil.getYMD() + ".xls";
			OutputStream out = new FileOutputStream(filePath);
			excel.exportExcel(headers, productInputStatisticsList, out);
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

	@Override
	public boolean exportExcelTotalSales(int status, String starttime,
			String endtime, int typeIdParent, String tempFilePath,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		vo.setProductId(typeIdParent);
		// TODO Auto-generated method stub
		ExportExcel<totalSalesModel> excel = new ExportExcel<totalSalesModel>();
		String[] headers = { "时间", "消费金额", "购买次数" };
		List<totalSalesModel> totalSalesStatisticsList = orderStatisticsMapper
				.typeStatistics(vo);
		try {
			String deskPath = ExportExcelUtil.getDeskPath();
			String filePath = deskPath + "//" + tempFilePath + "-"
					+ TimeUtil.getYMD() + ".xls";
			OutputStream out = new FileOutputStream(filePath);
			excel.exportExcel(headers, totalSalesStatisticsList, out);
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

	@Override
	public void exportExcelTotalSales(int status, String starttime, String endtime, String string,
			HttpServletResponse response) {
		OrderQueryVo vo=new OrderQueryVo();
		vo.setStatus(status);
		vo.setStarttime(starttime);
		vo.setEndtime(endtime);
		ExportExcel<totalSalesModel> excel = new ExportExcel<totalSalesModel>();
		String[] headers = { "时间", "消费金额", "购买次数" };
		List<totalSalesModel> totalSalesStatisticsList = orderStatisticsMapper
				.totalSalesStatistics(vo);
		try {
			String deskPath = ExportExcelUtil.getDeskPath();
			String filePath = deskPath + File.separator + string + "-"
					+ TimeUtil.getYMD() + ".xls";
			OutputStream out = new FileOutputStream(filePath);
			excel.exportExcel(headers, totalSalesStatisticsList, out);
			out.close();
			ExportExcelUtil.download(filePath, response);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
