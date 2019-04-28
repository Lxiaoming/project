<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="PowerCheck" prefix="shop"%>
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
        <input type="text" class="layui-input" id="flowname" placeholder="流程名称">
      </div> 
    
      <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>
 <div class="layui-btn-group demoTable">
 	<shop:permission code="ADD_FLOW">
    <button class="layui-btn layui-btn-sm" data-type="addFlow">增加流程 </button>
    </shop:permission>
  </div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   
  <div class="layui-row" id="fromdiv" style="display: none;">
        <div style="margin: 10px 0"></div>
    <form class="layui-form" action="" id="form1">

  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">流程编号</label>
      <div class="layui-input-inline">
        <input type="text" name="flow_id" id="flow_id" readonly="readonly" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">流程名称</label>
      <div class="layui-input-inline">
        <input type="text" name="flow_name" id="flow_name" class="layui-input">
      </div>
    </div>
  </div>
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">流程描述位</label>
      <div class="layui-input-inline">
        <input type="text" name="flow_description" id="flow_description" class="layui-input">
      </div>
    </div>
    </div>
    <div class="layui-form-item">
       <div class="layui-input-block">
           <button type="button" class="layui-btn layui-btn-normal" onclick="btnSaveEva();">提交</button>
       </div>
   </div>
  </form>
  </div>

<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
<shop:permission code="ADD_FLOW">
  <a class="layui-btn layui-btn-xs" lay-event="look">查看</a>
  <a class="layui-btn layui-btn-xs" lay-event="editFlow">编辑</a>
  <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="btnDele">删除</a>
 </div>
</shop:permission>
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
         ,url:'flowLists.do'
    	 ,cols: [[ //标题栏
    	         {type:'checkbox',width: 70}
   	            ,{field: 'flow_id', title: '流程编号', width: 70}
   	            ,{field: 'flow_name', title: '流程名称', width: 200}
   	            ,{field: 'flow_description', title: '流程描述位', width: 200}
   	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:150}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:10
    ,height: 600
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         //查看
         if(obj.event === 'look'){
        		location.href = "flowNew.do?" + $.param({
					'flow_id':data.flow_id, //获取id
				});
        	//编辑	
         }else if(obj.event == 'editFlow'){
     		    	layer.open({
   			    	  type: 1,
   			          title:"流程信息",
   			          area:['40%'],
   			          content:$("#fromdiv"),
   			    	});
   			      	$("#flow_id").val(data.flow_id);
 					$("#flow_name").val(data.flow_name);
 					$("#flow_description").val(data.flow_description);
                //删除
             }else if(obj.event =='btnDele'){
            	 layer.confirm('确定删除吗？', {
           		  btn: ['确定', '取消'] //可以无限个按钮
           		 ,btn1: function(index, layero){
   			       	 $.ajax({
   							type : 'POST',
   							url : 'deleFlow.do',
   							traditional : true,
   							data : {
   								'flowid' : data.flow_id
   							},success : function(data) {
   								if (data.Success) {
    								 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
    	                                location.reload();
    	                            });
    							} else {
    								 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
    							}
    						
   			
   							}
   						});

           		  }
           		});
        	 
         }
       });
       
       var $ = layui.$, active = {
    		   addFlow: function(){ //新增
    			   layer.open({
    			    	  type: 1,
    			          title:"流程信息",
    			          area:["40%"],
    			          content:$("#fromdiv"),
    			    	});
    			   document.getElementById("form1").reset();
    		    },reload: function(){ 
    		    	   var flow_name=$('#flowname').val();
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'flow_name':flow_name

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
       function btnSaveEva(){
    	   $.ajax({
				type : 'POST',
				url : 'saveFlow.do',
				data :$("#form1").serialize(),
                success:function(data) {
				if (data.Success) {
					 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
						  location.reload();
					 })
				} else {
					 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
					
				}
	        }
			});
       }
	
		
</script>

</body>
</html>