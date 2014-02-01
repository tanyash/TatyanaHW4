package headHunter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: tanyash
 * Date: 22.12.13
 * Time: 12:01
 * To change this template use File | Settings | File Templates.
 */
public class HHController {
    public URL url;
    public VacancyDAO vDao;

    public HHController(URL url, VacancyDAO vDao) {
        this.url = url;
        this.vDao = vDao;
    }

    //getDocument(URL url);
    public String getDocument(){
        URLConnection uc = null;
        String s = null;
        try {
            uc = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null){
                sb.append(inputLine);
            }
            in.close();
            s = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    public List<Vacancy> parseVacancies(){
        String document = getDocument();
        String[] items = document.split("<item>");
        List<Vacancy> vacancies = new ArrayList<Vacancy>();

        for (int i = 0; i < items.length; i++){
             if (!(items[i].contains("</item>"))){
                 continue;
             }
             vacancies.add(parseVacancy(items[i]));
        }
        return vacancies;

    }

    private Vacancy parseVacancy (String str){
        String title;
        String desc;
        String pubDate;

        title = str.split("title")[1];
        desc =  str.split("description")[1];
        pubDate = str.split("pubDate")[1];

        Vacancy v = new Vacancy(title.substring(1,title.length() - 2), desc, pubDate);

        return v;
    }

    public void storeVacancies(){
        vDao.addAllVacancies(parseVacancies());
    }

    public void getVacancies(){
        List<Vacancy> vc = new ArrayList<Vacancy>();
        vc = vDao.getVacancies();
        for(Vacancy v:vc){
            System.out.println(v.toString());
        }
    }

    public void getVacancies(String title){
        List<Vacancy> vc = new ArrayList<Vacancy>();
        vc = vDao.getVacancyByTitle(title);
        for(Vacancy v:vc){
            System.out.println(v.toString());
        }
    }

    public void getNewVacancies(){
        List<Vacancy> vc = new ArrayList<Vacancy>();
        vc = vDao.getNewVacancies(parseVacancies());
        vDao.addAllVacancies(vc);
        for(Vacancy v:vc){
            //deleteVacancy(v);
            v.setDescription("UPDATED VACANCY");
            updateVacancy(v);
            System.out.println(v.toString());
        }
    }

    public  void deleteVacancy(Vacancy v){
        vDao.deleteVacancy(v);
    }

    public void updateVacancy(Vacancy v){
        vDao.updateVacancy(v);
    }


}
