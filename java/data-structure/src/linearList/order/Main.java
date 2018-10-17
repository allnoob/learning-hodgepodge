package linearList.order;

public class Main {

    public static void main(String[] args) {

        OrderLinearList linearList = new OrderLinearList();

        // init
        System.out.println(linearList.init(5));

        // delete
        System.out.println(linearList.delete(3));
        System.out.println(linearList.delete(1));

        // insert
        System.out.println(linearList.insert(66, 2));

        // get
        System.out.println(linearList.get(4));

        // getLength
        System.out.println(linearList.getLength());

    }

}
