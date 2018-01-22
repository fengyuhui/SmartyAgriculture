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
import cn.bupt.smartyagl.entity.autogenerate.UserAuthView;
import cn.bupt.smartyagl.entity.autogenerate.Users;
import cn.bupt.smartyagl.service.IAdminUserService;
/**
 * 
 *<p>Title:LogInterceptor后台权限拦截器</p>
 *<p>Description:</p>
 * @author waiting
 *@date 2016年7月14日 下午3:16:20
 */
@Component
public class AdminAuthInterceptor extends HandlerInterceptorAdapter {
	
	private Users users;
	//
	@Autowired
	IAdminUserService adminUserService;
	
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
		AdminUser user =  (cn.bupt.smartyagl.entity.autogenerate.AdminUser) request.getSession().getAttribute(Constants.SESSION_USER);
		if(user != null){
			AdminUser adminUser = adminUserService.fidnAdminUser(user.getName());
			List<UserAuthView> userAuthViews = adminUserService.getUserAuthViewName( adminUser.getId() );
			for( UserAuthView uv :userAuthViews){
				if( requestUri.contains( uv.getUrl() ) ){
					return true;
				}
			}
		}
		java.io.PrintWriter out=response.getWriter();
		out.println("<html>");
		out.println("<script>");
		String path=request.getContextPath() + "/"+ Constants.ERROR_PAGE;
		out.println("window.open('"+path+"','_top')");
		out.println("</script>");
		out.println("</html>");
		return false;
	}

}
