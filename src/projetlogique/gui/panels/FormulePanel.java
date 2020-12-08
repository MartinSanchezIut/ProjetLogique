package projetlogique.gui.panels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import projetlogique.Main;
import projetlogique.formules.Formule;
import projetlogique.formules.Operateur;
import projetlogique.formules.SplitFormule;
import projetlogique.score.Score;

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

	public static Color marron = new Color(179,229,255);

	public FormulePanel() {
		
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
		pane.setPreferredSize(new Dimension(1500, 500));
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
				formulesDeveloppees.setBackground(marron);
				if ( maxBranchs % 2 == 1 )
					paneConstraints.gridx = maxBranchs/2+1;
				else 
					paneConstraints.gridx = maxBranchs/2;
				
				paneConstraints.gridy = 0;
				paneConstraints.insets = new Insets(10, 0, 0, 0);
				pane.add(formulesDeveloppees, paneConstraints);
				
				addMouseListener(formulesDeveloppees);
				
				textAreaToGridX.clear();
				textAreaToGridY.clear();
				textAreaToGridX.put(formulesDeveloppees, paneConstraints.gridx);
				textAreaToGridY.put(formulesDeveloppees, paneConstraints.gridy);
				rootArea = formulesDeveloppees;
				
				
				JTextArea separator = new JTextArea();
				separator.setEditable(false);
				separator.setText("-----------------");
				//split.setText(formule.getF1().toString()+"\n\n"+formule.getF2().toString());
				separator.setBounds(5, 30, 990, 30);
				separator.setBackground(marron);
				paneConstraints.gridy = 1;
				paneConstraints.insets = new Insets(10, 0, 0, 0);
				pane.add(separator, paneConstraints);
				
				
				
				pane.revalidate();
				pane.repaint();

				Main.score = new Score();  // J'ai changé ici
				OptionsPanel.setPoints(Main.score.getScore());  // J'ai changé ici
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

			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if ( e.getComponent() instanceof JTextArea) {
					
					if ( e.getButton() == 1 ) { //clic gauche
						
						SplitFormule formules = new Formule(((JTextArea) e.getComponent()).getText()).split();
						//System.out.println("maxBranchs: "+ maxBranchs);
						
						createChildren(formules, (JTextArea) e.getComponent());
						
						
						//fix GUI and add listener to the textArea generated
						pane.revalidate();
						
						
						//remove listener for this area
						for ( MouseListener m : e.getComponent().getMouseListeners() ) {
							e.getComponent().removeMouseListener(m);
						}
						
						Main.score.addScore(100);  // J'ai changé ici
						OptionsPanel.setPoints(Main.score.getScore()); // J'ai changé ici
					}
					
					else if ( e.getButton() == 3 ) { //clic droit
						
						String str = ((JTextArea) e.getComponent()).getText().replaceAll(" ", "");
						
						if ( containsContradiction(str) ) {
							JOptionPane.showMessageDialog(null, "Contradiction trouvée !", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
							
							Font font = new Font("helvetica", Font.PLAIN, 12);
							Map attributes = font.getAttributes();
							attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
							Font newFont = new Font(attributes); 
							((JTextArea) e.getComponent()).setFont(newFont);
							
							for ( MouseListener m : e.getComponent().getMouseListeners() ) {
								e.getComponent().removeMouseListener(m);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Aucune contradiction ici !", "InfoBox", JOptionPane.INFORMATION_MESSAGE);
						}
						
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
		
		System.out.println("-------NEAREST LEFT----------");
		
		JTextArea actual = directFather;
		boolean gridXHasOnlyReduced = true;
		int gridX = textAreaToGridX.get(actual);
		boolean wasAlwaysEquals = false;
		
		while ( childrenToParent.get(actual) != null ) { //tant qu'il y a un père
			if ( textAreaToGridX.get(actual) < textAreaToGridX.get(childrenToParent.get(actual)) ) {
				actual = childrenToParent.get(actual);
			} else {
				actual = childrenToParent.get(actual);
				
				if ( gridX == textAreaToGridX.get(actual) )
					wasAlwaysEquals = true;
				
				gridXHasOnlyReduced = false;
				break;
			}
		}
		
		System.out.println("actual = directFather: "+ actual.equals(directFather));
		System.out.println("actual = rootArea : "+ actual.equals(rootArea) +" && hasOnlyReduced: "+ gridXHasOnlyReduced+ " wasAlwaysEquals: "+ wasAlwaysEquals);
		
		System.out.println("---------------------");
		if ( actual.equals(directFather) || (actual.equals(rootArea) && gridXHasOnlyReduced) || wasAlwaysEquals )
			return 0;
		
		System.out.println("nearestLeftGridX: "+textAreaToGridX.get(actual));
		return textAreaToGridX.get(actual);
	}
	
	/*
	 * Get the nearest right grid x by going up in the tree
	 */
	private int getNearestRightGridX(JTextArea directFather) {
		
		System.out.println("-------NEAREST RIGHT----------");
		
		JTextArea actual = directFather;
		boolean gridXHasOnlyReduced = true;
		int gridX = textAreaToGridX.get(actual);
		boolean wasAlwaysEquals = false;
		
		while ( childrenToParent.get(actual) != null ) { //tant qu'il y a un père
			if ( textAreaToGridX.get(childrenToParent.get(actual)) < textAreaToGridX.get(actual) ) {
				actual = childrenToParent.get(actual);
			} else {
				actual = childrenToParent.get(actual);
				
				if ( gridX == textAreaToGridX.get(actual) )
					wasAlwaysEquals = true;
				
				gridXHasOnlyReduced = false;
				System.out.println("broke at the parent of : "+actual.getText());
				break;
			}
		}
		
		System.out.println("actual = directFather: "+ actual.equals(directFather));
		System.out.println("actual = rootArea : "+ actual.equals(rootArea) +" && hasOnlyReduced: "+ gridXHasOnlyReduced);
		
		System.out.println("---------------------");
		if ( actual.equals(directFather) || (actual.equals(rootArea) && gridXHasOnlyReduced) || wasAlwaysEquals )
			return maxBranchs;
		
		return textAreaToGridX.get(actual);
	}
	
	
	
	/*
	 * Add a textArea on the bottom right of the father
	 */
	private void createRightChild(String text, JTextArea father, int gridY) {
		
		int fatherGridX = textAreaToGridX.get(father);
		int actualGridX = (int) (Math.ceil((getNearestRightGridX(father) - fatherGridX) / 2.0 ) + fatherGridX);
		
		if ( childrenToParent.get(father) != null ) {// grandfather is not null
			
			JTextArea grandFather = childrenToParent.get(father);
			for ( JTextArea txtArea : getChildrenFromFather(grandFather) ) {
				
				if ( !txtArea.equals(father) ) {
					//remove mouselistener from the upper one
					for ( MouseListener m : txtArea.getMouseListeners() ) {
						txtArea.removeMouseListener(m);
					}
					
					//re add into this father
					JTextArea oldFormule = new JTextArea();
					oldFormule.setEditable(false);
					oldFormule.setText(txtArea.getText());
					oldFormule.setBounds(5, 30, 990, 30);
					oldFormule.setBackground(marron);
					paneConstraints.gridx = actualGridX;
					paneConstraints.gridy = gridY++;
					
					paneConstraints.insets = new Insets(10, 0, 0, 0);
					pane.add(oldFormule, paneConstraints);
					
					
					textAreaToGridX.put(oldFormule, paneConstraints.gridx);
					textAreaToGridY.put(oldFormule, paneConstraints.gridy);
					childrenToParent.put(oldFormule, father);
					if ( oldFormule.getText().length() > 2 )
						addMouseListener(oldFormule);
					
					System.out.println(oldFormule.getText()+ " : "+ gridY);
				}
			}
			
		}
		
		
		JTextArea secondSplit = new JTextArea();
		secondSplit.setEditable(false);
		secondSplit.setText(text);
		secondSplit.setBounds(5, 30, 990, 30);
		secondSplit.setBackground(marron);
		paneConstraints.gridx = actualGridX;
		//paneConstraints.gridy = textAreaToGridY.get(father)+2;
		paneConstraints.gridy = gridY;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(secondSplit, paneConstraints);
		
		
		textAreaToGridX.put(secondSplit, paneConstraints.gridx);
		textAreaToGridY.put(secondSplit, paneConstraints.gridy);
		childrenToParent.put(secondSplit, father);
		addMouseListener(secondSplit);
		
		
		JTextArea separator = new JTextArea();
		separator.setEditable(false);
		separator.setText("-----------------");
		//split.setText(formule.getF1().toString()+"\n\n"+formule.getF2().toString());
		separator.setBounds(5, 30, 990, 30);
		separator.setBackground(marron);
		paneConstraints.gridx = fatherGridX;
		paneConstraints.gridy = gridY+1;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(separator, paneConstraints);
		
		
		//System.out.println("RIGHT CHILD /// fatherGridX: "+ fatherGridX + " gridX: "+ paneConstraints.gridx + " NearestRight: "+ getNearestRightGridX(father) );
		
		
	}
	
	/*
	 * Add a textArea on the bottom left of the father
	 */
	private void createLeftChild(String text, JTextArea father, int gridY) {
		
		int fatherGridX = textAreaToGridX.get(father);
		int actualGridX = (int) (Math.ceil((fatherGridX - getNearestLeftGridX(father)) / 2) + getNearestLeftGridX(father));
		
		if ( childrenToParent.get(father) != null ) {// grandfather is not null
			
			JTextArea grandFather = childrenToParent.get(father);
			for ( JTextArea txtArea : getChildrenFromFather(grandFather) ) {
				
				if ( !txtArea.equals(father) ) {
					//remove mouselistener from the upper one
					for ( MouseListener m : txtArea.getMouseListeners() ) {
						txtArea.removeMouseListener(m);
					}
					
					//re add into this father
					JTextArea oldFormule = new JTextArea();
					oldFormule.setEditable(false);
					oldFormule.setText(txtArea.getText());
					oldFormule.setBounds(5, 30, 990, 30);
					oldFormule.setBackground(marron);
					paneConstraints.gridx = actualGridX;
					paneConstraints.gridy = gridY++;
					
					paneConstraints.insets = new Insets(10, 0, 0, 0);
					pane.add(oldFormule, paneConstraints);
					
					
					textAreaToGridX.put(oldFormule, paneConstraints.gridx);
					textAreaToGridY.put(oldFormule, paneConstraints.gridy);
					childrenToParent.put(oldFormule, father);
					if ( oldFormule.getText().length() > 2 )
						addMouseListener(oldFormule);
					
					System.out.println(oldFormule.getText()+ " : "+ gridY);
				}
			}
			
		}
		
		
		JTextArea firstSplit = new JTextArea();
		firstSplit.setEditable(false);
		firstSplit.setText(text);
		firstSplit.setBounds(5, 30, 990, 30);
		firstSplit.setBackground(marron);
		paneConstraints.gridx = actualGridX;
		//paneConstraints.gridy = textAreaToGridY.get(father)+2;
		paneConstraints.gridy = gridY;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(firstSplit, paneConstraints);
		
		
		textAreaToGridX.put(firstSplit, paneConstraints.gridx);
		textAreaToGridY.put(firstSplit, paneConstraints.gridy);
		childrenToParent.put(firstSplit, father);
		addMouseListener(firstSplit);
		
		
		JTextArea separator = new JTextArea();
		separator.setEditable(false);
		separator.setText("-----------------");
		//split.setText(formule.getF1().toString()+"\n\n"+formule.getF2().toString());
		separator.setBounds(5, 30, 990, 30);
		separator.setBackground(marron);
		paneConstraints.gridx = fatherGridX;
		paneConstraints.gridy = gridY+1;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(separator, paneConstraints);
		
		System.out.println("LEFT CHILD /// fatherGridX: "+ fatherGridX + " gridX: "+ paneConstraints.gridx + " NearestRight: "+ getNearestLeftGridX(father) );
		
		
	}
	
	/*
	 * Add a textArea below the fatherGridX
	 */
	private void createAloneChild(SplitFormule formule, JTextArea father) {
		
		int fatherGridX = textAreaToGridX.get(father);
		int fatherGridY = nearestGridY(father)+3/*textAreaToGridY.get(father)+2*/; //add one to add a space between the next text and the father
		
		System.out.println("gridY: "+fatherGridY);
		
		if ( childrenToParent.get(father) != null ) {// grandfather is not null
			
			JTextArea grandFather = childrenToParent.get(father);
			for ( JTextArea txtArea : getChildrenFromFather(grandFather) ) {
				
				if ( !txtArea.equals(father) ) {
					//remove mouselistener from the upper one
					for ( MouseListener m : txtArea.getMouseListeners() ) {
						txtArea.removeMouseListener(m);
					}
					
					//re add into this father
					JTextArea oldFormule = new JTextArea();
					oldFormule.setEditable(false);
					oldFormule.setText(txtArea.getText());
					oldFormule.setBounds(5, 30, 990, 30);
					oldFormule.setBackground(marron);
					paneConstraints.gridx = fatherGridX;
					paneConstraints.gridy = fatherGridY++;
					
					paneConstraints.insets = new Insets(10, 0, 0, 0);
					pane.add(oldFormule, paneConstraints);
					
					
					textAreaToGridX.put(oldFormule, paneConstraints.gridx);
					textAreaToGridY.put(oldFormule, paneConstraints.gridy);
					childrenToParent.put(oldFormule, father);
					if ( oldFormule.getText().length() > 2 )
						addMouseListener(oldFormule);
					
					System.out.println(oldFormule.getText()+ " : "+ fatherGridY);
				}
			}
			
		}
		
		JTextArea firstSplit = new JTextArea();
		firstSplit.setEditable(false);
		firstSplit.setText(formule.getF1().toString());
		//split.setText(formule.getF1().toString()+"\n\n"+formule.getF2().toString());
		firstSplit.setBounds(5, 30, 990, 30);
		firstSplit.setBackground(marron);
		paneConstraints.gridx = fatherGridX;
		paneConstraints.gridy = fatherGridY++;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(firstSplit, paneConstraints);
		
		
		textAreaToGridX.put(firstSplit, paneConstraints.gridx);
		textAreaToGridY.put(firstSplit, paneConstraints.gridy);
		childrenToParent.put(firstSplit, father);
		if ( firstSplit.getText().length() > 2 )
			addMouseListener(firstSplit);
		
		System.out.println(firstSplit.getText()+ " : "+ fatherGridY);
		
		
		
		JTextArea secondSplit = new JTextArea();
		secondSplit.setEditable(false);
		secondSplit.setText(formule.getF2().toString());
		//split.setText(formule.getF1().toString()+"\n\n"+formule.getF2().toString());
		secondSplit.setBounds(5, 30, 990, 30);
		secondSplit.setBackground(marron);
		paneConstraints.gridx = fatherGridX;
		paneConstraints.gridy = fatherGridY++;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(secondSplit, paneConstraints);
		
		
		textAreaToGridX.put(secondSplit, paneConstraints.gridx);
		textAreaToGridY.put(secondSplit, paneConstraints.gridy);
		childrenToParent.put(secondSplit, father);
		if ( secondSplit.getText().length() > 2 )
			addMouseListener(secondSplit);
		
		System.out.println(secondSplit.getText()+ " : "+ fatherGridY);
		
		
		JTextArea separator = new JTextArea();
		separator.setEditable(false);
		separator.setText("-----------------");
		//split.setText(formule.getF1().toString()+"\n\n"+formule.getF2().toString());
		separator.setBounds(5, 30, 990, 30);
		separator.setBackground(marron);
		paneConstraints.gridx = fatherGridX;
		paneConstraints.gridy = fatherGridY+1;
		
		paneConstraints.insets = new Insets(10, 0, 0, 0);
		pane.add(separator, paneConstraints);
		
		System.out.println(separator.getText()+ " : "+ (fatherGridY+1));
	}
	
	
	
	
	private void createChildren(SplitFormule formule, JTextArea father) {
		System.out.println("formule.getOp(): "+ formule.getOp());
		if ( formule.getOp() == Operateur.AND ) {
			createAloneChild(formule, father);
		}
		else {
			int gridY = nearestGridY(father)+3;
			createLeftChild(formule.getF1().toString(), father, gridY);
			createRightChild(formule.getF2().toString(), father, gridY);
		}
			
	}
	
	
	
	private boolean containsContradiction(String str) {
		String[] parts = str.split("\n\n");
		String[] tab = new String[parts.length];
		
		for ( int i = 0 ; i < parts.length ; i++ ) {
			parts[i] = parts[i].replaceAll(" ", "");
			parts[i] = parts[i].replaceAll("\\(", "");
			parts[i] = parts[i].replaceAll("\\)", "");
			
			tab[i]= parts[i];
		}
		
		
		for ( String part : tab ) {
			
			//System.out.println("length : "+part.length()+ " ("+part+")");
			
			if ( part.length() < 3 ) {
				for ( String otherPart : parts ) {
					
					//System.out.println("part: "+part+" / otherPart: "+otherPart);
					
					if ( !part.equals(otherPart) ) {
						
						if ( part.contains("¬") && !otherPart.contains("¬") ) {
							
							char c = part.charAt(part.indexOf("¬")+1);
							if ( otherPart.contains(c+"") )
								return true;
							
						} else if ( otherPart.contains("¬") && !part.contains("¬") ) {
							
							char c = otherPart.charAt(part.indexOf("¬")+1);
							if ( part.contains(c+"") ) 
								return true;
							
						}
						
					}
				}
			}
		}
		
		return false;
	}
	
	
	
	private int nearestGridY(JTextArea directFather) {
		
		int fatherGridY = textAreaToGridY.get(directFather);
		
		/*List<Integer> childrenGridY = new ArrayList<>();
		for ( JTextArea text : childrenToParent.keySet() ) {
			if ( childrenToParent.get(text).equals(directFather) ) {
				childrenGridY.add(textAreaToGridY.get(text));
			}
		}*/
		
		return getChildrenFromFather(directFather).stream().mapToInt(t -> textAreaToGridY.get(t)).max().orElse(fatherGridY+1);
		
		
		/*if ( !childrenGridY.isEmpty() )
			return Collections.max(childrenGridY);
		
		return fatherGridY+1;*/
	}
	
	
	
	private List<JTextArea> getChildrenFromFather(JTextArea father) {
		List<JTextArea> children = new ArrayList<>();
		for ( JTextArea text : childrenToParent.keySet() ) {
			if ( childrenToParent.get(text).equals(father) ) {
				children.add(text);
			}
		}
		
		return children;
	}
	
	
	
	
	
	
	

}
