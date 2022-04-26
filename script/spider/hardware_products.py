from bs4 import BeautifulSoup
import requests
import time
from utils.mongoUtils import connect_device

def products_spider():
    url = 'https://www.cvedetails.com/product-list/product_type-h/vendor_id-0/firstchar-A/Hardwares.html'
    res = requests.get(url)
    soup = BeautifulSoup(res.text, features="lxml")
    num = 0
    collection = connect_device()
    for a in soup.find_all('a', title="Next Page-List of software or hardware products"):
        url = "https://www.cvedetails.com" + a["href"]
        res1 = requests.get(url)
        soup1 = BeautifulSoup(res1.text, features="lxml")
        table = soup1.find("table", class_="listtable")
        items = table.find_all("tr")[2:]
        for item in items:
            document = {}
            document["vendor"] = item.find_all("td")[1].text.strip()
            document["product"] = item.find_all("td")[0].text.strip()
            document["product_type"] = ""
            document["version"] = ""
            num += int(item.find_all("td")[2].text)
            collection.insert_one(document)
            print(document)
        time.sleep(30)
    print("漏洞总数：",num)

if __name__ == '__main__':
    products_spider()

