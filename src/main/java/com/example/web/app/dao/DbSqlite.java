package com.example.web.app.dao;

import com.example.web.app.api.request.Man;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DbSqlite implements InitializingBean {
    private Logger log = Logger.getLogger(getClass().getName());
    private static String dbPath = "webappp-example.db";

    @Override
    public void afterPropertiesSet() throws Exception {
        initDb();
    }

    public void initDb() {
        try {
            Class.forName("org.sqlite.JDBC");
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath)) {
            }
        } catch (ClassNotFoundException | SQLException ex) {
            log.log(Level.WARNING, "База не подключена", ex);
        }
    }

    public Boolean execute(String query) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            return stat.execute(query);
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос", ex);
            return false;
        }
    }

    //Когда ввел данные
    public static void insertMan(String name, String fam, String secondName, String university, Integer age, Integer course, String group, String login, String password, String gender, String kindofeducation, String role) {
        //  User user = new User(man.getId(), man.getName() , man.getFamily() , man.getUniversity());
        // String query = ("insert into Man (Name,Family,SecondName,University,Age,Course,Gr) values('" + name + "','" + fam + "','" + secondName + "','" + university + "','" + age + "','" + course + "','" + group + "')");
        String query = "insert into Man (Name,Family,SecondName,University,Age,Course,Gr,Login,Password,Gender,Kindofeducation,Role) " + "values ('%s', '%s', '%s', '%s', '%d', '%d', '%s', '%s', '%s', '%s', '%s', '%s')";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            stat.executeUpdate(String.format(query, name, fam, secondName, university, age, course, group, login, password, gender, kindofeducation, role));
        } catch (SQLException ex) {
            System.out.println("Ошибка  записи в БД" + ex.getMessage() + ex.getCause());
        }
    }

    public  Man selectUserById(int id) {
        String query = "select * from Man where id = " + id;
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            Man man = new Man();
            man.setName(resultSet.getString("Name"));
            man.setFamily(resultSet.getString("Family"));
            man.setSecondname(resultSet.getString("SecondName"));
            man.setUniversity(resultSet.getString("University"));
            man.setAge(resultSet.getInt("Age"));
            man.setCourse(resultSet.getInt("Course"));
            man.setGroup(resultSet.getString("Gr"));
            man.setLogin(resultSet.getString("Login"));
            man.setGender(resultSet.getString("Gender"));
            man.setKindeducation(resultSet.getString("Kindofeducation"));
            man.setRole(resultSet.getString("Role"));
            man.setAllid(getUsersId());
            return man;

        } catch (SQLException ex) {
            System.out.println("Ошибка получения пользователя из БД" + ex.getMessage());
            log.log(Level.WARNING, "Не удалось выбрать пользователя", ex);
            return new Man();
        }
    }

    public List getUsersId() {
        String query = "select ID from Man";
        List<Integer> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getInt("ID"));
            }
            return list;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос на получение списка id", ex);
            return null;
        }

    }

    public List getUsersLogin() {
        String query = "select Login from Man";
        List<String> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            while (resultSet.next()) {
                list.add(resultSet.getString("Login"));
            }
            return list;
        } catch (SQLException ex) {
            log.log(Level.WARNING, "Не удалось выполнить запрос на получение логинов", ex);
            return null;
        }

    }

    public static Man selectUserByLogin(String login) {
        String query = "select * from Man where Login = " + "'" + login + "'";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            ResultSet resultSet = stat.executeQuery(query);
            Man man = new Man();
            man.setName(resultSet.getString("Name"));
            man.setFamily(resultSet.getString("Family"));
            man.setSecondname(resultSet.getString("SecondName"));
            man.setUniversity(resultSet.getString("University"));
            man.setAge(resultSet.getInt("Age"));
            man.setCourse(resultSet.getInt("Course"));
            man.setGroup(resultSet.getString("Gr"));
            man.setLogin(resultSet.getString("Login"));
            man.setGender(resultSet.getString("Gender"));
            man.setKindeducation(resultSet.getString("Kindofeducation"));
            man.setPassword(resultSet.getString("Password"));
            man.setRole(resultSet.getString("Role"));
            return man;

        } catch (SQLException ex) {
            System.out.println("Ошибка получения пользователя из БД (Login)" + ex.getMessage());
            Man errorman = new Man();
            errorman.setLogin("Error");
            errorman.setPassword("Error");
            errorman.setRole("Error");
            return errorman;

        }
    }

    public static void changeTable(String name, String fam, String secondName, String university, Integer age, Integer course, String group, String login,String me) {
        String query = "UPDATE Man set Name = '%s' , Family = '%s' ,Secondname = '%s' , University = '%s' , Age = '%d',Course = '%d' , Gr = '%s' , Login = '%s' " + "WHERE Login = '%s'";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            stat.executeUpdate(String.format(query, name, fam, secondName, university, age, course, group, login,me));

        } catch (SQLException ex) {
            System.out.println("Ошибка модификации профиля)" + ex.getMessage());
        }
    }
    public static void changeaccadm(String name, String fam, String secondName, String university, Integer age, Integer course, String group, String login,String gender,String kindofeducation,String role,Integer id) {
        String query = "UPDATE Man set Name = '%s' , Family = '%s' ,Secondname = '%s' , University = '%s' , Age = '%d',Course = '%d' , Gr = '%s' , Login = '%s', Gender = '%s', Kindofeducation = '%s', Role = '%s' " + "WHERE id = '%d'";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            stat.executeUpdate(String.format(query, name, fam, secondName, university, age, course, group, login,gender,kindofeducation,role,id));

        } catch (SQLException ex) {
            System.out.println("Ошибка модификации профиля)" + ex.getMessage());
        }
    }
    public static void deletAcc(Integer id) {
        String query = "DELETE FROM Man WHERE id='%d'";
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
             Statement stat = conn.createStatement()) {
            stat.executeUpdate(String.format(query,id ));

        } catch (SQLException ex) {
            System.out.println("Ошибка модификации профиля)" + ex.getMessage());
        }
    }


}