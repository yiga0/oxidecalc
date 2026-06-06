/*
 * Copyright (c) 2015-2026 Nolan Peard. MIT License.
 */
package io.github.npeard.oxidecalc;

import java.time.Duration;

/**
 * An immutable oxidation run time, rendered as {@code h:mm:ss}.
 *
 * @param duration the run time; never negative
 */
public record GrowthTime(Duration duration) {

  public GrowthTime {
    if (duration.isNegative()) {
      throw new IllegalArgumentException("growth time cannot be negative: " + duration);
    }
  }

  /** Builds a {@code GrowthTime} from a fractional number of hours. */
  static GrowthTime ofHours(double hours) {
    long totalSeconds = (long) Math.floor(hours * 3600.0);
    return new GrowthTime(Duration.ofSeconds(totalSeconds));
  }

  /** Formats as {@code h:mm:ss} (hours are not zero-padded). */
  @Override
  public String toString() {
    return "%d:%02d:%02d"
        .formatted(duration.toHours(), duration.toMinutesPart(), duration.toSecondsPart());
  }
}
