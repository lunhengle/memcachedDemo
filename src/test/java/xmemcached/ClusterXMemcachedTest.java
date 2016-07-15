package xmemcached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：集群测试.
 * 工程名：memcachedDemo
 * 包名：xmemcached
 * 创建时间：2016/7/15 16:05
 * sc start "memcached2"  //启动
 * sc stop "memcached2"  //停止
 * sc delete "memcached2"  //卸载该服务
 * sc create "memcached2" start= auto binPath= "D:\memcached-1.4.4-win32\memcached.exe -d runservice -m 32 -p 11212 -l 127.0.0.1" DisplayName= "memcached2" //创建服务
 *
 * @author lunhengle
 */
public class ClusterXMemcachedTest {
    private XMemcachedClientBuilder xMemcachedClientBuilder;
    private MemcachedClient memcachedClient;

    /**
     * 创建客户端.
     *
     * @throws IOException
     */
    @Before
    public void setUp() throws IOException {
        /**
         *<!-- # 服务器节点:形如："主节点1:port,备份节点1:port 主节点2:port,备份节点2:port"的字符串， #可以不设置备份节点，主备节点逗号隔开，不同分组空格隔开
         *#由于该应用功能是加载数据，可以只考虑分布式，即以空格分隔ip的配置方式，不考虑主备关系 -->
         */
        xMemcachedClientBuilder = new XMemcachedClientBuilder(AddrUtil.getAddresses("127.0.0.1:11210 127.0.0.1:11211 127.0.0.1:11212"));
        memcachedClient = xMemcachedClientBuilder.build();
    }

    /**
     * 测试set方法
     */
    @Test
    public void testSet() throws InterruptedException, MemcachedException, TimeoutException {
        memcachedClient.set("lun", 0, "hengle");
        String value = memcachedClient.get("lun");
        System.out.println(value);
    }

    /**
     * 销毁客户端.
     *
     * @throws IOException
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @After
    public void setDown() throws IOException, InterruptedException, MemcachedException, TimeoutException {
        memcachedClient.delete("lun");
        memcachedClient.shutdown();
    }
}
