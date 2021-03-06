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
  </style>
</head>
<body> 
<div class="demoTable" style="margin:20px 10px 10px 10px">
 <div class="layui-form">
  <div class="layui-form-item">
    <div class="layui-inline">
   
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="construct_project_name" placeholder="项目名称">
      </div> 
      <button class="layui-btn" id="btnSearch" data-type="reload" style="margin-left: 10px">搜索</button>
    </div>
  </div>
</div>
 
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="LAY_table_user" lay-filter="demo"></table> 
  </div>   
  
  <!-- 表格查看按钮 -->
<script type="text/html" id="barDemo">
  <div class="layui-btn-group">
  	<a class="layui-btn layui-btn-xs" lay-event="conMaterialList">项目进度计划</a>
 </div>
</script>

<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#LAY_table_user'
         ,url:'proSchConLists.do'
    	 ,cols: [[ //标题栏
           {field: 'construct_project_id', title: '项目编号', width: 80}
           ,{field: 'construct_project_name', title: '项目名称', width: 150}
           ,{field: 'construct_project_addr', title: '工程地址', width: 200}
           ,{field: 'construct_project_leader', title: '项目经理', width: 150}
           ,{field: 'construct_project_leaderTel', title: '项目经理联系方式', width: 150}
           ,{field: 'construct_project_startDate', title: '合同项目开始日期', width: 150 }
           ,{field: 'psn', title: '操作',toolbar:'#barDemo', width:160}
    	 ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650
  });
  
       var $ = layui.$, active = {
		    reload: function(){	
		    	 var construct_project_name = $('#construct_project_name').val();
		    	//执行重载
		      table.reload('testReload', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		         'construct_project_name':construct_project_name
		      
		        }
		        
		      });
		    }
		  };
  
		 //监听工具条 table  lay-filter="demo"
		 table.on('tool(demo)', function(obj){
		   var data = obj.data;
		    //办理任务
		     if(obj.event == 'conMaterialList'){
		    	 location.href = "proSch.do?" + $.param({
						'construct_project_id' : data.construct_project_id, 
						'construct_project_name' : data.construct_project_name
						
					});
		     }
		     });

		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		
		});

	
		

	  	
		
</script>

</body>
</html>