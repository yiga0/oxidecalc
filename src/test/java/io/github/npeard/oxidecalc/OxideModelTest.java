/*
 * Copyright (c) 2015-2026 Nolan Peard. MIT License.
 */
package io.github.npeard.oxidecalc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Golden-value tests for {@link OxideModel}, pinned to the output of the original 2015 calculator
 * so the modernization stays behavior-preserving.
 */
class OxideModelTest {

  @ParameterizedTest(name = "{0}/{1} {2}C {3}->{4}A = {5}")
  @CsvSource({
    "WET, ORI_100, 1050,   50, 10000,  3:05:09",
    "WET, ORI_110, 1050,   50, 10000,  2:52:50",
    "WET, ORI_111, 1050,   50, 10000,  2:49:06",
    "DRY, ORI_100, 1000,  100,  1000,  2:57:32",
    "DRY, ORI_110, 1100,  200,  5000, 12:32:22",
    "DRY, ORI_111,  900,   50,   500,  3:26:49",
  })
  void matchesLegacyGoldenValues(
      Ambient ambient,
      Orientation orientation,
      int temperatureC,
      int initialAngstrom,
      int finalAngstrom,
      String expected) {
    GrowthTime time =
        OxideModel.growthTime(ambient, orientation, temperatureC, initialAngstrom, finalAngstrom);
    assertEquals(expected, time.toString());
  }

  @Test
  void zeroGrowthWhenTargetEqualsStart() {
    GrowthTime time = OxideModel.growthTime(Ambient.WET, Orientation.ORI_100, 1050, 1000, 1000);
    assertEquals("0:00:00", time.toString());
  }

  @Test
  void negativeGrowthIsClampedToZero() {
    GrowthTime time = OxideModel.growthTime(Ambient.WET, Orientation.ORI_100, 1050, 5000, 50);
    assertEquals(Duration.ZERO, time.duration());
  }

  @Test
  void higherTemperatureGrowsFaster() {
    GrowthTime hot = OxideModel.growthTime(Ambient.WET, Orientation.ORI_100, 1100, 50, 10000);
    GrowthTime cold = OxideModel.growthTime(Ambient.WET, Orientation.ORI_100, 1000, 50, 10000);
    assertEquals(true, hot.duration().compareTo(cold.duration()) < 0);
  }

  @Test
  void growthTimeFormatsHoursWithoutLeadingZero() {
    assertEquals("3:05:09", GrowthTime.ofHours(3.086).toString());
  }
}
