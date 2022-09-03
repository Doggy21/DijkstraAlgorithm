import algorithms.dijkstra.Dijkstra;
import model.Graph;
import model.implementation.WeightedGraph;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<List<Integer>> matrix=List.of(
                List.of(0,4,2,4,0,0,0),
                List.of(4,0,0,6,2,0,0),
                List.of(2,0,0,1,0,0,0),
                List.of(4,6,1,0,3,2,0),
                List.of(0,2,0,3,0,1,1),
                List.of(0,0,0,2,1,0,5),
                List.of(0,0,0,0,1,5,0)
        );

        List<List<Integer>> matrixIsolatedNode=List.of(
                List.of(0,4,0,4,0,0,0),
                List.of(4,0,0,6,2,0,0),
                List.of(2,0,0,1,0,0,0),
                List.of(4,6,0,0,3,2,0),
                List.of(0,2,0,3,0,1,1),
                List.of(0,0,0,2,1,0,5),
                List.of(0,0,0,0,1,5,0)
        );

        List<List<Integer>> directedGraphMatrix=List.of(
                List.of(0,2,0,0),
                List.of(1,0,0,0),
                List.of(0,0,0,1),
                List.of(0,0,1,0)
        );

        Graph graph=new WeightedGraph(matrix);

        Dijkstra dijkstra=new Dijkstra(graph,0,6);
        System.out.println(dijkstra.findPath());
    }
}
