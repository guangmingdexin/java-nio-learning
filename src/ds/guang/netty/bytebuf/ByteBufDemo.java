package ds.guang.netty.bytebuf;

import io.netty.buffer.*;

import java.nio.ByteBuffer;

/**
 * @author guangyong.deng
 * @date 2022-01-05 9:36
 */
public class ByteBufDemo {

    public static void main(String[] args) {

        ByteBuf byteBuf;

        // 使用堆内存
        byteBuf = ByteBufAllocator.DEFAULT.heapBuffer();
        // 使用直接内存
        byteBuf = ByteBufAllocator.DEFAULT.directBuffer();

        ByteBuffer direct = ByteBuffer.allocateDirect(48);

        // 非池化对象
        byteBuf = Unpooled.buffer();


       //  CompositeByteBuf buf = new CompositeByteBuf(ByteBufAllocator.DEFAULT.heapBuffer());

    }
}
