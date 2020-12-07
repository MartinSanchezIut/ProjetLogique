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
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import projetlogique.formules.Formule;
import projetlogique.formules.Operateurs;
import projetlogique.formules.SplitFormule;

public class FormulePanel extends JPanel {
	
	private static final long serialVersionUID = 6335221551069472985L;
	private static JTextField textField;
	private JLayeredPane pane;
	private GridBagConstraints paneConstraints;
	private Map<JTextArea , Integer> textAreaToGridX = new HashMap<>();
	private Map<JTextArea , Integer> textAreaToGridY = new HashMap<>();
	private static int maxBranchs = 0;
	private Map<JTextArea, JTextArea> childrenToParent = new HashMap<>();
	private JTextArea rootArea;
	
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
				
				maxBranchs = 100; /*countSymbolesPropositionnels(textField.getText())*2; *///x2 au cas où ce soit seulement des OU
				
				for ( Component comp : pane.getComponents() ) {
					pane.remove(comp);
				}
				
				JTextArea formulesDeveloppees = new JTextArea();
				formulesDeveloppees.setEditable(false);
				formulesDeveloppees.setText(textField.getText());
				formulesDeveloppees.setBounds(5, 30, 990, 30);
				formulesDeveloppees.setBackground(new Color(190, 140, 100));
				if ( maxBranchs % 2 == 1 )
					paneConstraints.gridx = maxBranchs/2+1;
				else 
					paneConstraints.gridx = maxBranchs/2;
				
				paneConstraints.gridy = 0;
				paneConstraints.insets = new Insets(10, 0, 0, 0);
				pane.add(formulesDeveloppees, paneConstraints);
				
				addMouseListener(formulesDeveloppees);
				
				textAreaToGridX.clear();
				textAreaToGridX.put(formulesDeveloppees, paneConstraints.gridx);
				textAreaToGridY.put(formulesDeveloppees, paneConstraints.gridy);
				rootArea = formulesDeveloppees;
				
				pane.revalidate();
				pane.repaint();
				
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
			public void mouseReleased(MouseEvent e) {}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			
			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				
				if ( e.getComponent() instanceof JTextArea) {
					SplitFormule formules = new Formule(((JTextArea) e.getComponent()).getText()).split();
					//System.out.println("maxBranchs: "+ maxBranchs);
					
					createChildren(formules, (JTextArea) e.getComponent());
					
					
					//fix GUI and add listener to the textArea generated
					pane.revalidate();
					
					
					//remove listener for this area
					for ( MouseListener m : e.getComponent().getMouseListeners() ) {
						e.getComponent().removeMouseListener(m);
					}
				}
				
			}
		});
	}
	
	
	
	
	/*private int countSymbolesPropositionnels(String str) {
		int count = 0;
		for ( int i = 0 ; i < str.length() ; i++ ) {
			if ( str.charAt(i) == '∧' || str.charAt(i) == '∨' || str.charAt(i) == '¬' || ((int) str.charAt(i)) == 8594) {
				count++;
			}
		}
		return count;
	}*/
	
	
	/*
	 * Get the nearest left grid x by going up in the tree
	 */
	private int getNearestLeftGridX(JTextArea directFather) {
		
		//System.out.println("-------NEAREST LEFT----------");
		
		JTextArea actual = directFather;
		boolean gridXHasOnlyReduced = true;
		
		while ( childrenToParent.get(actual) != null ) { //tant qu'il y a un père
			if ( textAreaToGridX.get(actual) < textAreaToGridX.get(childrenToParent.get(actual)) ) {
				actual = childrenToParent.get(actual);
			} else {
				actual = childrenToParent.get(actual);
				gridXHasOnlyReduced = false;
				break;
			}
		}
		
		/*System.out.println("actual = directFather: "+ actual.equals(directFather));
		System.out.println("actual = rootArea : "+ actual.equals(rootArea) +" && hasOnlyReduced: "+ gridXHasOnlyReduced);
		
		System.out.println("---------------------");*/
		if ( actual.equals(directFather) || (actual.equals(rootArea) && gridXHasOnlyReduced) )
			return 0;
		
		//System.out.println("nearestLeftGridX: "+textAreaToGridX.get(actual));
		return textAreaToGridX.get(actual);
	}
	
	/*
	 * Get the nearest right grid x by going up in the tree
	 */
	private int getNearestRightGridX(JTextArea directFather) {
		
		//System.out.println("-------NEAREST RIGHT----------");
		
		JTextArea actual = directFather;
		boolean gridXHasOnlyReduced = true;
		
		while ( childrenToParent.get(actual) != null ) { //tant qu'il y a un père
			if ( textAreaToGridX.get(childrenToParent.get(actual)) < textAreaToGridX.get(actual) ) {
				actual = childrenToParent.get(actual);
			} else {
				actual = childrenToParent.get(actual);
				gridXHasOnlyReduced = false;
				//System.out.println("broke at the parent of : "+actual.getText());
				break;
			}
		}
		
		/*System.out.println("actual = directFather: "+ actual.equals(directFather));
		System.out.println("actual = rootArea : "+ actual.equals(rootArea) +" && hasOnlyReduced: "+ gridXHasOnlyReduced);
		
		System.out.println("---------------------");*/
		if ( actual.equals(directFather) || (actual.equals(rootArea) && gridXHasOnlyReduced) )
			return maxBranchs;
		
		return textAreaToGridX.get(actual);
	}
	
	
	
	/*
	 * Add a textArea on the bottom right of the father
	 */
	private void createRightChild(String text, JTextArea father) {
		
		int fatherGridX = textAreaToGridX.get(father);
		
		JTextArea secondSplit = new JTextArea();
		secondSplit.setEditable(false);
		secondSplit.setText(text);
		secondSplit.setBounds(5, 30, 990, 30);
		secondSplit.setBackground(new Color(190, 140, 100));
		paneConstraints.gridx = (int) (Math.ceil((getNearestRightGridX(father) - fatherGridX) / 2.0 ) + fatherGridX);
		paneConstraints.gridy = textAreaToGridY.get(father)+1;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(secondSplit, paneConstraints);
		
		
		textAreaToGridX.put(secondSplit, paneConstraints.gridx);
		textAreaToGridY.put(secondSplit, paneConstraints.gridy);
		childrenToParent.put(secondSplit, father);
		addMouseListener(secondSplit);
		
		
		//System.out.println("RIGHT CHILD /// fatherGridX: "+ fatherGridX + " gridX: "+ paneConstraints.gridx + " NearestRight: "+ getNearestRightGridX(father) );
		
		
	}
	
	/*
	 * Add a textArea on the bottom left of the father
	 */
	private void createLeftChild(String text, JTextArea father) {
		
		int fatherGridX = textAreaToGridX.get(father);
		
		JTextArea firstSplit = new JTextArea();
		firstSplit.setEditable(false);
		firstSplit.setText(text);
		firstSplit.setBounds(5, 30, 990, 30);
		firstSplit.setBackground(new Color(190, 140, 100));
		paneConstraints.gridx = (int) (Math.ceil((fatherGridX - getNearestLeftGridX(father)) / 2) + getNearestLeftGridX(father));
		paneConstraints.gridy = textAreaToGridY.get(father)+1;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(firstSplit, paneConstraints);
		
		
		textAreaToGridX.put(firstSplit, paneConstraints.gridx);
		textAreaToGridY.put(firstSplit, paneConstraints.gridy);
		childrenToParent.put(firstSplit, father);
		addMouseListener(firstSplit);
		
	}
	
	/*
	 * Add a textArea below the fatherGridX
	 */
	private void createAloneChild(SplitFormule formule, JTextArea father) {
		
		int fatherGridX = textAreaToGridX.get(father);
		
		JTextArea split = new JTextArea();
		split.setEditable(false);
		split.setText(formule.getF1().toString()+"\n"+formule.getF2().toString());
		split.setBounds(5, 30, 990, 30);
		split.setBackground(new Color(190, 140, 100));
		paneConstraints.gridx = fatherGridX;
		paneConstraints.gridy = textAreaToGridY.get(father)+1;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(split, paneConstraints);
		
		
		textAreaToGridX.put(split, paneConstraints.gridx);
		textAreaToGridY.put(split, paneConstraints.gridy);
		childrenToParent.put(split, father);
		addMouseListener(split);
	}
	
	
	
	
	private void createChildren(SplitFormule formule, JTextArea father) {
		if ( formule.getOp() == Operateurs.AND ) {
			createAloneChild(formule, father);
		}
		else {
			createLeftChild(formule.getF1().toString(), father);
			createRightChild(formule.getF2().toString(), father);
		}
			
	}
	
	
	
	
	
	
	

}
