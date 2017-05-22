define(['ajaxhelper', 'utility'], function(ajaxHelper, util) {
    var CompleteSuccess = {
    	intervalId: null,
    	count: 3,
        initialize: function() {
        	var me = this;
			if(me.intervalId)
				clearInterval(me.intervalId);
			me.intervalId = setInterval(function() {
				me.count--;
				if(me.count == 0){
					window.location = "my_question_list.html";
				}
				me._updateTime(me.count);
    		}, 1000);
        },
        _updateTime:function(count){
			$("#i_time").text(count + "s");
		}
    };
    return CompleteSuccess;
});