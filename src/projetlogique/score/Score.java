package projetlogique.score;

import java.time.LocalTime;

public class Score {

    public static boolean DEBUG = false;

    private Integer score;
    private LocalTime time;

    public Score() {
        this.score = 0;
        time = LocalTime.now();

        if(DEBUG) {
            System.out.println("LocalTime: " + time);
            System.out.println("Score: " + score);
        }
    }

    public int getScore() { return score;}

    public int addScore(int amount) {
        score += amount;
        return score;
    }

    public int addTimeScore() {
        LocalTime now = LocalTime.now();

        int hour = now.getHour() - time.getHour();
        int min = now.getMinute() - time.getMinute() ;
        int seconds = now.getSecond() - time.getSecond();

        if (DEBUG) {
            System.out.println("Time : " + now);
            System.out.println("h: " + hour + "  m: " + min + "  s: " + seconds);
        }

        addScore(100 * (60 * hour));
        addScore(100 * min);
        addScore(seconds);

        return score;
    }

}
