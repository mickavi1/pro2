package utils;

import model.FeedItem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    //metoda pro ukladani feed items
    public static void saveAllFeeds(List<FeedItem> items) {
        try {
            File file = new File("feedItems.csv");
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write("url;addedMillis;shouldShow;alias");
            bufferedWriter.newLine();
            bufferedWriter.flush();
            for (FeedItem item : items){
                bufferedWriter.write(item.toString());
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //metoda pro nacitani feeditems
    public static List<FeedItem> getAllFeeds() {
        List<FeedItem> feedItems = new ArrayList<>();
        try {
            File file = new File("feedItems.csv");
            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(reader);
            bufferedReader.readLine(); // přeskočit první řádek
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                feedItems.add(FeedItem.parseFromCSV(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return feedItems;
    }
}
