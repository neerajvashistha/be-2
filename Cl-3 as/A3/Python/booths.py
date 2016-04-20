import bitstring
from bitstring import BitArray
from flask import Flask, request, render_template

def booths(m,r):
	x=len(bin(m))
	y=len(bin(r))
	totallength = x+y+1

	if m<0 and r<0 or r<0:
		bugbit = 1
	else:
		bugbit = 0

	A = BitArray(int = m,length = totallength) << (y+1)
	compliment = BitArray(int = -m,length = totallength) << (y+1)
	P = BitArray(int = r, length = totallength) 
	P = P<<1

	for i in range(1,y+1):
		if P[-2:]=='0b01':
			P = BitArray(int = P.int + A.int, length = totallength)
		elif P[-2:]=='0b10':
			P = BitArray(int = P.int + compliment.int, length = totallength)
		P = BitArray(int = P.int>>1, length = totallength)

	P = P[:-1]

	P.int = P.int + bugbit
	steps =""
	return '<h1>RESULT</h1><br>'+steps+'<br><h3>decimal value: '+str(P.int)+'</br><br> binary value: '+str(P.bin)+"</h3>"


#print(booths(12,13))

app = Flask(__name__)

@app.route('/')
def rend():
	return render_template("booths.html")

@app.route('/',methods=['POST'])
def post_method():
	m = request.form['multiplier']
	r = request.form['multiplicand']
	try:
		multiplier = int(m)
		multiplicand = int(r)
	except:
		return "<br><h1>Error</h1><br> One of the item found"

	print multiplier, multiplicand
	return booths(multiplier,multiplicand)

if __name__ == '__main__':
 	app.run(debug=True)