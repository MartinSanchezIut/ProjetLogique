package projetlogique.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import projetlogique.formules.Formule;

public class FormulePanel extends JPanel {
	
	private static final long serialVersionUID = 6335221551069472985L;
	private static JTextField textField;
	private JLayeredPane pane;
	private GridBagConstraints paneConstraints;
	private static int gridY = 1;
	
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
		
		
		pane = new JLayeredPane();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(10, 0, 0, 0);
		pane.setPreferredSize(new Dimension(1000, 500));
		pane.setBorder(BorderFactory.createTitledBorder("Arbre"));
		
		pane.setLayout(new GridBagLayout());
		paneConstraints = new GridBagConstraints();
		paneConstraints.fill = GridBagConstraints.NORTH;
		add(pane, constraints);
		
		
		bouton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				for ( Component comp : pane.getComponents() ) {
					pane.remove(comp);
				}
				
				JTextArea formulesDeveloppees = new JTextArea();
				formulesDeveloppees.setEditable(false);
				formulesDeveloppees.setText(textField.getText());
				formulesDeveloppees.setBounds(5, 30, 990, 30);
				formulesDeveloppees.setBackground(new Color(190, 140, 100));
				paneConstraints.gridx = 0;
				paneConstraints.gridy = 0;
				paneConstraints.insets = new Insets(10, 0, 0, 0);
				pane.add(formulesDeveloppees, paneConstraints);
				
				addMouseListener(formulesDeveloppees);
				
				//pane.updateUI();
				pane.revalidate();
				pane.repaint();
				/*updateUI();
				repaint();
				revalidate();
				doLayout();*/
				
				//pane.doLayout();
				
				//TODO RESET POINTS
			}
		});
		
	}
	
	
	public static void setTextInTextField(String text) {
		textField.setText(text);
	}
	
	
	
	private void addMouseListener(JTextArea area) {
		area.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				JTextArea formuleSplitted = new JTextArea();
				formuleSplitted.setEditable(false);
				formuleSplitted.setText(textField.getText());
				formuleSplitted.setBounds(5, 30, 990, 30);
				formuleSplitted.setBackground(new Color(190, 140, 100));
				paneConstraints.gridx = 0;
				paneConstraints.gridy = gridY;
				gridY++;
				paneConstraints.insets = new Insets(10, 0, 0, 0);
				pane.add(formuleSplitted, paneConstraints);
				
				pane.revalidate();
				addMouseListener(formuleSplitted);
				
				for ( MouseListener m : e.getComponent().getMouseListeners() ) {
					e.getComponent().removeMouseListener(m);
				}
			}
		});
	}
	

}
