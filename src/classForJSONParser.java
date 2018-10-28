import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class classForJSONParser extends webCrawl{


    private ArrayList<Pages> webj;

    public classForJSONParser(ArrayList<Pages> webj){
        this.webj = webj;
    }



    public static ArrayList<Pages> GettingJSONParsed (ArrayList<Pages> someWebj){
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
}
