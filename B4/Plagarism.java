import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Plagarism extends HttpServlet 
{

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
            out.println("Comparing the 2 files......");
            out.println("The result of the 2 files is ....");
            if (compareStrings(fileOne,fileTwo)) {
                out.println("Plagiarism detected. Cheaters!!!!");
            } else {
                out.println("No plagiarism detected");
            }
            
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }

    public static boolean compareStrings(String a,String b){
        boolean checkForPlagiarism = false;
        String[] piecesA = a.split("\\s");
        String[] piecesB = b.split("\\s");

        int count1 = 0;
        int count2 = 0;
   
        for (int counter = 0; counter <= piecesA.length - 1; counter++){
            for(int counter2 = 0; counter2<= piecesB.length - 1; counter2++){
                if(piecesA[counter].equals(piecesB[counter2])){
                count1++;
                }
            }
        }

        for(int counter=0;counter<=piecesA.length-1;counter++){
            for(int counter2 = 0; counter2 < piecesB.length; counter2++){
                if(piecesA[counter].equals(piecesB[counter2])){
                    count2++;
                }
            }
        }
   
        if((count1/(int)piecesA.length)*100>=65 && (count2/(int)piecesB.length)*100>=65){
            checkForPlagiarism=true;
            }    
        return checkForPlagiarism;
        }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

        @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
