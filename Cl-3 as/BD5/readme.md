https://www.digitalocean.com/community/tutorials/how-to-use-psad-to-detect-network-intrusion-attempts-on-an-ubuntu-vps


sudo /sbin/iptables -A INPUT -s 65.55.44.102 -j DROP ---------------add ip address to be blocked

sudo apt-get install psad

sudo iptables -A INPUT -j LOG ---------------------------For logging purpose(optional)
sudo iptables -A FORWARD -j LOG--------------------------

sudo iptables -F ---------------Flush rules
sudo iptables -L ---------------List all rules

sudo iptables -A INPUT -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT --------------------- explicitly allow all traffic related to
an existing connection.

sudo iptables -A INPUT -p tcp --dport 22 -j ACCEPT ---------------------services that we wish to keep open to the public

sudo iptables -A INPUT -j LOG
sudo iptables -A FORWARD -j LOG

sudo iptables -P INPUT DROP ------------------DROP all extraneous messages

sudo apt-get install iptables-persistent ------------------- makes these rules persistent(optional)
sudo service iptables-persistent start(opyional)

sudo gedit /etc/psad/psad.conf -----open and change e-mail address to your email or any other and can also change the domain as per your need

sudo psad --sig-update -------------------Update the signatures of the definitions of the known attacks

sudo service psad restart --------------Restart the psad IDS


-----------end-------------






