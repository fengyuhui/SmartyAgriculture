package cn.bupt.smartyagl.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.entity.autogenerate.AdminUser;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.entity.autogenerate.Users;
/**
 * 
 *<p>Title:LogInterceptor后台登陆验证拦截器</p>
 *<p>Description:</p>
 * @author waiting
 *@date 2016年7月14日 下午3:16:20
 */
@Component
public class BlockManagerLogInterceptor extends HandlerInterceptorAdapter {
	
	private BlockManager blcokManager;
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// TODO Auto-generated method stub
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath() + "/";
		String url = requestUri.substring(contextPath.length());
		if (url.equals(Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_LOGIN)) {
			return true;
		}
		else {
			blcokManager=(BlockManager)request.getSession().getAttribute(Constants.SESSION_BLOCK_MANAGER);
			if (null == blcokManager) {
			java.io.PrintWriter out=response.getWriter();
			out.println("<html>");
			out.println("<script>");
			String path=request.getContextPath() + "/"+ Constants.BLOCK_MANAGER_SYSTEM_PUBLIC_LOGIN;
			out.println("window.open('"+path+"','_top')");
			out.println("</script>");
			out.println("</html>");
				return false;
			}
			else{
				return true;
			}
		}
	}

}
