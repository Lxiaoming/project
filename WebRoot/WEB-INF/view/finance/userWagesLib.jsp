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
        <input type="text" class="layui-input" name="uc_company_name" id="uc_company_name" placeholder="输入公司">
      </div> 
       <div class="layui-input-inline">
        <input type="text" class="layui-input" name="username1" id="username1" placeholder="用户名">
      </div> 
       <div class="layui-input-inline">
        <input type="text" class="layui-input" id="uc_wage_yearMon" placeholder="yyyy-MM">
      </div> 

      <button class="layui-btn" id="btnSearch"  data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>

</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div> 

  <!-- 需要弹出的添加员工界面 -->
        <div class="layui-row" id="fromdiv" style="display: none;background-color:#FFD9EC;">
        <div style="margin: 10px 0"></div>
            <div class="layui-col-md10">
		     <form class="layui-form" id="form1" action="">
		     
		        <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label"><a href="#" onclick="openWin()" style="color: red;">用户名称</a></label>
				      <div class="layui-input-inline">
				        <input type="hidden" name="uc_wage_userid" id="uc_wage_userid"  readonly="readonly" class="layui-input">
				         <input type="text" name="uc_username" id="uc_username" readonly="readonly" class="layui-input">
				      </div>
				    </div>
				  <!--   <div class="layui-inline">
				      <label class="layui-form-label">公司名称</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_company_name" class="layui-input">
				      </div>
				    </div> -->
			    </div>
		     
		       <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">中心名称</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_center_name" id="uc_wage_center_name" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">公司名称</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_company_name" id="uc_wage_company_name" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			     <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">出勤天数</label>
				      <div class="layui-input-inline">
				        <input type="text" name="finance_wages_attCount" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">休假天数</label>
				      <div class="layui-input-inline">
				        <input type="text" name="finance_wages_vacaCount" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			     <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">请假天数</label>
				      <div class="layui-input-inline">
				        <input type="text" name="finance_wages_leaveCount" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">实际天数</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_actualDay" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			       <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">基本工资</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_base" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">岗位工资</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_post" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			       <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">绩效工资</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_achieve" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">津贴补助</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_subsidy" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			       <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">代扣社保</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_socSec" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">代扣公积金</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_accFund" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			       <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">代扣个税</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_tax" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">考勤扣除</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_dedu" class="layui-input">
				      </div>
				    </div>
			    </div>
			    
			    <div class="layui-form-item">
				    <div class="layui-inline">
				      <label class="layui-form-label">应发小计</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_baseTotal" class="layui-input">
				      </div>
				    </div>
				    <div class="layui-inline">
				      <label class="layui-form-label">实发工资</label>
				      <div class="layui-input-inline">
				        <input type="text" name="uc_wage_realhair" class="layui-input">
				      </div>
				    </div>
			    </div>
		         
				<div class="layui-form-item">
				    <div class="layui-input-block">
				      <button class="layui-btn" type="button" onclick="saveHistoricalWage()">提交</button>
				    
				    </div>
				</div>	
		</form>
     </div>
    </div>


   <script type="text/html" id="toolbarDemo">
    <div class="layui-btn-group">
 
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">添加</button>
   </div>
</script>
<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
  <a class="layui-btn layui-btn-xs layui-btn layui-btn-danger" lay-event="dele_userWages">删除</a>

  </div>
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
         ,url:'userWagesLibs.do'
         ,toolbar: '#toolbarDemo'
         ,totalRow: true
    	 ,cols: [[ //标题栏
    	             
    	            {field: 'uc_wage_center_name', title: '中心名称', width: 100}
    	            ,{field: 'username', title: '用户名', width: 70, totalRowText: '合计'}
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
    	            ,{field: 'uc_wage_tax', title: '代扣个税', width: 70,totalRow: true}
    	            ,{field: 'uc_wage_realhair', title: '实发工资', width: 80,totalRow: true}
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:175}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650
  });
    laydate.render({
         elem: '#uc_wage_yearMon'
        ,format: 'yyyy-MM'
      });
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
        	 if(obj.event === 'dele_userWages'){
        		 layer.confirm('确定删除吗？', {
	        		  btn: ['确定', '取消'] //可以无限个按钮
	        		 ,btn1: function(index, layero){
	         $.ajax({
  			type : 'POST',
  			url : 'dele_userWages.do',
  			data : {'finance_wages_id':data.finance_wages_id},
  			success : function(data) {
  				if(data.Success){
  				 layer.msg(data.Msg, {icon: 6, time: 2000,shade:[0.3]}, function () {
                     location.reload();
                 });
				} else {
					 layer.msg(data.Msg, {icon: 5, time: 3000,shade:[0.3]});
				}
  			  } 
  			}); 
	       }
      });	   
        		
		 }
       });

	
		
     
      
       //工具栏事件
       table.on('toolbar(demo)', function(obj){
         var checkStatus = table.checkStatus(obj.config.id);
         switch(obj.event){
           case 'getCheckData':
        	   layer.open({
		        	  type: 1,
		              title:"模板",
		              area: ['800px', '500px'],
		              content:$("#fromdiv"),					      
		        	})
  
           break;
         
         };
       
     });
    
    
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	 var uc_wage_yearMon=$("#uc_wage_yearMon").val();
    		    	 var username=$("#username1").val();
    		    	 var uc_company_name=$("#uc_company_name").val(); 	 
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		'uc_wage_yearMon':uc_wage_yearMon,
     			    		'username':username,	
     			    		'uc_company_name':uc_company_name,

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
		
   //获取上个月时间
	function lastMonthDate(){ 
		   var nowdays = new Date();
	       var year = nowdays.getFullYear();
	       var month = nowdays.getMonth();
	       if(month==0){
	           month = 12;
	           year = year-1;
	
	       }
	       if(month<10){
	           month = '0'+month;
	       }
	       var startDate = year+'-'+month; //上个月第一天
	   
	    	return startDate; 
		}
        
         //打开子页面窗口
        function openWin(){
			 layer.open({
				  type: 2,
				  area: ['50%','50%'],
				  fixed: false, //不固定
				  maxmin: true,
				  content: 'userListCheck1.do',
				  success:function(layero,index){
					
				  },end:function(data){
					
				  }
				});
			
		 }
        //子页面连接父页面js事件
       function  getValue(data){
        
    	    $("#uc_wage_userid").val(data.userid);
    	    $("#uc_username").val(data.username);
    	    $("#uc_wage_center_name").val(data.center_name);
    	    $("#uc_wage_company_name").val(data.company_name);  
    	   
       }; 
        //保存历史工资
       function  saveHistoricalWage(){
        	var uc_wage_userid =$("#uc_wage_userid").val();
        	  if(uc_wage_userid==''){
        		  layer.msg("请选择用户名", {icon: 5, time: 3000,shade:[0.3]});
        		  return;  
        	  }
        	 layer.confirm('确定提交吗？', {
	        		  btn: ['确定', '取消'] //可以无限个按钮
	        		 ,btn1: function(index, layero){
	         $.ajax({
   			type : 'POST',
   			url : 'saveHistoricalWage.do',
   			data : 	$("#form1").serialize(),
   			success : function(data) {
   				if(data.Success){
   				 layer.msg(data.Msg, {icon: 6, time: 2000,shade:[0.3]}, function () {
                      location.reload();
                  });
				} else {
					 layer.msg(data.Msg, {icon: 5, time: 3000,shade:[0.3]});
				}
   			  } 
   			}); 
	       }
       });	   
     }
   
	  $(function(){
		 $("#uc_wage_yearMon").val(lastMonthDate())//默认上个月
	  })
		
</script>

</body>
</html>