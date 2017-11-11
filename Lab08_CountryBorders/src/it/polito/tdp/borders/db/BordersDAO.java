package it.polito.tdp.borders.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.borders.model.Border;
import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.CountryPair;

public class BordersDAO {

	public List<Country> loadAllCountries(int year) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Country> countries = new ArrayList<>();
		
		String sql = "SELECT DISTINCT countries.abb, countries.code, countries.name " +
					 "FROM ( " +
						"SELECT c1.StateAbb as abb, c1.CCode as code, c1.StateNme as name  " +
						"FROM contiguity c, country c1 " +
						"WHERE c.state1no = c1.CCode " +
						"AND c.conttype = 1 " +
						"AND c.year <= ? " +
						"UNION " +
						"SELECT c2.StateAbb as abb, c2.CCode as code, c2.StateNme as name  " +
						"FROM contiguity c, country c2 " +
						"WHERE c.state2no = c2.CCode " +
						"AND c.conttype = 1 " +
						"AND c.year <= ? " +
					") countries " +
					" ORDER BY countries.abb";

		try {
			conn = DBConnect.getInstance().getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, year);
			st.setInt(2, year);
			rs = st.executeQuery();

			while (rs.next()) {
				Country c = new Country(rs.getString(1), rs.getInt(2), rs.getString(3));
				countries.add(c);
			}

			return countries;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- loadAllCountries");
			throw new RuntimeException("Database Error");
		} finally{
			DBConnect.getInstance().closeResources(conn, st, rs);
		}
	}

	public List<Border> getCountryPairs(int anno) {

		System.out.println("TODO -- BordersDAO -- getCountryPairs(int anno)");
		return new ArrayList<Border>();
	}

	public List<CountryPair> retrieveListaCountryPairAdiacenti(int year) {
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		List<CountryPair> cpList = new ArrayList<>();
		
		String sql = "SELECT c1.StateAbb, c1.CCode, c1.StateNme, c2.StateAbb, c2.CCode, c2.StateNme " +
					 "FROM contiguity c, country c1, country c2 " +
					 "WHERE c.state1no = c1.CCode " +
					 "AND c.state2no = c2.CCode " +
					 "AND c.conttype = 1 " +
					 "AND c.year <= ? ";
	
		try {
			conn = DBConnect.getInstance().getConnection();
			st = conn.prepareStatement(sql);
			st.setInt(1, year);
			rs = st.executeQuery();

			while (rs.next()) {
				Country c1 = new Country(rs.getString(1), rs.getInt(2), rs.getString(3));
				Country c2 = new Country(rs.getString(4), rs.getInt(5), rs.getString(6));
				CountryPair cp = new CountryPair(c1, c2);
				cpList.add(cp);
			}

			return cpList;

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Database Error -- loadAllCountries");
			throw new RuntimeException("Database Error");
		} finally{
			DBConnect.getInstance().closeResources(conn, st, rs);
		}

	}
}
