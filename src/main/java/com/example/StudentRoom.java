package com.example;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Array;
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

public class StudentRoom extends HttpServlet{
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("JSON");
        PrintWriter out = response.getWriter();
        int cid = Integer.parseInt(request.getParameter("cid"));
        int choice = Integer.parseInt(request.getParameter("choice"));
        //out.print(choice);
        JsonObject object = new JsonObject();
        try{
            Class.forName(connections.driver);
            Connection con = DriverManager.getConnection(connections.url,connections.name,connections.password);
            Statement st = con.createStatement();
            if(choice==1){
                //show students
                String query = "select * from classrooms where cid = '"+cid+"' ; ";
                ResultSet rs = st.executeQuery(query);
                if(rs.next()){
                    String section = rs.getString("section");
                    String branch  = rs.getString("branch");
                    String batch   = rs.getString("batch");
                    String inner_query = "select * from sdetails where branch = '"+branch+"' and section = '"+section+"' and batch = '"+batch+"';";
                    ResultSet rs1 = st.executeQuery(inner_query);
                    JsonArray list_of_students = new JsonArray();
                    JsonObject new_obj = new JsonObject();
                    while(rs1.next()){
                        new_obj.addProperty("sid", rs1.getString("sid"));
                        new_obj.addProperty("name",rs1.getString("name"));
                        new_obj.addProperty("Rollno",rs1.getString("Rollno"));
                        list_of_students.add(new_obj);
                    }
                    object.addProperty("statues", "success");
                    object.addProperty("status code", "200");
                    object.add("details", list_of_students);
                }
                else{
                    object.addProperty("status","failed");
                    object.addProperty("status code","404");
                    object.addProperty("message","Invalid");
                }
            }
            else if(choice == 2){
                //discussions forms
                String query = "select * from forms_message_table where cid = '"+cid+"';";
                out.print(cid);
                ResultSet rs = st.executeQuery(query);
                JsonArray list_of_messages = new JsonArray();
                JsonObject new_obj = new JsonObject();
                if(rs.next()){
                    out.println("fcuk..........");
                    String sid = rs.getString("sid");
                    String innerquery = "select * from sdetails where sid = '"+sid+"';";
                    String message = rs.getString("message");
                    Array arr = rs.getArray("reactions");
                    //int len_of_array = ((String[])arr.getArray()).length;
                    ResultSet rs1 = st.executeQuery(innerquery);
                    out.println(sid);

                }
                else{
                    object.addProperty("status","failed");
                    object.addProperty("status code","404");
                    object.addProperty("message","Invalid");
                }
                // object.addProperty("statues", "success");
                // object.addProperty("status code", "200");
                // object.add("details", list_of_messages);
            }
            else if(choice ==3){
                //material
            }
            else if(choice==4){
                //Talk to teacher
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
