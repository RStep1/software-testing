package com.mycompany.app.avltree;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.mycompany.app.LoggingTestLifecycleExtension;

@ExtendWith(LoggingTestLifecycleExtension.class)
public class AVLTreeUnitTest {

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
    public void givenSingleKey_whenInsertInEmptyTree_thenRootEquals() {
        final int key = 3;
        AVLTree actualAvlTree = new AVLTree();
        actualAvlTree.insert(key);

        AVLTree expectedAvlTree = createTree(key, null, null);

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void givenSingleKey_whenInsertInLeftSubtreeRoot_thenTreeEquals() {
        final int keyToInsert = 10;
        AVLTree actualAvlTree = createTree(17, null, createTree(20, null, null));
        actualAvlTree.insert(keyToInsert);

        AVLTree expectedAvlTree = createTree(17, createTree(keyToInsert, null, null), createTree(20, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void givenSingleKey_whenInsertInRightSubtreeRoot_thenTreeEquals() {
        final int keyToInsert = 8;
        AVLTree actualAvlTree = createTree(7, createTree(5, null, null), null);
        actualAvlTree.insert(keyToInsert);

        AVLTree expectedAvlTree = createTree(7, createTree(5, null, null), createTree(keyToInsert, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }
    
    @Test
    public void givenLeftLeftCase_whenInsert_thenTreeEquals() {
        final int keyToInsert = 1;
        AVLTree actualAvlTree = createTree(5, createTree(3, createTree(2, null, null), createTree(4, null, null)), createTree(6, null, null));
        actualAvlTree.insert(keyToInsert);

        AVLTree expectedAvlTree = createTree(3, 
            createTree(2, createTree(1, null, null), null),
            createTree(5,
                createTree(4, null, null), createTree(6, null, null)));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void givenRightRightCase_whenInsert_thenTreeEquals() {
        final int keyToInsert = 7;
        AVLTree actualAvlTree = createTree(3, null, createTree(5, null, null));
        actualAvlTree.insert(keyToInsert);

        AVLTree expectedAvlTree = createTree(5, createTree(3, null, null), createTree(7, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    
    @Test
    public void givenLeftRightCase_whenInsert_thenTreeEquals() {
        final int keyToInsert = 4;
        AVLTree actualAvlTree = createTree(5, createTree(3, null, null), null);
        actualAvlTree.insert(keyToInsert);

        AVLTree expectedAvlTree = createTree(4, createTree(3, null, null), createTree(5, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void givenRightLeftCase_whenInsert_thenTreeEquals() {
        final int keyToInsert = 4;
        AVLTree actualAvlTree = createTree(3, null, createTree(5, null, null));
        actualAvlTree.insert(keyToInsert);

        AVLTree expectedAvlTree = createTree(4, createTree(3, null, null), createTree(5, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void givenExistingKey_whenInsert_thenThrowsRuntimeException() {
        final int keyToInsert = 11;
        AVLTree actualAvlTree = createTree(10, null, createTree(11, null, null));

        assertThrows(RuntimeException.class, () -> {
            actualAvlTree.insert(keyToInsert);
        });
    }

    @Test
    public void whenDeleteFromEmptyTree_thenTreeEmpty() {
        final int keyToDelete = 1;
        AVLTree actualAvlTree = new AVLTree();
        actualAvlTree.delete(keyToDelete);

        AVLTree expectedAvlTree = new AVLTree();
        
        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteNonexistingKeyFromNotEmptyTree_thenTreeEquals () {
        AVLTree actualAvlTree = createTree(4, createTree(3, null, null), createTree(8, null, null));
        actualAvlTree.delete(1);

        AVLTree expectedAvlTree = createTree(4, createTree(3, null, null), createTree(8, null, null));
        
        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteLeafWithoutChildren_thenTreeEquals() {
        AVLTree actualAvlTree = createTree(2, createTree(1, null, null), createTree(3, null, null));
        actualAvlTree.delete(3);

        AVLTree expectedAvlTree = createTree(2, createTree(1, null, null), null);

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteLeafWithLeftChild_thenTreeEquals() {
        AVLTree actualAvlTree = createTree(5,
            createTree(1, null, null),
            createTree(8, createTree(6, null, null), null));
        actualAvlTree.delete(8);

        AVLTree expectedAvlTree = createTree(5,
            createTree(1, null, null),
            createTree(6, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteLeafWithRightChild_thenTreeEquals() {
        AVLTree actualAvlTree = createTree(1, null, createTree(3, null, null));
        actualAvlTree.delete(3);

        AVLTree expectedAvlTree = createTree(1, null, null);

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteBothLeafs_thenTreeEquals() {
        AVLTree actualAvlTree = createTree(2, createTree(1, null, null), createTree(3, null, null));
        actualAvlTree.delete(2);

        AVLTree expectedAvlTree = createTree(3, createTree(1, null, null), null);

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteLeftLeftCase_thenTreeEquals() {
        AVLTree actualAvlTree = createTree(5, 
            createTree(3, createTree(2, null, null), null),
            createTree(6, null, null));
        actualAvlTree.delete(6);

        AVLTree expectedAvlTree = createTree(3, 
            createTree(2, null, null),
            createTree(5, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteRightRightCase_thenTreeEquals() {
        AVLTree actualAvlTree = createTree(3,
            createTree(1, null, null),
            createTree(5, null, createTree(7, null, null)));
        actualAvlTree.delete(1);

        AVLTree expectedAvlTree = createTree(5, 
            createTree(3, null, null),
            createTree(7, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteLeftRightCase_thenTreeEquals() {
        AVLTree actualAvlTree = createTree(5,
            createTree(3, null, createTree(4, null, null)),
            createTree(6, null, null));
        actualAvlTree.delete(6);

        AVLTree expectedAvlTree = createTree(4, 
            createTree(3, null, null),
            createTree(5, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenDeleteRightLeftCase_thenTreeEquals() {
        AVLTree actualAvlTree = createTree(3,
            createTree(2, null, null),
            createTree(5, createTree(4, null, null), null));
        actualAvlTree.delete(2);

        AVLTree expectedAvlTree = createTree(4, 
            createTree(3, null, null),
            createTree(5, null, null));

        assertTreeEquals(expectedAvlTree, actualAvlTree);
    }

    @Test
    public void whenFindInEmptyTree_thenNull() {
        AVLTree avlTree = new AVLTree();
        AVLTree.Node actualNode = avlTree.find(1);

        assertNull(actualNode);
    }

    @Test
    public void whenFindExistingRoot_thenSucceed() {
        AVLTree avlTree = createTree(4, createTree(2, null, null), null);
        AVLTree.Node actualNode = avlTree.find(4);

        AVLTree.Node expectedNode = createTree(4, createTree(2, null, null), null).getRoot();

        assertTreeEquals(expectedNode, actualNode);
    }

    @Test
    public void whenFindInLeftSubtree_thenSucceed() {
        AVLTree avlTree = createTree(6,
            createTree(3,
                null,
                createTree(4, null, null)),
            createTree(10, null, null));
        AVLTree.Node actualNode = avlTree.find(3);

        AVLTree.Node expectedNode = createTree(3, null, createTree(4, null, null)).getRoot();

        assertTreeEquals(expectedNode, actualNode);
    }

    @Test
    public void whenFindInRightSubtree_thenSucceed() {
        AVLTree avlTree = createTree(4,
            createTree(2, null, null),
            createTree(6,
                createTree(5, null, null),
                createTree(8, null, null)));
        AVLTree.Node actualNode = avlTree.find(6);

        AVLTree.Node expectedNode = createTree(6,
            createTree(5, null, null),
            createTree(8, null, null))
            .getRoot();

        assertTreeEquals(expectedNode, actualNode);
    }

    @Test
    public void whenFindNonexistingKey_thenGetNull() {
        AVLTree avlTree = createTree(4,
            createTree(2, 
                createTree(1, null, null),
                null),
            createTree(6,
                createTree(5, null, null),
                createTree(8, null, null)));
        AVLTree.Node actualNode = avlTree.find(10);

        assertNull(actualNode);
    }
}
