package ds.guang.netty.server;

import io.netty.channel.nio.NioEventLoopGroup;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author guangyong.deng
 * @date 2021-12-15 9:21
 */
public class NettyServer {


    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        System.out.println(TimeUnit.SECONDS.toNanos(1));
    }
}
