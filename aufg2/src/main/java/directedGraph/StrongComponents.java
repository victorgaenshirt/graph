// O. Bittel;
// 05-09-2018

package directedGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 * @author Oliver Bittel
 * @since 02.03.2020
 * @param <V> Knotentyp.
 */
public class StrongComponents<V> {
	// comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

	private final Map<Integer,Set<V>> comp = new TreeMap<>();
	
	/**
	 * Ermittelt alle strengen Komponenten mit
	 * dem Kosaraju-Sharir Algorithmus.
	 * @param g gerichteter Graph.
	 */
	public StrongComponents(DirectedGraph<V> g) {

		/* Teilaufgabe a) des Kosaraju-Sharir-Algorithmus */

		DepthFirstOrder<V> gAsDepthTree = new DepthFirstOrder<>(g);
		List<V> p = gAsDepthTree.postOrder();
		List<V> pInverted = new ArrayList<V>(p.size());

		System.out.println("postOrder p: " + p);

		for (int i = p.size(); i > 0; i--) {
			pInverted.add(p.get(i-1));
		}

		System.out.println("postOrder Inverted (pInverted): " + pInverted);

		/* Teilaufgabe b) des Kosaraju-Sharir-Algorithmus */

		DirectedGraph<V> invertedGraph = g.invert();

		/* Teilaufgabe c) des Kosaraju-Sharir-Algorithmus */

//		DepthFirstOrder<V> tiefenSuchBaumInverted = new DepthFirstOrder<>(invertedGraph);

		tiefenSucheInInvertedPostOrder(invertedGraph, pInverted);


		/* TODO
		 *   Tiefensuche durchführen und einzelne Zusammenhangs-Komponenten jeweils in comp abspeichern
		 */

		// Hier müssten die einträge zur map "comp" hinzugefügt werden (siehe test)

	}

	private void tiefenSucheInInvertedPostOrder(DirectedGraph<V> g, List<V> invertedList) {

		Set<V> besucht = new TreeSet<V>();
		int nrComponents = 0;

		for (var vertex : invertedList) {

			if (!besucht.contains(vertex)) {
				Set<V> setForThisComponent = new TreeSet<>();
				visitDFRec(vertex, g, besucht, setForThisComponent);
				comp.put(nrComponents, setForThisComponent);
				nrComponents++;
			}

		}
	}

		/* arbeitet alle Knoten einer Zusammenhangskomponenten ab*/
	void visitDFRec(V v, DirectedGraph<V> g, Set<V> besucht, Set<V> setForThisComponent) {

		besucht.add(v);

		setForThisComponent.add(v);

		for (var nachbarKnoten : g.getSuccessorVertexSet(v)) {

			if ( ! besucht.contains(nachbarKnoten) )  // w noch nicht besucht
				visitDFRec(nachbarKnoten, g, besucht, setForThisComponent);
		}

	}


	/**
	 * 
	 * @return Anzahl der strengen Komponeneten.
	 */
	public int numberOfComp() {
		return comp.size();
	}

	@Override
	// output soll folgendermaßen aussehen:
	//
	// Component 0: 5, 6, 7,
	// Component 1: 8,
	// Component 2: 1, 2, 3,
	// Component 3: 4,
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (var singleComponent : comp.entrySet()) {

			sb.append("Component " + singleComponent.getKey() + ": ");

			for (var singleEntry : singleComponent.getValue()) {
				sb.append(singleEntry + ", ");
			}
			sb.append("\n");

		}

		return sb.toString();
	}

	/**
	 * Liest einen gerichteten Graphen von einer Datei ein.
	 * @param fn Dateiname.
	 * @return gerichteter Graph.
	 * @throws FileNotFoundException
	 */
	public static DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		Scanner sc = new Scanner(fn);
		sc.nextLine();
        sc.nextLine();
		while (sc.hasNextInt()) {
			int v = sc.nextInt();
			int w = sc.nextInt();
			g.addEdge(v, w);
		}
		return g;
	}

	private static void test1() {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1,2);
		g.addEdge(1,3);
		g.addEdge(2,1);
		g.addEdge(2,3);
		g.addEdge(3,1);

		g.addEdge(1,4);
		g.addEdge(5,4);

		g.addEdge(5,7);
		g.addEdge(6,5);
		g.addEdge(7,6);

		g.addEdge(7,8);
		g.addEdge(8,2);

		StrongComponents<Integer> sc = new StrongComponents<>(g);

		System.out.println("sc.numberOfComp(): " + sc.numberOfComp());  // 4

		System.out.println(sc);
			// Component 0: 5, 6, 7,
        	// Component 1: 8,
            // Component 2: 1, 2, 3,
            // Component 3: 4,
	}

	private static void test2() throws FileNotFoundException {

		File basePath = new File(System.getProperty("user.dir"));
		String fileName = "aufg2/src/main/java/directedGraph/mediumDG.txt";
		File file = new File(basePath, fileName);

		DirectedGraph<Integer> g = readDirectedGraph(file);

		System.out.println(g.getNumberOfVertexes());
		System.out.println(g.getNumberOfEdges());
		System.out.println(g);

		System.out.println("");

		StrongComponents<Integer> sc = new StrongComponents<>(g);
		System.out.println("sc.numberOfComp(): " + sc.numberOfComp());  // 10
		System.out.println(sc);

	}

	public static void main(String[] args) throws FileNotFoundException {
		test1();
		test2();
	}

}
