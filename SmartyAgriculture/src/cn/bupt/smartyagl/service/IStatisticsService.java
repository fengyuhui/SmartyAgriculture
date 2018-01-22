package cn.bupt.smartyagl.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public interface IStatisticsService {
  
    
    
    
	/**
	 * 总销售额统计
	 * @param modelAndView
	 * @param pageNumber
	 * @param pageSize
	 * @param status
	 * @param timeFlag
	 */
	public void totalSalesStatistics(ModelAndView modelAndView, int pageNumber, int pageSize,int status,String starttime,String endtime);
	/**
	 * 不分页获取销售额数据，主要用户前台echarts数据
	 * @param status
	 * @param timeFlag
	 * @param returnMap
	 */
	public void totalSalesStatisticsNotDivide(int status,String starttime,String endtime,Map<String, List<String>> returnMap);
	/**
	 * 导出总销售额
	 * @param status
	 * @param timeFlag
	 * @return
	 */
	public boolean exportExcelTotalSales(int goodsId,int status,String starttime,String endtime,String tempFilePath,HttpServletResponse response);
	/**
     * 商品销售额资金来源统计
     * @param modelAndView
     * @param pageNumber
     * @param pageSize
     * @param status
     * @param timeFlag
     * @param goodsId
     */
	public  void sourceStatictics(ModelAndView modelAndView,int pageNumber, int pageSize,int status,String starttime,String endtime,int goodsId);
	
	
	
	/**
	 * 商品销售额统计
	 * @param modelAndView
	 * @param pageNumber
	 * @param pageSize
	 * @param status
	 * @param timeFlag
	 * @param goodsId
	 */
	public  void productStatictics(ModelAndView modelAndView,int pageNumber, int pageSize,int status,String starttime,String endtime,int goodsId);
	/**
	 * 商品销售统计 图表数据
	 * @param status
	 * @param timeFlag
	 * @param returnMap
	 * @param goodsId
	 */
	public void echartsProductStatistics(int status,String starttime,String endtime,Map<String, List<String>> returnMap,int goodsId);
   /**
    * 单个商品进货统计
    * @param modelAndView
    * @param pageNumber
    * @param pageSize
    * @param status
    * @param starttime
    * @param endtime
    * @param goodsId
    */
	public void productInputStatics(ModelAndView modelAndView,int pageNumber, int pageSize,String starttime,String endtime,int goodsId);
	
	/**
     * 一级类别商品销售额统计
     * @param modelAndView
     * @param pageNumber
     * @param pageSize
     * @param status
     * @param timeFlag
     * @param typeIdParent
     */
    public void typeStatistics(ModelAndView modelAndView,int pageNumber, int pageSize,int status,String starttime,String endtime,int typeIdParent);
    /**
     * 一级类别商品销售额统计 生成图标
     * @param status
     * @param timeFlag
     * @param returnMap
     * @param typeIdParent
     */
    public void echartsTypeStatistics(int status,String starttime,String endtime,Map<String, List<String>> returnMap,int typeIdParent);
    /**
     * 商品进货统计 生成图所需要的数据
     * @param starttime
     * @param endtime
     * @param returnMap
     * @param goodsId
     */
    public void echartsProductInputStatistics(String starttime,String endtime,Map<String, List<String>> returnMap,int goodsId);
    /**
	 * 导出商品进货统计
	 * @return
	 */
	public boolean exportExcelProductInput(String starttime,String endtime,int goodsID,String tempFilePath,HttpServletResponse response);
	/**
	 * 导出一级商品销售 导出excel
	 * @param status
	 * @param starttime
	 * @param endtime
	 * @param typeIdParent
	 * @param tempFilePath
	 * @param response
	 * @return
	 */
	public boolean exportExcelTotalSales(int status,String starttime,String endtime,int typeIdParent,String tempFilePath,HttpServletResponse response);
	public void exportExcelTotalSales(int status, String starttime, String endtime, String string,
			HttpServletResponse response);
}
