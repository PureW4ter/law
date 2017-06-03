"use strict";

define([],function(){
	var ListScroll = {
		myScroll:"",
		dragTarget:"",
		getNewList:false,
		enabled:true,
		initialize :function(dragTarget){
			this.dragTarget = dragTarget;
			this._loaded();
		},
		_loaded:function(){
	        this.myScroll=new iScroll("i_mainbox",{
	                useTransition : true,
	                topOffset : 0,
	                onRefresh : function() {
	                    $("#i_refresh_content").text('上拉刷新...'); 
	                },
	                onScrollMove : function() {
	                    if(this.y<(this.maxScrollY-5)){
	                        $("#i_refresh_content").text('松手开始更新...');
	                        this.scollObj.getNewList = true;
	                    }
	                },
	                onScrollEnd : function() {
	                	if(this.scollObj.getNewList && this.scollObj.enabled){
		                    $("#i_refresh_content").text('加载中...');    
		                    this.scollObj._pullUpAction();
	                    }
	                }
	        });
	        //为了iscroll 事件响应
	        this.myScroll["scollObj"] = this;
    	},
		_pullUpAction:function () {
			this.getNewList = false;
		    this.dragTarget.dragFresh();
    	},
    	refresh: function(){
    		this.myScroll.refresh();
    	},
    	setDisabled: function(){
    		this.enabled = false;
    	},
    	setEnabled: function(){
    		this.enabled = true;
    	}

    };
	return ListScroll;
});

