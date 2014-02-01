package headHunter;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by tanya on 1/19/14.
 */
public class VacancyDAOFS implements VacancyDAO {
    public String path;
    private File dest;

    public VacancyDAOFS(String path) {
        this.path = path;
        dest = new File(path);
    }

    @Override
    public Vacancy getVacancy(int id) {
        List<Vacancy> vc = new ArrayList<Vacancy>();
        vc = getList();
        if (!vc.isEmpty()){
            return vc.get(id);
        }
        return null;
    }

    @Override
    public List<Vacancy> getVacancies() {
        List<Vacancy> vc = new ArrayList<Vacancy>();
        vc = getList();
        return vc;
    }

    @Override
    public void addVacancy(Vacancy vac) {
        List<Vacancy> vc = new ArrayList<Vacancy>();
        vc = getList();
        vc.add(vac);
        putList(vc);
    }

    @Override
    public void updateVacancy(Vacancy vac) {
        List<Vacancy> vc = new ArrayList<Vacancy>();
        vc = getList();
        for (Vacancy v:vc){
            if ((v.getTitle().equals(vac.getTitle())) && (v.getPubDate().equals(vac.getPubDate()))){
                v.setDescription(vac.getDescription());
            }
        }
        putList(vc);
    }

    @Override
    public void addAllVacancies(List<Vacancy> vc) {
        putList(vc);
    }

    @Override
    public void deleteVacancy(Vacancy vac) {
        List<Vacancy> vc = new ArrayList<Vacancy>();
        Vacancy v;

        vc = getList();
        if (vc == null){
            return;
        }
        Iterator<Vacancy> it = vc.iterator();
        while(it.hasNext()){
            v = it.next();
            if ((v.getTitle().equals(vac.getTitle())) && (v.getPubDate().equals(vac.getPubDate()))){
                it.remove();
            }
        }
        dest.delete();
        try {
            dest.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        putList(vc);
    }

    @Override
    public List<Vacancy> getVacancyByTitle(String s) {
        List<Vacancy> vc = new ArrayList<Vacancy>();
        List<Vacancy> vcReturn = new ArrayList<Vacancy>();

        vc = getList();
        for (Vacancy v: vc){
            if ((v.getTitle()).matches(s)){
                vcReturn.add(v);
            }
        }
        return vcReturn;
    }

    @Override
    public List<Vacancy> getVacanciesByDescription(String keyword) {
        List<Vacancy> vc = new ArrayList<Vacancy>();
        List<Vacancy> vcReturn = new ArrayList<Vacancy>();

        vc = getList();
        for (Vacancy v: vc){
            if ((v.getDescription()).matches(keyword)){
                vcReturn.add(v);
            }
        }
        return vcReturn;
    }

    @Override
    public List<Vacancy> getNewVacancies(List<Vacancy> vac) {
        List<Vacancy> vcOld = new ArrayList<Vacancy>();
        List<Vacancy> vcNew = new ArrayList<Vacancy>();
        Vacancy vacancy;

        if (dest.exists()){
            vcOld = getList();
        }

        if (vcOld == null){
            putList(vac);
            return vac;
        }

        Iterator<Vacancy> it;
        int k = 0;

        for (Vacancy v: vac){
            it = vcOld.iterator();
            while(it.hasNext()){
                vacancy = it.next();
                if ((v.getTitle().equals(vacancy.getTitle())) && (v.getPubDate().equals(vacancy.getPubDate()))){
                    k = -1;
                    break;
                }
            }
            if (k == 0){
                vcNew.add(v);
                vcOld.add(v);
            }
            else{
                k = 0;
            }
        }

        putList(vcOld);
        return vcNew;
    }

    private List<Vacancy> getList(){
        ObjectInputStream in = null;
        FileInputStream fis = null;
        List<Vacancy> vacancies = new ArrayList<Vacancy>();
        try{
            fis = new FileInputStream(dest);
            in = new ObjectInputStream(fis);
            vacancies = (List<Vacancy>) in.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            if (in != null){
                try {
                    fis.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return vacancies;

    }

    private void putList(List<Vacancy> vc){
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream((dest), true));
            out.writeObject(vc);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if (out != null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}



