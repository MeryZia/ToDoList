package Implementation;

import Classes.Users;
import Connexion.Connect;
import Daos.Dao;
import Daos.SignUpIn;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ImplDaoUser implements Dao<Users>, SignUpIn {
   Connection cn;
   ResultSet rs=null;
   PreparedStatement ps=null;

    @Override
    public void insert(Users item) {
        cn= Connect.getConnection();
        String sql="insert into Users (LastName,FirstName,UserName,Pwd) values (?,?,?,?)";
        try {
            ps =cn.prepareStatement(sql);
            ps.setString(1,item.getLastName());
            ps.setString(2,item.getLastName());
            ps.setString(3,item.getLogin());
            ps.setString(4,item.getPwd());
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Users item) {
        cn= Connect.getConnection();

        try {
            String sql="delete from Users where UserName = ? and Pwd=?)";
            ps =cn.prepareStatement(sql);
            ps.setString(1,item.getLogin());
            ps.setString(2,item.getPwd());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Users item) {
        cn= Connect.getConnection();

        try {
            String sql="update Users set lastname=?,firstname=?,username=?,pwd=? where iduser =?";
            ps =cn.prepareStatement(sql);
            ps.setString(1,item.getLastName());
            ps.setString(2,item.getLastName());
            ps.setString(3,item.getLogin());
            ps.setString(4,item.getPwd());
            ps.setInt(5,item.getIdUser());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void select(Users item) {

    String sql="select count(1) from users where userName=? and pwd=? ";
        Connection cn = Connect.getConnection();
    try{
        ps =cn.prepareStatement(sql);
        ps.setString(1,item.getLogin());
        ps.setString(2,item.getPwd());
        rs=ps.executeQuery();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public ObservableList<Users> getList() {
        throw new AssertionError("No list to display.");

    }
    public Integer id;
    public Users HaveIdUser(Users user){
        String sql="select iduser from users where username=? and pwd = ?";
        Connection cn = Connect.getConnection();

        try{
            ps =cn.prepareStatement(sql);
            ps.setString(1,user.getLogin());
            ps.setString(2,user.getPwd());

            rs=ps.executeQuery();
            rs.getInt(1);

            while(rs.next()) {
                System.out.println(rs.getInt("iduser"));
                rs.getInt("iduser");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

            return null;
    }

    @Override
    public void SignIn(Users user) {

    }

    @Override
    public void SignUp(Users user) {
        PreparedStatement psInsert = null;
        PreparedStatement psExists = null;
        ResultSet resultset = null;
        String lastName = user.getLastName();
        String FirstName= user.getFirstName();
        String username = user.getLogin();
        String password = user.getPwd();


        try {
            if(!lastName.equals("") && !FirstName.equals("") && !username.equals("") && !password.equals("")) {
                String query = "select * from users where username = ?";
                psExists = cn.prepareStatement(query);
                psExists.setString(1,user.getLogin());
                resultset = psExists.executeQuery();

                if(resultset.isBeforeFirst()) {
                    System.out.println("User already exists!!");
                }else {
                    String query1 = "insert into users(lastname , firstname , username , pwd) values (?,?,?,?)";
                    psInsert=cn.prepareStatement(query1);
                    psInsert.setString(1,lastName);
                    psInsert.setString(2,FirstName);
                    psInsert.setString(3,username);
                    psInsert.setString(4,password);
                    psInsert.executeUpdate();

                }
            }else {
                System.out.println("Fill in information!!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
