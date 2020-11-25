package projetlogique;

import javax.swing.SwingUtilities;

import projetlogique.gui.Fenetre;

public class Main {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new Fenetre();
            }
        });
	}

}
