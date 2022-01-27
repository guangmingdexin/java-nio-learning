package ds.guang.netty.bytebuf;

/**
 * @author guangyong.deng
 * @date 2022-01-25 10:18
 */
public class PoolDemo {

    public static void main(String[] args) {

//        long bit = 0b1111;
//
//        System.out.println(Long.toBinaryString(~bit));
//        System.out.println(Long.toBinaryString(bit));

        // baseVal
        int i = 1;
        int baseVal = i << 6;
        System.out.println(Integer.toBinaryString(baseVal));

        int j = 2;
        int bitmapIndex = baseVal | j;

        System.out.println(Integer.toBinaryString(bitmapIndex));

        // q
        int q = bitmapIndex >>> 6;
        System.out.println(Integer.toBinaryString(q));


        // r
        int r = bitmapIndex & 63;
        System.out.println(Integer.toBinaryString(r));

        long bit = 0b011;

        long res = bit | (1 << r);

        System.out.println("1 << r: " + Long.toBinaryString(1<< r));
        System.out.println(Long.toBinaryString(res));
    }
}
