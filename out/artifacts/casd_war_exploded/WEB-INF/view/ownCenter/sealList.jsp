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
        <input type="text" class="layui-input" name="company_name" id="company_name" placeholder="公司">
      </div> 
        <div class="layui-input-inline">
        <input type="text" class="layui-input" name="own_seal_fileName" id="own_seal_fileName" placeholder="文件名称">
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
  <a class="layui-btn layui-btn-xs" lay-event="btnDele">删除</a>
  <a class="layui-btn layui-btn-xs" href="{{d.own_seal_filePath}}" download="">下载</a>
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
         ,url:'sealLists.do'
    	 ,cols: [[ //标题栏
    	         {field:'own_seal_id', title:'ID',width: 70}  
   	            ,{field: 'own_seal_fileName', title: '文件名称', width: 150}
   	            ,{field: 'company_name', title: '用章公司', width: 100}
   	            ,{field: 'own_seal_chapCategory', title: '用章类别', 
   	              width: 150,templet:'<div>{{chap_formatter(d.own_seal_chapCategory)}}</div>'}
   	            ,{field: 'own_seal_sender', title: '主送单位', width: 150,}
   	            ,{field: 'own_seal_creatTime', title: '创建时间', width: 80}
   	            ,{field: 'own_seal_status', title: '状态', width: 70,templet:'<div>{{operate_status(d.own_seal_status)}}</div>'} 
   	            ,{field:'own_seal_remark',width: 300, title:'备注'}  
   	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:130}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         console.log(data);
         if(obj.event === 'btnEdit'){
        //	 location.href="http://img.ca315189.com/casd/sealFile/1553828273412sealView.png" download="";
        	//	location.href = "sealNew.do?own_seal_id="+data.own_seal_id+"";
         }else if(obj.event === 'view'){    	
        	 location.href = "sealView.do?bizId="+data.own_seal_id+"";	

             }else if(obj.event === 'btnDele'){
            	 layer.confirm('确定删除吗？', {
           		  btn: ['确定', '取消'] //可以无限个按钮
           		 ,btn1: function(index, layero){
           			 $.ajax({
    						type : 'POST',
    						url : 'delect_seal.do',
    						data : {
    							'own_seal_id':data.own_seal_id,
    							'own_seal_filePath':data.own_seal_filePath
    						},
    						success : function(data) {
    							if (data.Success) {
    								 layer.msg(data.msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
    	                                location.reload();
    	                            });
    							} else {
    								 layer.msg(data.msg, {icon: 5, time: 1000,shade:[0.3]});
    							}
    							}
    					});
           		  }
           		});
             }
       });
       
       var $ = layui.$, active = {
    		   btnAdd: function(){ //新增 
    			   
  			 	location.href = "sealNew.do?own_seal_id=";
    		    },reload: function(){ //验证是否全选
    		    	   var company_name=$('#company_name').val();
    		    	   var own_seal_fileName=$('#own_seal_fileName').val();
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'company_name':company_name
     			    		,'own_seal_fileName':own_seal_fileName
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
		//格式化章的类型
		function chap_formatter(value) {
			var category = value.split(",");
			var categoryName="";
			for (var i = 0; i < category.length; i++) {
				var name="";
				if(category[i]==1){
					name= "公章";
				}else if(category[i]==2){
					name= "业务章";
					
				}if(category[i]==3){
					name= "出图章";
				    
				}if(category[i]==4){
					name= "竣工章";
				}if(category[i]==5){
					name= "项目章";   
				}
				categoryName =categoryName+name+"、";
				
			} 
			return categoryName;
		}
		//格式化状态
		function operate_status(value){
			if(value == 0){
				return "初始录入";
			} else if (value == 1) {
				  return '<span style="color: #F581B1;">审核中</span>';
			} else if (value ==2) {
				return  '<span style="color: #00CD00;">审核通过</span>';
			}
		}


		//下载附件
		function loadSeries() {
	/* 		var own_seal_filePath = $('#own_seal_filePath')
					.val();
			if (own_seal_filePath == "") {
				alert("没有附件！");
				return false;

			form.attr('action', "downloadseal.do?own_seal_filePath="+ own_seal_filePath);//下载文件的请求路径   */
		
		}
</script>

</body>
</html>