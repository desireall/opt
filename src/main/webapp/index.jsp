<%@ page pageEncoding="UTF-8"%>
<html>
<head>
  <title> 欢迎使用  </title>


<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript">
function test(){
	setButton(true);
$.ajax({
    type: "post", 
    url: "/opt/testServlet", 
    dataType: "text", 
    timeout : 10000,
    success: function (data) {
    	setButton(false);
        alert(data);
    }, 
    error: function() {
    	setButton(false);
        alert("网络异常，请稍后重试");

    } 
});
}


function reload(){
	setButton(true);
	$.ajax({
	    type: "post", 
	    url: "/opt/reloadServlet", 
	    dataType: "text", 
	    timeout : 20000,
	    success: function (data) {
	    	setButton(false);
	        alert(data);
	    }, 
	    error: function() {
	    	setButton(false);
	        alert("网络异常，请稍后重试");

	    } 
	});
	}


function restart(compile){
	setButton(true);
	$.ajax({
	    type: "post", 
	    url: "/opt/restartServlet", 
	    dataType: "text", 
	    data : {
	       compile : compile
	    },
	    timeout : 50000,
	    success: function (data) {
	    	setButton(false);
	    	alert(data);
	    }, 
	    error: function() {
	    	setButton(false);
	        alert("打包重启成功 ,不能进入游戏联系服务器！");
	    } 
	});
	}


function setButton(canuse){
	var element = $(".aa");
	for(i = 0; i < element.length ; i++){
		 $(element[i]).attr("disabled",canuse)
	}
}


function start(){
	setButton(true);
$.ajax({
    type: "post", 
    url: "/opt/remoteShellServlet", 
    dataType: "text", 
    timeout : 10000,
    success: function (data) {
    	setButton(false);
        alert(data);
    }, 
    error: function() {
    	setButton(false);
        alert("网络异常，请稍后重试");

    } 
});
}

</script>

</head>

<body>
<h2>欢迎使用</h2>

<input type="button" name="重载服务器" value="重载服务器" onclick="reload()" class="aa"/>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>

<input type="button" name="重启更新ecf" value="重启更新ecf" onclick="restart(false)" class="aa"/>
(重启之前请T6群告知)

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>


<!--
<input type="button" name="重新打包启动" value="重新打包启动" onclick="restart(true)" class="aa"/>
-->

<p/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<input type="button" name="测试" value="测试" onclick="test()" class="aa"/>
<!-- 
-->

<p/>


<!-- 
<input type="button" name="测试启动" value="测试启动" onclick="start()" class="aa"/>
-->

</body>
</html>
