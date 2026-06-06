/*
 * Copyright (c) 2015-2026 Nolan Peard. MIT License.
 */
package io.github.npeard.oxidecalc;

/**
 * Silicon crystal surface orientation.
 *
 * <p>Orientation changes the number of available bonds at the surface and therefore the parabolic
 * (interface-reaction-limited) growth rate. Rates are expressed relative to the &lt;111&gt;
 * surface: &lt;100&gt; oxidizes about 1/1.68 as fast, and &lt;110&gt; sits between the two.
 */
public enum Orientation {
  /** &lt;100&gt; surface. */
  ORI_100("100", 1.0 / 1.68),
  /** &lt;110&gt; surface. */
  ORI_110("110", 1.45 / 1.68),
  /** &lt;111&gt; surface (reference). */
  ORI_111("111", 1.0);

  private final String label;
  private final double relativeRate;

  Orientation(String label, double relativeRate) {
    this.label = label;
    this.relativeRate = relativeRate;
  }

  /** Miller-index label for the UI, e.g. {@code "100"}. */
  public String label() {
    return label;
  }

  /** Parabolic-rate multiplier relative to the &lt;111&gt; surface. */
  double relativeRate() {
    return relativeRate;
  }
}
