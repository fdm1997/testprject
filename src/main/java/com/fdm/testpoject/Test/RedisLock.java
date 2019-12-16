package com.fdm.testpoject.Test;

import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * @author fdm
 * @date 2019/7/15 10:03
 * @Description:
 */
public class RedisLock {


    public static void main(String[] args) {

/*        RedisCallback<Boolean> redisCallback = new RedisCallback<boolean>() {
            @Override
            public boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return false;
            }
        };*/
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.execute( (RedisCallback<Boolean>) redisconnect ->{
            Jedis jedis = (Jedis) redisconnect.getNativeConnection();
            String result = jedis.set("id","1234","NX","PX",200);
            System.out.println(result+"-----");

            return Boolean.TRUE;
        } );

    }
}
