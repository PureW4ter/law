define(["text!./nav_bar.html","../utility"],function(e,t){var n={initialize:function(t,n){$("html").append(e),this.navbartplfun=_.template($("#i_navbar_tpl").html()),$("#i_navbar_tpl").remove(),$("#"+t).html(this.navbartplfun({nav_select:n})),this._registEvent()},_registEvent:function(){$(".i_tree_level ").off("click",this._list).on("click",{ctx:this},this._list)},_list:function(e){$(e.target).next().toggle(),$(e.target).find("span").toggleClass("right-icon")}};return n});