define([ 'component/nav_bar', 'component/header', 'ajaxhelper', 'utility' ],
		function(nav_bar, header, ajaxHelper, util) {
			var LawyerHome = {
				currentPage : 0,
				size : 30,
				initialize : function() {
					// nav_bar
					nav_bar.initialize("i_navbar", 7);
					header.initialize("i_header", "律师任务");
					this.mainBox = $('#i_mainbox');
					this.tplFun = _.template($("#i_tpl").html());
					this._sendRequest();
				},
				_sendRequest : function() {
					var userData = $.parseJSON(localStorage.userInfo);
					var params = {
						"lawyerId" : userData.id,
						"page" : this.currentPage,
						"size" : this.size
					};
					ajaxHelper.get("http://" + window.frontJSHost
							+ "/api/order/lawyer", params, this, this._render,
							null);
				},
				_render : function(data) {
					this.mainBox.html(this.tplFun({
						'result' : data
					}));
					this._registEvent();
				},
				_registEvent : function() {
					$('.j_reply').off("click", this._goReply).on("click",
							this._goReply);
				},
				_goReply : function(e) {
					var oid = $(e.target).parents("tr").data("id");
					var productId = $(e.target).parents("tr").data("pid");
					var productCode = $(e.target).parents("tr").data("pcode");
					if (productCode == util.PRODUCT_CODE_HUKOU
							|| productCode == util.PRODUCT_CODE_CHAFENG
							|| productCode == util.PRODUCT_CODE_HUKOU_XQ) {
						window.location = "diaocha_reply.html?id=" + oid;
					} else {
						window.location = "zixun_reply.html?id=" + oid;
					}
				}
			};
			return LawyerHome;
		});
