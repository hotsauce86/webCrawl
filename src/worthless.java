import java.util.ArrayList;
import java.util.List;

public class worthless {

    private String Address;
    private ArrayList<String> Links;

    worthless(String Address, ArrayList<String> Links){
        this.Address = Address;
        this.Links = Links;
    }

    public String getAddress(){
        return this.Address;
    }

    public void setAddress(String Address){
        this.Address = Address;
    }

    public ArrayList<String> getLinks(){
        return this.Links;
    }


    public void setLinks(){
        this.Links = Links;
    }

    public String getStringFromList(int i){
        return Links.get(i);
    }
}
