package blackJack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 27.11.13
 * Time: 9:04
 * To change this template use File | Settings | File Templates.
 */
public class CardsDeck implements Serializable {
    //Количество карт в колоде
    public final int countCardsInDeck;
    public final int countDecks;
    public final int BLACK_JACK = 21;

    private Card[] cards;
    private int lastNotNullIndex = -1;

    public final String[] arrSuit = {"Пики", "Черви", "Трефы", "Бубны"};
    public final String [][] arrValue = {{"Туз", "Король", "Дама", "Валет", "10", "9", "8", "7", "6", "5", "4", "3", "2"},
            {"11", "10", "10", "10", "10", "9","8", "7", "6", "5", "4", "3", "2"}}  ;

    //Create cards array by arrSuit and arrValue arrays
    public CardsDeck(int countDecks, int countCardsInDeck){
        cards = new Card[countDecks * countCardsInDeck];
        this.countCardsInDeck = countCardsInDeck;
        this.countDecks = countDecks;
        fill();
        shuffle();
    }

    //Fill the deck with cards
    private void fill(){
        int countOneSuit = countCardsInDeck/arrSuit.length;

        for (int i = 0; i < countDecks; i++){
            for (String suit: arrSuit){
                int countCards = 0;
                for (String[] valueArr: arrValue){
                    for (String value: valueArr){
                        if (countCards < countOneSuit){
                            Card card = new Card(suit, value);
                            add(card);
                            countCards++;
                        }
                    }
                }
            }
        }
    }

    //Add the card to the deck
    private void add(Card card) {
        lastNotNullIndex++;
        cards[lastNotNullIndex] = card;
    }

    //Get the last not null card
    public Card getCardOff() {
        Card card = cards[lastNotNullIndex];
        cards[lastNotNullIndex] = null;
        lastNotNullIndex--;
        return card;
    }

    //Print the deck
    public void printCards() {
        for (Card card: this.cards ){
            if (card != null){
                System.out.println(card.toString());
            }
            else{
                System.out.println("null");
            }

        }
    }

    //Scuffle cards array values by the pair cards indexes changing
    private void shuffle() {
        Card cardTmp = new Card("","");
        int randomValue = 0;
        double r1;
        int l = cards.length;

        for (int i = 0; i < l; i++){
            cardTmp = cards[i];
            do{
                r1 = Math.random() * l - 1;
                randomValue = (int)r1;
            } while (randomValue == i);

            cards[i] = cards[randomValue];
            cards[randomValue] = cardTmp;
            randomValue = 0;
        }
    }

    //Get the score of the card
    public int getScoreByValue(String value){
        for (String[] valueArr: arrValue){
             for(int i = 0; i < valueArr.length; i++){
                  if (value.equals(valueArr[i])) {
                      return Integer.parseInt(arrValue[1][i]);
                  }
             }
        }
        return -1;
    }

    //Get the total score of the given cards
    public int getScore (ArrayList<Card> cardsArray){
        int sum = 0;
        int score = 0;
        int countTuz = 0;

        for (Card c: cardsArray){
            if (c == null) {
                break;
            }
            score = getScoreByValue(c.getValue());
            if (score < 0){
                return -1;
            }
            if (score == 11){
               countTuz++;
            }
            sum += getScoreByValue(c.getValue());
        }

        while ((countTuz >= 1) && (sum > BLACK_JACK)){
            sum -= 10;
            countTuz--;
        }

        return sum;
    }

    public int getScoreCardsDeck() {
        ArrayList<Card> cardList = new ArrayList<Card>();
        for(int i = 0; i <= lastNotNullIndex; i++){
            cardList.add(cards[i]);
        }
        return getScore(cardList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CardsDeck other = (CardsDeck) obj;
        if (cards == null) {
            if (other.cards != null)
                return false;
        } else if (!Arrays.equals(cards, other.cards)){
            return false;
        }
        return true;
    }
}
