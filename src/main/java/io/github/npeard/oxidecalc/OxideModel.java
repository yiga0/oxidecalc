/*
 * Copyright (c) 2015-2026 Nolan Peard. MIT License.
 */
package io.github.npeard.oxidecalc;

/**
 * Deal-Grove model of thermal silicon-dioxide growth.
 *
 * <p>The Deal-Grove linear-parabolic model relates oxide thickness {@code x} to oxidation time
 * {@code t}:
 *
 * <pre>
 *   x^2 / B  +  x / (B/A)  =  t  +  tau
 * </pre>
 *
 * where {@code B} is the parabolic rate constant (diffusion-limited) and {@code B/A} is the linear
 * rate constant (interface-reaction-limited). Both follow an Arrhenius temperature dependence.
 * Inverting for the time to grow from an initial thickness {@code x0} to a final thickness {@code
 * x} gives:
 *
 * <pre>
 *   t = (x^2 - x0^2) / B  +  (x - x0) / (B/A)
 * </pre>
 *
 * <p>Reference: B. E. Deal and A. S. Grove, "General Relationship for the Thermal Oxidation of
 * Silicon," <i>J. Appl. Phys.</i> 36, 3770 (1965).
 *
 * <p>Thicknesses are in angstroms, temperature in degrees Celsius. The class is a stateless,
 * side-effect-free calculation utility.
 */
public final class OxideModel {

  /** Boltzmann constant in eV/K (normalized to the electron charge). */
  private static final double BOLTZMANN_EV_PER_K = 8.6173e-5;

  /** Celsius-to-Kelvin offset used by the original 2015 model. */
  private static final int CELSIUS_TO_KELVIN = 273;

  /** Angstroms per micrometer (the rate constants are quoted in um). */
  private static final double ANGSTROM_PER_MICRON = 10_000.0;

  private OxideModel() {}

  /**
   * Parabolic rate constant {@code B} in um^2/hr.
   *
   * @param ambient oxidation ambient
   * @param temperatureCelsius furnace temperature in degrees Celsius
   */
  public static double parabolicRate(Ambient ambient, int temperatureCelsius) {
    int kelvin = temperatureCelsius + CELSIUS_TO_KELVIN;
    return ambient.preExpLinear()
        * Math.exp(-ambient.activationEnergyLinear() / (BOLTZMANN_EV_PER_K * kelvin));
  }

  /**
   * Linear rate constant {@code B/A} in um/hr.
   *
   * @param ambient oxidation ambient
   * @param orientation silicon surface orientation
   * @param temperatureCelsius furnace temperature in degrees Celsius
   */
  public static double linearRate(
      Ambient ambient, Orientation orientation, int temperatureCelsius) {
    int kelvin = temperatureCelsius + CELSIUS_TO_KELVIN;
    double preExp = ambient.preExpParabolic111() * orientation.relativeRate();
    return preExp * Math.exp(-ambient.activationEnergyParabolic() / (BOLTZMANN_EV_PER_K * kelvin));
  }

  /**
   * Time to grow oxide from {@code initialThicknessAngstrom} to {@code finalThicknessAngstrom}.
   *
   * @param ambient oxidation ambient (wet/dry)
   * @param orientation silicon surface orientation
   * @param temperatureCelsius furnace temperature in degrees Celsius
   * @param initialThicknessAngstrom starting oxide thickness, in angstroms
   * @param finalThicknessAngstrom target oxide thickness, in angstroms
   * @return the growth time, clamped to zero if the target is already reached
   */
  public static GrowthTime growthTime(
      Ambient ambient,
      Orientation orientation,
      int temperatureCelsius,
      int initialThicknessAngstrom,
      int finalThicknessAngstrom) {

    double parabolic = parabolicRate(ambient, temperatureCelsius);
    double linear = linearRate(ambient, orientation, temperatureCelsius);

    double x = finalThicknessAngstrom / ANGSTROM_PER_MICRON;
    double x0 = initialThicknessAngstrom / ANGSTROM_PER_MICRON;

    double hours = (x * x - x0 * x0) / parabolic + (x - x0) / linear;
    if (hours < 0.0) {
      hours = 0.0;
    }
    return GrowthTime.ofHours(hours);
  }
}
