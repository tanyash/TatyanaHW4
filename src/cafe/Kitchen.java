package cafe;

import java.util.ArrayList;

/**
 * Created by tanya on 1/28/14.
 */
public class Kitchen {
private int task;
private int done;

    public void prepareMeal(ArrayList<String> orderToKitchen, int maxCount, Cassier cassier){
        Cook cook;
        Meal meal;
        for (String dish:orderToKitchen){
            for (int i = 1; i<= maxCount; i++){
                task++;
                cook = new Cook(cassier, "Cook" + String.valueOf(i));
                meal = new Meal(dish);
                cook.setMeal(meal);
                cook.start();
            }
        }

    }

    public void incDone() {
        this.done++;
    }

    public boolean isDone(){
        if (task == 0){
            return false;
        }
        if (task == done){
            task = 0;
            done = 0;
            return true;
        }
        return false;
    }

}
