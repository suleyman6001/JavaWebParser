import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class TestClass {
    public static void main(String[] args) {

        try {
            String urL = "https://www.jobsearch.az/vacancies70774_en.html";
            final Document doc = Jsoup.connect(urL).get();

            String e = doc.select("td.text").get(3).text();

            System.out.println(e);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
