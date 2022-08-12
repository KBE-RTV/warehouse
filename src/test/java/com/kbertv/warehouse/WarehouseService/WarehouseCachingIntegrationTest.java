package com.kbertv.warehouse.WarehouseService;

import com.kbertv.warehouse.config.CacheConfig;
import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.CelestialBodyTypes;
import com.kbertv.warehouse.service.CelestialBodyRepository;
import com.kbertv.warehouse.service.IWarehouseService;
import com.kbertv.warehouse.service.PlanetarySystemRepository;
import com.kbertv.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.embedded.RedisServer;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import({ CacheConfig.class, WarehouseService.class})
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class WarehouseCachingIntegrationTest {

    @MockBean
    private CelestialBodyRepository mockCelestialBodyRepository;

    @MockBean
    private PlanetarySystemRepository mockPlanetarySystemRepository;

    @Autowired
    private IWarehouseService warehouseService;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void givenRedisCaching_whenFindComponentById_thenComponentReturnedFromCache() {
        CelestialBody celestialBody1 = new CelestialBody(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5"),"Body1",1,1, CelestialBodyTypes.sun,1,1,1,1,1,1,1);
        given(mockCelestialBodyRepository.findById(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5")))
                .willReturn(Optional.of(celestialBody1));

        Optional<CelestialBody> itemCacheMiss = warehouseService.getComponent(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5"));
        Optional<CelestialBody> itemCacheHit = warehouseService.getComponent(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5"));

        assertThat(itemCacheMiss.get()).isEqualTo(celestialBody1);
        assertThat(itemCacheHit.get()).isEqualTo(celestialBody1);

        verify(mockCelestialBodyRepository, times(1)).findById(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5"));
        assertThat(itemFromCache()).isEqualTo(celestialBody1);
    }

    @TestConfiguration
    static class EmbeddedRedisConfiguration {

        private final RedisServer redisServer;

        public EmbeddedRedisConfiguration() {
            this.redisServer = new RedisServer();
        }

        @PostConstruct
        public void startRedis() {
            redisServer.start();
        }

        @PreDestroy
        public void stopRedis() {
            this.redisServer.stop();
        }
    }

    private Object itemFromCache() {
        return cacheManager.getCache("componentCache").get(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5")).get();
    }
}
