package dk.kauman.dtu.brint.galgeleg.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Helper class to help with data retrieval from
 * the world wide web.
 */
public class WebHelper {

    /**
     * Get data from a URL
     * Original code from https://github.com/nordfalk/Galgeleg
     *
     * @param url URL, the url to get data from.
     * @return String, the data read from the url.
     * @throws IOException is thrown if the program is unable to get data from the specified url
     */
    public static String getDataFromUrl(URL url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        while (br.readLine() != null) {
            sb.append(br.readLine() + "\n");
        }

        return sb.toString();
    }

    /**
     * Clean data from the web.
     * Original code from https://github.com/nordfalk/Galgeleg
     *
     * @param data String, data to be cleaned
     * @return String, cleaned data
     */
    public static String cleanData(String data) {
        return data.substring(data.indexOf("<body")). // fjern headere
                replaceAll("<.+?>", " ").toLowerCase(). // fjern tags
                replaceAll("&#198;", "æ"). // erstat HTML-tegn
                replaceAll("&#230;", "æ"). // erstat HTML-tegn
                replaceAll("&#216;", "ø"). // erstat HTML-tegn
                replaceAll("&#248;", "ø"). // erstat HTML-tegn
                replaceAll("&oslash;", "ø"). // erstat HTML-tegn
                replaceAll("&#229;", "å"). // erstat HTML-tegn
                replaceAll("[^a-zæøå]", " "); // fjern tegn der ikke er bogstaver
    }

}
