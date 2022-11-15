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

import com.google.gson.JsonObject;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;

public class login extends HttpServlet{
    public void doPost(HttpServletRequest request , HttpServletResponse response) throws IOException , ServletException{

        response.setContentType("JSON");
        PrintWriter out = response.getWriter();
        // Take parameters from user.
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        JsonObject object = new JsonObject();

        String user = "select * from details where email = '"+email+"' and password = '"+password+"'";





        // Take a secret key.
        // String secret = "asdfSFS34wfsdfsdfSDSD32dfsddDDerQSNCK34SOWEK5354fdgdf4";
        // Key hmac = new SecretKeySpec(Base64.getDecoder().decode(secret), SignatureAlgorithm.HS256.getJcaName());



        // Check whether the parameters are not null or not.
        if(email.length() == 0 || password.length() == 0){
            object.addProperty("status", "failed");
            object.addProperty("status code", "404");
            object.addProperty("message", "fill the fields.");
            out.print(object);
            return;
        }

        try{

            Class.forName(connections.url);
            Connection con = DriverManager.getConnection(connections.url,connections.name,connections.password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(user);
            if(rs.next()){


                // creating the json token.
                // String jwtToken = Jwts.builder().claim("email", email).signWith(hmac).compact();


                object.addProperty("statues", "success");
                object.addProperty("status code", "200");
                object.addProperty("name", rs.getString("email"));
                object.addProperty("password", rs.getString("password"));
                // object.addProperty("token", jwtToken);
                out.print(object);
            }else{
                object.addProperty("statues", "failed");
                object.addProperty("status code", "404");
                object.addProperty("message", "Invalid Details");
                out.print(object);
            }

        }catch(Exception e){
            object.addProperty("status", "failed");
            object.addProperty("status code", "500");
            object.addProperty("message", e.getMessage());
            out.print(object);
        }


        // Fetch the data from the user.
        // Create a token for the user and send in the json format. 


    }
    
}









