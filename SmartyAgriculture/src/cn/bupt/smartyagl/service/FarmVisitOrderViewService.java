package cn.bupt.smartyagl.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import cn.bupt.smartyagl.entity.autogenerate.FarmVisitOrderView;

@Service
public interface FarmVisitOrderViewService {
	/**
	 * 根据用户id获取参观农场的订单
	 * @param userId
	 * @return
	 */
	public List<FarmVisitOrderView> getOrderListByUserId(Integer userId);

	public void getOrderList(ModelAndView mv, int currentPage, int pageSize, String string, Date starttimeDate,
			Date endtimeDate);

	public void exportExcelOrder(Date starttimeDate, Date endtimeDate, HttpServletResponse response);

	FarmVisitOrderView getOrderDetail(Integer orderId);

}
