package com.wei.sample.data;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description 单向链表
 * @date 2019/3/23
 * @email weishuxin@icourt.cc
 */
public class SingleLinkedList<T> {
    private Node header;
    private int size;


    private class Node {
        private T data;
        private Node next;

        public T getData() {
            return data;
        }

        public Node(T data) {
            this.data = data;
        }
    }

    public Node getHeader() {
        return header;
    }

    /**
     * 添加
     */
    public void addHeader(T data) {
        Node node = new Node(data);
        node.next = header;
        header = node;
        size++;
    }

    /**
     * 删除
     */
    public boolean delete(T data) {
        if (size == 0) {
            return false;
        }
        if (data == null) {
            throw new NullPointerException("数据不能为null");
        }

        Node current = header;

        Node previous = null;
        while (current != null) {
            if (current.data.equals(data)) {
                if (previous != null) {
                    previous.next = current.next;
                }
                size--;
                return true;
            } else {
                previous = current;
                current = current.next;
            }
        }
        return false;
    }

    /**
     * 查询
     */
    public boolean find(T data) {
        if (header != null) {
            Node temp = header;
            while (temp != null) {
                if (temp.data.equals(data)) {
                    return true;
                }
                temp = temp.next;
            }
        }
        return false;
    }


    /**
     * 打印链表
     */
    public void printList() {
        System.out.println("Size = " + size);
        Node temp = header;
        while (temp != null) {
            System.out.print(temp.getData().toString() + " ");
            temp = temp.next;
        }
        System.out.println();
    }

    public void printReverseList(Node head) {
        if (head != null) {
            printReverseList(head.next);
            System.out.print(head.data + " ");
        }
    }


    public void reverseList() {
        if (header == null) {
            return;
        }

        if (header.next == null) {
            return;
        }

        //previous上一个节点
        Node preNode = null;
        //current节点当前节点，并让它指向传进来的对象所在地址（是保存该对象的地址，不是它的next值）
        Node curNode = header;
        //next节点下一个节点
        Node nextNode = null;

        while (curNode != null) {
            //让next节点指向后一个节点所在地址，并改变新地址的值（包括data，next）
            nextNode = curNode.next;
            //将current节点存储的地址（也就是next）的值改为preNode节点所指向的地址（这样就把指向箭头反转了）这儿有个误区
            //注意：是将preNode指向的地址给curNode的next，不是把preNode的next给它。
            curNode.next = preNode;
            //让previous节点指向的地址向后移动一个单位，并改变新地址的值（包括data，next）
            preNode = curNode;
            //让current节点的索引向后移动一个单位，并改变新地址的值包括（data，next）
            curNode = nextNode;
        }
        header = preNode;
    }
}

class Test {
    public static void main(String[] args) {
        SingleLinkedList<String> singleLinkedList = new SingleLinkedList<>();
        for (int i = 0; i < 10; i++) {
            singleLinkedList.addHeader(i + "");
        }
        singleLinkedList.printList();
//        singleLinkedList.delete("1");
        singleLinkedList.reverseList();
        singleLinkedList.printList();
//        System.out.println(singleLinkedList.find("0"));
        System.gc();
    }
}