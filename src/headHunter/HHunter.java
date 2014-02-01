package headHunter;

import java.io.File;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 22.12.13
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
public class HHunter {
    public static void main(String[] args)  {

        HHController hhc = null;
        VacancyDAO vDao = null;


        try{
            //vac1.xml contains less records than vac.xml
            URL url = new File("./vac1.xml").toURI().toURL();

            //URL url = new File("/home/tanya/IdeaProjects/vac.xml").toURI().toURL();
            //vDao = new VacancyDAOFS("/home/tanya/IdeaProjects/vac.txt");

            vDao = new VacancyDAOFS("./vac.txt");
            //vDao = new VacancyDAODB("jdbc:derby:/home/tanya/IdeaProjects/db/vac");

            hhc = new HHController(url, vDao);
            hhc.getNewVacancies();

        }
        catch (Exception e){
            e.printStackTrace();

        }
        finally{

        }

    }
}
