package cn.bupt.smartyagl.unipay.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//import cn.bupt.smartyagl.controller.inf.UniPayBase;

public class CharsetEncodingFilter implements Filter {
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		request.setCharacterEncoding(UniPayBase.encoding_UTF8);
//		response.setContentType("text/html; charset="+ UniPayBase.encoding_UTF8);
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
}
