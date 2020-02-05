package com.cts.bscp.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.cts.bscp.exception.TvStoreException;
import com.cts.bscp.model.Tv;

public class TvDAOIOStreamImpl implements ITvDAO {

	public static final String DATA_STORE_FILE_NAME = "Tvstore.dat";

	private Map<String, Tv> Tvs;

	public TvDAOIOStreamImpl() throws TvStoreException {
		File file = new File(DATA_STORE_FILE_NAME);

		if (!file.exists()) {
			Tvs = new TreeMap<>();
		} else {
			try (ObjectInputStream fin = new ObjectInputStream(
					new FileInputStream(DATA_STORE_FILE_NAME))) {

				Object obj = fin.readObject();

				if (obj instanceof Map) {
					Tvs = (Map<String, Tv>) obj;
				} else {
					throw new TvStoreException("Not a valid DataStore");
				}
			} catch (IOException | ClassNotFoundException exp) {
				throw new TvStoreException("Loading Data Failed");
			}
		}
	}

	@Override
	public String add(Tv Tv) throws TvStoreException {
		String bcode = null;
		if (Tv != null) {
			bcode = Tv.getTvcode();
			Tvs.put(bcode, Tv);
		}
		return bcode;
	}

	@Override
	public boolean delete(String bcode) throws TvStoreException {
		boolean isDone = false;
		if (bcode != null) {
			Tvs.remove(bcode);
			isDone = true;
		}
		return isDone;
	}

	@Override
	public Tv get(String bcode) throws TvStoreException {
		return Tvs.get(bcode);
	}

	@Override
	public List<Tv> getAll() throws TvStoreException {
		return new ArrayList<Tv>(Tvs.values());
	}

	@Override
	public boolean update(Tv Tv) throws TvStoreException {
		boolean isDone = false;
		if (Tv != null) {
			String bcode = Tv.getTvcode();
			Tvs.replace(bcode, Tv);
		}
		return isDone;
	}

	
	public void persist() throws TvStoreException {
		try (ObjectOutputStream fout = new ObjectOutputStream(
				new FileOutputStream(DATA_STORE_FILE_NAME))) {
			fout.writeObject(Tvs);
		} catch (IOException exp) {
			throw new TvStoreException("Saving Data Failed");
		}
	}
}