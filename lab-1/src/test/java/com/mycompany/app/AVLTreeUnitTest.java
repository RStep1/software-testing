package com.mycompany.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.mycompany.app.avltree.AVLTree;

public class AVLTreeUnitTest {

    private static AVLTree expectedAvlTree;

    @BeforeAll
    public static void setUpTree() {
        expectedAvlTree = new AVLTree();
    }

    private AVLTree createTree(int key, AVLTree leftTree, AVLTree rightTree) {
        AVLTree tree = new AVLTree();
    
        AVLTree.Node rootNode = tree.new Node(key);
        rootNode.setLeft(leftTree != null ? leftTree.getRoot() : null);
        rootNode.setRight(rightTree != null ? rightTree.getRoot() : null);
    
        int leftHeight = leftTree != null ? leftTree.getRoot().getHeight() : -1;
        int rightHeight = rightTree != null ? rightTree.getRoot().getHeight() : -1;
        rootNode.setHeight(1 + Math.max(leftHeight, rightHeight));
    
        tree.setRoot(rootNode);
    
        return tree;
    }

    private void assertTreeEquals(AVLTree expected, AVLTree actual) {
        assertTreeEquals(expected.getRoot(), actual.getRoot());
    }

    private void assertTreeEquals(AVLTree.Node expected, AVLTree.Node actual) {
        if (expected == null && actual == null) {
            return;
        }
        assertNotNull(expected, "Expected node cannot be null");
        assertNotNull(actual, "Actual node cannot be null");
        assertEquals(expected.getKey(), actual.getKey(), "Keys do not match");
        assertEquals(expected.getHeight(), actual.getHeight(), "Node height do not match");

        assertTreeEquals(expected.getLeft(), actual.getLeft());
        assertTreeEquals(expected.getRight(), actual.getRight());
    }

    @Test
    public void givenNumbersSequentially_whenInsert() {
        AVLTree actualAvlTree = new AVLTree();
        actualAvlTree.insert(3);
        actualAvlTree.insert(1);
        actualAvlTree.insert(2);
        AVLTree expectedAvlTree = createTree(2, createTree(1, null, null), createTree(3, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }
}
