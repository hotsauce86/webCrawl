import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import java.io.*;
import java.util.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;


public class webCrawl {


    /*
        This project is based on using json.simple to parse a JSON 'internet' file

        The project is split into two sections
        -a parser section to go through the JSON and add the elements to an ArrayList
        -a crawler section that crawls through the objects in the list until all pages are explored
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


        /*
            TEST CASE

            This section was used to test the crawler with example addresses and
            list of addresses
         */

        ArrayList<String> exampleList1 =  new ArrayList<>();
        exampleList1.add("http://foo.bar.com/p2");
        exampleList1.add("http://foo.bar.com/p3");
        exampleList1.add("http://foo.bar.com/p4");

        ArrayList<String> exampleList2 =  new ArrayList<>();
        exampleList2.add("http://foo.bar.com/p2");
        exampleList2.add("http://foo.bar.com/p4");

        ArrayList<String> exampleList3 =  new ArrayList<>();
        exampleList3.add("http://foo.bar.com/p5");
        exampleList3.add("http://foo.bar.com/p1");
        exampleList3.add("http://foo.bar.com/p6");

        ArrayList<String> exampleList4 =  new ArrayList<>();

        ArrayList<String> exampleList5 =  new ArrayList<>();
        exampleList5.add("http://foo.bar.com/p7");
        exampleList5.add("http://foo.bar.com/p4");
        exampleList5.add("http://foo.bar.com/p5");

        worthless example1 = new worthless("http://foo.bar.com/p1", exampleList1);
        worthless example2 = new worthless("http://foo.bar.com/p2", exampleList2);
        worthless example3 = new worthless("http://foo.bar.com/p4", exampleList3);
        worthless example4 = new worthless("http://foo.bar.com/p5", exampleList4);
        worthless example5 = new worthless("http://foo.bar.com/p6", exampleList5);

        //checking if list is working
        for(int i =0; i <exampleList1.size(); i++){
            System.out.println(exampleList1.get(i));
        }
        System.out.println("address "+example1.getAddress()+", links"+example1.getLinks().size());

        //another test case
        for(int i = 0; i<example5.getLinks().size(); i++ ){
            System.out.println(example5.getLinks().get(i));
        }
        System.out.println("address "+example5.getAddress()+", links"+example5.getLinks().size());

        /*
        //test cases added to stack to test crawler
        webj.add(example1);
        webj.add(example2);
        webj.add(example3);
        webj.add(example4);
        webj.add(example5);
        */



        /*
            JSON PARSER

            this section uses json.simple and iterates through the JSON to collect the web information

         */
        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
        //creating a parser
        JSONParser parser = new JSONParser();
        try{
            //Object obj2 = parser.parse(new FileReader("C:\\Users\\tim\\IdeaProjects\\webCrawl\\resources\\JSONfile"));
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
                worthless someWorthless = new worthless(theAddress, gettingTheLinks);
                webj.add(someWorthless);

                System.out.println(webj.size()+"!!!!");
            }

            System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
            /*
            for(Iterator iterator = obj4.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                System.out.println(key);
                System.out.println("test     "+ obj4.get(key));
            }

            System.out.println(obj3.toString());
            */

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


        //FileReader reader = null;

        /*
        System.out.println("/////////////////////////////////////");
        try{
            reader = new FileReader("resources/JSONfile.json");
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.print(e.getMessage());
        }
        finally {
            try{
                if(reader != null)
                {
                    reader.close();
                }

            }catch (IOException ex){
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }

        }
        System.out.println("/////////////////////////////////////");
        */

        /*
            TESTING WHAT IS IN THE LIST BEFORE GOING IN THE STACK
         */
        int checker =4;
        System.out.println("web j at 10: " +webj.get(checker).getAddress()+" " +webj.get(checker).getLinks().size());
        for(int i =0; i <webj.get(checker).getLinks().size(); i++){
            System.out.println(webj.get(checker).getStringFromList(i));
        }

        System.out.println("CURRENT WEBJ: " + webj.size());






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
        webCrawler.push(webj.get(0).getAddress());

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
        while(webCrawler.size() != 0){
        //for(int cycle =0; cycle < MAX_CYCLE; cycle++){

            if(webCrawler.size() ==0){
                break;
            }

            /*
                GETTING CURRENT PAGE
             */
            currentPage = webCrawler.pop();
            webCrawler.push(currentPage);
            System.out.println("current page address: "+currentPage);

            for(int i =0; i<webj.size(); i++){
                System.out.println("checking "+ webj.get(i).getAddress() +" with " +currentPage);
                if(webj.get(i).getAddress().equals(currentPage)){
                //if(webj.get(i).getAddress() == currentPage){
                    currentPageint = i;
                    System.out.println("success");
                    break;
                }

            }
            System.out.println("current page location: "+currentPageint);

            /*
                GETTING ERROR PAGE
             */
            if(!webj.get(currentPageint).getAddress().equals(currentPage) ){
            //if(webj.get(currentPageint).getAddress() != currentPage){
                errorPages.add(currentPage);
                webCrawler.pop();
                System.out.println("failed to find address");
                continue;
            }
            /*
                GETTING SUCCESS PAGE
             */

            //page was loaded successfully
            //and popped from the stack
            successPages.add(webCrawler.pop());

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
                if(skippedPages.size() >0){
                    for(int k=0; k<skippedPages.size(); k++){
                        if(webj.get(currentPageint).getStringFromList(i).equals(skippedPages.get(k))){
                        //if(webj.get(currentPageint).getStringFromList(i) == skippedPages.get(k)){
                            System.out.println("the "+webj.get(currentPageint).getStringFromList(i)+" already skipped");
                            isSeen = true;
                            continue;
                        }
                    }
                    if(isSeen == true){
                        continue;
                    }
                }

                //if page has been successfully seen
                for(int j = 0; j<successPages.size(); j++){
                    if(webj.get(currentPageint).getStringFromList(i).equals(successPages.get(j))){
                    //if(webj.get(currentPageint).getStringFromList(i) == successPages.get(j)){
                        isSeen = true;
                        skippedPages.add(webj.get(currentPageint).getStringFromList(i));
                        System.out.println("adding "+webj.get(currentPageint).getStringFromList(i)+" to skipped, already seen");
                    }
                }
                //if the page has already been queued in the stack
                for(int j = 0; j<webCrawler.size(); j++){
                    if(webj.get(currentPageint).getStringFromList(i).equals(webCrawler.get(j))){
                    //if(webj.get(currentPageint).getStringFromList(i) == webCrawler.get(j)){
                        isSeen =true;
                        skippedPages.add(webj.get(currentPageint).getStringFromList(i));
                        System.out.println("adding "+webj.get(currentPageint).getStringFromList(i)+" to skipped, already in the stack");
                    }
                }
                //if the address has not been visited yet and not yet added to the stack, we push it to the stack
                if(isSeen == false){
                    webCrawler.push(webj.get(currentPageint).getStringFromList(i));
                    System.out.println("pushing "+webj.get(currentPageint).getStringFromList(i)+" to stack");
                }
            }
        }

        /*
            TESTING OUTPUTS

            once we have looped through the whole stack, we print out the results
         */
        //if we are done, the crawl should be empty
        System.out.print("crawlStack: ");
        for(int i =0; i < webCrawler.size(); i++){
            System.out.print(webCrawler.get(i) + ", ");
        }
        System.out.println("");
        //what pages were viewed successfully
        System.out.print("Success: ");
        for(int i =0; i < successPages.size(); i++){
            System.out.print(successPages.get(i) + ", ");
        }
        System.out.println("");
        //pages that were already visited and skipped
        System.out.print("Skipped: ");
        for(int i =0; i < skippedPages.size(); i++){
            System.out.print(skippedPages.get(i) + ", ");
        }
        System.out.println("");
        //pages that do not exit
        System.out.print("Error: ");
        for(int i =0; i < errorPages.size(); i++){
            System.out.print(errorPages.get(i) + ", ");
        }
        //methods to return the lists
        getSuccessPages(successPages);
        getSkippedPages(skippedPages);
        getErrorPages(errorPages);
        //end of main

        int numberofsocks =9;
        numberofsocks = numberofsocks/2;
        System.out.println(numberofsocks);

        String s1 = "asbcsdsdf";
        String s2 = "sdfbeljkdcfj";

        String[] s1collect = s1.split("");
        String[] s2collect = s2.split("");

        boolean match =false;
        for(String a : s1collect){
            for(String b: s2collect){
                if(a.equals(b)){
                    match=true;
                    System.out.println("match is true");
                    break;
                }
            }
            if(match==true){
                break;
            }

        }
        if(match==false){
            System.out.println("match is false");
        }




        String countSpaceExample = "asdhfwer t asdljfno as doahedjkhnd ";


        System.out.println(countSpace(countSpaceExample));
    }


    public static String countSpace(String x){

        String y;
        int spaceCounter =0;
        String[] z = x.split("");

        for(String temp : z){
            if(temp.equals(" ")){
                spaceCounter++;
            }
        }

        y="spaces found in string: " + spaceCounter;

        return y;
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

