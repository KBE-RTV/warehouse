package com.kbertv.warehouse.WarehouseService;

import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.service.CelestialBodyRepository;
import com.kbertv.warehouse.service.WarehouseService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
public class WarehouseFeaturesTest {

    @Mock
    private static CelestialBodyRepository celestialBodyRepository;
    @InjectMocks
    private WarehouseService warehouseService;

    private static final List<CelestialBody> A_AND_B_BODIES = new LinkedList<>();
    private static final List<CelestialBody> ONLY_B_BODIES = new LinkedList<>();
    private static final List<CelestialBody> TWOA_AND_B_BODIES = new LinkedList<>();
    private static final List<CelestialBody> emptyList = new LinkedList<>();

    private static final UUID UUID_BODY_A = UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5");
    private static final UUID UUID_BODY_B = UUID.fromString("3e0a825c-b9cf-4b51-a1c1-1f9db6450fbf");
    private static final CelestialBody BODY_A = new CelestialBody(UUID_BODY_A, "Body1", 1, 1, "sun", 1, 1, 1, 1, 1, 1, 1);
    private static final CelestialBody BODY_B = new CelestialBody(UUID_BODY_B, "Body2", 1, 1, "planet", 1, 1, 1, 1, 1, 1, 1);

    @BeforeAll
    static void setUp() {
        A_AND_B_BODIES.add(BODY_A);
        A_AND_B_BODIES.add(BODY_B);
        ONLY_B_BODIES.add(BODY_B);

        BODY_A.setAmount(2);
        TWOA_AND_B_BODIES.add(BODY_A);
        TWOA_AND_B_BODIES.add(BODY_B);
    }

    @Test
    void givenAPresentWhenAddingAAndBThenReturnedTwoAAndBTest() {
        Mockito.when(celestialBodyRepository.findById(UUID_BODY_A)).thenReturn(Optional.of(BODY_A));
        Mockito.when(celestialBodyRepository.findById(UUID_BODY_B)).thenReturn(Optional.empty());

        Assertions.assertEquals(TWOA_AND_B_BODIES, warehouseService.adjustAmountWithCelestialBodyRepoEntries(A_AND_B_BODIES));
    }

    @Test
    void givenAPresentWhenAddingBThenReturnedAAndBTest() {
        Mockito.when(celestialBodyRepository.findById(UUID_BODY_A)).thenReturn(Optional.of(BODY_A));
        Mockito.when(celestialBodyRepository.findById(UUID_BODY_B)).thenReturn(Optional.empty());

        Assertions.assertEquals(ONLY_B_BODIES, warehouseService.adjustAmountWithCelestialBodyRepoEntries(ONLY_B_BODIES));
    }

    @Test
    void givenAPresentThenAddingNothingThenReturnedNothingTest() {
        Mockito.when(celestialBodyRepository.findById(UUID_BODY_A)).thenReturn(Optional.of(BODY_A));
        Mockito.when(celestialBodyRepository.findById(UUID_BODY_B)).thenReturn(Optional.empty());

        Assertions.assertEquals(emptyList, warehouseService.adjustAmountWithCelestialBodyRepoEntries(emptyList));
    }

}
