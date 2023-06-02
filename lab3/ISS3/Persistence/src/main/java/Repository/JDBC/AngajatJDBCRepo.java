package Repository.JDBC;

import Domain.Angajat;
import Domain.Farmacist;
import Domain.PM;
import Repository.AngajatRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AngajatJDBCRepo implements AngajatRepo {
    private JdbcUtils jdbcUtils;

    public AngajatJDBCRepo() {
        jdbcUtils = new JdbcUtils();
    }

    public Angajat findUserPass(String username, String password){
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "select * from angajati where username=? and password=?")) {
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Integer id = resultSet.getInt("a_id");
                Integer tip = resultSet.getInt("tip");
                if(tip==0){
                    return new Farmacist(id,username,password);
                }else {
                    return new PM(id,username,password);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB "+e);
        }
        return null;
    }
    @Override
    public Angajat findID(Integer id) {
        return null;
    }

    @Override
    public List<Angajat> getAll() {
        return null;
    }

    @Override
    public Angajat add(Angajat angajat) {
        return null;
    }

    @Override
    public Angajat remove(Angajat angajat) {
        return null;
    }

    @Override
    public Angajat update(Angajat angajat) {
        return null;
    }
}
