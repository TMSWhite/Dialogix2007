PROC IMPORT OUT= WORK.ss 
            DATAFILE= "C:\cvs2\Dialogix\cet_analysis\sunrisetimes_ss.txt" 
            DBMS=DLM REPLACE;
     DELIMITER='09'x; 
     GETNAMES=YES;
     DATAROW=2; 
RUN;


PROC IMPORT OUT= WORK.ws 
            DATAFILE= "C:\cvs2\Dialogix\cet_analysis\sunrisetimes_ws.txt" 
            DBMS=DLM REPLACE;
     DELIMITER='09'x; 
     GETNAMES=YES;
     DATAROW=2; 
RUN;

proc sort data=ss;
	by Lat Long;
run;

proc sort data=ws;
	by Lat Long;
run;

data cet7.sunrisetimes; 
	merge ss ws;
	by Lat Long;
	Sunrise_WSvsSS = Sunrise_WS - Sunrise_SS;
	if (Sunrise_WSvsSS < 0) then Sunrise_WSvsSS = - Sunrise_WSvsSS;
	if (Sunrise_WSvsSS = .) then delete;
run;

