package blackJack;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 05.01.14
 * Time: 13:39
 * To change this template use File | Settings | File Templates.
 */
public abstract class Player implements Comparable<Player>, Serializable {
    protected ArrayList<Card> cards;
    protected float income;
    private int score;


    protected Player() {
        cards = new ArrayList<Card>(5);
        this.income = 0;
    }

    public float getIncome() {
        return income;
    }

    public int getScore() {
        return score;
    }

    private void setScore(int score) {
        this.score = score;
    }

    public void setIncome(float income) {
        this.income = income;
    }

    public void deal(CardsDeck deck, int times){
        for (int i = 1; i<= times; i++){
            cards.add(deck.getCardOff());
            setScore(checkScore(deck));
        }
    }

    public void hit(CardsDeck deck){
       deal(deck, 1);
    }

    public int checkScore(CardsDeck deck){
        int score = deck.getScore(cards);
        return score;
    }

    @Override
    public String toString() {
        return "Player{" +
                "cards=" + cards.toString() + " score= " + String.valueOf(score) +  " income =" + String.valueOf(income) +
                '}';
    }

    @Override
    public int compareTo(Player o) {
        return score - o.getScore();
    }
}
