package Simulation;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.*;

/**
 * An item processing and packing line simulator, based on 2 arrival 
 * probabilities p and q.
 * 
 * @author Akash Parmar
 * @version 18-04-2016
 */

public class SimGUIv2 {
	private JFrame mainFrame;
	private Simulator s;
	private int simTime;
	
	LabelledSlider pSlider = new LabelledSlider("P Value: ", 0.01, 1, 5, 100);
	LabelledSlider qSlider = new LabelledSlider("Q Value: ", 0.01, 1, 5, 100);
	LabelledSlider packingTimeSlider = new LabelledSlider("Packing Time Value: ", 10, 1, 100, 1);
	LabelledSlider proccessingMachine1TimeSlider = new LabelledSlider("Proccessing Time Value: ", 20, 1, 100, 1);
	LabelledSlider proccessingMachine2TimeSlider = new LabelledSlider("Proccessing Time Value: ", 20, 1, 100, 1);
	LabelledSlider proccessingMachine3TimeSlider = new LabelledSlider("Proccessing Time Value: ", 22, 1, 100, 1);
	LabelledSlider numPackersSlider = new LabelledSlider("Number of Packers Value: ", 2, 1, 100, 1);
	LabelledSlider SimPeriodSlider = new LabelledSlider("Simulation Time(seconds): ", 7200, 1, 10000, 1);
	
	
	
	public SimGUIv2(final Simulator s) {
		final int blankSpace = 6;
		this.s = s;
		
		// Step 1: create the components
		JButton closeButton = new JButton();
		JButton runButton = new JButton();
		

		JLabel pLabel = new JLabel();
		JLabel qLabel = new JLabel();
		JLabel packingTimeLabel = new JLabel();
		JLabel proccessingMachine1TimeLabel = new JLabel();
		JLabel proccessingMachine2TimeLabel = new JLabel();
		JLabel proccessingMachine3TimeLabel = new JLabel();
		JLabel numPackersLabel = new JLabel();
		JLabel simPeriodLabel = new JLabel();
		
	
		// Step 2: Set the properties of the components
		closeButton.setText("Close");
		runButton.setText("Run");
		
		
		pLabel.setText("P Value: ");
		qLabel.setText("Q Value: ");
		packingTimeLabel.setText("Packing Time Value: ");
		proccessingMachine1TimeLabel.setText("Proccessing Cheese Time Value: ");
		proccessingMachine2TimeLabel.setText("Proccessing BlueCheese Time Value: ");
		proccessingMachine3TimeLabel.setText("Proccessing SoupPowder Time Value: ");
		numPackersLabel.setText("Number of Packing Machines: ");
		simPeriodLabel.setText("Simulation Period: ");
		
		// Step 3: Create containers to hold the components
		mainFrame = new JFrame("Food Simulation");
		mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		mainFrame.setPreferredSize(new Dimension(500, 650));
		mainFrame.setMinimumSize(new Dimension(500, 650));
		
		JPanel commandBox = new JPanel();
		
		JPanel infoBox = new JPanel();
		
		// Step 4: Specify LayoutManagers
		mainFrame.setLayout(new BorderLayout());
		((JPanel)mainFrame.getContentPane()).setBorder(new 
				EmptyBorder(blankSpace, blankSpace, blankSpace, blankSpace));
		commandBox.setLayout(new FlowLayout());
		infoBox.setLayout(new GridLayout(9,2));
		
		// Step 5: Add components to containers 
		commandBox.add(closeButton);
		commandBox.add(runButton);
		
		infoBox.add(pLabel);
		infoBox.add(pSlider);
		infoBox.add(qLabel);
		infoBox.add(qSlider);
		infoBox.add(packingTimeLabel);
		infoBox.add(packingTimeSlider);
		infoBox.add(proccessingMachine1TimeLabel);
		infoBox.add(proccessingMachine1TimeSlider);
		infoBox.add(proccessingMachine2TimeLabel);
		infoBox.add(proccessingMachine2TimeSlider);
		infoBox.add(proccessingMachine3TimeLabel);
		infoBox.add(proccessingMachine3TimeSlider);
		infoBox.add(numPackersLabel);
		infoBox.add(numPackersSlider);
		infoBox.add(simPeriodLabel);
		infoBox.add(SimPeriodSlider);


		
	
		
		mainFrame.add(commandBox, BorderLayout.SOUTH);
		mainFrame.add(infoBox, BorderLayout.NORTH);

		
		// Step 6: Arrange to handle events in the user interface
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exitApp();
			}
		});  
		
		closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exitApp();
			}
		});
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setP();
				setQ();
				setSimPeriod();
				setNumPackers();
				s.initialiseSystem();
				setProcessTime();
				setPackingTime();
				runSim();
				System.exit(0);
			}
		});
		
		// Step 7: Display the GUI
		mainFrame.pack();
		mainFrame.setVisible(true);
	}
	
	
	private void exitApp() {
		// Display confirmation dialog before exiting application
		int response = JOptionPane.showConfirmDialog(mainFrame, 
				"Do you really want to quit?",
				"Quit?",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (response == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		
		// Don't quit
	}
	
	private void runSim() {
		s.simulate(simTime);
		
	}
	
	private void setP() {
		double value = pSlider.getValue();
		s.setP(value);
	}
	
	
	private void setQ() {
		double value = qSlider.getValue();
		s.setQ(value);
	}
	
	private void setPackingTime() {
		Double value = packingTimeSlider.getValue();
		int i = value.intValue();
		s.setPackingTime(i);
	}
	
	private void setNumPackers() {
		Double value = numPackersSlider.getValue();
		int i = value.intValue();
		s.setNumPackers(i); 
	}
	
	private void setSimPeriod() {
		Double value = SimPeriodSlider.getValue();
		int i = value.intValue();
		simTime = i;
	}
	
	private void setProcessTime() {
		Double value = proccessingMachine1TimeSlider.getValue();
		Double value1 = proccessingMachine2TimeSlider.getValue();
		Double value2 = proccessingMachine3TimeSlider.getValue();
		int a = value.intValue();
		int b = value1.intValue(); 
		int c = value2.intValue(); 
		s.setProccessTime(a, b, c);
	}

}
