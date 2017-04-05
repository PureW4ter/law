package com.destinesia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.destinesia.dao.IPictureDao;
import com.destinesia.dao.IUserDao;
import com.destinesia.entity.Picture;
import com.destinesia.entity.TokenInfo;
import com.destinesia.entity.Vote;
import com.destinesia.service.IPictureService;

@Service
public class PictureServiceImpl implements IPictureService{
	
	@Autowired
	private IPictureDao pictureDao;
	@Autowired
	private IUserDao userDao;
	
	@Override
	public void addPicture(Picture picture) {
		pictureDao.addPicture(picture);
	}

	@Override
	public void updatePicture(Picture picture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Picture getPicture(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePicture(String id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addVote(Vote vote, String token) {
		TokenInfo info = userDao.getTokenByToken(token);
		vote.setUserId(info.getUserId());
		pictureDao.addVote(vote);
	}

	@Override
	public void deleteVote(Vote vote, String token) {
		TokenInfo info = userDao.getTokenByToken(token);
		vote.setUserId(info.getUserId());
		pictureDao.deleteVote(vote);
	}
}
