package com.thyberg.kata.chop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class BinaryChop {
    private static final List<Integer> INITIAL_VALUE = new ArrayList<>(
            Arrays.asList(1,3,5,7,9,10,11,13,15,19,30,40,56,76,89,100));

    public static void main(String [] args) {
        System.out.println(new IterChop().chop(INITIAL_VALUE, 76));
    }
}

interface Chop {
    Integer chop(List<Integer> values, Integer searched_value);
}

class IterChop implements Chop {

    @Override
    public Integer chop(List<Integer> values, Integer searched_value) {
        int index = -1;
        int i = 0;
        int j = values.size() - 1;
        while (i <= j) {
            int mid = (i + j) / 2;
            int mid_val = values.get(mid);
            if (mid_val < searched_value) {
                i = mid + 1;
            } else if (mid_val > searched_value) {
                j = mid - 1;
            } else if (mid_val == searched_value) {
                return mid;
            }

        }
        return index;
    }
}

class RecurseChop implements Chop {

    @Override
    public Integer chop(List<Integer> values, Integer searched_value) {
        if (values.isEmpty()) {
            return -1;
        }
        return chopRecurse(values, searched_value, 0, values.size() - 1);
    }

    private Integer chopRecurse(List<Integer> values, int searched_value, int low, int high) {
        int mid = (low + high) / 2;
        if (values.get(mid) == searched_value) {
            return mid;
        }
        if (low > high) {
            return -1;
        }
        int mid_val = values.get(mid);
        if (mid_val < searched_value) {
            return chopRecurse(values, searched_value, mid + 1, high);
        } else {
            return chopRecurse(values, searched_value, low, mid - 1);
        }

    }
}

class Node {
    int value;
    int index;
    Node left;
    Node right;

    Node(int value, int index) {
        this.value = value;
        this.index = index;
        right = null;
        left = null;
    }
}

class BinaryTree {
    Node root;

    public BinaryTree(List<Integer> values) {
        for (int i=0; i < values.size(); i++) {
            add(values.get(i), i);
        }
    }

    public void add(int value, int index) {
        root = addRecursive(root, value, index);
    }

    private Node addRecursive(Node current, int value, int index) {
        if (current == null) {
            return new Node(value, index);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value, index);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value, index);
        } else {
            // value already exists
            return current;
        }

        return current;
    }

    public int getNodeIndex(int value) {
        return getNodeIndexRecursive(root, value);
    }

    private int getNodeIndexRecursive(Node current, int value) {
        if (current == null) {
            return -1;
        }
        if (value == current.value) {
            return current.index;
        }
        return value < current.value
                ? getNodeIndexRecursive(current.left, value)
                : getNodeIndexRecursive(current.right, value);
    }
}

class TreeChop implements Chop {

    @Override
    public Integer chop(List<Integer> values, Integer searched_value) {
        return new BinaryTree(values).getNodeIndex(searched_value);
    }
}

class FunctionalChop implements Chop {

    @Override
    public Integer chop(List<Integer> values, Integer searched_value) {
        return Collections.binarySearch(values, searched_value) >= 0 ?
               Collections.binarySearch(values, searched_value) :
               -1;

    }
}