package com.cts.bscp.model;

import java.io.Serializable;
import java.time.LocalDate;

/*
 * Represents a book in a library or book store.
 */
@SuppressWarnings("serial")
public class Tv implements Serializable /* implements Comparable<Book> */ {

	private String tvcode;
	private String companytitle;
	private LocalDate releaseDate;
	private double price;

	public Tv() {
		/* default constructor */
	}

	public Tv(String tvcode, String companytitle, LocalDate releaseDate, double price) {
		super();
		this.tvcode = tvcode;
		this.companytitle = companytitle;
		this.releaseDate = releaseDate;
		this.price = price;
	}

	public String getTvcode() {
		return tvcode;
	}

	public void setTvcode(String tvcode) {
		this.tvcode = tvcode;
	}

	public String getCompanytitle() {
		return companytitle;
	}

	public void setCompanytitle(String companytitle) {
		this.companytitle = companytitle;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Tv [tvcode=" + tvcode + ", companytitle=" + companytitle + ", releaseDate=" + releaseDate + ", price="
				+ price + "]";
	}

		
	

}
