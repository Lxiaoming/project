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
        <input type="text" class="layui-input"  id="supOpinion_company" placeholder="公司">
      </div> 
       <div class="layui-input-inline">
        <input type="text" class="layui-input"  id=supOpinion_title placeholder="月份">
      </div> 
     
      <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>
   <div class="layui-btn-group demoTable">
     <button class="layui-btn layui-btn-sm" data-type="btnAdd">新增</button>
   </div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   

<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
  <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
  </div>
</script>      
<script>

layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'supOpinionLists.do'
    	 ,cols: [[ //标题栏
    	          //,{field: 'notice_id', title:'ID',hidden:'hidden',width: 80}
	             {field: 'supOpinion_company', title: '被监察公司', width: 100}
	            ,{field: 'supOpinion_title', title: '月份', width: 110}
	            ,{field: 'supOpinion_creatDate', title: '创建时间', width: 140,templet:'<div>{{start_time(d.supOpinion_creatDate.time)}}</div>'} 
	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:100}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:12
    ,height: 650
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         if(obj.event === 'view'){
        		location.href = "supOpinionView.do?bizId="+data.supOpinion_id+"";
           }else if(obj.event === 'delete'){
        	   layer.confirm('确定删除吗？', {
            		  btn: ['确定', '取消'] //可以无限个按钮
            		 ,btn1: function(index, layero){
        	   $.ajax({
					type : 'POST',
					url : 'delect_supOpinion.do',
					data : {
						 'supOpinion_id' : data.supOpinion_id
					   },success : function(data) {
							 if (data.Success) {	 
	  							 layer.msg(data.Msg, {icon: 6, time: 1500,shade:[0.3]}, function () {
		                                location.reload();
	  	 						  });
  	 							}else {
  	 							 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
  	 					     }
						}
				});
        	   }
        	   });   
           }
       });
       
       var $ = layui.$, active = {
    	reload: function(){ //验证是否全选
   		    	   var supOpinion_company=$('#supOpinion_company').val();
   		    	   var supOpinion_title=$('#supOpinion_title').val();
   		    	   table.reload('testReload', {
    			    	 page: {
    			    		   curr: 1 //重新从第 1 页开始
    			    	 },where: {
    			    		 'supOpinion_company':supOpinion_company
    			    		,'supOpinion_title':supOpinion_title
    			    	 } //设定异步数据接口的额外参数
    			    	 
    			      });
    		      },btnAdd: function(){ //新增
    		    	  location.href = "supOpinionNew.do?supOpinion_id=";
         		    }
    		  };
       		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		 
		});
		

	  //表格时间格式化
	  function start_time(obj){
		   var date=new  Date(obj); 
		   var time=date.Format("yyyy-MM-dd");
		   return time;
	  }
		
</script>

</body>
</html>