package com.destinesia.app.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.destinesia.base.LJConstants;
import com.destinesia.base.Utility;
import com.destinesia.entity.Album;
import com.destinesia.entity.View;
import com.destinesia.entity.dto.AlbumDTO;
import com.destinesia.service.IAlbumService;
@Controller
@RequestMapping(value = "/album")
public class AlbumController extends BaseController{
	@Autowired
	private IAlbumService albumService;
	
	/**
	 * @api {POST} /album/detail 获取专辑详情
	 * @apiPermission none
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 根据专辑的ID获取专辑详情
	 *
	 * @apiParam {String} id 专辑的ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "id": "4b8d5e5ec1da0fad27b786387030ecd4"
	 * }
	 *
	 * @apiSuccess (200) {String} id 专辑id
	 * @apiSuccess (200) {String} name 专辑名称
	 * @apiSuccess (200) {String} cover 专辑封面
	 * @apiSuccess (200) {Region} nickName 用户昵称
	 * @apiSuccess (200) {String} region 地区对象
	 * @apiSuccess (200) {String} region.country 国家
	 * @apiSuccess (200) {String} region.province 省
	 * @apiSuccess (200) {String} region.city 城市
	 * @apiSuccess (200) {String} region.district 地区
	 * @apiSuccess (200) {String} createDate 专辑创建日期
	 * @apiSuccess (200) {int} votes 专辑点赞数量
	 * @apiSuccess (200) {int} viewed 专辑浏览数量
	 * @apiSuccess (200) {Array} picList 专辑图片列表
	 * @apiSuccess (200) {double} distance 专辑图片中用户走过的距离
	 * @apiSuccess (200) {String} road 专辑图片中用户走过的路径
	 * 
	 * @apiSuccessExample {json} 返回样例:
	 *	 {
	 *   "userNickName": "love destinesia",
 	 *   "cover": "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
	 *   "picList": [
	 *    {
	 *      "albumId": "7e3efc3f847749dfb4f0d07f2082e048",
	 *      "longitude": 114.19084,
	 *      "latitude": 39.37021,
	 *      "altitude": 2,
	 *      "regionDTO": {
	 *        "province": "山西省",
	 *        "city": "大同市",
	 *        "district": "灵丘县",
	 *        "id": "886e5a4982574ccca71f3d113513abc3",
	 *        "country": "中国"
	 *      },
	 *      "description": "Great Day2 pic desc",
	 *      "name": "Great Day2 pic",
	 *      "id": "ee72106de84c4cdbb89a6a47390737d5",
	 *      "type": 0,
	 *      "path": null
	 *    },
	 *    {
	 *      "albumId": "7e3efc3f847749dfb4f0d07f2082e048",
	 *      "longitude": 121.47617,
	 *      "latitude": 41.672394,
	 *      "altitude": 2,
	 *      "regionDTO": {
	 *        "province": "辽宁省",
	 *        "city": "锦州市",
	 *        "district": "义县",
	 *        "id": "c719d22f96b644d0b5573ba734548860",
	 *        "country": "中国"
	 *      },
	 *      "description": "Great Day pic desc",
	 *      "name": "Great Day pic",
	 *      "id": "0a549f3950134df9b0bd6bd09aa8daf3",
	 *      "type": 0,
	 *      "path": null
	 *    }
	 *  ],
	 *  "longitude": 121.199777,
	 *  "latitude": 23.840343,
	 *  "altitude": 2,
	 *  "userGrade": 1,
	 *  "picCount": 2,
	 *  "token": null,
	 *  "description": "Great Day",
	 *  "name": "Great Day",
	 *  "id": "7e3efc3f847749dfb4f0d07f2082e048",
	 *  "distance":"33.2",
	 *  "road":"39.930429,116.385009,1,-78,32,0",
	 *  "region": {
	 *    "province": "台湾省",
	 *    "city": "南投县",
	 *    "district": "信义乡",
	 *    "id": "b729364b860348bea46d4ef5217e8bb6",
	 *    "country": "中国"
	 *  }
	 *}
	 */
	@RequestMapping(value = "/detail", method=RequestMethod.POST)
	@ResponseBody
	public Object detail(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String id = request.getParameter("id");
		Album album =  albumService.getAlbumById(id);
		if(album == null)
			return null;
		return AlbumDTO.revert(album);
	}

	/**
	 * @throws UnsupportedEncodingException 
	 * @api {POST} /album/list 获取专辑列表
	 * @apiPermission none
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 获取用户专辑列表，会按照创建时间排序，最新创建的在第一个
	 *
	 * @apiParam {int} type 获取专辑类型，1为个人专辑，2为最新专辑（审核），3为根据地理位置，4为关键字，5为时间, 6为推荐
	 * @apiParam {int} from 第几页，从0开始
	 * @apiParam {int} size 每页数量，不传为30
	 * @apiParam {double} lat 为维度，type为3使用
	 * @apiParam {double} lon 为经度，type为3使用
	 * @apiParam {String} keys 关键词，使用空格分隔，type为4使用
	 * @apiParam {String} date 时间，格式为 YYYY-MM-DD，type为5的时候使用
	 * @apiParamExample {json} 请求样例：
	 * {
	 *    "type": 4,
	 *    "from": 5,
	 *    "size": 10,
	 *    "lat": 23.840343,
	 *    "lon": 121.199777,
	 *    "keys": "中国 台湾省"
	 *    "date": "2016-12-30"
	 * 
	 * }
	 *
	 * @apiSuccess (200) {String} id 专辑id
	 * @apiSuccess (200) {String} name 专辑名称
	 * @apiSuccess (200) {String} cover 专辑封面
	 * @apiSuccess (200) {String} userNickName 用户昵称
	 * @apiSuccess (200) {String} userGrade 用户等级
	 * @apiSuccess (200) {String} picCount 图片数量
	 * @apiSuccess (200) {String} createDate 专辑创建日期
	 * @apiSuccess (200) {double} distance 专辑图片中用户走过的距离
	 * @apiSuccess (200) {String} road 专辑图片中用户走过的距离
	 * @apiSuccess (200) {Region} region 地区对象
	 * @apiSuccess (200) {String} region.country 国家
	 * @apiSuccess (200) {String} region.province 省
	 * @apiSuccess (200) {String} region.city 城市
	 * @apiSuccess (200) {String} region.district 地区
	 * @apiSuccess (200) {int} votes 专辑点赞数量
	 * @apiSuccess (200) {int} viewed 专辑浏览数量
	 * @apiSuccessExample {json} 返回样例:
	 * 	 [
	 *   {
	 *     "userNickName": "love destinesia",
	 *     "cover": "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
	 *     "picList": null,
	 *     "longitude": 121.199777,
	 *     "latitude": 23.840343,
	 *     "altitude": 2,
	 *     "userGrade": 1,
	 *     "picCount": 2,
	 *     "token": null,
	 *     "description": "Great Day",
	 *     "name": "Great Day",
 	 *     "id": "7e3efc3f847749dfb4f0d07f2082e048",
 	 *     "distance":"33.2",
	 *     "road":"39.930429,116.385009,1,-78,32,0",
	 *     "region": {
	 *       "province": "台湾省",
	 *       "city": "南投县",
	 *       "district": "信义乡",
	 *       "id": "b729364b860348bea46d4ef5217e8bb6",
	 *       "country": "中国"
	 *     }
	 *   },
	 *   {
	 *     "userNickName": "love destinesia",
	 *     "cover": "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
	 *     "picList": null,
	 *     "longitude": 121.199777,
	 *     "latitude": 23.840343,
	 *     "altitude": 2,
 	 *     "userGrade": 1,
	 *     "picCount": 2,
	 *     "token": null,
	 *     "description": "Great Day",
	 *     "name": "Great Day",
 	 *     "id": "d63899b95bfb4f8c8894a544d2314e03",
 	 *     "distance":"33.2",
	 *     "road":"39.930429,116.385009,1,-78,32,0",
 	 *     "region": {
  	 *       "province": "台湾省",
  	 *       "city": "南投县",
  	 * 	     "district": "信义乡",
	 *       "id": "b729364b860348bea46d4ef5217e8bb6",
	 *       "country": "中国"
	 *     }
	 *   }
	 * 	]
	 */
	@RequestMapping(value = "/list", method=RequestMethod.POST)
	@ResponseBody
	public Object list(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException, UnsupportedEncodingException {
		
		String token = request.getParameter("token");
		String type = request.getParameter("type");
		int from = request.getParameter("from")==null?0:Integer.parseInt(request.getParameter("from"));
		int size = request.getParameter("size")==null?LJConstants.pageSize:Integer.parseInt(request.getParameter("size"));
		List<Album> albums = null;
		if (type == null || type.length() == 0 || Integer.parseInt(type) == 1) {
			//my
			albums = albumService.getMyAlbums(token, from, size);
		}else if(Integer.parseInt(type) == 2){
			//new
			albums = albumService.getNewAlbums(token, from, size);
		}else if(Integer.parseInt(type) == 3){
			//local
			double lat = Double.parseDouble(request.getParameter("lat"));
			double lon = Double.parseDouble(request.getParameter("lon"));
			albums =  albumService.getNearAlbums(lat, lon, from, size);
		}else if(Integer.parseInt(type) == 4){
			//keys
			String pkey = new String(
					request.getParameter("keys").getBytes("ISO-8859-1"),"UTF-8");
			List<String> keys = new ArrayList<String>();
			if(pkey!=null){
				Collections.addAll(keys, pkey.split(" "));
			}
			albums = albumService.getAlbumsByKeys(keys, from, size);
		}else if(Integer.parseInt(type) == 5){
			//date
			String date = request.getParameter("date");
			albums = albumService.getAlbumsByDate(date, from, size);
		}else if(Integer.parseInt(type) == 6){
			//promotion
			albums = albumService.getAlbumsByPromotioned(from, size);
		}
		if (albums == null)
			return new ArrayList<Album>();
		List<AlbumDTO> albumDTOs = new ArrayList<AlbumDTO>();
		for (Album album : albums) {
			AlbumDTO dto = AlbumDTO.revert(album);
			albumDTOs.add(dto);
		}
		return albumDTOs;
	}

	/**
	 * @api {POST} /album/listbyloc 根据距离获取专辑列表
	 * @apiPermission none
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 获取用户专辑列表，会按照创建时间排序，最新创建的在第一个
	 * @deprecated Use list method
	 *
	 * @apiParam {float} lat 纬度
	 * @apiParam {float} lon 经度
	 * @apiParam {int} from 第几页，从0开始
	 * @apiParam {int} size 每页数量，不传为30
	 * 
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "lat": "23.8403430000", 
	 *    "lon": "121.1997770000",
	 *    "from": 5,
	 *    "size": 10
	 * }
	 *
	 * @apiSuccess (200) {String} id 专辑id
	 * @apiSuccess (200) {String} name 专辑名称
	 * @apiSuccess (200) {String} cover 专辑封面
	 * @apiSuccess (200) {String} userNickName 用户昵称
	 * @apiSuccess (200) {String} userGrade 用户等级
	 * @apiSuccess (200) {String} picCount 图片数量
	 * @apiSuccess (200) {double} distance 专辑图片中用户走过的距离
	 * @apiSuccess (200) {String} road 专辑图片中用户走过的距离
	 * @apiSuccess (200) {String} createDate 专辑创建日期
	 * @apiSuccess (200) {Region} region 地区对象
	 * @apiSuccess (200) {String} region.country 国家
	 * @apiSuccess (200) {String} region.province 省
	 * @apiSuccess (200) {String} region.city 城市
	 * @apiSuccess (200) {String} region.district 地区
	 * @apiSuccess (200) {int} votes 专辑点赞数量
	 * @apiSuccess (200) {int} viewed 专辑浏览数量
	 * @apiSuccessExample {json} 返回样例:
	 * 	 [
	 *   {
	 *     "userNickName": "love destinesia",
	 *     "cover": "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
	 *     "picList": null,
	 *     "longitude": 121.199777,
	 *     "latitude": 23.840343,
	 *     "altitude": 2,
	 *     "userGrade": 1,
	 *     "picCount": 2,
	 *     "token": null,
	 *     "description": "Great Day",
	 *     "name": "Great Day",
 	 *     "id": "7e3efc3f847749dfb4f0d07f2082e048",
	 *     "region": {
	 *       "province": "台湾省",
	 *       "city": "南投县",
	 *       "district": "信义乡",
	 *       "id": "b729364b860348bea46d4ef5217e8bb6",
	 *       "country": "中国"
	 *     }
	 *   },
	 *   {
	 *     "userNickName": "love destinesia",
	 *     "cover": "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
	 *     "picList": null,
	 *     "longitude": 121.199777,
	 *     "latitude": 23.840343,
	 *     "altitude": 2,
 	 *     "userGrade": 1,
	 *     "picCount": 2,
	 *     "token": null,
	 *     "description": "Great Day",
	 *     "name": "Great Day",
 	 *     "id": "d63899b95bfb4f8c8894a544d2314e03",
 	 *     "region": {
  	 *       "province": "台湾省",
  	 *       "city": "南投县",
  	 * 	     "district": "信义乡",
	 *       "id": "b729364b860348bea46d4ef5217e8bb6",
	 *       "country": "中国"
	 *     }
	 *   }
	 * 	]
	 */
	@RequestMapping(value = "/listbyloc", method=RequestMethod.POST)
	@ResponseBody
	public Object listByLocation(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		int from = request.getParameter("from")==null?0:Integer.parseInt(request.getParameter("from"));
		int size = request.getParameter("size")==null?LJConstants.pageSize:Integer.parseInt(request.getParameter("size"));
		List<Album> albums =  albumService.getNearAlbums(lat, lon, from, size);
		 if(albums == null)
			 return null;
		 List<AlbumDTO> albumDTOs = new ArrayList<AlbumDTO>();
		 for(Album album : albums){
			 AlbumDTO dto = AlbumDTO.revert(album);
			 dto.setPicList(null);
			 albumDTOs.add(dto);
		 }
		return albumDTOs;
	}
	/**
	 * @api {POST} /album/save 新建/更新已有专辑
	 * @apiPermission none
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 新建/更新已有专辑，如果id为空，就代表添加，否则为更新
	 *
	 * @apiParam {String} id 专辑id
	 * @apiParam {String} name 专辑名称
	 * @apiParam {String} desc 专辑描述
	 * @apiParam {String} cover 专辑封面
	 * @apiParam {String} latitude 纬度
	 * @apiParam {String} longitude 经度
	 * @apiParam {String} altitude 海拔
	 * @apiParam {String} createDate 专辑创建时间，毫秒数
	 * @apiParam {Array}  pics 图片列表
	 * @apiParam {String} pics.path 图片地址
	 * @apiParam {int} pics.type 图片类型，1为图片，2为视频，3为AR
	 * @apiParam {String} pics.name 图片名称
	 * @apiParam {String} pics.desc 图片描述
	 * @apiParam {String} pics.latitude 图片拍摄点的纬度
	 * @apiParam {String} pics.longitude 图片拍摄点的经度
	 * @apiParam {String} pics.altitude 图片拍摄点的海拔
	 * @apiParam {String} pics.createDate 照片创建时间，毫秒数
	 * @apiParamExample {json} 请求样例：
	 * { 
     *	"token": "690208343a974ba0a1146ccd81b7757d",
     *	"name": "Great Day",
     *	"description": "Great Day",
     *	"longitude": 121.199777,
     *	"latitude": 23.840343,
     *	"altitude": 2,
     *	"cover": "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png",
     *  "createDate":"1484494253246",	
     *  "picList": [
     *	  {
     *	    "name": "Great Day pic",
     *	    "type": 1,
     *	    "path": "http://7xlovk.com2.z0.glb.qiniucdn.com/exchange/upload/roomPhoto/20160816/147133686654354.jpeg",
     *	    "description": "Great Day pic desc",
     *	    "longitude": 121.47617,
     *	    "latitude": 41.672394,
     *		"altitude": 2,
     *      "createDate":"1484494253246"
     *	  },
     *	  {
     *	    "name": "Great Day2 pic",
     *	    "type": 1,
     *	    "path": "http://7xlovk.com2.z0.glb.qiniucdn.com/exchange/upload/roomPhoto/20160816/147133686654480.jpeg",
     *	    "description": "Great Day2 pic desc",
     *	    "longitude": 114.19084,
     *	    "latitude": 39.37021,
     *		"altitude": 2,
     *      "createDate":"1484494253246"
     *	  }
     *	]
     * }
	 * 
	 * @apiSuccess (200) {String} id 专辑id
	 * 
	 * @apiSuccessExample {json} 返回样例:
	 *	{
	 *		"id":"690208343a974ba0a1146ccd81b7757d"
	 *	}
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"名称不能为空"} 
	 */
	@RequestMapping(value = "/save", method=RequestMethod.POST)
	@ResponseBody
	public Object save(HttpServletRequest request, HttpServletResponse response, @RequestBody AlbumDTO albumDto)
			throws ServletRequestBindingException {
		
		String token = albumDto.getToken();
		Album album = AlbumDTO.convert(albumDto);
		if(album.getId() == null){
			album.setId(Utility.generageId());
		}
		albumService.insertAlbum(album, token);
		Album albumRes = albumService.getAlbumById(album.getId());
		Map<String, String> resutl = new HashMap<String, String>();
		resutl.put("id", albumRes.getId());
		resutl.put("createDate", albumRes.getCreateDate().getTime()+"");
		return resutl;
	}
	

	
	/**
	 * @api {POST} /album/view 增加专辑访问次数
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 增加专辑流量次数，因为用户可以不登录，所以不需要用户id
	 * @apiParam {String} albumId 专辑ID
	 * @apiParam {String} deviceNO 专辑ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "albumId":"4b8d5e5ec1da0fad27b786387030ecd4",
	 * 	  "deviceNO":"dxedwewew"
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"相册不存在"} 
	 */
	@RequestMapping(value = "/view", method=RequestMethod.POST)
	@ResponseBody
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		
		String albumId = request.getParameter("albumId");
		String deviceNO = request.getParameter("deviceNO");
		View view = new View();
		view.setId(Utility.generageId());
		view.setAlbumId(albumId);
		view.setDeviceNO(deviceNO);
		view.setIpAddress(request.getRemoteAddr());
		albumService.addView(view);
	}
	
	/**
	 * @api {POST} /album/pass 审核不通过指定专辑
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 审核不通过指定专辑
	 * @apiParam {String} albumId 专辑ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "albumId":"4b8d5e5ec1da0fad27b786387030ecd4",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"相册不存在"} 
	 */
	
	@RequestMapping(value = "/unpass", method=RequestMethod.POST)
	@ResponseBody
	public boolean unpass(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		
		String albumId = request.getParameter("albumId");
		albumService.unpassAlbum(albumId, "");
		return true;
	}
	
	/**
	 * @api {POST} /album/pass 审核通过指定专辑
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 审核通过指定专辑
	 * @apiParam {String} albumId 专辑ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "albumId":"4b8d5e5ec1da0fad27b786387030ecd4",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"相册不存在"} 
	 */
	@RequestMapping(value = "/pass", method=RequestMethod.POST)
	@ResponseBody
	public boolean pass(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		
		String albumId = request.getParameter("albumId");
		albumService.passAlbum(albumId, "");
		return true;
	}
	
	/**
	 * @api {POST} /album/delete 删除指定专辑
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 删除指定的专辑
	 * @apiParam {String} albumId 专辑ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "albumId":"4b8d5e5ec1da0fad27b786387030ecd4",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"相册不存在"} 
	 */
	@RequestMapping(value = "/delete", method=RequestMethod.POST)
	@ResponseBody
	public boolean delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String token = request.getParameter("token");
		String id = request.getParameter("albumId");
		albumService.deleteAlbum(id, token);
		return true;
	}
	
	/**
	 * @api {POST} /album/promote 审核不通过指定专辑
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 审核不通过指定专辑
	 * @apiParam {String} albumId 专辑ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "albumId":"4b8d5e5ec1da0fad27b786387030ecd4",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"相册不存在"} 
	 */
	
	@RequestMapping(value = "/promote", method=RequestMethod.POST)
	@ResponseBody
	public boolean promote(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		
		String albumId = request.getParameter("albumId");
		albumService.promoteAlbum(albumId, "");
		return true;
	}
	/**
	 * @api {POST} /album/addpath 为用户增加出行路径，每日一条
	 * @apiGroup album
	 * @apiVersion 1.0.0
	 * @apiDescription 为用户增加一天的路径，如果传递的用户ID和时间已经存在一条路径，那么就把新路径附加到已有路径上
	 * @apiParam {String} path 用户路径
	 * @apiParam {String} date 时间，格式为毫秒数
	 * @apiParam {String} albumId 专辑ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "albumId":"4b8d5e5ec1da0fad27b786387030ecd4",
	 * 	  "path":"39.930429,116.385009,1,-78,32,0",
	 *    "date":"2016-12-31",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"无法保存"} 
	 */
	@RequestMapping(value = "/addpath", method=RequestMethod.POST)
	@ResponseBody
	public void addPath(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String token = request.getParameter("token");
		String path = request.getParameter("path");
		String date = request.getParameter("date");
		albumService.addPath(path, Long.parseLong(date), token);
		
	}
	
	@RequestMapping(value = "/manage", method=RequestMethod.POST)
	@ResponseBody
	public Object manage(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		int type = request.getParameter("type")==null?0:Integer.parseInt(request.getParameter("type"));
		int from = request.getParameter("from")==null?0:Integer.parseInt(request.getParameter("from"));
		int size = request.getParameter("size")==null?LJConstants.pageSize:Integer.parseInt(request.getParameter("size"));
		List<Album> albums = new ArrayList<Album>();
		switch(type){
			//待审核
			case 1:
				albums = albumService.getPassAlbums(0, from, size);
				break;
			//推荐
			case 2:
				albums = albumService.getAlbumsByPromotioned(from, size);
				break;
			//通过
			case 3:
				albums = albumService.getPassAlbums(1, from, size);
				break;
			//不通过
			case 4:
				albums = albumService.getPassAlbums(2, from, size);
				break;
			default:
				albums = albumService.getPassAlbums(0, from, size);
		}
		List<AlbumDTO> albumDTOs = new ArrayList<AlbumDTO>();
		for (Album album : albums) {
			AlbumDTO dto = AlbumDTO.revert(album);
			albumDTOs.add(dto);
		}
		return albumDTOs;
	}
	
	@RequestMapping(value = "/counts", method=RequestMethod.POST)
	@ResponseBody
	public Object counts(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		return albumService.getCounts();
		
	}
	
	@RequestMapping(value = "/countstoday", method=RequestMethod.POST)
	@ResponseBody
	public Object countstoday(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		return albumService.getCountsForToday();
		
	}
}