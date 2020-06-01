package edu.cmu.ds.ksoneja;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BirdsModel {

    public List<String> doBirdSearch(String searchTag, String picSize)
            throws UnsupportedEncodingException {
        /*
         * URL encode the searchTag, e.g. to encode spaces as %20
         *
         * There is no reason that UTF-8 would be unsupported.  It is the
         * standard encoding today.  So if it is not supported, we have
         * big problems, so don't catch the exception.
         */
        searchTag = URLEncoder.encode(searchTag, "UTF-8");
        //System.out.println(searchTag);
        String response = "";

        // Create a URL for the page to be screen scraped
        String birdUrl =
                "https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?pix="
                        + searchTag;


        // Fetch the page
        response = fetch(birdUrl);

        //Scrapping the bird names from the website

        String temp[] = response.split("<select name=\"pix\" id=\"pix\">");
        String content[] = temp[1].split("</select");
        String newtemp[] = content[0].split("</option>");
        List<String> birdnames = new ArrayList<>();

        for (int i = 0; i < newtemp.length - 1; i++) {
            String temp2[] = newtemp[i].split(">");
            birdnames.add(temp2[1]);
        }
        int count = 0;
        List<String> tempBirdList = new ArrayList<>();
        for (int i = 0; i < birdnames.size(); i++) {
            if (birdnames.get(i).contains(searchTag)) {
                tempBirdList.add(birdnames.get(i));
            }
        }

            //Randomly generating the bird names
            List<String> finalBirdList = new ArrayList<>();
            Random rand = new Random();
            if (tempBirdList.size() > 10) {
                while (count < 10) {
                    int j = rand.nextInt(tempBirdList.size());
                    if (!finalBirdList.contains(tempBirdList.get(j))) {
                        finalBirdList.add(tempBirdList.get(j));
                        count++;
                    }
                }
            } else {
                while (count < tempBirdList.size()) {
                    int j = rand.nextInt(tempBirdList.size());
                    if (!finalBirdList.contains(tempBirdList.get(j))) {
                        finalBirdList.add(tempBirdList.get(j));
                        count++;
                    }
                }
            }
        return finalBirdList;
        }

        /*
         * Return a URL of an image of appropriate size
         *
         * Arguments
         * @param picSize The string "mobile" or "desktop" indicating the size of
         * photo requested.
         * @return The URL an image of appropriate size.
         */
        private String interestingPictureSize (String pictureURL, String picSize){
            int finalDot = pictureURL.lastIndexOf(".");
            /*
             * From the flickr online documentation, an underscore and a letter
             * before the final "." and file extension is a size indicator.
             * "_m" for small and "-z" for big.
             */
            String sizeLetter = (picSize.equals("mobile")) ? "m" : "z";
            if (pictureURL.indexOf("_", finalDot - 2) == -1) {
                // If the URL currently did not have a _? size indicator, add it.
                return (pictureURL.substring(0, finalDot) + "_" + sizeLetter
                        + pictureURL.substring(finalDot));
            } else {
                // Else just change it
                return (pictureURL.substring(0, finalDot - 1) + sizeLetter
                        + pictureURL.substring(finalDot));
            }
        }

        /*
         * Make an HTTP request to a given URL
         *
         * @param urlString The URL of the request
         * @return A string of the response from the HTTP GET.  This is identical
         * to what would be returned from using curl on the command line.
         */
        private String fetch (String urlString){
            String response = "";
            try {
                URL url = new URL(urlString);
                /*
                 * Create an HttpURLConnection.  This is useful for setting headers
                 * and for getting the path of the resource that is returned (which
                 * may be different than the URL above if redirected).
                 * HttpsURLConnection (with an "s") can be used if required by the site.
                 */
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String str;
                // Read each line of "in" until done, adding each to "response"
                while ((str = in.readLine()) != null) {
                    // str is one line of text readLine() strips newline characters
                    response += str;
                }
                in.close();
            } catch (IOException e) {
                System.out.println("Eeek, an exception");
                // Do something reasonable.  This is left for students to do.
            }
            return response;
        }

        //Scrapping the bird image URL to display the image of the bird and the photographer name
        public String imageURL (String search) throws UnsupportedEncodingException {
            search = URLEncoder.encode(search, "UTF-8");
            String newbirdUrl =
                    "https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?pix="
                            + search;
            String res = fetch(newbirdUrl);
            String temp[] = res.split("<div id=\"picgrid\">");
            String newtemp[] = temp[1].split("</main>");
            String temp1[] = newtemp[0].split("<div>");

            String finalresult = null;
            List<String> imageURL = new ArrayList<>();
            for (int i = 1;i<temp1.length-1;i++) {

                //Temporary variables to extract the bird image link and photographer name
                String imagetemp[] = temp1[i].split("&copy;");
                String linktemp[] = imagetemp[0].split("<img src=\"");
               String temp2[] = linktemp[1].split("alt=\"[Photo]\" title");
               String temp3[] = temp2[0].split("title=\"Enlarge photograph\">");
               String temp4[] = temp3[0].split("=\"[Photo]\"");
               String temp5 = temp4[0].substring(0,temp4[0].indexOf("\" alt=\"[Photo]\""));
               String finalURL = temp5;

                String phototemp[] = imagetemp[1].split("</a>");
                String finalpname = phototemp[0];

                 finalresult = finalURL + "}" +finalpname;

            }
            return finalresult;
        }
    }

