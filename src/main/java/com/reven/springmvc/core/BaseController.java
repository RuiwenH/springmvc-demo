package com.reven.springmvc.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 控制器基类
 */
public abstract class BaseController {
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	private static ThreadLocal<Map<String, Object>> outPutMsg = new ThreadLocal<Map<String, Object>>();

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected Model model;
	protected String refererURI;

	protected PropertiesFactoryBean propertiesReader;

	@ModelAttribute
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response, Model model) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		this.model = model;
		try {
			String referer = request.getHeader("referer");
			if (!StringUtils.isEmpty(referer)) {
				refererURI = new URI(referer).getPath();
			} else {
				refererURI = null;
			}
		} catch (URISyntaxException e) {
			refererURI = null;
		}

		String currentUrl = request.getServletPath();

		if (currentUrl.indexOf("/", 2) >= 0)
			currentUrl = currentUrl.substring(0, currentUrl.indexOf("/", 2));
		this.model.addAttribute("currentUrl", currentUrl);

	}

	/**
	 * 获取request
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取session
	 * 
	 * @return
	 */
	protected HttpSession getSession() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
	}

	protected ServletContext getServletContext() {
		return ContextLoader.getCurrentWebApplicationContext().getServletContext();
	}

	/**
	 * 取当前登录用户信息
	 * 
	 * @return
	 * 
	 * 
	 * 		public BUser getCurrentUser() { Object currentUser =
	 *         this.getRequest().getSession().getAttribute(Constants.CURRENT_SAAS);
	 * 
	 *         if (null != currentUser) { // return (BUser) currentUser; }
	 * 
	 *         return null; }
	 */
	public String getString(String name) {
		return getString(name, null);
	}

	public String getString(String name, String defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr == null || "".equals(resultStr) || "null".equals(resultStr) || "undefined".equals(resultStr)) {
			return defaultValue;
		} else {
			return resultStr;
		}
	}

	/**
	 * 输出，同时清空outPutMsg
	 * 
	 * @param response
	 * @param result
	 */
	public void outPrint(HttpServletResponse response, Object result) {
		try {
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result.toString());
			getOutputMsg().clear();
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 线程绑定，其内容会在outPrint方法调用后清空
	 * 
	 * @return the outputMsg
	 */
	public Map<String, Object> getOutputMsg() {
		Map<String, Object> output = outPutMsg.get();
		if (output == null) {
			output = new HashMap<String, Object>();
			outPutMsg.set(output);
		}
		return output;
	}

	protected void setOutputMsg(String key, String value) {
		getOutputMsg().put(key, value);
	}

	@SuppressWarnings("rawtypes")
	protected Map<String, String> getParamMap() {
		Map<String, String> parameters = new HashMap<String, String>();
		Map map = getRequest().getParameterMap();
		Set keys = map.keySet();
		for (Object key : keys) {
			parameters.put(key.toString(), getRequest().getParameter(key.toString()));
		}
		return parameters;
	}

	/**
	 * 将参数封装成Map<String,Object>空字符串转为null
	 * 
	 * @return add by hlf 2013-2-10
	 */
	@SuppressWarnings("rawtypes")
	protected Map<String, Object> getParaMap() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		Map map = getRequest().getParameterMap();
		Set keys = map.keySet();
		for (Object key : keys) {
			Object o = getRequest().getParameter(key.toString());
			if (o instanceof String) {
				if ("".equals(o)) {
					o = null;
				}
			}
			parameters.put(key.toString(), o);
		}
		return parameters;
	}

	public int getInt(String name) {
		return getInt(name, 0);
	}

	public int getInt(String name, int defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return Integer.parseInt(resultStr);
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	public BigDecimal getBigDecimal(String name) {
		return getBigDecimal(name, null);
	}

	public BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
		String resultStr = getRequest().getParameter(name);
		if (resultStr != null) {
			try {
				return BigDecimal.valueOf(Double.parseDouble(resultStr));
			} catch (Exception e) {
				return defaultValue;
			}
		}
		return defaultValue;
	}

	/**
	 * 得到IP地址
	 * 
	 * @param request
	 * @return
	 */
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public void putCommonPath() {
		int port = getRequest().getServerPort();
		String scheme = getRequest().getScheme();
		String path = getRequest().getScheme() + "://" + getRequest().getServerName()
				+ ((("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443)) ? "" : ":" + port)
				+ getRequest().getContextPath();
		getSession().setAttribute("base", path);
		getSession().setAttribute("imgBase", path + "/images");
		getSession().setAttribute("imgPrePath", path + "/default/style/images");
		getSession().setAttribute("initImagePath", path + "/default");
	}

	protected String getBasePath() {
		int port = getRequest().getServerPort();
		String scheme = getRequest().getScheme();
		String path = getRequest().getScheme() + "://" + getRequest().getServerName()
				+ ((("http".equals(scheme) && port == 80) || ("https".equals(scheme) && port == 443)) ? "" : ":" + port)
				+ getRequest().getContextPath();
		return path;
	}

	protected boolean isMobileDevice() {
		String qhd = getRequest().getHeader("user-agent");
		if (qhd != null) {
			if (qhd.indexOf("Android") > -1) {
				return true;
			} else if (qhd.indexOf("iPhone") > -1) {
				return true;
			} else if (qhd.indexOf("iPad") > -1) {
				return true;
			} else if (qhd.indexOf("Windows") < 0 && qhd.indexOf("Linux") < 0) {// 根据请求的客户端信息判断
																				// 如果是非PC登录
				return true;
			}
		}
		return false;
	}

}
