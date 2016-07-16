package spymemcached;

import net.spy.memcached.MemcachedClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 功能描述：spymemcached客户端调用.
 * 工程名：memcachedDemo
 * 包名：spymemcached
 * 创建时间：2016/7/16 11:01
 *
 * @author lunhengle
 */
public class BaseSpymemcachedTest {
    /**
     * 设定失效时间.
     */

    private final int expiredSeconds = 100;
    /**
     * 设定链接地址.
     */
    private final InetSocketAddress server = new InetSocketAddress("127.0.0.1", 11211);
    /**
     * 设定memcached客户端.
     */
    private MemcachedClient memcachedClient;

    @Before
    public void setUp() throws IOException {
        memcachedClient = new MemcachedClient(server);
    }

    @Test
    public void testSet() {
        memcachedClient.set("lun", expiredSeconds, "hengle");
        String value = String.valueOf(memcachedClient.get("lun"));
        System.out.println("value:" + value);
    }

    @After
    public void setDown() {
        memcachedClient.delete("lun");
        memcachedClient.shutdown();
    }
}
