<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@include file="/common/import-tags.jsp"%>
<html>
<%@include file="../common/import-static.jsp"%>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width,target-densitydpi=high-dpi,initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
	<meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black" >
    <meta name="format-detection" content="telephone=no">
<title>商品详情</title>
<script>
String.prototype.replaceAll = function(s1,s2){
	return this.replace(new RegExp(s1,"gm"),s2);
}
$(function(){
	$("#roll_cart").css({"height":($(document.body).width()*0.752)});
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
	
	$("#c_spage").click(function(){
		$("#c_showpages").toggle();
	});
});
var spid = "${param.spid}";
$(document).ready(function(){
  	 $.ajax({
           data:{spid:spid},
           url: "${ctx}/spxx/findOneById",//要访问的后台地址
           complete :function(){},
           success: function(data){
              debugger
              var data = eval( "(" + data + ")" );
              if(data.success){
              document.getElementById("sp_name").innerHTML = "";
              document.getElementById("sp_name").innerHTML = data.data.spname;
              
              document.getElementById("sp_chprice").innerHTML = "";
              document.getElementById("sp_chprice").innerHTML = "¥"+data.data.chprice;
              
              document.getElementById("sp_scprice").innerHTML = "";
              document.getElementById("sp_scprice").innerHTML = "¥"+data.data.scprice;
              document.getElementById("imageinfo").innerHTML = "";
              document.getElementById("imageinfo").innerHTML = data.data.imageinfo;
              
              
              document.getElementById("sp_id").value=data.data.scprice.spid;
              }
           }
       });
  	 
  	 $.ajax({
         data:{spid:spid},
         url: "${ctx}/spxx/font",//要访问的后台地址
         complete :function(){},
         success: function(data){ 
            var data = eval( "(" + data + ")" );
            if(data.total>0){
            	var moStr = "<li><img src='"+ctx+"/upload/%_URL_%'/></li>";
            	document.getElementById("roll_cart").innerHTML = "";
            	var roll_cart = $("#roll_cart");
                $.each(data.root,function(i,n){
                	var mo = moStr;
                	mo = mo.replaceAll("%_URL_%",n.url);
                	roll_cart.append($(mo));
                });
            }
         }
     });
   });
</script>
</head>

<body>
<div class="main">
	<div class="head">
    	<div class="backdiv">
        	<a href="javascript:history.back(-1)" onClick="javascript:history.back(-1);"><img src="${ctx}/static/images/det_order_06.png" /></a>
        </div>
        
        <div class="c_title">
        	商品详情
        </div>
    </div>
    
    <!-- 轮播 -->
    <div class="layer">
        <div id="shuffling" class="roll_cart">
        	
        	<div id="rollcar" class="cart">
            	<ul id="roll_cart">
            	    <!--   -->
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
    <!-- 轮播	END -->
    
    <div class="layer2">
    	<div class="c_intro layer">
            <div class="sellhot">
                <div class="layer">
                    <div style="" class="c_pname" id="sp_name">
                        ORIGINS悦木之源 膜法混搭套装 密集 密集滋养 保湿 清透洁净
                    </div>
                    <div class="c_collect">
                        <a href="#"><img src="${ctx}/static/images/det_order_13.png" /><br>
                        收藏</a>
                    </div>
                </div>
                
                <div class="layer">
                    <span id="sp_chprice" class="c_tagp">￥79.9</span><span class="c_tagc">今日促销</span>
                </div>
                
                <div class="layer3">
                    <span id="sp_scprice">￥420.0</span>
                </div>
                
                <div class="line1"></div>
                
                <div class="layer2">
                    <div style="" class="c_tagr">更多服务、优惠、活动信息</div>
                    <div style="text-align:right;" class="r10"><img class="c_gor" src="${ctx}/static/images/det_order_17.png" /></div>
                </div>
                
            </div>
            <div class="line1"></div>
            <div class="layer2">
                <div class="line1"></div>
                <div class="layer5">
                    <div class="c_moonsales"><span>月销量</span><br>153</div>
                    <div class="c_express"><span>江苏南京 至 广州</span><br>快递：10.00</div>
                </div>
            </div>
            <div class="line1"></div>
        
            <div class="layer2">
                <div class="sellhot">
                    <div class="l10">评分</div>
                    <div class="c_gradediv"><img src="${ctx}/static/images/det_order_20.png" /><img src="${ctx}/static/images/det_order_20.png" />
                    <img src="${ctx}/static/images/det_order_20.png" /><img src="${ctx}/static/images/det_order_20.png" />
                    <img src="${ctx}/static/images/det_order_22.png" /> 4.5</div>
                </div>
            </div>
            <div class="line1"></div>
            
            <div class="layer2">
                <div style="text-indent:1em;" class="c_tagr">请选种类购买</div>
                <div class="r10"><img class="c_gor" src="${ctx}/static/images/det_order_17.png" /></div>
            </div>
            <div class="line1"></div>
            <div class="layer5">
            	<table style="" id="c_buttable">
                	<tr>
                        <td><a style="border:1px solid #A8A7A8;border-radius:0.3em;padding:0.4em 0.8em;">我的购物车</a></td>
                        <td><button class="c_addcart">加入购物车</button></td>
                        <td><input class="c_submit" type="submit" value="立即购买" /></td>
                	</tr>
                </table>
            </div>
        
            <div class="layer2">
                <table id="c_pinfo">
                    <tr><td class="thtag">图文详情</td><td>产品参数</td><td>累计评价</td><td>同店推荐</td></tr>
                </table>
                <div class="layer" id="imageinfo">
                	
                </div>
                
                <div class="layer2" style="display:none;">
                    <div style="float:left;width:26%;margin-left:2%;">
                     <a href="#"><img src="${ctx}/static/images/pre.png" /></a></div>
                    <div style="float:left;width:38%;margin-left:3%;padding:0.3em 0;height:20%;">
                        <div class="c_ppinfo">
                        <a id="c_spage"><span style="font-size:1.3em;">1/4</span><img style="margin-top:0em;margin-bottom:-0.3em;" src="${ctx}/static/images/x.png" /></a>
                        </div>
                        <div style="display:none;" id="c_showpages">
                        	<ul><li><a href="#">1/4</a></li><li><a href="#">2/4</a></li><li><a href="#">3/4</a></li><li><a href="#">4/4</a></li></ul>
                        </div>
                    </div>
                    <div style="float:right;width:26%;margin-right:2%;"><a href="#"><img src="${ctx}/static/images/next.png" /></a></div>
                </div>
            </div>
        </div>
        
        <div class="line1"></div>
        <div class="layer2">
            <div class="c_tagb">看了又看</div>
            <div style="text-align:center;color:#E40024;" class="layer">
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/det_order_48.png" /></a>
                    <span>￥79.9</span>
                </div>
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/c_p02.png" /></a>
                    <span>￥79.9</span>
                </div>
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/c_p02.png" /></a>
                    <span>￥79.9</span>
                </div>
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/det_order_48.png" /></a>
                    <span>￥79.9</span>
                </div>
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/c_p02.png" /></a>
                    <span>￥79.9</span>
                </div>
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/c_p02.png" /></a>
                    <span>￥79.9</span>
                </div>
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/det_order_48.png" /></a>
                    <span>￥79.9</span>
                </div>
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/c_p02.png" /></a>
                    <span>￥79.9</span>
                </div>
                <div class="c_lview">
                    <a href="#"><img src="${ctx}/static/images/c_p02.png" /></a>
                    <span>￥79.9</span>
                </div>
            </div>
            
        </div>
    </div>
    
    <div class="c_bottom">
        <div class="layer2 text2">
            <a href="#">登录</a>&nbsp;
            <a href="#">注册</a>
            <span style="float:right;"><a href="#">意见反馈</a></span>
            <span style="float:right;margin-right:0.5em;"><a href="#">服务中心</a></span>
        </div>
        <div class="c_copyright">
            Copyright © 雪蕾化妆品 保留公司所有权利   粤ICP 123456
        </div>
    </div>
    <input type="hidden" id="sp_id" value>
</div>
</body>
</html>
