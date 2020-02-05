package com.cts.bscp.dao;

public interface IQueryMapper {

	public static final String ADD_Tv_QRY = 
			"INSERT INTO Tvs(tvcode,  companytitle, price, rdate) VALUES(?,?,?,?)";
	public static final String MODIFY_Tv_QRY = 
			"Urdate Tvs SET companytitle=?,price=?,rdate=? WHERE tvcode=?";
	public static final String DEL_Tv_QRY = 
			"DELETE FROM Tvs WHERE tvcode=?";
	public static final String GET_ALL_TvS_QRY = 
			"SELECT * FROM Tvs";
	public static final String GET_Tv_QRY = 
			"SELECT * FROM Tvs WHERE tvcode=?";
}
