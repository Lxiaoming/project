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
   .layui-table-cell{
   padding:0 5px !important;
   }
  </style>
</head>
<body> 
<div class="demoTable" style="margin:20px 10px 10px 10px">
 <div class="layui-form">
  <div class="layui-form-item">
    <div class="layui-inline">
       <div class="layui-input-inline">
        <input type="text" class="layui-input" name="username" id="username" placeholder="姓名">
      </div> 
      <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>


</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   



<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
  <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
  <a class="layui-btn layui-btn-xs" lay-event="btnEdit">修改</a>
  </div>
</script>
              
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'userListChecks.do'
    	 ,cols: [[ //标题栏
    	         {field:'userid', title:'员工编号',width: 70}  
   	            ,{field: 'username', title: '员工名称', width: 150}
   	            ,{field: 'phone_number', title: '联系方式', width: 100}
   	            ,{field: 'role_name', title: '员工职位'}
   	            ,{field: 'department_name', title: '部门', width: 150,}
   	            ,{field: 'center_name', title: '中心', width: 80}
   	            ,{field: 'company_name', title: '公司', width: 70} 
   	            ,{field:'department_id', title:'部门id'}  
   	           
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	var  username=$('#username').val()
     		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		'username':username
     			    	 } //设定异步数据接口的额外参数
     			    	 
     			      });
    		      }
    		  };
       		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });

		  //监听行单击事件（单击事件为：rowDouble）
		  table.on('row(demo)', function(obj){
		    var data = obj.data;
		    parent.getValue(data); //子页面js事件
		    parent.layer.close(index); //关闭子窗口
		  
		  });
		 /*  $('#transmit').on('click', function(){
			    parent.$('#parentIframe').text('我被改变了');
			    parent.layer.tips('Look here', '#parentIframe', {time: 5000});
			    parent.layer.close(index);
			}); */
       		  
		});

		
</script>

</body>
</html>