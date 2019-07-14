package asad.model;

public class Link {
    private Integer node1Id;
    private Integer node2Id;

    public Link(Integer node1Id, Integer node2Id) {
        this.node1Id = node1Id;
        this.node2Id = node2Id;
    }

    public Integer getNode1Id() {
        return node1Id;
    }

    public void setNode1Id(Integer node1Id) {
        this.node1Id = node1Id;
    }

    public Integer getNode2Id() {
        return node2Id;
    }

    public void setNode2Id(Integer node2Id) {
        this.node2Id = node2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        Link l = (Link) o;
        return (this.node1Id == l.node1Id || this.node1Id == l.node2Id) &&
                (this.node2Id == l.node2Id || this.node2Id == l.node1Id);
    }
}
