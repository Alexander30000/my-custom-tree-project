package main.java.org.example;

public class RedBlackTree {

    private static final Node EMPTY = new Node(0);

    // static block to initialize EMPTY Node object
    static {
        EMPTY.left = EMPTY;
        EMPTY.right = EMPTY;
    }

    // fields to store linked objects
    protected Node current;
    private Node parent;
    private Node grand;
    private Node great;
    private Node header;

    public RedBlackTree() {
        header = new Node(Integer.MIN_VALUE);
        header.left = EMPTY;
        header.right = EMPTY;
    }


    /**
     * return status of tree
     *
     * @return boolean
     */
    public boolean isEmpty() {

        boolean left = header.left == EMPTY;
        boolean right = header.right == EMPTY;
        return left && right;
    }

    /**
     * method delete all nodes
     */
    public void clear() {
        header.right = EMPTY;
    }

    /**
     * method insert new node in the tree
     *
     * @param item
     */
    public void insert(int item) {
        current = grand = parent = header;
        EMPTY.element = item;
        while (current.element != item) {
            great = grand;
            grand = parent;
            parent = current;
            current = item > current.element ? current.right : current.left;

            if (current.left.color == Color.RED && current.right.color == Color.RED) {
                reorient(item);
            }
        }

        if (current != EMPTY) {
            return;
        }

        current = new Node(item, EMPTY, EMPTY);

        if (item < parent.element) {
            parent.left = current;
        } else {
            parent.right = current;
        }
        reorient(item);
    }

    /**
     * method reorient elements in the tree when we insert node
     *
     * @param item
     */
    protected void reorient(int item) {
        current.color = Color.RED;
        current.left.color = Color.BLACK;
        current.right.color = Color.BLACK;

        if (parent.color == Color.RED) {
            grand.color = Color.RED;
            if (item < grand.element != item < parent.element) {
                parent = rotate(item, grand);
            }
            current = rotate(item, great);
            current.color = Color.BLACK;
        }

        header.right.color = Color.BLACK;
    }

    /**
     * method rotate elements in the tree
     *
     * @param item
     * @param parent
     * @return Node
     */
    private Node rotate(int item, Node parent) {
        if (item < parent.element) {
            Node node = parent.left;
            Node resultNode = item < node.element ? rotateWithLeftNode(node) : rotateWithRightNode(node);
            parent.left = resultNode;
            return parent.left;
        } else {
            Node node = parent.right;
            Node resultNode = item < node.element ? rotateWithLeftNode(node) : rotateWithRightNode(node);
            parent.right = resultNode;
            return parent.right;
        }
    }

    /**
     * method rotate elements with left node
     *
     * @param element
     * @return Node
     */
    private Node rotateWithLeftNode(Node element) {
        Node left = element.left;
        element.left = left.right;
        left.right = element;
        return left;
    }

    /**
     * method rotate elements with right node
     *
     * @param element
     * @return Node
     */
    private Node rotateWithRightNode(Node element) {
        Node right = element.right;
        element.right = right.left;
        right.left = element;
        return right;
    }

    /**
     * static enum Color to store colors of nodes
     */
    public static enum Color {
        BLACK,
        RED
    }

    /**
     * inner class Node
     */
    public static class Node {
        private int element;
        private Node left;
        private Node right;
        private Color color;

        public Node(int element) {
            this(element, null, null);
        }

        public Node(int element, Node left, Node right) {
            this.left = left;
            this.right = right;
            this.element = element;
            this.color = Color.BLACK;
        }
    }
}
