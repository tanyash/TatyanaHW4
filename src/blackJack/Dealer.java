package blackJack;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 05.01.14
 * Time: 13:52
 * To change this template use File | Settings | File Templates.
 */
public class Dealer extends Player {
    public final int maxScore = 18;
    private CardsDeck deck;

    public Dealer(int decks, int countCards) {
        super();
        deck = new CardsDeck(decks, countCards);
    }

    public CardsDeck getDeck() {
        return deck;
    }

    public void setDeck(CardsDeck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return "Dealer{}"+ super.toString();
    }

    public void hit() {
        while (checkScore(deck) <= maxScore){
            super.hit(deck);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Dealer other = (Dealer) obj;
        if (deck == null) {
            if (other.getDeck() != null)
                return false;
        } else if (!deck.equals(other.deck)){
            return false;
        }
        if (cards == null) {
            if (other.cards != null)
                return false;
        } else if (!cards.equals(other.cards))
            return false;
        return true;
    }
}
