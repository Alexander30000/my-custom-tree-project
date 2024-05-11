
import java.io.Serializable;
import java.util.*;

/**
 * CustomTree class extends AbstractList<String> work partially as a List
 */
public class CustomTree extends AbstractList<String> implements Serializable, Cloneable {

    /**
     * constant fields using in our class
     */
    private static final String LEFT = "LEFT_CHILD";
    private static final String RIGHT = "RIGHT_CHILD";

    /**
     * root field to store root Entry
     */
    Entry<String> root = new Entry<>("Root");

    /**
     * nested Entry<T> class implements Serializable
     * to store information in our tree
     *
     * @param <T>
     */
    static class Entry<T> implements Serializable {

        /**
         * name of Entry
         */
        String elementName;

        /**
         * boolean field to store information about child Entry's status
         */
        boolean availableToAddLeftChildren;
        boolean availableToAddRightChildren;

        /**
         * fields to store linked Entry<T> objects
         */
        Entry<T> parent, leftChild, rightChild;

        /**
         * Entry object's constructor
         *
         * @param elementName
         */
        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        /**
         * method check and reset children Entry objects
         */
        public void checkChildren() {
            if (leftChild != null)
                availableToAddLeftChildren = false;
            if (rightChild != null)
                availableToAddRightChildren = false;
        }

        /**
         * method check child Entry object's status
         *
         * @return boolean
         */
        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }

    /**
     * method remove object from the tree
     *
     * @param o element to be removed from this list, if present
     * @return boolean
     */
    @Override
    public boolean remove(Object o) {

        // check that object is String
        if (!o.getClass().getSimpleName().equals("String")) {
            throw new UnsupportedOperationException("You can add only String objects.");
        }

        // create Queue of Entry<String> objects
        Queue<Entry<String>> nodes = new LinkedList<>(Collections.singletonList(root));

        // use loop to find objects
        while (!nodes.isEmpty()) {

            // get Entry object from queue
            Entry<String> node = nodes.poll();

            // remove object and reset tree
            if (node.elementName.equals(o)) {
                if (node.parent.leftChild == node) {
                    node.parent.leftChild = null;
                    node.parent.availableToAddLeftChildren = true;
                }

                if (node.parent.rightChild == node) {
                    node.parent.rightChild = null;
                    node.parent.availableToAddRightChildren = true;
                }
                return true;
            }

            // add child objects to queue
            if (node.leftChild != null) {
                nodes.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                nodes.offer(node.rightChild);
            }
        }
        return false;
    }

    /**
     * method size() return size of our tree
     *
     * @return int
     */
    @Override
    public int size() {

        // initialize counter
        int count = -1;

        // create Queue of Entry<String> objects
        Queue<Entry<String>> nodes = new LinkedList<>(Collections.singletonList(root));

        // use loop to count all Entry objects
        while (!nodes.isEmpty()) {

            Entry<String> node = nodes.poll();
            count++;

            // add child objects to queue
            if (node.leftChild != null) {
                nodes.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                nodes.offer(node.rightChild);
            }
        }
        return count;
    }

    /**
     * method add object to the tree
     *
     * @param s element whose presence in this collection is to be ensured
     * @return boolean
     */
    @Override
    public boolean add(String s) {

        // create Queue of Entry<String> objects
        Queue<Entry<String>> nodes = new LinkedList<>(Collections.singletonList(root));

        // use loop to get free Entry object
        while (!nodes.isEmpty()) {

            Entry<String> currentNode = nodes.poll();

            if (currentNode.isAvailableToAddChildren()) {
                if (currentNode.availableToAddLeftChildren) {
                    return appendChild(s, currentNode, LEFT);
                }
                if (currentNode.availableToAddRightChildren) {
                    return appendChild(s, currentNode, RIGHT);
                }
            } else {
                if (currentNode.leftChild != null) {
                    nodes.offer(currentNode.leftChild);
                }
                if (currentNode.rightChild != null) {
                    nodes.offer(currentNode.rightChild);
                }
            }
        }
        return false;
    }

    /**
     * method append child Entry object to argument Entry object
     *
     * @param s
     * @param node
     * @param child
     * @return boolean
     */
    private boolean appendChild(String s, Entry<String> node, final String child) {

        if(child == LEFT){
            node.leftChild = new Entry<>(s);
            node.leftChild.parent = node;
            node.checkChildren();
            return true;
        } else if(child == RIGHT){
            node.rightChild = new Entry<>(s);
            node.rightChild.parent = node;
            node.checkChildren();
            return true;
        }
        return false;
    }

    /**
     * method return name of element's parents Entry object
     *
     * @param elementName
     * @return String
     */
    private String getParent(String elementName) {

        // create Queue of Entry<String> objects
        Queue<Entry<String>> nodes = new LinkedList<>(Collections.singletonList(root));

        // use loop to find Entry
        while (!nodes.isEmpty()) {

            Entry<String> node = nodes.poll();

            if (node.elementName.equals(elementName)) {
                return node.parent.elementName;
            }

            // add child objects to queue
            if (node.leftChild != null) {
                nodes.offer(node.leftChild);
            }
            if (node.rightChild != null) {
                nodes.offer(node.rightChild);
            }
        }
        return null;
    }

     // initialise AbstractList's methods, but don't use them
    @Override
    public String get(int index) {
        throw new UnsupportedOperationException("Operation not supported by this class!");
    }

    @Override
    public String set(int index, String element) {
        throw new UnsupportedOperationException("Operation not supported by this class!");
    }

    @Override
    public void add(int index, String element) {
        throw new UnsupportedOperationException("Operation not supported by this class!");
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException("Operation not supported by this class!");
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Operation not supported by this class!");
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Operation not supported by this class!");
    }

    @Override
    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException("Operation not supported by this class!");
    }

    /**
     * method return String presentation of Tree
     *
     * @return String
     */
    @Override
    public String toString() {

        // create Queue of Entry<String> objects
        Queue<Entry<String>> nodes = new LinkedList<>(Collections.singletonList(root));

        // // create StringBuilder object
        StringBuilder stringBuilder = new StringBuilder();

        // use loop to get names of elements and append them to StringBuilder object
        while (!nodes.isEmpty()) {

            Entry<String> entry = nodes.poll();

            stringBuilder.append(entry.elementName).append(" -> ");

            // add child objects to queue
            if (entry.leftChild != null) {
                nodes.offer(entry.leftChild);
            }
            if (entry.rightChild != null) {
                nodes.offer(entry.rightChild);
            }
        }
        return stringBuilder.toString();
    }
}
