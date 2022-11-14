package com.example;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

public class login extends HttpServlet {
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("JSON");
        PrintWriter out = response.getWriter();
        JsonObject object = new JsonObject();

        try {
            Class.forName(connections.driver);
            Connection con = DriverManager.getConnection(connections.url, connections.name, connections.password);
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from user_table where id = 1");
            rs.next();
            object.addProperty("name", rs.getString("username"));
            object.addProperty("password", rs.getString("password"));
        } catch (Exception e) {
            out.print(e.getMessage());
        }

        // object.addProperty("name", "saketh");
        // object.addProperty("port", "8081");

        out.println(object);
    }

}

/*
 * 
 * 
 * Json array - [{},{}]
 * Json Objects - {
 * 
 * }
 * 
 * 
 */