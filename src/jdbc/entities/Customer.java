package jdbc.entities;

public class Customer {
    private int custid;
    private String companyname;
    private String addres;
    private String city;

    public Customer(int custid, String companyname, String addres, String city) {
        this.custid = custid;
        this.companyname = companyname;
        this.addres = addres;
        this.city = city;
    }

    public int getCustid() {
        return custid;
    }

    public String getCompanyname() {
        return companyname;
    }

    public String getAddres() {
        return addres;
    }

    public String getCity() {
        return city;
    }
}
