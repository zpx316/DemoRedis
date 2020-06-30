package cn.zp.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.List;

/**
 * @author zp
 * @Description:
 * @date 2020/6/25 下午5:55
 */
public class DemoRedis {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1", 6379);

        //  第一节最简单的分布式锁
        jedis.set("hello", "zhangsan");
        System.out.println(jedis.get("hello"));

        jedis.del("lock_test");
        String result = jedis.set("lock_test", "value_test", SetParams.setParams().nx());
        System.out.println("第一次获取分布式锁 :" + result);
        String result1 = jedis.set("lock_test", "value_test", SetParams.setParams().nx());
        System.out.println("第二次获取分布式锁 :" + result1);

        jedis.del("lock_test");
        String result2 = jedis.set("lock_test", "value_test", SetParams.setParams().nx());
        System.out.println("第三次获取分布式锁 :" + result2);
        //第二节  批量操作  mget  mset  msetnx
        // 微博的 发布修改和查看
        Long publishResult = jedis.msetnx("article:1:title", "学习redis",
                "article:1:content", "如何学好redis的使用",
                "article:1:author", "张三",
                "article:1:time", "2020-06-25 00:00:00"
        );

        System.out.println("微博发布的结果:" + publishResult);
        List<String> lists = jedis.mget("article:1:title", "article:1:time");
        lists.forEach(i -> System.out.println(i));
        jedis.mset("article:1:title", "修改后的学习redis"
                , "article:1:time", "2020-06-25 01:00:00");

        lists.forEach(i -> System.out.println(i));

        //第三节  strlen  查看长度


        Long blogLength = jedis.strlen("article:1:content");

        System.out.println("博客内容的长度：" + blogLength);


        String blogview = jedis.getrange("article:1:content", 0, 5);


        System.out.println("博客内容预览 :" + blogview);


        //第四节  审计日志   append 追加


        jedis.setnx("operation_log_2020_06_25", "");

        for (int i = 0; i < 10; i++) {
            jedis.append("operation_log_2020_06_25", "今天的第" + (i + 1) + "条操作日志\n");
        }


        System.out.println("审计日志：\n" + jedis.get("operation_log_2020_06_25"));

        //第五节  计数器  incr  自增
        jedis.del("order_id_counter");
        for (int i = 0; i < 10; i++) {
            Long orderId = jedis.incr("order_id_counter");

            System.out.println("生成的第:" + (i + 1) + "个唯一id ：" + orderId);
        }

    }
}