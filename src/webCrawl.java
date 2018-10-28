import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


public class webCrawl {


    /*
        This project is based on using json.simple to parse a JSON 'internet' file

        While this was initially built with most of the code in the main class,
        this project is currently undergoing changes and evolving, breaking apart
        the initial 'two' sections (1: The JSON Parser, 2: The Crawler). The sections
        are now being broken apart into smaller methods and also being placed into
        separate classes for easier management, and also because my Razer mouse dislikes
        scrolling large documents.


        The "classForJSONParser" was given a clever name for a good reason, it houses the method
        used with json.simple to parse the JSON object. This class still needs to be modified to
        handle "List" type instead on "ArrayList", but that will be for a future update


        Likewise, "theCrawlerInAClass" it the web-crawler code... in a class. It should take the
        object filled in with "classForJSONParser" and print out the results that we are looking
        for. This one again will also need a future update to handle "List".


        Future changes
        -convert "ArrayList" items to "List"
        -creat a "loop" method or a better search method for finding the current address in the crawl
        stack with the other ArrayLists.
        -remove old test code
        -rename variables and classes that are more descriptive and better suited
        -add better testing technology
        -eat extremely hot hotsauce!


     */


    @SuppressWarnings("unchecked")
    public static void main(String[] args){


        //The stack is used to crawl through each page of a site
        Stack<String> webCrawler = new Stack<String>();

        //these arrays track successfully loaded pages, pages seen, and pages that don't exist
        ArrayList<String> successPages = new ArrayList<>();
        ArrayList<String> skippedPages = new ArrayList<>();
        ArrayList<String> errorPages = new ArrayList<>();

        //this array uses a class 'worthless' to store page addresses and a list
        //of their links on the page
        ArrayList<worthless> webj = new ArrayList<>();
        ArrayList<Pages> webj2 = new ArrayList<>();

        //GettingJSONParsed(webj2);

        //webCrawlerForJson(webj2);


        /*
            USING theCrawlerInAClass

            here we make an object of the class, and apply the same
            webCrawlerForJson method below that is now also in the class
            and send over the 'webj2' (it's version 2.0!) to the object
         */

        Object thisJSONParser = new classForJSONParser(webj2);
        ((classForJSONParser)thisJSONParser).GettingJSONParsed(webj2);

        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println("//////////////////////////the crawler class/////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
         Object thisCrawler =   new theCrawlerInAClass(webj2);

         ((theCrawlerInAClass) thisCrawler).webCrawlerForJson(webj2);
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");



    }


    public static ArrayList<Pages> GettingJSONParsed ( ArrayList<Pages> someWebj){
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
        //creating a parser
        JSONParser parser = new JSONParser();
        try{

            //creating a object that contains the contents of the JSON file
            Object obj3 = parser.parse(new FileReader("resources/JSONfile.json"));
            //converting the object into a usable JSONObject
            JSONObject obj4 = (JSONObject) obj3;
            //checking size
            System.out.println("obj size:     "+ obj4.size());
            //collecting the sites within pages
            JSONArray websites = (JSONArray) obj4.get("pages");

            System.out.println(websites);

            System.out.println("current size:  "+ websites.size());

            //checking what each page has
            for (int i=0; i < websites.size(); i++){
                System.out.println(websites.get(i).toString());
            }

            System.out.println("////////////////////////////////////////////////////////////////////////////////////////");

            Iterator i = websites.iterator();
            /*
                HERE IS WHERE WE GET THE SITE ADDRESS
             */
            while(i.hasNext()){
                JSONObject slide = (JSONObject) i.next();
                String title = (String)slide.get("address");
                System.out.println(title);
                String theAddress = title;

                JSONArray thelinksforaddress = (JSONArray) slide.get("links");
                System.out.println("new size: "+ thelinksforaddress.size());
                System.out.println(slide.get("links"));


                ArrayList<String> gettingTheLinks = new ArrayList<>();
                for(int i1 = 0; i1 < thelinksforaddress.size(); i1++){
                    /*
                        HERE IS WHERE WE GET THE LINKS
                     */
                    System.out.println(thelinksforaddress.get(i1).toString());
                    gettingTheLinks.add(thelinksforaddress.get(i1).toString());
                }
                System.out.println("array list size:  "+gettingTheLinks.size());

                /*
                HERE IS WHERE WE ADD THE JSON ELEMENTS TO THE LIST
                 */
                System.out.println("CHECKING");
                System.out.println(theAddress + "    " +gettingTheLinks.size());
                Pages someWorthless = new Pages(theAddress, gettingTheLinks);
                someWebj.add(someWorthless);

                System.out.println(someWebj.size()+"!!!!");
            }

            System.out.println("////////////////////////////////////////////////////////////////////////////////////////");

            /*
                json.simple likes to throw errors until you catch all the Exceptions

             */
        }catch (ParseException e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }



        /*
            TESTING WHAT IS IN THE LIST BEFORE GOING IN THE STACK
         */
        int checker =4;
        System.out.println("web j at 10: " +someWebj.get(checker).getAddress()+" " +someWebj.get(checker).getLinks().size());
        for(int i =0; i <someWebj.get(checker).getLinks().size(); i++){
            System.out.println(someWebj.get(checker).getAddressFromList(i));
        }

        System.out.println("CURRENT WEBJ: " + someWebj.size());

        return  someWebj;
    }











    public static void webCrawlerForJson(ArrayList<Pages> webj){

        Stack<String> webCrawler2 = new Stack<String>();

        ArrayList<String> successPages2 = new ArrayList<>();
        ArrayList<String> skippedPages2 = new ArrayList<>();
        ArrayList<String> errorPages2 = new ArrayList<>();
        /*
            WEB CRAWLER

            the webcrawler works by starting with the first webpage, taking the String of its address
            and the Int location of that address. The String will be used to compare the current page
            with the String of the link elements on the select page, and the judge whether the page
            has already been added to the stack (that has yet to be seen), if it already has been successfully
            views (successPages) and add it to skippedPages, if we determine that it's already in skippedPages
            and just ignore it, or if it does not exist in the available addresses and therefore added
            to errorPages
         */

        //first we initialize the components needed to get the crawler to work

        //holds the string value of the address given
        String currentPage ="";
        //its position within the pages
        int currentPageint =0;

        //webCrawler.push("http://foo.bar.com/p1");
        //we initialize the start of the stack with the first address
        webCrawler2.push(webj.get(0).getAddress());

        /*
            DISCLAIMER
            While we could use a "while(webCrawler.size() != 0)" loop for the crawler,
            I found it useful to debug by being able to control the number of steps the loop takes
            and seeing what the progress is of each list and the stack.

            for the example case, the max number of loops needed is 7, but we can use larger values
            which would still end the loop once the webCrawler is empty

            Also to note, when running the test case, the '==' comparator works for evaluation.
            However, once we use the JSON elements added to webj, they stop working. for
            comparing the strings we need to use the '.equals()' method.

         */
        int MAX_CYCLE =7;
        while(webCrawler2.size() != 0){
            //for(int cycle =0; cycle < MAX_CYCLE; cycle++){

            if(webCrawler2.size() ==0){
                break;
            }

            /*
                GETTING CURRENT PAGE
             */
            currentPage = webCrawler2.pop();
            webCrawler2.push(currentPage);
            System.out.println("current page address: "+currentPage);

            /*
            for(int i =0; i<webj.size(); i++){
                System.out.println("checking "+ webj.get(i).getAddress() +" with " +currentPage);
                if(webj.get(i).getAddress().equals(currentPage)){
                    //if(webj.get(i).getAddress() == currentPage){
                    currentPageint = i;
                    System.out.println("success");
                    break;
                }

            }
            */
            ////NOW REPLACED WITH A LINE OF CODE WOO!!!///////
            currentPageint = getCurrentPage(currentPage, webj);


            System.out.println("current page location: "+currentPageint);

            /*
                GETTING ERROR PAGE
             */
            if(!webj.get(currentPageint).getAddress().equals(currentPage) ){
                //if(webj.get(currentPageint).getAddress() != currentPage){
                errorPages2.add(currentPage);
                webCrawler2.pop();
                System.out.println("failed to find address");
                continue;
            }
            /*
                GETTING SUCCESS PAGE
             */

            //page was loaded successfully
            //and popped from the stack
            successPages2.add(webCrawler2.pop());

            //checking list if there are any links
            if(webj.get(currentPageint).getLinks().size() ==0){
                System.out.println("empty list");
            }

            /*
                CHECKING IF ADDRESS HAS BEEN SEEN
             */
            for (int i =0; i < webj.get(currentPageint).getLinks().size(); i++){
                boolean isSeen = false;


                //checking the skipped list if the address has been already added
                if(skippedPages2.size() >0){
                    for(int k=0; k<skippedPages2.size(); k++){
                        if(webj.get(currentPageint).getAddressFromList(i).equals(skippedPages2.get(k))){
                            //if(webj.get(currentPageint).getStringFromList(i) == skippedPages.get(k)){
                            System.out.println("the "+webj.get(currentPageint).getAddressFromList(i)+" already skipped");
                            isSeen = true;
                            continue;
                        }
                    }
                    if(isSeen == true){
                        continue;
                    }
                }

                //if page has been successfully seen
                for(int j = 0; j<successPages2.size(); j++){
                    if(webj.get(currentPageint).getAddressFromList(i).equals(successPages2.get(j))){
                        //if(webj.get(currentPageint).getStringFromList(i) == successPages.get(j)){
                        isSeen = true;
                        skippedPages2.add(webj.get(currentPageint).getAddressFromList(i));
                        System.out.println("adding "+webj.get(currentPageint).getAddressFromList(i)+" to skipped, already seen");
                    }
                }
                //if the page has already been queued in the stack
                for(int j = 0; j<webCrawler2.size(); j++){
                    if(webj.get(currentPageint).getAddressFromList(i).equals(webCrawler2.get(j))){
                        //if(webj.get(currentPageint).getStringFromList(i) == webCrawler.get(j)){
                        isSeen =true;
                        skippedPages2.add(webj.get(currentPageint).getAddressFromList(i));
                        System.out.println("adding "+webj.get(currentPageint).getAddressFromList(i)+" to skipped, already in the stack");
                    }
                }
                //if the address has not been visited yet and not yet added to the stack, we push it to the stack
                if(isSeen == false){
                    webCrawler2.push(webj.get(currentPageint).getAddressFromList(i));
                    System.out.println("pushing "+webj.get(currentPageint).getAddressFromList(i)+" to stack");
                }
            }
        }

        /*
            TESTING OUTPUTS

            once we have looped through the whole stack, we print out the results
         */
        //if we are done, the crawl should be empty
        System.out.print("crawlStack: ");
        for(int i =0; i < webCrawler2.size(); i++){
            System.out.print(webCrawler2.get(i) + ", ");
        }
        System.out.println("");
        //what pages were viewed successfully
        System.out.print("Success: ");
        for(int i =0; i < successPages2.size(); i++){
            System.out.print(successPages2.get(i) + ", ");
        }
        System.out.println("");
        //pages that were already visited and skipped
        System.out.print("Skipped: ");
        for(int i =0; i < skippedPages2.size(); i++){
            System.out.print(skippedPages2.get(i) + ", ");
        }
        System.out.println("");
        //pages that do not exit
        System.out.print("Error: ");
        for(int i =0; i < errorPages2.size(); i++){
            System.out.print(errorPages2.get(i) + ", ");
        }
    }


    /*
            GET CURRENT PAGE
            This method is used to find the location of the 'int' value of the
            address in the webj object. However we can do this one better with
            a '.compare', but that will be used later this is just for a test
     */
    public static Integer getCurrentPage(String currentPage, ArrayList<Pages> webj){

        int currentPageInt=0;
        for(int i =0; i<webj.size(); i++){
            System.out.println("checking "+ webj.get(i).getAddress() +" with " +currentPage);
            if(webj.get(i).getAddress().equals(currentPage)){
                //if(webj.get(i).getAddress() == currentPage){
                currentPageInt = i;
                System.out.println("success");
                break;
            }
        }
        return currentPageInt;
    }





    //return methods
    public static ArrayList getSuccessPages(ArrayList x){
        return x;
    }
    private static ArrayList getSkippedPages(ArrayList x){
        return x;
    }
    private static ArrayList getErrorPages(ArrayList x){
        return x;
    }
}

