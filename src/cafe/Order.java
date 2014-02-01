package cafe;

/**
 * Created by tanya on 1/28/14.
 */
public class Order {
    private Client client;
    private Meal meal;
    private String nameMeal;
    private boolean isDone = false;

    public Order(Client client, String nameMeal) {
        this.client = client;
        this.nameMeal = nameMeal;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public String getNameMeal() {
        return nameMeal;
    }

    public Client getClient() {
        return client;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    @Override
    public String toString() {
        return "Order{" +
                "client=" + client.getClientName() +
                ", nameMeal='" + nameMeal + '\'' +
                ", isDone=" + isDone +
                '}';
    }
}
