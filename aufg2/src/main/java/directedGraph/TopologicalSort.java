// O. Bittel;
// 22.02.2017

package directedGraph;

import java.util.*;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class 	TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
	Map<V, Integer> inDegree = new TreeMap<>();
	Queue<V> q = new LinkedList<>();


	/**
	 * Führt eine topologische Sortierung für g durch.
	 * @param g gerichteter Graph.
	 */
	public TopologicalSort(DirectedGraph<V> g) {

		/* finde die Startknoten (also den/die ersten Knoten der topologisch sortierten Liste) */

		for (var v : g.getVertexSet()) {

			inDegree.put(v, g.getInDegree(v));
			if (inDegree.get(v) == 0) {
				q.add(v);
			}
		}

		/* fülle topologisch sortierte Liste ts */

		while (q.size() != 0) {

			V v = q.remove();
			ts.add(v);

			for (var suc : g.getSuccessorVertexSet(v)) {

				inDegree.put(suc, inDegree.get(suc) - 1);

				/* nicht zur Liste hinzufügen, wenn mehr als ein Vorgänger-Knoten besteht,
				sonst könnte ein Zyklus vorliegen */
				if ( (inDegree.get(suc)) == 0 ) {
					q.add(suc);
				}

			}
		}


		if (ts.size() != g.getNumberOfVertexes()) {
			System.out.println("Graph ist zyklisch");
			ts.clear();
		}
		else {
			return;
		}

    }
    
	/**
	 * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
	 * die topologisch sortiert ist.
	 * @return topologisch sortierte Liste
	 */
	public List<V> topologicalSortedList() {
        return Collections.unmodifiableList(ts);
    }
    

	public static void main(String[] args) {
		DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
		g.addEdge(1, 2);
		g.addEdge(2, 3);
		g.addEdge(3, 4);
		g.addEdge(3, 5);
		g.addEdge(4, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		System.out.println(g);

		TopologicalSort<Integer> ts = new TopologicalSort<>(g);
		
		if (ts.topologicalSortedList() != null) {
			System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}


		/* Ab hier die Kleider-Anzieh-Anleitung topoligisch sortiert*/

		DirectedGraph<String> anziehAnleitung = new AdjacencyListDirectedGraph<>();
		anziehAnleitung.addEdge("Socken", "Schuhe");
		anziehAnleitung.addEdge("Schuhe", "Handschuhe");

		anziehAnleitung.addEdge("Unterhose", "Hose");
		anziehAnleitung.addEdge("Hose", "Schuhe");
		anziehAnleitung.addEdge("Hose", "Gürtel");
		anziehAnleitung.addEdge("Gürtel", "Mantel");

		anziehAnleitung.addEdge("Unterhemd", "Hemd");
		anziehAnleitung.addEdge("Hemd", "Pulli");
		anziehAnleitung.addEdge("Pulli", "Mantel");
		anziehAnleitung.addEdge("Mantel", "Schal");
		anziehAnleitung.addEdge("Schal", "Handschuhe");

		anziehAnleitung.addEdge("Mütze", "Handschuhe");

//		System.out.println(anziehAnleitung);

		TopologicalSort<String> topoligischesAnziehen = new TopologicalSort<>(anziehAnleitung);

		if (topoligischesAnziehen.topologicalSortedList() != null) {
			System.out.println(topoligischesAnziehen.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
		}

	}
}
