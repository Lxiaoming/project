<!DOCTYPE html>

  <link rel="stylesheet" href="<%=request.getContextPath()%>/res/css/demo.css"  type="text/css" />
  <link rel="stylesheet" href="<%=request.getContextPath()%>/res/css/zTreeStyle.css" type="text/css" />
  <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery.ztree.core.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery.ztree.exedit.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery.ztree.excheck.min.js"></script>
  
  
  <style>
.ztree * {
	    font-size: 15px;
	
}

ul.ztree{

	    height: 600px;
	    with:100%;
}
</style>
  
  <SCRIPT LANGUAGE="JavaScript">
   var zTreeObj;
   var data=$("#roleName").val();
   
   var setting = {
   	check: {
		enable: true,
		chkStyle: "checkbox",
		chkboxType: { "Y": "p", "N": "s" },
		chkDisabledInherit: true
	}
   
   };
   var zNodes;
   $(document).ready(function(){
   
   		$.ajax({
				async : false,
				cache : false,
				type : 'POST',
				dataType : "json",
				data :{'roleName':data},
				url : "menuTreeList.do",
				error : function() {
					alert('è¯·æ±å¤±è´¥');
				},
				success : function(data) {    
					zNodes = data; 
				}
			});
   
      	zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
      
   });
  </SCRIPT>
