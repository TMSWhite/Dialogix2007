8/19/2009 - README for Dialogix version which works for CET

SOURCES
/home/tomwhitemd/cvs/Dialogix2007/Dialogix_J2EE/
-Dialogix
-DialogixApplication
-DialogixCore
-DialogixEntities

DEPLOYMENT
-Glassfish

MYSQL PREP
./Dialogix/docs/Dialogix_Schema3_Mysql.sql
./Dialogix/docs/TricepsBundle_all_UTF8.sql

VMWARE PREP
Install Dialogix on VMware ESX.txt
Note, uses 50 GB of space, 3196 RAM.  Load tested with 200 concurrent users works fine.

IDE
Netbeans 6.5
-configure to deploy to a single VMWare instance


Notes
(1) For cet-surveys, need to configure glassfish to listen on port 80.
