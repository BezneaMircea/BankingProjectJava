package org.poo.utils;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Class used for applying FloydWarshall algorithm on a graph
 * and storing the computed data. The shortest path will not be a sum
 * of the edges cost but a product in this case.
 * @param <V> The Vertex of graph
 * @param <E>
 * @see org.jgrapht.alg.shortestpath.FloydWarshallShortestPaths
 */
public final class CustomFloydWarshallPaths <V, E> {
    private final Graph<V, E> graph;
    private final double[][] distanceMatrix;
    private final Map<V, Integer> vertexMap;


    /**
     * Constructor used to run initialize the data needed for applying the algorithm
     * @param graph the graph on which we want to compute FloydWarshall
     */
    public CustomFloydWarshallPaths(Graph<V, E> graph) {
        this.graph = graph;
        distanceMatrix = new double[graph.vertexSet().size()][graph.vertexSet().size()];
        vertexMap = new HashMap<>();

        initializeDistanceMatrix();
        compute();
    }

    private void initializeDistanceMatrix() {
        List<V> vertexList = new ArrayList<>(graph.vertexSet());

        for (int i = 0; i < vertexList.size(); i++) {
            vertexMap.put(vertexList.get(i), i);
            for (int j = 0; j < graph.vertexSet().size(); j++) {
                if (i == j)
                    distanceMatrix[j][i] = 1;
                else
                    distanceMatrix[j][i] = Double.POSITIVE_INFINITY;
            }
        }

        List<E> edgeList = new ArrayList<>(graph.edgeSet());
        for (E e : edgeList) {
            V source = graph.getEdgeSource(e);
            V target = graph.getEdgeTarget(e);
            double weight = graph.getEdgeWeight(e);
            distanceMatrix[vertexMap.get(source)][vertexMap.get(target)] = weight;
        }
    }

    private void compute() {
        for (int k = 0; k < graph.vertexSet().size(); k++) {
            for (int i = 0; i < graph.vertexSet().size(); i++) {
                for (int j = 0; j < graph.vertexSet().size(); j++) {
                    if (distanceMatrix[i][k] * distanceMatrix[k][j] < distanceMatrix[i][j])
                        distanceMatrix[i][j] = distanceMatrix[i][k] * distanceMatrix[k][j];
                }
            }
        }
    }

    /**
     * This method is used to get the shortest path from source to dest
     * after computing it in the class constructor
     * @param source source node
     * @param dest destination node
     * @return the shortest path from source to dest
     */
    public double getRate(V source, V dest) {
        int sourceIndex = vertexMap.get(source);
        int destIndex = vertexMap.get(dest);

        return distanceMatrix[sourceIndex][destIndex];
    }

}
