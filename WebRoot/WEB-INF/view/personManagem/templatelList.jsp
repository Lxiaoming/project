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
        <input type="text" class="layui-input"  
						   id="hr_templatel_type"  placeholder="类型">
      </div> 
      <button class="layui-btn" id="btnSearch" data-type="reload" style="margin-left: 10px">搜索</button>
    </div>
  </div>
</div>
 
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="LAY_table_user" lay-filter="demo"></table> 
  </div>   
  
  
  <!-- 需要弹出的添加员工界面 -->
        <div class="layui-row" id="fromdiv" style="display: none;background-color:#FFD9EC;">
        <div style="margin: 10px 0"></div>
            <div class="layui-col-md10">
        <form class="layui-form" id="form1" action="">
  <div class="layui-form-item">
    <label class="layui-form-label">模板编号</label>
    <div class="layui-input-block">
      <input type="text" id="hr_templatel_id" name="hr_templatel_id" value="${hr_templatel_id==null?0:hr_templatel_id}"
             style="width: 300px;" readonly="readonly" class="layui-input">
    </div>
  </div>
  
   <div class="layui-form-item" hidden="hidden">
    <label class="layui-form-label">模板路径</label>
    <div class="layui-input-block">
      <input type="text" id="hr_template_path" name="hr_template_path" value="${hr_template_path}" class="layui-input">
    
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label">模板名称</label>
    <div class="layui-input-block">
      <input type="text" name="hr_template_name" id="hr_template_name" value="${hr_template_name}" style="width: 300px;" class="layui-input">
    
    </div>
  </div>
  <div class="layui-form-item">
    <label class="layui-form-label" >类型</label>
    <div class="layui-input-inline" style="width:300px;">
      <select  id="templatel_type"  name="hr_templatel_type">
        <option value="1">合同标准</option>
        <option value="2">行政标准</option>
        <option value="3">财务标准</option>
        <option value="4">企业资质</option>
      </select>
    </div>
    </div>
 <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">普通文本域</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" id="hr_templatel_describe" name="hr_templatel_describe" class="layui-textarea" style="width: 300px">${hr_templatel_describe}</textarea>
    </div>
  </div>

  <div class="layui-form-item">
    <div class="layui-input-block">
      <button type="button" class="layui-btn layui-btn-normal" id="test8">选择文件</button>
      <button type="button" class="layui-btn" id="test9" onclick="btnSave();">开始上传</button>
    </div>
  </div>
</form>
            </div>
        </div>
  
  
  <!-- 表格查看按钮 -->
<script type="text/html" id="barDemo">
  <div class="layui-btn-group">
  	<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="downLoad">下载</a>
 </div>
</script>

<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-group">
    <button class="layui-btn layui-btn-sm" lay-event="btnNew">新增</button>
    <button class="layui-btn layui-btn-sm" onclick="btnCompany(1)">合同标准</button>
    <button class="layui-btn layui-btn-sm" onclick="btnCompany(2)">行政标准</button>
    <button class="layui-btn layui-btn-sm" onclick="btnCompany(3)">财务标准</button>
    <button class="layui-btn layui-btn-sm" onclick="btnCompany(4)">企业标准</button>
  </div>
</script>

<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
layui.use(['table','form','upload','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	 var form = layui.form
	  ,$ = layui.$ 
	  ,upload = layui.upload
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#LAY_table_user'
         ,url:'templatelLists.do'
    	 ,toolbar: '#toolbarDemo'
    	 ,cols: [[ //标题栏
           {field: 'hr_templatel_id', title: '模板号', width: 80, sort: true}
           ,{field: 'hr_template_name', title: '模板名称', width: 150}
           ,{field: 'hr_templatel_time', title: '时间', width: 120}
           ,{field: 'hr_template_path', title: '路径', width: 150}
           ,{field: 'hr_templatel_type', title: '类型', width: 120,templet:'<div>{{operate_type(d.hr_templatel_type)}}</div>'}
           ,{field: 'hr_templatel_describe', title: '描述', }
           ,{field: 'psn', title: '操作',toolbar:'#barDemo', width:160}
    	 ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height:650
  });
       
       var $ = layui.$, active = {
		    reload: function(){	
		    	   var hr_templatel_type = $('#hr_templatel_type').val();
		    	//执行重载
		      table.reload('testReload', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		         'hr_templatel_type':hr_templatel_type
		      
		        }
		        
		      });
		    }
		  };
       //创建文件上传时间戳
       var timestamp=new Date().getTime();
     //选完文件后不自动上传
      upload.render({
     	     elem: '#test8'
     	    ,url: 'uploadFile.do'
     	    ,auto: false
     	    ,data:{'timestamp':timestamp,
     	    	    'oldPath':$("#hr_template_path").val(),
     	    	    'newPath':"TemplateModel/"+timestamp,
     	    	  }
     	    ,accept: 'file'  
     	    ,bindAction: '#test9'
     	    ,choose: function(obj){ //obj参数包含的信息，跟 choose回调完全一致，可参见上文。
         	    obj.preview(function(index, file, result){
         	    	
         	          var paht="http://img.ca315189.com/casd/TemplateModel/"+timestamp+(file.name);
         	    	  $("#hr_template_path").val(paht);
         	       
         	    });
     	    		
     	    		  }
     	 
         
        });
       
       
  
		 //监听工具条 table  lay-filter="demo"
		 table.on('tool(demo)', function(obj){
		   var data = obj.data;
		    //办理任务
		     if(obj.event == 'edit'){
		    	 layer.open({
		        	  type: 1,
		              title:"模板",
		              area:["40%"],
		              content:$("#fromdiv"),
		        	});
		    	 $("#hr_templatel_id").val(data.hr_templatel_id);
		    	 $("#hr_template_path").val(data.hr_template_path);
		    	 $("#hr_template_name").val(data.hr_template_name);
		    	 $("#templatel_type").val(data.hr_templatel_type);
		    	 $("#hr_templatel_describe").val(data.hr_templatel_describe);
		    	 
		      //删除事件
		     }else if(obj.event == 'delete'){
		    	 layer.confirm('确定删除吗？', {
	        		  btn: ['确定', '取消'] //可以无限个按钮
	        		 ,btn1: function(index, layero){
	        			 $.ajax({
								type : 'POST',
								url : 'deleteTemplate.do',
								data : {
									'biz' : data.hr_templatel_id,
									'hr_template_path':data.hr_template_path,
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
		    	 //下载事件
		     }else if(obj.event=='downLoad'){
					location.href = "downloadTemplate.do?"+$.param({
						hr_template_path:data.hr_template_path
					   });
				  }
		     });

		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		//头工具栏事件
		  table.on('toolbar(demo)', function(obj){	
		    switch(obj.event){
		      case 'btnNew':
		    	   document.getElementById("form1").reset();
		    	  layer.open({
		        	  type: 1,
		        	
		              title:"模板",
		              area:["40%"],
		              content:$("#fromdiv"),
		        	});
		    	
		      break;
		     
		    };
		  });
		});
				
		//类型格式化 
		function operate_type(value, row, index){
			if (value == 1) {
				return "合同标准";
			  } else if (value == 2) {
				return "行政标准";
			} else if (value ==3) {
				return "财务标准";
			} else if (value ==4) {
				return "企业资质";
			}
			
		   }

	
		//连接点击事件
		function btnCompany(obj){
				$("#hr_templatel_type").val(obj);

			//IE
			if (document.all) {
				document.getElementById("btnSearch").click();
			}
			// 其它浏览器
			else {
				var e = document.createEvent("MouseEvents");
				e.initEvent("click", true, true); //这里的click可以换成你想触发的行为
				document.getElementById("btnSearch").dispatchEvent(e); //这里的clickME可以换成你想触发行为的DOM结点
			}
		}
		
		//提交表单
		function btnSave(){
			  $.ajax({
					type : 'POST',
				    url :'saveTemplatel.do',
					data : $("#form1").serialize(),
					success : function(data) {
						if (data.Success) {
							 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
								 location.href = 'templatelList.do';
		                   });
						}else {
							 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
						}
					} 
					});
		  }
	  	
		
</script>

</body>
</html>