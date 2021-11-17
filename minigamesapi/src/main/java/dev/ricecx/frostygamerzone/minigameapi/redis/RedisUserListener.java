package dev.ricecx.frostygamerzone.minigameapi.redis;

import dev.ricecx.frostygamerzone.common.redis.RedisListener;

public class RedisUserListener extends RedisListener {
    public RedisUserListener() {
        super("redis-user-listener", "users");
    }

    @Override
    protected void onMessage(String channel, String message) {

    }
}
