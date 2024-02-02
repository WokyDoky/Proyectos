package Omok;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class HW5 {
    public String sendGet(String urlString) {
        HttpURLConnection con = null;
        try {
            URL url = new URL(urlString);
            con = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        String FORMAT = "http://omok.atwebpages.com/new/?strategy=%s";
        String strategy = "Smart";
        String url = String.format(FORMAT, strategy);
        String response = new HW5().sendGet(url);
        System.out.println(response);

        FORMAT = "http://omok.atwebpages.com/play/?pid=%s&x=%d&y=%d";
        String pid = "65663e869a86c";
        int x = 5;
        int y = 5;
        url = String.format(FORMAT, pid, x, y);
        response = new HW5().sendGet(url);
        System.out.println(response);

        FORMAT = "http://omok.atwebpages.com/play/?pid=%s";
        pid = "65663e869a86c";
        x = 0;
        y = 0;
        url = String.format(FORMAT, pid, -1, -1);
        response = new HW5().sendGet(url);
        System.out.println(response);

    }

}
