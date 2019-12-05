package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Node a = new Node();
        Node c = new Node("second", "optional" ,null, null ,null);
        System.out.println(a.getLeft_child());
        Tree b = new Tree(a);
        System.out.println(b.getNodes().get(0).getInfo());
        b.add_node(a ,c);
        //System.out.println(b.getNodes().get(0).getInfo() + "  " + b.getNodes().get(1).getInfo());
        System.out.println(b);

    }
}
