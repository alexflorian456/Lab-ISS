package Repository.JDBC;

import Repository.MedicamentRepo;
import Domain.Medicament;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicamentJDBCRepo implements MedicamentRepo {
    private JdbcUtils jdbcUtils;

    public MedicamentJDBCRepo() {
        jdbcUtils = new JdbcUtils();
    }

    @Override
    public Medicament findID(Integer id) {
        return null;
    }

    @Override
    public List<Medicament> getAll() {
        return null;
    }

    @Override
    public Medicament add(Medicament medicament) {
        return null;
    }

    @Override
    public Medicament remove(Medicament medicament) {
        return null;
    }

    @Override
    public Medicament update(Medicament medicament) {
        return null;
    }

    @Override
    public List<String> getMedTypes() {
        List<String> types = new ArrayList<>();
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "select distinct tip " +
                "from medicamente")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                String type = resultSet.getString("tip");
                types.add(type);
            }
            return types;
        } catch (SQLException e) {
            System.out.println("err db "+e);
        }
        return null;
    }

    @Override
    public Integer findIDof(String tip) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "select * from medicamente " +
                "where tip=?")){
            preparedStatement.setString(1,tip);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("m_id");
        } catch (SQLException e) {
            System.out.println("err db "+e);
        }
        return null;
    }

    @Override
    public void addCommandItem(Integer c_id, Integer m_id, Integer cantitate) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "insert into itemecomenzi(c_id,m_id,cantitate)values " +
                "(?,?,?)")){
            preparedStatement.setInt(1,c_id);
            preparedStatement.setInt(2,m_id);
            preparedStatement.setInt(3,cantitate);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("err db "+e);
        }
    }
}
