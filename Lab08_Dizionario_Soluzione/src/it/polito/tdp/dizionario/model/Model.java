package it.polito.tdp.dizionario.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.dizionario.db.WordDAO;

public class Model {

	private UndirectedGraph<String, DefaultEdge> graph;
	private int numeroLettere = 0;
	private WordDAO wordDAO;

	public Model() {
		wordDAO = new WordDAO();
	}

	public List<String> createGraph(int numeroLettere) {

		graph = new SimpleGraph<>(DefaultEdge.class);
		this.numeroLettere = numeroLettere;

		List<String> parole = wordDAO.getAllWordsFixedLength(numeroLettere);

		// Aggiungo tutti i vertici del grafo
		for (String parola : parole)
			graph.addVertex(parola);

		// Per ogni parola aggiungo un arco di collegamento con le parole
		// che differiscono per una sola lettera (stessa lunghezza)
		for (String parola : parole) {

			// Alternativa 1: uso il Database -- LENTO!
			// List<String> paroleSimili = wordDAO.getAllSimilarWords(parola, numeroLettere);

			// Alternativa 2: uso il mio algoritmo in Java
			List<String> paroleSimili = Utils.getAllSimilarWords(parole, parola, numeroLettere);

			// Creo l'arco
			for (String parolaSimile : paroleSimili) {
				graph.addEdge(parola, parolaSimile);
			}
		}

		// Ritorno la lista dei vertici
		return parole;
	}

	public List<String> displayNeighbours(String parolaInserita) {

		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		List<String> parole = new ArrayList<String>();

		// Ottengo la lista dei vicini di un vertice
		parole.addAll(Graphs.neighborListOf(graph, parolaInserita));

		// Ritorno la lista dei vicini
		return parole;
	}

	public String findMaxDegree() {

		int max = 0;
		String temp = null;

		for (String vertex : graph.vertexSet()) {
			if (graph.degreeOf(vertex) > max) {
				max = graph.degreeOf(vertex);
				temp = vertex;
			}
		}

		if (max != 0) {
			StringBuilder sb = new StringBuilder();
			sb.append(String.format("Max degree: %d from vertex: %s\n", max, temp));

			for (String v : Graphs.neighborListOf(graph, temp))
				sb.append(v + "\n");

			return sb.toString();
		}
		return "Non trovato.";
	}

	/*
	 * VERSIONE ITERATIVA
	 */
	public List<String> displayAllNeighboursIterative(String parolaInserita) {

		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		// Creo due liste: quella dei noti visitati ..
		List<String> visited = new LinkedList<String>();
		// .. e quella dei nodi da visitare
		List<String> toBeVisited = new LinkedList<String>();

		// Aggiungo alla lista dei vertici visitati il nodo di partenza.
		visited.add(parolaInserita);
		// Aggiungo ai vertici da visitare tutti i vertici collegati a quello inserito
		toBeVisited.addAll(Graphs.neighborListOf(graph, parolaInserita));

		while (!toBeVisited.isEmpty()) {

			// Rimuovi il vertice in testa alla coda
			String temp = toBeVisited.remove(0);

			// Aggiungi il nodo alla lista di quelli visitati
			visited.add(temp);

			// Ottieni tutti i vicini di un nodo
			List<String> listaDeiVicini = Graphs.neighborListOf(graph, temp);

			// Rimuovi da questa lista tutti quelli che hai già visitato..
			listaDeiVicini.removeAll(visited);
			// .. e quelli che sai già che devi visitare.
			listaDeiVicini.removeAll(toBeVisited);

			// Aggiungi i rimanenenti alla coda di quelli che devi visitare.
			toBeVisited.addAll(listaDeiVicini);
		}

		// Ritorna la lista di tutti i nodi raggiungibili
		return visited;
	}

	/*
	 * VERSIONE LIBRERIA JGRAPHT
	 */
	public List<String> displayAllNeighboursJGraphT(String parolaInserita) {
		
		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		List<String> visited = new LinkedList<String>();
		
		// Versione 1 : utilizzo un BreadthFirstIterator
		// GraphIterator<String, DefaultEdge> bfv = new BreadthFirstIterator<String, DefaultEdge>(graph, parolaInserita);
		// while (bfv.hasNext()) {
		// 	 visited.add(bfv.next());
		// }
		
		// Versione 2 : utilizzo un DepthFirstIterator
		GraphIterator<String, DefaultEdge> dfv = new DepthFirstIterator<String, DefaultEdge>(graph, parolaInserita);
		while (dfv.hasNext()) {
			visited.add(dfv.next());
		}

		return visited;
	}
	
	/*
	 * VERSIONE RICORSIVA
	 */
	public List<String> displayAllNeighboursRecursive(String parolaInserita) {

		if (numeroLettere != parolaInserita.length())
			throw new RuntimeException("La parola inserita ha una lunghezza differente rispetto al numero inserito.");

		List<String> visited = new LinkedList<String>();
		recursiveVisit(parolaInserita, visited);
		return visited;
	}

	protected void recursiveVisit(String n, List<String> visited) {
		
		// Do always
		visited.add(n);

		// cycle
		for (String c : Graphs.neighborListOf(graph, n)) {
			// filtro
			if (!visited.contains(c))
				recursiveVisit(c, visited);
		}
	}

}
