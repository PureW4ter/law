package com.destinesia.dao;

import com.destinesia.entity.Picture;
import com.destinesia.entity.Vote;

public interface IPictureDao {
	public void addPicture(Picture picture);
	public void updatePicture(Picture picture);
	public Picture getPicture(String id);
	public void deletePicture(String id);
	public void addVote(Vote vote);
	public void deleteVote(Vote vote);
}
