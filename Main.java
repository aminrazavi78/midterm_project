package com.company;

import java.util.*;

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
            this.Left_child = left_child;
        }

        void setRight_brother(Node right_brother) {
            this.Right_brother = right_brother;
        }

        @Override
        public boolean equals(Object o) {
            Node node = (Node) o;
            if (node.getInfo().equals(info))
                return true;
            return false;
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


    ///////////////////////node//////////////////////////////////
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


        void add_node(Node temp_node) {
//            System.out.println(temp_node.getInfo().trim());
            if (temp_node.getStatus().equals("Mandatory"))
                mandatory_nodes.add(temp_node);

            if (!nodes.contains(temp_node)) {
                nodes.add(temp_node);
            }
        }

        void add_node(Node parent, Node temp_node) {
//            System.out.println(parent.getInfo().trim() + " " + temp_node.getInfo().trim());
            if(search_parent(temp_node.getInfo().trim()) != null)
                temp_node = search_parent(temp_node.getInfo().trim());

            if (temp_node.getStatus().equals("Mandatory"))
                mandatory_nodes.add(temp_node);


            temp_node.setParent(parent);
            if (parent.getLeft_child() == null) {
                parent.setLeft_child(temp_node);
            } else {
                boolean check_add_right_brother = true;
                Node node = parent.getLeft_child();
                if (node.getInfo().trim().equals(temp_node.getInfo().trim()))
                    check_add_right_brother = false;
                if (parent.getInfo().trim().equals("m") && temp_node.getInfo().trim().equals("n"))
                    while (node.getRight_brother() != null) {
                        node = node.getRight_brother();
                        if (node.getInfo().trim().equals(temp_node.getInfo().trim()))
                            check_add_right_brother = false;
                    }
                if (check_add_right_brother)
                    node.setRight_brother(temp_node);
            }
            if (!nodes.contains(temp_node))
                  nodes.add(temp_node);
        }

        boolean search(Node node) {
            for (Node n : nodes)
                if (n.getInfo().trim().equals(node.getInfo().trim()))
                    return true;
            return false;
        }

        Node search2(Node node) {
            for (Node node_tree : nodes) {
                if (node.getInfo().trim().equals(node_tree.getInfo().trim())) {
                    return node_tree;
                }
            }
            return null;
        }

        public Node search_parent(String info) {
            Node temp = null;
            for (Node node : nodes)
                if (node.getInfo().trim().equals(info.trim())) {
                    temp = node;
                }
            return temp;
        }

        public void find_father() {
            for (Node node : nodes) {
                if (node.getParent() == null) {
                    for (Node temp_parent : nodes) {
                        if (temp_parent.getLeft_child() != null)
                            if (temp_parent.getLeft_child().getInfo().trim().equals(node.getInfo().trim())) {
                                node.setParent(temp_parent);
                            }
                        if (temp_parent.getRight_brother() != null)
                            if (temp_parent.getRight_brother().getInfo().trim().equals(node.getInfo().trim())) {
                                node.setParent(temp_parent.getParent());
                            }
                    }
                }
            }
        }

        public void delete_extra() {
            ArrayList<Node> deleting = new ArrayList<>();
            for (Node node : nodes) {
                if (node.getParent() != null)
                    if (node.getInfo().trim().equals(node.getParent().getInfo().trim()))
                        deleting.add(node);

            }
            for (Node node : deleting) {
                nodes.remove(node);

            }
        }

        @Override
        public String toString() {
            String str = " ";
            for (int i = 0; i < nodes.size(); i++) {
                str += "node is:" + nodes.get(i).getInfo() + "  " + "father : " + nodes.get(i).getParent();
            }
            return str;
        }

    }

    ///////////////////////////tree////////////////////////////////
    private static boolean check(Tree tree, HashSet<Node> set) {
        for (Node node : set) {
            if (node.getStatus().equals("Mandatory"))
                if (!node.equals(tree.search2(node)))
                    return false;
        }
        for (Node node : set) {
            if (!tree.search(node)) {
                return false;
            }
        }
        for (Node node : set) {
            Node n = tree.search2(node);
            Node q = new Node();
            q.setInfo(n.getParent().getInfo().trim());
            if (!set.contains(q) && !q.getInfo().equals("Fake"))
                return false;
        }
        for (Node n1 : set) {
            Node node = tree.search2(n1);
            if (node.getLeft_child() != null) {
                int counter = 0;
                int mcounter = 0;
                Node backup = node.getLeft_child();
                Node child = node.getLeft_child();
                while (child != null) {
                    if (set.contains(child))
                        counter++;
                    if (child.getStatus().equals("Mandatory")) {
                        if (!set.contains(child))
                            return false;
                        mcounter++;
                    }
                    child = child.getRight_brother();
                }
                child = backup;
                if (child.getStatus().equals("Alternative") && counter != 1) {
                    return false;
                }
                if (child.getStatus().equals("Or") && counter == 0) {
                    return false;
                }
                if ((child.getStatus().equals("Optional") || child.getStatus().equals("Mandatory")) && counter < mcounter) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<String> result = new LinkedList<>();
        String str = scan.nextLine().replace(" ", "");

        while (!str.equals("###")) {
            Node fake_father = new Node();
            fake_father.setInfo("Fake");
            Tree tree = new Tree(fake_father);
            boolean check_point = true;
            while (!str.equals("#")) {
                if (str.contains("=") && !str.contains("+") && !str.contains("^") && !str.contains("|")) {
                    Node node = new Node();

                    String[] temp_line = str.split("[=]");
                    if(tree.search_parent(temp_line[1].trim()) != null)
                        node= tree.search_parent(temp_line[1].trim());
                    Node parent = tree.search_parent(temp_line[0].trim());
                    if (str.contains("?")) {
                        node.setInfo(temp_line[1].replace("?", "").trim());
                        node.setStatus("Optional");
                    } else {
                        node.setStatus("Mandatory");
                        node.setInfo(temp_line[1].trim());
                    }
                    if (parent == null && check_point == false) {
                        parent = new Node();
                        parent.setInfo(temp_line[0].trim());
                        node.setInfo(temp_line[1].trim());
                        if (str.contains("?")) {
                            node.setInfo(temp_line[1].replace("?", "").trim());
                            node.setStatus("Optional");
                        } else
                            node.setStatus("Mandatory");
                        parent.setLeft_child(node);
                        tree.add_node(parent);
                        tree.add_node(parent, node);

                    } else if (check_point == true) {
                        parent = new Node();
                        parent.setStatus("Mandatory");
                        parent.setInfo(temp_line[0].trim());
                        tree.add_node(tree.getRoot(), parent);
                        //parent.setInfo(temp_line[0].trim());
                        tree.add_node(parent, node);
                    } else {
//                        tree.add_node(parent);
                        tree.add_node(parent, node);
                    }


                }
                if (!str.contains("=")) {
                    Node node = new Node();
                    node.setInfo(str.trim());

                    tree.add_node(tree.getRoot(), node);
                }

                if (str.contains("+")) {
                    String[] temp_line = str.split("[=,+]");
                    Node parent = tree.search_parent(temp_line[0].trim());
                    if (parent == null && check_point == false) {

                        parent = new Node();
                        parent.setInfo(temp_line[0].trim());
                        tree.add_node(parent);
                    }

                    if (check_point == true) {
                        parent = new Node();
                        parent.setStatus("Mandatory");
                        parent.setInfo(temp_line[0].trim());

                        tree.add_node(tree.getRoot(), parent);
                    }
                    for (int i = 1; i < temp_line.length; i++) {
                        Node node = new Node(); // new nabayad beshe lozoman
                        if(tree.search_parent(temp_line[i].trim()) != null)
                            node= tree.search_parent(temp_line[i].trim());
                        node.setInfo(temp_line[i].trim());
                        if (!temp_line[i].contains("?"))
                            node.setStatus("Mandatory");
                        else {

                            node.setStatus("Optional");
                            node.setInfo(node.getInfo().replace("?", "").trim());
                        }
                        //if (!tree.getNodes().contains(node))

                        tree.add_node(parent, node);
                        // if(tree.getNodes().contains(node) && tree.search2(node).getParent()== null)
                        // tree.search2(node).setParent(parent);

                    }

                }
                if (str.contains("|")) {
                    String[] temp_line = str.split("[=,|]");
                    Node parent = tree.search_parent(temp_line[0].trim());
                    if (parent == null && check_point == false) {
                        parent = new Node();
                        parent.setInfo(temp_line[0].trim());
                        tree.add_node(parent);
                    }
                    if (check_point == true) {
                        parent = new Node();
                        parent.setStatus("Mandatory");
                        parent.setInfo(temp_line[0].trim());
                        tree.add_node(tree.getRoot(), parent);
                    }
                    for (int i = 1; i < temp_line.length; i++) {
                        Node node = new Node();
                        if(tree.search_parent(temp_line[i].trim()) != null)
                            node= tree.search_parent(temp_line[i].trim());
                        node.setInfo(temp_line[i].trim());
                        node.setStatus("Or");


                        tree.add_node(parent, node);
                    }
                }
                if (str.contains("^")) {
                    String[] temp_line = str.split("[=,^]");
                    Node parent = tree.search_parent(temp_line[0].trim());
                    if (parent == null && check_point == false) {
                        parent = new Node();
                        parent.setInfo(temp_line[0].trim());
                        tree.add_node(parent);
                    }
                    if (check_point == true) {
                        parent = new Node();
                        parent.setStatus("Mandatory");
                        parent.setInfo(temp_line[0].trim());

                        tree.add_node(tree.getRoot(), parent);
                    }
                    for (int i = 1; i < temp_line.length; i++) {
                        Node node = new Node();
                        if(tree.search_parent(temp_line[i].trim()) != null)
                            node= tree.search_parent(temp_line[i].trim());
                        node.setInfo(temp_line[i].trim());
                        node.setStatus("Alternative");
                        tree.add_node(parent, node);
                    }
                }
                check_point = false;
                str = scan.nextLine().replace(" ", "");
            }
            tree.find_father();
            tree.delete_extra();
//            System.out.println(tree);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ArrayList<HashSet<Node>> sets = new ArrayList<>();
            String input = scan.nextLine().replace(" ", "");
            while (!input.equals("##")) {
                input = input.replaceAll("\\{", "");
                input = input.replaceAll("\\}", "");
                String[] temp_line1 = input.split(",");
                HashSet<Node> temp_set = new HashSet<>();
                for (int i = 0; i < temp_line1.length; i++) {
                    Node node = new Node();
                    node.setInfo(temp_line1[i].trim());
                    temp_set.add(node);
                }
                sets.add(temp_set);
                input = scan.nextLine().replace(" ", "");

            }
            for (HashSet<Node> final_set : sets) {
                if (check(tree, final_set))
                    result.add("Valid");
                else
                    result.add("Invalid");

            }
            result.add("+++");
            str = scan.nextLine().replace(" ", "");
        }
        for (String s : result) {
            System.out.println(s);
        }
    }
}
