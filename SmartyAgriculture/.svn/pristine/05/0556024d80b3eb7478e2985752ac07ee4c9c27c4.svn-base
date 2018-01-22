package cn.bupt.smartyagl.unipay.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import cn.bupt.smartyagl.config.UnipayConfig;


public class AutoLoadServlet extends HttpServlet {
	
	/**
	 * 2016-8-24
	 */
	private static final long serialVersionUID = 68434667576990515L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		UnipayConfig.getConfig().loadPropertiesFromSrc();// 从classpath加载acp_sdk.properties文件
		super.init();
	}
}
