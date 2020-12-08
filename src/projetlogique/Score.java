package projetlogique;

import java.time.LocalTime;

public class Score {

    private Integer score;
    private LocalTime time;

    public Score() {
        this.score = 0;
        time = LocalTime.now();
        System.out.println(time);
    }

    public int getScore() { return score;}

    public int addScore(int amount) {
        score += amount;
        return score;
    }

    /*
    		JButton termine = new JButton("J'ai fini !");
		constraints.gridx = 0;
		constraints.gridy = 2;
		add(termine, constraints);

		termine.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Coucou");
				new Score();
			}
		});
     */

}
