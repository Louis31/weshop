<!DOCTYPE html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<%@include file="./common/import-tags.jsp"%>
<title><spring:eval expression="@webConf['admin.title']" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="./common/import-static.jsp"%>
<style type="text/css">
body {
	background: #eee
}

#loginForm {
	
}

.loginBox {
	background: -moz-linear-gradient(center top, #FFFFFF, #EFEFEF 8%) repeat
		scroll 0 0 rgba(0, 0, 0, 0);
	border: 1px solid #FFFFFF;
	border-radius: 8px;
	box-shadow: 0 0 15px #222222;
	color: #000000;
	font: 11.5px/1.5em 'Microsoft YaHei';
	height: 280px;
	left: 50%;
	margin-left: -210px;
	margin-top: -165px;
	padding: 0 0px;
	position: absolute;
	top: 50%;
	width: 420px;
	background: #ffffff;
	border-color: #acd5ff;
	/* 	background-color: #e6f2ff; */
}

input,textarea {
	-webkit-transition: all 0.30s ease-in-out;
	-moz-transition: all 0.30s ease-in-out;
	-ms-transition: all 0.30s ease-in-out;
	-o-transition: all 0.30s ease-in-out;
	outline: none;
	padding: 3px 0px 3px 3px;
	margin: 5px 1px 3px 0px;
	border: 1px solid #DDDDDD;
	height: 24px;
	inline-height: 24px;
	border-radius: 5px; /* css  */
	-moz-border-radius: 5px; /* mozilla */
	-webkit-border-radius: 5px; /* webkit */
}

#submitCode {
	width: 40px;
}

#loginForm {
	padding-left: 24px;
}

.tips {
	padding: 3px;
	border: 1px solid;
	border-color: #e5e5e5;
	-webkit-border-radius: 3px;
	-moz-border-radius: 3px;
	border-radius: 3px;
	background-color: #fafafa;
}

.tips-warning {
	border-color: #ffb2b2;
	background-color: #fff3f3;
}
</style>
</head>
<body>
	<div class="loginBox ">
		<fieldset>
			<legend style="text-align: center">用户登录</legend>
			<form class="form-horizontal" id="loginForm" 
				action="${ctx }/admin/loginForm" method="post">
				
				<div class="row">
					<div class="control-group ">
						<label class="control-label">用户名：</label>
						<div class="controls">
							<input type="text" data-rules="{required : true}"
								value="${loginName}" data-messages="{required:'用户名不能为空'}"
								name="username" data-tip="{text:'请输入用户名'}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="control-group ">
						<label class="control-label">密 码：</label>
						<div class="controls">
							<input type="password" data-rules="{required : true}"
								data-messages="{required:'密码不能为空'}" name="password"
								data-tip="{text:'请输入密码'}">
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="control-group">
						<div class="controls">
							<label class="checkbox"><input name="rememberMe"
								type="checkbox" value="true" />记住我</label>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="offset3 ">
						<button class="button button-primary" id="loginBtn" type='submit'>登录</button>
						&nbsp;
						<button class="button" type="reset">重置</button>
					</div>
				</div>
				<div id="errorDiv" style="display: none">
					<span class="tips tips-warning tips-small"></span>
				</div>
			</form>
		</fieldset>

	</div>

</body>
	<script type="text/javascript">
		BUI.use([ 'bui/form', 'bui/tooltip' ], function(Form, Tooltip) {
			var loginForm = new Form.HForm({
				srcNode : '#loginForm',
				submitType : 'ajax',
				action : ctx + '/admin/loginForm',
				method : 'post',
				callback : function(data) {
					if (data.success) {
						location.href = ctx + '/admin/home';
					} else {
						$('#errorDiv span').text(data.msg);
						$('#errorDiv').show();
						changeCode();
						hide();
					}
				}
			}).render();
			
			$('#code').click(function(){
				changeCode();
			});
			
			function changeCode(){
				var time = new Date().getTime();
				$('#code').attr('src','${ctx}/kaptcha?_t='+time);
			}
		});
		function hide() {
			setTimeout(function() {
				$('#errorDiv span').text('');
				$('#errorDiv').hide();
			}, 3000);
		}
		
		if (window != top) {
			top.location.href = location.href;
		}
	</script>
</html>
