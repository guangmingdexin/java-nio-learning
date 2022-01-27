package ds.guang.netty.internal;

import io.netty.util.concurrent.FastThreadLocal;
import io.netty.util.concurrent.FastThreadLocalThread;

/**
 * @author guangyong.deng
 * @date 2021-12-16 14:05
 */
public class MemoryLeakTest {

    public static void main(String[] args) {
        new FastThreadLocalThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    TestClass t = new TestClass(i);
                    t.printId();
                    // 会发生内存泄漏
                    t = null;
                    //t.threadLocal.remove(); //不会发生泄漏
                }
            }
        }).start();
    }


    static class TestClass{
        private int id;
        private int[] arr;
        private FastThreadLocal<TestClass> threadLocal;
        TestClass(int id){
            this.id = id;
            arr = new int[1000000];
            threadLocal = new FastThreadLocal<>();
            threadLocal.set(this);
        }

        public void printId(){
            System.out.println(threadLocal.get().id);
        }
    }

}
