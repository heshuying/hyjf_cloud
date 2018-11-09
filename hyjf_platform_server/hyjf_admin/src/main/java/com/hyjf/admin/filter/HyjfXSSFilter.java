package com.hyjf.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.springframework.stereotype.Component;

@Component
@WebFilter(filterName = "XSSFilter", urlPatterns = "/*")
public class HyjfXSSFilter implements Filter {

	/** filter配置 */
	FilterConfig filterConfig = null;

	/**
	 * 滤器的过滤方法
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		chain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) request), response);
	}

	/**
	 * 滤器初始化
	 */
	@Override
	public void init(FilterConfig filterConfig) {
		// 过滤器被创建
		this.filterConfig = filterConfig;
	}

	/**
	 * 滤器配置销毁
	 */
	@Override
	public void destroy() {
		// 过滤器被销毁
		this.filterConfig = null;
	}

	/**
	 * 滤器过滤内部类
	 */
	private class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

		// 构造方法
		public XssHttpServletRequestWrapper(HttpServletRequest servletRequest) {
			super(servletRequest);
		}

		/**
		 * 重写获取参数的方法
		 */
		@Override
		public String[] getParameterValues(String parameter) {
			String[] values = super.getParameterValues(parameter);
			if (values == null) {
				return null;
			}
			int count = values.length;
			String[] encodedValues = new String[count];
			for (int i = 0; i < count; i++) {
				encodedValues[i] = cleanXSS(values[i]);
			}
			return encodedValues;
		}

		/**
		 * 重写获取参数值的方法
		 */
		@Override
		public String getParameter(String parameter) {
			String value = super.getParameter(parameter);
			if (value == null) {
				return null;
			}
			value = cleanXSS(value);
			return value;
		}

		/**
		 * 重写相应的请求头获取
		 */
		@Override
		public String getHeader(String name) {
			String value = super.getHeader(name);
			if (value == null)
				return null;
			value = cleanXSS(value);
			return value;
		}

		/**
		 * 替换相应的非法字符
		 * 
		 * @param value
		 * @return
		 */
		private String cleanXSS(String value) {
			/*value = value.replaceAll("<", "").replaceAll(">", "");*/
			/*value = value.replaceAll("&lt;", "").replaceAll("&gt;", "");*/
			/*value = value.replaceAll("& lt;", "").replaceAll("& gt;", "");*/
			/*value = value.replaceAll("\\(", "").replaceAll("\\)", "");*/
			/*value = value.replaceAll("&#40;", "").replaceAll("&#41;", "");*/
			/*value = value.replaceAll("& #40;", "").replaceAll("& #41;", "");*/
			/*value = value.replaceAll("'", "");*/
			/*value = value.replaceAll("&#39;", "");*/
			/*value = value.replaceAll("& #39;", "");*/
			/*value = value.replaceAll("eval\\((.*)\\)", "");*/
			value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "");
			value = value.replaceAll("script", "");
			value = value.replaceAll("<(no)?script[^>]*>.*?</(no)?script>", "");
			value = value.replaceAll("eval\\((.*?)\\)", "");
			value = value.replaceAll("expression\\((.*?)\\)", "");
			value = value.replaceAll("(javascript:|vbscript:|view-source:)*", "");
			/*value = value.replaceAll("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>", "");*/
			value = value.replaceAll("(window\\.location|window\\.|\\.location|document\\.cookie|document\\.|alert\\(.*?\\)|window\\.open\\()*", "");
			value = value.replaceAll(
					"<+\\s*\\w*\\s*(oncontrolselect|oncopy|oncut|ondataavailable|ondatasetchanged|ondatasetcomplete|ondblclick|ondeactivate|ondrag|ondragend"
					+ "|ondragenter|ondragleave|ondragover|ondragstart|ondrop|onerror=|onerroupdate|onfilterchange|onfinish|onfocus|onfocusin|onfocusout|onhelp"
					+ "|onkeydown|onkeypress|onkeyup|onlayoutcomplete|onload|onlosecapture|onmousedown|onmouseenter|onmouseleave|onmousemove|onmousout|onmouseover"
					+ "|onmouseup|onmousewheel|onmove|onmoveend|onmovestart|onabort|onactivate|onafterprint|onafterupdate|onbefore|onbeforeactivate|onbeforecopy"
					+ "|onbeforecut|onbeforedeactivate|onbeforeeditocus|onbeforepaste|onbeforeprint|onbeforeunload|onbeforeupdate|onblur|onbounce|oncellchange"
					+ "|onchange|onclick|oncontextmenu|onpaste|onpropertychange|onreadystatechange|onreset|onresize|onresizend|onresizestart|onrowenter|onrowexit"
					+ "|onrowsdelete|onrowsinserted|onscroll|onselect|onselectionchange|onselectstart|onstart|onstop|onsubmit|onunload)+\\s*=+",
					"");
			return value;
		}
	}
}
