package model.implementation;

import model.Graph;
import utils.Graphs;

import java.util.ArrayList;
import java.util.List;

public class WeightedGraph implements Graph {
    private final List<List<Integer>> adjacencyMatrix;

    public WeightedGraph(List<List<Integer>> adjacencyMatrix) {
        Graphs.matrixContainsNull(adjacencyMatrix);
        testWeightedGraph(adjacencyMatrix);

        this.adjacencyMatrix = Graphs.initImmutableMatrix(adjacencyMatrix);
    }
    static void testWeightedGraph(List<List<Integer>> adjacencyMatrix) {
        List<Integer> allElements=new ArrayList<>();
        adjacencyMatrix.forEach(allElements::addAll);

        if (allElements.stream().noneMatch(weight -> weight > 1))
            throw new RuntimeException("Graph is non-weighted !");
    }
    @Override
    public List<List<Integer>> getAdjacencyMatrix() {
        return adjacencyMatrix;
    }


}
