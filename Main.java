package com.company;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;
import sun.security.jgss.GSSUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @author
 */
public class Main {
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
                if (child.getStatus().equals("Or") && (counter == 0 || counter < 0))
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
            System.out.println(tree);
//            System.out.println(tree.getNodes().get(4).getRight_brother().getStatus());
        }
        // System.out.println(tree.getNodes().get(9).getParent());
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
        HashSet<Node> a = sets.get(0);
        for (Node node : a) {
            System.out.println(node + " : " + tree.search(node));
        }

        for (HashSet <Node> final_set:sets) {
            if(check(tree, final_set))
                System.out.println("Valid");
            else
                System.out.println(("Invalid"));

        }



    }
}
