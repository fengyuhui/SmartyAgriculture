package cn.bupt.smartyagl.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.service.IStatisticsService;

@Controller
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {

	@Autowired
	IStatisticsService statisticsService;

	int pageSize = Constants.PAGESIZE;// ，每一页的大小

	/**
	 * 总销售额统计
	 * 
	 * @param allPages
	 * @param currentPage
	 * @param type
	 * @param status
	 * @param timeFlag
	 * @return
	 */
	@RequestMapping(value = "/totalSales/{allPages}/{currentPage}/{type}/{status}/{startime}/{endtime}")
	public ModelAndView totalSales(
			@PathVariable(value = "allPages") int allPages,
			@PathVariable(value = "currentPage") int currentPage,
			@PathVariable(value = "type") String type,
			@PathVariable(value = "status") int status,
			@PathVariable(value = "startime") String starttime,
			@PathVariable(value = "endtime") String endtime) {
		ModelAndView modelAndView = new ModelAndView(
				Constants.TOTAL_SALES_STATISTICS);
		if ("prvious".equals(type)) {
			if (currentPage > 1) {// 第一页不能往前翻页
				currentPage--;
			}
		} else if ("next".equals(type)) {
			currentPage++;
		} else if ("first".equals(type)) {
			currentPage = 1;
		} else if ("last".equals(type)) {
			currentPage = allPages;
		} else {
			currentPage = Integer.parseInt(type);
		}
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		statisticsService.totalSalesStatistics(modelAndView, currentPage,
				pageSize, status, starttime, endtime);
		modelAndView.addObject("status", status);
		// modelAndView.addObject("timeFlag", timeFlag);
		// 返回时间
		if ((starttime != null) && (!starttime.equals("0"))) {
			starttime = starttime.replaceFirst("-", "年");
			starttime = starttime.replace("-", "月");
			starttime = starttime + "日";
			modelAndView.addObject("starttime", "\"" + starttime + "\"");
		}
		else{
			modelAndView.addObject("starttime", "0");
		}
		if ((endtime != null) && (!endtime.equals("0"))) {
			endtime = endtime.replaceFirst("-", "年");
			endtime = endtime.replace("-", "月");
			endtime = endtime + "日";
			modelAndView.addObject("endtime", "\"" + endtime + "\"");
		}
		else{
			modelAndView.addObject("endtime", "0");
		}
		return modelAndView;
	}

	/**
	 * 总销售额图标数据
	 * 
	 * @param status
	 * @param timeFlag
	 * @return
	 */
	@RequestMapping(value = "/echartsDataTotalSales/{status}/{starttime}/{endtime}")
	@ResponseBody
	public Map<String, List<String>> echartsDataTotalSales(
			@PathVariable(value = "status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value = "endtime") String endtime) {
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		// int status, timeFlag=0;
		Map<String, List<String>> returnMap = new HashMap<String, List<String>>();
		statisticsService.totalSalesStatisticsNotDivide(status, starttime,
				endtime, returnMap);
		return returnMap;
	}

	/**
	 * 总销售额统计导出excel
	 * 
	 * @param status
	 * @param timeFlag
	 * @param response
	 */
	@RequestMapping(value = "/exportExcelDataTotalSales/{status}/{starttime}/{endtime}")
	public void exportExcelDataTotalSales(
			@PathVariable(value = "status") int status,
			@PathVariable(value = "starttime") String starttime,
			@PathVariable(value="endtime") String endtime,
			HttpServletResponse response) {
		if (starttime.equals("0")) {
			starttime = null;
		}
		if (endtime.equals("0")) {
			endtime = null;
		}
		statisticsService.exportExcelTotalSales(status,starttime,endtime, "总销售额统计",
				response);
	}

}
