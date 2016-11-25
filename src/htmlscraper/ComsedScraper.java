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
public class ComsedScraper extends AbstractScraper {

    String baseUrl = "http://comsed.net/%D0%B2%D1%81%D0%B8%D1%87%D0%BA%D0%B8?filter_promo_id=0";
    public static final int SHOP_IDENTIFIER = 1;


    public List<Offer> scrape() {return scrapePage(baseUrl);}

    public List<Offer> scrapePage(String url){
        List<Offer> offers = new ArrayList<>();

        try{
            UserAgent userAgent = new UserAgent();//create new userAgent (browser).
            userAgent.settings.checkSSLCerts = false;
            userAgent.sendGET(baseUrl, "user-agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");                                        //visit a url

            Element container = userAgent.doc.findFirst("<div class=\"row pl15\">"); // element = html tag
            Elements offerElements = container.findEach("<div class=\"product-info promo-product\">"); // list of elements = html tags

            for(Element e : offerElements)
            {
                    if(e.findEvery("<strike>").size() == 0) continue;

                    Offer offer = new Offer();

                    offer.setLinkToItem(e.findFirst("<a class=\"thumbnail\">").getAt("href"));
                    offer.setProductName(e.findFirst("<h2 class=\"product-title\">").innerText().trim());
                    offer.setProductPhoto(e.findFirst("<img>").getAt("src"));
                    offer.setOldPrice(getPrice(e.findFirst("<strike>").innerText()));
                    offer.setNewPrice(getPrice(e.findFirst("<strike>").nextSiblingElement().innerText()));
                    offer.setShopName("Comsed");
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
