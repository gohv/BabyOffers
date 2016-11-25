package model;

import htmlscraper.*;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * this class will use all the scraper classes and will fill the database
 */
public class Downloader implements Runnable {

    private final int INTERVAL = 7200 * 1000;
    List<AbstractScraper> scrapers = new ArrayList<>();

    public void run() {
        // IF LESS THAN CERTAIN AMMOUNT OF OFFERS ARE AVAILABLE - APP CRASHES!!!!!!!!
        // add new scrapers below:
        scrapers.clear();

        scrapers.add(new ComsedScraper());
        scrapers.add(new DeichmannScraper());
        scrapers.add(new HMScraper());
        scrapers.add(new EmagScraper());
        scrapers.add(new BebelandiaScraper());
        scrapers.add(new HippolandScraper());
        scrapers.add(new HippolandChristmasScraper());

        while (true) {
            System.out.println("Downloader started....");
            Instant start = Instant.now();
            scrapeAll();
            System.out.println("Downloader finished " + Duration.between(start, Instant.now()).getSeconds() + " seconds");

            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void scrapeAll() {
        Offer.offers.clear();

        for (AbstractScraper a : scrapers) {

            try {
                System.out.println("\tStarted " + a.getClass().getName());
                Instant start = Instant.now();
                List<Offer> tempOffer = a.scrape();
                Offer.offers.addAll(tempOffer);
                System.out.println("\tfinished " +
                        a.getClass().getName() +
                        " " + Duration.between(start, Instant.now()).getSeconds() +
                        " seconds " + " Offer size: " + tempOffer.size());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

        }


        Collections.shuffle(Offer.offers);
        System.out.println("Total of " + Offer.getCounter() + " offers");
        Offer.save();
    }


}
