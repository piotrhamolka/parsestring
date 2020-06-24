package com.testtask.stringparsing;

import java.util.*;
import java.util.stream.Collectors;

public class ParseStringToTree {

    private int hyphCounter;

    public void parse(String input) {
        hyphCounter = 0;
        ArrayList<Node> list = buildTree(cleanInputString(input));
        printNodeList(sortList(list));
    }

    public ArrayList<Node> buildTree(String input) {

        int parenCount = 0;
        ArrayList<Node> resultList = new ArrayList<>();
        StringBuilder accumulator = new StringBuilder("");

        for (int i = 0; i < input.length(); i++) {
            char curChar = input.charAt(i);
            switch (curChar) {
                case ',' -> {
                    if (parenCount == 0) {
                        resultList.add(createNode(accumulator.toString()));
                        accumulator.setLength(0);
                    } else {
                        accumulator.append(curChar);
                    }
                }
                case '(' -> {
                    parenCount++;
                    accumulator.append(curChar);
                }
                case ')' -> {
                    parenCount--;
                    accumulator.append(curChar);
                }
                default -> {
                    accumulator.append(curChar);
                }
            }
        }
        resultList.add(createNode(accumulator.toString()));
        accumulator.setLength(0);

        return resultList;
    }

    private void printNodeList(ArrayList<Node> list) {
        for (Node node : list) {
            System.out.println(traverseNodes(node));
        }
    }

    public String traverseNodes(Node node) {
        String res = node.getName();
        hyphCounter++;
        char[] chars = new char[hyphCounter];
        Arrays.fill(chars, '-');
        if (node.getChildren() != null && !node.getChildren().isEmpty()) {
            for (Node child : node.getChildren()) {

                res = res + System.lineSeparator() + new String(chars) + traverseNodes(child);
            }
        }
        hyphCounter--;
        return res;
    }

    private ArrayList<Node> sortList(ArrayList<Node> list) {
        return (ArrayList<Node>) list
                .stream()
                .sorted(Comparator.comparing(Node::getName))
                .collect(Collectors.toList());
    }

    public String cleanInputString(String input) {
        input = input.replaceAll("\\s+", "");
        if ((input.charAt(0) == '(') && (')' == input.charAt(input.length() - 1))) {
            input = (input.substring(0, input.length() - 1)).substring(1);
        }
        return input;
    }

    static class Node {
        private String name;
        private List<Node> children;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Node> getChildren() {
            return children;
        }

        public void setChildren(List<Node> children) {
            this.children = children;
        }
    }

    private Node createNode(String text) {
        Node node = new Node();
        if (text.contains("(") || text.contains(")")) {
            int index = text.indexOf("(");
            if (index != -1) {
                node.setName(text.substring(0, index));
                List<Node> list = new ArrayList<>();
                String other = text.substring(index, text.length());
                if (!other.isEmpty()) {
                    list = buildTree(cleanInputString(other.replaceAll("\\s+", "")));
                }
                node.setChildren(list);
            }
        } else {
            node.setName(text);
        }
        return node;
    }
}