package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 *  JVM 参数设置
 */
public class JVMParamSet {

    private static int count;

    public static void main(String[] args) {
        // 测试虚拟机参数，影响的内存结构
        //testVmParam();

        // 测试内存溢出
        //testMemoryOverflow();

        // 测试栈内存溢出
        //testStackOverflow();

    }

    /**
     * 测试栈内存溢出
     */
    public static void testStackOverflow() {
        // -Xss5m 设置栈调用的深度
        // 例如这次的递归调用，因为没有出口，所有造成死循环、现在设置 Xss5m 深度为 5 M ，那么将会调用的更深，时间更久。设置 为 1M 的时候 可能就会递归 10次就失败了
        try {
            count++;
            testStackOverflow();
        }catch (Throwable e) {
            System.out.printf("共循环 %s 次后，发生 栈内存溢出！", count);
            e.printStackTrace();
        }
    }

    /**
     * 测试内存溢出
     */
    private static void testMemoryOverflow() {
        // -Xms20m -Xmx20m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC -XX:NewRatio=3 -XX:+HeapDumpOnOutOfMemoryError
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            // 新生代 产生 2M 的内存， 最大内存是20 M，循环10次左右之后，内存不够用了， 就会发生内存溢出
            list.add(new byte[2 * 1024 * 1024]);
        }
    }

    private static void testVmParam() {
        System.out.print("最大内存：");
        System.out.println(Runtime.getRuntime().maxMemory() / 1024 / 1024 + " M");
        System.out.print("可用内存：");
        System.out.println(Runtime.getRuntime().freeMemory() / 1024 / 1024 + " M");
        System.out.print("已使用内存：");
        System.out.println(Runtime.getRuntime().totalMemory() / 1024 / 1024 + " M");
    }

}
