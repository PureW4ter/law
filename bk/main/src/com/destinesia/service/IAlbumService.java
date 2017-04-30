package com.destinesia.service;

import java.util.List;

import com.destinesia.entity.Album;
import com.destinesia.entity.Counts;
import com.destinesia.entity.View;

public interface IAlbumService {
	public void insertAlbum(Album album, String token);
	public void updateAlbum(Album album, String token);
	public List<Album> getMyAlbums(String token, int from, int pageSize);
	public List<Album> getNewAlbums(String token, int from, int pageSize);
	public List<Album> getNearAlbums(double lat, double lon, int from, int pageSize);
	public List<Album> getAlbumsByKeys(List<String> keys, int from, int pageSize);
	public List<Album> getAlbumsByDate(String date, int from, int pageSize);
	public List<Album> getAlbumsByPromotioned(int from, int pageSize);
	public List<Album> getPassAlbums(int pass, int from, int pageSize);
	public Album getAlbumById(String id);
	public void deleteAlbum(String id, String token);
	public void unpassAlbum(String id, String token);
	public void passAlbum(String id, String token);
	public void promoteAlbum(String id, String token);
	public void addView(View view);
	public void addPath(String path, Long date, String token);
	public String getPath(Long date, String token);
	public Counts getCounts();
	public Counts getCountsForToday();
}
