package dk.kauman.dtu.brint.galgeleg.models.wordproviders;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dk.kauman.dtu.brint.galgeleg.helpers.WebHelper;
import dk.kauman.dtu.brint.galgeleg.models.interfaces.WordProvider;

public class DR implements WordProvider {

    private ArrayList<String> words;


    public DR() {
        try {
            URL url = new URL("https://dr.dk");
            String data = WebHelper.getDataFromUrl(url);
            data = WebHelper.cleanData(data).
                    replaceAll(" [a-zæøå] ", " ").
                    replaceAll(" [a-zæøå][a-zæøå] "," ");

            this.words = new ArrayList<String>(Arrays.asList(data.split("\\s+")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while getting data from dr.dk");
            e.printStackTrace();
        }

    }

    @Override
    public List<String> getWords() {
        return this.words;
    }
}
