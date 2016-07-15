package xmemcached;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * 功能描述：xmemcached 测试.
 * 工程名：memcachedDemo
 * 创建时间：2016/7/15 14:05
 *
 * @author lunhengle
 */
public class BaseXMemcachedTest {
    private final static String addressHost = "127.0.0.1";
    private final static String addressPort = "11211";
    private XMemcachedClientBuilder xMemcachedClientBuilder;
    private MemcachedClient memcachedClient;

    /**
     * 创建客户端.
     *
     * @throws IOException
     */
    @Before
    public void setUp() throws IOException {
        xMemcachedClientBuilder = new XMemcachedClientBuilder(AddrUtil.getAddresses(addressHost + ":" + addressPort));
        memcachedClient = xMemcachedClientBuilder.build();
    }

    /**
     * 测试set方法.
     * set 方法有三个参数 第一个参数是存储key名称 第二个参数是存储保留多长时间 找过这个时间（秒），memcached将这个数据替换出去，
     * 0代表 永久存储 （默认是一个月）
     *
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @Test
    public void testSet() throws InterruptedException, MemcachedException, TimeoutException {
        memcachedClient.set("lun", 0, "world");
        String value = memcachedClient.get("lun");
        System.out.println("value:" + value);
    }

    /**
     * 测试 add 方法.
     *
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @Test
    public void testAdd() throws InterruptedException, MemcachedException, TimeoutException {
        memcachedClient.add("lun", 0, "hengle");
        String value = memcachedClient.get("lun");
        System.out.println("add value:" + value);
    }

    /**
     * 测试set和add混合调用.
     * add方法和set方法的区别是第二次赋值 set会改变保留第二次的新值 add不会改变还是保持第一次的值
     *
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @Test
    public void testSetOrAdd() throws InterruptedException, MemcachedException, TimeoutException {
        //set
        memcachedClient.set("lun", 0, "hengle");
        String value = memcachedClient.get("lun");
        System.out.println("set value:" + value);
        //add
        memcachedClient.add("lun", 0, "yu");
        value = memcachedClient.get("lun");
        System.out.println("add value:" + value);
        //set
        memcachedClient.set("lun", 0, "li");
        value = memcachedClient.get("lun");
        System.out.println("set value:" + value);
        //add
        memcachedClient.add("lun", 0, "yu");
        value = memcachedClient.get("lun");
        System.out.println("add value:" + value);
        //add
        memcachedClient.add("lun", 0, "yu");
        value = memcachedClient.get("lun");
        System.out.println("add value:" + value);
    }

    /**
     * 之前之后拼接.
     *
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @Test
    public void testAppendAndPrepend() throws InterruptedException, MemcachedException, TimeoutException {
        memcachedClient.set("lun", 0, " hengle");
        String value = memcachedClient.get("lun");
        System.out.println("value:" + value);
        memcachedClient.prepend("lun", "lun");
        value = memcachedClient.get("lun");
        System.out.println("prepend value:" + value);
        memcachedClient.append("lun", " hello world");
        value = memcachedClient.get("lun");
        System.out.println("append value:" + value);
    }

    /**
     * 测试替换.
     *
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @Test
    public void testReplace() throws InterruptedException, MemcachedException, TimeoutException {
        memcachedClient.set("lun", 0, "hengle");
        String value = memcachedClient.get("lun");
        System.out.println("value:" + value);
        memcachedClient.replace("lun", 0, "yu");
        value = memcachedClient.get("lun");
        System.out.println("replace value:" + value);
    }

    /**
     * 测试数组类型.
     *
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @Test
    public void testArray() throws InterruptedException, MemcachedException, TimeoutException {
        String[] arr = {"one", "two", "three", "four", "five"};
        memcachedClient.set("lun", 0, arr);
        String[] values = memcachedClient.get("lun");
        for (String value : values) {
            System.out.println("value:" + value);
        }
    }

    /**
     * 测试list.
     *
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @Test
    public void testList() throws InterruptedException, MemcachedException, TimeoutException {
        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");
        list.add("four");
        list.add("five");
        memcachedClient.set("lun", 0, list);
        List<String> values = memcachedClient.get("lun");
        for (String value : values) {
            System.out.println("value:" + value);
        }
    }

    /**
     * 测试map.
     *
     * @throws InterruptedException
     * @throws MemcachedException
     * @throws TimeoutException
     */
    @Test
    public void testMap() throws InterruptedException, MemcachedException, TimeoutException {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");
        map.put(4, "four");
        map.put(5, "five");
        memcachedClient.set("lun", 2, map);
        Map<Integer, String> values = memcachedClient.get("lun");
        for (Map.Entry value : values.entrySet()) {
            System.out.println("value:" + value.getValue());
        }
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
