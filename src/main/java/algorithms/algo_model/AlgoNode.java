package algorithms.algo_model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlgoNode {
    private final Integer nodeNr;
    private boolean visited;
    private Integer distance;
    private List<Integer> shortestPath;

    public AlgoNode(Integer nodeNr) {
        this.nodeNr = nodeNr;
        this.visited=false;
        this.distance=Integer.MAX_VALUE;
        this.shortestPath=new ArrayList<>();
    }

    public AlgoNode(Integer nodeNr,Integer distance,List<Integer> path){
        this(nodeNr);
        this.distance=distance;
        this.updatePath(path);
    }

    public Integer getNodeNr(){
        return nodeNr;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited() {
        this.visited = true;
    }

    public Integer getDistance() {
        return distance;
    }

    public void updateDistance(Integer distance) {
        this.distance += distance;
    }

    public List<Integer> getShortestPath() {
        return new ArrayList<>(shortestPath);
    }

    public void addToPath(Integer newNode){
        shortestPath.add(newNode);
    }

    public void updatePath(List<Integer> newPath) {
        shortestPath = new ArrayList<>(newPath);
        if (!lastElementIsNodeNr())
            shortestPath.add(nodeNr);
    }

    public Integer getNormalizedDistance(){
        return distance==Integer.MAX_VALUE ? 0 : distance;
    }

    private boolean lastElementIsNodeNr(){
        return nodeNr.equals(shortestPath.get(shortestPath.size()-1));
    }

    public boolean hasShorterPathThan(AlgoNode other){
        return compareDistanceTo(this,other)<0;
    }

    public void updateWith(AlgoNode other) {
        distance= other.getDistance();
        shortestPath=other.getShortestPath();
    }

    public void setStartDistance() {
        distance=0;
    }

    public static int compareDistanceTo(AlgoNode first,AlgoNode second){
        return first.distance.compareTo(second.distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AlgoNode)) return false;

        AlgoNode algoNode = (AlgoNode) o;

        return nodeNr.equals(algoNode.nodeNr);
    }

    @Override
    public int hashCode() {
        return nodeNr.hashCode();
    }

    @Override
    public String toString() {
        return "AlgoNode{" +
                "nodeNr=" + nodeNr +
                ", visited=" + visited +
                ", distance=" + distance +
                ", shortestPath=" + shortestPath +
                '}';
    }
}
