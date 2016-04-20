/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cipher
 */
@WebServlet(urlPatterns = {"/Plagarism"})
public class Plagarism extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Plagarism</title>");            
            out.println("</head>");
            out.println("<body>");
            
            String fileOne = request.getParameter("File1");
            String fileTwo = request.getParameter("File2");
            out.println("Comparing the 2 files......");out.println("<br>");
            String[] str1 = fileOne.split("\\s");
            String[] str2 = fileTwo.split("\\s");
            
            for (int j = 0; j < str1.length; j++) { //alphabetical order sorting of str1
                for (int i = j + 1; i < str1.length; i++) {
                    if (str1[i].compareTo(str1[j]) < 0) {
                      String t = str1[j];
                      str1[j] = str1[i];
                      str1[i] = t;
                    }
                }              
            }
            
            int results; double plag=0;
            for (int key = 0; key < str2.length; key++){
                results = searchString(str1, str2[key]);
                if (results == 1) {
                    plag = plag+1;
                }
            
            }
            double plagPercent = (plag/str2.length)*100;
            
            out.println("Similarity between two text is "+ plagPercent);
            out.println("<br>");
            out.println("Plagrism "+((plagPercent>50.0) ? "exist" : "does not exist"));
            
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
    //simple binary search
    public static int searchString(String[] str1, String key) {
        int first = 0;
        int last  = str1.length;

        while (first < last) {
            int mid = (first + last) / 2;
            if (key.compareTo(str1[mid]) < 0) {
                last = mid;
            } else if (key.compareTo(str1[mid]) > 0) {
                first = mid + 1;
            } else {
                return 1;
            }
        }
        return -1;
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
