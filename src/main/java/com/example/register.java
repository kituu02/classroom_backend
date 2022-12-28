package com.example;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.JsonObject;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class register extends HttpServlet{
    public void doPost(HttpServletRequest request , HttpServletResponse response) throws ServletException , IOException{

        response.setContentType("JSON");
        PrintWriter out = response.getWriter();


        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");
        JsonObject object = new JsonObject();
        

        //TODO: 
        // validate email.
        // validate password.
        // check confirm password.

        if(!cpassword.equals(password)){
            // status 404
            object.addProperty("status", "failed");
            object.addProperty("status code", "404");
            object.addProperty("message", "password doesn't matches");
            out.println(object);
            return;
        }

        
        // if valid details store data in the datebase.
        // return the json response.

        try {


            Class.forName(connections.url);
            Connection con = DriverManager.getConnection(connections.url,connections.name,connections.password);
            
            Statement st = con.createStatement();
            // check if user exists in the data base.
            ResultSet rs = st.executeQuery("Select * from details where email = '"+email+"'");
            if(rs.next()){
                object.addProperty("status", "failed");
                object.addProperty("status code", "404");
                object.addProperty("message", "user already exists");
                out.println(object);
                return;
            }

            String insert = "insert into details values(?,?);";
            PreparedStatement pst = con.prepareStatement(insert);
            pst.setString(1, email);
            pst.setString(2, password);
            pst.executeUpdate();

            object.addProperty("status", "success");
            object.addProperty("status code", "200");
            object.addProperty("message", "successfully registered.");
            out.println(object);

        } catch (Exception e) {
            object.addProperty("status", "failed");
            object.addProperty("status code", "500");
            object.addProperty("message", e.getMessage());
            out.println(object);
        }       
    }
}