package Repository.JDBC;

import Domain.Comanda;
import Domain.ItemComanda;
import Domain.StatusComanda;
import Repository.ComandaRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComandaJDBCRepo implements ComandaRepo {
    private JdbcUtils jdbcUtils;

    public ComandaJDBCRepo() {
        this.jdbcUtils = new JdbcUtils();
    }

    @Override
    public Comanda findID(Integer id) {
        return null;
    }

    @Override
    public List<Comanda> getAll() {
        List<Comanda> comenzi = new ArrayList<>();
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from comenzi")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("c_id");
                String deadline = resultSet.getString("deadline");
                String stringStatus = resultSet.getString("status");
                StatusComanda status = null;
                if(stringStatus.equals("NEONORATA")){
                    status = StatusComanda.NEONORATA;
                }
                if(stringStatus.equals("ONORATA")){
                    status=StatusComanda.ONORATA;
                }
                if(stringStatus.equals("INDISPONIBIL")){
                    status=StatusComanda.INDISPONIBIL;
                }
                Comanda comanda = new Comanda(id,deadline,status);
                comenzi.add(comanda);
            }
            return comenzi;
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Comanda add(Comanda comanda) {
        return null;
    }

    @Override
    public Comanda remove(Comanda comanda) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "delete from itemecomenzi where c_id=?")) {
            preparedStatement.setInt(1,comanda.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Err db "+e);
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "delete from comenzi where c_id=?")) {
            preparedStatement.setInt(1,comanda.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Err db "+e);
        }
        return comanda;
    }

    @Override
    public Comanda update(Comanda comanda) {
        return null;
    }

    @Override
    public List<Comanda> getCommandsOf(Integer a_id) {
        List<Comanda> comenzi = new ArrayList<>();
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select * from comenzi where a_id=?")) {
            preparedStatement.setInt(1,a_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("c_id");
                String deadline = resultSet.getString("deadline");
                String stringStatus = resultSet.getString("status");
                StatusComanda status = null;
                if(stringStatus.equals("NEONORATA")){
                    status = StatusComanda.NEONORATA;
                }
                if(stringStatus.equals("ONORATA")){
                    status=StatusComanda.ONORATA;
                }
                if(stringStatus.equals("INDISPONIBIL")){
                    status=StatusComanda.INDISPONIBIL;
                }
                Comanda comanda = new Comanda(id,deadline,status);
                comenzi.add(comanda);
            }
            return comenzi;
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public List<ItemComanda> getMedsOf(Integer c_id) {
        List<ItemComanda> iteme = new ArrayList<>();
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("" +
                "select * from itemecomenzi i inner join medicamente m on " +
                "i.m_id=m.m_id where i.c_id=?")) {
            preparedStatement.setInt(1,c_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id =  resultSet.getInt("i_id");
                Integer cant = resultSet.getInt("cantitate");
                String tip = resultSet.getString("tip");
                ItemComanda item = new ItemComanda(id,cant,tip);
                iteme.add(item);
            }
            return iteme;
        } catch (SQLException e) {
            //logger.error(e);
            System.out.println("Error DB " + e);
        }
        return null;
    }

    @Override
    public Comanda addCommand(Comanda comanda, Integer a_id) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "insert into comenzi (deadline,status,a_id) values " +
                "(?,'NEONORATA',?)")){
            preparedStatement.setString(1,comanda.getDeadline());
            preparedStatement.setInt(2,a_id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("err db "+e);
        }
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "select * from comenzi where c_id=(select max(c_id) from comenzi)")){
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String stringStatus = resultSet.getString("status");
            StatusComanda status = null;
            if(stringStatus.equals("NEONORATA")){
                status = StatusComanda.NEONORATA;
            }
            if(stringStatus.equals("ONORATA")){
                status=StatusComanda.ONORATA;
            }
            if(stringStatus.equals("INDISPONIBIL")){
                status=StatusComanda.INDISPONIBIL;
            }
            return new Comanda(resultSet.getInt("c_id"),
                    resultSet.getString("deadline"),
                    status);
        } catch (SQLException e) {
            System.out.println("err db "+e);
        }
        return null;
    }

    @Override
    public void honorCommand(Comanda comanda) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "UPDATE comenzi SET status='ONORATA' WHERE c_id=?")) {
            preparedStatement.setInt(1, comanda.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Comanda> filterByType(String text) {
        Connection connection = jdbcUtils.getConnection();
        List<Comanda>  comenzi = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT c.c_id,c.deadline,c.status FROM comenzi c " +
                "INNER JOIN itemecomenzi i " +
                "ON c.c_id=i.c_id INNER JOIN Medicamente m " +
                "ON i.m_id=m.m_id " +
                "WHERE m.tip=?")){
            preparedStatement.setString(1,text);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("c_id");
                String deadline = resultSet.getString("deadline");
                String stringStatus = resultSet.getString("status");
                StatusComanda status = null;
                if(stringStatus.equals("NEONORATA")){
                    status = StatusComanda.NEONORATA;
                }
                if(stringStatus.equals("ONORATA")){
                    status=StatusComanda.ONORATA;
                }
                if(stringStatus.equals("INDISPONIBIL")){
                    status=StatusComanda.INDISPONIBIL;
                }
                Comanda comanda = new Comanda(id,deadline,status);
                comenzi.add(comanda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comenzi;
    }

    @Override
    public List<Comanda> filterByDeadline(String toString) {
        Connection connection = jdbcUtils.getConnection();
        List<Comanda>  comenzi = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("" +
                "SELECT * FROM comenzi WHERE deadline=?")){
            preparedStatement.setString(1,toString);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt("c_id");
                String deadline = resultSet.getString("deadline");
                String stringStatus = resultSet.getString("status");
                StatusComanda status = null;
                if(stringStatus.equals("NEONORATA")){
                    status = StatusComanda.NEONORATA;
                }
                if(stringStatus.equals("ONORATA")){
                    status=StatusComanda.ONORATA;
                }
                if(stringStatus.equals("INDISPONIBIL")){
                    status=StatusComanda.INDISPONIBIL;
                }
                Comanda comanda = new Comanda(id,deadline,status);
                comenzi.add(comanda);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comenzi;
    }
}
