//画图开始
var canvas1 = document.getElementById("canvas1");
var canvas2 = document.getElementById("canvas2");
var canvas3 = document.getElementById("canvas3");
var canvas4 = document.getElementById("canvas4");
StokeCicle(canvas1, 0.9);
StokeCicle(canvas2, 0.8);
StokeCicle(canvas3, 0.7);
StokeCicle(canvas4, 0.7);
function StokeCicle(obj, N) {
	if (obj.getContext) {
		var context = obj.getContext("2d");
	} else {
		alert("你的浏览器不支持");
	}
	context.beginPath();
	context.arc(100, 100, 80, 0, 2 * N * Math.PI, false);
	context.strokeStyle = "#30bdac";
	context.lineWidth = "10";
	context.stroke();
	context.closePath();
}
//画图结束
//改变textarea的颜色开始
 $(".textareaWidth").click(function(){


 })
 
 

