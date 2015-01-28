<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="resource" type="java.lang.String" required="true" description="资源标识" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<shiro:hasPermission name="${resource}:add">
    add = true;
</shiro:hasPermission>
<shiro:hasPermission name="${resource}:update">
    update = true;
</shiro:hasPermission>
<shiro:hasPermission name="${resource}:del">
    del = true;
</shiro:hasPermission>
<shiro:hasPermission name="${resource}:list">
    list = true;
</shiro:hasPermission>
<shiro:hasPermission name="${resource}:distributor">
    distributor = true;
</shiro:hasPermission>
<shiro:hasPermission name="${resource}:update_image">
    update_image = true;
</shiro:hasPermission>


