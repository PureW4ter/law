package com.destinesia.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.destinesia.base.StatusMsg;
import com.destinesia.base.Utility;
import com.destinesia.dao.IAlbumDao;
import com.destinesia.dao.IPictureDao;
import com.destinesia.dao.IUserDao;
import com.destinesia.entity.Album;
import com.destinesia.entity.Counts;
import com.destinesia.entity.Picture;
import com.destinesia.entity.Region;
import com.destinesia.entity.TokenInfo;
import com.destinesia.entity.User;
import com.destinesia.entity.View;
import com.destinesia.service.IAlbumService;

@Service
public class AlbumServiceImpl implements IAlbumService{

	@Autowired
	private IAlbumDao albumDao;
	@Autowired
	private IUserDao userDao;
	@Autowired
	private IPictureDao pictureDao;
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void insertAlbum(Album album, String token) {
		TokenInfo info = userDao.getTokenByToken(token);
		try{
			if(info !=null){
				album.setUser(new User());
				album.getUser().setId(info.getUserId());
				//转换经纬度到地址
				//Region region = Utility.getAddressBAIDU(album.getLatitude(), album.getLongitude());
				//TODO，两次请求，可能会影响性能
				Region regionCN = Utility.getAddressGOOGLECN(album.getLatitude(), album.getLongitude());
				Region region = Utility.getAddressGOOGLEEN(album.getLatitude(), album.getLongitude());
				
				//如果地址信息不存在，保存
				Region oldRegion = albumDao.findRegion(region.getCountry(), region.getCity(), region.getDistrict(), region.getRoad());
				if(oldRegion == null){
					region.setId(Utility.generageId());
					region.setCityCN(regionCN.getCityCN());
					region.setCountryCN(regionCN.getCountryCN());
					region.setDistrictCN(regionCN.getDistrictCN());
					region.setProvinceCN(regionCN.getProvinceCN());
					region.setRoadCN(regionCN.getRoadCN());
					albumDao.addRegion(region);
					album.setRegion(region);
				}else{
					album.setRegion(oldRegion);
				}
				album.setKeywords(region.getCountry()+","+region.getProvince()+","+region.getCity()+","
						+region.getDistrict()+","+region.getRoad()+","+regionCN.getCountryCN()+","+regionCN.getRoadCN()
						+","+regionCN.getProvinceCN()+","+regionCN.getCityCN()+","+regionCN.getDistrictCN());
				//保存专辑所在位置，方便查询
				Map<String, Long> ps = Utility.getPosFromLatLon(album.getLatitude(), album.getLongitude());
				album.setLevel(ps.get("level"));
				album.setX(ps.get("xIndex"));
				album.setY(ps.get("yIndex"));
				//生成专辑信息
				if(album.getId()==null){
					album.setId(Utility.generageId());
				}
				//计算专辑路径
				Collections.sort(album.getPicList(), new SortByDate());
				long timeSub = album.getPicList().get(album.getPicList().size()-1).getCreateDate().getDate()
							- album.getPicList().get(0).getCreateDate().getDate();
				if(timeSub == 0){
					SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
					String a1=dateformat1.format(album.getPicList().get(0).getCreateDate());
					String path = albumDao.getUserPath(info.getUserId(), a1);
					album.setRoad(path);
				}
				
				albumDao.addAlbum(album);
				//添加相册
				for(Picture pic : album.getPicList()){
					pic.setId(Utility.generageId());
					pic.setAlbumId(album.getId());
					Region regionP = Utility.getAddressGOOGLEEN(pic.getLatitude(), pic.getLongitude());
					Region regionPCN = Utility.getAddressGOOGLECN(album.getLatitude(), album.getLongitude());
					Region oldRegionP = albumDao.findRegion(regionP.getCountry(), regionP.getCity(), regionP.getDistrict(), regionP.getRoad());
					if(oldRegionP == null){
						regionP.setId(Utility.generageId());
						regionP.setCityCN(regionPCN.getCityCN());
						regionP.setCountryCN(regionPCN.getCountryCN());
						regionP.setDistrictCN(regionPCN.getDistrictCN());
						regionP.setProvinceCN(regionPCN.getProvinceCN());
						regionP.setRoadCN(regionPCN.getRoadCN());
						albumDao.addRegion(regionP);
						pic.setRegion(regionP);
					}else{
						pic.setRegion(oldRegionP);
					}
					pictureDao.addPicture(pic);
				}
			}
		}catch(Exception e){
			//可以通过捕获异常，发送自己的异常
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			throw new StatusMsg(StatusMsg.CODE_DATABASE_ROLLBACK, "数据库操作出错，并且回滚");
		}
	}

	@Override
	public void updateAlbum(Album album, String token) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteAlbum(String id, String token) {
		albumDao.deleteAlbum(id);
	}

	@Override
	public void unpassAlbum(String id, String token) {
		albumDao.unpassAlbum(id);
	}

	@Override
	public void promoteAlbum(String id, String token) {
		albumDao.promoteAlbum(id);
	}
	
	@Override
	public void passAlbum(String id, String token) {
		albumDao.passAlbum(id);
		Album album =  albumDao.getAlbumById(id);
		long count = albumDao.getPassedAlbumsForUser(album.getUser().getId());
		switch(album.getUser().getGrade()){
			case 0:
				if(count>=3){
					userDao.increaseGrade(album.getUser().getId());
				}
				break;
			case 1:
				if(count>20){
					userDao.increaseGrade(album.getUser().getId());
				}
				break;
			case 2:
				if(count>50){
					userDao.increaseGrade(album.getUser().getId());
				}
				break;
			default:
		}
	}
	
	@Override
	public void addView(View view) {
		albumDao.addView(view);
	}

	@Override 
	public List<Album> getMyAlbums(String token, int from, int pageSize) {
		TokenInfo info = userDao.getTokenByToken(token);
		if(info !=null){
			return albumDao.getMyAlbums(info.getUserId(), from, pageSize);
		}
		return null;
	}

	@Override
	public List<Album> getNearAlbums(double lat, double lon, int from, int pageSize) {
		Map<String, Long> ps = Utility.getPosFromLatLon(lat, lon);
		return albumDao.getNearAlbums(ps.get("xIndex"), ps.get("yIndex"), ps.get("level"), from, pageSize);
	}

	@Override
	public Album getAlbumById(String id) {
		Album album =  albumDao.getAlbumById(id);
		return album;
	}

	@Override
	public List<Album> getNewAlbums(String token, int from, int pageSize) {
		return albumDao.getNewAlbums(from, pageSize);
	}

	@Override
	public List<Album> getAlbumsByKeys(List<String> keys, int from, int pageSize) {
		return albumDao.getAlbumsByKeys(keys, from, pageSize);
	}

	@Override
	public List<Album> getAlbumsByDate(String date, int from, int pageSize) {
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		Date d = new Date();
		try {
			d = dateformat1.parse(date);
		} catch (ParseException e) {
			
		}
		return albumDao.getAlbumsByDate(d, from,  pageSize);
	}

	@Override
	public List<Album> getAlbumsByPromotioned(int from, int pageSize) {
		return albumDao.getAlbumsByPromotioned(from, pageSize);
	}
	
	@Override
	public void addPath(String path, Long date, String token) {
		TokenInfo info = userDao.getTokenByToken(token);
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateformat1.format(new Date(date));
		String oldPath = albumDao.getUserPath(info.getUserId(), dateStr);
		if(oldPath!=null){
			path = oldPath+","+path;
		}
		albumDao.updatePath(path, info.getUserId(), dateStr);
	}

	@Override
	public String getPath(Long date, String token) {
		TokenInfo info = userDao.getTokenByToken(token);
		SimpleDateFormat dateformat1=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = dateformat1.format(new Date(date));
		return albumDao.getUserPath(info.getUserId(), dateStr);
	}

	@Override
	public Counts getCounts() {
		return albumDao.getCounts();
	}

	@Override
	public Counts getCountsForToday() {
		return albumDao.getCountsForToday();
	}

	@Override
	public List<Album> getPassAlbums(int pass, int from, int pageSize) {
		return albumDao.getPassAlbums(pass, from, pageSize);
	}
}

class SortByDate implements Comparator {
	public int compare(Object o1, Object o2) {
		Picture p1 = (Picture) o1;
		Picture p2 = (Picture) o2;
		if (p1.getCreateDate().getTime() > p2.getCreateDate().getTime())
			return 1;
		return 0;
	}
}