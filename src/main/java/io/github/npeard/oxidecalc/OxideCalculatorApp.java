/*
 * Copyright (c) 2015-2026 Nolan Peard. MIT License.
 */
package io.github.npeard.oxidecalc;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Swing desktop front end for the {@link OxideModel} Deal-Grove calculator.
 *
 * <p>The user enters initial/final oxide thickness (angstroms) and furnace temperature (degrees
 * Celsius), picks the ambient and crystal orientation, and the app reports the required run time as
 * {@code h:mm:ss}.
 */
public final class OxideCalculatorApp extends JFrame {

  // Valid furnace range for this process, in degrees Celsius.
  private static final int MIN_TEMPERATURE_C = 700;
  private static final int MAX_TEMPERATURE_C = 1200;

  private final JTextField initialThicknessField = new JTextField("50");
  private final JTextField finalThicknessField = new JTextField("10000");
  private final JTextField temperatureField = new JTextField("1050");

  private final JRadioButton orient100 = new JRadioButton("100", true);
  private final JRadioButton orient110 = new JRadioButton("110");
  private final JRadioButton orient111 = new JRadioButton("111");

  private final JRadioButton wet = new JRadioButton("Wet", true);
  private final JRadioButton dry = new JRadioButton("Dry");

  private final JLabel timeLabel = new JLabel(" ");

  public OxideCalculatorApp() {
    super("Oxide Growth Calculator");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setContentPane(buildPanel());
    setSize(new Dimension(260, 520));
    setLocationByPlatform(true);
  }

  private JPanel buildPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    addField(panel, "Initial Thickness (Å)", initialThicknessField);
    addField(panel, "Final Thickness (Å)", finalThicknessField);
    addField(panel, "Temperature (°C)", temperatureField);

    ButtonGroup orientation = new ButtonGroup();
    orientation.add(orient100);
    orientation.add(orient110);
    orientation.add(orient111);
    addRadioRow(panel, "Orientation", orient100, orient110, orient111);

    ButtonGroup ambient = new ButtonGroup();
    ambient.add(wet);
    ambient.add(dry);
    addRadioRow(panel, "Wet/Dry", wet, dry);

    addCentered(panel, new JLabel("Run Time (h:mm:ss)"));
    panel.add(Box.createRigidArea(new Dimension(0, 3)));
    timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    timeLabel.setFont(panel.getFont().deriveFont(35.0f));
    panel.add(timeLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    JButton calcButton = new JButton("Calculate");
    calcButton.setMaximumSize(new Dimension(100, 30));
    calcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    calcButton.addActionListener(e -> calculate());
    panel.add(calcButton);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    getRootPane().setDefaultButton(calcButton);
    return panel;
  }

  private static void addField(JPanel panel, String label, JTextField field) {
    addCentered(panel, new JLabel(label));
    panel.add(Box.createRigidArea(new Dimension(0, 3)));
    field.setMaximumSize(new Dimension(75, 20));
    field.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(field);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));
  }

  private static void addRadioRow(JPanel panel, String label, JRadioButton... buttons) {
    addCentered(panel, new JLabel(label));
    panel.add(Box.createRigidArea(new Dimension(0, 3)));
    JPanel row = new JPanel();
    row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
    for (JRadioButton button : buttons) {
      row.add(button);
    }
    panel.add(row);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));
  }

  private static void addCentered(JPanel panel, JComponent component) {
    component.setAlignmentX(Component.CENTER_ALIGNMENT);
    panel.add(component);
  }

  private void calculate() {
    Integer initial = parsePositive(initialThicknessField);
    Integer fnal = parsePositive(finalThicknessField);
    Integer temperature = parseInt(temperatureField);
    if (initial == null || fnal == null || temperature == null) {
      return;
    }
    if (temperature < MIN_TEMPERATURE_C || temperature > MAX_TEMPERATURE_C) {
      timeLabel.setText("T?");
      return;
    }

    Ambient ambient = wet.isSelected() ? Ambient.WET : Ambient.DRY;
    Orientation orientation =
        orient100.isSelected()
            ? Orientation.ORI_100
            : orient110.isSelected() ? Orientation.ORI_110 : Orientation.ORI_111;

    GrowthTime time = OxideModel.growthTime(ambient, orientation, temperature, initial, fnal);
    timeLabel.setText(time.toString());
  }

  private Integer parseInt(JTextField field) {
    try {
      return Integer.valueOf(field.getText().trim());
    } catch (NumberFormatException e) {
      timeLabel.setText("?");
      return null;
    }
  }

  private Integer parsePositive(JTextField field) {
    Integer value = parseInt(field);
    if (value != null && value < 0) {
      timeLabel.setText("?");
      return null;
    }
    return value;
  }

  /** Launches the calculator. */
  public static void main(String[] args) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (Exception ignored) {
      // Fall back to the default look and feel.
    }
    SwingUtilities.invokeLater(() -> new OxideCalculatorApp().setVisible(true));
  }
}
