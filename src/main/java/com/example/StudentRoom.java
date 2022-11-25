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
        int sid  = Integer.parseInt(request.getParameter("sid"));
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
                    object.addProperty("status", "success");
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
                    String sid = rs.getString("sid");
                    String innerquery = "select * from sdetails where sid = '"+sid+"';";
                    String message = rs.getString("message");
                    int reactions = rs.getInt("reactions");
                    out.print(message);
                    Connection con1 = DriverManager.getConnection(connections.url,connections.name,connections.password);
                    Statement st1 = con1.createStatement();
                    ResultSet rs1 = st1.executeQuery(innerquery);
                    String name = new String();
                    if(rs1.next()){
                        name = rs1.getString("name");
                    }
                    new_obj.addProperty("name", name);
                    new_obj.addProperty("message",message);
                    new_obj.addProperty("array_length",reactions);
                    list_of_messages.add(new_obj);
                    out.println(sid);
                    while(rs.next()){
                        new_obj = new JsonObject();
                    sid = rs.getString("sid");
                    innerquery = "select * from sdetails where sid = '"+sid+"';";
                    message = rs.getString("message");
                    reactions = rs.getInt("reactions");
                    con1 = DriverManager.getConnection(connections.url,connections.name,connections.password);
                    st1 = con1.createStatement();
                    rs1 = st1.executeQuery(innerquery);
                    if(rs1.next()){
                        name = rs1.getString("name");
                    }
                    new_obj.addProperty("name", name);
                    new_obj.addProperty("message",message);
                    new_obj.addProperty("array_length",reactions);
                    list_of_messages.add(new_obj);
                    out.println(sid);
                    }
                    object.addProperty("status", "success");
                object.addProperty("status code", "200");
                object.add("details", list_of_messages);
                }
                else{
                    object.addProperty("status","failed");
                    object.addProperty("status code","404");
                    object.addProperty("message","Invalid");
                }
                
            }
            else if(choice ==3){
                //material
                String query = "select * from material_links where cid = '"+cid+"';";
                ResultSet rs = st.executeQuery(query);
                if(rs.next()){
                    String mat_name = rs.getString("mat_name");
                    String mat_link = rs.getString("mat_link");
                    int sender = rs.getInt("sender");
                    String name = new String();
                    JsonArray list_of_materials = new JsonArray();
                    JsonObject new_obj = new JsonObject();
                    if(sender==1){
                        String sid = rs.getString("sid");
                        String innerquery = "select * from sdetails where sid = '"+sid+"';";
                        Statement st1 = con.createStatement();
                        ResultSet rs1 = st1.executeQuery(innerquery);
                        rs1.next();
                        name = rs1.getString("name");
                        new_obj.addProperty("sender","student");
                    }else if(sender==2){
                        String tid = rs.getString("tid");
                        String innerquery = "select * from tdetails where tid = '"+tid+"';";
                        Statement st1 = con.createStatement();
                        ResultSet rs1 = st1.executeQuery(innerquery);
                        rs1.next();
                        name = rs1.getString("name");
                        new_obj.addProperty("sender","teacher");
                    }
                    new_obj.addProperty("name", name);
                    new_obj.addProperty("material_name", mat_name);
                    new_obj.addProperty("material_link", mat_link);
                    list_of_materials.add(new_obj);
                    while(rs.next()){
                        mat_name = rs.getString("mat_name");
                        mat_link = rs.getString("mat_link");
                        new_obj = new JsonObject();
                        new_obj.addProperty("material_name", mat_name);
                        new_obj.addProperty("material_link", mat_link);
                        list_of_materials.add(new_obj);
                    }
                    object.addProperty("status", "success");
                    object.addProperty("status code", "200");
                    object.add("details", list_of_materials);
                }
                else{
                    object.addProperty("status","failed");
                    object.addProperty("status code","404");
                    object.addProperty("message","Invalid");
                }
            }
            else if(choice==4){
                //Talk to teacher
                // String query = "select * from individual_message_table where sid '"+sid+"'';";
                // ResultSet rs = st.executeQuery(query);

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
