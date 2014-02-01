package blackJack;

import java.io.*;

/**
 * Created by tanya on 1/16/14.
 */
public class Saver extends Thread {
    private PlayTable pt;
    private PlayTable pt1;
    final int TIME = 1000;
    private boolean finished = false;

    public Saver (PlayTable pt){
        super();
        this.pt = null;
        this.pt1 = pt;
    }

    public PlayTable getPt() {
        return pt;
    }

    public void setPt1(PlayTable pt1) {
        this.pt1 = pt1;
    }

    public void setPt(PlayTable pt1) {
        if (pt1 == null){
            this.pt = null;
            return;
        }

        try{
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(pt1);
            oos.flush();

            byte[] byteData = bos.toByteArray();

            oos.close();
            bos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            this.pt = (PlayTable)ois.readObject();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public void run() {
        while (!finished){
            try {
                sleep(TIME);
                savePlayTable(pt1);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void savePlayTable(PlayTable pt1) throws IOException{
        ObjectOutputStream output = null;
        try{
                if (((this.pt != null) && (!this.pt.equals(pt1))) || ((this.pt == null) && (pt1 != null))){
                    output = new ObjectOutputStream(new FileOutputStream("pt" + pt1.hashCode()));
                    output.writeObject(pt1);
                    System.out.println("PT was saved");
                    setPt(pt1);
                }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (output != null){
                output.close();
            }

        }
    }

}

