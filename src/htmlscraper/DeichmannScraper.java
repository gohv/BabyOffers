package htmlscraper;

import com.jaunt.Element;
import com.jaunt.Elements;
import com.jaunt.JauntException;
import com.jaunt.UserAgent;
import model.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Georgy on 8/3/2016.
 */
public class DeichmannScraper extends AbstractScraper {

    String baseUrl = "http://www.deichmann.com/BG/bg/shop/home-kinder/home-kinder-namalenia.cat";
    private String stringContainer = "<section id=\"product-list\">";

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

            Element container = userAgent.doc.findFirst(stringContainer); // element = html tag
            Elements offerElements = container.findEvery("<article>"); // list of elements = html tags

            for(Element e : offerElements) {


                Offer offer = new Offer();

                String productName = (e.findFirst("<div class=\"product-name\">").innerText().trim());
                String productBrand = (e.findFirst("<div class=\"product-brand\">").innerText().trim());


                //offer.setLinkToItem("http:"+e.findFirst("<a class=\"product-seolink\"").getAt("href"));
                offer.setLinkToItem("http://www.deichmann.com/BG/bg/shop/home-kinder/home-kinder-namalenia.cat");
                offer.setProductName(productName + " " +  productBrand);
                offer.setProductPhoto(e.findEvery("<img>").getElement(1).getAt("src"));
                offer.setOldPrice(getPrice(e.findFirst("<span class=\"line-through\">").innerText()));
                offer.setNewPrice(getPrice(e.findFirst("<div class=\"fe-first-price PRODUCT_GLOBAL_PRICE\">").innerText()));
                offer.setShopName("DeichMann");
                offers.add(offer);
            }

            return offers;
        }
        catch(JauntException e)
        {
            e.printStackTrace();
        }

        return null;
    }


}
