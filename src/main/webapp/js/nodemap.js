function createNodemap() {
	var app = $("#loginName").val();
	if (app == "") {
		alert("请选择App");
		return;
	}
	$("#nodemap").empty();
	$("#chart").empty();
	$.ajax({
		type : 'get',
		url : app,
		//data : formParam,
		cache : false,
		dataType : 'html',
		beforeSend:function(XMLHttpRequest){            
            $("#loading").html("<img src='images/loading.gif' />"); 
       },
		success : function(data) {	
			//console.log(data);
			$("#loading").empty();
			$("#nodemap").html(data);
			//$("#chart").empty();
			$("#org").jOrgChart({
				chartElement : '#chart',
				dragAndDrop : true
			});
		}
	});
	
}