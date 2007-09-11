%macro StartAnODSReport(htmlfile);
	ods listing close;	
	ods html file=&htmlfile stylesheet;
%mend StartAnODSReport;

%macro FinishAnODSReport;
	ods html close;
	ods listing;
%mend FinishAnODSReport;

%setInitParams;

%StartAnODSReport("&cet7_11_lib.scatterplots.html");

proc plot data=cet7.automeq;
  plot MajorDepression_dx*sunrise_local_sum_solst='@';
  title ' Plot of Major Depression by Local sunrise time ' ;
  run; 
proc plot data=cet7.automeq;
  plot MajorDepression_dx*d_age='$';
  title 'Plot of Major Depression by age';
  run;
proc plot data=cet7.automeq;
  plot MajorDepression_dx*photoperiod='%';
  title 'Plot of Major Depression by photoperiod';
  run;
proc plot data=cet7.automeq;
  plot MajorDepression_dx*timezone='+';
  title ' Plot of Major Depression by timezone ' ;
  run;
proc plot data=cet7.automeq;
  plot MajorDepression_dx*timezone='+' MajorDepression_dx*d_age='$' MajorDepression_dx*photoperiod='%' MajorDepression_dx*sunrise_local_sum_solst='@';
  title ' Plot of Major Depression by Geographic Variables ';
Run;

proc plot data=cet7.automeq;
  plot A9*sunrise_local_sum_solst='@';
  title 'Plot of Suicide by Local sunrise time';
proc plot data=cet7.automeq;
  plot A9*d_age='$';
  title 'Plot of Suicide by age';
proc plot data=cet7.automeq;
  plot A9*photoperiod='%';
  title 'Plot of Suicide by photoperiod';
proc plot data=cet7.automeq;
  plot A9*timezone='+';
  title 'Plot of Suicide by timezone';
proc plot data=cet7.automeq;
  plot A9*timezone='+' A9*photoperiod='%' A9*d_age='$' A9*sunrise_local_sum_solst='@';
  title 'Plot of Suicide by Geographic Variables';
Run;

proc plot data=cet7.automeq;
  plot Ascore*sunrise_local_sum_solst='@';
  title 'Plot of score on depression scale by Local sunrise time';
proc plot data=cet7.automeq;
  plot Ascore*d_age='$';
  title 'Plot of the score on the depression scale by age';
proc plot data=cet7.automeq;
  plot Ascore*photoperiod='%';
  title 'Plot of the score on depression scale by photoperiod';
proc plot data=cet7.automeq;
  plot Ascore*timezone='+';
  title 'Plot of the score on depression scale by timezone';
proc plot data=cet7.automeq;
  plot Ascore*timezone='+' Ascore*photoperiod='%' Ascore*d_age='$' Ascore*sunrise_local_sum_solst='@';
  title 'Plot of Score on the Depression scale by Geographic Variables';
Run;

%FinishAnODSReport;