package cn.tomoya.interceptor;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.tomoya.common.config.SiteConfig;
import cn.tomoya.module.accesslog.dao.AccessLogDao;
import cn.tomoya.module.accesslog.entity.AccessLog;
import cn.tomoya.util.IpUtil;

import com.alibaba.fastjson.JSON;

/**
 * Created by tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://tomoya.cn
 */
@Component
public class CommonInterceptor implements HandlerInterceptor {

    Logger log = Logger.getLogger(CommonInterceptor.class);
    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);
    @Autowired
    private SiteConfig siteConfig;
    @Autowired
    private AccessLogDao accessLogDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        long start = System.currentTimeMillis();
        request.setAttribute("_start", start);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            ModelMap modelMap = modelAndView.getModelMap();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isAuthenticated = false;
            if (authentication != null) {
                Object o = authentication.getPrincipal();
                if (o instanceof UserDetails) {
                    isAuthenticated = true;
                    modelMap.addAttribute("_principal", ((UserDetails) o).getUsername());
                    modelMap.addAttribute("_roles", ((UserDetails) o).getAuthorities());
                }
            }
            //freemarker全局变量
            modelMap.addAttribute("_isAuthenticated", isAuthenticated);
            modelMap.addAttribute("baseUrl", siteConfig.getBaseUrl());
            modelMap.addAttribute("siteTitle", siteConfig.getName());
            modelMap.addAttribute("sections", siteConfig.getSections());
            modelMap.addAttribute("baseUrlC", siteConfig.getBaseUrlC());
            modelMap.addAttribute("beianName", siteConfig.getBeianName());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long start = (long) request.getAttribute("_start");
        String uri = request.getRequestURI();
        String actionName = request.getRequestURI();
        String clientIp = IpUtil.getIpAddr(request);
        StringBuffer logstring = new StringBuffer();
        logstring.append(clientIp).append("==>").append(uri).append("|").append(actionName).append("|");
        Map<String, String[]> parmas = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> iter = parmas.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, String[]> entry = iter.next();
            logstring.append(entry.getKey());
            logstring.append("=");
            for (String paramString : entry.getValue()) {
                logstring.append(paramString);
            }
            logstring.append("|");
        }
        long executionTime = System.currentTimeMillis() - start;
        logstring.append("excutetime=").append(executionTime).append("ms");
        log.info(logstring.toString());
        insertAccessLog(request, 0L);
    }
    private void insertAccessLog(HttpServletRequest httpRequest, Long timeMs){
		String uri = httpRequest.getRequestURI();
		if(uri.indexOf("/dltb/static/") >= 0){
			return;
		}
		final AccessLog accessLog = new AccessLog();
		accessLog.setAccessDate(new Date());
		accessLog.setCostTime(timeMs.intValue());
		accessLog.setIp(IpUtil.getIpAddr(httpRequest));
//		accessLog.setRemark(remark);
		accessLog.setResUri(uri);
		accessLog.setParam(JSON.toJSONString(httpRequest.getParameterMap()));
		threadPool.submit(new Thread(){
			public void run() {
				accessLogDao.save(accessLog);
			}
		});
		
	}
}
