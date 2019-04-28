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
        <input type="text" class="layui-input" name="role_name" id="role_name" placeholder="职位名">
      </div> 
   
      <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-group">
    <button class="layui-btn layui-btn-sm" lay-event="addUser">新增职位</button>
  </div>
</script>
<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
  <a class="layui-btn layui-btn-xs" lay-event="btnEdit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="btnDele">删除</a>
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
    elem: '#lay_table_user'
         ,url:'roleLists.do'
        ,toolbar: '#toolbarDemo'
    	 ,cols: [[ //标题栏
    	         {field:'role_id', title:'职位编号',width: 70}  
   	            ,{field: 'role_name', title: '职位名', width: 150}
   	            ,{field: 'state', title: '状态', width: 70,templet:'<div>{{status_formatter(d.state)}}</div>'} 
   	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:90}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
       //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         if(obj.event === 'btnEdit'){
        		location.href = "editRole.do?" + $.param({
					'cid' : data.role_id}); //获取用户id	location.href = "sealNew.do?own_seal_id="+data.own_seal_id+"";
         }else if(obj.event === 'btnDele'){
        	 layer.confirm('确定删除吗？', {
       		  btn: ['确定', '取消'] //可以无限个按钮
       		 ,btn1: function(index, layero){
 			$.ajax({
				type : 'POST',
				url : 'deleRole.do',
				data : {
					'roleId' : data.role_id
				  },
				success : function(data) {
					if (data.Success) {
						 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
                            location.reload();
                        });
					} else {
						 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
					}
				}
			});
 			}});
           }
       });
     
       //头工具栏事件
       table.on('toolbar(demo)', function(obj){
         var checkStatus = table.checkStatus(obj.config.id);
         switch(obj.event){
           case 'addUser':
        	   location.href = "editRole.do";
           break;
           case 'getCheckLength':
             var data = checkStatus.data;
             layer.msg('选中了：'+ data.length + ' 个');
           break;
        
         };
       });
     
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	   var role_name=$('#role_name').val();
    		    	
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'role_name':role_name
  
     			    	 } //设定异步数据接口的额外参数
     			    	 
     			      });
    		      }
    		  };
       		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });

		});
	
		//格式化状态
		function status_formatter(value) {
			if (value == 1) {
				return  '<span style="color: red;">启用</span>';
			} else if (value == 2) {
				return "禁用";

			}
		}


		
</script>

</body>
</html>