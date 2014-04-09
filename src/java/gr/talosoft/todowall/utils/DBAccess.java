/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.talosoft.todowall.utils;

import static java.sql.DriverManager.getConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class connect to DB
 *
 * @author Panagiotis Giotis <giotis.p@gmail.com>
 */
public class DBAccess {

    private static String DBusername;
    private static String DBpassword;
    private static String DBurl;
    private java.sql.Connection con;

    /**
     * DBAccess constructor
     */
    public DBAccess(String workingDir) {
        try {
            //load DB settings
            Settings WallSettings = new Settings(workingDir);
            DBusername = WallSettings.getDBUsername();
            DBpassword = WallSettings.getDBPassword();
            DBurl = WallSettings.getDBurl();
            Class.forName("com.mysql.jdbc.Driver");

            con = getConnection(DBurl, DBusername, DBpassword);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Get a statement from a sql connection
     *
     * @return the sql statement
     */
    public Statement getStatment() {
        Statement st = null;
        try {
            st = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return st;
    }

    /**
     * Close given sql statement
     *
     * @param st
     */
    public void closeStatment(Statement st) {
        try {
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Close sql Connection
     */
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
