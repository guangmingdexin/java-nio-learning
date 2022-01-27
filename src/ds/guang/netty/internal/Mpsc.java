package ds.guang.netty.internal;


import org.jctools.queues.MpscArrayQueue;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author guangyong.deng
 * @date 2022-01-17 11:01
 */
public class Mpsc {


    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        MpscArrayQueue<Integer> mpsc = new MpscArrayQueue<>(100);

        final Field field = Unsafe.class.getDeclaredField("theUnsafe");
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);


        int[] a = new int[] {1, 2, 3};
        int[] b = new int[]{4, 5, 6};

//        System.out.println(unsafe.arrayIndexScale(int[].class));
//        System.out.println(unsafe.arrayIndexScale(Object[].class));
//        System.out.println(unsafe.arrayIndexScale(byte[].class));


        System.out.println(unsafe.arrayBaseOffset(int[].class));
        System.out.println(unsafe.arrayBaseOffset(byte[].class));
    }
}
