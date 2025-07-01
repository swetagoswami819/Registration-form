package com.user;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.sql.DriverManager;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {

    protected void processRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        res.setContentType("text/html");
        try (PrintWriter out = res.getWriter()) {

            // getting all the incoming details from the request
            String name = req.getParameter("user_name");
            String password = req.getParameter("user_password");
            String email = req.getParameter("user_email");

           // out.print(name + password + email);

            // connection
            try {
            	Thread.sleep(3000);
                Class.forName("com.mysql.cj.jdbc.Driver");

                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/register_Servlet", "root", "@0108madhav");

                // query
                String q = "insert into user_data (name , password, email) values(?, ?, ?)";

                PreparedStatement pstmt = conn.prepareStatement(q);
                pstmt.setString(1, name);
                pstmt.setString(2, password);
                pstmt.setString(3, email);

                pstmt.executeUpdate();

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

