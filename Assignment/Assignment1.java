package Assignment;
	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.net.HttpURLConnection;
	import java.net.URL;
	import java.util.ArrayList;
	import java.util.List;
	import java.util.regex.Matcher;
	import java.util.regex.Pattern;
	public class Assignment1 {

	    public static void main(String[] args) {
	        String timeStoriesJson = getTimeStories();
	        System.out.println(timeStoriesJson);
	    }

	    public static String getTimeStories() {
	        String url = "https://time.com";
	        StringBuilder content = new StringBuilder();

	        try {
	            // Creating a URL object
	            URL timeUrl = new URL(url);

	            // Creating a HttpURLConnection
	            HttpURLConnection connection = (HttpURLConnection) timeUrl.openConnection();

	            // Setting request method
	            connection.setRequestMethod("GET");

	            // Reading response
	            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                content.append(inputLine);
	            }
	            in.close();

	            // Closing connection
	            connection.disconnect();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "{\"error\": \"" + e.getMessage() + "\"}";
	        }

	        // Extracting the latest 6 stories using regex
	        String pattern = "<h2 class=\"title\">.*?<a href=\"(.*?)\".*?>(.*?)</a>";
	        Pattern r = Pattern.compile(pattern);
	        Matcher m = r.matcher(content);

	        List<String> stories = new ArrayList<>();
	        while (m.find() && stories.size() < 6) {
	            String link = m.group(1);
	            String title = m.group(2);
	            stories.add("{\"title\": \"" + title + "\", \"link\": \"" + link + "\"}");
	        }

	        // Building JSON response
	        StringBuilder json = new StringBuilder("[");
	        for (int i = 0; i < stories.size(); i++) {
	            json.append(stories.get(i));
	            if (i < stories.size() - 1) {
	                json.append(", ");
	            }
	        }
	        json.append("]");

	        return json.toString();
	    }
	}


