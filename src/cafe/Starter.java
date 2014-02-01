package cafe;

import java.util.ArrayList;

/**
 * Created by tanya on 1/28/14.
 */
public class Starter {
    public static void main(String[] args) throws InterruptedException {
        final int count = 20;
        Cassier cassier = new Cassier(count);
        cassier.start();
        //Thread.sleep(2000);

        ArrayList<Client> clients = new ArrayList<Client>();

        Client client;
        for (int i = 0; i < count; i++){
            client = new Client(String.valueOf(i), cassier);
            clients.add(client);
        }

        for (Client c: clients){
            c.start();
        }

    }
}
