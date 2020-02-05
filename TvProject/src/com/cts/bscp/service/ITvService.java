package com.cts.bscp.service;

import java.util.List;

import com.cts.bscp.exception.TvStoreException;
import com.cts.bscp.model.Tv;

public interface ITvService {	
	String add(Tv tv) throws TvStoreException;
	boolean delete(String tvcode) throws TvStoreException;
	Tv get(String tvcode) throws TvStoreException;
	List<Tv> getAll() throws TvStoreException;;
	boolean update(Tv tv) throws TvStoreException;
	void persist()throws TvStoreException;
}
 