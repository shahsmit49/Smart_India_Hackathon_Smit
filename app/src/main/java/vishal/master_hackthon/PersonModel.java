package vishal.master_hackthon;

/**
 * Created by smit on 01-04-2017.
 */

public class PersonModel {
    String name;
    boolean isvarified;
    String aadhar,email;

    public PersonModel(String s, boolean b,String aadhar,String email) {
        name=s;
        isvarified=b;
        this.aadhar=aadhar;
        this.email=email;
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

    public String getAadhar() {
        return aadhar;
    }

    public void setAadhar(String aadhar) {
        this.aadhar = aadhar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
