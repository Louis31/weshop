<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="../common/import-tags.jsp"%>
<%@include file="../common/import-static.jsp"%>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,target-densitydpi=high-dpi,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black" >
    <meta name="format-detection" content="telephone=no">
<title>首页</title>
<script>
String.prototype.replaceAll = function(s1,s2){
return this.replace(new RegExp(s1,"gm"),s2);
}
$(function(){
	
	
	$("#roll_cart").css({"height":($(document.body).width()*0.51)});
	$("#roll_cart li").css({"width":$(document.body).width(),"height":$("#roll_cart").height()});
	//alert($("#roll_cart li").width());
	$("#shuffling").css("height",$("#roll_cart").height());
	$("#rollcar").css({"height":$("#roll_cart").height()});
	
	var current = 0;
	function f(){
		if(++current >4){
			current = 0;
		} 
		$(".current").removeClass("current");
		$(".tag li").eq(current).addClass("current");
		$("#roll_cart").animate({left:-$("#roll_cart li").width()},1000,'linear',function(){
			//alert($("#roll_cart")[0].scrollLeft);
			if( $("#roll_cart")[0].scrollLeft >= 0 ){
				$("#roll_cart li:first").insertAfter($("#roll_cart li:last"));
				$("#roll_cart").css({left:0});
			}
			
		});
	}
	var start = setInterval(f,4000);
	
	$("#roll_cart").onmouseover=function() {
		clearInterval(start);
	};
	$("#roll_cart").onmouseout=function() {
		start=setInterval(f,4000);
	};
});
var itemClass = "<div class='sellhot'></div>"
var divLayer = "<div class='layer'><div class='l17'><a href='lonkoom/list.jsp?lbid=%LDID%'><img src='${ctx}/static/images/tag_CLAName.png' /></a></div>"+
           "<div class='i_slidt_m'><a><img src='${ctx}/static/images/CLAName_01.png' /></a></div>"+
           "<div class='r39'><a><img src='${ctx}/static/images/CLAName_02.png' /></a></div></div>";

var divLayer2 = "<div class='layer2'></div>";
 $(document).ready(function(){
	 $.ajax({
         dataType: "json",//返回json格式的数据
         url: "${ctx}/splb/findSplbTree",//要访问的后台地址
         complete :function(){},
         success: function(data){
           debugger
           $.each(data, function(i, n){
        	   itemClass = $("<div class='layer2'></div>");
        	   var strRel = "cz";
        	   switch(n.text){
        	   case "彩妆":
        		   strRel = "cz";
        		   break;
        	   case "香水":
        		   strRel = "xs";
        		   break;
        	   case "护肤":
        		   strRel = "hf";
        		   break;
        	   case "面膜":
        		   strRel = "mm";
        		   break;
        	   case "彩妆工具":
        		   strRel = "czgj";
        		   break;
        	   default:
        		   strRel = null;
        	   }
        	   if(strRel!=null){
        	   var strdivLayer = divLayer;
        	   strdivLayer = strdivLayer.replaceAll("CLAName",strRel);
        	   strdivLayer = strdivLayer.replaceAll("%LDID%",n.id);
        	   var divLayerobj = $(strdivLayer);
        	   $(itemClass).append(divLayerobj);
        	   divLayer2 = $("<div class='layer2'></div>");
        	   if(n.children.length>0){
        		   $.each(n.children,function(ic,nc){
        			   $(divLayer2).append("<a class='czlink' href='lonkoom/list.jsp?lbid="+nc.id+"'  dataIndex='"+nc.id+"'>"+nc.text+"</a>&nbsp;&nbsp;");
        		   });
        	   }
        	   $(itemClass).append(divLayer2);
        	   $("#container").append(itemClass);
        	   }
           });
         }
 });
 });
</script>
</head>

<body>

<div class="main">
	<div class="head">
    	<div class="logo_div"><img src="${ctx}/static/images/logo.png" /></div>
        <div class="search_div">
        	<form id="myform" action="#">
        	<div class="simg">
            <img onClick="javascript:document.getElementById('myform').submit()" src="${ctx}/static/images/search.png" />
            </div>
            <div class="stext">
            	<input type="text" placeholder="搜索宝贝" />
            </div>
            </form>
        </div>
        
        <div class="headland">
        	<div style="" class="hlandimg_div"><img src="${ctx}/static/images/landbac.png" /></div>
            <div class="land">深圳</div>
        </div>
        
    </div>
    
	<div class="layer">
    	<div id="shuffling" class="roll_cart">
        	
        	<div id="rollcar" class="cart">
            	<ul id="roll_cart">
        			<li><img src="${ctx}/static/images/01.png" /></li>
        			<li><img src="${ctx}/static/images/01.png" /></li>
        			<li><img src="${ctx}/static/images/01.png" /></li>
        			<li><img src="${ctx}/static/images/01.png" /></li>
        			<li><img src="${ctx}/static/images/01.png" /></li>
				</ul> 
			</div>
           
        </div>
        
        <div class="roll_tag">
            <ul class="tag">
                <li class="current"></li>
                <li></li>
                <li></li>
                <li></li>
                <li></li>
            </ul>
        </div>
        
    </div>
    
    <div class="layer">
    	<a href="lonkoom/hot.html" class="l25"><img src="${ctx}/static/images/hot.png" /></a>
        <a  class="l25"><img src="${ctx}/static/images/collect.png" /></a>
        <a class="l25"><img src="${ctx}/static/images/qi.png" /></a>
        <a class="l25"><img src="${ctx}/static/images/add.png" /></a>
    </div>
    
    <div class="interim">
    
    </div>
    
    <div class="layer2">
    	<div class="sellhot i_plist">
        	<div style="width:101%;" class="layer">
            	<div style="padding-bottom:0.5em;" class="shotpl">
                	<div class="btag1">今日热销</div>
                    <div class="layer">
                    	<div><img src="${ctx}/static/images/p_1.png" /></div>
                        <div class="pname b_dashed"><a href="lonkoom/content.html" class="i_hotpname">美丽容颜护肤水一切变得如此美丽动人</a></div>
                        <div style="padding-top:0.3em;" class="layer"><span class="price1">￥199.9</span><a href="content.html" class="rushbuy" >立即抢购</a></div>
                    </div>
                </div>
                <div style="padding-bottom:0.5em;" class="shotpr">
                	<div class="btag1">&nbsp;</div>
                    <div class="layer">
                    	<div><img src="${ctx}/static/images/p_2.png" /></div>
                        <div class="pname b_dashed"><a href="lonkoom/content.html" class="i_hotpname">美加净护手霜防护型净含量75克</a></div>
                        <div style="padding-top:0.3em;" class="layer"><span class="price1">￥79.9</span><a href="content.html" class="rushbuy" >立即抢购</a></div>
                    </div>
                </div>
            </div>
            <div class="line1"></div>
            
            <div style="width:101%;" class="layer">
            	<div class="shotpl">
                	<div class="layer2">
                    	<div><a href="lonkoom/content.html"><img src="${ctx}/static/images/p_3.png" /></a></div>
                        <div class="pname b_dashed"><a href="lonkoom/content.html" class="i_hotpname">美肤宝护手霜滋润保湿美白防冻裂那女润手霜</a></div>
                        <div style="padding-top:0.3em;" class="layer"><span class="price1">￥19.8</span><a href="content.html" class="rushbuy" >立即抢购</a></div>
                    </div>
                </div>
                <div class="shotpr">
                	<div class="layer2">
                    	<div><a href="lonkoom/content.html"><img src="${ctx}/static/images/p_4.png" /></a></div>
                        <div class="pname b_dashed"><a href="lonkoom/content.html" class="i_hotpname">美加净护手霜防护型净含量75克</a></div>
                        <div style="padding-top:0.3em;" class="layer"><span class="price1">￥39.9</span><a href="content.html" class="rushbuy" >立即抢购</a></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="layer2 i_plist">
    	<div class="line1"></div>
        <div id="container" class="layer2">
        <!--  
        	<div class="sellhot">
            	<div class="layer">
                    <div class="l17">
                        <a><img src="images/tag_cz.png" /></a>
                    </div>
                    
                    <div class="i_slidt_m">
                        <a><img src="images/cz_01.png" /></a>
                    </div>
                    
                    <div class="r39">
                        <a><img src="images/cz_02.png" /></a>
                    </div>
                </div>
                
                <div class="layer2">
                	<a class="czlink">护肤品</a>&nbsp;&nbsp;
                    <a class="czlink">护肤</a>&nbsp;&nbsp;
                    <a class="czlink">护品阿</a>&nbsp;&nbsp;
                    <a class="czlink">是护肤品</a>
                </div>
            </div>
            
        	<div class="sellhot">
            	<div class="layer2">
                    <div class="l17">
                        <a><img src="images/tag_xs.png" /></a>
                    </div>
                    
                    <div class="i_slidt_m">
                        <a href="content.html" ><img src="images/xs_01.png" /></a>
                    </div>
                    
                    <div class="r39">
                        <a href="content.html"><img src="images/xs_02.png" /></a>
                    </div>
                </div>
                
                <div class="layer2">
                	<a class="xslink">护肤品</a>&nbsp;&nbsp;
                    <a class="xslink">高级化妆品</a>&nbsp;&nbsp;
                    <a class="xslink">高级护肤品</a>
                </div>
            </div>
            
        	<div class="sellhot">
            	<div class="layer2">
                    <div class="l17">
                        <a><img src="images/tag_hf.png" /></a>
                    </div>
                    
                    <div class="i_slidt_m">
                        <a href="content.html"><img src="images/hf_01.png" /></a>
                    </div>
                    
                    <div class="r39">
                        <a href="content.html"><img src="images/hf_02.png" /></a>
                    </div>
                </div>
                
                <div class="layer2">
                	<a class="hflink">护肤品</a>&nbsp;&nbsp;
                    <a class="hflink">高级护肤品XX</a>&nbsp;&nbsp;
                    <a class="hflink">护肤品</a>&nbsp;&nbsp;
                    <a class="hflink">护肤</a>
                </div>
            </div>
            
        	<div class="sellhot">
            	<div class="layer2">
                    <div class="l17">
                        <a><img src="images/tag_mm.png" /></a>
                    </div>
                    
                    <div class="i_slidt_m">
                        <a><img src="images/mm_01.png" /></a>
                    </div>
                    
                    <div class="r39">
                        <a><img src="images/mm_02.png" /></a>
                    </div>
                </div>
                
                <div class="layer2">
                	<a class="mmlink">护肤品</a>&nbsp;&nbsp;
                    <a class="mmlink">高级护肤品XX</a>&nbsp;&nbsp;
                    <a class="mmlink">护肤品</a>&nbsp;&nbsp;
                    <a class="mmlink">护肤</a>
                </div>
            </div>
            
        	<div class="sellhot">
            	<div class="layer2">
                    <div class="l17">
                        <a><img src="images/tag_czgj.png" /></a>
                    </div>
                    
                    <div class="i_slidt_m">
                        <a><img src="images/czgj_01.png" /></a>
                    </div>
                    
                    <div class="r39">
                        <a><img src="images/czgj_02.png" /></a>
                    </div>
                </div>
                
                <div class="layer2">
                	<a class="czgjlink">护肤品</a>&nbsp;&nbsp;
                    <a class="czgjlink">高级护肤品XX</a>&nbsp;&nbsp;
                    <a class="czgjlink">护肤品</a>&nbsp;&nbsp;
                    <a class="czgjlink">护肤</a>
                </div>
            </div>-->
            
        </div>
    </div>
    <!-- 底部 -->
    <div class="layer_bottom">
    	<div class="l20"><img src="${ctx}/static/images/home.png" /></div>
        <div class="l20"><a href="lonkoom/goods_class.html"><img src="${ctx}/static/images/sort.png" /></a></div>
        <div class="l20"><img src="${ctx}/static/images/cart.png" /></div>
        <div class="l20"><img src="${ctx}/static/images/find.png" /></div>
        <div class="l20"><a href="lonkoom/agent.html"><img src="${ctx}/static/images/mymanage.png" /></a></div>
    </div>
    <!-- 底部	END-->
</div>

</body>
</html>
