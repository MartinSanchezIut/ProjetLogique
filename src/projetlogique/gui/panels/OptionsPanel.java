package projetlogique.gui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class OptionsPanel extends JPanel{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2562596013158468156L;
	private static JTextArea points;
	
	public OptionsPanel() {
		
		setBackground(new Color(196, 147, 105));
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NORTH;
		
		
		JButton bouton = new JButton("Formule Aléatoire");
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(bouton, constraints);
		
		bouton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				FormulePanel.setTextInTextField(getRandomFormule());
			}
		});
		
		
		points = new JTextArea("Point(s) : 0");
		points.setBackground(new Color(196, 147, 105));
		constraints.gridx = 0;
		constraints.gridy = 1;
		add(points, constraints);
		
	}
	
	
	
	
	private String getRandomFormule() {
		//TODO to implement
		return null;
	}
	
	
	
	public static int getPoints() {
		return Integer.parseInt(points.getText().replace("Point(s) : ", ""));
	}
	
	
	public static void setPoints(int points) {
		OptionsPanel.points.setText("Point(s) : "+points);
	}

}
