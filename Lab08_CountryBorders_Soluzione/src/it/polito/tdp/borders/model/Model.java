package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {

	private BordersDAO bordersDAO;
	private List<Country> countries;
	private SimpleGraph<Country, DefaultEdge> graph;

	public Model() {
		bordersDAO = new BordersDAO();
	}

	public void createGraph(int anno) {

		List<Border> confini = bordersDAO.getCountryPairs(anno);

		if (confini == null || confini.isEmpty())
			throw new RuntimeException("No country pairs for specified year");

		graph = new SimpleGraph<>(DefaultEdge.class);

		for (Border cp : confini) {
			// Aggiungo le nazioni per il solo intervallo specificato dall'anno
			graph.addVertex(cp.getC1());
			graph.addVertex(cp.getC2());
			graph.addEdge(cp.getC1(), cp.getC2());
		}

		System.out.format("Inseriti: %d vertici, %d archi\n", graph.vertexSet().size(), graph.edgeSet().size());

		countries = new ArrayList<>(graph.vertexSet());
		Collections.sort(countries);
	}
	
	public List<Country> getCountries() {
		if (graph == null)
			throw new RuntimeException("Grafo non esistente");
		
		return countries;
	}

	public Map<Country, Integer> getCountryCounts() {
		if (graph == null)
			throw new RuntimeException("Grafo non esistente");
		
		Map<Country, Integer> stats = new TreeMap<Country, Integer>();
		for (Country country : graph.vertexSet()) {
			stats.put(country, graph.degreeOf(country));
		}

		return stats;
	}

	public int getNumberOfConnectedComponents() {
		if (graph == null)
			throw new RuntimeException("Grafo non esistente");
		
		ConnectivityInspector<Country, DefaultEdge> ci = new ConnectivityInspector<Country, DefaultEdge>(graph);
		return ci.connectedSets().size();
	}

}
