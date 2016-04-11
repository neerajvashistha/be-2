<%-- 
    Document   : newjsp
    Created on : Mar 30, 2016, 12:17:10 PM
    Author     : Tanvi
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <%
             String fileOne = request.getParameter("File1"); 
           String fileTwo = request.getParameter("File2"); 
           out.println("Comparing the 2 files......"); 
           out.println("The result of the 2 files is ...."); 
           if (compareStrings(fileOne,fileTwo)) { 
               out.println("Plagiarism detected. Cheaters!!!!"); 
           } else { 
               out.println("No plagiarism detected"); 
           } 
            %>
        <%!
       
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
   
       if((count1/(int)piecesA.length)*100>=68 && (count2/(int)piecesB.length)*100>=68){ 
           checkForPlagiarism=true; 
           }     
       return checkForPlagiarism; 
       } 
        %>
    </body>
</html>
