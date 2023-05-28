package buslaev.model;

import buslaev.build.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MirrorSQLDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/mirrors";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static Connection connection;

    public MirrorSQLDAO() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Mirror> getMirrorsForModel(int  modelId){
        List<Mirror> mirrors = new ArrayList<>();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM mirror WHERE model_id = ?;");
            preparedStatement.setInt(1, modelId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Mirror mirror = new Mirror();
                mirror.setId(resultSet.getInt("id"));
                mirror.setSphere(resultSet.getBoolean("isSphere"));
                mirror.setConcave(resultSet.getBoolean("isConcave"));
                mirror.setR(resultSet.getInt("R"));
                mirror.setPosX(resultSet.getInt("posX"));
                mirror.setPosY(resultSet.getInt("posY"));
                mirrors.add(mirror);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return mirrors;
    }

    public void addNewMirror(Mirror mirror){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                            INSERT INTO public.mirror (id,
                                                    isSphere,
                                                    isConcave,
                                                    R,
                                                    posX,
                                                    posY,
                                                    model_id)
                            VALUES (nextval('mirror_id_seq'), ?, ?, ?, ?, ?, ?);""");

            setStatement(mirror, preparedStatement);

            PreparedStatement seqStat = connection.prepareStatement("SELECT last_value FROM model_id_seq;");
            ResultSet resultSet = seqStat.executeQuery();
            resultSet.next();
            int nextVal = resultSet.getInt("last_value");
            preparedStatement.setInt(6, nextVal);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateMirror(Mirror mirror){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE public.mirror SET
                    isSphere=?,
                    isConcave=?,
                    R=?,
                    posX=?,
                    posY=? WHERE id=?;""");
            setStatement(mirror, preparedStatement);
            preparedStatement.setInt(6, mirror.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setStatement(Mirror mirror, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setBoolean(1, mirror.isSphere());
        preparedStatement.setBoolean(2, mirror.getConcave());
        preparedStatement.setInt(3, mirror.getR());
        preparedStatement.setInt(4, mirror.getPosX());
        preparedStatement.setInt(5, mirror.getPosY());
    }
}
