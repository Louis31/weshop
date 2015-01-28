<%@tag import="java.util.HashMap"%>
<%@ tag language="java" pageEncoding="UTF-8"%>


<%
	HashMap<String, String> cityMap = new HashMap<String, String>() {

		{
			put("0020", "广州市");
			put("0751", "韶关市");
			put("0752", "惠州市");
			put("0753", "梅州市");
			put("0754", "汕头市");
			put("0755", "深圳市");
			put("0756", "珠海市");
			put("0757", "佛山市");
			put("0758", "肇庆市");
			put("0759", "湛江市");
			put("0760", "中山市");
			put("0762", "河源市");
			put("0763", "清远市");
			put("0765", "顺德市");
			put("0766", "云浮市");
			put("0768", "潮州市");
			put("0662", "阳江市");
			put("0663", "茂名市");
			put("0750", "江门市");

		}
	};
%>

<%@attribute name="code" required="true" type="java.lang.String"%>


<%
	String name = cityMap.containsKey(code) ? cityMap.get(code) : "未知";
	out.print(name);
%>


