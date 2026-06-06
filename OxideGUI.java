//Copyright (c) 2015, Nolan Peard, All rights reserved.

import java.lang.Integer;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.BoxLayout;
import javax.swing.Box;
//import java.util.Calendar;

public class OxideGUI extends JFrame implements ActionListener
{
    private static int initialThickness;
    private static int finalThickness;
    private static int temperature;
    
    //The main panel containing the components
    private JPanel panel;
    private JPanel steamPanel;
    private JPanel orientationPanel;
    
    //Initial Thickness text label
    private JLabel initialText;
    //Final Thickness text label
    private JLabel finalText;
    //Temperature text label
    private JLabel temperatureText;
    //Orientation text label
    private JLabel orientationText;
    //Steam text label
    private JLabel steamText;
    //Time text label
    private JLabel timeText;
    //Current time text label
    //private JLabel currentTimeText;
    //Time Out text label
    //private JLabel timeOutText;
    
    //Initial Thickness field
    private JTextField initialThicknessField;
    //Final Thickness field
    private JTextField finalThicknessField;
    //Temperature field
    private JTextField temperatureField;
    //Orientation Buttons
    private JRadioButton orient100Button;
    private JRadioButton orient110Button;
    private JRadioButton orient111Button;
    //Steam Buttons
    private JRadioButton wetButton;
    private JRadioButton dryButton;
    //Time
    private JLabel time;
    //Current time
    //private JLabel currentTime;
    //private Calendar current;
    //Time Out
    //private JLabel timeOut;
    //private Calendar out;
    
    //Calculate time button
    private JButton calcButton;
    //---------------------------------------------------------------------------------------------------------------------
    public OxideGUI()
    {
        initDisplay();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        repaint();
    }
    
    public void displayCalc()
    {
        java.awt.EventQueue.invokeLater(new Runnable(){ public void run(){ setVisible(true);}});
    }
    
    //Panel Initialization
    private void initDisplay()
    {
        panel = new JPanel() { public void paintComponent(Graphics g){ super.paintComponent(g);}};
        setTitle("Oxide Growth Calculator");
        this.setSize(new Dimension(250, 500));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        //Initial Thickness Label Initialization
        initialText = new JLabel("Initial Thickness (\u00C5)");
        panel.add(initialText);
        //initialText.setMaximumSize(new Dimension(125, 15));
        initialText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        //Initial Thickness Field
        initialThicknessField = new JTextField("50");
        panel.add(initialThicknessField);
        initialThicknessField.setMaximumSize(new Dimension(75, 20));
        initialThicknessField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        //Final Thickness Label Initialization
        finalText = new JLabel("Final Thickness (\u00C5)");
        panel.add(finalText);
        //finalText.setMaximumSize(new Dimension(125, 15));
        finalText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        //Final Thickness Field
        finalThicknessField = new JTextField("10000");
        panel.add(finalThicknessField);
        finalThicknessField.setMaximumSize(new Dimension(75, 20));
        finalThicknessField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        //Temperature Label Initialization
        temperatureText = new JLabel("Temperature (\u00B0C)");
        panel.add(temperatureText);
        //temperatureText.setMaximumSize(new Dimension(125, 15));
        temperatureText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        //Temperature Field
        temperatureField = new JTextField("1050");
        panel.add(temperatureField);
        temperatureField.setMaximumSize(new Dimension(75, 20));
        temperatureField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        //Orientation Label Initialization
        orientationText = new JLabel("Orientation");
        panel.add(orientationText);
        //orientationText.setMaximumSize(new Dimension(125, 15));
        orientationText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        
        //Orientation Button Initialization
        orientationPanel = new JPanel();
        orientationPanel.setLayout(new BoxLayout(orientationPanel, BoxLayout.X_AXIS));
        
        orient100Button = new JRadioButton("100");
        orient100Button.setSelected(true);
        orient100Button.addActionListener(this);
        orientationPanel.add(orient100Button);
        
        orient110Button = new JRadioButton("110");
        orient110Button.addActionListener(this);
        orientationPanel.add(orient110Button);
        
        orient111Button = new JRadioButton("111");
        orient111Button.addActionListener(this);
        orientationPanel.add(orient111Button);
        panel.add(orientationPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        //Steam Label Initialization
        steamText = new JLabel("Wet/Dry");
        panel.add(steamText);
        //steamText.setMaximumSize(new Dimension(125, 15));
        steamText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        
        //Steam Button Initialization
        steamPanel = new JPanel();
        steamPanel.setLayout(new BoxLayout(steamPanel, BoxLayout.X_AXIS));
        
        wetButton = new JRadioButton("Wet");
        wetButton.setSelected(true);
        wetButton.addActionListener(this);
        steamPanel.add(wetButton);
        
        dryButton = new JRadioButton("Dry");
        dryButton.addActionListener(this);
        steamPanel.add(dryButton);
        panel.add(steamPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        //Time Label Initialization
        timeText = new JLabel("Run Time (hh:mm:ss)");
        panel.add(timeText);
        //timeText.setMaximumSize(new Dimension(125, 15));
        timeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createRigidArea(new Dimension(0, 3)));
        //Final Time Field
        time = new JLabel();
        panel.add(time);
        //time.setMaximumSize(new Dimension(75, 15));
        time.setAlignmentX(Component.CENTER_ALIGNMENT);
        time.setFont(panel.getFont().deriveFont((float)35.0));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        //  //Current Time Label Initialization
        //  currentTimeText = new JLabel("Current Time");
        //  panel.add(currentTimeText);
        //  //currentTimeText.setMaximumSize(new Dimension(125, 15));
        //  currentTimeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        //  panel.add(Box.createRigidArea(new Dimension(0, 3)));
        //  //Current Time Field
        //  currentTime = new JLabel();
        //  panel.add(currentTime);
        //  //currentTime.setMaximumSize(new Dimension(180, 15));
        //  currentTime.setAlignmentX(Component.CENTER_ALIGNMENT);
        //  panel.add(Box.createRigidArea(new Dimension(0, 15)));
        //
        //  //Time Out Label Initialization
        //  timeOutText = new JLabel("Time Out");
        //  panel.add(timeOutText);
        //  //timeOutText.setMaximumSize(new Dimension(125, 15));
        //  timeOutText.setAlignmentX(Component.CENTER_ALIGNMENT);
        //  panel.add(Box.createRigidArea(new Dimension(0, 3)));
        //  //Time Out Field
        //  timeOut = new JLabel();
        //  panel.add(timeOut);
        //  //timeOut.setMaximumSize(new Dimension(180, 15));
        //  timeOut.setAlignmentX(Component.CENTER_ALIGNMENT);
        //  panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        //Calculate Button Initialization
        calcButton = new JButton();
        calcButton.setText("Calculate");
        panel.add(calcButton);
        calcButton.setMaximumSize(new Dimension(100, 30));
        calcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        calcButton.setEnabled(true);
        calcButton.addActionListener(this);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        //pack();
        getContentPane().add(panel);
        getRootPane().setDefaultButton(calcButton);
        panel.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(calcButton) || e.getSource().equals(KeyEvent.VK_ENTER)) {
            //Error-checking
            if(!(isTempInRange(Integer.parseInt(temperatureField.getText())))){
                temperatureField.setText("1050");
            }
            
            //Set the values for initial and final thickness and temperature
            initialThickness = Integer.parseInt(initialThicknessField.getText());
            finalThickness = Integer.parseInt(finalThicknessField.getText());
            temperature = Integer.parseInt(temperatureField.getText());
            
            OxideTime mainTime; //Declares an OxideTime object to be used in calculating a run time
            //TimeUtility rampTime; //Declares a TimeUtility object to be used for setting the Time Out field
            
            if(wetButton.isSelected()){
                
                //     if(temperature <= 925) { rampTime = new TimeUtility(4, 15, 0); }
                //     else if(temperature > 925 && temperature <= 975) { rampTime = new TimeUtility(4, 30, 0); }
                //     else if(temperature > 975 && temperature <= 1025) { rampTime = new TimeUtility(5, 30, 0); }
                //     else if(temperature > 1025 && finalThickness < 40000) { rampTime = new TimeUtility(5, 45, 0); }
                //     else { rampTime = new TimeUtility(7, 15, 0); }
                
                if(orient100Button.isSelected()){
                    mainTime = new OxideTime(OxideTime.E1wet, OxideTime.E2wet, OxideTime.C1wet, OxideTime.C2wet100);
                }
                else if(orient110Button.isSelected()){
                    mainTime = new OxideTime(OxideTime.E1wet, OxideTime.E2wet, OxideTime.C1wet, OxideTime.C2wet110);
                }
                else{
                    mainTime = new OxideTime(OxideTime.E1wet, OxideTime.E2wet, OxideTime.C1wet, OxideTime.C2wet111);
                }
            }
            else{
                
                //     if(temperature <= 925) { rampTime = new TimeUtility(4, 15, 0); }
                //     else if(temperature > 925 && temperature <= 975) { rampTime = new TimeUtility(4, 30, 0); }
                //     else if(temperature > 975 && temperature <= 1025) { rampTime = new TimeUtility(5, 15, 0); }
                //     else { rampTime = new TimeUtility(6, 0, 0); }
                
                if(orient100Button.isSelected()){
                    mainTime = new OxideTime(OxideTime.E1dry, OxideTime.E2dry, OxideTime.C1dry, OxideTime.C2dry100);
                }
                else if(orient110Button.isSelected()){
                    mainTime = new OxideTime(OxideTime.E1dry, OxideTime.E2dry, OxideTime.C1dry, OxideTime.C2dry110);
                }
                else{
                    mainTime = new OxideTime(OxideTime.E1dry, OxideTime.E2dry, OxideTime.C1dry, OxideTime.C2dry111);
                }
            }
            
            //Remember to convert temperature to Kelvin
            time.setText((mainTime.calculateTime(temperature + 273, initialThickness, finalThickness)).toString());
            
            //   //Calculating and setting the values for current time and time out
            //   current = Calendar.getInstance();
            //   out = Calendar.getInstance();
            //   out.add(Calendar.SECOND, (mainTime.calculateTime(temperature + 273, initialThickness, finalThickness)).getSeconds());
            //   out.add(Calendar.SECOND, rampTime.getSeconds());
            //   out.add(Calendar.MINUTE, (mainTime.calculateTime(temperature + 273, initialThickness, finalThickness)).getMinutes());
            //   out.add(Calendar.MINUTE, rampTime.getMinutes());
            //   out.add(Calendar.HOUR_OF_DAY, (mainTime.calculateTime(temperature + 273, initialThickness, finalThickness)).getHours());
            //   out.add(Calendar.HOUR_OF_DAY, rampTime.getHours());
            //
            //   currentTime.setText(current.getTime().toString());
            //   timeOut.setText(out.getTime().toString());
            
        }
        else if(e.getSource().equals(wetButton)){
            wetButton.setSelected(true);
            dryButton.setSelected(false);
        }
        else if(e.getSource().equals(dryButton)){
            wetButton.setSelected(false);
            dryButton.setSelected(true);
        }
        else if(e.getSource().equals(orient100Button)){
            orient100Button.setSelected(true);
            orient110Button.setSelected(false);
            orient111Button.setSelected(false);
        }
        else if(e.getSource().equals(orient110Button)){
            orient100Button.setSelected(false);
            orient110Button.setSelected(true);
            orient111Button.setSelected(false);
        }
        else if(e.getSource().equals(orient111Button)){
            orient100Button.setSelected(false);
            orient110Button.setSelected(false);
            orient111Button.setSelected(true);
        }
    }
    
    //Error-checking on Temperature
    public boolean isTempInRange(int temperature)
    {
        if(temperature < 700 || temperature > 1200){
            return false;
        }
        else{
            return true;
        }
    }
}


