<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>首页</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	 <script src="<%=request.getContextPath()%>/res/js/jquery-1.4.4.min.js" charset="utf-8"></script>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/res/css/demo.css"  type="text/css" />
  <link rel="stylesheet" href="<%=request.getContextPath()%>/res/css/zTreeStyle.css" type="text/css" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery.ztree.core.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery.ztree.exedit.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery.ztree.excheck.min.js"></script>
 
</head>

<BODY style="background: #FAEBD7">
<h1>选择审核人</h1>
<div>
<div class="content_wrap">
	<div class="zTreeDemoBackground left">
		<ul id="treeDemo" class="ztree"></ul>
	</div>
</div>

<div style="margin: 20px">
<form class="layui-form" action="">
  <div class="layui-form-item">
    <div class="layui-input-block">
      <button class="layui-btn"type="button" onclick="setAuditor()">确定</button>

    </div>
  </div>
</form>
</div>
</div>
</BODY>
<script>

var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
	 
	 });

var setting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "level"
		},
		data: {
			simpleData: {
				enable: true
			}
		}
	};

	var zNodes =${arr};
	var code;	
	var zTreeObj;
	function setCheck() {
		var type = "all";
		setting.check.radioType = type;
		showCode('setting.check.radioType = "' + type + '";');
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	}
	function showCode(str) {
		if (!code) code = $("#code");
		code.empty();
		code.append("<li>"+str+"</li>");
	}
	
	$(document).ready(function(){
		setCheck();		
	  	zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
	  	var nodes = zTreeObj.transformToArray(zTreeObj.getNodes());
        for (var i=0, l=nodes.length; i < l; i++) {
          if (nodes[i].isParent){
        	  zTreeObj.setChkDisabled(nodes[i], true);
          }
        }
		
	});



  function setAuditor(){
	  var checkedNode = zTreeObj.getCheckedNodes();

	var nodeId=checkedNode[0].id;

	if(nodeId==undefined || nodeId==''){
		 layer.msg("未选择审核人", {icon:5, time: 1500,shade:[0.3]});
		 return;
	}else{
		    parent.setUserName(checkedNode); //子页面js事件
		    parent.layer.close(index); //关闭子窗口
	}

	
		
	
  }

</script>

</HTML>