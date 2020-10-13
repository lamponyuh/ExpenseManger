import java.sql.*;

public class DBController implements AutoCloseable{

    private static DBController instance;
    private final Connection con;
    private final Statement stmt;

    private DBController() throws SQLException {
        con = DriverManager.getConnection(ConnectionConf.URL, ConnectionConf.USER, ConnectionConf.PASSWORD);
        stmt = con.createStatement();
        System.out.println("Connection is done!!!");
    }

    static {
        try {
            instance = new DBController();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static DBController getInstance() {
        return instance;
    }

    private ResultSet selectRequest(String request) throws SQLException {
        return stmt.executeQuery(request);
    }

    private int updateRequest(String request) throws SQLException {
        return stmt.executeUpdate(request);
    }

    public ResultSet findUser(String login, String password) throws SQLException {
        String request = "SELECT * FROM users WHERE login = '" + login + "' AND password = '" + password + "';";
        ResultSet result = selectRequest(request);
        return  result.next() ? result : null;
    }

    public int createUser(String login, String password) throws SQLException {
        String request = "INSERT INTO users (`login`, `password`) VALUES ('" + login + "', '" + password + "');";
        return updateRequest(request);
    }

    @Override
    public void close() throws SQLException {
        con.close();
        stmt.close();
        System.out.println("Connection is closed!!!");
    }
}
