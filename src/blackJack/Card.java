package blackJack;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 27.11.13
 * Time: 8:50
 * To change this template use File | Settings | File Templates.
 */
public class Card implements Serializable {
    private String suit; // масть
    private String value; // достоинство

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    //@Override
    public String toString() {
        return suit + " " + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Card other = (Card) obj;
        if (suit == null) {
            if (other.suit != null)
                return false;
        } else if (!suit.equals(other.suit)){
            return false;
        }
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value)){
            return false;
        }
        return true;
    }
}
