package org.example;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * JAVA 并发 锁的实现
 */
public class Lock {

    /**
     * volatile 不具有原子性和有序性
     */
    private static volatile int count = 0;

    /**
     * volatile 具有可见性 （变量在其他的线程中把值改变，那么其他的线程中在使用的时候，回去查找最新的值）
     */
    public static boolean open = false;

    /**
     * Atomic 具有原子性、可见性、有序性
     */
    private static final AtomicInteger countAtomic = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        // 读写锁测试
        //readWriteLockTest();

        /**
         * volatile 关键字测试
         */
        volatileTest();
    }

    private static void volatileTest() throws InterruptedException {
        int threadCount = 2;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    count++;
                    countAtomic.getAndAdd(1);
                }
            });
            threads[i] = thread;
        }
        for (int i = 0; i < threadCount; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threadCount; i++) {
            threads[i].join();
        }
        System.out.println(threadCount + "个线程，执行 volatile 修饰的 int 的并发执行的结果：" + count + " , 结果错误");
        System.out.println(threadCount + "个线程，执行 AtomicInteger类 的并发执行的结果：" + countAtomic.get()+ " , 结果正确");

        /*****************************测试 volatile 的 可见性*********************************/

        Thread thread = new Thread(() -> {
            System.out.println("等待开门");
            while (!open) {
                System.out.println("不允许开门");
            }
            System.out.println("开门成功！");
        });
        thread.start();
        //Thread.sleep(1000);
        System.out.println("下发开门");
        open = true;
        thread.join();


    }

    /**
     * 测试读写锁
     * ReentrantReadWriteLock
     *
     * ReentrantReadWriteLock是ReadWriteLock的一个实现。上面也讲到了ReadWriteLock主要有两个方法：
     *
     * Read Lock - 如果没有线程获得写锁，那么可以多个线程获得读锁。
     *
     * Write Lock - 如果没有其他的线程获得读锁和写锁，那么只有一个线程能够获得写锁。
     */
    private static void readWriteLockTest() throws InterruptedException {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        // 获取到读锁
        java.util.concurrent.locks.Lock readLock = readWriteLock.readLock();
        java.util.concurrent.locks.Lock writeLock = readWriteLock.writeLock();
        new Thread(() -> {
            readLock.lock();
            System.out.println("获取读锁成功！不释放锁的话，那么将不能获取写锁");
            readLock.unlock();
        }).start();

        new Thread(() -> {
            readLock.lock();
            System.out.println("获取多个读锁成功！");
            readLock.unlock();
        }).start();
        Thread.sleep(1000);
        System.out.println("释放读锁成功！读锁加锁了几次，就要释放几次读锁");
        writeLock.lock();
        System.out.println("获取写锁成功！写锁没释放，不能获取读锁");
        writeLock.lock();
        System.out.println("获取多个写锁成功！");
    }

}
