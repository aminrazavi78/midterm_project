package com.company;

import java.util.ArrayList;

public class Tree {
    private Node root;
    private ArrayList<Node> nodes = new ArrayList<>();
    private  ArrayList<Node> mandatory_nodes = new ArrayList<>();



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

    public ArrayList<Node> getMandatory_nodes() {
        return mandatory_nodes;
    }

    public void add_node(Node parent, Node temp_node) {
        if(temp_node.getStatus().equals("Mandatory"))
            mandatory_nodes.add(temp_node);

        if (nodes.size() == 0) {
            root = temp_node;
            nodes.add(temp_node);
        } else {
            temp_node.setParent(parent);
            if (parent.getLeft_child() == null)
                parent.setLeft_child(temp_node);
            else {
                Node node = parent.getLeft_child();
                while (node.getRight_brother() != null)
                    node = node.getRight_brother();
                node.setRight_brother(temp_node);

            }
            nodes.add(temp_node);

        }
    }

    public boolean search(Node node) {
        if (nodes.contains(node))
            return true;
        else return false;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < nodes.size(); i++) {
            str += nodes.get(i).getInfo() + "  ";
        }
        return str;
    }

}
