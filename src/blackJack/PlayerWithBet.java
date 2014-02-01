package blackJack;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 05.01.14
 * Time: 13:51
 * To change this template use File | Settings | File Templates.
 */
public class PlayerWithBet extends Player {
    private float bet;
    private String name;

    public PlayerWithBet(String name, float bet) {
        super();
        this.name = name;
        this.bet = bet;
    }

    public String getName() {
        return name;
    }

    public float getBet() {
        return bet;
    }

    @Override
    public String toString() {
        return "PlayerWithBet{" +
                "bet=" + bet +
                ", name='" + name + '\'' +
                '}' + super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayerWithBet other = (PlayerWithBet) obj;
        if (cards == null) {
            if (other.cards != null)
                return false;
        } else if (!cards.equals(other.cards))
            return false;
        if (bet != other.getBet()) {
                return false;
        }
        return true;
    }
}
