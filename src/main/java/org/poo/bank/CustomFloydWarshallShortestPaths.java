package org.poo.bank;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomFloydWarshallShortestPaths <V, E> {
    private final Graph<V, E> graph;
    private final double[][] distanceMatrix;
    private final Map<V, Integer> vertexMap;


    public CustomFloydWarshallShortestPaths(Graph<V, E> graph) {
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

    public double getRate(V source, V dest) {
        int sourceIndex = vertexMap.get(source);
        int destIndex = vertexMap.get(dest);

        return distanceMatrix[sourceIndex][destIndex];
    }

    public void printIt() {
        for (V v : graph.vertexSet()) {
            int sourceIndex = vertexMap.get(v);

            List<V> vertexList = new ArrayList<>(graph.vertexSet());
            for (V vertex : vertexList) {
                int destIndex = vertexMap.get(vertex);
                System.out.printf("%s to %s has rate: %f\n", v, vertex, distanceMatrix[sourceIndex][destIndex]);
            }
        }
    }
}
