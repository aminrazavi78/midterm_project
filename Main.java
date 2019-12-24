package com.company;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

/**
 * @author Amin Razavi
 */
 class Main {

///////////////////////////node////////////////////////////
public static class Node {
    private String info;
    private String status;
    private Node parent;
    private Node Left_child;
    private Node Right_brother;

    String getInfo() {
        return info;
    }

    String getStatus() {
        return status;
    }

    Node getParent() {
        return parent;
    }

    Node getLeft_child() {
        return Left_child;
    }

    Node getRight_brother() {
        return Right_brother;
    }

    //new
    Node(String info, String status, Node parent, Node left_child, Node right_brother) {
        this.info = info;
        this.status = status;
        this.parent = parent;
        Left_child = left_child;
        Right_brother = right_brother;
    }

    Node() {
        this.info = "";
        this.status = "";
        this.parent = null;
        this.Left_child = null;
        this.Right_brother = null;
    }

    void setInfo(String info) {
        this.info = info;
    }

    void setStatus(String status) {
        this.status = status;
    }

    void setParent(Node parent) {
        this.parent = parent;
    }

    void setLeft_child(Node left_child) {
        Left_child = left_child;
    }

    void setRight_brother(Node right_brother) {
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
//////////////////////node//////////////////////////////
//////////////////////tree///////////////////////////////////
    static class Tree {
        private Node root;
        private ArrayList<Node> nodes = new ArrayList<>();
        private ArrayList<Node> mandatory_nodes = new ArrayList<>();


        Tree(Node root) {
            this.root = root;
            nodes.add(root);
        }

        void setRoot(Node root) {
            this.root = root;
        }

        void setNodes(ArrayList<Node> nodes) {
            this.nodes = nodes;
        }

        Node getRoot() {
            return root;
        }

        ArrayList<Node> getNodes() {
            return nodes;
        }

        ArrayList<Node> getMandatory_nodes() {
            return mandatory_nodes;
        }

        void add_node(Node parent, Node temp_node) {
            if (temp_node.getStatus().equals("Mandatory"))
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

        boolean search(Node node) {
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
///////////////////////tree////////////////////////////////

    private static boolean check(Tree tree, HashSet<Node> set) {
        for (Node node : set) {
            if (!tree.search(node))
                return false;
        }
        for (Node node : tree.getMandatory_nodes()) {
            if (!set.contains(node))
                return false;


        }
        for (Node node : tree.getNodes()) {
            if (node.getLeft_child() != null && (node.getLeft_child().getStatus().equals("Alternative") || node.getLeft_child().getStatus().equals("Or"))) {
                int counter = 0;
                Node child = node.getLeft_child();
                while (child.getRight_brother() != null) {
                    if (set.contains(child))
                        counter++;
                    child = child.getRight_brother();
                }
                if (child.getStatus().equals("Alternative") && counter != 1)
                    return false;
                else if  (child.getStatus().equals("Or") && (counter == 0 || counter < 0))
                    return false;
            }


        }
        return true;
    }

    public static void main(String[] args) {
        // write your code here
//        Node a = new Node();
//        Node c = new Node("second", "optional" ,null, null ,null);
//        System.out.println(a.getLeft_child());
//        Tree b = new Tree(a);
//        System.out.println(b.getNodes().get(0).getInfo());
//        b.add_node(a ,c);
//        //System.out.println(b.getNodes().get(0).getInfo() + "  " + b.getNodes().get(1).getInfo());
//        System.out.println(b);

        Scanner scan = new Scanner(System.in);
        Node fake_father = new Node();
        fake_father.setInfo("Fake");
        Tree tree = new Tree(fake_father);
        while (true) {
            String str = scan.nextLine();

            if (str.contains("+")) {
                String[] temp_line = str.split("[=,+]");

                Node parent = new Node();


                parent.setInfo(temp_line[0]);

                for (int i = 0; i < temp_line.length; i++) {
                    Node node = new Node();
                    node.setInfo(temp_line[i].trim());

                    // System.out.println(node);

//                    node.setParent(parent);
                    tree.add_node(parent, node);

                    if (!temp_line[i].contains("?"))
                        node.setStatus("Mandatory");

                    else
                        node.setStatus("Optional");

                }
            }
            if (str.contains("|")) {
                String[] temp_line = str.split("[=,|]");

                Node parent = new Node();


                parent.setInfo(temp_line[0]);

                for (int i = 0; i < temp_line.length; i++) {
                    Node node = new Node();
                    node.setInfo(temp_line[i].trim());

                    // System.out.println(node);

//                    node.setParent(parent);
                    tree.add_node(parent, node);


                    node.setStatus("Or");

                }
            }
            if (str.contains("^")) {
                String[] temp_line = str.split("[=,^]");

                Node parent = new Node();


                parent.setInfo(temp_line[0]);

                for (int i = 0; i < temp_line.length; i++) {
                    Node node = new Node();
                    node.setInfo(temp_line[i].trim());

                    // System.out.println(node);

//                    node.setParent(parent);
                    tree.add_node(parent, node);


                    node.setStatus("Alternative");

                }
            }
            if (str.equals("#"))
                break;
//            System.out.println(tree.getNodes().get(0).getInfo()+ tree.getNodes().get(1).getInfo());
//            System.out.println(tree);
//            System.out.println(tree.getNodes().get(4).getRight_brother().getStatus());
        }
        // System.out.println(tree.getNodes().get(9).getParent());
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ArrayList<HashSet<Node>> sets = new ArrayList<>();
        while (true) {

            String input = scan.nextLine();
            if (input.equals("##"))
                break;
            input = input.replaceAll("\\{", "");
            input = input.replaceAll("\\}", "");

            String[] temp_line1 = input.split(",");
            HashSet<Node> temp_set = new HashSet<>();
            for (int i = 0; i < temp_line1.length; i++) {
                Node node = new Node();
                node.setInfo(temp_line1[i]);
                temp_set.add(node);
            }
            sets.add(temp_set);


        }
        scan.nextLine();
//        HashSet<Node> a = sets.get(0);
//        for (Node node : a) {
//            System.out.println(node + " : " + tree.search(node));
//        }

        for (HashSet<Node> final_set : sets) {
            if (check(tree, final_set))
                System.out.println("Valid");
            else
                System.out.println(("Invalid"));

        }


    }
}
