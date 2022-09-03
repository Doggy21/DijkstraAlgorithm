package algorithms.dijkstra;

import algorithms.algo_model.AlgoNode;
import model.Graph;
import utils.Graphs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Dijkstra {

    private final List<AlgoNode> nodes;

    private final Integer start;
    private final Integer end;
    private final List<List<Integer>> adjacencyMatrix;

    public Dijkstra(Graph graph, Integer start, Integer end) {
        requireNotNull(graph, start, end);

        this.start = start;
        this.end = end;
        this.adjacencyMatrix = Graphs.initImmutableMatrix(graph.getAdjacencyMatrix());
        this.nodes = IntStream.range(0, adjacencyMatrix.size()).mapToObj(AlgoNode::new).collect(Collectors.toList());

    }

    public List<Integer> findPath() {
        testMatrix();
        initStartNode();
        initShortestPath();

        return startAlgorithm();
    }

    private void testMatrix() {
        if (Graphs.matrixContainsNull(adjacencyMatrix)) throw new RuntimeException("Matrix contains null values.");
        if(!Graphs.isSquareMatrix(adjacencyMatrix)) throw new RuntimeException("Adjacency matrix should be a square matrix.");
        if (!Graphs.containsNodes(adjacencyMatrix,start,end)) throw new RuntimeException("Start or end node does not exists in graph.");
        checkForIsolatedNodes();
    }

    private void checkForIsolatedNodes() {

        checkUnreachableNodes();
        checkUnstartableNode();
    }

    private void checkUnstartableNode() {
        if (allValuesAreZero(adjacencyMatrix.get(start))) {
            throw new RuntimeException("Cannot start from node " + start + " because is not bound with any other node !");
        }
    }

    private void checkUnreachableNodes() {
        for (int col = 0; col < adjacencyMatrix.size(); col++) {
            evaluateValues(col, getAdjacencyMatrixColumn(col));
        }
    }

    private List<Integer> getAdjacencyMatrixColumn(int col) {
        List<Integer> tempList = new ArrayList<>();
        for (List<Integer> matrix : adjacencyMatrix) {
            tempList.add(matrix.get(col));
        }
        return tempList;
    }

    private void evaluateValues(int col, List<Integer> tempList) {
        if (notStartNode(start, col) && allValuesAreZero(tempList)) {
            throw new RuntimeException("Node " + col + " is unreachable. You can only start from this node!");
        }
    }

    private boolean notStartNode(Integer start, int col) {
        return col != start;
    }

    private boolean allValuesAreZero(List<Integer> tempList) {
        return tempList.stream().allMatch(value -> value == 0);
    }

    private void requireNotNull(Object... objList) {
        Arrays.stream(objList).forEach(Objects::requireNonNull);
    }

    private void initStartNode() {
        getAlgoNode(start).setVisited();
        getAlgoNode(start).setStartDistance();
    }

    private void initShortestPath() {
        nodes.forEach(algoNode -> algoNode.addToPath(start));
    }

    private List<Integer> startAlgorithm() {
        while (existsUnvisitedNodes()) {
            parseVisitedAdjacentNodes();
        }

        return shortestPathOf();
    }

    private List<Integer> shortestPathOf() {
        return getAlgoNode(end).getShortestPath();
    }

    private void parseVisitedAdjacentNodes() {
        List<AlgoNode> adjacentNodesWithDistances = findAdjacentNodesDistances(getVisitedNodes());
        setNewVisitedNode(adjacentNodesWithDistances);
        updateDistancesAndPaths(adjacentNodesWithDistances);
    }

    private List<AlgoNode> findAdjacentNodesDistances(List<AlgoNode> visitedNodes) {

        List<AlgoNode> adjacentNodes = new ArrayList<>();

        for (AlgoNode visNode : visitedNodes) {
            adjacentNodes.addAll(getAdjacentNodes(visNode));
        }

        return adjacentNodes.stream().filter(Predicate.not(visitedNodes::contains)).collect(Collectors.toList());
    }

    private List<AlgoNode> getAdjacentNodes(AlgoNode node) {
        List<AlgoNode> adjacentNodesRaw = IntStream.range(0, adjacencyMatrix.size())
                .mapToObj(i -> new AlgoNode(i, getMatrixElement(i, node.getNodeNr()), node.getShortestPath()))
                .filter(algoNode -> existVertex(algoNode.getDistance()))
                .collect(Collectors.toList());

        adjacentNodesRaw.forEach(algoNode -> algoNode.updateDistance(node.getNormalizedDistance()));


        return adjacentNodesRaw;
    }

    private List<AlgoNode> getVisitedNodes() {
        return nodes.stream().filter(AlgoNode::isVisited).collect(Collectors.toList());

    }

    private void setNewVisitedNode(List<AlgoNode> adjacentNodesWithDistances) {
        AlgoNode minDistanceNode = adjacentNodesWithDistances.stream()
                .min(AlgoNode::compareDistanceTo)
                .orElseThrow();

        getAlgoNode(minDistanceNode.getNodeNr()).setVisited();
    }

    private void updateDistancesAndPaths(List<AlgoNode> adjacentNodesWithDistances) {
        for (AlgoNode node : nodes) {
            for (AlgoNode adjacentNode : adjacentNodesWithDistances) {
                if (adjacentNode.equals(node) && adjacentNode.hasShorterPathThan(node)) {
                    node.updateWith(adjacentNode);
                }
            }
        }

    }

    private Boolean existVertex(Integer distance) {
        return distance > 0;
    }

    private Integer getMatrixElement(int line, int column) {
        return adjacencyMatrix.get(column).get(line);
    }

    private AlgoNode getAlgoNode(Integer nodeNr) {
        return nodes.stream()
                .filter(algoNode -> Objects.equals(algoNode.getNodeNr(), nodeNr))
                .findFirst()
                .orElseThrow();
    }

    private boolean existsUnvisitedNodes() {
        return nodes.stream().anyMatch(Predicate.not(AlgoNode::isVisited));
    }


}
