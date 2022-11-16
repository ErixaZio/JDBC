import jdbc.data.CustomerRepository;
import jdbc.entities.Customer;
import org.postgresql.Driver;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //ResultSet rs=null;
//        CustomerRepository c=null
        String url = "jdbc:postgresql://localhost/student?user=postgresMaster&password=goPostgresGo";
        String allCustomers = "SELECT custid, companyname, address, city FROM customers";
        try( Connection c = DriverManager.getConnection(url);
             Statement st=c.createStatement();/*Factory method pattern*/
             ResultSet rs=st.executeQuery(allCustomers);/*Factory method pattern*/) {
            //Class.forName("org.postgresql.Driver");
            System.out.println(c.getClass().getName());
            System.out.println(st.getClass().getName());
            System.out.println(rs.getClass().getName());
            while(rs.next()){
                int id=rs.getInt("custid");
                String cn=rs.getString("companyname");
                String ads=rs.getString("address");
                String city=rs.getString("city");
                System.out.printf("id: %d, company name: %s, adress: %s, city: %s%n",id,cn,ads,city);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
//        finally {
//            try {
//                if(rs!=null) {
//                   rs.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();//throw new RuntimeException(e);
//            }
//        }
    }
}