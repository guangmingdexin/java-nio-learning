package ds.guang.netty.internal;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;
import io.netty.util.internal.InternalThreadLocalMap;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author guangyong.deng
 * @date 2021-12-15 14:17
 */
public class ThreadLocalDemo {

    private static final AtomicInteger nextId = new AtomicInteger(1);

    private static final ThreadLocal<Integer> threadId = new ThreadLocal<>();

    private static final ThreadLocal<String> threadName = new ThreadLocal<>();

    public static void threadLocalDemo() {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                /**
                 *
                 * 每个 thread 中都有一个 ThreadMap 属性，会在开始使用时才创建（延迟加载）
                 * 如何实现创建多个 ThreadLocal,不会产生多个 ThreadMap （会进行判断，只有当线程）
                 * key 为 ThreadLocal
                 * 相当与 多个线程都会有一个指向 ThreadLocal的弱引用,所以只要有一个 线程不释放 ThreadLocal，都不会被释放
                 *
                 * 思考：为什么 ThreadLocalMap 会被定义为 static
                 *       1.创建静态内部类时不需要将静态内部类的实例绑定在外部类的实例上，可以直接通过 类名.访问
                 *       2.ThreadLocalMap 中有 static 属性，所以作为内部类必须被声明为 static
                 *
                 */
                threadId.set(nextId.getAndIncrement());
                threadName.set("local-thread-" + threadId.get());

                System.out.println("threadName=" + threadName.get() + ",threadId=" + threadId.get());
            }).start();
        }
    }


    /**
     * 创建之后，首先初始化 variablesToRemoveIndex 为 0 ， nextIndex = 1
     * 然后 构造函数 index 1，nextIndex = 2
     * 再然后
     */
    private static final FastThreadLocal<Integer> fastThreadId = new FastThreadLocal<>();

    public static void fastThreadLocalDemo() {

        fastThreadId.set(nextId.getAndIncrement());
        for (int i = 0; i < 5; i++) {
            new FastThreadLocalThread(() -> {
                /**
                 *
                 * 1.获取 FastThreadLocalThread 中的 InternalThreadLocalMap
                 *
                 */
                fastThreadId.set(nextId.getAndIncrement());
              //  threadName.set("local-thread-" + threadId.get());

                System.out.println("threadName=" + Thread.currentThread().getName() + ",threadId=" + fastThreadId.get());
            }).start();
        }
    }

    public static void main(String[] args) {
        AtomicInteger initial = new AtomicInteger();

        System.out.println(initial.intValue());
      //  fastThreadLocalDemo();
//        int i = InternalThreadLocalMap.nextVariableIndex();
//
//        int j = InternalThreadLocalMap.nextVariableIndex();


//        System.out.println(i + "->" + j);
    }


}
