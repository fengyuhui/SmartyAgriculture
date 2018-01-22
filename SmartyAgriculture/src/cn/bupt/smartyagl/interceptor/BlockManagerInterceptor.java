package cn.bupt.smartyagl.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.bupt.smartyagl.constant.Constants;
import cn.bupt.smartyagl.constant.ConstantsSql;
import cn.bupt.smartyagl.entity.autogenerate.AdminUser;
import cn.bupt.smartyagl.entity.autogenerate.BlockManager;
import cn.bupt.smartyagl.entity.autogenerate.UserAuthView;
import cn.bupt.smartyagl.entity.autogenerate.Users;
import cn.bupt.smartyagl.service.IAdminUserService;
import cn.bupt.smartyagl.service.IBlockManagerService;
/**
 * 
 *<p>Title:LogInterceptor后台权限拦截器</p>
 *<p>Description:</p>
 * @author waiting
 *@date 2016年7月14日 下午3:16:20
 */
@Component
public class BlockManagerInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	IBlockManagerService blockManagerService;
	
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
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath() + "/";
		String url = requestUri.substring(contextPath.length());
		BlockManager user =  (cn.bupt.smartyagl.entity.autogenerate.BlockManager) request.getSession().getAttribute(Constants.SESSION_BLOCK_MANAGER);
		if(user != null){
			return true;
		}
		java.io.PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<script>");
		String path=request.getContextPath() + "/"+ Constants.BLOCK_MANAGER_SYSTEM_ERROR_PAGE;
		out.println("window.open('"+path+"','_top')");
		out.println("</script>");
		out.println("</html>");
		return false;
	}

}
