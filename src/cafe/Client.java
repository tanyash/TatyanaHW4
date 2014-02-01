package cafe;

import java.util.Random;

/**
 * Created by tanya on 1/28/14.
 */
public class Client extends Thread {
    private String clientName;
    private Cassier cassier;

    public Client(String clientName, Cassier cassier) {
        this.clientName = clientName;
        this.cassier = cassier;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Order makeOrder(){
        int k = new Random().nextInt(cassier.menu.length);
        Order order = new Order(this, cassier.menu[k]);
        if (order != null){
            cassier.addOrder(order);
        }
        return order;
    }

    public void run() {
        Order order;
        try {
            sleep(new Random().nextInt(3000));
            synchronized (cassier){
                order = makeOrder();
                System.out.println("Client " + order.getClient().getClientName() + ": I am waiting for my " + order.getNameMeal());
                cassier.notifyAll();
            }
            synchronized (this){
                while (!order.isDone()){
                    wait();
                }
            }
            System.out.println("Client " + order.getClient().getClientName() + ": Thank you for my " + order.getNameMeal());
            yield();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
