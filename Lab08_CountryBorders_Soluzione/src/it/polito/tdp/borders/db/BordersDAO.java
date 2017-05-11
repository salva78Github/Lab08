package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Border;

public class BordersDAO {

	public List<Country> loadAllCountries() {

		String sql = "SELECT ccode,StateAbb,StateNme " + "FROM country " + "ORDER BY StateAbb ";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();
			List<Country> list = new LinkedList<Country>();

			while (rs.next()) {
				Country c = new Country(rs.getInt("ccode"), rs.getString("StateAbb"), rs.getString("StateNme"));
				list.add(c);
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- loadAllCountries");
			throw new RuntimeException("Database Error");
		}
	}

	public List<Border> getCountryPairs(int anno) {

		String sql = "select c1.CCode as ccode1, c1.StateAbb as stateabb1, c1.StateNme as statenme1, " + "c2.CCode as ccode2, c2.StateAbb as stateabb2, c2.StateNme as statenme2 " + "from contiguity, country c1, country c2 "
				+ "where c1.CCode=contiguity.state1no " + "and c2.CCode=contiguity.state2no " + "and contiguity.conttype=1 " + "and contiguity.year <= ?";

		try {
			Connection conn = DBConnect.getInstance().getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, anno);

			ResultSet rs = st.executeQuery();
			List<Border> list = new LinkedList<Border>();

			while (rs.next()) {
				Country c1 = new Country(rs.getInt("ccode1"), rs.getString("StateAbb1"), rs.getString("StateNme1"));
				Country c2 = new Country(rs.getInt("ccode2"), rs.getString("StateAbb2"), rs.getString("StateNme2"));
				list.add(new Border(c1, c2));
			}

			conn.close();
			return list;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- getCountryPairs");
			throw new RuntimeException("Database Error");
		}
	}
}
