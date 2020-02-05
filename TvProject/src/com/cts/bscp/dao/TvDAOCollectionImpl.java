package com.cts.bscp.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cts.bscp.exception.TvStoreException;
import com.cts.bscp.model.Tv;

public class TvDAOCollectionImpl implements ITvDAO {
	
	private Map<String, Tv> tvs;
	
	public TvDAOCollectionImpl() {
		tvs = new TreeMap<>();
	}
	
	@Override
	public String add(Tv tv) throws TvStoreException {
		String tvcode = null;
		if (tv != null) {
			tvcode = tv.getTvcode();
			tvs.put(tvcode, tv);	
		}
		return tvcode;
	}
	
	@Override
	public boolean delete(String tvcode) throws TvStoreException {
		boolean isDone = false;
		if (tvcode != null) {
			tvs.remove(tvcode);
			isDone = true;
		}
		return isDone;
	}
	
	@Override
	public Tv get(String tvcode) throws TvStoreException {
		return tvs.get(tvcode);
	}
	

	@Override
	public List<Tv> getAll() throws TvStoreException {
		return new ArrayList<>(tvs.values());
	}
	
	@Override
	public boolean update(Tv tv) throws TvStoreException {
		boolean isDone = false;
		if (tv != null) {
			String bcode = tv.getTvcode();
			tvs.replace(bcode, tv);
			
		}
		return isDone;
	}

	@Override
   public void persist() throws TvStoreException {
	//no implementation is required */
	}
	
	
}
