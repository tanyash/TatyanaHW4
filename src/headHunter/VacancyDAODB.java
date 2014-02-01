package headHunter;

import java.sql.*;
import java.util.*;

/**
 * Created by tanya on 1/19/14.
 */
public class VacancyDAODB implements VacancyDAO {
    private String path;
    private Connection c;

    public VacancyDAODB(String path) {
        this.path = path;
        Connection c;

        try {
            c = DriverManager.getConnection(path);
            //c.setAutoCommit(false);
            this.c = c;
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Vacancy getVacancy(int id) {
        Vacancy v = null;
        try{
            String sql = "SELECT title, description, createddate, pubdate  FROM vacancy WHERE id = ?";

            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, id);

            ResultSet rs = pst.executeQuery();
            while(rs.next()){

                v = new Vacancy(rs.getString("title"), rs.getString("description"),
                        rs.getString("pubdate"),rs.getLong("createddate"));
                break;

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public List<Vacancy> getVacancies() {
        return getVacanciesByStringCondition("", "");
    }

    public List<Vacancy> getNewVacancies(List<Vacancy> vac) {
        List<Vacancy> newVac = new ArrayList();
        for(Vacancy v: vac){
            if ((getDuplicatedVacancies(v)).isEmpty()){
                newVac.add(v);
            }
        }
        return newVac;
    }

    @Override
    public void addVacancy(Vacancy vac) {
        List<Vacancy> vacancies;
        vacancies = getDuplicatedVacancies(vac);
        if (vacancies.isEmpty()){
            try{
                String sql = "INSERT INTO vacancy (title, description, pubdate, createddate) VALUES(?,?,?,?)";
                PreparedStatement pst = c.prepareStatement(sql);


                pst.setString(1, vac.getTitle());
                pst.setString(2, (vac.getDescription()).substring(0, 100));
                pst.setString(3, vac.getPubDate());
                pst.setLong(4, vac.getCreatedDate());

                pst.executeUpdate();

            }
            catch(SQLException e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public void updateVacancy(Vacancy vac) {
        try{
            String sql = "UPDATE vacancy " +
                    "SET description='" + vac.getDescription() +"'"+
                    "WHERE title LIKE '%" + vac.getTitle() +"%'" + " AND " +
                    "pubDate LIKE '%" + vac.getPubDate() + "%'";
            PreparedStatement pst = c.prepareStatement(sql);

            pst.executeUpdate();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public void addAllVacancies(List<Vacancy> vc) {
        for(Vacancy v:vc){
            addVacancy(v);
        }
    }

    @Override
    public void deleteVacancy(Vacancy vac) {
        try{
            String sql = "DELETE FROM vacancy " +
                    "WHERE title LIKE '%" + vac.getTitle() +"%'" + " AND " +
                    "pubDate LIKE '%" + vac.getPubDate() + "%'";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Vacancy> getVacancyByTitle(String s) {
          return getVacanciesByStringCondition("title", s);
    }

    @Override
    public List<Vacancy> getVacanciesByDescription(String keyword) {
        return getVacanciesByStringCondition("description", keyword);
    }

    private List<Vacancy> getDuplicatedVacancies(Vacancy vac) {
        String title = vac.getTitle();
        String pubDate = vac.getPubDate();
        HashMap<String, String> mapParam = new HashMap<String, String>();
        mapParam.put("title", title);
        mapParam.put("pubDate", pubDate);

        return getVacanciesByCondition(mapParam);
    }

    private List<Vacancy> getVacanciesByStringCondition(String field, String fieldValue) {
        HashMap<String, String> mapParam = new HashMap<String, String>();
        if (!field.isEmpty()){
            mapParam.put(field, fieldValue);
        }
        return getVacanciesByCondition(mapParam);
    }

    private List<Vacancy> getVacanciesByCondition(HashMap<String, String> mapParam) {
        List<Vacancy> vc= new ArrayList<Vacancy>();
        Vacancy v = null;
        String key, value;
        String sql = "SELECT id, title, description, createddate, pubdate  FROM vacancy ";
        int k = 1;
        ArrayList<String> values = new ArrayList<String>();

        Iterator iterator = mapParam.entrySet().iterator();

        while (iterator.hasNext()){
            if (k == 1){
                sql += " WHERE ";
            }
            else{
                sql += " AND ";
            }
            Map.Entry mEntry = (Map.Entry) iterator.next();
            key = (String) mEntry.getKey();
            sql += (key + " LIKE ");
            value = (String) mEntry.getKey();
            sql += ("'%" + (String)mEntry.getValue() + "%'");
            k++;
        }

        try{

            PreparedStatement pst = c.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();
            while(rs.next()){

                v = new Vacancy(rs.getString("title"), rs.getString("description"),
                        rs.getString("pubdate"),rs.getLong("createddate"));
                vc.add(v);

            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return vc;
    }

}
