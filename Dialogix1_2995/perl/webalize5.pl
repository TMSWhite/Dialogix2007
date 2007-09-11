# process Triceps log files
# (1) report => webalize.log.err.ips
#		(a) list of unique IPs (optional name), and number of hits from each
#		(b) list of unique browsers, and hits from each
#		(c) list of unique sessions, associated IP, resolved_name, start_time/date, stop_time/date, browser, page, steps, and raw_step-path
#		(d) for each session
#				IP
#				name
#				start time
#				stop time
#				duration
#				browser
#				instrument
#				num_Steps
#				path
# (2) report => convert to modified Common Log Format for processing by webalizer and others
#
# Triceps format is
#	command time session ip useragent accept_lang ??? instrument(step)
#   #@#(next) [Thu Jan 03 16:13:26 EST 2002] grfp3uw0c1 205.185.3.25 "Mozilla/4.7 [en] (Win95; U)" "en" "iso-8859-1,*,utf-8" CET/AutoMEQ-SA.jar(16)
#
#   @#(next) [Sun May 28 07:26:16 EDT 2006] 6C19507DCA69B9BD4CA247AD2E9BD7CA 212.123.161.253 "Mozilla/5.0 (Macintosh; U; PPC Mac OS X Mach-O; en-US; rv:1.8) Gecko/20051107 Camino/1.0b1" "en,nl;q=0.9,ja;q=0.9,fr;q=0.8,de;q=0.7,es;q=0.7,it;q=0.6,sv;q=0.5,no;q=0.5,da;q=0.4,fi;q=0.3,pt;q=0.3,zh-cn;q=0.2,zh-tw;q=0.1,ko;q=0.1" "ISO-8859-1,utf-8;q=0.7,*;q=0.7" CET/AutoMEQ-SA-irb.jar(446) OK HTTP [17879040, 6728920]
# Common Log Format is
#
#	%h %l %u %t \"%r\" %>s %b \"%{Referer}i\" \"%{User-agent}i\"
#	ip client userid time request status_code bytes_returned referrer useragent
#	127.0.0.1 - frank [10/Oct/2000:13:55:36 -0700] "GET /apache_pb.gif HTTP/1.0" 200 2326 "http://www.example.com/start.html" "Mozilla/4.08 [en] (Win98; I ;Nav)"
#
#	Transformation for Dialogix will be:
#		ip => ip
#		client => -
#		userid => session
#		time => time
#		request => instrument
#		status code => 200
#		bytes_returned => step
#		referrer = command
#		useragend => useragent
#
# TODO -- read a config file for $src, $dst, $JAR, list of IP addressed to exclude, list of instrument files to include
# 

use strict;
#use Net::hostent;
use Socket;
use HTTP::Date;
use Archive::Tar;

# things to do:
# (1) was session closed?  copyFile => success
# (2) false starts

my (%ips, %browsers, %sessions, %ip2host, @dates);

my $src_dir = "/usr/local/dialogix/logs";
my $dst_dir = "/home/tmw/webalizer";

&main;

sub main {
	open (LOG,">$dst_dir/webalize.log") or die "unable to open $dst_dir/webalize.log";
	open (CLF,">$dst_dir/webalize.clf") or die "unable to open $dst_dir/webalize.clf";
	&readLogs;
	&writeTimes;
	&writeReport;
	close (LOG);
	close (CLF);
}

sub readLogs {
	# dialogix files (which are newer)
	@files = glob("$src_dir/Dialogix.*");
	push(@sorted_files, sort { lc($a) cmp lc($b) } @files);
	
	foreach (@sorted_files) {
		readLog($_);
	}
}

sub readLog {
	my $file = shift;
	open (SRC,"<$file")	or die "unable to open $file";
	
	while (<SRC>) {
		chomp;
		my %info = %{ &readLine($_) };
		
		my ($sessionID, $start, $steps, $cstart);
				
		if ($info{'valid'} != 0) {
			print LOG "OK($info{'valid'}) => $info{'line'}\n";
		}
		else {
			print LOG "bad => $info{'line'}\n";
		}
		
		if ($info{'valid'}) {
#			next if (&skipThis($info{'ip'},$info{'schedule'}));
			
			# write Common Log format file
			print CLF "$info{'ip'} - $info{'sessionID'} [" . 
				&clf_time($info{'date'}) . 
				"] \"GET dialogix.cgi?instrument=/$info{'schedule'}?step=$info{'step'}&command=$info{'command'} HTTP/1.0\" 200 1024 \"$info{'command'}\" \"$info{'browser'}\"\n";

			my $ctime = $info{'date'};
			$ctime =~ s/E[DS]T //;
			$ctime = str2time($ctime);
			
			push @dates, { date=>$info{'date'}, ctime=>$ctime };
			
			++$ips{$info{'ip'}};
			++$browsers{$info{'browser'}};
			
			if ($info{'sessionID'}) {
				$sessionID = $info{'sessionID'};
				
				if (exists $sessions{$sessionID}) {
					my %session = %{ $sessions{$sessionID} };
					$start = $session{'start'};
					$cstart = $session{'cstart'};
					$steps = $session{'steps'};
				}
				
				$sessions{$sessionID} = {
					id => $sessionID,
					start => ($start ? $start : $info{'date'}),
					cstart => ($cstart ? $cstart : $ctime),
					stop => $info{'date'},
					cstop => $ctime,
					ip => $info{'ip'},
					browser => $info{'browser'},
					schedule => $info{'schedule'},
					steps => ($steps . " $info{'step'}"),
				};
			}
		}
	}
	close (SRC);
}

sub skipThis {
	my $ip = shift;
	my $schedule = shift;
	return 1 if ($ip eq '156.111.139.159');	# mine
	return 1 if ($ip eq '156.111.139.242');	# Terman's
	return 1 if ($ip eq '156.111.178.193');	# piwhite
	return 1 if ($ip eq '156.111.80.79');	# new piwhite
	return 1 if ($ip eq '156.111.80.78');	# new mine
	return 1 if ($ip eq '198.190.230.66');	# OMH
	return 1 unless ($schedule =~ /AutoMEQ/);
	return 0;
}


sub readLine {
	my $arg = shift;
	my $ctime;
	
	if ($arg =~ /\((.+?)\)\s\[(.+?)\]\s(.+?)\s(.+?)\s"(.+?)\"\s\".+?\"\s\".+?\"\s(.+?)(\((\d+?)\))?\s((OK)|(FINISHED)|(UNSUPPORTED BROWSER)|(EXPIRED SESSION)).*$/o) {
		# syntax from Feb 18, 2002 -> present
		##@#(next) [Fri Aug 03 18:50:14 EDT 2001] xqa985ado1 156.111.139.159 "Mozilla/4.77 [en] (Win98; U)" "en" "iso-8859-1,*,utf-8" CET/AutoMEQ-SA.jar(10) OK
		#@#(START) [Thu Jan 03 16:07:36 EST 2002] grfp3uw0c1 205.185.3.25 "Mozilla/4.7 [en] (Win95; U)" "en" "iso-8859-1,*,utf-8" null

		return {
			valid => 5,
			line => $arg,
			command => $1,
			date => $2,
			sessionID => $3,
			ip => $4,
			browser => $5,
			schedule => $6,
			step => (defined($8) ? $8 : -1),
		};
	}	
	else {
		return { 
			valid => 0, 
			line => $arg,
		};
	}
}

sub convertTime {
	my $arg = shift;
	chomp;
	if ($arg =~ /(\w+?)\s+(\w+?)\s+(\d+?)\s+(\d+?):(\d+?):(\d+?)\s+(\w+?)\s+(\d\d\d\d)/) {
		#Wed Jun 06 14:18:33 EDT 2001
		return &month($2) . "/$3/$8 $4:$5:$6";
	}
	
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

sub writeTimes {
	open (OUT, ">$dst_dir/webalize.log.err.times") or die "unable to open $dst_dir/webalize.log.err.times";
	
	foreach my $date (@dates) {
		my %s = %{ $date };
		print OUT "$s{'date'}\t$s{'ctime'}\t". &convertTime($s{'date'}) . "\n";
	}
	close (OUT);
}

sub resolveHosts {
	foreach my $ip (sort keys(%ips)) {
		my $host = gethostbyaddr(inet_aton($ip),AF_INET);
		$ip2host{$ip} = $host;
	}	
}

sub writeReport {
	open (OUT,">$dst_dir/webalize.log.err.ips")	or die "unable to open $dst_dir/webalize.log.err.ips";
	
	foreach my $ip (sort keys(%ips)) {
		print OUT "$ip\t" . $ip2host{$ip} . "\t$ips{$ip}\n";
	}
	print OUT "\n";
		
	foreach my $type (sort keys(%browsers)) {
		print OUT "$type\t$browsers{$type}\n";
	}
	print OUT "\n";
	
	foreach my $sess (sort keys(%sessions)) {
		my %s = %{ $sessions{$sess} };
		my $duration = $s{'cstop'} - $s{'cstart'};
		my @a_steps = split(/ /,$s{'steps'});
		my $steps = $#a_steps;
		print OUT "$sess\t$s{'ip'}\t" . $ip2host{$s{'ip'}} . "\t" . &convertTime($s{'start'}) . "\t" . &convertTime($s{'stop'}) . "\t$duration\t$s{'browser'}\t$s{'schedule'}\t$steps\t$s{'steps'}\n";
	}
	print OUT "\n";
	
	foreach my $sess (sort keys(%sessions)) {
		my %s = %{ $sessions{$sess} };
		my $duration = $s{'cstop'} - $s{'cstart'};
		my @a_steps = split(/ /,$s{'steps'});
		my $steps = $#a_steps;		
		print OUT "$sess\n\t$s{'ip'}\n\t" . $ip2host{$s{'ip'}} . "\n\t$s{'start'}\n\t$s{'stop'}\n\t$s{'cstart'}\n\t$s{'cstop'}\n\t$duration\n\t$s{'browser'}\n\t$s{'schedule'}\n\t\t$steps\n\t\t$s{'steps'}\n";
	}
	print OUT "\n";
		
	close OUT;
}

sub clf_time {
	my $arg = shift;
	chomp;
	if ($arg =~ /(\w+?)\s+(\w+?)\s+(\d+?)\s+(\d+?):(\d+?):(\d+?)\s+(\w+?)\s+(\d\d\d\d)/) {
		#input => Wed Jun 06 14:18:33 EDT 2001
		#output => 10/Oct/2000:13:55:36 -0700
		return "$3/$2/$8:$4:$5:$6 -0000";
	}
}
