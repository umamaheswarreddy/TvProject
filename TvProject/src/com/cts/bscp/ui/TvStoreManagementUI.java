package com.cts.bscp.ui;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.cts.bscp.exception.TvStoreException;
import com.cts.bscp.model.Tv;
import com.cts.bscp.model.TvStoreAppMenu;


import com.cts.bscp.service.TvServiceImpl;
import com.cts.bscp.service.ITvService;

public class TvStoreManagementUI {

	private static ITvService tvService;
	private static Scanner scan;
	private static DateTimeFormatter dtFormater;

	public static void main(String[] args) {

		try {
			tvService = new TvServiceImpl();
		} catch (TvStoreException e) {
			e.printStackTrace();
		}


		scan = new Scanner(System.in);
		dtFormater = DateTimeFormatter.ofPattern("dd-MM-yyyy");

		TvStoreAppMenu menu = null;

		while (menu != TvStoreAppMenu.QUIT) {

			System.out.println("Choice\tMenu Item");
			System.out.println("===========================");
			for (TvStoreAppMenu m : TvStoreAppMenu.values()) {
				System.out.println(m.ordinal() + "\t" + m.name());
			}
			System.out.print("Choice: ");
			int choice = -1;
			if (scan.hasNextInt())
				choice = scan.nextInt();
			else {
				scan.next();
				System.out.println("Pleae choose a choice displayed");
				continue;
			}

			if (choice < 0 || choice >= TvStoreAppMenu.values().length) {
				System.out.println("Invalid Choice");
			} else {
				menu = TvStoreAppMenu.values()[choice];
				switch (menu) {
				case ADD:
					doAdd();
					break;
				case LIST:
					doList();
					break;
				case SEARCH:
					doSearch();
					break;
				case REMOVE:
					doRemove();
					break;
				case QUIT:
					System.out.println("ThanQ Come Again!");
					break;
				}
			}

		}

		scan.close();
	try {
			tvService.persist();
		} catch (TvStoreException e) {
			e.printStackTrace();
		}

	}

	private static void doAdd() {
		try {
			Tv Tv = new Tv();
			System.out.print("tv model code: ");
			Tv.setTvcode(scan.next());
			System.out.print("company name: ");
			Tv.setCompanytitle(scan.next());
			System.out.print("Release Date(dd-MM-yyyy): ");
			String pubDtStr = scan.next();

			try {
				Tv.setReleaseDate(LocalDate.parse(pubDtStr, dtFormater));
			} catch (DateTimeException exp) {
				throw new TvStoreException(
						"Date must be in the format day(dd)-month(MM)-year(yyyy)");
			}
			System.out.print("Price: ");
			if (scan.hasNextDouble())
				Tv.setPrice(scan.nextDouble());
			else {
				scan.next();
				throw new TvStoreException("Price is a number");
			}

			String tvcode = tvService.add(Tv);
			System.out.println("Tv is Added with code: " + tvcode);
		} catch (TvStoreException exp) {
			System.out.println(exp.getMessage());
		}
	}

	private static void doList() {
		List<Tv> Tvs;
		try {
			Tvs = tvService.getAll();
			if (Tvs.size() == 0) {
				System.out.println("No Tvs To display");
			} else {
				for (Tv b : Tvs)
					System.out.println(b);
			}
		} catch (TvStoreException exp) {
			System.out.println(exp.getMessage());
		}
	}

	private static void doSearch() {
		System.out.print("TVCode: ");
		String tvcode = scan.next();

		try {
			Tv Tv = tvService.get(tvcode);
			if (Tv != null) {
				System.out.println(Tv);
			} else {
				System.out.println("No Such Tv");
			}
		} catch (TvStoreException exp) {
			System.out.println(exp.getMessage());
		}
	}

	private static void doRemove() {
		System.out.print("TvCode: ");
		String tvcode = scan.next();
		try {
			boolean isDone = tvService.delete(tvcode);
			if (isDone) {
				System.out.println("Tv is Deleted");
			} else {
				System.out.println("No Such Tv");
			}
		} catch (TvStoreException exp) {
			System.out.println(exp.getMessage());
		}
	}
}
