(function($){

	var tipsopt;
	$.fn.tips=function(options){
		var scrollTop=document.documentElement.scrollTop||document.body.scrollTop; //滚动的高度
		
		tipsopt = $.extend({}, $.fn.tips.defaults, options);
		var el=tipsopt.tipName.split("#").join("");
		$(tipsopt.tipInsert).append("<div id="+el+" class='cl'></div>");
		$(tipsopt.tipName).append("<p class='text-white row'>"+tipsopt.tipContent+"</p>").css({"background":"#000","-moz-opacity":"0.7","color":"#fff","opacity":".70", 
            "filter":"alpha(opacity=70)","padding":"8px 0","top":-$(tipsopt.tipName).outerHeight()+'px',"position":"absolute","width":"100%","display":"none","left":0,
            "-webkit-transition":"top .5s ease-in .1s",
            "-o-transition":"top .5s ease-in .1s",
            "-moz-transition":"top .5s ease-in .1s",
            "transition":"top .5s ease-in .1s"}).addClass("text-center");
        $(tipsopt.tipName).css({"top":(scrollTop-44)+'px',"display":"block"});
        
        $("body").css({"overflow-y":"hidden"});
          
        var permit=0;
        document.body.addEventListener('touchmove', function (event) {//阻止下滑
        if(permit==0){
              event.preventDefault();
        }
        }, false);
        
        setTimeout(function() { 
            $(tipsopt.tipName).remove();
            if(tipsopt.tipArea){
        	   $(tipsopt.tipArea).val("请输入您对我们的反馈");
            }
            permit=1;
            $("body").css({"overflow-y":"scroll"});
            $(".dealfeedback .situation-table,.btn-tip").css({"background":"#dcdcdc"});
            $(".situation-table .textarea").css({"background":"#eee"});
        },2000);
     
	}
	
})(jQuery)