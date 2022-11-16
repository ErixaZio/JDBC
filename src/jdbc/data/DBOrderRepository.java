package jdbc.data;

import jdbc.entities.Customer;
import jdbc.entities.Order;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

public class DBOrderRepository implements OrderRepository{
    //Questo metodo può fare al massimo 2 query: uno per il customer e uno fra order e orderline
    static final String URL = "jdbc:postgresql://localhost/student?user=postgresMaster&password=goPostgresGo";
    static final String CUSTOMERS_ORDERS="SELECT C.custid, O.orderid FROM customers AS C JOIN orders AS O ON C.custid=O.custid WHERE C.custid = ?";
    static final String ORDERS_ORDERDETAILS="SELECT O.orderid, O.orderdate, OD.productid, OD.unitprice, OD.qty, OD.discount FROM orders AS O JOIN orderdetails AS OD ON O.orderid=OD.orderid WHERE O.orderid = ?";
    int oId;
    @Override
    public Iterable<Order> findByCustomerId(int custId) throws DataExeption{
        Customer cust=null;
        Collection<Customer> custs=new ArrayList<>();
        try(Connection c = DriverManager.getConnection(URL);
            PreparedStatement st=c.prepareStatement(CUSTOMERS_ORDERS);/*Factory method pattern*/
        ) {
            st.setInt(1, custId);
            try (ResultSet rs = st.executeQuery()) {
                //Collection<Customer> custs=new ArrayList<>();
                if(rs.next()){
                    int cId=rs.getInt("C.custid");
                    oId=rs.getInt("O.orderid");
                    String cn=rs.getString("companyname");
                    String ads=rs.getString("address");
                    String city=rs.getString("city");
                    Customer customer=new Customer(cId,cn,ads,city);
                    custs.add(customer);
                }
            }
        } catch (SQLException e) {
            throw new DataExeption(e.getMessage(),e);
        }
        try(Connection c = DriverManager.getConnection(URL);
            PreparedStatement st=c.prepareStatement(ORDERS_ORDERDETAILS);/*Factory method pattern*/
        ) {
            st.setInt(1, oId);
            try (ResultSet rs = st.executeQuery()) {
                Collection<Order> ords=new ArrayList<>();
                while(rs.next()){
                    oId=rs.getInt("O.orderid");
                    LocalDate orderDate= rs.getDate("O.orderdate").toLocalDate();
                    int prodId=rs.getInt("OD.productid");
                    double unitP=rs.getDouble("OD.unitprice");
                    int qty=rs.getInt("OD.qty");
                    double discount=rs.getDouble("OD.discount");
                    Order ord=new Order(oId,cust,orderDate);
                    ords.add(ord);
                }
                return ords;
            }
        } catch (SQLException e) {
            throw new DataExeption(e.getMessage(),e);
        }
    }
}
