package vishal.master_hackthon;

/**
 * Created by smit on 01-04-2017.
 */

public class PersonModel {
    String name;
    boolean isvarified;

    public PersonModel(String s, boolean b) {
        name=s;
        isvarified=b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isvarified() {
        return isvarified;
    }

    public void setIsvarified(boolean isvarified) {
        this.isvarified = isvarified;
    }
}
