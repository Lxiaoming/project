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
        <input type="hidden" class="layui-input"  name="user_own" id="user_own">
        <input type="hidden" class="layui-input"  name="user_creat" id="user_creat">
        <input type="hidden" class="layui-input"  name="user_finish" id="user_finish">
        <input type="text" id="date1" placeholder="创建时间"  class="layui-input">
      </div> 
      <div class="layui-inline">
       <div class="layui-input-inline">
        <input type="text" id="date2" placeholder="结束时间"  class="layui-input">
      </div> 
      </div>
      <button class="layui-btn" id="btnSearch" data-type="reload" style="margin-left: 10px">搜索</button>
    </div>
  </div>
</div>
 <div class="layui-btn-group demoTable">
  <button class="layui-btn layui-btn-sm" data-type="btnAdd">新增</button>
  <button class="layui-btn layui-btn-sm" data-type="update">修改</button>
  <button class="layui-btn layui-btn-sm" data-type="btnDele">删除</button>
  <button class="layui-btn layui-btn-sm"  onclick="btnUser('own');">待办任务<span style="color:#00FFFF">${dealtCount}</span></button>
  <button class="layui-btn layui-btn-sm"  onclick="btnUser('finish');">已完成任务 <span style="color:#00FFFF">${finishCount}</span></button>
  <button class="layui-btn layui-btn-sm"  onclick="btnUser('creat');">我的创建<span style="color:red">${creatCount}</span></button>
</div>	
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>  

  
<!-- 需要弹出的添加员工界面 -->
<div class="layui-row" id="fromdiv1" style="display: none;">
        <div style="margin: 10px 0"></div>
    <form class="layui-form form1" action="">
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">实际完成时间</label>
      <div class="layui-input-inline">
          <input type="hidden" name="own_workArranged_id" class="layui-input workArranged_id">
          <input type="text" id="own_workArranged_finishTime" name="own_workArranged_finishTime"class="layui-input">
          <input type="hidden" id="own_workArranged_status" value="1" name="own_workArranged_status" class="layui-input"> 
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
    <div class="layui-row" id="fromdiv2" style="display: none;">
        <div style="margin: 10px 0"></div>
    <form class="layui-form form1" action="">
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label"><a href="#" onclick="open22()" style="color: red;">协助人</a></label>
      <div class="layui-input-inline">
            <input type="hidden" name="own_workArranged_id" class="layui-input workArranged_id">
          	<input type="text" id="username" name="username" class="layui-input" readonly="readonly">
			<input type="hidden" id="own_workArranged_coordinator" name="own_workArranged_coordinator" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">协助备注</label>
      <div class="layui-input-inline">
          <input type="text" name="own_workArranged_assist" id="own_workArranged_assist" class="layui-input">
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

    <div class="layui-row" id="fromdiv3" style="display: none;">
        <div style="margin: 10px 0"></div>
    <form class="layui-form form1" action="" >
    
    <div class="layui-form-item layui-form-text">
    <label class="layui-form-label">问题反馈</label>
    <div class="layui-input-block">
      <input type="hidden" name="own_workArranged_id" class="layui-input workArranged_id">
      <textarea name="own_workArranged_feedback"  class="layui-textarea" style="width: 400px"></textarea>
    </div>
  </div>
  
    <div class="layui-form-item">
       <div class="layui-input-block">
           <button type="button" class="layui-btn layui-btn-normal" onclick="btnSaveEva();">提交</button>
       </div>
   </div>
  </form>
  </div>
   <div class="layui-row" id="fromdiv4" style="display: none;">
        <div style="margin: 10px 0"></div>
    <form class="layui-form form1" action="" >
   <div class="layui-form-item">
    <label class="layui-form-label">完成情况</label>
    <div class="layui-input-inline">
      <select name="own_workArranged_finishQua">
        <option value="0"></option>
		<option value="1">A</option>
		<option value="2">B</option>										
		<option value="3">C</option>
      </select>
    </div></div>
    <div class="layui-form-item">
       <div class="layui-input-block">
           <button type="button" class="layui-btn layui-btn-normal" onclick="btnSaveEva();">提交</button>
       </div>
   </div>
  </form>
  </div>
  

<script type="text/html" id="barDemo1">
   <a href="#"  style="color: #FF1493;"  lay-event="1">完成</a>
</script>
<script type="text/html" id="barDemo2">
   <a href="#"  style="color: #FF1493;"  lay-event="2">协助指派</a>
</script>
<script type="text/html" id="barDemo3">
   <a href="#"  style="color: #FF1493;"  lay-event="3">反馈</a>
</script>
<script type="text/html" id="barDemo4">
   <a href="#"  style="color: #FF1493;"  lay-event="4">完成情况</a>
</script>
              
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */

layui.use(['table','form','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'ownWorkLists.do'
    	 ,cols: [[ //标题栏
    	          {type:'checkbox',width: 50}
   	            ,{field: 'construct_project_name', title: '项目名称', width: 100}
   	            ,{field: 'own_workArrangHead_category', title: '工作分类', width: 110}
   	            ,{field: 'own_workArranged_content', title: '工作内容', width: 150,}
   	            ,{field: 'arranger', title: '指令', width: 60}
   	            ,{field: 'sponsor', title: '主办', width: 60}
   	            ,{field: 'coordinator', title: '协办', width: 60}
   	            ,{field: 'own_workArranged_current', title: '目前情况', width: 120}
   	            ,{field: 'own_workArranged_feedback', title: '问题反馈', width: 135} 
   	            ,{field: 'own_workArranged_assist', title: '协助备注', width: 135} 
   	            ,{field: 'own_workArranged_creatTime', title: '创建时间', width: 80}
	            ,{field: 'own_workArranged_planTime', title: '计划时间要求', width: 80}
	            ,{field: 'own_workArranged_finishTime', title: '实际完成时间', width: 80} 
	            ,{field: 'own_workArranged_finishQua', title: '完成情况', width: 135} 
	            ,{field: 'own_workArranged_remarks', title: '备注', width: 120}
	            ,{field: 'psn1', title: '操作1', width: 50,toolbar: '#barDemo1',}
	            ,{field: 'psn2', title: '操作2', width: 80,toolbar: '#barDemo2',} 
	            ,{field: 'psn3', title: '操作3', width: 50,toolbar: '#barDemo3',} 
	            ,{field: 'psn3', title: '操作4', width: 80,toolbar: '#barDemo4'} 
                ]]
	    ,id:'testReload'
	    ,page: true
	    ,limit:10
	    ,height: 600
	    ,done: function (res, curr, count) {
	    	merge(res, curr, count);
	    	}
	    	
  	});

    //时间格式
    laydate.render({
        elem: '#own_workArranged_finishTime'
      });
    //时间格式
    laydate.render({
        elem: '#date1'
      });
    //时间格式
    laydate.render({
        elem: '#date2'
      });
  
     //监听工具条
       table.on('tool(demo)', function(obj,field){
         var data = obj.data;
        $('.workArranged_id').val(data.own_workArranged_id);
         if(obj.event === '1'){
        	  layer.open({
            	  type: 1,
               
                  area:["30%"],
                  content:$("#fromdiv1"),
            	});
             }else if(obj.event === '2'){
            	 layer.open({
               	  type: 1,
                   
                     area:["40%"],
                     content:$("#fromdiv2"),
               	});
             }else if(obj.event === '3'){
            	 layer.open({
                  	  type: 1,
                        area:["40%"],
                        content:$("#fromdiv3"),
                  	});
            	
             }else if(obj.event === '4'){
            	 layer.open({
                  	  type: 1,
                        area:["40%","50%"],
                        content:$("#fromdiv4"),
                  	});
                }
       });
       
       var $ = layui.$, active = {
    		   btnAdd: function(){ //新增
    				location.href = "ownWorkNew.do";
    			 //修改
    		    },update: function(){
    		    	   var checkStatus = table.checkStatus('testReload')
    		    	      ,data = checkStatus.data;
    		    	  if(data.length==0){
    		    		  layer.msg('请勾选修改行', {icon: 5, time: 1500,shade:[0.3]});
    		    	  }else if(data.length>1){
    		    		  layer.msg('只能勾选一行', {icon: 5, time: 1500,shade:[0.3]});
    					}else{
    						location.href = "ownWorkNew.do?own_workArranged_categoryId="+data[0].own_workArranged_categoryId+"&type='update'";
    					}
    		     //删除
    		    },btnDele: function (){
    		    	  var checkStatus = table.checkStatus('testReload')
		    	      ,data = checkStatus.data;
    		    	  if(data.length==0){
    		    		  layer.msg('请勾选修改行', {icon: 5, time: 1500,shade:[0.3]});
    		    	  }else if(data.length>1){
    		    		  layer.msg('只能勾选一行', {icon: 5, time: 1500,shade:[0.3]});
    				  }else{
    					  layer.msg(data[0].own_workArranged_categoryId);
   						 layer.confirm('确定删除吗？', {
   			           		  btn: ['确定', '取消'] //可以无限个按钮
   			           		 ,btn1: function(index, layero){
			    		    	$.ajax({
									type : 'POST',
									url : 'delete_workerHead.do',
									data : {
										'own_workArranged_categoryId' :data[0].own_workArranged_categoryId
									},
									success : function(data) {
										 if (data.Success) {	 
				  							 layer.msg(data.Msg, {icon: 6, time: 1500,shade:[0.3]}, function () {
					                                location.reload();
				  	 						          });
				  	 							}else {
				  	 							 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
				  	 					     }
										}
								});}
   						 });
    				  } 
    		    },reload: function(){ //验证是否全选
    		    var user_own = $('#user_own').val();
    		    var user_creat = $('#user_creat').val(); 
    		    var user_finish = $('#user_finish').val();
    		    var date1 = $('#date1').val();
    		    var date2 = $('#date2').val();
  			    	//执行重载
	 			      table.reload('testReload', {
	 			        page: {
	 			          curr: 1 //重新从第 1 页开始
	 			        }
	 			        ,where: {
	 			         'user_own':user_own
	 			        ,'user_creat':user_creat
	 			        ,'user_finish':user_finish
	 			        ,'date1':date1
	 			        ,'date2':date2
	 			        }
	 			        
  			      });
  			   

    		      }
    		  };
       		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		 
		  
		});
		
		 function open22(){
			 layer.open({
				  type: 2,
				  area: ['50%','50%'],
				  fixed: false, //不固定
				  maxmin: true,
				  content: 'userListCheck1.do',
				  success:function(layero,index){
					 console.log()
				  },end:function(data){
					
				  }
				});
			
		 }
		 //子页面传值事件
		 function getValue(obj){
			$("#username").val(obj.username)
		    $("#own_workArranged_coordinator").val(obj.userid);
		  }

       //四个表单提交共同方法
		function btnSaveEva(){
			  $.ajax({
					type :'post',
					url : 'update_work.do',
					data : $(".form1").serialize(),
					success : function(data) {						
						if (data.Success) {
							 layer.msg(data.Msg, {icon: 6, time: 1500,shade:[0.3]}, function () {
	                                location.reload();
	 					});
						}else{
							 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
							}
						}
					});
		      }
		

		function btnUser(obj){
			
			if(obj==='own'){
				$("#user_finish").val("");
				$("#user_creat").val("");
				$("#user_own").val(${loginUser.userid});
			}else if(obj==='finish'){
				$("#user_creat").val("");
				$("#user_own").val("");
				$("#user_finish").val(${loginUser.userid});
			}else if(obj==='creat'){
				$("#user_own").val("");
				$("#user_finish").val("");
				$("#user_creat").val(${loginUser.userid});
			}
			//IE
			if (document.all) {
				document.getElementById("btnSearch").click();
			}else {
				var e = document.createEvent("MouseEvents");
				e.initEvent("click", true, true); //这里的click可以换成你想触发的行为
				document.getElementById("btnSearch").dispatchEvent(e); //这里的clickME可以换成你想触发行为的DOM结点
			}

		}
		
		/**
		* 合并单元格
		* @param res 表格数据
		* @param curr 当前页
		* @param count 总数
		*/
		function merge(res, curr, count) {
		var data = res.data;
		var mergeIndex = 0;//定位需要添加合并属性的行数
		var mark = 1; //这里涉及到简单的运算，mark是计算每次需要合并的格子数
		var columsName = ['own_workArranged_categoryId'];//需要合并的列名称
		var columsIndex = [1];//需要合并的列索引值

		for (var k = 0; k < columsName.length; k++)//这里循环所有要合并的列
		{
		var trArr = $(".layui-table-body>.layui-table").find("tr");//所有行
		for (var i = 1; i < res.data.length; i++) { //这里循环表格当前的数据
		var tdCurArr = trArr.eq(i).find("td").eq(columsIndex[k]);//获取当前行的当前列
		var tdPreArr = trArr.eq(mergeIndex).find("td").eq(columsIndex[k]);//获取相同列的第一列

		if (data[i][columsName[k]] === data[i - 1][columsName[k]]) { //后一行的值与前一行的值做比较，相同就需要合并
		mark += 1;
		tdPreArr.each(function () {//相同列的第一列增加rowspan属性
		$(this).attr("rowspan", mark);
		});
		tdCurArr.each(function () {//当前行隐藏
		$(this).css("display", "none");
		});
		}else {
		mergeIndex = i;
		mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
		}
		}
		}
		}
		
</script>

</body>
</html>