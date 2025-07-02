package com.user;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.DriverManager;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import jakarta.servlet.annotation.MultipartConfig;


@MultipartConfig
public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html");
        try (PrintWriter out = res.getWriter()) {

            // getting all the incoming details from the request
            String name = req.getParameter("user_name");
            String password = req.getParameter("user_password");
            String email = req.getParameter("user_email");
            Part part=req.getPart("image");
            String filename=part.getSubmittedFileName();
           // out.println(filename);

           // out.print(name + password + email);

            // connection............................
            try {
            	Thread.sleep(3000);
                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/register_Servlet", "root", "@0108madhav");

                // query
                String q = "insert into user_data (name , password, email,imagename) values(?, ?, ?,?)";

                PreparedStatement pstmt = conn.prepareStatement(q);
                pstmt.setString(1, name);
                pstmt.setString(2, password);
                pstmt.setString(3, email);
                pstmt.setString(4, filename);

                pstmt.executeUpdate();
                
                //upload file..............
                //dsdd
                InputStream is=part.getInputStream();
                byte []data=new byte[is.available()];
                
                is.read(data);
                String path =  getServletContext().getRealPath("/")+"img"+File.separator+filename;
              // out.println(path);
                
                FileOutputStream fout=new FileOutputStream(path);
                fout.write(data);
                fout.close();
                
                

                out.println("Done");

                conn.close(); // optional cleanup
            } catch (Exception e) {
                e.printStackTrace();
               // out.println("error");
                out.println("Exception: " + e.getMessage());

            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        processRequest(req, res);
    }
}

