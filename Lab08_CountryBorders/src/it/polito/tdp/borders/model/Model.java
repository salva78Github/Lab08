package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private UndirectedGraph<Country, DefaultEdge> graph;
	private static final BordersDAO dao = new BordersDAO();
	
	public Model() {
		graph = new SimpleGraph<>(DefaultEdge.class);
	}
	
	private void createGraph(int year) {
		
		List<Country> vertexList = dao.loadAllCountries(year);
		System.out.println("<creaGrafo> numero vertici/paesi: " + vertexList.size());
		// crea i vertici del grafo
		Graphs.addAllVertices(graph, vertexList );
		
		// crea gli archi del grafo --versione 3
		//faccio fare tutto il lavoro al dao
		//che mi dà la lista della coppia dei vertici
		for(CountryPair cp : dao.retrieveListaCountryPairAdiacenti(year)){
			graph.addEdge(cp.getC1(), cp.getC2());
		}
	}

	public List<Country> retrieveConfini(int year) {
		// TODO Auto-generated method stub
		createGraph(year);
		List<Country> countries = new ArrayList<>();
		
		for(Country c : graph.vertexSet()){
			c.setNumeroStatiConfinanti(graph.degreeOf(c));
			countries.add(c);
		}
		
		return countries;
		
	}

	public int getNumeroComponentiConnesse() {

		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(graph);
		System.out.println("<getNumeroComponentiConnesse> Il grafo è connesso? " + ci.isGraphConnected());
		
		return ci.connectedSets().size();
	}
	
	

}
