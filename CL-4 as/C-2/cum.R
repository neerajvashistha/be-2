cat ("Reading placement data from records .csv file . Data is : " )
cat ("\n")
heads<-read.csv("record.csv")
heads
Percentage=heads$Percentage
Projects=heads$Projects
Internships=heads$Internships
Papers=heads$Papers
Company=heads$Company
Salary=heads$Salary
#curve ( x^2+x , from=0, to=100)
calculate <-function(Percentage , Projects , Internships , Papers ){
  return ( Percentage+Projects+Internships+Papers )
}
Cumulative_Value=calculate ( Percentage , Projects , Internships , Papers )
par (mfrow = c ( 2 , 1 ) )
plot ( Percentage , Salary , type="o" , col="blue " , ylim = c ( 0 , 50 ) )
text ( Percentage , Salary , labels=Company , cex= 0.7 , pos=3)
plot ( Cumulative_Value , Salary , type="o" , col="red ")
text ( Cumulative_Value , Salary , labels=Company , cex= 0.7 , pos=3)
max_value=max( Salary )
index=match(max_value , Salary)
cat ("The maximum profit ( highest salary ) is in the case of : ")
cat ("\n")
cat ( paste (" Percentage : " , Percentage [ index ] ) )
cat ("\n")
cat ( paste (" Projects : " , Projects [ index ] ) )
cat ("\n")
cat ( paste (" Internships : " , Internships [ index ] ) )
cat ("\n")
cat ( paste (" Papers : " , Papers [ index ] ) )
cat ("\n")
cat ( paste ("With the company being : " ,Company [ index ] ) )
cat ("\n")
cat ( paste (" Cumulative Value being : " , Cumulative_Value [ index ] ) )
cat ("\n")
cat ("*** Refer Rplots.pdf for overview ***")
cat ("\n")
cat (" Enter data to check possible placement company and salary : " )
cat ("\n")
cat (" Enter percentage : ")
percent1 <-80
cat (" Enter number of projects : ")
projects1 <-6
cat (" Enter number of internships : ")
internships1 <-1
cat (" Enter number of papers : ")
papers1 <-2

check_value=percent1+projects1+internships1+papers1
temp<-c( ( check_value-1) : (check_value+1))
storeindex=-1
for(i in 1 : length ( temp ) ) {
  for( j in 1 : length ( Cumulative_Value ) ) {
    if ( temp [ i ]==Cumulative_Value [ j ] ) {
      storeindex=j
      break
    }}}

if(storeindex!=-1){
  print(paste ("Recommended company is : " ,Company [ storeindex] , " with salary : " , Salary))
}
else{
  print("Canot determine possible placement scenerio ")
}
