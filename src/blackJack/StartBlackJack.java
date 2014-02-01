package blackJack;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 10.01.14
 * Time: 11:38
 * To change this template use File | Settings | File Templates.
 */
public class StartBlackJack {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        PlayTable playTable = null;
        String replyRestore;
        Saver t;

        while (((new Dialog<String>(in, "Do you want to play Black Jack? Input 'Y' for YES",
                String.class)).getResult()).toLowerCase().equals("y")) {
            replyRestore = (new Dialog<String>(in, "Do you want to restore your game? Input 'Y' for YES",
                    String.class)).getResult();
            if (replyRestore.toLowerCase().equals("y")) {
                playTable = PlayTable.getPlayTable(in);
            }
            else
            {
                PlayTable.removeOld();
            }

            if (playTable != null){
                if (playTable.isCompleted()){
                    playTable.getPlayers();
                    System.out.println("This game was over");
                    PlayTable.removeOld();
                    playTable = null;
                    continue;
                }
            }
            else{
                playTable = new PlayTable();

                playTable.placeBets(in);
                playTable.deal();
            }
            t = new Saver(playTable);
            t.start();

            playTable.getPlayers();

            playTable.hit(in);
            playTable.getWinner();

            playTable.getPlayers();


            //The sum of scores the whole deck - the sum scores of the players card mod 10 = 0
            assert ((playTable.totalScoreDeck - playTable.getSumScore()- playTable.getDealer().getDeck().getScoreCardsDeck())% 10 == 0):
                    "There is an error in the score sum";

            System.out.println(playTable.getSumIncome());
            //The result income value of the bets, players winning and dealer gain
            assert (playTable.getSumIncome() == 0):"There is an error in sum income";

            t.setFinished(true);
            playTable = null;

        }
        PlayTable.removeOld();
        if (in != null){
            in.close();
        }

    }

}
