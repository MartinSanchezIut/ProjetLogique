package projetlogique;

import javax.swing.SwingUtilities;

import projetlogique.gui.Fenetre;
import projetlogique.score.Score;

public class Main {

    public static Score score;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                new Fenetre();
            }
        });
	}

}
