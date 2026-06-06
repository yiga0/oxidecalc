# OxideCalc

[![build](https://github.com/npeard/oxidecalc/actions/workflows/build.yml/badge.svg)](https://github.com/npeard/oxidecalc/actions/workflows/build.yml)
[![Java](https://img.shields.io/badge/Java-21-blue.svg)](https://adoptium.net/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

A small Swing desktop calculator for **thermal silicon-dioxide growth times** in
microfabrication. Enter the initial and final oxide thickness, the furnace
temperature, the ambient (wet/dry), and the wafer orientation; OxideCalc returns
the required furnace run time as `h:mm:ss`.

<p align="center">
  <em>Initial Thickness (Å) · Final Thickness (Å) · Temperature (°C) · Orientation · Wet/Dry → Run Time</em>
</p>

## The physics

OxideCalc implements the **Deal-Grove linear-parabolic model** of silicon
oxidation. Oxide thickness `x` and time `t` are related by

```
x^2 / B  +  x / (B/A)  =  t  +  tau
```

where `B` is the parabolic (diffusion-limited) rate constant and `B/A` is the
linear (interface-reaction-limited) rate constant, each with an Arrhenius
temperature dependence. Inverting for the time to grow from `x0` to `x`:

```
t = (x^2 - x0^2) / B  +  (x - x0) / (B/A)
```

The wet/dry rate parameters and the orientation-dependent scaling live in
[`Ambient`](src/main/java/io/github/npeard/oxidecalc/Ambient.java) and
[`Orientation`](src/main/java/io/github/npeard/oxidecalc/Orientation.java); the
calculation itself is in
[`OxideModel`](src/main/java/io/github/npeard/oxidecalc/OxideModel.java).

> Reference: B. E. Deal and A. S. Grove, *General Relationship for the Thermal
> Oxidation of Silicon*, J. Appl. Phys. **36**, 3770 (1965).

## Quick start

OxideCalc uses [pixi](https://pixi.sh) to provision a project-local JDK 21 and
Maven into `./.pixi` — there is no need to install Java or Maven on your system.

```bash
# 1. Install pixi (one time)
curl -fsSL https://pixi.sh/install.sh | bash

# 2. Build, test, and launch
pixi install      # downloads JDK 21 + Maven into ./.pixi
pixi run test     # run the JUnit suite
pixi run run      # build the runnable jar and open the calculator
```

### Tasks

| Command            | What it does                                         |
| ------------------ | ---------------------------------------------------- |
| `pixi run build`   | Package the runnable jar (`target/oxidecalc-1.0.0.jar`) |
| `pixi run test`    | Run the JUnit 5 test suite                           |
| `pixi run verify`  | Format-check (google-java-format), test, and package |
| `pixi run run`     | Build, then launch the Swing app                     |
| `pixi run format`  | Reformat sources with `spotless:apply`               |

Already have a JDK 21 + Maven? You can skip pixi and run `mvn verify` /
`java -jar target/oxidecalc-1.0.0.jar` directly.

## History

OxideCalc began life in 2015 as a DrJava class project used in a cleanroom. This
repository is a modernized public release (`v1.0.0`) of that internal "v2.0"
tool: the Deal-Grove math is unchanged and pinned by golden-value tests, while
the code was repackaged, split into a testable core plus a Swing front end, and
given a modern Maven/pixi build.

## License

[MIT](LICENSE) © 2015-2026 Nolan Peard.
