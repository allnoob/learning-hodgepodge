package linearList.chain;

public class Main {

    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();

        linkedList.init(createLinkedList());
        linkedList.print();

        // insert linkedList
        linkedList.insert(2, "阳江");
        linkedList.print();

        // delete linkedList
        linkedList.delete(3);
        linkedList.print();

        // getNodeByIndex
        Node node = linkedList.get(1);
        if (node != null) {
            System.out.println(node.getData());
        } else {
            System.out.println("无数据");
        }

        // getIndexByNodeData
        System.out.println(linkedList.locateLinkedList("阳江"));

        // getLinkedList
        System.out.println(linkedList.getLength());
    }

    private static Node createLinkedList() {
        Node<String> head = new Node<>(null);
        Node<String> node1 = new Node<>("深圳");
        Node<String> node2 = new Node<>("广州");
        Node<String> node3 = new Node<>("梅州");
        Node<String> node4 = new Node<>("中山");
        head.setNext(node1);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        return head;
    }

}
