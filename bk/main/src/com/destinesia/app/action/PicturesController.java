package com.destinesia.app.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.destinesia.base.Utility;
import com.destinesia.entity.Vote;
import com.destinesia.service.IPictureService;


@Controller
@RequestMapping(value = "/picture")
public class PicturesController extends BaseController{
	@Autowired
	private IPictureService pictureService;
	
	
	/**
	 * @api {POST} /picture/vote 为图片点赞
	 * @apiGroup picture
	 * @apiVersion 1.0.0
	 * @apiDescription 为图片点赞
	 * @apiParam {String} albumId 专辑ID
	 * @apiParam {String} pictureId 图片ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "albumId":"4b8d5e5ec1da0fad27b786387030ecd4",
	 * 	  "pictureId":"51cd003dd9605f4a6756ff537b3d96a4",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"相册不存在"} 
	 */
	@RequestMapping(value = "/vote", method=RequestMethod.POST)
	@ResponseBody
	public Object vote(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		
		String token = request.getParameter("token");
		String albumId = request.getParameter("albumId");
		String pictureId = request.getParameter("pictureId");
		Vote vote = new Vote();
		vote.setId(Utility.generageId());
		vote.setAlbumId(albumId);
		vote.setPictureId(pictureId);
		pictureService.addVote(vote, token);
		
		return null;
	}
	
	/**
	 * @api {POST} /picture/unvote 为图片取消点赞
	 * @apiGroup picture
	 * @apiVersion 1.0.0
	 * @apiDescription 为图片取消点赞
	 * @apiParam {String} albumId 专辑ID
	 * @apiParam {String} pictureId 图片ID
	 * @apiParamExample {json} 请求样例：
	 * {
	 * 	  "albumId":"4b8d5e5ec1da0fad27b786387030ecd4",
	 * 	  "pictureId":"51cd003dd9605f4a6756ff537b3d96a4",
	 * }
	 *                
	 * @apiError (400) {String} message 信息
	 * @apiError (400) {String} code 数字
	 * @apiErrorExample {json} 返回样例:
	 *                {"code":"1", "message":"相册不存在"} 
	 */
	@RequestMapping(value = "/unvote", method=RequestMethod.POST)
	@ResponseBody
	public Object unvote(HttpServletRequest request, HttpServletResponse response)
			throws ServletRequestBindingException {
		String token = request.getParameter("token");
		String albumId = request.getParameter("albumId");
		String pictureId = request.getParameter("pictureId");
		Vote vote = new Vote();
		vote.setAlbumId(albumId);
		vote.setPictureId(pictureId);
		pictureService.deleteVote(vote, token);
		
		return null;
	}
}
