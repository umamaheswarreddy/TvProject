package com.cts.bscp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cts.bscp.dao.ITvDAO;
import com.cts.bscp.dao.TvDAOCollectionImpl;
import com.cts.bscp.dao.TvDAOIOStreamImpl;
import com.cts.bscp.dao.TvDAOJDBCImpl;
import com.cts.bscp.exception.TvStoreException;
import com.cts.bscp.model.Tv;

public class TvServiceImpl implements ITvService {
	
	private ITvDAO tvDao;

	public ITvDAO getDAO(){
		return tvDao;
	}
	
	public TvServiceImpl() throws TvStoreException {
		tvDao = new TvDAOJDBCImpl();
		// tvDao = new tvDaoStreamImpl();
		
	}
	
	public boolean isValidtvcode(String tvcode){
		
		/*
		 * First letter must be capital
		 * Followed by three digits
		 */
		Pattern tvcodePattern = Pattern.compile("[A-Z]\\d{3}");
		Matcher tvcodeMatcher = tvcodePattern.matcher(tvcode);
		
		return tvcodeMatcher.matches();
	}
	
	public boolean isValidcompanytitle(String companytitle){
		/*
		 * First Letter should be capital
		 * Minimum length is 4 chars
		 * Maximum length is 20 chars.		
		 */
		Pattern companytitlePattern = Pattern.compile("[A-Z]\\w{3,19}");
		Matcher companytitleMatcher = companytitlePattern.matcher(companytitle);
		
		return companytitleMatcher.matches();
	}
	
	public boolean isValidPrice(double price){
		/*
		 * Price is between 5 and 5000
		 */
		return price>=10000 && price<=500000;
	}
	
	public boolean isValidPublishDate(LocalDate publishDate){
		/*
		 * publish date should not be greater than the current day.
		 */
		LocalDate today = LocalDate.now();
		//return publishDate.isBefore(today) || publishDate.equals(today);
		return today.isAfter(publishDate) || publishDate.equals(today);
	}
	
	public boolean isValidtv(Tv tv) throws TvStoreException{
		boolean isValid=false;
		
		List<String> errMsgs = new ArrayList<>();
		
		if(!isValidtvcode(tv.getTvcode()))
			errMsgs.add("tvcode should start with a capital alphabet followed by 3 digits");
		
		if(!isValidcompanytitle(tv.getCompanytitle()))
			errMsgs.add("companytitle must start with capital and must be in between 4 to 20 chars in length");
		
		if(!isValidPrice(tv.getPrice()))
			errMsgs.add("Price must be between INR.5 and INR.5000");
		
		if(!isValidPublishDate(tv.getReleaseDate()))
			errMsgs.add("Publish Date should not be a future date");
		
		if(errMsgs.size()==0)
			isValid=true;
		else
			throw new TvStoreException(errMsgs.toString());
		
		return isValid;
	}


	@Override
	public String add(Tv tv) throws TvStoreException {
		String tvcode=null;
		if(tv!=null && isValidtv(tv)){
			tvcode=tvDao.add(tv);
		}
		return tvcode;
	}

	@Override
	public boolean delete(String tvcode) throws TvStoreException {
		boolean isDone=false;
		if(tvcode!=null && isValidtvcode(tvcode)){
			tvDao.delete(tvcode);
			isDone = true;
		}else{
			throw new TvStoreException("tvcode should be a capital letter followed by 3 digits");
		}
		return isDone;
	}

	@Override
	public Tv get(String tvcode) throws TvStoreException {
		return tvDao.get(tvcode);
	}

	@Override
	public List<Tv> getAll() throws TvStoreException {
		return tvDao.getAll();
	}

	@Override
	public boolean update(Tv tv) throws TvStoreException {
		boolean isDone = false;
		
		if(tv!=null && isValidtv(tv)){
			isDone = tvDao.update(tv);
		}
		
		return isDone;
	}

	


	@Override
	public void persist() throws TvStoreException {
		tvDao.persist();
   }
}
