package dk.kauman.dtu.brint.galgeleg.models.wordproviders;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import dk.kauman.dtu.brint.galgeleg.helpers.WebHelper;

public class DR extends WordProvider {

    public DR() {}

    public void setupWords() {
        try {
            URL url = new URL("https://dr.dk");
            String data = WebHelper.getDataFromUrl(url);
            data = WebHelper.cleanData(data).
                    replaceAll(" [a-zæøå] ", " ").
                    replaceAll(" [a-zæøå][a-zæøå] "," ");

            this.words = new ArrayList<String>(Arrays.asList(data.split("\\s+")));

            cleanUpWords();
            notifyAllObservers();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error occurred while getting data from dr.dk");
            e.printStackTrace();
        }
    }

    private void cleanUpWords() {
        ArrayList<String> words2 = (ArrayList<String>) this.words.clone();
        int numberRemoved = 0;
        for (int i = 0; i < words2.size(); i++) {
            String word = words2.get(i);
            if (word.length() <= 2) {
                if (i - numberRemoved < 0)
                    return;

                this.words.remove(i - numberRemoved++);
            }
        }
    }
}
