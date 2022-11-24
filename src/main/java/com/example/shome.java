
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

public class shome extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("JSON");
        PrintWriter out = response.getWriter();
        // Take parameters from user.
        int sid = 1;
        JsonObject object = new JsonObject();
        //int choice = Integer.parseInt(request.getParameter("choice"));
        String query = "select * from sdetails where sid = '"+sid+"';";
            try {
                //out.print("hi");
                Class.forName(connections.driver);
                Connection con = DriverManager.getConnection(connections.url,connections.name,connections.password);
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
            //out.print(rs.getString("sid"));
            if(rs.next()){
                String batch = rs.getString("batch");
                String branch = rs.getString("branch");
                String section = rs.getString("section");
                //out.println(batch+" "+branch+" "+section);
                String inner_query = "select * from classrooms where batch = '"+batch+"' and branch = '"+branch+"' and section = '"+section+"'; ";
                ResultSet rs1 = st.executeQuery(inner_query);
                JsonArray list_of_classes = new JsonArray();
                JsonObject new_obj = new JsonObject();
                while(rs1.next()){
                    new_obj = new JsonObject();
                    new_obj.addProperty("cid", rs1.getString("cid"));
                    new_obj.addProperty("subject",rs1.getString("subject"));
                    
                    list_of_classes.add(new_obj);
                }
                //out.print(new_obj);
                object.addProperty("statues", "success");
                object.addProperty("status code", "200");
                object.add("details", list_of_classes);
            }
            else{
                object.addProperty("status","failed");
                object.addProperty("status code","404");
                object.addProperty("message","Invalid");
            }
        }
        catch(Exception e){
            object.addProperty("status","failed");
            object.addProperty("status code","500");
            object.addProperty("message",e.getMessage());
        }
        
        out.print(object);
        

    }

}
