// O. Bittel;
// 22.02.2017
package directedGraph;

import java.util.*;

/**
 * Klasse für Tiefensuche.
 *
 * @author Oliver Bittel
 * @since 22.02.2017
 * @param <V> Knotentyp.
 */
public class DepthFirstOrder<V> {

    private final List<V> preOrder = new LinkedList<>();
    private final List<V> postOrder = new LinkedList<>();
    private final DirectedGraph<V> myGraph;
    private int numberOfDFTrees = 0;
	// ...

    /**
     * Führt eine Tiefensuche für g durch.
     *
     * @param g gerichteter Graph.
     */
    public DepthFirstOrder(DirectedGraph<V> g) {
        myGraph = g;
        visitDFAllNodes(myGraph);

    }

    /* ruft visitDFRec() für jede Zusammenhangskomponente auf*/
    void visitDFAllNodes(DirectedGraph<V> g) {

        Set<V> besucht = new TreeSet<V>();
        Set<V> allVertexes = g.getVertexSet();

        for (var vertex : allVertexes) {

            if (!besucht.contains(vertex)) {
                numberOfDFTrees++;
                visitDFRec(vertex, g, besucht);
            }
        }

    }

    /* arbeitet alle Knoten einer Zusammenhangskomponenten ab*/
    void visitDFRec(V v, DirectedGraph<V> g, Set<V> besucht) {

        besucht.add(v);

        // Bearbeite v:

        /* Aus der Aufgabenstellung:
        Die PreOrder-Reihenfolge ergibt sich, indem jeder Knoten, sobald er
        besucht wird, in eine Liste angehängt wird.
        */
        preOrder.add(v);

        for (var nachbarKnoten : g.getSuccessorVertexSet(v)) {

            if ( ! besucht.contains(nachbarKnoten) )  // w noch nicht besucht
                visitDFRec(nachbarKnoten, g, besucht);
        }

        /* Aus der Aufgabenstellung:
        Bei der Post-Order-Reihenfolge wird der Knoten erst dann  in  eine  Liste  angehängt,
        sobald  die  rekursive  Besuchsmethode  für  den  Knoten  verlassen wird.
        */
        postOrder.add(v);

    }


    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Pre-Order-Reihenfolge zurück.
     *
     * @return Pre-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> preOrder() {
        return Collections.unmodifiableList(preOrder);
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Post-Order-Reihenfolge zurück.
     *
     * @return Post-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> postOrder() {
        return Collections.unmodifiableList(postOrder);
    }

    /**
     *
     * @return Anzahl der Bäume des Tiefensuchwalds.
     */
    public int numberOfDFTrees() {
        return numberOfDFTrees;
    }

    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(5, 1);
        g.addEdge(2, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
//        g.addEdge(7,3);
        g.addEdge(7, 4);

        DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(g);
        System.out.println("dfs.numberOfDFTrees():");
        System.out.println(dfs.numberOfDFTrees());	// 2
        System.out.println("dfs.preOrder()");
        System.out.println(dfs.preOrder());		// [1, 2, 5, 6, 3, 7, 4]
        System.out.println("dfs.postOrder()");
        System.out.println(dfs.postOrder());		// [5, 6, 2, 1, 4, 7, 3]

    }

}
