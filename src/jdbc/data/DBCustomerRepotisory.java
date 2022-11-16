package jdbc.data;

import jdbc.entities.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class DBCustomerRepotisory implements CustomerRepository{
    static final String URL = "jdbc:postgresql://localhost/student?user=postgresMaster&password=goPostgresGo";
    static final String ALL_CUSTOMER = "SELECT custid, companyname, address, city FROM customers";
    static final String FIND_BY_ID="SELECT custid, companyname, address, city FROM customers WHERE custid = ?";
    static final String DELETE_BY_ID="DELETE FROM custometrs WHERE custid=?";
    static final String UPDATE="UPDATE customers SET companyname = ?, address = ?, city = ? WHERE custid = ?";
    static final String INSERT="INSERT INTO customers(companyname,address,city) VALUES(?,?,?)";
    @Override
    public Iterable<Customer> getAll() throws DataExeption {
        try(Connection c = DriverManager.getConnection(URL);
            Statement st=c.createStatement();/*Factory method pattern*/
            ResultSet rs=st.executeQuery(ALL_CUSTOMER);/*Factory method pattern*/) {
            Collection<Customer> custs=new ArrayList<>();
            while(rs.next()){
                int id=rs.getInt("custid");
                String cn=rs.getString("companyname");
                String ads=rs.getString("address");
                String city=rs.getString("city");
                Customer customer=new Customer(id,cn,ads,city);
                custs.add(customer);
            }
            return custs;
        } catch (SQLException e) {
            throw new DataExeption(e.getMessage(),e);
        }
    }

    @Override
    public Optional<Customer> findById(int id) throws DataExeption {
        try(Connection c = DriverManager.getConnection(URL);
            PreparedStatement st=c.prepareStatement(FIND_BY_ID);/*Factory method pattern*/
        ) {
            st.setInt(1,id);
            try (ResultSet rs=st.executeQuery()){
                if (rs.next()){
                    int custId=rs.getInt("custid");
                    String cn=rs.getString("companyname");
                    String ads=rs.getString("address");
                    String city=rs.getString("city");
                    Customer customer=new Customer(id,cn,ads,city);
                    return Optional.of(customer);
                }else{
                    return Optional.empty();
                }
            }
        } catch (SQLException e) {
            throw new DataExeption(e.getMessage(),e);
        }
    }

    @Override
    public boolean delete(int id) throws DataExeption {
        try(Connection c = DriverManager.getConnection(URL);
            PreparedStatement st=c.prepareStatement(DELETE_BY_ID);/*Factory method pattern*/
        ) {
            st.setInt(1,id);
            int numberRows=st.executeUpdate();
            return numberRows==1;
        }catch (SQLException e) {
            throw new DataExeption(e.getMessage(),e);
        }
    }

    @Override
    public boolean update(Customer customer) throws DataExeption {
        try(Connection c = DriverManager.getConnection(URL);
            PreparedStatement st=c.prepareStatement(UPDATE);/*Factory method pattern*/
        ) {
            st.setString(1,customer.getCompanyname());
            st.setString(2,customer.getAddres());
            st.setString(3,customer.getCity());
            st.setInt(4,customer.getCustid());
            int numberRows=st.executeUpdate();
            return numberRows==1;
        }catch (SQLException e) {
            throw new DataExeption(e.getMessage(), e);
        }
    }

    @Override
    public void insert(Customer customer) throws DataExeption {
        try(Connection c = DriverManager.getConnection(URL);
            PreparedStatement st=c.prepareStatement(INSERT);/*Factory method pattern*/
        ) {
            st.setString(1,customer.getCompanyname());
            st.setString(2,customer.getAddres());
            st.setString(3,customer.getCity());
            st.execute();
        }catch (SQLException e) {
            throw new DataExeption(e.getMessage(),e);
        }
    }
}
