/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.entities.Analysis;
import tn.esprit.tools.DBConnexion;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;






/**
 *
 * @author ASUS
 */
public class AnalysisService implements IService<Analysis>{
    Connection cnx;
    
    public AnalysisService(){
        cnx=DBConnexion.getInstance().getCnx();
        
    }

        public void Create(Analysis a){
       String sql="insert into analysis(analysis_name,description, result_type, price) values"
                 + "(?,?,?,?)";
        try {
            PreparedStatement st = cnx.prepareStatement(sql);
            
            st.setString(1, a.getAnalysis_name());
            st.setString(2, a.getDescription());
            st.setString(3, a.getResult_type());
            st.setFloat(4, a.getPrice());
           
                    
            
            st.executeUpdate();
            System.out.println("Analysis add");
        } 
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }}
    
    
    public ObservableList<Analysis> Read() {
         ObservableList<Analysis> list = FXCollections.observableArrayList();
        try {
           
            String sql="select * from Analysis where user_id=2";//laboratory id statique en attendant la preparation de la méthode session
            Statement st = cnx.createStatement();
            
            ResultSet rs= st.executeQuery(sql);
            UserService ms= new UserService();
            while(rs.next()){
                Analysis a = new Analysis(rs.getInt("analysis_id"),rs.getString("Analysis_name"), rs.getString("Description"),
                rs.getString("result_type"),rs.getFloat("price"));
                list.add(a);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return list;
    }
    

        public void Delete (int id) {
try {
            String sql="delete from Analysis where analysis_id ="+id;
            Statement ste = cnx.createStatement();
            ste.executeUpdate(sql);
            System.out.println("Analysis deleted");
        } 
            catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


    }  
        
        
        @Override
public void Update(Analysis a) {
    String sql = "UPDATE Analysis SET analysis_name=?, description=?, result_type=?, price=? WHERE analysis_id=?";
    try {
        PreparedStatement st = cnx.prepareStatement(sql);
        st.setString(1, a.getAnalysis_name());
        st.setString(2, a.getDescription());
        st.setString(3, a.getResult_type());
        st.setFloat(4, a.getPrice());
        st.setInt(5, a.getAnalysis_id());

        st.executeUpdate();
        System.out.println("Analysis updated successfully.");
    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}

  public  void  smsreporting () {
 
   
          Twilio.init("AC727b58b86ea4b6329528676b91b8bc49","b1af49ba31b27c76c6b29d045456abfd");
           Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21652986595"),
                new com.twilio.type.PhoneNumber("+12176248786"), 
                "votre commande est préte" )
            .create();

        System.out.println(message.getSid());
    }

    @Override
    public void Delete(Analysis o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Analysis SearchbyId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    

    
    
}
