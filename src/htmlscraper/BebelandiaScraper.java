package htmlscraper;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import model.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Html downloader
 */
public class BebelandiaScraper extends AbstractScraper {

    String baseUrl = "http://www.bebelandia.bg/best-prices.html";
    public static final int SHOP_IDENTIFIER = 0;



    public List<Offer> scrape() {return scrapePage(baseUrl);}

    public List<Offer> scrapePage(String url){
        List<Offer> offers = new ArrayList<>();
        try{
            UserAgent userAgent = new UserAgent();//create new userAgent (browser).
            userAgent.settings.checkSSLCerts = false;
            userAgent.sendGET(baseUrl, "user-agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");                                        //visit a url

            Element container = userAgent.doc.findFirst("<div class=\"page-content-listing-bg-repeat\">"); // element = html tag
            Elements offerElements = container.findEach("<div class=\"badge\">"); // list of elements = html tags



            for(Element e : offerElements)
            {


                    Offer offer = new Offer();

                    offer.setProductName(e.findFirst("<h3>").innerText().trim());
                    offer.setLinkToItem(e.findFirst("<a class=\"product-tm\">").getAt("href"));
                    offer.setProductPhoto(e.findFirst("<img>").getAt("src"));
                    offer.setOldPrice(getPrice(e.findFirst("<div class=\"list-price\">").findEvery("<del class=\"price-value\">").innerText()));


                    offer.setNewPrice(getPrice(e.findFirst("<div class=\"product-details-bgl bottom-price\">")
                            .findFirst("<div class=\"product-details-bgr\">")
                            .findFirst("<div class=\"product-prices2\">")
                            .findFirst("<div class=\"product-prices \">")
                            .findEvery("<div class=\"final-price\">")
                            .findEvery("<div class=\"price-withtax\">")
                            .findEvery("<span class=\"price-value price-is-discounted\">").innerText()) / 100);



                    offer.setShopName("Bebelandia");
                    offer.setShopIdentifier(SHOP_IDENTIFIER);
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
