package projetlogique.gui;

import java.awt.BorderLayout;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.border.EmptyBorder;

import projetlogique.gui.panels.FormulePanel;
import projetlogique.gui.panels.OptionsPanel;

public class Fenetre extends JFrame {
	
	
	private static final long serialVersionUID = -3560404405310867980L;

	public Fenetre() {
		super("Affichage d'arbres");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		FormulePanel formulePanel = new FormulePanel();
		formulePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		OptionsPanel optionsPanel = new OptionsPanel();
		optionsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		Panel panel = new Panel();
		panel.setLayout(new BorderLayout());
		panel.add(formulePanel, BorderLayout.CENTER);
		panel.add(optionsPanel, BorderLayout.EAST);
		
		setContentPane(panel);
		pack();
		
		setVisible(true);
	}
	
	
}
