import requests
from bs4 import BeautifulSoup

def scrape_hippoland():
    base_url = "https://www.hippoland.net/top-oferti?p="
    offers = []
    page = 1
    while True:
        url = f"{base_url}{page}"
        try:
            print(f"Fetching Hippoland URL: {url}")
            response = requests.get(url, headers={'User-Agent': 'Mozilla/5.0'})
            response.raise_for_status()
            print(f"Hippoland response status: {response.status_code}")
            soup = BeautifulSoup(response.content, 'html.parser')

            product_elements = soup.select('li.product-item')
            print(f"Found {len(product_elements)} product elements on Hippoland page {page}.")
            if not product_elements:
                break

            for element in product_elements:
                old_price_element = element.select_one('span.old-price span.price')
                if not old_price_element:
                    continue

                name_element = element.select_one('a.product-item-link')
                if not name_element:
                    continue
                name = name_element.text.strip()
                link = name_element['href']
                image = element.select_one('img.product-image-photo')['src']
                old_price = old_price_element.text.strip()
                new_price = element.select_one('span.special-price span.price').text.strip()

                offers.append({
                    'product_name': name,
                    'product_photo': image,
                    'old_price': old_price,
                    'new_price': new_price,
                    'link_to_item': link,
                    'shop_name': 'Hippoland'
                })
            page += 1
        except requests.exceptions.RequestException as e:
            print(f"Error scraping Hippoland page {page}: {e}")
            break

    return offers

if __name__ == '__main__':
    hippoland_offers = scrape_hippoland()
    for offer in hippoland_offers:
        print(offer)