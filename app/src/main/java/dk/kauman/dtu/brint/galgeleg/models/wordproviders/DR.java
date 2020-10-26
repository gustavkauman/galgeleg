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
            data = WebHelper.cleanData(data);

            this.words = new ArrayList<String>(Arrays.asList(data.split("\\s+")));
            removeNonEssentialWords();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while getting data from dr.dk");
            e.printStackTrace();
        }

    }

    private void removeNonEssentialWords() {
        if (this.words == null) {
            System.out.println("Word list is currently empty. Skipping...");
            return;
        }

        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).length() <= 2) {
                words.remove(i);
            }
        }
    }

    @Override
    public List<String> getWords() {
        return this.words;
    }
}
