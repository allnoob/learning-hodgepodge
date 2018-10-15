package linearList.chain;

import java.util.StringJoiner;

public class LinkedList<T> {

    private Node head;

    private int length;

    public int getLength() {
        return length;
    }

    public void init() {
        this.head = new Node<>(null, null);
        this.length = 0;
    }

    public void init(Node head) {
        this.head = head;
        this.length = computeLength(head);
    }

    public void insert(int index, T data) {
        Node next = this.head.getNext();
        if (next == null) {
            System.out.println("链表为空");
            return;
        }
        if (index <= 0 || index > this.length) {
            System.out.println("插入的位置不对");
            return;
        }
        Node<String> node = new Node<>((String) data);
        if (index == 1) {
            node.setNext(this.head.getNext());
            this.head.setNext(node);
        } else {
            node.setNext(get(index - 1).getNext());
            get(index - 1).setNext(node);
        }
        this.length++;
    }

    public void delete(int index) {
        if (this.head.getNext() == null) {
            System.out.println("链表为空");
            return;
        }
        if (index <= 0 || index > this.length) {
            System.out.println("删除位置不对");
            return;
        }
        if (index == 1) {
            this.head.setNext(get(index).getNext());
        } else {
            Node node = get(index - 1);
            node.setNext(node.getNext().getNext());
        }
        this.length--;
    }

    public Node get(int index) {
        Node next = this.head.getNext();
        if (next == null) {
            System.out.println("链表为空");
            return null;
        }
        if (index <= 0 || index > this.length) {
            System.out.println("获取位置不对");
            return null;
        }
        for (int i = 1; i <= this.length; i++) {
            if (index == i) {
                return next;
            }
            next = next.getNext();
        }
        return null;
    }

    public int locateLinkedList(T data) {
        Node next = head.getNext();
        if (next == null) {
            System.out.println("链表为空");
            return 0;
        }
        for (int i = 1; i <= this.length; i++) {
            if (next.getData() == data) {
                return i;
            }
            next = next.getNext();
        }
        System.out.println("找不到相关的数据");
        return 0;
    }

    public void print() {
        Node next = this.head.getNext();
        StringJoiner joiner = new StringJoiner(" ").add("LinkedList (print): ");
        if (next.getNext() == null) {
            System.out.println("链表为空");
            return;
        }
        for (int i = 1; i <= this.length; i++) {
            joiner.add(next.getData().toString());
            next = next.getNext();
        }
        System.out.println(joiner.toString());
    }

    private int computeLength(Node head) {
        int count = 1;
        Node next = head.getNext();
        if (next.getNext() == null) {
            System.out.println("链表为空");
            return 0;
        }
        while (true) {
            next = next.getNext();
            if (next == null) {
                break;
            }
            count++;
        }
        return count;
    }

}
