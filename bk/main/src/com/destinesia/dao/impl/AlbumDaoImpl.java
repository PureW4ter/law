package com.destinesia.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.destinesia.dao.IAlbumDao;
import com.destinesia.entity.Album;
import com.destinesia.entity.Counts;
import com.destinesia.entity.Region;
import com.destinesia.entity.View;

@Repository
public class AlbumDaoImpl extends SqlSessionDaoSupport implements IAlbumDao{

	@Override
	public void addAlbum(Album album) {
		this.getSqlSession().insert("AlbumSpace.addAlbum", album);
	}

	@Override
	public void updateAlbum(Album album) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteAlbum(String id) {
		this.getSqlSession().update("AlbumSpace.deleteAlbum", id);
	}

	@Override
	public void unpassAlbum(String id) {
		this.getSqlSession().update("AlbumSpace.unpassAlbum", id);
	}
	
	@Override
	public void passAlbum(String id) {
		this.getSqlSession().update("AlbumSpace.passAlbum", id);
	}

	@Override
	public void promoteAlbum(String id) {
		this.getSqlSession().update("AlbumSpace.promoteAlbum", id);
		
	}
	@Override
	public void addView(View view) {
		this.getSqlSession().insert("AlbumSpace.addView", view);
	}

	@Override
	public List<Album> getMyAlbums(String userId, int from, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("AlbumSpace.getMyAlbums", param);
	}
	
	@Override
	public Album getAlbumById(String id) {
		return this.getSqlSession().selectOne("AlbumSpace.getAlbumById", id);
	}
	
	@Override
	public void addRegion(Region region) {
		this.getSqlSession().selectOne("AlbumSpace.addRegion", region);
	}
	
	@Override
	public Region findRegion(String country, String city, String district, String road) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("country", country);
		param.put("city", city);
		param.put("district", district);
		param.put("road", road);
		return this.getSqlSession().selectOne("AlbumSpace.findRegion", param);
	}
	
	@Override
	public List<Album> getNewAlbums(int from, int pageSize) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("AlbumSpace.getNewAlbums", param);
	}

	@Override
	public List<Album> getAlbumsByKeys(List<String> keys, int from, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("keys", keys);
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("AlbumSpace.getAlbumsByKeys", param);
	}

	@Override
	public List<Album> getAlbumsByDate(Date date, int from, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("date", date);
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("AlbumSpace.getAlbumsByDate", param);
	}

	@Override
	public List<Album> getAlbumsByPromotioned(int from, int pageSize) {
		Map<String, Integer> param = new HashMap<String, Integer>();
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("AlbumSpace.getAlbumsByPromotioned", param);
	}
	
	@Override
	public List<Album> getNearAlbums(long xIndex, long yIndex, long level, int from, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("xIndex", xIndex);
		param.put("yIndex", yIndex);
		param.put("level", level);
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("AlbumSpace.getNearAlbums", param);
	}
	@Override
	public long getPassedAlbumsForUser(String id) {
		return this.getSqlSession().selectOne("AlbumSpace.getPassedAlbumsForUser", id);
	}
	
	@Override
	public String getUserPath(String userId, String date) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("date", date);
		return this.getSqlSession().selectOne("AlbumSpace.getUserPath", param);
	}

	@Override
	public void insertPath(String userId, String path, String date) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("date", date);
		param.put("path", path);
		this.getSqlSession().insert("AlbumSpace.addPath", param);
	}

	@Override
	public void updatePath(String userId, String path, String date) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("userId", userId);
		param.put("date", date);
		param.put("path", path);
		this.getSqlSession().update("AlbumSpace.updatePath", param);
	}
	
	@Override
	public Counts getCounts() {
		return this.getSqlSession().selectOne("AlbumSpace.getCounts");
	}

	@Override
	public Counts getCountsForToday() {
		return this.getSqlSession().selectOne("AlbumSpace.getCountsForToday");
	}
	
	@Override
	public List<Album> getPassAlbums(int pass, int from, int pageSize) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pass", pass);
		param.put("from", from*pageSize);
		param.put("size", pageSize);
		return this.getSqlSession().selectList("AlbumSpace.getPassAlbums", param);
	}
	
	@Resource  
    public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory){  
        super.setSqlSessionFactory(sqlSessionFactory);  
    }
}
