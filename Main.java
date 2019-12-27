
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


            temp_node.setParent(parent);
            if (parent.getLeft_child() == null) {
                parent.setLeft_child(temp_node);

            } else {
                Node node = parent.getLeft_child();

                while (node.getRight_brother() != null)
                    node = node.getRight_brother();

                node.setRight_brother(temp_node);


            }
            nodes.add(temp_node);


        }

        boolean search(Node node) {
            for (Node n : nodes)
                if (n.getInfo().equals(node.getInfo().trim()))
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

        @Override
        public String toString() {
            String str = "node is:";
            for (int i = 0; i < nodes.size(); i++) {
                str += nodes.get(i).getInfo() + "  " + "left child : " + nodes.get(i).getLeft_child();
            }
            return str;
        }

        public void print() {
            for (Node node : mandatory_nodes) {
//                System.out.print(node + " ");

            }
        }
    }
///////////////////////tree////////////////////////////////

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
            q.setInfo(n.getParent().getInfo());
            if (!set.contains(q) && !q.getInfo().equals("Fake"))
                return false;
        }
        for (Node n1 : set) {
            Node node = tree.search2(n1);

            if (node.getLeft_child() != null) {
                int counter = 0;
                int mcounter = 0;
                int ocounter = 0;
                Node backup = node.getLeft_child();
                Node child = node.getLeft_child();
                while (child != null) {
                    if (set.contains(child))
                        counter++;
                    if (child.getStatus().equals("Mandatory"))
                        mcounter++;
                    if (child.getStatus().equals("Optional"))
                        ocounter++;
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
        String str = scan.nextLine();
        while (!str.equals("###")) {
            Node fake_father = new Node();
            fake_father.setInfo("Fake");
            Tree tree = new Tree(fake_father);
            boolean check_point = true;
            while (!str.equals("#")) {
                if (str.contains("=") && !str.contains("+") && !str.contains("^") && !str.contains("|")) {
                    Node node = new Node();
                    Node parent = new Node();
                    String[] temp_line = str.split("[=]");
                    parent.setInfo(temp_line[0].trim());
                    if (str.contains("?"))
                        node.setStatus("Optional");
                    node.setInfo(temp_line[1].replace("?", "").trim());
                    tree.add_node(tree.getRoot(), parent);
                    tree.add_node(parent, node);
                }
                if (!str.contains("=")) {
                    Node node = new Node();
                    node.setInfo(str);
                    tree.add_node(tree.getRoot(), node);
                }

                if (str.contains("+")) {
                    String[] temp_line = str.split("[=,+]");

                    Node parent = tree.search_parent(temp_line[0].trim());
                    if (check_point == true) {
                        parent = new Node();
                        parent.setStatus("Mandatory");
                        parent.setInfo(temp_line[0].trim());
                        tree.add_node(tree.getRoot(), parent);
                    }
                    for (int i = 1; i < temp_line.length; i++) {
                        Node node = new Node();
                        node.setInfo(temp_line[i].trim());

                        if (!temp_line[i].contains("?"))
                            node.setStatus("Mandatory");
                        else {
                            node.setStatus("Optional");
                            node.setInfo(node.getInfo().replace("?", ""));
                        }


                        if (!tree.nodes.contains(node))
                            tree.add_node(parent, node);
                    }
                }
                if (str.contains("|")) {
                    String[] temp_line = str.split("[=,|]");
                    Node parent = tree.search_parent(temp_line[0].trim());
                    if (check_point == true) {
                        parent = new Node();
                        parent.setStatus("Mandatory");
                        parent.setInfo(temp_line[0].trim());
                        tree.add_node(tree.getRoot(), parent);

                    }
                    for (int i = 1; i < temp_line.length; i++) {
                        Node node = new Node();
                        node.setInfo(temp_line[i].trim());
                        node.setStatus("Or");
                        if (!tree.nodes.contains(node))
                            tree.add_node(parent, node);

                    }
                }
                if (str.contains("^")) {
                    String[] temp_line = str.split("[=,^]");

                    Node parent = tree.search_parent(temp_line[0].trim());
                    //parent.setInfo(temp_line[0]);
                    if (check_point == true) {
                        parent = new Node();
                        parent.setStatus("Mandatory");
                        parent.setInfo(temp_line[0].trim());
                        tree.add_node(tree.getRoot(), parent);

                    }


                    for (int i = 1; i < temp_line.length; i++) {
                        Node node = new Node();
                        node.setInfo(temp_line[i].trim());
//                    System.out.println(node);
//                    node.setParent(parent);
                        node.setStatus("Alternative");
                        if (!tree.nodes.contains(node))
                            tree.add_node(parent, node);

                    }
                }
                check_point = false;
                str = scan.nextLine();
            }
            // System.out.println(tree.getNodes().get(9).getParent());
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ArrayList<HashSet<Node>> sets = new ArrayList<>();
            String input = scan.nextLine();

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
                input = scan.nextLine();
            }

            for (HashSet<Node> final_set : sets) {
                if (check(tree, final_set))
                    result.add("Valid");
                else
                    result.add("Invalid");
            }
            result.add("+++");

            str = scan.nextLine();
        }

        for (String s : result) {
            System.out.println(s);
        }
    }
}