package com.company;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WikiPage {
    private static final String USER_AGENT = "Mozilla/5.0";

    private String payload = null;
    private URL url = null;

    WikiPage() { }

    WikiPage(final String _uri) throws IOException {
        url = makeURL(_uri);
        payload = getPage(url);
    }

    public static void main(String[] args) throws Exception {
        WikiPage page = new WikiPage();
        page.setURL("/wiki/home");
        System.out.println(page.getPayload());
    }

    // Make a proper url out of a wiki topic
    public void setURL(final String page_name) throws IOException {
        url = makeURL(page_name);
        setURL(url);
    }

    public void setURL(final URL new_url) throws IOException {
        url = new_url;
        payload = getPage(url);
    }

    public URL getURL() {
        return url;
    }

    public String getPayload() {
        return payload;
    }

    @Override
    public int hashCode() {
        if (url == null)
            return -1;
        return url.hashCode();
    }

    public boolean equals(final WikiPage p) {
        return p.url.equals(this.url);
    }

    private static URL makeURL(final String page_name) throws java.net.MalformedURLException {
        // If we are given a full URL already
        if (page_name.startsWith("http")) {
            return new URL(page_name);
        }
        // else if we're given /wiki/[topic]
        else if (page_name.contains("/wiki/")) {
            return new URL("https://en.wikipedia.org" + page_name);
        }
        // ELSE If we're given just a [topic]
        return new URL("https://en.wikipedia.org/wiki/" + page_name);
    }

    private static String getPage(URL url) throws java.io.IOException {
        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

}
