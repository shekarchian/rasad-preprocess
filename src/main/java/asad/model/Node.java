package asad.model;

public class Node {
    private Integer code;
    private String words;

    public Node() {
    }

    public Node(Integer code, String words) {
        this.code = code;
        this.words = words;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}

