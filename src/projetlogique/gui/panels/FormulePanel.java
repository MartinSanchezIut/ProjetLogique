package projetlogique.gui.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FormulePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6335221551069472985L;
	private static JTextField textField;
	
	public FormulePanel() {
		
		Color marron = new Color(196, 147, 105);
		setBackground(marron);
		
		setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NORTH;
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(400, 30));
		constraints.gridx = 0;
		constraints.gridy = 0;
		add(textField, constraints);
		
		JButton bouton = new JButton("Valider");
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 10, 0, 0);
		add(bouton, constraints);
		
		
		JTextArea formulesDeveloppees = new JTextArea();
		formulesDeveloppees.setEditable(false);
		formulesDeveloppees.setBackground(new Color(190, 140, 100));
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(10, 0, 0, 0);
		add(formulesDeveloppees, constraints);
		//formulesDeveloppees.viewToModel2D(MouseInfo.getPointerInfo().getLocation());
		
		bouton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				formulesDeveloppees.setText(textField.getText());
				//TODO RESET POINTS
			}
		});
	}
	
	
	public static void setTextInTextField(String text) {
		textField.setText(text);
	}
	

}
