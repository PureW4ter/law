var addressInit=function(e,t,n,r,i){function y(e){f=$(e.target).data("id"),T(e),E()}function b(e){l=$(e.target).data("id"),T(e),S()}function w(e){c=$(e.target).data("id"),T(e),x()}function E(){var e;s.data("id",f),d=$(".d"+f).index(),d<0&&(d=0),a="";for(var t=0;t<r.children[d].children.length;t++)e=r.children[d].children[t].name.split("_"),a+="<li class=d"+e[1]+" data-id="+e[1]+" >"+e[0]+"</li>";o.html(a).find("li").off("click",b).on("click",b),u.html("")}function S(){var e;o.data("id",l),v=$(".d"+l).index(),v<0&&(v=0),a="";for(var t=0;t<r.children[d].children[v].children.length;t++)e=r.children[d].children[v].children[t].name.split("_"),a+="<li class=d"+e[1]+" data-id="+e[1]+" >"+e[0]+"</li>";u.html(a),u.find("li").length>0?u.html(a).find("li").on("click",w):u.data("id",l)}function x(){u.data("id",c),$("#address_detail").val(c)}function T(e){var t=$(e.target).text(),n=$(e.target).parents(".btn-group");n.find("button:eq(0)").text(t),n.nextAll(".btn-group").attr("data-id","0").find("button:eq(0)").text("选择")}var s=$(e),o=$(t),u=$(n),a="",f,l,c,h=typeof arguments[4]=="string"?arguments[4]:null,p,d,v,m;for(var g=0;g<r.children.length;g++)p=r.children[g].name.split("_"),a+="<li class=d"+p[1]+" data-id="+p[1]+" >"+p[0]+"</li>";s.html(a).find("li").on("click",y);if(h!=null){var p=i.split(".");p.length>2?(f=p[0],l=p[1],c=p[2],E(),S(),x(),s.data("id",f).parents(".btn-group").find(".j_default").text($(".d"+f).text()),o.data("id",l).parents(".btn-group").find(".j_default").text($(".d"+l).text()),u.data("id",c).parents(".btn-group").find(".j_default").text($(".d"+c).text())):(s.data("id","").parents(".btn-group").find(".j_default").text("选择"),o.data("id","").parents(".btn-group").find(".j_default").text("选择"),u.data("id","").parents(".btn-group").find(".j_default").text("选择"))}h=f+l+c};