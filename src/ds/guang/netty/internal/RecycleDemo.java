package ds.guang.netty.internal;

import io.netty.util.Recycler;

import java.lang.ref.WeakReference;

/**
 * @author guangyong.deng
 * @date 2021-12-22 14:12
 */
public class RecycleDemo {

    private static final Recycler<User> userRecycler = new Recycler<User>() {
        @Override
        protected User newObject(Handle<User> handle) {
            return new User(handle);
        }
    };

    private static void singleThreadTest() {

        // 从对象池中获取 对象
        User user = userRecycler.get();

        user.name = "张三";

        System.out.println("user: " + user);

        user.recycle();

        System.out.println("user: " + user);
    }

    private static void multiThreadTest() {

        // 1、从回收池获取对象
        User user1 = userRecycler.get();
        // 2、设置对象并使用
        user1.setName("hello,java");

        Thread thread = new Thread(()->{
            System.out.println(Thread.currentThread().getName() + ":" + user1);
            // 3、对象恢复出厂设置
            user1.setName(null);
            // 4、回收对象到对象池
            user1.recycle();
        }, "t1");

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 5、从回收池获取对象
        User user2 = userRecycler.get();

        System.out.println(Thread.currentThread().getName() + ":" + user2);

        System.out.println(user1 == user2);
    }

    public static void weakReferenceDemo() {

        Car car = new Car("一汽大众");

        WeakReference<Car> weakReferenceCar = new WeakReference<>(car);

        System.out.println(weakReferenceCar.get());

        int i = 0;
        car = null;
        System.gc();

        System.out.println(weakReferenceCar.get());

//        while (true) {
//            if(weakReferenceCar.get()!=null){
//                i++;
//                System.out.println("Object is alive for "+ i + " loops - " + weakReferenceCar);
//            }else{
//                System.out.println("Object has been collected.");
//                System.out.println("after collected: " + weakReferenceCar);
//                break;
//            }
//        }
    }

    public static void main(String[] args) {

      // multiThreadTest();
      //  weakReferenceDemo();
        singleThreadTest();
    }


   static  class User {

       private String name;

        private Recycler.Handle<User> handle;

        public User(Recycler.Handle<User> handle) {
            this.handle = handle;
        }

        public void recycle() {
            handle.recycle(this);
        }

       public User setName(String name) {
           this.name = name;
           return this;
       }

       @Override
       public String toString() {
           final StringBuilder sb = new StringBuilder();
           sb.append("{")
                   .append("\"name\":").append(name)
                   .append('}');
           return sb.toString();
       }
   }

   static class Car {

       private String name;

       public Car(String name) {
           this.name = name;
       }
   }
}
