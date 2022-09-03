package utils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Graphs {

    private Graphs() {}

    public static List<List<Integer>> initImmutableMatrix(List<List<Integer>> adjacencyMatrix) {
        return List.copyOf(
                adjacencyMatrix.stream()
                        .map(List::copyOf)
                        .collect(Collectors.toList()));
    }

    public static boolean matrixContainsNull(List<List<Integer>> adjacencyMatrix) {
        try {
            Objects.requireNonNull(adjacencyMatrix);
            adjacencyMatrix.forEach(Objects::requireNonNull);
            adjacencyMatrix.forEach(list->list.forEach(Objects::requireNonNull));
        }catch (Exception e){
            return true;
        }
        return false;
    }

    public static boolean isSquareMatrix(List<List<Integer>> adjacencyMatrix){
        return adjacencyMatrix.stream().allMatch(list-> list.size() == adjacencyMatrix.size());
    }

    public static boolean containsNodes(List<List<Integer>> adjacencyMatrix,Integer...nodesList){
        return Arrays.stream(nodesList).allMatch(node->node<adjacencyMatrix.size());
    }
}
