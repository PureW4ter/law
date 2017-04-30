package com.destinesia.dao;

import java.util.Date;
import java.util.List;

import com.destinesia.entity.Album;
import com.destinesia.entity.Counts;
import com.destinesia.entity.Region;
import com.destinesia.entity.View;

public interface IAlbumDao {
	public void addAlbum(Album album);
	public void updateAlbum(Album album);
	public List<Album> getMyAlbums(String userId, int from, int pageSize);
	public List<Album> getNewAlbums(int from, int pageSize);
	public List<Album> getAlbumsByKeys(List<String> keys, int from, int pageSize);
	public List<Album> getAlbumsByDate(Date date, int from, int pageSize);
	public List<Album> getAlbumsByPromotioned(int from, int pageSize);
	public List<Album> getNearAlbums(long xIndex, long yIndex, long level, int from, int pageSize);
	public List<Album> getPassAlbums(int pass, int from, int pageSize);
	public Album getAlbumById(String id);
	public void unpassAlbum(String id);
	public void passAlbum(String id);
	public void deleteAlbum(String id);
	public void promoteAlbum(String id);
	public long getPassedAlbumsForUser(String id);
	public void addView(View view);
	public void addRegion(Region region);
	public Region findRegion(String country, String city, String district, String road);
	public String getUserPath(String userId, String date);
	public void insertPath(String userId, String path, String date);
	public void updatePath(String userId, String path, String date);
	public Counts getCounts();
	public Counts getCountsForToday();
}
