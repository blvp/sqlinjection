/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ramki.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

/**
 * @author ramakrishnan
 */
@WebServlet(name = "UserCheck", urlPatterns = {"/UserCheck"})
public class UserCheck extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException            if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<h1>SQL Injection Example</h1><br/><br/>");
        try {

            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserCheck</title>");
            out.println("</head>");
            out.println("<body>");
            String user = request.getParameter("user");


            System.out.println("MySQL Connect Example.");
            Connection conn = null;
            String url = "jdbc:postgresql://178.213.246.19:25432/injection";
            String userName = "practice";
            String password = "123";
            try {
                conn = DriverManager.getConnection(url, userName, password);
                System.out.println("Connected to the database");


                Statement st = conn.createStatement();
                PreparedStatement pst = conn.prepareStatement("SELECT * FROM  \"user\" where userid=?");
                out.println("Query : " + pst.toString());


                //PreparedStatement  preparedStatement=conn.prepareStatement("SELECT * FROM  usercheck where username=?") ;
                //preparedStatement.setString(1, user);
                Integer userid = null;
                try{
                    userid = Integer.parseInt(user);
                }catch (NumberFormatException e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "you must enter the integer value");
                    return;
                }
                pst.setInt(1, userid);
                ResultSet res = pst.getResultSet();
                //ResultSet res = preparedStatement.executeQuery();
                out.println("<br/><br/>Results");
                while (res.next()) {
                    //int i = res.getInt("Emp_code");
                    String s = res.getString("userId");
                    out.println("<br/><br/>\t\t" + s);
                }
                conn.close();
                System.out.println("Disconnected from database");
            } catch (Exception e) {
                e.printStackTrace();
            }


            out.println("<br/><a href='/sqlinjection'>Back</a>");

            out.println("</body>");
            out.println("</html>");

        } finally {
            out.close();
        }
    }
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException            if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException            if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
