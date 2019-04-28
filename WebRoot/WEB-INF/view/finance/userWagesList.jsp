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
	     <select id="company_id">     
	       <option value="">请公司</option>
	       <option value="12">诚安时代</option>
	       <option value="13">传诚管理</option>
	       <option value="14">诚安科技</option>
	       <option value="15">传诚教育</option>
	       <option value="16">诚安建设</option>
	     </select>
      </div> 
       <div class="layui-input-inline">
        <input type="text" class="layui-input" name="username" id="username" placeholder="用户名">
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
		     
		           <div class="layui-form-item"  hidden="hidden">
					  <label class="layui-form-label">单据编号</label>
					  <div class="layui-input-inline">
					    <input type="text" id="uc_wage_id" name="uc_wage_id" class="layui-input">
					  </div>
					</div>
					<div class="layui-form-item" hidden="hidden">
					  <label class="layui-form-label">用户编号</label>
					  <div class="layui-input-inline">
					    <input type="text" name="uc_wage_userId" id="uc_wage_userId" class="layui-input">
					  </div>
					</div>
					<div class="layui-form-item">
					  <label class="layui-form-label">实际出勤</label>
					  <div class="layui-input-inline">
					    <input type="text" id="uc_wage_actualDay" name="uc_wage_actualDay" class="layui-input">
					  </div>
					</div>
					<div class="layui-form-item">
					  <label class="layui-form-label">基本工资</label>
					  <div class="layui-input-inline">
					    <input type="text" id="uc_wage_base" name="uc_wage_base" class="layui-input">
					  </div>
					</div>
					<div class="layui-form-item">
					  <label class="layui-form-label">岗位工资</label>
					  <div class="layui-input-inline">
					    <input type="text" id="uc_wage_post" name="uc_wage_post" class="layui-input">
					  </div>
					</div>
					<div class="layui-form-item">
					  <label class="layui-form-label">绩效工资</label>
					  <div class="layui-input-inline">
					    <input type="text" name="uc_wage_achieve" id="uc_wage_achieve" class="layui-input">
					  </div>
					</div>
					<div class="layui-form-item">
					  <label class="layui-form-label">津贴补助</label>
					  <div class="layui-input-inline">
					    <input type="text" name="uc_wage_subsidy" id="uc_wage_subsidy" class="layui-input">
					  </div>
					</div>
				 	<div class="layui-form-item">
					  <label class="layui-form-label">代扣社保</label>
					  <div class="layui-input-inline">
					    <input type="text" name="uc_wage_socSec" id="uc_wage_socSec"  class="layui-input">
					  </div>
					</div>
					<div class="layui-form-item">
					  <label class="layui-form-label">代扣公积金</label>
					  <div class="layui-input-inline">
					    <input type="text" name="uc_wage_accFund" id="uc_wage_accFund" class="layui-input">
					  </div>
					</div>
					<div class="layui-form-item">
					  <label class="layui-form-label">代扣个税</label>
					  <div class="layui-input-inline">
					    <input type="text" name="uc_wage_tax" id="uc_wage_tax" class="layui-input">
					  </div>
					</div>
				<div class="layui-form-item">
				    <div class="layui-input-block">
				      <button class="layui-btn" type="button" onclick="submitWages()">提交</button>
				    
				    </div>
				</div>	
		</form>
     </div>
  </div>

   <script type="text/html" id="toolbarDemo">
 
    <div class="layui-btn-group">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">历史工资</button>
        <button class="layui-btn layui-btn-sm" lay-event="updateWages">更新</button>
     
   </div>
</script>
<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
{{#  if(d.uc_wage_status === 0){ }}
 <a class="layui-btn layui-btn-xs" name="ssss" lay-event="save_userWages">存档</a>
  {{#  } else { }}
   <button class="layui-btn layui-btn-xs layui-btn-disabled" >存档</button>
  {{#  } }}
  
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
         ,url:'userWagesLists.do'
         ,toolbar: '#toolbarDemo'
    	 ,cols: [[ //标题栏
    	             {type:'checkbox', fixed: 'left',width: 60}
    	            ,{field: 'center_name', title: '用户名', width: 70}     
    	            ,{field: 'username', title: '用户名', width: 70}
    	            ,{field: 'finance_wages_attCount', title: '出勤天数', width: 70}
    	            ,{field: 'finance_wages_vacaCount', title: '休假天数', width: 70,templet:'<div>{{finance_wages_vacaCount(d)}}</div>'}
    	            ,{field: 'finance_wages_leaveCount', title: '请假天数', width: 70}
    	            ,{field: 'uc_wage_actualDay', title: '实际出勤', width: 70}
    	            ,{field: 'uc_wage_base', title: '基本工资', width: 70, event: 'setSign', style:'cursor: pointer;'}
    	            ,{field: 'uc_wage_post', title: '岗位工资', width: 70}
    	            ,{field: 'uc_wage_achieve', title: '绩效工资', width: 70}
    	            ,{field: 'uc_wage_subsidy', title: '津贴补助', width: 70}
    	            ,{field: 'uc_wages_dedu', title: '考勤扣除', width: 70,templet:'<div>{{uc_wages_dedu(d)}}</div>'}
    	            ,{field: 'uc_wages_baseTotal', title: '应发小计', width: 70,templet:'<div>{{uc_wages_baseTotal(d)}}</div>'}           
    	            ,{field: 'uc_wage_socSec', title: '代扣社保', width: 70}
    	            ,{field: 'uc_wage_accFund', title: '公积金', width: 60}
    	            ,{field: 'field_personnel_rolename', title: '扣除小计', width: 70,}
    	            ,{field: 'uc_wage_tax', title: '代扣个税', width: 70}
    	            ,{field: 'arealhair', title: '实发工资', width: 80,templet:'<div>{{arealhair(d)}}</div>'}
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:175}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
        	 if(obj.event === 'setSign'){
        		 var userid=data.userid;
        		 $.ajax({
						type : 'get',
						url : 'base_Wages.do',
						traditional : true,
						data : {
							'userid' : data.userid
						},
						success : function(data) {
							 layer.open({
					        	  type: 1,
					              title:"模板",
					              area:["40%"],
					              content:$("#fromdiv"),					      
					        	})
					        	
	       		    $("#uc_wage_id").val(data.uc_wage_id == undefined ? 0 : data.uc_wage_id);
				    $("#uc_wage_actualDay").val(data.uc_wage_actualDay == undefined ? 0 : data.uc_wage_actualDay);
					$("#uc_wage_base").val(data.uc_wage_base == undefined ? 0 : data.uc_wage_base);
					$("#uc_wage_post").val(data.uc_wage_post == undefined ? 0 : data.uc_wage_post);
					$("#uc_wage_achieve").val(data.uc_wage_achieve == undefined ? 0 : data.uc_wage_achieve);
					$("#uc_wage_subsidy").val(data.uc_wage_subsidy == undefined ? 0 : data.uc_wage_subsidy);
					$("#uc_wage_socSec").val(data.uc_wage_socSec == undefined ? 0 : data.uc_wage_socSec);
					$("#uc_wage_accFund").val(data.uc_wage_accFund == undefined ? 0 : data.uc_wage_accFund);
					$("#uc_wage_userId").val(userid == undefined ? 0 : userid);
					$("#uc_wage_tax").val(data.uc_wage_tax == undefined ? 0 : data.uc_wage_tax);
			}
           });
        		
		 }else if(obj.event === 'save_userWages'){
			    var company_name=data.company_name; //公司名称
			    var center_name=data.center_name//中心名称
			    var userid=data.userid; //用户id
			    /*  计算休假天数开始  */
			    var dayCount=getLastDays().length; //当月天数
				var vacaCount =data.finance_wages_attCount; //出勤天数
				var leaveCount =data.finance_wages_leaveCount==null?0:data.finance_wages_leaveCount;//请假天数	
				var xiujiaCount=dayCount-(vacaCount+leaveCount);//计算休假天数
				  /*  计算休假天数开结束 */
				  /*  计算考勤扣除开始  */
					var uc_wage_base=data.uc_wage_base==undefined?0:data.uc_wage_base; //基本工资
					var uc_wage_post=data.uc_wage_post==undefined?0:data.uc_wage_post; //岗位工资
					var actualDay=data.uc_wage_actualDay==undefined?0:data.uc_wage_actualDay; //实际出勤天数
					var uc_wages_dedu;//考勤扣除变量
				 if(leaveCount>=0 && actualDay==0){

					  uc_wages_dedu= (((parseFloat(uc_wage_base)+parseFloat(uc_wage_post))/dayCount)*leaveCount).toFixed(2);
					   
				   }else if(actualDay>0) {
					  uc_wages_dedu= (((parseFloat(uc_wage_base)+parseFloat(uc_wage_post))/dayCount)*(dayCount-actualDay)).toFixed(2);;
				  } 	
				   /* 计算考勤扣除结束  */
				 
				  /* 计算应发开始  */
				    var  uc_wages_baseTotal;
					var  uc_wage_achieve=data.uc_wage_achieve==undefined?0:data.uc_wage_achieve; //绩效
					var  uc_wage_subsidy=data.uc_wage_subsidy==undefined?0:data.uc_wage_subsidy; //津贴工资
					var countWages=uc_wage_achieve+uc_wage_subsidy;//绩效+津贴工资
					
					if(parseFloat(actualDay)>0){
						 
					   uc_wages_baseTotal= ((((uc_wage_base+uc_wage_post)/dayCount)*actualDay)+countWages).toFixed(2);
					}else if(vacaCount>0&&vacaCount!=undefined){
						uc_wages_baseTotal=((((uc_wage_base+uc_wage_post)/dayCount)*dayCount+countWages)-uc_wages_dedu).toFixed(2);
					}
					 /* 计算应发结束  */
					 
					 /* 计算个税开始  */
					var uc_wage_socSec=data.uc_wage_socSec==undefined?0:data.uc_wage_socSec; //社保
					var uc_wage_accFund=data.uc_wage_accFund==undefined?0:data.uc_wage_accFund; //公积金
					var sum=(parseFloat(uc_wages_baseTotal)-uc_wage_socSec-uc_wage_accFund-5000);  //个税
					
			            
					var  uc_wage_tax=data.uc_wage_tax==undefined?0:data.uc_wage_tax;//个税变量
					 /* 计算个税结束  */
					 
					 /* 计算实发  开始*/
					var realhair= parseFloat(uc_wages_baseTotal-uc_wage_tax-uc_wage_socSec-uc_wage_accFund).toFixed(2);
					 /* 计算实发  结束*/
					 
					 
				$.ajax({
					type : 'POST',
					url : 'save_userWages.do',
					data :{"data":JSON.stringify(data),
						   "finance_wages_vacaCount":xiujiaCount,
						   "uc_wages_dedu":uc_wages_dedu,
						   "uc_wages_baseTotal":uc_wages_baseTotal,
						   "uc_wage_tax":uc_wage_tax,
						   "company_name":company_name,
						   "userid":userid,
						   "uc_wage_realhair":realhair,
						   "uc_wage_actualDay":actualDay,
						   "uc_wage_center_name":center_name,
						   },
					     dataType:"json",
					     success : function(data) {
								if(data.Success){
									 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {				
										 var btn=document.createElement("BUTTON");
										 var dom = obj.tr[2].childNodes[0].lastChild.children[0];
										 dom.replaceChild(btn,dom.childNodes[1]);
										 dom.childNodes[1].innerHTML = '存档';
										 dom.childNodes[1].setAttribute('class','layui-btn layui-btn-xs layui-btn-disabled');
		 	                           
		 	                            });
								}else{
									
									  layer.msg(data.Msg, {icon: 5, time: 3000,shade:[0.3]});
								}
					        }
			          });
		         }
		          
       });

	
		
     
       //头工具栏事件
       //工具栏事件
       table.on('toolbar(demo)', function(obj){
         var checkStatus = table.checkStatus(obj.config.id);
         switch(obj.event){
           case 'getCheckData':
        		location.href = "userWagesLib.do?" + $.param({
    				'type' : "whole", 
    		 	});
  
           break;
           case 'updateWages':
        	 
        		$.ajax({
					type : 'POST',
					url : 'update_Wages.do',
					data :{},
					success : function(data) {
					
							if(data.Success){
								 layer.msg(data.Msg, {icon: 6, time: 3000,shade:[0.3]}, function () {
	 	                                location.reload();
	 	                            });
							}else{
								
								  layer.msg(data.Msg, {icon: 5, time: 3000,shade:[0.3]});
							}
				        }
		          });
   		 
          break;
         };
       
     });
    
    
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	   var username=$('#username').val();
    		    	   var company_id=$('#company_id').val(); 
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'username':username,
     			    		 'company_id':company_id


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
		

		//格式化休假天数
	function finance_wages_vacaCount (obj){
	    var dayCount=getLastDays().length;
		var vacaCount =obj.finance_wages_attCount;
		var leaveCount =obj.finance_wages_leaveCount==null?0:obj.finance_wages_leaveCount;	
		var xiujiaCount=dayCount-(vacaCount+leaveCount);
	  	return xiujiaCount;
	  }
		
		
    //格式考勤扣除
	function uc_wages_dedu(obj){
    
		    var day_count= obj.finance_wages_leaveCount==undefined?0:obj.finance_wages_leaveCount;
			var uc_wage_base=obj.uc_wage_base==undefined?0:obj.uc_wage_base; //基本工资
			var uc_wage_post=obj.uc_wage_post==undefined?0:obj.uc_wage_post; //岗位工资
			var actualDay=obj.uc_wage_actualDay==undefined?0:obj.uc_wage_actualDay; //实际出勤天数
			  var dateCount=getLastDays().length;
		   if(day_count>=0 && actualDay==0){
			   
			 	return (((parseFloat(uc_wage_base)+parseFloat(uc_wage_post))/dateCount)*day_count).toFixed(2);
			   
		   }else if(actualDay>0){
			 	return (((parseFloat(uc_wage_base)+parseFloat(uc_wage_post))/dateCount)*(dateCount-actualDay)).toFixed(2);;
		   } 	
	    }	

      //格式化应发工资
	function uc_wages_baseTotal(obj){
		var  uc_wage_base=obj.uc_wage_base==undefined?0:obj.uc_wage_base; //基本工资
		var  uc_wage_post=obj.uc_wage_post==undefined?0:obj.uc_wage_post; //岗位工资
		var  uc_wage_achieve=obj.uc_wage_achieve==undefined?0:obj.uc_wage_achieve; //绩效
		var  uc_wage_subsidy=obj.uc_wage_subsidy==undefined?0:obj.uc_wage_subsidy; //津贴工资
		var  attCount=obj.finance_wages_attCount; //出勤天数	
		var  actualDay=obj.uc_wage_actualDay==undefined?0:obj.uc_wage_actualDay; //实际出勤天数
		var countWages=uc_wage_achieve+uc_wage_subsidy;//绩效+津贴工资
		var	dateCount=getLastDays().length;// 上个月天数
		if(parseFloat(actualDay)>0){
		
			return ((((uc_wage_base+uc_wage_post)/dateCount)*actualDay)+countWages).toFixed(2);
		}else if(attCount>0&&attCount!=undefined){
			
			return ((((uc_wage_base+uc_wage_post)/dateCount)*dateCount+countWages)-uc_wages_dedu(obj)).toFixed(2);
		}
	 }

		//获取上个月天数
		function getLastDays(){
		    var now = new Date;
		    now.setMonth(now.getMonth() - 1);
		    now.setDate(1);
		    var next = new Date;
		    next.setDate(1);
		    var arr = [];
		    while(now < next){
		        arr.push(now.getDate());
		        now.setDate(now.getDate() + 1);
		    }
		    return arr;
		}
		
	
		
		//格式化实发工资
		function arealhair(obj){
			var  uc_wage_tax=obj.uc_wage_tax==undefined?0:obj.uc_wage_tax; //基本工资
			return (uc_wages_baseTotal(obj)-uc_wage_tax-obj.uc_wage_socSec-obj.uc_wage_accFund).toFixed(2);
		}
		
		
         //提交
		function submitWages(){
			$.ajax({
				type : 'POST',
			    url :'submitWages.do',
				data :$("#form1").serialize(),
				success : function(data) {
					if (data.Success) {
						 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
								location.reload(true);
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