package htmlscraper;

import com.jaunt.*;
import model.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Html downloader
 */
public class HippolandScraper extends AbstractScraper {

    String baseUrl = "http://www.hippoland.net/top-oferti?p=";

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
        Element pager = doc.findFirst("<div class=\"paging\">");
        List<Element> children = pager.getChildElements();

        return children.get(children.size()-1).getAt("class") == "selected";
    }

    public List<Offer> scrapePage(String url){
        List<Offer> offers = new ArrayList<>();

        try{
            UserAgent userAgent = new UserAgent();                       //create new userAgent (browser).
            userAgent.visit(url);                                        //visit a url
            Element container = userAgent.doc.findFirst("<div class=\"productList\">"); // element = html tag
            Elements offerElements = container.findEach("<div class=\"productBox.*\">"); // list of elements = html tags

            if (isLastPage(userAgent.doc))
            {
                return null;
            }

            for(Element e : offerElements){

                Offer offer = new Offer();
                offer.setLinkToItem(e.findFirst("<a title>").getAt("href"));
                offer.setProductName(e.findFirst("<h2>").innerText().trim());
                offer.setProductPhoto(e.findFirst("<img>").getAt("src"));
                offer.setOldPrice(getPrice(e.findFirst("<p class=\"old-price\">").innerText()));
                offer.setNewPrice(getPrice(e.findFirst("<span class=\"special-price\">").innerText()));
                offer.setShopName("Hippoland");
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
