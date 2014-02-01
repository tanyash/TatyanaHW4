package blackJack;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 08.01.14
 * Time: 10:52
 * To change this template use File | Settings | File Templates.
 */
public class Dialog <T> {
    private T result;
    private Class<T> type;
    private String question;
    private Scanner in = null;
    private T minValue;
    private T maxValue;


    @SuppressWarnings({"unchecked"})
    public Dialog(Scanner in, String question, Class<T> type) {
        this(in, question, type, null, null);
    }

    @SuppressWarnings({"unchecked"})
    public Dialog(Scanner in, String question, Class<T> type, T min, T max) {
        this.in = in;
        this.question = question;
        this.type = type;
        this.minValue = min;
        this.maxValue = max;

        this.result = getResultWithScanner();
    }

    @SuppressWarnings({"unchecked"})
    private T getResultWithScanner(){
        T result = null;
        System.out.println(question);

        boolean flag = false;
        do
        {
            try
            {
               if (type == Integer.TYPE){
                   int k = in.nextInt();
                   if ((k < (Integer)minValue)||(k > (Integer)maxValue)){
                       throw new InputMismatchException("");
                   }
                   result = (T)((Integer)k);
               }
               else if
                 (type == Float.TYPE){
                   float k = in.nextFloat();
                   if ((k < (Float)minValue)||(k > (Float)maxValue)){
                       throw new InputMismatchException("");
                   }
                   result = (T)((Float)k);
                }
                else {
                    result = (T)(in.next());
                }

                flag = true;
            }
            catch (InputMismatchException e)
            {
                System.out.println("Input the correct value, please.");
                in.nextLine();
            }
        }
        while ( !flag );

        System.out.println("OK! Input value is: " + result.toString());
        return result;
    }

    public T getResult() {
        return result;
    }
}
