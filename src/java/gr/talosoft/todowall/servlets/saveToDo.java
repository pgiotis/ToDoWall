/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.talosoft.todowall.servlets;

import gr.talosoft.todowall.utils.DBAccess;
import gr.talosoft.todowall.utils.JSon;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author pgiotis
 */
@WebServlet(name = "saveToDo", urlPatterns = {"/saveToDo"})
public class saveToDo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Get current working dir
        ServletContext servletContext = getServletContext();
        String workingDir = servletContext.getRealPath("/") + "WEB-INF/";

        //init variables
        String Output = "";
        HashMap<String, String> OutpuMap = new HashMap<String, String>();
        int InsertQuery = 0;
        int saved = 0;

        //set response Content Type
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //Get name parameter from request
        String username = request.getParameter("username");
        String data = request.getParameter("data");

        //save data to DB
        //create DBAccess object
        DBAccess db = new DBAccess(workingDir);

        //get sql statment
        Statement st = db.getStatment();
        try {
            InsertQuery = st.executeUpdate("UPDATE savedjson set saveddata='" + data
                    + "' where username='" + username + "';");
        } catch (SQLException ex) {
            Logger.getLogger(saveToDo.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        //close statment
        db.closeStatment(st);

        if (InsertQuery == 1) {
            saved = 1;
        }

        //write output registered or not
        OutpuMap.put("saved", Integer.toString(saved));
        Output = JSon.jsonize(OutpuMap, HashMap.class);

        try {
            out.println(Output);
        } finally {
            //close db connection and output PrintWriter
            db.closeConnection();
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
