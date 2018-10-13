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





    @SuppressWarnings("unchecked")
    public static void main(String[] args){



        Stack<String> webCrawler = new Stack<String>();

        ArrayList<String> successPages = new ArrayList<>();
        ArrayList<String> skippedPages = new ArrayList<>();
        ArrayList<String> errorPages = new ArrayList<>();

        ArrayList<worthless> webj = new ArrayList<>();


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


        for(int i =0; i <exampleList1.size(); i++){
            System.out.println(exampleList1.get(i));
        }

        System.out.println(example5.getAddress()+example5.getLinks().size());
        for(int i = 0; i<example5.getLinks().size(); i++ ){
            System.out.println(example1.getLinks().get(i));
        }

        /*
        webj.add(example1);
        webj.add(example2);
        webj.add(example3);
        webj.add(example4);
        webj.add(example5);
        */

        System.out.println(example5.getAddress()+example5.getLinks().size());

        System.out.println("////////////////////////////////////////////////////////////////////////////////////////");
        JSONParser parser = new JSONParser();
        try{
            //Object obj2 = parser.parse(new FileReader("C:\\Users\\tim\\IdeaProjects\\webCrawl\\resources\\JSONfile"));
            Object obj3 = parser.parse(new FileReader("resources/JSONfile.json"));

            JSONObject obj4 = (JSONObject) obj3;

            System.out.println("obj size:     "+ obj4.size());

            JSONArray websites = (JSONArray) obj4.get("pages");

            System.out.println(websites);
           // websites2.forEach(site -> parseEmployeeObject((JSONObject) site));
            System.out.println("current size:  "+ websites.size());

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
                    System.out.println(thelinksforaddress.get(i1).toString());
                    gettingTheLinks.add(thelinksforaddress.get(i1).toString());
                }
                System.out.println("array list size:  "+gettingTheLinks.size());
                //Iterator j = thelinksforaddress.iterator();

               //while(j.hasNext()){
                    //JSONObject slide2 = (JSONObject) j.next();
                    //String title2 = (String)slide2.get("links");
                  //  System.out.println("hello");
                //}


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
            for(Iterator iterator = obj4.keySet().iterator(); iterator.hasNext();) {
                String key = (String) iterator.next();
                System.out.println(key);
                System.out.println("test     "+ obj4.get(key));
            }

            System.out.println(obj3.toString());



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







        FileReader reader = null;

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

        int checker =4;
        System.out.println("web j at 10: " +webj.get(checker).getAddress()+" " +webj.get(checker).getLinks().size());
        for(int i =0; i <webj.get(checker).getLinks().size(); i++){
            System.out.println(webj.get(checker).getStringFromList(i));
        }

        System.out.println("CURRENT WEBJ: " + webj.size());
        String currentPage ="";
        int currentPageint =0;
        int MAX_CYCLE =8;

        //webCrawler.push("http://foo.bar.com/p1");
        webCrawler.push(webj.get(0).getAddress());

        for(int cycle =0; cycle < MAX_CYCLE; cycle++){

            if(webCrawler.size() ==0){
                break;
            }

            currentPage = webCrawler.pop();
            webCrawler.push(currentPage);
            System.out.println("current page address: "+currentPage);

            for(int i =0; i<webj.size(); i++){
                System.out.println("checking "+ webj.get(i).getAddress() +" with " +currentPage);
                if(webj.get(i).getAddress().equals(currentPage)){
                //if(webj.get(i).getAddress() == currentPage){
                    currentPageint = i;
                    System.out.println("success");
                }

            }

            System.out.println("current page location: "+currentPageint);

            if(!webj.get(currentPageint).getAddress().equals(currentPage) ){
            //if(webj.get(currentPageint).getAddress() != currentPage){
                errorPages.add(currentPage);
                webCrawler.pop();
                System.out.println("failed to find address");
                continue;
            }

            successPages.add(webCrawler.pop());

            if(webj.get(currentPageint).getLinks().size() ==0){
                System.out.println("empty list");
            }

            for (int i =0; i < webj.get(currentPageint).getLinks().size(); i++){
                boolean isSeen = false;

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

                for(int j = 0; j<successPages.size(); j++){
                    if(webj.get(currentPageint).getStringFromList(i).equals(successPages.get(j))){
                    //if(webj.get(currentPageint).getStringFromList(i) == successPages.get(j)){
                        isSeen = true;
                        skippedPages.add(webj.get(currentPageint).getStringFromList(i));
                        System.out.println("adding "+webj.get(currentPageint).getStringFromList(i)+" to stack");
                    }
                }
                for(int j = 0; j<webCrawler.size(); j++){
                    if(webj.get(currentPageint).getStringFromList(i).equals(webCrawler.get(j))){
                    //if(webj.get(currentPageint).getStringFromList(i) == webCrawler.get(j)){
                        isSeen =true;
                        skippedPages.add(webj.get(currentPageint).getStringFromList(i));
                        System.out.println("adding "+webj.get(currentPageint).getStringFromList(i)+" to skipped");
                    }
                }
                if(isSeen == false){
                    webCrawler.push(webj.get(currentPageint).getStringFromList(i));
                    System.out.println("adding "+webj.get(currentPageint).getStringFromList(i)+" to stack");
                }
            }




        }
        System.out.print("crawlStack: ");
        for(int i =0; i < webCrawler.size(); i++){
            System.out.print(webCrawler.get(i) + ", ");
        }

        System.out.println("");

        System.out.print("Success: ");
        for(int i =0; i < successPages.size(); i++){
            System.out.print(successPages.get(i) + ", ");
        }

        System.out.println("");
        System.out.print("Skipped: ");
        for(int i =0; i < skippedPages.size(); i++){
            System.out.print(skippedPages.get(i) + ", ");
        }
        System.out.println("");
        System.out.print("Error: ");
        for(int i =0; i < errorPages.size(); i++){
            System.out.print(errorPages.get(i) + ", ");
        }

        getSuccessPages(successPages);
        getSkippedPages(skippedPages);
        getErrorPages(errorPages);

    }
    public static ArrayList getSuccessPages(ArrayList x){
        return x;
    }
    private static ArrayList getSkippedPages(ArrayList x){
        return x;
    }
    private static ArrayList getErrorPages(ArrayList x){
        return x;
    }

    private static void parseEmployeeObject(JSONObject employee)
    {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("pages");

        //Get employee first name
        String firstName = (String) employeeObject.get("address");
        System.out.println(firstName);

    }




}

