<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>部门选择框</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
     <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="${CASD_PATH}/res/layui/format/dateformat.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/res/layui/jquery.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
  <style type="text/css">
 .layui-table td, .layui-table th{
      font-size: 13px !important;
      color: #3a3a3a;
  }
  /*设置隔行颜色  */
   table tr:nth-child(2n){
   background-color:#FFF0F5;
   }
  .layui-input, .layui-select, .layui-textarea{
      height: 35px !important;
  }
   .layui-table-cell .layui-form-checkbox[lay-skin=primary]{
       top: 1px !important;
   }
  </style>
</head>
<body> 
<div class="demoTable">
  部门：
  <div class="layui-inline">
    <input class="layui-input" name="department_name" id="department_name" autocomplete="off">
  </div>
  <button class="layui-btn" data-type="reload">搜索</button>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   

<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
layui.use('table', function(){
  var table = layui.table
  ,$ = layui.$ 
  ,laypage = layui.laypage
  ,layer = layui.layer
  ,laydate = layui.laydate;
  //展示已知数据
      table.render({
    elem: '#lay_table_user'
         ,url:'orgChecks.do'
    	 ,cols: [[ //标题栏
    	           {field:'company_name', title: '公司', width:150,}
    	   	    ,{field:'center_name', title: '中心', width: 150,}
    	   	    ,{field:'department_name', title: '部门', width: 150,}
                ]]
    ,id:'department_list'
    ,page: true
    ,limit:15
    ,height: 750
  });
  

  //监听行单击事件（单击事件为：rowDouble）
  table.on('rowDouble(demo)', function(obj){
    var data = obj.data;
 
    parent.setCompany(data); //子页面js事件
    parent.layer.close(index); //关闭子窗口
  
  });
  
  
  
 	var $ = layui.$, active = {
	    reload: function(){
	      //执行重载
	      table.reload('department_list', {
	        page: {
	          curr: 1 //重新从第 1 页开始
	        }
	        ,where: {
	        	department_name: $("#department_name").val()
	        }
	      });
	    }
	  };
  
  	  $('.demoTable .layui-btn').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	  });
});
	

</script>

</body>
</html>