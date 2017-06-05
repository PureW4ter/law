/**
 * Created by Totooria Hyperion on 2016/7/26.
 */
define(['ajaxhelper',
	'text!./up_xiniu.html?' + (+new Date()),
	"jquery",
	"lib/MD5",
	"lib/qiniu/plupload.full.min",
	"lib/qiniu/qiniu"
], function (ajaxHelper, xiniuTpl, $, md5, plupload, qiniu, zh_CN) {
	window.newToken = false;
	var upXiniu = function (selector, folderName, successFun, errFun) {
		//header
		this.root = $(selector);
		if (this.root.length == 1) {
			this.selector = "j_qiniuContainer" + this.__proto__.count;
			this.upbtn = "j_qiniuBtn" + this.__proto__.count;
			this.__proto__.count++;
			this.folderName = folderName;
			this.successFun = successFun;
			this.errFun = errFun;
			this.gettingToken = false;
			return this._sendRequest();
		} else {
			var n = [];
			for (var i = 0; i < this.root.length; i++) {
				n.push(new upXiniu(this.root[i], folderName, successFun, errFun));
			}
			return n;
		}
	};

	$("body").append(xiniuTpl);
	upXiniu.prototype.xiniuTplFun = _.template($("#i_xiniu_tpl").html());
	$("#i_xiniu_tpl").remove();


	upXiniu.prototype.count = 0;
	upXiniu.prototype._sendRequest = function () {
		if ((!window.localStorage.xiniuToken && !this.gettingToken) || !window.newToken) {
			this.gettingToken = true;
			var param2 = {};
			var _this = this;
			ajaxHelper.get("http://" + window.frontJSHost + "/api/pic/token",
				param2, this, function (data) {
					window.localStorage["xiniuToken"] = data.r;
					window.newToken = true;
					_this.gettingToken = false;
				}, this.error, false, false);
		}
		return this._render();
	};
	upXiniu.prototype._render = function (data) {

		var renderParam = {
			container: this.selector,
			upbtn: this.upbtn
		};
		this.root.html(this.__proto__.xiniuTplFun(renderParam));


		var _this = this;
		var QiniuSDK = new QiniuJsSDK();
		var uploader = QiniuSDK.uploader({
			runtimes: 'html5,flash,html4',
			browse_button: this.upbtn,
			container: this.selector,
			drop_element: this.selector,
			max_file_size: '1000mb',
			flash_swf_url: '../../bower_components/plupload/js/Moxie.swf',
			dragdrop: true,
			chunk_size: '4mb',
			uptoken: window.localStorage["xiniuToken"],
			unique_names: false,
			save_key: false,
			domain: window.qiniuDomain,
			get_new_uptoken: false,
			auto_start: true,
			log_level: 5,
			init: {
				'FilesAdded': function (up, files) {

				},
				'BeforeUpload': function (up, file) {

				},
				'UploadProgress': function (up, file) {

				},
				'UploadComplete': function () {

				},
				'FileUploaded': function (up, file, info) {
					console.log(typeof _this.successFun);
					if (typeof _this.successFun == "function") {
						_this.successFun.apply(_this.root[0], arguments);
					}
				},
				'Error': function (up, err, errTip) {
					if (typeof _this.errFun == "function") {
						_this.errFun.apply(_this.root[0], arguments);
					}
				},
				'Key': function (up, file) {
					var key = _this.folderName;
					if (key.indexOf("/") == 0) {
						key = key.substr(1);
					}
					if (key[key.length - 1] == "/") {
						key = key.substr(0, key.length - 1);
					}
					var date = new Date();
					var arrDay = [
						date.getFullYear(),
						date.getMonth() + 1 > 10 ? date.getMonth() + 1 : "0" + (date.getMonth() + 1),
						date.getDate() > 10 ? date.getDate() : "0" + date.getDate()
					];
					var millionSecond = date.getTime().toString();
					var extname = file.name.substr(file.name.lastIndexOf("."));
					// do something with key
					//return file.name;
					//return "TB18NbfLFXXXXb1XpXXXXXXXXXX-1224-1264.jpg";
					key += "/" + arrDay.join("") + "/" + millionSecond.substr(4) + extname;
					return key;
				}
			}
		});

		this._registEvent();
		return uploader;
	};
	upXiniu.prototype._registEvent = function () {

	};
	upXiniu.prototype.error = function (xhr) {

	};

	return upXiniu;
});