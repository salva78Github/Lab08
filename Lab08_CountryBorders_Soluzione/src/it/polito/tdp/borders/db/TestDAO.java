package it.polito.tdp.borders.db;

import java.util.List;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class TestDAO {

	public static void main(String[] args) {

		BordersDAO dao = new BordersDAO();

		System.out.println("Lista di tutte le nazioni:");
		List<Country> countries = dao.loadAllCountries();

		System.out.format("Trovate %d nazioni\n", countries.size());
		for (Country c : countries) {
			System.out.println(c);
		}

		System.out.println();

		System.out.println("Lista di tutti i confini prima del 2000:");
		List<Border> confini = dao.getCountryPairs(2000);

		System.out.format("Trovati %d confini\n", confini.size());
		for (Border cp : confini) {
			System.out.format("%s-%s\n", cp.getC1().getStateAbb(), cp.getC2().getStateAbb());
		}

	}

}
