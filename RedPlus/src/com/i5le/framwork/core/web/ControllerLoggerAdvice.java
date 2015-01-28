package com.i5le.framwork.core.web;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.i5le.framwork.core.annotation.LogAnnotation;
import com.i5le.framwork.core.entity.ShiroUser;

public class ControllerLoggerAdvice implements AfterReturningAdvice,
		ThrowsAdvice {

	private final Logger logger;
	private Map<String, LogConf> logConfigMap;
	private int returnBodyMaxLength = 100;// 打印日志里返回的结果最大长度

	private final static Object _LOCK = new Object();

	public ControllerLoggerAdvice(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
		this.logConfigMap = new ConcurrentHashMap<String, LogConf>();
	}

	public ControllerLoggerAdvice() {
		this.logger = LoggerFactory.getLogger(this.getClass().getName());
		this.logConfigMap = new ConcurrentHashMap<String, LogConf>();
	}

	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {

		LogConf conf = this.getLogConf(method, target);
		if (!conf.value)
			return;// 不写日志

		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra)
				.getRequest();

		// 获取访问路径
		String requestURI = request.getRequestURI();
		String contextPath = request.getContextPath();
		String relativPath = requestURI.substring(contextPath.length(),
				requestURI.length());

		StringBuilder logBuffer = new StringBuilder();

		if (conf.wirteAdminUser) {
			ShiroUser user = (ShiroUser) SecurityUtils.getSubject()
					.getPrincipal();
			if (user != null) {
				String userStr = "[ " + user.getId() + ", "
						+ user.getLoginName() + " ]";
				logBuffer.append(userStr);
			} else {
				logBuffer.append("[nologin]");
			}
		}
		logBuffer.append(" |");

		logBuffer.append(request.getMethod());
		logBuffer.append(" |");
		logBuffer.append(relativPath);
		logBuffer.append(" |");

		if (conf.writeParams) {
			// 提交的数据内容，参数间用&隔开
			StringBuilder params = new StringBuilder();
			Map<String, String[]> paramMap = request.getParameterMap();
			if (paramMap != null && paramMap.size() > 0) {
				Set<String> key = paramMap.keySet();
				if (key != null && key.size() > 0) {
					for (Object obj : key) {
						String paramName = obj.toString();
						String paramValue = request.getParameter(paramName);
						if (StringUtils.isNotBlank(paramValue)) {
							params.append(paramName);
							params.append("=");
							params.append(paramValue);
							params.append("&");
						}
					}
					if(params.length()>0 ){
						params.delete(params.length() - 1, params.length());
					}
				}
			}
			logBuffer.append(params);
		}

		logBuffer.append(" |");

		if (conf.writeRespBody) {
			if (returnValue != null) {
				String respBody = returnValue.toString();
				if (respBody.length() > this.returnBodyMaxLength) {
					logBuffer.append(respBody.subSequence(0,
							this.returnBodyMaxLength) + "...");
				} else {
					logBuffer.append(respBody);
				}
			}
		}

		logger.info(logBuffer.toString());
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private LogConf getLogConf(Method method, Object target) {
		String key = method.toGenericString();

		LogConf conf = this.logConfigMap.get(key);
		if (conf != null)
			return conf;

		synchronized (_LOCK) {
			conf = new LogConf();
			Class clazz = target.getClass();
			LogAnnotation clazzAnnotation = null;
			if (clazz.isAnnotationPresent(LogAnnotation.class)) {
				clazzAnnotation = (LogAnnotation) clazz
						.getAnnotation(LogAnnotation.class);
				if (!clazzAnnotation.value()) {
					conf.setValue(false);
				} else {
					conf.setDescribe(clazzAnnotation.describe());
					conf.setWirteAdminUser(clazzAnnotation.writeAdminUser());
					conf.setWriteParams(clazzAnnotation.writeParams());
					conf.setWriteRespBody(clazzAnnotation.writeRespBody());
				}
			}
			LogAnnotation methodAnnotation = null;
			if (conf.isValue()
					&& method.isAnnotationPresent(LogAnnotation.class)) {
				methodAnnotation = (LogAnnotation) method
						.getAnnotation(LogAnnotation.class);
				if (!methodAnnotation.value()) {
					conf.setValue(false);
				} else {
					conf.setDescribe(methodAnnotation.describe());
					conf.setWirteAdminUser(methodAnnotation.writeAdminUser());
					conf.setWriteParams(methodAnnotation.writeParams());
					conf.setWriteRespBody(methodAnnotation.writeRespBody());
				}
			}
			this.logConfigMap.put(key, conf);
			return conf;
		}
	}

	public void afterThrowing(Method method, Object[] args, Object target,
			Exception e) {

	}

	public int getReturnBodyMaxLength() {
		return returnBodyMaxLength;
	}

	public void setReturnBodyMaxLength(int returnBodyMaxLength) {
		this.returnBodyMaxLength = returnBodyMaxLength;
	}

	class LogConf {
		private String describe;// 操作描述
		private boolean value = true;// 是否打印日志
		private boolean writeRespBody = true;// 是否把整个响应结果都打印
		private boolean writeParams = true;// 是否打印参数
		private boolean wirteAdminUser = true;// 是否打印后台管理员信息，会打印user.id和user.loginName

		public String getDescribe() {
			return describe;
		}

		public void setDescribe(String describe) {
			this.describe = describe;
		}

		public boolean isValue() {
			return value;
		}

		public void setValue(boolean value) {
			this.value = value;
		}

		public boolean isWriteRespBody() {
			return writeRespBody;
		}

		public void setWriteRespBody(boolean writeRespBody) {
			this.writeRespBody = writeRespBody;
		}

		public boolean isWriteParams() {
			return writeParams;
		}

		public void setWriteParams(boolean writeParams) {
			this.writeParams = writeParams;
		}

		public boolean isWirteAdminUser() {
			return wirteAdminUser;
		}

		public void setWirteAdminUser(boolean wirteAdminUser) {
			this.wirteAdminUser = wirteAdminUser;
		}

	}

}
