package vishal.master_hackthon;

/**
 * Created by Falgun on 4/2/2017.
 */

public class Custom_Model {
    String name;
    String role;
    String payment_status;

    public Custom_Model(String name, String role, String payment_status) {
        this.name = name;
        this.role = role;
        this.payment_status = payment_status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }
}
