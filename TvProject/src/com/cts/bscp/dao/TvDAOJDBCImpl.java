package com.cts.bscp.dao;

import com.cts.bscp.model.Tv;
import com.cts.bscp.util.ConnectionProvider;
import com.cts.bscp.exception.TvStoreException;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class TvDAOJDBCImpl implements ITvDAO {

	ConnectionProvider conProvider;
	

	public TvDAOJDBCImpl() throws TvStoreException {
		

		try {
			conProvider = ConnectionProvider.getInstance();
		} catch (ClassNotFoundException | IOException exp) {
			
			throw new TvStoreException("Database is not reachable");
		}
	}

	@Override
	public String add(Tv Tv) throws TvStoreException {
		String tvcode = null;
		if (Tv != null) {
			try (Connection con = conProvider.getConnection();
					PreparedStatement pInsert = con
							.prepareStatement(IQueryMapper.ADD_Tv_QRY)) {

				pInsert.setString(1, Tv.getTvcode());
				pInsert.setString(2, Tv.getCompanytitle());
				pInsert.setDouble(3, Tv.getPrice());
				pInsert.setDate(4, Date.valueOf(Tv.getReleaseDate()));

				int rowCount = pInsert.executeUpdate();

				if (rowCount == 1) {
					tvcode = Tv.getTvcode();
				}
			} catch (SQLException exp) {
				
				throw new TvStoreException("Tv is not inserted");
			}
		}
		return tvcode;
	}

	@Override
	public boolean delete(String tvcode) throws TvStoreException {
		boolean isDone = false;

		try (Connection con = conProvider.getConnection();
				PreparedStatement pDelete = con
						.prepareStatement(IQueryMapper.DEL_Tv_QRY)) {

			pDelete.setString(1, tvcode);

			int rowCount = pDelete.executeUpdate();

			if (rowCount == 1) {
				isDone = true;
			}
		} catch (SQLException exp) {
			
			throw new TvStoreException("Tv is not inserted");
		}

		return isDone;
	}

	@Override
	public Tv get(String tvcode) throws TvStoreException {
		Tv Tv=null;
		try (Connection con = conProvider.getConnection();
				PreparedStatement pSelect = con
						.prepareStatement(IQueryMapper.GET_Tv_QRY)) {

			pSelect.setString(1, tvcode);

			ResultSet rs = pSelect.executeQuery();
			
			if(rs.next()){
				Tv = new Tv();
				Tv.setTvcode(rs.getString("tvcode"));
				Tv.setCompanytitle(rs.getString("companytitle"));
				Tv.setPrice(rs.getDouble("price"));
				Tv.setReleaseDate(rs.getDate("rdate").toLocalDate());
			}
			
		} catch (SQLException exp) {
			
			throw new TvStoreException("Tv is not available");
		}		
		return Tv;
	}

	@Override
	public List<Tv> getAll() throws TvStoreException {
		List<Tv> Tvs=null;
		try (Connection con = conProvider.getConnection();
				PreparedStatement pSelect = con
						.prepareStatement(IQueryMapper.GET_ALL_TvS_QRY)) {

			ResultSet rs = pSelect.executeQuery();
			
			Tvs = new ArrayList<Tv>();
			
			while(rs.next()){
				Tv Tv = new Tv();
				Tv.setTvcode(rs.getString("tvcode"));
				Tv.setCompanytitle(rs.getString("companytitle"));
				Tv.setPrice(rs.getDouble("price"));
				Tv.setReleaseDate(rs.getDate("rdate").toLocalDate());
				Tvs.add(Tv);
			}
			
		} catch (SQLException exp) {
			
			throw new TvStoreException("No Tvs are available.");
		}		
		return Tvs;	
	}

	@Override
	public boolean update(Tv Tv) throws TvStoreException {
		boolean isDone = false;
		if (Tv != null) {
			try (Connection con = conProvider.getConnection();
					PreparedStatement pUpdate = con
							.prepareStatement(IQueryMapper.MODIFY_Tv_QRY)) {

				
				pUpdate.setString(1, Tv.getCompanytitle());
				pUpdate.setDouble(2, Tv.getPrice());
				pUpdate.setDate(3, Date.valueOf(Tv.getReleaseDate()));
				pUpdate.setString(4, Tv.getTvcode());
				

				int rowCount = pUpdate.executeUpdate();

				if (rowCount == 1) {
					isDone = true;
				}
			} catch (SQLException exp) {
				
				throw new TvStoreException("Tv is not updated.");
			}
		}
		return isDone;
	}

	@Override
	public void persist() throws TvStoreException {
		
		
	}
}
