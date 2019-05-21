package com.wei.code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author shuxin.wei
 * @version v1.0.0
 * @description 排序算法
 * @date 2019-05-21
 * @email weishuxin@icourt.cc
 */
public class SortCode {
    /**
     * 1.直接插入排序
     * <p>
     * 直接插入排序（Straight Insertion Sorting）的基本思想：在要排序的一组数中，假设前面(n-1) [n>=2] 个数已经是排好顺序的，现在要把第n个数插到前面的有序数中，使得这n个数也是排好顺序的。如此反复循环，直到全部排好顺序。
     * <p>
     * 代码实现：
     * 首先设定插入次数，即循环次数，for(int i=1;i<length;i++)，1个数的那次不用插入。
     * 设定插入数和得到已经排好序列的最后一个数的位数。insertNum和j=i-1。
     * 从最后一个数开始向前循环，如果插入数小于当前数，就将当前数向后移动一位。
     * 将当前数放置到空着的位置，即j+1。
     */
    public static class InsertSort {
        public void insertSort(int[] a) {
            System.out.println("直接插入排序前的数组为：" + Arrays.toString(a));
            int len = a.length;//单独把数组长度拿出来，提高效率
            int insertNum;//要插入的数
            for (int i = 1; i < len; i++) {//因为第一次不用，所以从1开始
                insertNum = a[i];
                int j = i - 1;//序列元素个数
                while (j >= 0 && a[j] > insertNum) {//从后往前循环，将大于insertNum的数向后移动
                    a[j + 1] = a[j];//元素向后移动
                    j--;
                }
                a[j + 1] = insertNum;//找到位置，插入当前元素
            }
            System.out.println("直接插入排序后的数组为：" + Arrays.toString(a));
        }
    }

    /**
     * 2.希尔排序
     * <p>
     * 针对直接插入排序的下效率问题，有人对次进行了改进与升级，这就是现在的希尔排序。希尔排序，也称递减增量排序算法，是插入排序的一种更高效的改进版本。希尔排序是非稳定排序算法。
     * 希尔排序是基于插入排序的以下两点性质而提出改进方法的：
     * 插入排序在对几乎已经排好序的数据操作时， 效率高， 即可以达到线性排序的效率
     * 但插入排序一般来说是低效的， 因为插入排序每次只能将数据移动一位
     * 对于直接插入排序问题，数据量巨大时。
     * 将数的个数设为n，取奇数k=n/2，将下标差值为k的数分为一组，构成有序序列。
     * 再取k=k/2 ，将下标差值为k的书分为一组，构成有序序列。
     * 重复第二步，直到k=1执行简单插入排序。
     * <p>
     * 代码实现：
     * 首先确定分的组数。
     * 然后对组中元素进行插入排序。
     * 然后将length/2，重复1,2步，直到length=0为止。
     */
    public static class SheelSort {
        public void sheelSort(int[] a) {
            System.out.println("希尔排序前的数组为：" + Arrays.toString(a));
            int len = a.length;//单独把数组长度拿出来，提高效率
            while (len != 0) {
                len = len / 2;
                for (int i = 0; i < len; i++) {//分组
                    for (int j = i + len; j < a.length; j += len) {//元素从第二个开始
                        int k = j - len;//k为有序序列最后一位的位数
                        int temp = a[j];//要插入的元素
                    /*for(;k>=0&&temp<a[k];k-=len){
                        a[k+len]=a[k];
                    }*/
                        while (k >= 0 && temp < a[k]) {//从后往前遍历
                            a[k + len] = a[k];
                            k -= len;//向后移动len位
                        }
                        a[k + len] = temp;
                    }
                }
            }
            System.out.println("希尔排序后的数组为：" + Arrays.toString(a));
        }
    }

    /**
     * 3.简单选择排序
     * 常用于取序列中最大最小的几个数时。
     * (如果每次比较都交换，那么就是交换排序；如果每次比较完一个循环再交换，就是简单选择排序。)
     * 遍历整个序列，将最小的数放在最前面。
     * 遍历剩下的序列，将最小的数放在最前面。
     * 重复第二步，直到只剩下一个数。
     * <p>
     * 代码实现：
     * 首先确定循环次数，并且记住当前数字和当前位置。
     * 将当前位置后面所有的数与当前数字进行对比，小数赋值给key，并记住小数的位置。
     * 比对完成后，将最小的值与第一个数的值交换。
     * 重复2、3步。
     */
    public static class SelectSort {
        public void selectSort(int[] a) {
            System.out.println("简单选择排序前的数组为：" + Arrays.toString(a));
            int len = a.length;
            for (int i = 0; i < len; i++) {//循环次数
                int value = a[i];
                int position = i;
                for (int j = i + 1; j < len; j++) {//找到最小的值和位置
                    if (a[j] < value) {
                        value = a[j];
                        position = j;
                    }
                }
                a[position] = a[i];//进行交换
                a[i] = value;
            }
            System.out.println("简单选择排序后的数组为：" + Arrays.toString(a));
        }
    }

    /**
     * 4.堆排序
     * 对简单选择排序的优化。
     * 将序列构建成大顶堆。
     * 将根节点与最后一个节点交换，然后断开最后一个节点。
     * 重复第一、二步，直到所有节点断开。
     */
    public static class HeapSort {
        public void heapSort(int[] a) {
            System.out.println("堆排序前的数组为：" + Arrays.toString(a));
            int len = a.length;
            //循环建堆
            for (int i = 0; i < len - 1; i++) {
                //建堆
                buildMaxHeap(a, len - 1 - i);
                //交换堆顶和最后一个元素
                swap(a, 0, len - 1 - i);
            }
            System.out.println("堆排序后的数组为：" + Arrays.toString(a));
        }

        //交换方法
        private void swap(int[] data, int i, int j) {
            int tmp = data[i];
            data[i] = data[j];
            data[j] = tmp;
        }

        //对data数组从0到lastIndex建大顶堆
        private void buildMaxHeap(int[] data, int lastIndex) {
            //从lastIndex处节点（最后一个节点）的父节点开始
            for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
                //k保存正在判断的节点
                int k = i;
                //如果当前k节点的子节点存在
                while (k * 2 + 1 <= lastIndex) {
                    //k节点的左子节点的索引
                    int biggerIndex = 2 * k + 1;
                    //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                    if (biggerIndex < lastIndex) {
                        //若果右子节点的值较大
                        if (data[biggerIndex] < data[biggerIndex + 1]) {
                            //biggerIndex总是记录较大子节点的索引
                            biggerIndex++;
                        }
                    }
                    //如果k节点的值小于其较大的子节点的值
                    if (data[k] < data[biggerIndex]) {
                        //交换他们
                        swap(data, k, biggerIndex);
                        //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                        k = biggerIndex;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    /**
     * 5.冒泡排序
     * 很简单，用到的很少，据了解，面试的时候问的比较多！
     * 将序列中所有元素两两比较，将最大的放在最后面。
     * 将剩余序列中所有元素两两比较，将最大的放在最后面。
     * 重复第二步，直到只剩下一个数。
     * <p>
     * 代码实现：
     * 设置循环次数。
     * 设置开始比较的位数，和结束的位数。
     * 两两比较，将最小的放到前面去。
     * 重复2、3步，直到循环次数完毕。
     */
    public static class BubbleSort {
        public void bubbleSort(int[] a) {
            System.out.println("冒泡排序前的数组为：" + Arrays.toString(a));
            int len = a.length;
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < len - i - 1; j++) {//注意第二重循环的条件
                    if (a[j] > a[j + 1]) {
                        int temp = a[j];
                        a[j] = a[j + 1];
                        a[j + 1] = temp;
                    }
                }
            }
            System.out.println("冒泡排序后的数组为：" + Arrays.toString(a));
        }
    }

    /**
     * 6.快速排序
     * 要求时间最快时。
     * 选择第一个数为p，小于p的数放在左边，大于p的数放在右边。
     * 递归的将p左边和右边的数都按照第一步进行，直到不能递归。
     */
    public static class QuickSort {
        public void quickSort(int[] a) {
            System.out.println("快速排序前的数组为：" + Arrays.toString(a));
            quickSort(a, 0, a.length - 1);
            System.out.println("快速排序后的数组为：" + Arrays.toString(a));
        }

        public void quickSort(int[] a, int start, int end) {

            if (start < end) {
                int baseNum = a[start];//选基准值
                int midNum;//记录中间值
                int i = start;
                int j = end;
                do {
                    while ((a[i] < baseNum) && i < end) {
                        i++;
                    }
                    while ((a[j] > baseNum) && j > start) {
                        j--;
                    }
                    if (i <= j) {
                        midNum = a[i];
                        a[i] = a[j];
                        a[j] = midNum;
                        i++;
                        j--;
                    }

                } while (i <= j);
                if (start < j) {
                    quickSort(a, start, j);
                }
                if (end > i) {
                    quickSort(a, i, end);
                }
            }
        }
    }

    /**
     * 7.归并排序
     * 速度仅次于快速排序，内存少的时候使用，可以进行并行计算的时候使用。
     * 选择相邻两个数组成一个有序序列。
     * 选择相邻的两个有序序列组成一个有序序列。
     * 重复第二步，直到全部组成一个有序序列。
     */
    public static class MergeSort {
        public void mergeSort(int[] a) {
            System.out.println("归并排序前的数组为：" + Arrays.toString(a));
            ;
            System.out.println("归并排序后的数组为：" + Arrays.toString(mergeSort(a, 0, 9)));
        }

        public static int[] mergeSort(int[] a,int low,int high){
            int mid = (low+high)/2;
            if(low<high){
                mergeSort(a,low,mid);
                mergeSort(a,mid+1,high);
                //左右归并
                merge(a,low,mid,high);
            }
            return a;
        }

        public static void merge(int[] a, int low, int mid, int high) {
            int[] temp = new int[high-low+1];
            int i= low;
            int j = mid+1;
            int k=0;
            // 把较小的数先移到新数组中
            while(i<=mid && j<=high){
                if(a[i]<a[j]){
                    temp[k++] = a[i++];
                }else{
                    temp[k++] = a[j++];
                }
            }
            // 把左边剩余的数移入数组
            while(i<=mid){
                temp[k++] = a[i++];
            }
            // 把右边边剩余的数移入数组
            while(j<=high){
                temp[k++] = a[j++];
            }
            // 把新数组中的数覆盖nums数组
            for(int x=0;x<temp.length;x++){
                a[x+low] = temp[x];
            }
        }
    }

    /**
     * 8.基数排序
     * 用于大量数，很长的数进行排序时。
     * 将所有的数的个位数取出，按照个位数进行排序，构成一个序列。
     * 将新构成的所有的数的十位数取出，按照十位数进行排序，构成一个序列。
     */

    public static class BaseSort {
        public void baseSort(int[] a) {
            System.out.println("基数排序前的数组为：" + Arrays.toString(a));
            //首先确定排序的趟数;
            int max = a[0];
            for (int i = 1; i < a.length; i++) {
                if (a[i] > max) {
                    max = a[i];
                }
            }
            int time = 0;
            //判断位数;
            while (max > 0) {
                max /= 10;
                time++;
            }
            //建立10个队列;
            List<ArrayList<Integer>> queue = new ArrayList<ArrayList<Integer>>();
            for (int i = 0; i < 10; i++) {
                ArrayList<Integer> queue1 = new ArrayList<Integer>();
                queue.add(queue1);
            }
            //进行time次分配和收集;
            for (int i = 0; i < time; i++) {
                //分配数组元素;
                for (int j = 0; j < a.length; j++) {
                    //得到数字的第time+1位数;
                    int x = a[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                    ArrayList<Integer> queue2 = queue.get(x);
                    queue2.add(a[j]);
                    queue.set(x, queue2);
                }
                int count = 0;//元素计数器;
                //收集队列元素;
                for (int k = 0; k < 10; k++) {
                    while (queue.get(k).size() > 0) {
                        ArrayList<Integer> queue3 = queue.get(k);
                        a[count] = queue3.get(0);
                        queue3.remove(0);
                        count++;
                    }
                }
            }
            System.out.println("基数排序后的数组为：" + Arrays.toString(a));
        }
    }

    public static void main(String[] args) {
        //排序方法测试
        new InsertSort().insertSort(getInts());
        new SheelSort().sheelSort(getInts());
        new SelectSort().selectSort(getInts());
        new HeapSort().heapSort(getInts());
        new BubbleSort().bubbleSort(getInts());
        new QuickSort().quickSort(getInts());
        new MergeSort().mergeSort(getInts());
        new BaseSort().baseSort(getInts());
    }

    private static int[] getInts() {
        int[] a = new int[10];
        for (int i = 1; i < a.length; i++) {
            a[i] = new Random().nextInt(100);
        }
        return a;
    }

}
