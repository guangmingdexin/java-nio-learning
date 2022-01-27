package ds.guang.nio.channel;

import java.nio.ByteBuffer;
import java.nio.channels.Channel;

/**
 * @author guangyong.deng
 * @date 2021-12-07 13:47
 */
public class ChannelDemo {

    public static void main(String[] args) {


        ByteBuffer buffer = ByteBuffer.allocate(1024);

        byte[] data = "hi new channel".getBytes();

        buffer.put(data);


        // 读
        buffer.flip();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }


        // 1.学习 Channel 模块

        System.out.println(~(1 << 4));

        System.out.println((1<<0) & ~(1 << 4));
    }
}
