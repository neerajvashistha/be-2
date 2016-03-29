<%-- 
    Document   : mypage
    Created on : 25 Jan, 2016, 4:07:40 PM
    Author     : Piyush Galphat
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1><u>Answer Page:</u></h1>
        
        <%!
        public int size=12;//no of steps 
        
        %>
        
        <%!
        //TO GET 2'S CIMPLEMENT
        public void do_2comp(int m[]){
	int i=size-1;	
	while((m[i--]!=1)){
            if(i<0)
                break;
        }
	for(;i>=0;i--)
		m[i]=m[i]==0?1:0;
        }
        //TO GET 2'S CIMPLEMENT
        public void do_2comp(int m[],int s){
	int i=s-1;	
	while((m[i--]!=1)){
            if(i<0)
                break;
        }
	for(;i>=0;i--)
		m[i]=m[i]==0?1:0;
        }

        public int to_decimal(int m[],int lim,int flag){
	int num=0;
	if(flag<1){
		do_2comp(m,lim);
	}
	for(int i=lim-1;i>=0;i--){
		num+=Math.pow(2,lim-1-i)*m[i];
	}
	return flag<1?-num:num;
        }

        public void to_binary(int m[],int m1){
	int flag=0;
	if(m1<0){
		m1*=-1;
		flag=1;
	}
	int i=size-1;
	do{
		if(i<0){
			//out.println("BITS OVERFLOW::");
			break;
		}
		m[i--]=(m1%2);
		m1/=2;
	}while(m1!=0);
	if(flag==1){
		do_2comp(m);
	}
        }

        public void bin_add(int a[],int b[]){
	int[] c=new int[size];
	int cr=0;
	for(int i=size-1;i>=0;i--){
		c[i]=(a[i]+b[i]+cr)%2;
		cr=(a[i]+b[i]+cr)/2;
		a[i]=c[i];
	}
       }

        public void bin_sub(int a[],int b[]){
            try{
	do_2comp(b);
	bin_add(a,b);
	do_2comp(b);
            }catch(Exception e){
               e.printStackTrace();
            }
        }
    %>
        
         <%   
             	//int out=1,in=1;
 //do{
//	do{
	int m1=Integer.parseInt(request.getParameter("m1"));
        int r1=Integer.parseInt(request.getParameter("m2"));
        int[] m=new int[size];	//multiplicand
	int r[]=new int[size];	//multiplier
	int q[]=new int[size];
	int bb=0;	//booth bit
	double range=(Math.pow(2, size-1)-1);
        out.println("Range: +-"+(range)+"<br>");
        if(m1>range||r1>range){
            out.println("Exceeds range....<br>");
            return;
        }
        
	//binary conversion
	to_binary(m,m1);
	to_binary(r,r1);
        
// booth's multiplication process starts
int w=size;
	while(w!=0){	//repeated size times
//checking bits
    out.print("***************************************************\n"+"<br>");
    out.println("<br>"+"\nM: ");
    for(int i=0;i<size;i++){
        out.print(m[i]);
    }
    
    out.println("<br>"+"\n<b>Step:\t\t::::::Q\t\t:::::::R\t\t::::::BB</b>");

   out.println("<br><b>"+(size-w)+"</b>\t\t");
    for(int i=0;i<size;i++){
     out.print(q[i]);
    }
    out.print("\t\t");
    for(int i=0;i<size;i++){
    out.print(r[i]);
    }
    
    out.println("\t\t"+bb);
	if(r[size-1]==1&&bb==0){
		//substraction
		bin_sub(q,m);
 
	out.print("<br>"+"\nSUBSTRACTION\n"+"<br>");
	}
	if(r[size-1]==0&&bb==1){
		//addition
		bin_add(q,m);
	out.print("<br>"+"\nADDITION\n"+"<br>");
	}
	//ASR
	bb=r[size-1];
	int t1;		//temporary variable
	t1=q[size-1];
	for(int i=size-1;i>0;i--){
		q[i]=q[i-1];
	}
	for(int i=size-1;i>0;i--){
		r[i]=r[i-1];
	}
	r[0]=t1;
	out.print("<br>"+"\nSHIFTING\n"+"<br>");
//end ASR
        w--;
	}//end for booths
	out.print("<br>"+"\nFINAL ANSWER IN BINARY: ");
	int alu[]=new int[2*size];
        int z=0;
	for(int i=0;i<size;i++){
		alu[z++]=q[i];
		//cout<<q[i];
	}
	for(int i=0;i<size;i++){
		alu[z++]=r[i];
		//cout<<r[i];
	}
	for(int i=0;i<2*size;i++){
		out.print(alu[i]);	
	}
	out.print("\t\t"+bb);
	int ans;
	out.print("<br>"+"\nFINAL ANSWER IN DECIMAL: ");
	
	if(!(m1<0&&r1<0)&&(m1<0||r1<0))
		ans=to_decimal(alu,2*size,-2);
	else
		ans=to_decimal(alu,2*size,2);
	out.println(ans);
	if(ans!=(m1*r1)){
		out.println("<br>"+"\n\t\t\tFailed for in:out |"+m1+":"+r1+"\n\n"+"<br>"+"<br>");
	}
	else{
	out.println("<br>"+"Passed");
	}
        
        %>
    </body>
</html>

