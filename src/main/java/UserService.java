import org.apache.commons.codec.digest.DigestUtils;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserService {
    private static final UserService instance;
    private final DBController db;


    private UserService() {
        db = DBController.getInstance();
    }

    static {
        instance = new UserService();
    }


    public static UserService getInstance() {
        return instance;
    }

    private String toSHA256(String string) {
        return DigestUtils.sha256Hex(string);
    }

    public User login(String login, String password) throws SQLException {
        ResultSet userRow = db.findUser(login,toSHA256(password));
        return userRow != null ? new User(userRow.getInt("id"), userRow.getString("login")) : null;

    }

    public User createUser(String login, String password) throws SQLException {
        int resultOfCreateUser = db.createUser(login,toSHA256(password));
        return resultOfCreateUser == 1 ? login(login, password) : null;
    }
}
