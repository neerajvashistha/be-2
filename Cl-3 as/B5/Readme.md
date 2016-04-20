# File: Plagarism.java below code changes from 
```
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

```
# to 

```
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
```
