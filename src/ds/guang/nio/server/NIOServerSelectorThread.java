package ds.guang.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;

/**
 * @author guangyong.deng
 * @date 2021-12-07 16:58
 */
public class NIOServerSelectorThread {

    private  ServerSocket serverSocket;

    private Selector selector;

    public NIOServerSelectorThread(int port) {
        try {
            //打开ServerSocketChannel，用于监听客户端的连接，他是所有客户端连接的父管道
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //将管道设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //利用ServerSocketChannel创建一个服务端Socket对象，即ServerSocket
            serverSocket = serverSocketChannel.socket();
            //为服务端Socket绑定监听端口
            serverSocket.bind(new InetSocketAddress(port));
            //创建多路复用器
            selector = Selector.open();

            //将ServerSocketChannel注册到Selector多路复用器上，并且监听ACCEPT事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("The server is start in port: "+port);

            while (true) {

                selector.select();

                Set<SelectionKey> keys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()) {

                    SelectionKey key = iterator.next();

                    if(key.isAcceptable()) {
                        System.out.println("有一个新连接");
                        // 新连接
                        // 创建一个 SocketChannel 进行通信
                        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
                        socketChannel.configureBlocking(false);

                        ByteBuffer buffer = ByteBuffer.allocate(1024);

                        byte[] data = "hi new channel".getBytes();
                        buffer.put(data);
                        System.out.println("发送数据" + Arrays.toString(buffer.array()));
                        buffer.flip();
                        socketChannel.write(buffer);

                        socketChannel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);


                    }

                    // 测试window 下，多路复用事件是水平还是垂直
                    if(key.isWritable()){


                    }

                    if(key.isReadable()) {
//                        SocketChannel clntChan = (SocketChannel) key.channel();
//                        ByteBuffer buffer = ByteBuffer.allocate(14);
//                        long bytesRead = clntChan.read(buffer);
//                        //如果read（）方法返回-1，说明客户端关闭了连接，那么客户端已经接收到了与自己发送字节数相等的数据，可以安全地关闭
//                        if (bytesRead == -1){
//                            clntChan.close();
//                        }else if(bytesRead > 0){
//                            //如果缓冲区总读入了数据，则将该信道感兴趣的操作设置为为可读可写
//                            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
//                            System.out.println(Arrays.toString(buffer.array()));
//                        }
                    }
                    iterator.remove();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        new NIOServerSelectorThread(8888);
    }

}
