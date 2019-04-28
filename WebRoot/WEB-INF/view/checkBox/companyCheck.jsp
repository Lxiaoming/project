<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title></title>
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
<div class="demoTable" style="margin:20px 10px 10px 10px">

</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   




              
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
layui.use('table', function(){
  var table = layui.table;
  
  //展示已知数据
  table.render({
    elem: '#lay_table_user'
    ,cols: [[ //标题栏
	     {field:'company_id', title: 'id', width:150,}
	    ,{field:'company_name', title: '公司名称', width: 150,}
   
    ]]
    ,data:${data}
    //,skin: 'line' //表格风格
    ,even: true
    //,page: true //是否显示分页
    //,limits: [5, 7, 10]
    //,limit: 5 //每页默认显示的数量
  });
  
  

  //监听行单击事件（单击事件为：rowDouble）
  table.on('row(demo)', function(obj){
    var data = obj.data;
 
    parent.setCompany(data); //子页面js事件
    parent.layer.close(index); //关闭子窗口
  
  });
  
});
	

</script>

</body>
</html>