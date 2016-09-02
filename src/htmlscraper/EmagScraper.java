package htmlscraper;

import com.jaunt.*;
import model.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Html downloader
 */
public class EmagScraper extends AbstractScraper {

        String baseUrl = "http://www.emag.bg/search/igrachki-detski-artikuli/promo/listall/p";

    public List<Offer> scrape()
    {
        List<Offer> offers = new ArrayList<>();
        List<Offer> tempOffers;

        int currentPage = 1;

        while (true)
        {
            tempOffers = scrapePage(baseUrl + currentPage);

            if (tempOffers == null) break;

            offers.addAll(tempOffers);
            currentPage++;
        }

        return offers;
    }

    public boolean isLastPage(Document doc) throws NotFound
    {
        Element pager = doc.findFirst("<div id=\"emg-pagination-numbers\">"); // find container of pagebuttons
        List<Element> children = pager.getChildElements();       // find all buttons


        return children.get(children.size()-2).getAt("class").equals("emg-pagination-no emg-pagination-selected");
    }

    public List<Offer> scrapePage(String url){
        List<Offer> offers = new ArrayList<>();

        try{
            UserAgent userAgent = new UserAgent();                       //create new userAgent (browser).
            userAgent.visit(url);                                        //visit a url
            Element container = userAgent.doc.findFirst("<div id=\"products-holder\">"); // element = html tag
            Elements offerElements = container.findEach("<div class=\"product-holder-grid\">"); // list of elements = html tags

            if (isLastPage(userAgent.doc))
            {
                return null;
            }

                for(Element e : offerElements){
                if(e.findEvery("<span class=\"old initial_price\">").size() == 0) continue;

                Offer offer = new Offer();
                offer.setLinkToItem(e.findEvery("<div class=\"middle-container\">").findFirst("<h2>").findFirst("<a>").getAt("href"));
                offer.setProductName(e.findFirst("<h2>").innerText().trim());
                offer.setProductPhoto(e.findFirst("<img>").getAt("data-src"));
                //offer.setProductPhoto("http:" + e.findFirst("<img>").getAt("data-src"));
                offer.setOldPrice(getPrice(e.findFirst("<span class=\"old initial_price\">").innerText()) / 100);
                offer.setNewPrice(getPrice(e.findFirst("<span class=\"price-over\">").innerText()) / 100);
                offer.setShopName("EMAG");
                offers.add(offer);
            }

            return offers;
        }
        catch(JauntException e)
        {
            System.err.println(e);
        }

        return null;
    }


}
