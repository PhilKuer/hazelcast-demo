package de.ordix.news.hazelcastdemo.config;

import com.hazelcast.config.*;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfig {
    @Bean
    public Config hazelCastConfig() {
        Config config = new Config();
        config.setInstanceName("hazelcast-member")
                .addMapConfig(new MapConfig()
                        .setName("firstMap")
                        .setMaxSizeConfig(new MaxSizeConfig(200,
                                MaxSizeConfig.MaxSizePolicy.FREE_HEAP_SIZE))
                        .setEvictionPolicy(EvictionPolicy.LRU)
                        .setTimeToLiveSeconds(-1));

        NetworkConfig networkConfig = config.getNetworkConfig();
        networkConfig.setPort(5701);
        networkConfig.setPortAutoIncrement(true);

        JoinConfig joinConfig = networkConfig.getJoin();

        // Deaktiviere Multicast
        joinConfig.getMulticastConfig().setEnabled(false);

        // Aktiviere TCP / IP
        TcpIpConfig tcpIpConfig = new TcpIpConfig();
        tcpIpConfig.setEnabled(true);
        tcpIpConfig.getMembers().add("127.0.0.1:5701");
        tcpIpConfig.getMembers().add("127.0.0.1:5702");
        joinConfig.setTcpIpConfig(tcpIpConfig);
        return config;
    }

    @Bean
    public CacheManager hazelcastCacheManager(HazelcastInstance hazelcastInstance) {
        return new HazelcastCacheManager(hazelcastInstance);
    }
}
