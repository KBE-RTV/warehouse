package com.kbertv.warehouse.WarehouseService;

import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.CelestialBodyTypes;
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
public class ComponentFeaturesTest {

    @Mock
    private CelestialBodyRepository celestialBodyRepository;
    @InjectMocks
    private WarehouseService warehouseService;

    private static final List<CelestialBody> testDataOverlapCelestialBodies = new LinkedList<>();
    private static final List<CelestialBody> testDataNoOverlapCelestialBodies = new LinkedList<>();
    private static final List<CelestialBody> referenceDataAdjustedCelestialBodies = new LinkedList<>();
    private static final List<CelestialBody> emptyList = new LinkedList<>();
    private static final CelestialBody celestialBody1 = new CelestialBody(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5"),"Body1",1,1, CelestialBodyTypes.sun,1,1,1,1,1,1,1);
    private static final CelestialBody celestialBody2 = new CelestialBody(UUID.fromString("3e0a825c-b9cf-4b51-a1c1-1f9db6450fbf"),"Body2",1,1, CelestialBodyTypes.planet,1,1,1,1,1,1,1);

    @BeforeAll
    static void setUp(){
        testDataOverlapCelestialBodies.add(celestialBody1);
        testDataOverlapCelestialBodies.add(celestialBody2);
        testDataNoOverlapCelestialBodies.add(celestialBody2);
        celestialBody1.setAmount(2);
        referenceDataAdjustedCelestialBodies.add(celestialBody1);
        referenceDataAdjustedCelestialBodies.add(celestialBody2);

    }

    @Test
     void adjustAmountWithCelestialBodyRepoEntries_Norm_Test(){
        Mockito.when(celestialBodyRepository.findById(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5"))).thenReturn(Optional.of(celestialBody1));
        Mockito.when(celestialBodyRepository.findById(UUID.fromString("3e0a825c-b9cf-4b51-a1c1-1f9db6450fbf"))).thenReturn(Optional.empty());

        Assertions.assertEquals(referenceDataAdjustedCelestialBodies,warehouseService.adjustAmountWithCelestialBodyRepoEntries(testDataOverlapCelestialBodies));
    }

    @Test
    void adjustAmountWithCelestialBodyRepoEntries_NoOverlap_Test(){
        Mockito.when(celestialBodyRepository.findById(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5"))).thenReturn(Optional.of(celestialBody1));
        Mockito.when(celestialBodyRepository.findById(UUID.fromString("3e0a825c-b9cf-4b51-a1c1-1f9db6450fbf"))).thenReturn(Optional.empty());

        Assertions.assertEquals(testDataNoOverlapCelestialBodies,warehouseService.adjustAmountWithCelestialBodyRepoEntries(testDataNoOverlapCelestialBodies));
    }

    @Test
    void adjustAmountWithCelestialBodyRepoEntries_Empty_Test(){
        Mockito.when(celestialBodyRepository.findById(UUID.fromString("9708b2f4-98d6-4891-b59e-52da0a484fc5"))).thenReturn(Optional.of(celestialBody1));
        Mockito.when(celestialBodyRepository.findById(UUID.fromString("3e0a825c-b9cf-4b51-a1c1-1f9db6450fbf"))).thenReturn(Optional.empty());

        Assertions.assertEquals(emptyList,warehouseService.adjustAmountWithCelestialBodyRepoEntries(emptyList));
    }

}
