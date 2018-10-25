import java.util.ArrayList;
import java.awt.List;

public class Pages {

    private String address;
    private ArrayList<String> links;

    private List someints = new List();

    public Pages(String address, ArrayList<String> links){
        this.address = address;
        this.links = links;
    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String Address){
        this.address = address;
    }

    public ArrayList<String> getLinks(){
        return this.links;
    }


    public void setLinks(){
        this.links = links;
    }

    public String getAddressFromList(int i){
        return links.get(i);
    }
}
