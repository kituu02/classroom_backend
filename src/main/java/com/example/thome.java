
package com.example;

import java.io.IOException;
import java.io.PrintWriter;
// import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
// import java.util.Base64;

// import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;

public class thome extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("JSON");
        PrintWriter out = response.getWriter();
        // Take parameters from user.
        int tid = Integer.parseInt(request.getParameter("tid"));
        JsonObject object = new JsonObject();
        int choice = Integer.parseInt(request.getParameter("choice"));
        if(choice==1){
            //creating a classroom
            String subject = request.getParameter("subject");
            String branch = request.getParameter("branch");
            String section = request.getParameter("section");
            String batch = request.getParameter("batch");
            String query = "  insert into classrooms (branch,section,subject,tid,batch) values ('"+branch+"','"+section+"','"+subject+"','"+tid+"','"+batch+"');";
            try {
                Class.forName(connections.driver);
                Connection con = DriverManager.getConnection(connections.url,connections.name,connections.password);
                Statement st = con.createStatement();
                int q = st.executeUpdate(query);
                
                //int q = 1;
                if(q>0){
                    object.addProperty("status","success");
                    object.addProperty("status code","200");
                    //object.addProperty("token",jwtToken);
                    out.print(object);
                }
                else{
                    object.addProperty("status","failed");
                    object.addProperty("status code","404");
                    object.addProperty("message","Invalid credentials");
                }
            }
            catch(Exception e){
                object.addProperty("status","failed");
                object.addProperty("status code","500");
                object .addProperty("message",e.getMessage());
                out.print(object);
            }
        }
        else{
            //showing exisiting classrooms
            String query = "select * from classrooms where tid = '"+tid+"';";
            try {
                Class.forName(connections.driver);
                Connection con = DriverManager.getConnection(connections.url,connections.name,connections.password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                JsonArray list_of_classrooms = new JsonArray();
                if(rs.next()){
                    object.addProperty("status","success");
                    object.addProperty("status code","200");
                    //list_of_classrooms.put(rs.getString("cid"));
                    JsonObject new_obj = new JsonObject();
                    new_obj.addProperty("cid",rs.getString("cid"));
                    new_obj.addProperty("branch",rs.getString("branch"));
                    new_obj.addProperty("section",rs.getString("section"));
                    new_obj.addProperty("subject",rs.getString("subject"));
                    new_obj.addProperty("batch",rs.getString("batch"));
                    list_of_classrooms.add(new_obj);
                    //new_obj.addProperty("detail", list_of_classrooms.toString());
                    while(rs.next()){
                        new_obj = new JsonObject();
                        new_obj.addProperty("cid",rs.getString("cid"));
                        new_obj.addProperty("branch",rs.getString("branch"));
                        new_obj.addProperty("section",rs.getString("section"));
                        new_obj.addProperty("subject",rs.getString("subject"));
                        new_obj.addProperty("batch",rs.getString("batch"));
                        list_of_classrooms.add(new_obj);
                    }
                    //object.addProperty("details",new JsonObject());
                    object.add("details", list_of_classrooms);
                    out.print(object);
                    
                }
                else{
                    object.addProperty("status","failed");
                    object.addProperty("status code","404");
                    object.addProperty("message","Invalid credentials");
                    out.print(object);
                }
            }
            catch(Exception e){
                object.addProperty("status","failed");
                object.addProperty("status code","500");
                object .addProperty("message",e.getMessage());
                out.print(object);
            }
        }
        

        

    }

}
