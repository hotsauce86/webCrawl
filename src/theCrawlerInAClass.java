import java.util.ArrayList;
import java.util.Stack;

public class theCrawlerInAClass extends webCrawl {

    private ArrayList<Pages> webj;

    public theCrawlerInAClass(ArrayList<Pages> webj){
        this.webj = webj;
    }

    public ArrayList<Pages> setTheWebj(ArrayList<Pages> webj){
        this.webj = webj;

        return webj;
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

        System.out.println("");
    }


}
