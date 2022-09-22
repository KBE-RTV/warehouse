package com.kbertv.warehouse.WarehouseService;

import com.kbertv.warehouse.config.RedisCacheConfig;
import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.PlanetarySystem;
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
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Import({RedisCacheConfig.class, WarehouseService.class})
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
    void givenRedisCachingWhenFindCelestialBodyByIdThenCelestialBodyReturnedFromCacheTest() {
        UUID testID = UUID.fromString("e0225629-05de-40fc-a29e-5c88e0c4c73c");
        CelestialBody celestialBody = new CelestialBody();
        celestialBody.setId(testID);
        given(mockCelestialBodyRepository.findById(testID))
                .willReturn(Optional.of(celestialBody));

        Optional<CelestialBody> itemCacheMiss = warehouseService.getCelestialBody(testID);
        Optional<CelestialBody> itemCacheHit = warehouseService.getCelestialBody(testID);

        assertThat(itemCacheMiss.orElseThrow()).isEqualTo(celestialBody);
        assertThat(itemCacheHit.orElseThrow()).isEqualTo(celestialBody);

        verify(mockCelestialBodyRepository, times(1)).findById(testID);
        assertThat(celestialBodyFromCache(testID)).isEqualTo(celestialBody);
    }

    @Test
    void givenRedisCachingWhenFindPlanetarySystemByIdThenPlanetarySystemReturnedFromCacheTest() {
        UUID testID = UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5");
        PlanetarySystem planetarySystem = new PlanetarySystem();
        planetarySystem.setId(testID);
        given(mockPlanetarySystemRepository.findById(testID))
                .willReturn(Optional.of(planetarySystem));

        Optional<PlanetarySystem> itemCacheMiss = warehouseService.getPlanetarySystem(testID);
        Optional<PlanetarySystem> itemCacheHit = warehouseService.getPlanetarySystem(testID);

        assertThat(itemCacheMiss.orElseThrow()).isEqualTo(planetarySystem);
        assertThat(itemCacheHit.orElseThrow()).isEqualTo(planetarySystem);

        verify(mockPlanetarySystemRepository, times(1)).findById(testID);
        assertThat(planetarySystemFromCache(testID)).isEqualTo(planetarySystem);
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

    private Object celestialBodyFromCache(Object key) {
        return Objects.requireNonNull(Objects.requireNonNull(cacheManager.getCache("celestialBodyCache")).get(key)).get();
    }

    private Object planetarySystemFromCache(Object key) {
        return Objects.requireNonNull(Objects.requireNonNull(cacheManager.getCache("planetarySystemCache")).get(key)).get();
    }
}
