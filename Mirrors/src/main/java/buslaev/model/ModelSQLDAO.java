package buslaev.model;

import buslaev.build.Autowired;
import buslaev.build.Component;

import java.sql.*;

@Component
public class ModelSQLDAO {
    private static final String URL = "jdbc:postgresql://localhost:5432/mirrors";
    private static final String USER = "root";
    private static final String PASS = "root";

    private static Connection connection;
    @Autowired
    private MirrorSQLDAO mirrorSQLDAO;

    public ModelSQLDAO() {
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

    public Model getModel(int id){
        Model model = new Model();
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM model WHERE id = ?;");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            resultSet.next();
            model.setId(resultSet.getInt("id"));
            model.setStartX(resultSet.getInt("startX"));
            model.setStartY(resultSet.getInt("startY"));
            model.setDirectionX(resultSet.getInt("directionX"));
            model.setDirectionY(resultSet.getInt("directionY"));

            model.setMirrors(mirrorSQLDAO.getMirrorsForModel(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return model;
    }

    public void addNewModel(Model model){
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("""
                            INSERT INTO public.model(id,
                                                    startX,
                                                    startY,
                                                    directionX,
                                                    directionY)
                            VALUES (nextval('model_id_seq'), ?, ?, ?, ?);""");

            setStatement(model, preparedStatement);
            for (Mirror m: model.getMirrors()) {
                mirrorSQLDAO.addNewMirror(m);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateModel(Model model){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("""
                    UPDATE public.model SET
                    startX=?,
                    startY=?,
                    directionX=?,
                    directionY=? WHERE id=?;""");
            setStatement(model, preparedStatement);
            preparedStatement.setInt(5, model.getId());
            preparedStatement.executeUpdate();
            for (Mirror c: model.getMirrors()) {
                mirrorSQLDAO.updateMirror(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setStatement(Model model, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, model.getStartX());
        preparedStatement.setInt(2, model.getStartY());
        preparedStatement.setInt(3, model.getDirectionX());
        preparedStatement.setInt(4, model.getDirectionY());
    }
}
