#! /usr/bin/perl
#
# perl script to cycle Dialogix logs when they are full
#   (1) greps file for OutOfMemory errors
# 	(1) stops Tomcat
# 	(2) changes name of $logname file with suffix of starting date of logs
# 	(3) re-starts Tomcat

use strict;

my $prefix = "/usr/local/dialogix";
#my $java_home = `which java`;
#chomp($java_home);
#$java_home =~ s|^(.*)/bin/java|$1|;
my $java_home = "/opt/jdk1.5.0_06";
my $logs = "$prefix/logs";
my $logname = "Dialogix.log.err";
my $bin = "$prefix/bin";
my $datefmt = "%04d.%02d.%02d";
my $errorflag = 0;

&main;

sub main {
	# test whether server is running - restart it if needed
	# check that java is running
	my @java_ps = qx/ps ax | grep java | grep dialogix | cut -d ' ' -f 1/;
	if ($#java_ps == 0) {
		# then processes are not running - start them
		print "Dialogix not running - restarting it\n";
		&restart_server;
		return;
	}
	
	# check whether Tomcat is out of memory
	open(LOG,"</usr/local/dialogix/logs/catalina.out") or warn "unable to open catalina.out";
	my @lines = (<LOG>);
	close (LOG) or warn "unable to close catalina.out";	
	
	foreach my $line (@lines) {
		if ($line =~ /OutOfMemoryError/) {
			$errorflag = 1;
			last;
		}
		if ($line =~ /BindException/) {
			$errorflag = 1;
			last;
		}
		if ($line =~ /\(Too many open files\)/) {
			$errorflag = 1;
			last;
		}
	}
	if ($errorflag == 1) {
		print "Tomcat out of memory - restarting it\n";
		&kill_java_process;	# don't bother trying to shutdown gracefully, won't be able to.
		&shutdown_tomcat;	# this should free the port, if needed
		&rotate_logs;
		sleep(20);	# give Tomcat a few moments to run
		&restart_server;
		return;
	}
			
	if (!(-e "/usr/local/dialogix/logs/Dialogix.log.err")) {
		print "Dialogix running, but no data collected yet\n";
		return;
	}

	open(LOG,"</usr/local/dialogix/logs/Dialogix.log.err") or die "unable to open Dialogix.log.err";
	my @lines = (<LOG>);
	close (LOG) or die "unable to close Dialogix.log.err";
	
	foreach my $line (@lines) {
		if ($line =~ /OutOfMemoryError/) {
			$errorflag = 1;
			last;
		}
		if ($line =~ /\(Too many open files\)/) {
			$errorflag = 1;
			last;
		}
	}
		
	if ($errorflag == 0) {
		print "Dialogix running, data being collected to Dialogix.log.err\n";
		exit;
	}
	
	print "Dialogix out of memory - Shutting down and restarting Tomcat\n";
	
	&shutdown_tomcat;
	
	&rotate_logs;
	
	#wait a few seconds to ensure that Tomcat completely stopped
	sleep(20);
	
	&kill_java_process;
	
	&restart_server;
}

sub restart_server {
	unlink("$logs/catalina.out");	# since may have OutOfMemory error in it
	my $val = system("$bin/startup.sh");	
	if ($val != 0) {
		print "failed to restart tomcat -- err code " . ($val/256) . "\n";
#		&show_err_logs;
	}
	else {
		print "tomcat restarted\n";
	}
}

sub show_err_logs {
	open (ERR, "</tmp/rotate_dialogix.stdout");
	while (<ERR>) { chomp; print "$_\n"; }
	close (ERR);
	open (ERR, "</tmp/rotate_dialogix.stderr");
	while (<ERR>) { chomp; print "$_\n"; }
	close (ERR);
}

sub month {
	$_ = shift;
	return '01' if (/Jan/i);
	return '02' if (/Feb/i);
	return '03' if (/Mar/i);
	return '04' if (/Apr/i);
	return '05' if (/May/i);
	return '06' if (/Jun/i);
	return '07' if (/Jul/i);
	return '08' if (/Aug/i);
	return '09' if (/Sep/i);
	return '10' if (/Oct/i);
	return '11' if (/Nov/i);
	return '12' if (/Dec/i);
	return '00';
}

sub kill_java_process {
		#now check that all of the java processes are terminated
	my @java_ps = qx/ps ax | grep java | grep dialogix | cut -d ' ' -f 1/;
	foreach (@java_ps) {
		system("kill -9 $_");
	}
}

sub rotate_logs {
	open(LOG,"<$logs/$logname");
	my @loglines = (<LOG>);
	my $startDate = $loglines[0];
	close (LOG);
	
	#default date is yesterday if can't read file
	my @tm= localtime(time() - (60*60*24));
	my $date = sprintf($datefmt,(1900+$tm[5]),(1+$tm[4]),$tm[3]);
	
	# otherwise, have re-name date be the day when the log file was started
	if ($startDate =~ /started on \w{3} (\w{3}) (\d+) \d+:\d+:\d+ \w+ (\d{4})/) {
	#	**CET version of Dialogix Interviewing System version 2.9.4 Log file started on Thu Jan 17 16:36:06 EST 2002
		$date = sprintf($datefmt,$3,&month($1),$2);
	}
	
	# safely copy contents to new file, appending as needed.
	if (-e "$logs/$logname") {
		if (-e "$logs/$logname.$date") {
			open (OUT,">>$logs/$logname.$date");
			foreach (@loglines) {
				print OUT $_;
			}
			close (OUT);
			unlink("$logs/$logname");
		}
		else {
			rename("$logs/$logname","$logs/$logname.$date");
		}
	}	
}

sub shutdown_tomcat {
	# also releases the port, if needed
	
	$ENV{'JAVA_HOME'} = $java_home;
	my $val = system("$bin/shutdown.sh");	
	if ($val != 0) {
		print "failed to shutdown tomcat -- err code " . ($val/256) . "\n";
		&show_err_logs;
	}
	else {
		print "tomcat stopped\n";
	}	
}