package com.kbertv.warehouse.WarehouseService;

import com.kbertv.warehouse.config.TextToCelestialBody;
import com.kbertv.warehouse.model.CelestialBody;
import com.kbertv.warehouse.model.CelestialBodyTypes;
import com.kbertv.warehouse.service.CelestialBodyRepository;
import com.kbertv.warehouse.service.WarehouseService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
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

import static org.junit.jupiter.api.Assertions.fail;

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
    private static final CelestialBody celestialBody1 = new CelestialBody(1,"Body1",1,1, CelestialBodyTypes.sun,1,1,1,1,1,1,1,1);
    private static final CelestialBody celestialBody2 = new CelestialBody(2,"Body2",1,1, CelestialBodyTypes.moon,1,1,1,1,1,1,1,1);
    private TextToCelestialBody textToCelestialBody = new TextToCelestialBody();

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
        Mockito.when(celestialBodyRepository.findById(1)).thenReturn(Optional.of(celestialBody1));
        Mockito.when(celestialBodyRepository.findById(2)).thenReturn(Optional.empty());

        Assertions.assertEquals(referenceDataAdjustedCelestialBodies,warehouseService.adjustAmountWithCelestialBodyRepoEntries(testDataOverlapCelestialBodies));
    }

    @Test
    void adjustAmountWithCelestialBodyRepoEntries_NoOverlap_Test(){
        Mockito.when(celestialBodyRepository.findById(1)).thenReturn(Optional.of(celestialBody1));
        Mockito.when(celestialBodyRepository.findById(2)).thenReturn(Optional.empty());

        Assertions.assertEquals(testDataNoOverlapCelestialBodies,warehouseService.adjustAmountWithCelestialBodyRepoEntries(testDataNoOverlapCelestialBodies));
    }

    @Test
    void adjustAmountWithCelestialBodyRepoEntries_Empty_Test(){
        Mockito.when(celestialBodyRepository.findById(1)).thenReturn(Optional.of(celestialBody1));
        Mockito.when(celestialBodyRepository.findById(2)).thenReturn(Optional.empty());

        Assertions.assertEquals(emptyList,warehouseService.adjustAmountWithCelestialBodyRepoEntries(emptyList));
    }

    @Test
    void stringToCelestialBody_Norm_Test(){
        String validString = "1+Sun+1+1+sun+0+1+1+1+1+1+1+1";
        CelestialBody referenceCelestialBody = new CelestialBody(1,"Sun",1,1,CelestialBodyTypes.sun,0,1,1,1,1,1,1,1);

        CelestialBody celestialBody = null;
        try {
            celestialBody = (CelestialBody) textToCelestialBody.convertToRead(validString);
        } catch (CsvDataTypeMismatchException e) {
            fail();
        }

        Assertions.assertEquals(referenceCelestialBody.toString(),celestialBody.toString());

    }

    @Test
    void stringToCelestialBody_Value_Missing_Test(){
        String invalidString = "1+Sun+1+1+sun+0+1+1+1+1+1+1";

        Assertions.assertThrows(CsvDataTypeMismatchException.class, () -> textToCelestialBody.convertToRead(invalidString));
    }

    @Test
    void stringToCelestialBody_Wrong_Datatype_Test(){
        String invalidString = "A+Sun+1+1+sun+0+1+1+1+1+1+1+1";

        Assertions.assertThrows(CsvDataTypeMismatchException.class, () -> textToCelestialBody.convertToRead(invalidString));
    }
}
