<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!--[if lt IE 8 ]><srcipt src="${RES_PATH }/admin/scripts/json2.min.js"></script><![endif]-->
<script src='<%=request.getContextPath()%>/res/admin/scripts/jquery.min.js'
	type="text/javascript"></script>

<script type="text/javascript"
	src='<%=request.getContextPath()%>/res/admin/scripts/jquery-easyui/jquery.easyui.min.js'></script>
<script
	src='<%=request.getContextPath()%>/res/admin/scripts/jquery-easyui/locale/easyui-lang-zh_CN.js'
	type="text/javascript"></script>
<script type="text/javascript">
	
/* function addSubPage(title,url){    
    
    var jq = top.jQuery;    

    if (jq("#tt").tabs('exists', title)){    
        jq("#tt").tabs('select', title);    
    } else {  
          var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';     
           jq("#tt").tabs('add',{    
                              title:title,    
                              content:content,    
                              closable:true    
                            });    
     }    
}  */   
	
function  formatDate(now)   {     
    var   year=now.getFullYear();     
    var   month=now.getMonth()+1;     
    var   date=now.getDate();     
    return   year+"-"+month+"-"+date;
    }     


 function dateformat(value, row, index) {
	if (value == undefined)
		return "";
	 var  now=new   Date(value); 
	return formatDate(now); 
   
}
 
	
	
</script>