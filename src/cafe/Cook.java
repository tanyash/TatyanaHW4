package cafe;

/**
 * Created by tanya on 1/28/14.
 */
public class Cook extends Thread{
    private String cookName;
    private Meal meal;
    private final Cassier cassier;

    Cook(Cassier cassier,String name){
        this.cassier = cassier;
        this.cookName = name;
    }

    public Meal getMeal() {
        return meal;
    }

    public String getCookName() {
        return cookName;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @Override
    public void run() {
        try {
            sleep(2000);
            System.out.println(getCookName() +": cooking " + meal.getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (cassier){
            cassier.addMealOnWindowCase(meal);
            cassier.incDoneTasks();
            cassier.notifyAll();
            System.out.println(getCookName() + ": " + meal.getName() + " is done");
        }
        yield();

    }
}
