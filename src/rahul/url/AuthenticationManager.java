package rahul.url;

import java.io.*;
import java.sql.*;

public class AuthenticationManager {
    private static final String URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "rahul12345";

    public static boolean authenticateUser(String username, String password){
        if(!isValidInput(username) || !isValidInput(password))
            return false;

        try(Connection connection = DriverManager.getConnection(URL,USERNAME,PASSWORD)){
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
                preparedStatement.setString(1,username);
                preparedStatement.setString(2,password);
                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Sanitizing the input OR checking the input
    private static boolean isValidInput(String input){
        String regex = "^[a-zA-Z0-9]+$";
        return input.matches(regex);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your username: ");  String username =  br.readLine().toLowerCase();
        System.out.print("Enter your password: "); String password = br.readLine().toLowerCase();

        boolean isAuthenticated = authenticateUser(username,password);

        if (isAuthenticated)
            System.out.println("Authentication successful");
        else
            System.out.println("Authentication failed");
    }
}
