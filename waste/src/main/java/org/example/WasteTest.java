package org.example;

/**
 * Hello world!
 */
public class WasteTest extends Object {

    public static void main(String[] args) throws InterruptedException {
        // wasteTest: 放入栈中指针指向对内存
        // new WasteTest()： 放入堆内存中
        WasteTest wasteTest = new WasteTest();

        // 使栈中指向为空，放弃引用
        wasteTest = null;

        // 呼叫java垃圾回收器 ， 进行回收
        System.gc();

        Thread.sleep(5000);
    }

    @Override
    protected void finalize() {
        System.out.println("对象被回收....");
    }
}
