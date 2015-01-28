<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增商品</title>
<link href="${BasePath}/static/artDialog5/skins/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${BasePath}/static/jquery/jquery-1.11.1.min.js"></script>
<script type="text/javascript"  src="${BasePath}/static/artDialog5/artDialog.min.js"></script>
<script type="text/javascript"  src="${BasePath}/static/artDialog5/artDialog.plugins.min.js"></script>
<script type="text/javascript" src="${BasePath}/static/jquery/jquery.form.js"></script>
<script type="text/javascript" src="${BasePath}/static/jquery/jquery-easyui-1.4/jquery.easyui.min.js"></script>
</head>
<body>
    <form id="menuForm"  method="post" >
	<div id="menucontent" style="width: 100%; height: 700px; text-align: center;overflow:auto;" >
	  <input type="hidden" id="funtiontype" value="addLed"/>
	  <input type="hidden" name="id" />
	  <input type="hidden" name="oldCode"  id="oldCode"/>
			<table style="width: 96%;height: 390px;" cellspacing="3" cellpadding="3">
				<tbody>
					<tr>
						<td align="right" style="width: 300px;">商品编号：
							
						</td>
						<td align="left">
						    <input name="commodityNumber" id="commodityNumber" type="text"  />
						</td>
				   </tr>
					<tr>
						<td align="right" style="width: 300px;">商品名称：
							
						</td>
						<td align="left">
						<input name="commodityName" id="commodityName" type="text"  />
						</td>
				   </tr>
					<tr>
						<td align="right" style="width: 300px;">商品状态：
							
						</td>
						<td align="left">
						    <select name="commodityStatus" id="commodityStatus">
						       <option value="1">1</option>
						       <option value="2">2</option>
						       <option value="3">3</option>
						       <option value="4">4</option>
						       <option value="5">5</option>
						    </select>
						</td>
				   </tr>
				   <tr>
						<td align="right" style="width: 300px;">市场价格：
							
						</td>
						<td align="left">
						    <input name="marketPrice" id="marketPrice" type="text"  />
						</td>
				   </tr>
				   <tr>
						<td align="right" style="width: 300px;">销售价格：
							
						</td>
						<td align="left">
						    <input name="sellPrice" id="sellPrice" type="text"  />
						</td>
				   </tr>
				   <tr>
						<td align="right" style="width: 300px;">分销商供货格：
							
						</td>
						<td align="left">
						    <input name="wholesalePrice" id="wholesalePrice" type="text"  />
						</td>
				   </tr>
				   <tr>
						<td align="right" style="width: 300px;">是否赠品：
							
						</td>
						<td align="left">
						     <select name="commodityStatus" id="commodityStatus">
						       <option value="0">是</option>
						       <option value="1">否</option>
						    </select>
						</td>
				   </tr>
				   <tr>
						<td align="right" style="width: 300px;">发布者：
							
						</td>
						<td align="left">
						    <input name="publisher" id="publisher" type="text"  />
						</td>
				   </tr>
				   <tr>
						<td align="right" style="width: 300px;">发布者编号：
							
						</td>
						<td align="left">
						    <input name="publishNumber" id="publishNumber" type="text"  />
						</td>
				   </tr>
				</tbody>
			</table>
			<table width="100%" style="height: 308px; text-align: center;">
			<tbody>
				<tr>
					<td>
					<div style="background: rgb(235, 235, 235); height: 44px; text-align: center;">
                        <input class="button" style="margin-top: 7px;"  type="button" onclick="save()" value="确 定">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <input class="button" style="margin-top: 7px;"  onclick="closewin(false)" type="button" value="取 消">
                    </div>
					</td>
				</tr>
			</tbody>
		</table>
		</div>
		
	</form>
<script  type="text/javascript">
function save(){
	$("#menuForm").ajaxSubmit({
        type: 'post',  
        url:'${BasePath}/commodityManage/addCommodity',
        datatype:'json',
        success: function(data){
        	if(data.mes)
        		closewin(true);
 	    	else 
 	    	   myalert('保存失败','失败');
        },  
        error: function(XmlHttpRequest, textStatus, errorThrown){  
            alert( "error");  
        }
    }); 
}
</script>    
</body>
</html>