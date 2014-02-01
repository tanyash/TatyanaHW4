package headHunter;

import java.util.List;

/**
 * Created by tanya on 1/19/14.
 * public interface VacancyDAO {
 Vacancy getVacancy(int id); //возвращает вакансию по id
 List<Vacancy> getVacancies(); //возвращает все вакансии
 void addVacancy(Vacancy vacancy); //сохраняет вакансию
 void updateVacancy(Vacancy vacancy); //ищет среди сохраненных вакансий вакансию с таким же id, и перезаписывает все поля
 void addAllVacancies(List<Vacancy> vacancies); //сохраняет все вакансии из списка
 void deleteVacancy(Vacancy vacancy);//удаляет вакансию из БД
 List<Vacancy> getVacanciesByTitle(String keyword); //возвращает список вакансий, в названии которых встречает заданное слово
 List<Vacancy> getVacanciesByDescription(String keyword);//возвращает список вакансий, в описании которых встречает заданное слово
 }
 */

public interface VacancyDAO {
    Vacancy getVacancy(int id);
    List<Vacancy> getVacancies();
    void addVacancy(Vacancy vac);
    void updateVacancy(Vacancy vac);
    void addAllVacancies(List<Vacancy> vc);
    void deleteVacancy(Vacancy vac);
    List<Vacancy> getVacancyByTitle(String s);
    List<Vacancy> getVacanciesByDescription(String keyword);
    List<Vacancy> getNewVacancies(List<Vacancy> vac);
}



