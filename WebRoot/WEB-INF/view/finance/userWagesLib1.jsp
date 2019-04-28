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

</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div> 

    
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */

layui.use(['table'], function(){
	 var table = layui.table
	 
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'userWagesLibs1.do'
         ,toolbar: '#toolbarDemo'
         ,totalRow: true
    	 ,cols: [[ //标题栏
    	           
    	             {field: 'username', title: '用户名', width: 70, totalRowText: '合计'}
    	            ,{field: 'uc_wage_yearMon', title: '日期', width: 70}
    	            ,{field: 'finance_wages_attCount', title: '出勤天数', width: 70}
    	            ,{field: 'finance_wages_vacaCount', title: '休假天数', width: 70,}
    	            ,{field: 'finance_wages_leaveCount', title: '请假天数', width: 70}
    	            ,{field: 'uc_wage_actualDay', title: '实际出勤', width: 70}
    	            ,{field: 'uc_wage_base', title: '基本工资', width: 70}
    	            ,{field: 'uc_wage_post', title: '岗位工资', width: 70}
    	            ,{field: 'uc_wage_achieve', title: '绩效工资', width: 70}
    	            ,{field: 'uc_wage_subsidy', title: '津贴补助', width: 70}
    	            ,{field: 'uc_wage_dedu', title: '考勤扣除', width: 70,}
    	            ,{field: 'uc_wage_baseTotal', title: '应发小计', width: 70,}           
    	            ,{field: 'uc_wage_socSec', title: '代扣社保', width: 70}
    	            ,{field: 'uc_wage_accFund', title: '公积金', width: 60}
    	            ,{field: 'field_personnel_rolename', title: '扣除小计', width: 70,}
    	            ,{field: 'uc_wage_tax', title: '代扣个税', width: 70}
    	            ,{field: 'uc_wage_realhair', title: '实发工资', width: 80,totalRow: true}
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:175}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650
  });
    
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		

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
		

 
		
</script>

</body>
</html>