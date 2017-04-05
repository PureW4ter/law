package com.destinesia.service;

import com.destinesia.entity.Picture;
import com.destinesia.entity.Vote;

public interface IPictureService {
	public void addPicture(Picture picture);
	public void updatePicture(Picture picture);
	public Picture getPicture(String id);
	public void deletePicture(String id);
	public void addVote(Vote vote, String token);
	public void deleteVote(Vote vote, String token);
}
