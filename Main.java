package com.company;

import com.sun.xml.internal.ws.addressing.WsaActionUtil;

import java.util.Scanner;

/**
 * @author
 */
public class Main {

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

                for(int i = 0 ; i < temp_line.length; i++){
                    Node node = new Node();
                    node.setInfo(temp_line[i].trim());

                   // System.out.println(node);

//                    node.setParent(parent);
                    tree.add_node(parent,node);

                    if(!temp_line[i].contains("?"))
                        node.setStatus("Mandatory");

                    else
                        node.setStatus("Optional");

                }
            }
            if (str.contains("|")) {
                String[] temp_line = str.split("[=,|]");

                Node parent = new Node();



                parent.setInfo(temp_line[0]);

                for(int i = 0 ; i < temp_line.length; i++){
                    Node node = new Node();
                    node.setInfo(temp_line[i].trim());

                    // System.out.println(node);

//                    node.setParent(parent);
                    tree.add_node(parent,node);


                    node.setStatus("Or");

                }
            }
            if (str.contains("^")) {
                String[] temp_line = str.split("[=,^]");

                Node parent = new Node();



                parent.setInfo(temp_line[0]);

                for(int i = 0 ; i < temp_line.length; i++){
                    Node node = new Node();
                    node.setInfo(temp_line[i].trim());

                    // System.out.println(node);

//                    node.setParent(parent);
                    tree.add_node(parent,node);


                    node.setStatus("Alternative");

                }
            }
            if(str.equals("#"))
                break;
//            System.out.println(tree.getNodes().get(0).getInfo()+ tree.getNodes().get(1).getInfo());
            System.out.println(tree);
//            System.out.println(tree.getNodes().get(4).getRight_brother().getStatus());
        }
        System.out.println(tree.getNodes().get(9).getParent());
    }
}
