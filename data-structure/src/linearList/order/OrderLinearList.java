package linearList.order;

import java.util.Random;

public class OrderLinearList {

    private static final int RANDOM_MAX_NUMBER = 100;

    private int[] list;

    private int length;

    public String init(int length) {
        Random random = new Random();
        this.list = new int[length];
        this.length = length;
        for (int i = 0; i < length; i++) {
            this.list[i] = random.nextInt(RANDOM_MAX_NUMBER);
        }
        String listString = createListString();
        return "OrderLinearList (init): " + listString;
    }

    public String get(int index) {
        if (index > this.length || index <= 0) {
            return "溢出啦！";
        } else {
            return "OrderLinearList (get index " + index + "): " + this.list[index - 1];
        }
    }

    public String getLength() {
        return "OrderLinearList (length): " + this.length;
    }

    public String insert(int number, int index) {
        if (this.length == 0) {
            return "不够位置，添加不了元素";
        }
        if (index > this.length || index <= 0) {
            return "添加位置已溢出";
        }
        for (int i = this.length - 1; i >= index - 1; i--) {
            this.list[i + 1] = this.list[i];
        }
        this.list[index - 1] = number;
        this.length++;
        String listString = createListString();
        return "OrderLinearList (insert): " + listString;
    }

    public String delete(int index) {
        if (this.length == 0) {
            return "线性表为空，不得操作";
        }
        if (index < 0 || index > this.length) {
            return "删除位置有误，删除失败";
        }
        for (int i = index - 1; i < this.length - 1; i++) {
            this.list[i] = this.list[i + 1];
        }
        this.length--;
        String listString = createListString();
        return "OrderLinearList (delete): " + listString;
    }

    private String createListString() {
        String listString = "";
        for (int i = 0; i < this.length; i++) {
            listString = listString + this.list[i] + "  ";
        }
        return listString;
    }

}
