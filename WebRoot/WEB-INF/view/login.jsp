<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"> 
<html>
<head>
<meta charset="utf-8">
  <title>诚安时代猪猴管理系统</title>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="description" content="particles.js is a lightweight JavaScript library for creating particles.">
  <meta name="author" content="Vincent Garreau" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="shortcut icon" href="<%=request.getContextPath()%>/res/login/img/favicon.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
<%-- 	<script src="<%=request.getContextPath()%>/res/layui/jquery-3.3.1.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script> --%>
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
  <link rel="stylesheet" media="screen" href="<%=request.getContextPath()%>/res/login/css/style.css">
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/res/login/css/reset.css"/>
</head>
<body>

<div id="particles-js">
	<form>
		<div class="login">
			<div class="logo">
				<img src="<%=request.getContextPath()%>/res/login/img/logo.png" alt="">
				<p>诚安时代猪猴管理系统</p>
			</div>
			<!-- <div class="login-top">
				登录
			</div> -->
			<div class="login-center clearfix">
				<div class="login-center-img"><img src="<%=request.getContextPath()%>/res/login/img/name.png"/></div>
				<div class="login-center-input">
					<input id="username" type="text" name="" value="" placeholder="请输入您的用户名" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的用户名'"/>
					<div class="login-center-input-text">用户名</div>
				</div>
			</div>
			<div class="login-center clearfix">
				<div class="login-center-img"><img src="<%=request.getContextPath()%>/res/login/img/password.png"/></div>
				<div class="login-center-input">
					<input id="password" type="password" name="" value="" placeholder="请输入您的密码" onfocus="this.placeholder=''" onblur="this.placeholder='请输入您的密码'"/>
					<div class="login-center-input-text">密码</div>
				</div>
			</div>
			<div class="login-center clearfix" style="margin-bottom:0;">
				<div class="login-center-input login-center-input1">
				<label for="j_captcha1" class="t">验证码：</label>
					 <input id="j_captcha" name="j_captcha" type="text" class="form-control x164 in">
					<img id="captcha_img" onclick="changeVerifyCode()"  alt="点击更换" title="点击更换" src="<%=request.getContextPath()%>/plugins/verifyCode.do?w=100&h=30&k=admin_code" class="m">
					<c:if test="${not empty sessionScope.error}">
					<label style="color: red;from-size:18px" for="j_captcha2" class="t">${sessionScope.error}</label>
					</c:if>
					</div>
			</div>
			<div class="login-button" style="margin-top:20px;">
				登录
			</div>
			<!-- <input type="reset" value="&nbsp;重&nbsp;置&nbsp;" class="login-button-v"> -->
		</div>
		<div class="sk-rotating-plane"></div>
	</form>
</div>

<!-- scripts -->
<script src="<%=request.getContextPath()%>/res/login/js/particles.min.js"></script>
<script src="<%=request.getContextPath()%>/res/layui/jquery.min.js"></script>

<script src="<%=request.getContextPath()%>/res/login/js/app.js"></script>
<script type="text/javascript">

var DEFAULT_VERSION = 8.0;  
var ua = navigator.userAgent.toLowerCase();  
var isIE = ua.indexOf("msie")>-1;  
var safariVersion;  
if(isIE){  
safariVersion = ua.match(/msie ([\d.]+)/)[1];  
}  
if(safariVersion <= DEFAULT_VERSION ){  
  alert('系统检测到您正在使用ie8以下内核的浏览器，不能实现完美体验，请使用360浏览器或升级IE浏览器访问！')
}; 


layui.use(['layer'], function(){
	 var layer = layui.layer;
});
	function hasClass(elem, cls) {
	  cls = cls || '';
	  if (cls.replace(/\s/g, '').length == 0) return false; //当cls没有参数时，返回false
	  return new RegExp(' ' + cls + ' ').test(' ' + elem.className + ' ');
	}
	 
	function addClass(ele, cls) {
	  if (!hasClass(ele, cls)) {
	    ele.className = ele.className == '' ? cls : ele.className + ' ' + cls;
	  }
	}
	 
	function removeClass(ele, cls) {
	  if (hasClass(ele, cls)) {
	    var newClass = ' ' + ele.className.replace(/[\t\r\n]/g, '') + ' ';
	    while (newClass.indexOf(' ' + cls + ' ') >= 0) {
	      newClass = newClass.replace(' ' + cls + ' ', ' ');
	    }
	    ele.className = newClass.replace(/^\s+|\s+$/g, '');
	  }
	}
		document.querySelector(".login-button").onclick = function(){
			addClass(document.querySelector(".login"), "active")
			setTimeout(function(){
				addClass(document.querySelector(".sk-rotating-plane"), "active")
				document.querySelector(".login").style.display = "none"
			},1000)
			setTimeout(function(){
				onlogin();
			},1500)
		}
		//取消加载方法
		function Timelodout () {
			removeClass(document.querySelector(".login"), "active")
				removeClass(document.querySelector(".sk-rotating-plane"), "active")
				document.querySelector(".login").style.display = "block"
		}
		
		
		
		function changeVerifyCode() {
			$("#captcha_img").attr(
					"src",
					'<%=request.getContextPath()%>/plugins/verifyCode.do?w=100&h=30&k=admin_code&t='
							+ new Date().getTime());
		}
		
		document.onkeydown = function(e){
	        if(!e) e = window.event;//火狐中是 window.event
	        if((e.keyCode || e.which) == 13){
	        	document.querySelector(".login-button").onclick();
	        }
        };
	 	
		function onlogin(){
			var username=$("#username").val();
		   	var paw=$("#password").val();
		   	var j_captcha=$("#j_captcha").val();
		    
			$.ajax({
				type : 'GET',
				url :'Logins.do',
				data : {'username':username,
				        'password':paw,
				        'j_captcha':j_captcha
				},
				
				success : function(data){
					if(data!=""){
						Timelodout();
						var json = eval('(' + data + ')'); 
						var msg=json.ver_msg
						if(msg!=null){
							layer.msg(msg, {time:1000,icon: 5,shade:0.3});
							return false;
						}else{
							layer.msg(json.us_msg, {time:1000,icon: 5,shade:0.3});
							return false;
						}
					}else{
						layer.msg('登陆成功', {time:1000,icon: 6,shade:0.3},function(){
							location.href="admin/index.do";
						});
					
					}
				}
	   		});
		
		}
		
</script>
</body>
</html>