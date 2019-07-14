package asad.model;

import java.io.Serializable;

public class PredictedLink implements Serializable {
    private Node node1;
    private Node node2;
    private Double probability;

    public PredictedLink(Node node1, Node node2, Double probability) {
        this.node1 = node1;
        this.node2 = node2;
        this.probability = probability;
    }

    public PredictedLink() {
    }

    public Node getNode1() {
        return node1;
    }

    public void setNode1(Node node1) {
        this.node1 = node1;
    }

    public Node getNode2() {
        return node2;
    }

    public void setNode2(Node node2) {
        this.node2 = node2;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }
}
