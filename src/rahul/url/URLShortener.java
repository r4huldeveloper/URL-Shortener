package rahul.url;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class URLShortener {
    private static final String BASE_URL = "http://rahul.url/";
    private static final int SHORT_URL_LENGTH = 6;
    private static final String ALGORITHM = "SHA-256";
    private static final String CHARACTERS = "abcedfghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Map<String,String> urlMap= new HashMap<>();
    private static final Random random = new Random();

    // Method to generate a random string for a short url
    private static String generateRandomString(){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < SHORT_URL_LENGTH; i++){
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(i));
        }
        return sb.toString();
    }

    // method to shorten a URL
    public static String shortenURL(String originalURL){
        if(!urlMap.containsValue(originalURL)){
            String shortURL = BASE_URL + generateRandomString();
            urlMap.put(shortURL,originalURL);
            return shortURL;
        }else {
            return urlMap.entrySet().stream().filter(entry -> entry.getValue().equals(originalURL))
                    .findFirst().get().getKey();
        }
    }

    //  method to expand a short url to its original url
    public static String expandURL(String shortURL){
        return urlMap.getOrDefault(shortURL,"Short URL not found");
    }

 /*
    public static String shortenURL(String originalURL){
        try {
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
            byte[] hash = digest.digest(originalURL.getBytes());
            String encodeHash = Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
            return BASE_URL + encodeHash.substring(0,6);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
*/

    public static void main(String[] args) {
        String originalURL = "https://www.javatpoint.com/PreparedStatement-interface";
        String shortURL = shortenURL(originalURL);
        System.out.println(shortURL);

        String expandedURL = expandURL(shortURL);
        System.out.println(expandedURL);
    }
}
