/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.55
 * Generated at: 2015-01-25 00:57:46 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html; charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');

	session.removeAttribute("userInfo");

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<title></title>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\t\tfunction check(){\r\n");
      out.write("\t\t\tvar logincode = document.getElementById(\"logincode\").value;\r\n");
      out.write("\t\t\tvar password = document.getElementById(\"password\").value;\r\n");
      out.write("\t\t\tvar error = document.getElementById(\"error\");\r\n");
      out.write("\t\t\terror.innerHTML=\"\";\r\n");
      out.write("\t\t\tif(logincode==\"\"){\r\n");
      out.write("\t\t\t\terror.innerHTML=\"用户名不能为空！\";\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\tif(password==\"\"){\r\n");
      out.write("\t\t\t\terror.innerHTML=\"密码不能为空！\";\r\n");
      out.write("\t\t\t\treturn false;\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t\treturn true;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t</script>\r\n");
      out.write("\t<style type=\"text/css\">\r\n");
      out.write("\t\t* { margin:0 auto; padding:0; border:0;font-size:12px;}\r\n");
      out.write("\t\tbody { background:#0462A5; font:12px \"宋体\"; color:#004C7E;}\r\n");
      out.write("\t\tinput { border:1px solid #004C7E;}\r\n");
      out.write("\t\t.main { background:url(static/img/login/bg.jpg) repeat-x;}\r\n");
      out.write("\t\t.login { background:#DDF1FE; width:468px; height:262px; border:1px solid #000;}\r\n");
      out.write("\t\t.top { background:url(static/img/login/login_bg.jpg) repeat-x; width:464px; height:113px; border:1px solid #2376B1; margin-top:1px;}\r\n");
      out.write("\t\t.logo { background:url(static/img/login/logo.gif) no-repeat; width:150px; height:42px; float:left; margin:29px 0 0 14px;}\r\n");
      out.write("\t\t.lable { background:url(static/img/login/lable.gif) no-repeat; width:157px; height:32px; float:right; margin:81px 31px 0 0;}\r\n");
      out.write("\t\t.submit { background:url(static/img/login/submit.gif) no-repeat; cursor:hand; width:71px; height:24px; border:0;} \r\n");
      out.write("\t\t.reset { background:url(static/img/login/reset.gif) no-repeat; cursor:hand; width:71px; height:24px; border:0;} \r\n");
      out.write("\t</style>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("<div>\r\n");
      out.write("<table width=\"100%\" height=\"100%\" class=\"main\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("  <tr>\r\n");
      out.write("    <td align=\"center\">\r\n");
      out.write("      <div class=\"login\">\r\n");
      out.write("        <div class=\"top\">\r\n");
      out.write("          <div class=\"logo\"></div>\r\n");
      out.write("        </div>\r\n");
      out.write("        <table width=\"466\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td style=\"padding-top:30px;\">\r\n");
      out.write("\t\t    <form action=\"user/login\" method=\"post\">\r\n");
      out.write("              <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\">\r\n");
      out.write("                <tr>\r\n");
      out.write("                  <td align=\"right\" height=\"27\">用户名:</td>\r\n");
      out.write("                  <td align=\"right\" width=\"161\">\r\n");
      out.write("                    <input type=\"text\" name=\"logincode\" style=\"width:150px;height:20px\" />\r\n");
      out.write("                  </td>\r\n");
      out.write("                  <td align=\"center\" height=\"29\">\r\n");
      out.write("                    <input name=\"submit\" type=\"submit\" value=\"\" onclick=\"return check()\" class=\"submit\" />\r\n");
      out.write("                  </td>\r\n");
      out.write("                </tr>\r\n");
      out.write("                <tr>\r\n");
      out.write("                  <td align=\"right\" height=\"27\">密&nbsp;&nbsp;码:</td>\r\n");
      out.write("                  <td align=\"right\" width=\"161\">\r\n");
      out.write("                    <input type=\"password\" name=\"password\" style=\"width:150px;height:20px\" />\r\n");
      out.write("                  </td>\r\n");
      out.write("                  <td align=\"center\" height=\"29\">\r\n");
      out.write("                    <input name=\"reset\" type=\"reset\" value=\"\" class=\"reset\" />\r\n");
      out.write("                  </td>\r\n");
      out.write("                </tr>\r\n");
      out.write("              </table>\r\n");
      out.write("\t        </form>\r\n");
      out.write("            </td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("        <table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin-top:8px;\">\r\n");
      out.write("          <tr>\r\n");
      out.write("          \t<td align=\"center\" style=\"height:25\"><font id=\"error\" color=\"red\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${error}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("&nbsp;</font></td>\r\n");
      out.write("          </tr>\r\n");
      out.write("          <tr>\r\n");
      out.write("            <td align=\"center\">雪蕾微商城 2015</td>\r\n");
      out.write("          </tr>\r\n");
      out.write("        </table>\r\n");
      out.write("      </div>\r\n");
      out.write("      <!--login -->\r\n");
      out.write("    </td>\r\n");
      out.write("  </tr>\r\n");
      out.write("</table>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
