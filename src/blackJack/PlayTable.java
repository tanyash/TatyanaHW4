package blackJack;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 05.01.14
 * Time: 14:10
 * To change this template use File | Settings | File Templates.
 */
public class PlayTable implements Serializable{
    private Dealer dealer;
    private ArrayList<PlayerWithBet> realPlayers;
    public final float WIN_RATE = 1.5f;
    public final int BLACK_JACK = 21;
    private boolean completed = false;
    //The sum of scores the whole deck
    final int totalScoreDeck;

    public PlayTable() {
        dealer = new Dealer(3, 52);
        realPlayers = new ArrayList<PlayerWithBet>(5);
        totalScoreDeck = getDealer().getDeck().getScoreCardsDeck();
    }

    private void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public static PlayTable getPlayTable(Scanner in) throws  IOException{
        ObjectInputStream input = null;
        PlayTable pt = null;

        File source = new File(".");
        File[] files = new File[0];
        File f = null;
        files = source.listFiles();
        String reply;

        if (files == null){
            return null;
        }

        for (File s: files){
            if(s.getName().startsWith("pt")){
                f = s;
                break;
            }
        }

        if (f == null){
            System.out.println("There is not saved game.");
            return null;
        }

        try{
            input = new ObjectInputStream(new FileInputStream(f.getPath()));
            pt = (PlayTable)input.readObject();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        finally{
            if (input != null){
                input.close();
            }
        }
        return pt;
    }

    public static void removeOld(){
        File source = new File(".");
        File[] files = new File[0];
        files = source.listFiles();

        if (files == null){
            return;
        }

        for (File s: files){
            if(s.getName().startsWith("pt")){
                s.delete();
                System.out.println(s.getName() + " was deleted");
            }
        }
        return;

    }


    private void addPlayer(String name, float bet){
        PlayerWithBet playerWithBet = new PlayerWithBet(name, bet);
        realPlayers.add(playerWithBet);
    }

    public void placeBets(Scanner in){
        Integer betsCount = (new Dialog<Integer>(in, "How many bets would you like to make? (<=10)",
                Integer.TYPE, 1, 10)).getResult();

        String name;
        float bet;

        for (int i = 1; i <= betsCount; i++){
            name = (new Dialog<String>(in, "Input bet name:", String.class)).getResult();
            bet = (new Dialog<Float>(in, "Input your bet sum(money) <=10:", Float.TYPE, 0.1f, 10.0f)).getResult();
            addPlayer(name, bet);
        }
    }

    public void deal(){
        for (int i = 1; i <= 2; i++){
            for (PlayerWithBet p: realPlayers){
                p.deal(dealer.getDeck(), 1);
            }
        }
        dealer.deal(dealer.getDeck(), 1);
    }

    public void hit(Scanner in){
        for (PlayerWithBet p: realPlayers){
            for(;;){
                if ((BLACK_JACK - p.getScore()) == 0){
                    System.out.println("Black Jack! You have chance to win!");
                    break;
                }

                if ((BLACK_JACK - p.getScore()) < 0){
                    System.out.println("Bust! You lost your money.");
                    break;
                }

                String reply = p.getName() + ", do you want to hit? Input 'Y' for YES";
                reply = (new Dialog<String>(in, reply, String.class)).getResult();
                if (reply.toLowerCase().equals("y")){
                    p.hit(dealer.getDeck());
                    System.out.println(p.toString());
                }
                else{
                    break;
                }
            };
        }
        dealer.hit();
        System.out.println(dealer.toString());
    }

    private float setPlayerIncome(PlayerWithBet p){
        float multiplier = -1;
        int diff = BLACK_JACK - p.getScore();
        if (diff != 0){
            diff /= Math.abs(diff);
        }
        int dealerDiff = BLACK_JACK - dealer.getScore();

        switch (diff){
            case 0:
                if (dealerDiff != 0){
                    multiplier = WIN_RATE;
                }
            case 1:
                if ((dealerDiff < 0) || ((dealerDiff > 0)&& (dealer.getScore() < p.getScore()))){
                    multiplier = WIN_RATE;
                }

            case -1:
            default:
                p.setIncome(p.getBet()*(multiplier));
                return -((multiplier == WIN_RATE) ? (p.getBet() * (multiplier - 1)) : -(p.getBet()));
        }
    }

    public void getWinner(){
        float casinoMoney = 0;
        Collections.sort(realPlayers);

        for (PlayerWithBet p: realPlayers){
            casinoMoney += (setPlayerIncome(p));
        }
        dealer.setIncome(casinoMoney);
        setCompleted(true);
    }

    public void getPlayers(){
        System.out.println();
        System.out.println("The result score is in the field 'score'");
        System.out.println("*****************");
        for (PlayerWithBet p: realPlayers){
            System.out.println(p.toString());
        }
        System.out.println(dealer.toString());
        System.out.println("*****************");
    }

    public Dealer getDealer() {
        return dealer;
    }

    public int getSumScore(){
        int sum = 0;
        for (PlayerWithBet p: realPlayers){
            sum += p.getScore();
        }
        return sum += dealer.getScore();
    }

    public int getSumIncome(){
        int sum = 0;
        for (PlayerWithBet p: realPlayers){
            if (p.getIncome() > 0){
                sum += (p.getIncome() - p.getBet());
            }
            else{
                sum -= p.getBet();
            }

        }
        return sum += dealer.getIncome();
    }

    @Override
    public String toString() {
        return "PlayTable{" +
                "dealer=" + dealer +
                ", realPlayers=" + realPlayers +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PlayTable other = (PlayTable) obj;

        if (dealer == null) {
            if (other.dealer != null)
                return false;
        } else if (!dealer.equals(other.dealer)){
            return false;
        }

        if (realPlayers == null) {
            if (other.realPlayers != null)
                return false;
        } else if (!realPlayers.equals(other.realPlayers)){
            return false;
        }
        return true;
    }
}
