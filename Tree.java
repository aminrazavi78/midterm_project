package com.company;

import java.util.ArrayList;

public class Tree {
    private Node root;
    private ArrayList<Node> nodes = new ArrayList<>();

    public Tree(Node root) {
        this.root = root;
        nodes.add(root);
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public Node getRoot() {
        return root;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public void add_node(Node parent, Node temp_node) {
        if (nodes.size() == 0) {
            root = temp_node;
            nodes.add(temp_node);
        } else {
            temp_node.setParent(parent);
            parent.setLeft_child(temp_node);
            nodes.add(temp_node);
        }

    }

    @Override
    public String toString() {
        String str = "" ;
        for(int i = 0 ; i < nodes.size(); i++ ){
            str += nodes.get(i).getInfo() + "  ";
        }
        return str;
    }
}
