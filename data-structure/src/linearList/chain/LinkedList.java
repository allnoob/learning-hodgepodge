package linearList.chain;

public class LinkedList {

    private Node head;

    public String init() {
        Node<String> node = new Node<>(null, null);
        return "LinkedList (init): " + node.getData();
    }

    public String insert() {
        return null;
    }

    public String delete() {
        return null;
    }

    public String get(Node head) {
        Node next = head.getNext();
        return null;
    }

    public String getLength(Node head) {
        int count = 0;
        Node next = head.getNext();
        if (next.getNext() == null) {
            return "链表为空";
        }
        while (true) {
            if (next.getNext() == null) {
                break;
            }
            count++;
        }
        return "LinkedList (length): " + count;
    }

}
