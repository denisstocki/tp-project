package tp.warcaby.serwer;

import java.sql.*;

public class CheckersDBConnection {

    final String url = "jdbc:mysql://localhost:3306/Checkers";

    final String username = "denis";

    final String password = "sined";

    private Connection connection;

    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertCourse(String course, String enemy, String gameType, String winner)  {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("INSERT INTO Games(enemy, course, game_type, winner) VALUES (?, ?, ?, ?);");
            statement.setString(1, enemy);
            statement.setString(2, course);
            statement.setString(3, gameType);
            statement.setString(4, winner);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String readGameType(int id)  {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT game_type FROM Games WHERE Games.id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String readCourse(int id)  {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT course FROM Games WHERE Games.id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect(){
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String readWinner(int id) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("SELECT winner FROM Games WHERE Games.id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
