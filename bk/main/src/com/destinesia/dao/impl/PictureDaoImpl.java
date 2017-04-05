package com.destinesia.dao.impl;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.destinesia.dao.IPictureDao;
import com.destinesia.entity.Picture;
import com.destinesia.entity.Vote;

@Repository
public class PictureDaoImpl extends SqlSessionDaoSupport implements IPictureDao{

	@Override
	public void addPicture(Picture picture) {
		this.getSqlSession().insert("AlbumSpace.addPicture", picture);
		
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
	public void addVote(Vote vote) {
		this.getSqlSession().insert("AlbumSpace.addVote", vote);
	}
	
	@Override
	public void deleteVote(Vote vote) {
		this.getSqlSession().delete("AlbumSpace.deleteVote", vote);
	}
	
	@Resource  
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){  
        super.setSqlSessionFactory(sqlSessionFactory);  
    }


}
