package cafe;

import java.util.*;

/**
 * Created by tanya on 1/28/14.
 */
public class Cassier extends Thread{
    private final static int maxCount = 10;
    private final static int minCount = 5;
    private boolean finished = false;
    private int clientCount;

    public final static String[] menu = {"tea", "coffee", "roll", "cake", "jam", "ice-cream"};

    Map<String, ArrayList<Meal>> mealsOnWindowCase;
    private Kitchen kitchen;

    private ArrayList<Order> orders;

    public Cassier(int count) {
        mealsOnWindowCase = new HashMap<String, ArrayList<Meal>>();
        kitchen = new Kitchen();
        orders = new ArrayList<Order>();
        clientCount = count;
    }

    public void incDoneTasks(){
        kitchen.incDone();
    }

    public int getClientCount() {
        return clientCount;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    private synchronized ArrayList<String> formOrderToKitchen(){
        ArrayList<Meal> portions = null;
        ArrayList<String> mealsOrderToKitchen = new ArrayList<String>();

        Iterator iterator = mealsOnWindowCase.entrySet().iterator();

        if (mealsOnWindowCase.entrySet().size() == 0) {
            return new ArrayList<String>(Arrays.asList(menu));
        }

        while (iterator.hasNext()){
            Map.Entry mEntry = (Map.Entry) iterator.next();
            portions = (ArrayList<Meal>) mEntry.getValue();
            if (portions.size() < minCount){
               mealsOrderToKitchen.add((String) mEntry.getKey());
            }
        }

        return mealsOrderToKitchen;

    }

    public synchronized void addMealOnWindowCase(Meal meal){
        boolean st = false;
        String dish = meal.getName();
        ArrayList<Meal> portions = new ArrayList<Meal>();
        String key;
        Iterator iterator = mealsOnWindowCase.entrySet().iterator();

        while (iterator.hasNext()){
            Map.Entry mEntry = (Map.Entry) iterator.next();
            key = (String) mEntry.getKey();
            if (key.equals(dish)){
                portions = (ArrayList<Meal>) mEntry.getValue();
                portions.add(meal);
                st = true;
                break;
            }
        }

        if ((!st) || (mealsOnWindowCase.entrySet().size() == 0)){
            portions.add(meal);
            mealsOnWindowCase.put(meal.getName(), portions);
            return;
        }
    }

    public synchronized void addOrder(Order order){
        orders.add(order);
    }

    private synchronized Meal satisfyOrder(Order order){
        String dish = order.getNameMeal();
        ArrayList<Meal> portions = new ArrayList<Meal>();
        String key;
        Iterator iterator = mealsOnWindowCase.entrySet().iterator();
        Meal meal;

        while (iterator.hasNext()){
            Map.Entry mEntry = (Map.Entry) iterator.next();
            key = (String) mEntry.getKey();
            if (key.equals(dish)){
                portions = (ArrayList<Meal>) mEntry.getValue();
                if (portions.size() > 0){
                    meal = portions.remove(0);
                    order.setMeal(meal);

                    synchronized (order.getClient()){
                        System.out.println("Satisfy order of the client " + order.getClient().getClientName());
                        order.setDone(true);
                        orders.remove(order);
                        order.getClient().notify();
                    }

                    return meal;
                }
                else{
                    return null;
                }
            }
        }
        return null;
    }

    @Override
    public void run() {
        synchronized (this){
            kitchen.prepareMeal(formOrderToKitchen(), maxCount, this);
            try {
                while (!kitchen.isDone()){
                    wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int k = 0;

        while (!finished){
            try {
                synchronized (this){
                    for (;;){
                        if (orders.size() == 0){
                            if (k == clientCount){
                                break;
                            }
                            wait();
                        }
                        else{
                            Order order = orders.get(0);
                            sleep(1000);
                            Meal meal = satisfyOrder(order);
                            ArrayList<String> s;
                            s = formOrderToKitchen();
                            if (s.size() > 0){
                                kitchen.prepareMeal(s, maxCount, this);
                                while (!kitchen.isDone()){
                                    wait();
                                }
                            }
                            k++;
                        }
                    }
                }

                finished = true;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
