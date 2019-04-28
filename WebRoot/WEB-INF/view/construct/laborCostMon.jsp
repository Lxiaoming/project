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
	        <input type="text" class="layui-input" name="hr_attend_date" id="hr_attend_date" placeholder="年份">
	      </div> 
	      <div class="layui-input-inline">
	        <input type="text" class="layui-input" name="construct_project_name" id="construct_project_name" placeholder="项目">
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
 <a class="layui-btn layui-btn-xs" lay-event="detail">查看打卡</a>
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
         ,url:'laborCostMons.do?companyId='+${companyId}
         ,toolbar: '#toolbarDemo'
         ,totalRow: true
    	 ,cols: [[ //标题栏
    	           
    	             {field: 'construct_project_name', title: '项目', width: 150,templet:'<div>{{name_num(d)}}</div>'}
    	            ,{field: 'construct_project_workTeam_category', title: '施工项目', width: 70,templet:'<div>{{category(d.construct_project_workTeam_category)}}</div>'}
    	            ,{field: 'hr_attend_workTeamId', title: '施工项目id', width: 70}
    	            ,{field: 'username', title: '班组', width: 80}
    	            ,{field: 'construct_project_workTeam_amount', title: '合同金额', width: 100,totalRowText: '合计'}
    	            ,{field: 'construct_project_workTeam_price', title: '单价', width: 70,}    	         
    	            ,{field: 'sumMoney1', title: '1月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney2', title: '2月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney3', title: '3月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney4', title: '4月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney5', title: '5月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney6', title: '6月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney7', title: '7月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney8', title: '8月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney9', title: '9月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney10', title: '10月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney11', title: '11月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney12', title: '12月', width: 80,totalRow: true}
    	            ,{field: 'sumMoney13', title: '累计付款', width: 70,totalRow: true}
    	 
    	            ,{field: 'balance', title: '累计余额', width: 100, templet:'<div>{{balance(d)}}</div>'}
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:175}
    	         
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650
    ,done: function (res, curr, count) {
    	merge(res, curr, count);
	}
  });
    laydate.render({
         elem: '#hr_attend_date'
         ,type:'year'
        ,format: 'yyyy'
      });
    
     //监听工具条
      //监听行单击事件（单击事件为：rowDouble）
    //监听单元格事件
    table.on('tool(demo)', function(obj){
      var data = obj.data;
      
      switch (obj.event) {
		case 'detail':
			location.href = "laborCostDate.do?" + $.param({
				'hr_attend_workTeamId' : data.hr_attend_workTeamId,
			
			}); 
		
			break;

		default:
			break;
		}

   
    

  });
		
     
      
   
    
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	var hr_attend_date=$("#hr_attend_date").val();
    		    	var construct_project_name=$("#construct_project_name").val();
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    	
     			    		'hr_attend_date':hr_attend_date,
     			    		'construct_project_name':construct_project_name

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
	
	
	 
     //格式化名称
	function name_num (row){
			
			return row.construct_project_name+row.manage_contract_num;
			
		}
		
     
	function balance (obj){
		
		var balance =Subtr(obj.construct_project_workTeam_amount,obj.sumMoney13);
		
		
			if(balance>=0){
				return balance;
			}else{
				return '<span style="color: #FF0000;">'+balance+'</span>';
			}
				
			
		} 
	
	//减法
	function Subtr(num1, num2) {
		var baseNum, baseNum1, baseNum2;
		var precision;// 精度 
		try {
			baseNum1 = num1.toString().split(".")[1].length;
		} catch (e) {
			baseNum1 = 0;
		}
		try {
			baseNum2 = num2.toString().split(".")[1].length;
		} catch (e) {
			baseNum2 = 0;
		}
		baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
		precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
		return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
	}

	//获取上个月时间
	function lastMonthDate() {
		var nowdays = new Date();
		var year = nowdays.getFullYear();
		var month = nowdays.getMonth();
		if (month == 0) {
			month = 12;
			year = year - 1;

		}
		if (month < 10) {
			month = '0' + month;
		}
		var startDate = year + '-' + month; //上个月第一天

		return startDate;
	}

	$(function() {
		$("#uc_wage_yearMon").val(lastMonthDate())//默认上个月
	})

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
		var columsName = [ 'construct_project_name' ];//需要合并的列名称
		var columsIndex = [ 0 ];//需要合并的列索引值

		for (var k = 0; k < columsName.length; k++)//这里循环所有要合并的列
		{
			var trArr = $(".layui-table-body>.layui-table").find("tr");//所有行
			for (var i = 1; i < res.data.length; i++) { //这里循环表格当前的数据
				var tdCurArr = trArr.eq(i).find("td").eq(columsIndex[k]);//获取当前行的当前列
				var tdPreArr = trArr.eq(mergeIndex).find("td").eq(
						columsIndex[k]);//获取相同列的第一列

				if (data[i][columsName[k]] === data[i - 1][columsName[k]]) { //后一行的值与前一行的值做比较，相同就需要合并
					mark += 1;
					tdPreArr.each(function() {//相同列的第一列增加rowspan属性
						$(this).attr("rowspan", mark);
					});
					tdCurArr.each(function() {//当前行隐藏
						$(this).css("display", "none");
					});
				} else {
					mergeIndex = i;
					mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
				}
			}
		}
	}
</script>

</body>
</html>