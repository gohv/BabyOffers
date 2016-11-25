package htmlscraper;

import model.Offer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class AbstractScraper {

    public abstract List<Offer> scrape();


    protected double getPrice(String str) {

        try {

            Pattern pattern = Pattern.compile("[0-9\\.]+");
            Matcher matcher = pattern.matcher(str.replace(",", "."));

            while (matcher.find()) {
                return Double.parseDouble(matcher.group());
            }

        }catch (NumberFormatException e){
            System.out.println("Cannot Set the price!!!");
            System.out.print(" !!Abstract Scraper!!!\n");
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }


}
