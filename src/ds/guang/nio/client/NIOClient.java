package ds.guang.nio.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author guangyong.deng
 * @date 2022-01-20 9:54
 */
public class NIOClient {


    public NIOClient(int port) throws IOException {

        try {
            // 创建一个通道, 并发起连接
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8888));

            // 发起连接
            while (!socketChannel.isConnected()) {
                System.out.println("正在连接！");
                Thread.sleep(1000);
            }

            if(socketChannel.isConnected()) {
                System.out.println("连接完成！");
            }

            // ByteBuffer 分配必须设置容量，且无法自动扩容
            ByteBuffer readBuf = ByteBuffer.allocate(14);


            //接收到的总的字节数
            int totalBytesRcvd = 0;
            //每一次调用read（）方法接收到的字节数
            int bytesRcvd;
            //循环执行，直到接收到的字节数与发送的字符串的字节数相等
            while (totalBytesRcvd < 14){

                //如果read（）接收到-1，表明服务端关闭，抛出异常
                if ((bytesRcvd = socketChannel.read(readBuf)) == -1){
                    throw new SocketException("Connection closed prematurely");
                }
                //计算接收到的总字节数
                System.out.println("readBytes: " + bytesRcvd);
                totalBytesRcvd += bytesRcvd;
                //在等待通信完成的过程中，程序可以执行其他任务，以体现非阻塞IO的异步特性
                //这里为了演示该方法的使用，同样只是一直打印"."
                // System.out.print(".");
            }


            //打印出接收到的数据
            System.out.println("Received: " +  new String(readBuf.array(), 0, totalBytesRcvd));
            //关闭信道
            socketChannel.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }




    public static void main(String[] args) throws IOException {
        new NIOClient(8888);
    }
}
