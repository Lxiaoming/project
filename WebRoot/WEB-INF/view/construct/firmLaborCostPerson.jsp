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
        <input type="text" class="layui-input" id="hr_attend_date" placeholder="yyyy-MM">
      </div> 

      <button class="layui-btn" id="btnSearch"  data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>

</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div> 

<script type="text/html" id="toolbarDemo">
   
</script>
<script type="text/html" id="barDemo">
 <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>

</script>
              
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */

layui.use(['table','form','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,form = layui.form
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'firmLaborCostPersons.do'
         ,toolbar: '#toolbarDemo'
         ,totalRow: true
    	 ,cols: [[ //标题栏
    	        {field: 'construct_project_name', title: '项目', width: 200}
   	            ,{field: 'construct_project_workTeam_category', title: '施工项目', width: 70,templet:'<div>{{category(d.construct_project_workTeam_category)}}</div>'}
   	            ,{field: 'username', title: '打卡人', width: 100}
   	            ,{field: 'hr_attend_date', title: '打卡日期', width: 150}
   	            ,{field: 'hr_attend_startWork', title: '上班打卡时间', width: 150,}
   	            ,{field: 'hr_attend_knockOff', title: '下班打卡时间', width: 170}
   	            ,{field: 'hr_attend_workAddress', title: '上班打卡地址', width: 170,}
   	         	,{field: 'hr_attend_offWorkAddress', title: '下班打卡地址', width: 190,}
   	            ,{field: 'hr_attend_WTLength', title: '工作时长', width: 70,totalRow: true}  
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:10
    ,height: 650
  });
    
       //引入时间插件
    laydate.render({
         elem: '#hr_attend_date'
         ,type:'month'
        ,format: 'yyyy-MM'
      });
    
     //监听工具条
    table.on('tool(demo)', function(obj){
      var data = obj.data;
      switch (obj.event) {
         //查看打卡
		case 'detail':
			location.href = "laborCostAttend.do?" + $.param({
				'hr_attend_date' : data.hr_attend_date,
				'hr_attend_workTeamId' : data.hr_attend_workTeamId,

			   }); //获取用户
			break;
		default:
			break;
		}

   
    

  });
		
     
      
   
    
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	 var hr_attend_date=$("#hr_attend_date").val();
    		    		
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 
     			    		'hr_attend_date':hr_attend_date,

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
		
		
		 //格式化班主
		function category(value) {
			switch (value) {
			case 1:
				return "预埋"; 
				break;
			case 2:
				return "消防水"; 
				break;
			case 3:
				return "消防电"; 
				break;
			case 4:
				return "防排烟"; 
				break;	
			case 5:
				return "消防水电"; 
				break;	
			default:
				break;
			}

		}
	 
</script>

</body>
</html>