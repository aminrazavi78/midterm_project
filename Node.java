package com.company;


import java.util.Objects;

public class Node {
    private String info ;
    private String status;
    private Node parent ;
    private Node Left_child ;
    private Node Right_brother;

    public String getInfo() {
        return info;
    }

    public String getStatus() {
        return status;
    }

    public Node getParent() {
        return parent;
    }

    public Node getLeft_child() {
        return Left_child;
    }

    public Node getRight_brother() {
        return Right_brother;
    }

    //new
    public Node(String info, String status, Node parent, Node left_child, Node right_brother) {
        this.info = info;
        this.status = status;
        this.parent = parent;
        Left_child = left_child;
        Right_brother = right_brother;
    }

    public Node(){
        this.info = "";
        this.status = "";
        this.parent = null;
        this.Left_child = null;
        this.Right_brother = null;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public void setLeft_child(Node left_child) {
        Left_child = left_child;
    }

    public void setRight_brother(Node right_brother) {
        Right_brother = right_brother;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return info.equals(node.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }

    @Override
    public String toString() {
        return "Node{" +
                "info='" + info + '\'' +
                '}';
    }
}
