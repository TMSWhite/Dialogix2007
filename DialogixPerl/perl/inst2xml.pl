# perl script for reading tab delimited instrument files and converting them to XML
# added ability to generate SQL insert statements

use strict;
use IO::File;
use XML::Parser;
use Data::Dumper;
use Cwd;

my $PERLDIR = "c:/cvs2/dialogix/perl";
my $TIDY_EXE = "$PERLDIR/tidy.exe";
my $TIDY_CONF = "$PERLDIR/tidy.conf";
my $DIFF_EXE = "$PERLDIR/diff.exe -bdiw";
my $USETIDY = 0;
my $CONVERT2XML = 0;
my $CREATETIDY = 0;
my $VALIDATEXML = 0;
my $RECURSEDIRS = 0;
my $CALCDIFFS = 0;
my $MAKELIST = 0;
my $ONLY_JARS = 0;
my $SHOW_LOGIC = 0;
my @FILEGLOB = ('*');
my $inst_dir;


my $dataTypes = {
	check => 'string',
	combo => 'string',
	combo2 => 'string',
	date => 'date',
	day => 'day',
	day_num => 'day_num',		# since start of year.
	double => 'number',
	hour => 'hour',
	list => 'string',
	list2 => 'string',
	memo => 'string',
	minute => 'minute',
	month => 'month',		# since name of the month
	month_num => 'month_num',
	nothing => 'string',
	password => 'string',
	radio => 'string',
	radio2 => 'string',
	radio3 => 'string',
	second => 'second',
	text => 'string',
	time => 'time',
	weekday => 'weekday',	# since name of weekday
	year => 'year',
	
	number => 'number',
	string => 'string',
};	

&main;

sub main {
	if ($#ARGV != 7) {
		die "usage\ninst2xml.pl <convert2xml> <createTidy> <validateXML> <calcDiffs> <recurseDirs> <makeList> <only_jars> <show_logic>\n";
	}
	($CONVERT2XML, $CREATETIDY, $VALIDATEXML, $CALCDIFFS, $RECURSEDIRS, $MAKELIST, $ONLY_JARS, $SHOW_LOGIC) = @ARGV;
	
	my $srcdir = Cwd::cwd();
	print "[$srcdir]\n";
	$inst_dir = $srcdir;
	
	open (INST_LIST,">Dialogix_instrument_list.txt") or die "unable to dump to Dialogix_instrument_list.txt";
	print INST_LIST "Group\tBase\tTitle\tVersion\tDate\tNumLanguages\tNumQs\tNumEs\tNumOptionalQs\tLaunchCommand\n";
	
	foreach (@FILEGLOB) {
		&look($_);
	}
	
	close (INST_LIST);
}

sub look {
	my @files = sort(glob(shift));
	my @txtfiles = sort(grep(/\.txt$/,@files));
	my @dirs;
	foreach (@files) {
		push @dirs, $_ if (-d);
	}
	
	foreach (@txtfiles,@dirs) {
		&processDir($_);
	}
}

sub processDir {
	my $file = shift;
	
	if (-d $file && $RECURSEDIRS) {
		my $srcdir = Cwd::cwd();
		if (chdir($file)) {
			print "[$srcdir/$file]\n";
			$inst_dir = "$srcdir/$file";
			
			foreach (@FILEGLOB) {
				&look($_);
			}
			chdir($srcdir);
			print "[$srcdir]\n";
			$inst_dir = $srcdir;
		}
		else {
			print "ERR - unable to chdir to $file";
		}
	}
	elsif ($file =~ /\.txt$/) {
		my $xmlfile;
		if ((-f $file) && (-r $file)) {
			$xmlfile = &inst2xml($file);
			return if ($xmlfile eq '');	# not a valid file
			if ((-f $xmlfile) && (-r $xmlfile)) {
				&validate_xml($xmlfile);
				&calc_diffs($xmlfile);
			}
			else {
				print "unable to find $xmlfile\n";
			}			
		}
	}
}

sub calc_diffs {
	my $dst = shift;
	my $src = $dst;
	$src =~ s/\.tidy\././;
	
	return unless ($CALCDIFFS);
	
	my $starttime = time;
	my $cmd = "$DIFF_EXE $src $dst";
	my @results = qx|$cmd|;
	if ($#results <= 0) {
		print "No diffs between  \"$src\" and tidied version\n";
	}
	else {
		if (!open (DIFF, ">$src.diff")) {
			print "unable to save diff to $src.diff\n";
			return;
		}
		print DIFF $cmd, "\n";
		my $count = 0;
		foreach (@results) {
			++$count if (/^---/);
			print DIFF $_;
		}
		close (DIFF);
		my $stoptime = time;
		print "$count diffs saved in \"$src.diff\" in " . ($stoptime - $starttime) . " seconds\n";
	}
	&show_problems($src,$dst);
}

sub show_problems {
	my ($src,$dst) = @_;
	
	my $starttime = time;
	
	if (!open (IN,"<$dst")) {
		print "unable to read from $dst\n";
		return;
	}
	
	my @lines = (<IN>);
	close (IN);
	
	if (!open(PROBS,">$dst.problems")) {
		print "unable to write to $dst.problems\n";
		return;
	}
	
	my $linenum = -1;
	my @errs;
	my $probcount = 0;
	foreach my $line (@lines) {
		if ($line =~ /linenum="(\d+?)">/) {
			# print problems
			if ($linenum >= 0 && $#errs >= 0) {
				++$probcount;
				print PROBS "<!-- === " . ($#errs + 1) . " POTENTIAL SYNTAX PROBLEMS ON LINE $linenum === -->\n";
				foreach (@errs) {
					print PROBS "$_";
				}
			}
			# reset counters
			$linenum = $1;
			undef @errs;
		}
		if ($line =~ /_binary_((?:and)|(?:or))_/) {
			push @errs, "	<!-- __binary_$1_ instead of $1 -->\n$line";
		}
		if ($line =~ /_assign_/) {
			push @errs, "	<!-- _assign_ instead of eq -->\n$line";
		}
		if ($line =~ /&((?:amp)|(?:lt)|(?:gt));/) {
			push @errs, "	<!-- &$1; -- could be bad variable name or equation -->\n$line";
		}
	}
	# print remaining problems
	if ($linenum >= 0 && $#errs >= 0) {
		++$probcount;
		print PROBS "<!-- === " . ($#errs + 1) . " POTENTIAL SYNTAX PROBLEMS ON LINE $linenum === -->\n";
		foreach (@errs) {
			print PROBS "$_";
		}
	}
	
	close (PROBS);
	my $timediff = (time - $starttime);

	if ($probcount == 0) {
		unlink("$dst.problems");
		return;
	}
	
	print "Wrote $probcount ?syntax problems to \"$dst.problems\" in $timediff seconds\n";
}

sub inst2xml {
	my $filename = shift;
	
	my $meta_group = &groupname($inst_dir);
	if ($meta_group eq '') {
		return '';
	}
	
	my $base = &basename($filename);
	if ($ONLY_JARS) {
		if (!((-f "$base.jar") && (-r "$base.jar"))) {
			return '';
		}
	}
	my $file_creation_date = &FileCreationDate($filename);
	
	# read data	
	open(IN,$filename) or die "unable to read from $filename\n";
	
	my @src = (<IN>);
	chomp(@src);
	close (IN);
	
	my $meta_instdir = $inst_dir;
	if ($meta_instdir =~ /^C:(.*)$/) {
		$meta_instdir = $1;
	}
	my ($num_qs, $num_es, $num_always_qs, $num_languages) = (0, 0, 0, 0);
	my ($meta_title, $meta_version_major, $meta_version_minor) = ('*UNTITLED*', 0, 0);
		
	# want tidied an original versions -- cheat by using global variables and processing twice, rather than re-writing access to &parse_html and &parse_eqn;
	if ($CONVERT2XML) {
		foreach my $tidy (0 .. ($CREATETIDY == 1)) {
			# parse the lines
			my @lines;
			my @languages = ( 'en_US' );	# default, if nothing specified, is to use English
			my ($starttime,$stoptime);
			$starttime = time;
			
			$USETIDY = $tidy;	# a global variable
			
			# read the data, without processing it (other than to remove Excel's extraneous quotes
			foreach (@src) {
				next if (/^\s*$/);
				if (/^\s*RESERVED/) {
					my $reserved = &parse_reserved($_);	# this pre-supposes that all reserveds must be parsed first -- isnt' this true in headers vs. body?
					if ($reserved->{'resname'} eq '__LANGUAGES__') {
						@languages = &parse_languages($reserved->{'resval'});
					}
					elsif ($reserved->{'resname'} eq '__TITLE__') {
						$meta_title = $reserved->{'resval'};
					}
					elsif ($reserved->{'resname'} eq '__SCHED_VERSION_MAJOR__') {
						$meta_version_major = $reserved->{'resval'};
					}					
					elsif ($reserved->{'resname'} eq '__SCHED_VERSION_MINOR__') {
						$meta_version_minor = $reserved->{'resval'};
					}
					
					push @lines, $reserved;
				}
				elsif (/^\s*COMMENT/) {
					push @lines, &parse_comment($_);
				}
				else {
					if ($#lines < 1) {
						# then no reserveds found, so not a valid file
						return '';
					}
					my $nodeval = &parse_node($_);
					push @lines, $nodeval;
					
					if ($nodeval->{'qoreval'}->{'qoreval'} =~ /^\s*e\s*/i) {
						++$num_es;
					}
					else {
						++$num_qs;
						if ($nodeval->{'relevance'}->{'rel_new'} =~ /^\s*1\s*$/) {
							++$num_always_qs;
						}						
					}
				}
			}
			
			$num_languages = ($#languages + 1);
			
			# write them as XML (initially not taking advantage of Perl's XML / DOM features
			my $xmlfile = $base;
			if ($tidy) {
				$xmlfile = "$base.tidy";
			}
			&write_xml($base,$xmlfile,\@languages,\@lines);
			my $stoptime = time;
			print "wrote XML to $xmlfile.xml in " . ($stoptime - $starttime) . " seconds\n";			
		}
	}
	unlink("tmp.tmp");	# remove extraneous file used by tidy.
	
	# print instrument info to tab delimited file so can call from PHP 
	print INST_LIST "$meta_group\t$base\t$meta_title\t$meta_version_major.$meta_version_minor\t$file_creation_date\t$num_languages\t$num_qs\t$num_es\t$num_always_qs" .
		"\thttp://psychinformatics.nyspi.org:8080/$meta_group/servlet/Dialogix?schedule=$meta_group/WEB-INF/schedules/$base.jar&DIRECTIVE=START\n";
		
	if ($SHOW_LOGIC) {
		&showLogic("$base.txt","$base.htm","$meta_title v. $meta_version_major.$meta_version_minor $file_creation_date");
	}
	return "$base.tidy.xml";
}

sub validate_xml {
	my $filename = shift;

	if (!$VALIDATEXML) {
#		print "skipping validation of \"$filename\"\n";
		return;
	}
	
	my ($starttime,$stoptime);
	$starttime = time;	
	
	my $parser = new XML::Parser(Style => 'Tree', ErrorContext => 2);
	my $parse = $parser->parsefile($filename);
	$stoptime = time;
	print "Validating \"$filename\" took " . ($stoptime - $starttime) . " seconds\n";
#	&dump_parse($filename,$parse);
}

sub dump_parse {
	my ($filename, $parse) = @_;
	
	open (DUMP,">$filename.dump;") or die "unable to dump to $filename.dump";
	print DUMP Data::Dumper->Dump($parse);
	close (DUMP);
}

sub parse_languages {
	my $arg = shift;
	my @args = split(/\|/,$arg);
	return @args;
}


sub write_xml {
	my ($base,$filename,$rlangs,$rlines) = @_;
	my @langs = @$rlangs;
	my @lines = @$rlines;
	
	open (OUT,">$filename.xml") or die "unable to write XML to $filename.xml\n";
	
	#write XML header
	print OUT qq|<?xml version="1.0" encoding="ISO-8859-1"?>|, "\n";
	print OUT qq|<dialogix_instrument src="$base">|, "\n";
	
	my $linenum = 0;
	foreach my $line (@lines) {
		# try to cheat by working recursively?
		my $type = lc($line->{'line_type'});
		++$linenum;
		
		print OUT "<$type linenum=\"$linenum\">\n";
		if ($type eq 'reserved') {
			print OUT "	<resname>$line->{'resname'}</resname>\n";
			print OUT "	<resval>$line->{'resval'}</resval>\n";
		}
		elsif ($type eq 'comment') {
			print OUT "	<commentval>$line->{'comment'}</commentval>\n";
		}
		else {
			print OUT "	<concept>$line->{'concept'}</concept>\n";
			print OUT "	<uniqueName>$line->{'uniqueName'}</uniqueName>\n";
			print OUT "	<displayName>$line->{'displayName'}</displayName>\n";
			print OUT "	<relevance>$line->{'relevance'}->{'rel_new'}</relevance>\n";
			print OUT "	<actionSymbol>$line->{'qoreval'}->{'qoreval'}</actionSymbol>\n";
			print OUT "	<actionType>$line->{'qoreval'}->{'actionType'}</actionType>\n";
			print OUT "	<nesting>$line->{'qoreval'}->{'nesting'}</nesting>\n";
			print OUT "	<dataType>$line->{'dataType'}</dataType>\n";
			print OUT "	<displayType>$line->{'displayType'}</displayType>\n";
			print OUT "	<validation>\n";
			print OUT "		<castto>$line->{'qoreval'}->{'returntype'}</castto>\n";
			print OUT "		<min>$line->{'qoreval'}->{'min'}</min>\n";
			print OUT "		<max>$line->{'qoreval'}->{'max'}</max>\n";
			print OUT "		<mask>$line->{'qoreval'}->{'mask'}</mask>\n";
			print OUT "		<regex>$line->{'qoreval'}->{'regex'}</regex>\n";
			if ($line->{'qoreval'}->{'num_extras'} > 0) {
				print OUT "		<extras count=\"$line->{'qoreval'}->{'num_extras'}\">\n";
				my $rextras = $line->{'qoreval'}->{'extras'};
				my @extras = @$rextras;
				foreach my $extra (@extras) {
					print OUT "			<value>$extra</value>\n";
				}
				print OUT "		</extras>\n";
			}
			else {
				print OUT "		<extras count=\"0\"/>\n";
			}
			print OUT "	</validation>\n";
			print OUT "	<hows count=\"$line->{'num_hows'}\">\n";
			my $rhows = $line->{'hows'};
			my @hows = @$rhows;
			my $hcount = 0;
			foreach my $how (@hows) {
				++$hcount;
				print OUT "		<how index=\"$hcount\" lang=\"$langs[$hcount-1]\">\n";
				print OUT "			<readback>$how->{'readback'}</readback>\n";
				print OUT "			<helpURL>$how->{'helpURL'}</helpURL>\n";
				print OUT "			<actionExp>$how->{'action'}->{'action_exp'}</actionExp>\n";
				my $num_options = $how->{'answerChoices'}->{'num_options'};
				if ($num_options > 0) {
					my $roptions = $how->{'answerChoices'}->{'options'};
					my @options = @$roptions;
					
					print OUT "			<options count=\"$num_options\">\n";
					foreach my $option (@options) {
						print OUT "				<option index=\"$option->{'option_counter'}\">\n";
						print OUT "					<msg>$option->{'option_msg'}</msg>\n";
						print OUT "					<val>$option->{'option_val'}</val>\n";
						print OUT "				</option>\n";
					}
					print OUT "			</options>\n";
				}
				else {
					print OUT "			<options count=\"0\"/>\n";
				}
				print OUT "		</how>\n";
			}
			print OUT "	</hows>\n";
		}
		print OUT "</$type>\n";
	}
	
	print OUT "</dialogix_instrument>\n";
	close (OUT);
}


sub write_xforms {
	my ($base,$filename,$rlangs,$rlines) = @_;
	my @langs = @$rlangs;
	my @lines = @$rlines;
	
	open (OUT,">$filename.xforms.xml") or die "unable to write XML to $filename.xforms.xml\n";
	
	#write XML header
	print OUT qq|	
		<html
		 xmlns="http://www.w3.org/1999/xhtml"
		 xmlns:xf="http://www.w3.org/2002/xforms"
		>
			<object id="FormsPlayer" classid="CLSID:4D0ABA11-C5F0-4478-991A-375C4B648F58" width="0" height="0">
				<b>FormsPlayer has failed to load! Please check your installation.</b>
				<br />
				<br />
			</object>
			<?import namespace="xf" implementation="#FormsPlayer"?>
	|, "\n";
	
	my $linenum = 0;
	foreach my $line (@lines) {
		# try to cheat by working recursively?
		my $type = lc($line->{'line_type'});
		++$linenum;
		
#		print OUT "<$type linenum=\"$linenum\">\n";
		if ($type eq 'reserved') {
			print OUT "	<resname>$line->{'resname'}</resname>\n";
			print OUT "	<resval>$line->{'resval'}</resval>\n";
		}
		elsif ($type eq 'comment') {
			print OUT "	<commentval>$line->{'comment'}</commentval>\n";
		}
		else {
			print OUT "	<concept>$line->{'concept'}</concept>\n";
			print OUT "	<uniqueName>$line->{'uniqueName'}</uniqueName>\n";
			print OUT "	<displayName>$line->{'displayName'}</displayName>\n";
			print OUT "	<relevance>$line->{'relevance'}->{'rel_new'}</relevance>\n";
			print OUT "	<actionSymbol>$line->{'qoreval'}->{'qoreval'}</actionSymbol>\n";
			print OUT "	<actionType>$line->{'qoreval'}->{'actionType'}</actionType>\n";
			print OUT "	<nesting>$line->{'qoreval'}->{'nesting'}</nesting>\n";
			print OUT "	<dataType>$line->{'dataType'}</dataType>\n";
			print OUT "	<displayType>$line->{'displayType'}</displayType>\n";
			print OUT "	<validation>\n";
			print OUT "		<castto>$line->{'qoreval'}->{'returntype'}</castto>\n";
			print OUT "		<min>$line->{'qoreval'}->{'min'}</min>\n";
			print OUT "		<max>$line->{'qoreval'}->{'max'}</max>\n";
			print OUT "		<mask>$line->{'qoreval'}->{'mask'}</mask>\n";
			print OUT "		<regex>$line->{'qoreval'}->{'regex'}</regex>\n";
			if ($line->{'qoreval'}->{'num_extras'} > 0) {
				print OUT "		<extras count=\"$line->{'qoreval'}->{'num_extras'}\">\n";
				my $rextras = $line->{'qoreval'}->{'extras'};
				my @extras = @$rextras;
				foreach my $extra (@extras) {
					print OUT "			<value>$extra</value>\n";
				}
				print OUT "		</extras>\n";
			}
			else {
				print OUT "		<extras count=\"0\"/>\n";
			}
			print OUT "	</validation>\n";
			print OUT "	<hows count=\"$line->{'num_hows'}\">\n";
			my $rhows = $line->{'hows'};
			my @hows = @$rhows;
			my $hcount = 0;
			foreach my $how (@hows) {
				++$hcount;
				print OUT "		<how index=\"$hcount\" lang=\"$langs[$hcount-1]\">\n";
				print OUT "			<readback>$how->{'readback'}</readback>\n";
				print OUT "			<helpURL>$how->{'helpURL'}</helpURL>\n";
				print OUT "			<actionExp>$how->{'action'}->{'action_exp'}</actionExp>\n";
				my $num_options = $how->{'answerChoices'}->{'num_options'};
				if ($num_options > 0) {
					my $roptions = $how->{'answerChoices'}->{'options'};
					my @options = @$roptions;
					
					print OUT "			<options count=\"$num_options\">\n";
					foreach my $option (@options) {
						print OUT "				<option index=\"$option->{'option_counter'}\">\n";
						print OUT "					<msg>$option->{'option_msg'}</msg>\n";
						print OUT "					<val>$option->{'option_val'}</val>\n";
						print OUT "				</option>\n";
					}
					print OUT "			</options>\n";
				}
				else {
					print OUT "			<options count=\"0\"/>\n";
				}
				print OUT "		</how>\n";
			}
			print OUT "	</hows>\n";
		}
		print OUT "</$type>\n";
	}
	
	print OUT "</dialogix_instrument>\n";
	close (OUT);
}

sub groupname {
	my $arg = shift;
	
	if ($arg =~ /^C:\/cvs2\/Dialogix\/web(-test)*\/(.*)\/WEB-INF\/schedules$/) {
		return $2;
	}
	elsif ($arg =~ /^C:\/usr\/local\/dialogix\/webapps\/(.*)\/WEB-INF\/schedules$/) {
		return $1;
	}
	else {
		return '?';
	}
}

sub basename {
	my $arg = shift;
	
	if ($arg =~ /^(.*[\\\/])*(.*?)\.txt$/) {
		return $2;
	}
	else {
		return $arg;
	}
}

sub parse_reserved {
	my $line = shift;
	my @args = &deExcelize(split(/\t/,$line));
	return {
		line_type => 'RESERVED',
		resname => $args[1],
		resval => &parse_html($args[2])
	};
}

sub parse_comment {
	my $line = shift;
	$line =~ s/\s*COMMENT\s*//;
	return {
		line_type => 'COMMENT',
		comment => &parse_html($line),
	};
}

#COMMENT concept	internalName	externalName	dependencies	questionOrEvalType	readback[0]	questionOrEval[0]	answerChoices[0]	helpURL[0]	readback[1]	questionOrEval[0]	answerChoices[1]	helpURL[1]	languageNum	questionAsAsked	answerGiven	comment	timeStamp

sub parse_node {
	my $line = shift;
	my @args = &deExcelize(split(/\t/,$line));
	
	my $concept = &parse_html(shift(@args));
	my $uniqueName = &parse_html(shift(@args));
	my $displayName = &parse_html(shift(@args));
	my $relevance = &parse_relevance(shift(@args));
	my $qoreval = &parse_qoreval(shift(@args));
	
	# now a variable number of parameters, depending upon the number of languages encoded.
	
	my @hows;
	
	while (@args) {
		my $readback = &parse_html(shift(@args));
		my $action = &parse_action($qoreval->{'qoreval'},shift(@args));
		my $answerChoices = &parse_answerChoices(shift(@args));
		my $helpURL = &parse_helpURL(shift(@args));
		
		push @hows, {
			readback => $readback,
			action => $action,
			answerChoices => $answerChoices,
			helpURL => $helpURL,
		}
	}
	
	my $displayType = $hows[0]->{'answerChoices'}->{'displayType'};
	my $dataType = &calc_dataType($qoreval->{'returnType'},$displayType);
	
	return {
		line_type => 'NODE',
		node_src => $line,
		concept => $concept,
		uniqueName => $uniqueName,
		displayName => $displayName,
		relevance => $relevance,
		qoreval => $qoreval,
		dataType => $dataType,
		displayType => $displayType,
		num_hows => ($#hows + 1),
		hows => \@hows,
	};
}

sub calc_dataType {
	my ($returnType,$displayType) = @_;
	
	if (defined($returnType) && exists($dataTypes->{$returnType})) {
		return $dataTypes->{$returnType};
	}
	elsif (defined($displayType) && exists($dataTypes->{$displayType})) {
		return $dataTypes->{$displayType};
	}
	else {
		return "*badtype ($returnType, $displayType)*";
	}
}

sub parse_helpURL {
	my $arg = shift;
	
	# option to check integrity of URL
	
	return $arg;
}


sub parse_answerChoices {
	my $arg = shift;
	my @args = split(/\|/,$arg);
	
	my $type = lc(shift(@args));
	
	my @options;
	my $counter = 0;
	while (@args) {
		my $val = &parse_eqn(shift(@args));
		my $msg = &parse_html(shift(@args));
		
		++$counter;
		push @options, {
			option_counter => $counter,
			option_msg => $msg,
			option_val => $val,
		}
	}
	
	return {
		displayType => $type,
		num_options => $counter,
		options => \@options,
	};
}


sub parse_action {
	my ($type,$line) = @_;
	
	if ($type eq 'e') {
		return {
			action_orig => $line,
			action_exp => &parse_eqn($line),
		};
	}
	else {
		my $temp = $line;
		my $parsed;
		
		while (1) {
			if ($temp =~ /^(.*?)`(.*?)`(.*)$/) {
				$parsed .= $1 . '`' . &parse_eqn($2) . '`';
				$temp = $3;
			}
			else {
				$parsed .= $temp;
				last;
			}
		}

		return {
			action_orig => $line,
			action_exp => &parse_html($parsed),
		}
	}
}

#		];number;0;(tAGE-10);;77;88;99
sub parse_qoreval {
	my $arg = shift;
	my @args = split(/;/,$arg);
	
	my $qoreval = lc(shift(@args));
	my $returnType = lc(shift(@args));
	my $min = shift(@args);
	my $max = shift(@args);
	my $mask = shift(@args);
	my $regex = '';
	my $extras = \@args;
	my $num_extras = ($#args + 1);
	
	my $nesting;
	$nesting = 'start_block' if ($qoreval eq '[');
	$nesting = 'stop_block' if ($qoreval eq ']');
	
	my $actionType = 'question';
	$actionType = 'eval' if ($qoreval eq 'e');
	
	return {
		qoreval_full => $arg,
		qoreval => $qoreval,
		returntype => $returnType,
		min => $min,
		max => $max,
		mask => $mask,
		regex => $regex,
		num_extras => $num_extras,
		extras => $extras,
		nesting => $nesting,
		actionType => $actionType,
	};
}


sub parse_relevance {
	my $arg = shift;
	my $eqn = &parse_eqn($arg);
	
	return {
		rel_orig => $arg,
		rel_new => $eqn,
	};
}

sub parse_eqn {
	my @words = &parse_words(shift);
	my $retval;
	
	foreach my $word (@words) {
		if ($word =~ /^(['"])(.*?)(['"])$/) {
			$retval .= $1 . &parse_html($2) . $3;
		}
		else {
			$retval .= &fix_eqn($word);
		}
	}
	return $retval;
}


sub fix_eqn {
	my $arg = shift;
	
	# this gives the opportunity to convert a string to MathML, or to replace >= / ==, etc with less dangerous characters

	$arg =~ s/==/ eq /g;
	$arg =~ s/>=/ ge /g;
	$arg =~ s/>/ gt /g;
	$arg =~ s/<=/ le /g;
	$arg =~ s/</ lt /g;	
	$arg =~ s/!=/ ne /g;
	$arg =~ s/\&\&/ and /g;
	$arg =~ s/\&/ _binary_and_ /g;
	$arg =~ s/\|\|/ or /g;
	$arg =~ s/\|/ _binary_or_ /g;
	$arg =~ s/=/ _assign_ /g;
	
	$arg =~ s/  / /g;
	
	return $arg;
}

sub parse_html {
	my $arg = shift;
	
	my $exp = $arg;
	
	if ($USETIDY) {
		# don't call tidy if no markup present
		my $call_tidy = m/<.+>/;
		
		if ($call_tidy) {
			open (TEMP,">tmp.tmp") or die "unable to write to tmp.tmp";
			print TEMP $exp;
			close (TEMP);
			my @results = qx|$TIDY_EXE -config $TIDY_CONF < tmp.tmp|;
			chomp(@results);
			$exp = '';
			# trim the extra space that tidy includes
			foreach my $result (@results) {
				if ($result =~ /^\s*(.*?)\s*$/) {
					$exp .= $1;
					if ($exp =~ /([:.?!])$/) {
						$exp .= " ";	# add a space after the end of a sentence, as needed
					}
				}
			}
#			$exp = join(' ',@results);
			
			$exp =~ s/%20/ /g;
		}
		else {
			# remove special characters
			$exp =~ s/&(?![#\d\w]{1,5};)/&amp;/g;
			$exp =~ s/</&lt;/g;
			$exp =~ s/>/&gt;/g;
		}
	}
	
	# these conversion are done by all to facilitate comparison
	$exp =~ s/&nbsp;/&#160;/g;
	$exp =~ s|<\s*br\s*/\s*>|<br />|gi;
	$exp =~ s|<\s*br\s*>|<br />|gi;
	$exp =~ s|([:.?!])\s+|$1 |g;
	$exp =~ s|^\s*(.*?)\s*$|$1|;		# remove trailing space at the start and end of a string
	
	return $exp;
}

sub deExcelize {
	my @args = @_;
	my @fixed;
	
	foreach my $arg (@args) {
		if ($arg =~ /^\s*"(.*)"\s*$/) {
			$arg = $1;
			$arg =~ s/""/"/g;
		}
		push @fixed, $arg;
	}
	return @fixed;
}

# creates list of words from text with embedded strings
sub parse_words {
	my @toks = &tokenize_string(shift);
	my ($word, $tok, $subtok, @words);
	
	while (@toks) {
		$tok = shift(@toks);
		if ($tok eq "\"") {
			$word = $tok;
			while (@toks) {
				$subtok = shift(@toks);
				$word .= $subtok;
				last if ($subtok eq "\"");
			}
			if (rindex($word,"\"") != (length($word)-1)) {
				$word .= "\"";	# add terminating quote if missing?
			}
			push @words, $word;
		}
		elsif ($tok eq "\'") {
			$word = $tok;
			while (@toks) {
				$subtok = shift(@toks);
				$word .= $subtok;
				last if ($subtok eq "\'");
			}
			if (rindex($word,"\'") != (length($word)-1)) {
				$word .= "\'";	# add terminating quote if missing?
			}
			push @words, $word;			
		}
		else {
			push @words, $tok;
		}
	}
	return @words;
}

# creates list of tokens from text with embedded / nested strings
sub tokenize_string {
	my @toks = split(/(?<!\\)(['"])/,shift);
	my @newtoks;
	foreach (@toks) {
		push @newtoks, $_ if ($_ ne '');
	}
	return @newtoks;
}


sub FileCreationDate {
	my $filename = shift;
	
	my ($dev,$ino,$mode,$nlink,$uid,$gid,$rdev,$size,$atime,$mtime,$ctime,$blksize,$blocks)
           = stat($filename);
           
	my $arg = localtime($mtime);
	if ($arg =~ /(\w+?)\s+(\w+?)\s+(\d+?)\s+(\d+?):(\d+?):(\d+?)\s+(\d\d\d\d)/) {
		#Wed Jun 06 14:18:33 2001
		return "$7-" . &month($2) . "-$3";
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

sub showLogic {
	my $infile = shift;
	my $outfile = shift;
	my $title = shift;
	my $command = "perl $PERLDIR/showlogic.pl $infile $outfile \"$title\"";
	&doit($command);
}

sub doit {
	my $cmd = shift;
	print "$cmd\n";
	(system($cmd) == 0)	or die "ERROR";
}