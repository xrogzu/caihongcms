var timeout=500;var closetimer=0;var ddmenuitem=0;function jsddm_open(){jsddm_canceltimer();jsddm_close();ddmenuitem=$('.down-list').show();}
function jsddm_close(){if(ddmenuitem)ddmenuitem.hide();}
function jsddm_timer(){closetimer=window.setTimeout(jsddm_close,timeout);}
function jsddm_canceltimer(){if(closetimer){window.clearTimeout(closetimer);closetimer=null;}}
$(document).ready(function(){$('#top .more').bind('mouseover',jsddm_open);$('#top .more').bind('mouseout',jsddm_timer);$('a.btn-follow').on('click',function(){var $this=$(this);var tuserid=$this.data('tuserid');$.ajax({url:'/ajax/addfollow',type:'post',dataType:'json',cache:false,data:{tuserid:tuserid,channelid:11,type:2},success:function(result){if('ok'==result.status){if(1==result.content){$this.html('取消');}else{$this.html('关注');}}else{alert(result.msg);}}});});$('a.btn-add').on('click',function(){var $this=$(this);var friendid=$this.data('tuserid');$.ajax({url:'/ajax/addfriend',type:'post',dataType:'json',cache:false,data:{friendid:friendid},success:function(result){if('ok'==result.status){if(1==result.content){$this.html('取消关注');}else{$this.html('加关注');}}else{alert(result.msg);}}});});});