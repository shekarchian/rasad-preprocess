package asad.model;

public class PredictedLinksRequest {
    public enum GraphType {author, article}
    public enum Method {ccs, keyword}

    private GraphType graph_type;
    private Method method;
    private Integer page;
    private Integer size;

    public PredictedLinksRequest(GraphType graph_type, Method method, Integer page, Integer size) {
        this.graph_type = graph_type;
        this.method = method;
        this.page = page;
        this.size = size;
    }

    public GraphType getGraph_type() {
        return graph_type;
    }

    public void setGraph_type(GraphType graph_type) {
        this.graph_type = graph_type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
