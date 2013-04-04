/*
 * If user not logged in - redirect to login page
 */

package com.netcracker.libra.util.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		String uri = request.getRequestURI();
		if (!uri.endsWith("contacts.html") && 
				!uri.endsWith("about.html") && 
				!uri.endsWith("index.html") && 
				!uri.endsWith("login.html") &&
				!uri.endsWith("university.html") &&
				!uri.endsWith("faculty.html") &&
				!uri.endsWith("department.html") &&
				!uri.endsWith("welcome.html") &&
				!uri.endsWith("register.html")) {
			SessionToken userData = (SessionToken) request.getSession().getAttribute("LOGGEDIN_USER");
			if (userData == null) {
				request.getSession().setAttribute("accessDeniedMessage", "Недостаточно прав для доступа к запрашиваемой странице");
				response.sendRedirect("/Libra/login.html");
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		String uri = request.getRequestURI();
			if (uri.contains("logout.html"))
				request.getSession().setAttribute("LOGGEDIN_USER", null);
	}
}
