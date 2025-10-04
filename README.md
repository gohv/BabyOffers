# Baby Offers Scraper

This project scrapes promotional offers from various baby and children's stores and serves them via a simple Flask web server. The data is provided in a JSON format, which can be easily consumed by other applications.

## Setup and Installation

To get started with this project, you'll need to have Python 3 and `pip` installed.

1.  **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd <repository_directory>
    ```

2.  **Install the dependencies:**
    The required Python libraries are listed in the `requirements.txt` file. You can install them using `pip`:
    ```bash
    pip install -r requirements.txt
    ```

## Running the Application

The application is a Flask web server that scrapes the latest promotional offers and serves them via an API endpoint.

To run the server, execute the following command in the root of the project directory:
```bash
python3 app.py
```

The server will start on `0.0.0.0:5000`.

## API Endpoint

The scraped offers are available at the following endpoint:

*   **`GET /offers`**: Returns a JSON array of all the promotional offers found by the scrapers.

You can access this endpoint using a web browser or a command-line tool like `curl`:
```bash
curl http://127.0.0.1:5000/offers
```

## Scrapers

The project includes the following scraper:

*   **Hippoland**: Scrapes promotions from `https://www.hippoland.net/top-oferti`.

### Other Scrapers

The following scrapers from the original project could not be implemented due to the reasons listed below:

*   **Comsed**: The content on the promotions page is loaded dynamically using JavaScript, which cannot be scraped with the current toolset (`requests` and `BeautifulSoup`).
*   **Beby.bg (formerly Bebelandia)**: The content on the promotions page is loaded dynamically using JavaScript, which cannot be scraped with the current toolset.
*   **Deichmann**: The website returned a 404 (Not Found) error, indicating that the URL is no longer valid. Further investigation did not yield a stable promotions page.
*   **H&M**: The website returned a 403 (Forbidden) error, indicating that access is restricted.
*   **eMAG**: The website returned a 511 (Network Authentication Required) error, indicating that access is restricted.