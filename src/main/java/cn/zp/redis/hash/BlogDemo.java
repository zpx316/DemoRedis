package cn.zp.redis.hash;

import redis.clients.jedis.Jedis;

import javax.lang.model.element.NestingKind;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zp
 * @Description: TODO
 * @date 2020/6/26 上午10:56
 */
public class BlogDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * @Description: 发布博客
     * @Param:
     * @return:
     * @Author: zp
     * @Date: 2020/6/26 上午11:02
     */
    public  void  publishBlog(long id, Map<String, String> blog){

        jedis.hmset("article::" + id, blog);
    }

    /**
     * @Description:  增加博客浏览次数
     * @Param:
     * @return:
     * @Author: zp
     * @Date: 2020/6/26 上午11:08
     */
    public void viewBlog(long id){

        jedis.hincrBy("article::" + id, "view_count", 1);
    }
    /**
     * @Description:  查看blog
     * @Param:
     * @return:
     * @Author: zp
     * @Date: 2020/6/26 上午11:12
     */
    public Map<String,String> findBlogById(long id){

        Map<String, String> result = jedis.hgetAll("article::" + id);
        viewBlog(id);
        return result;
    }
    /**
     * @Description: 更新一篇博客
     * @Param:
     * @return:
     * @Author: zp
     * @Date: 2020/6/26 上午11:14
     */
    public void updateBlog(long id, Map<String,String> updateBlog){
        jedis.hmset("article::" + id, updateBlog);

    }
    /**
     * @Description:  对博客进行点赞
     * @Param:
     * @return:
     * @Author: zp
     * @Date: 2020/6/26 上午11:16
     */
    public void LikeBlog(long id){

        jedis.hincrBy("article::" + id, "like_count", 1);
    }

    /**
     * @Description:  获取时间字符串
     * @Param:
     * @return:
     * @Author: zp
     * @Date: 2020/6/26 上午11:02
     */
    public static String getTimeStr(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(date);
    }

}
