package htmlscraper;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import model.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gohv on 19.08.16.
 */
public class HMScraper extends AbstractScraper {

    String baseUrl = "http://www2.hm.com/bg_bg/sale/shopbyproductkids/best-of-sale.html";


    @Override
    public List<Offer> scrape() {
        return scrapePage(baseUrl);
    }



    public List<Offer> scrapePage(String url){
        List<Offer> offers = new ArrayList<>();
        try{

            UserAgent userAgent = new UserAgent();//create new userAgent (browser).
            userAgent.settings.checkSSLCerts = false;
            userAgent.sendGET(baseUrl, "user-agent:Mozilla/5.0 (Windows NT 10.0; WOW64; rv:47.0) Gecko/20100101 Firefox/47.0");                                        //visit a url

            Element container = userAgent.doc.findFirst("<div class=\"product-items-wrapper\">"); // element = html tag
            Elements offerElements = container.findEach("<div class=\"grid col-3\"> "); // list of elements = html tags



            for(Element e : offerElements)
            {


                Offer offer = new Offer();

                offer.setProductName(e.findFirst("<h3>").innerText().trim());
                 offer.setLinkToItem(e.findFirst("<h3 class=\"product-item-headline\">").findFirst("<a>").getAt("href"));
                offer.setProductPhoto("http://www.southsidewandsworth.com/images/shops/logos/hmkids.png");
                offer.setOldPrice(getPrice(e.findFirst("<div class=\"product-item-price product-item-price-discount\">").findFirst("<small>").innerText()));
                offer.setNewPrice(getPrice(e.findFirst("<div class=\"product-item-price product-item-price-discount\">").innerText()));
                offer.setShopName("H&M");
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
