package com.spring.cloud.product.main;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@SpringBootTest
class ProductApplicationTests {

    public static void main(String[] args) {
        useSemaphore();
    }

    @Test
    public static void useSemaphore() {
        // 线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        // 信号量，这里采用3个许可信号，线程间采用公平机制
        final Semaphore semaphore = new Semaphore(3, true);
        // 循环10次
        for (int i = 0; i < 10; i++) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        // 获取许可信号，获取不到的线程会被阻塞，等待唤起
                        semaphore.acquire();
                        System.out.println("线程：" + Thread.currentThread().getName()
                                + " 进入当前系统的并发数是："
                                + (3 - semaphore.availablePermits()));
                        // 线程休眠一个随机时间戳（1秒内）
                        Thread.sleep(new Random().nextInt(1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程：" + Thread.currentThread().getName()
                            + " 即将离开");
                    // 释放许可信号
                    semaphore.release();
                    System.out.println("线程：" + Thread.currentThread().getName()
                            + " 已经离开，当前系统的并发数是："
                            + (3 - semaphore.availablePermits()));
                }
            };
            pool.execute(runnable); // 执行线程池任务
        }
    }

}
