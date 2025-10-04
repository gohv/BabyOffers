from flask import Flask, jsonify
from scrapers.hippoland import scrape_hippoland
import json

app = Flask(__name__)

def run_all_scrapers():
    all_offers = []

    hippoland_offers = scrape_hippoland()
    if hippoland_offers:
        all_offers.extend(hippoland_offers)

    return all_offers

@app.route('/offers', methods=['GET'])
def get_offers():
    offers = run_all_scrapers()
    return jsonify(offers)

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0')