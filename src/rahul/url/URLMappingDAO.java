package rahul.url;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class URLMappingDAO {
    private static final String INSERT_QUERY = "INSERT INTO url_mapping (original_url, short_url) VALUES (?,?)";
    private static final String FIND_BY_SHORT_URL_QUERY = "SELECT original_url FROM url_mapping WHERE short_url=?";

    public static void saveMapping(String originalURL, String shortURL) throws SQLException{
        try(Connection con = URLShortenerDatabaseConnector.getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_QUERY)){
            ps.setString(1,originalURL);
            ps.setString(2,shortURL);
            ps.executeUpdate();
        }
    }

    public static String findOriginalURL(String shortURL) throws SQLException{
        try(Connection con = URLShortenerDatabaseConnector.getConnection();
        PreparedStatement ps = con.prepareStatement(FIND_BY_SHORT_URL_QUERY)){
            ps.setString(1,shortURL);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    return rs.getString("original_url");
            }
        }
        return null;
    }
}
