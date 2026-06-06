/*
 * Copyright (c) 2015-2026 Nolan Peard. MIT License.
 */
package io.github.npeard.oxidecalc;

/**
 * Oxidation ambient (the gas the wafer is grown in) and its Deal-Grove rate parameters.
 *
 * <p>Each ambient carries the two activation energies and the two pre-exponential constants of the
 * linear-parabolic model. The parabolic pre-exponential ({@code c2Base111}) is quoted for the
 * &lt;111&gt; surface orientation; other orientations scale it via {@link Orientation}.
 */
public enum Ambient {
  /** Wet (steam) oxidation. */
  WET("Wet", 0.78, 2.05, 3.86e2, 1.63e8),
  /** Dry (O2) oxidation. */
  DRY("Dry", 1.23, 2.00, 7.72e2, 6.23e6);

  private final String label;
  private final double activationEnergyLinear; // E1, eV
  private final double activationEnergyParabolic; // E2, eV
  private final double preExpLinear; // C1
  private final double preExpParabolic111; // C2 for <111>

  Ambient(
      String label,
      double activationEnergyLinear,
      double activationEnergyParabolic,
      double preExpLinear,
      double preExpParabolic111) {
    this.label = label;
    this.activationEnergyLinear = activationEnergyLinear;
    this.activationEnergyParabolic = activationEnergyParabolic;
    this.preExpLinear = preExpLinear;
    this.preExpParabolic111 = preExpParabolic111;
  }

  /** Human-readable name for the UI. */
  public String label() {
    return label;
  }

  double activationEnergyLinear() {
    return activationEnergyLinear;
  }

  double activationEnergyParabolic() {
    return activationEnergyParabolic;
  }

  double preExpLinear() {
    return preExpLinear;
  }

  double preExpParabolic111() {
    return preExpParabolic111;
  }
}
