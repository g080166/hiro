$(document).ready(function(data){
});

function addUser(){
	var managerName = $("#username").val();
	var cookie = $("#cookie").val();
	var sysUrl = $("#sysUrl").val();
	$.ajax({
		url:"/account/addAccount",
		type:"post",
		data:{"managerName":managerName,"cookie":cookie,"sysUrl":sysUrl},
		success:function(data){
			if(data.ret == "0"){
				alert("添加成功!");
			}else{
				alert("添加失败!");
			}
		}
	});
}
