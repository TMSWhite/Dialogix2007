#/* ******************************************************** 
#** Copyright (c) 2000-2001, Thomas Maxwell White, all rights reserved. 
#** $Header$
#******************************************************** */ 

# This perl script checks whether any equation references a variable before it is defined
# Usage:
#		perl convertAbt.pl <directory name> <log file>
#	(so reads instrument from inst.txt, and writes writes the file with embedded error messages to out.txt, and writes just the errors to errs.txt)


use strict;

use File::Basename;
use XML::XPath;
use XML::XPath::XMLParser;

&main();

sub main {
	open (LOG,">convertAbtruments.log") or die "unable to write log to convertAbtruments.log";
	
	&look(".");
	
	close (LOG);
}

sub look {
	my @files = glob(shift);
	
	foreach (@files) {
		&processDir($_);
	}
}

sub processDir {
	my $file = shift;
	
	if (-d $file) {
		&look("$file/*");
	}
	else {
		return unless ($file =~ /^.+\.xml$/);
		print LOG "[$file]\t";
		print LOG &processFile($file) . "\n";
	}
}

sub processFile {
	my $srcfile = shift;
	my $instname = basename($srcfile,"\.xml");
	my $outfile = "$instname.txt";	# for Dialogix
	
	if (!open (OUT,">$outfile")) {
		return "unable to open $outfile to write Dialogix";
	}
	
  my $xp = XML::XPath->new(filename => $srcfile);
  
	print OUT "RESERVED	__TRICEPS_FILE_TYPE__	SCHEDULE\n";
	print OUT "RESERVED	__ICON__	dialogo.jpg\n";
	print OUT "RESERVED	__LANGUAGES__	en\n";
	print OUT "RESERVED	__SCHED_VERSION_MAJOR__	1\n";
	print OUT "RESERVED	__SCHED_VERSION_MINOR__	0\n";

	my $title = $xp->getNodeText('/Survey/@name');
	print OUT "RESERVED	__TITLE__	$title\n";
	print OUT "RESERVED	__HEADER_MSG__	$title\n";
	
  my $nodeset = $xp->find('/Survey/Page/Questions/Question');
  foreach my $node ($nodeset->get_nodelist) {
		my $varName = &trim($xp->find('@guid',$node)->string_value());
		my $question = &trim($xp->find('Text',$node)->string_value());
		
		if ($question =~ /^\s*<![CDATA[(.*)\s*]]>\s*$/) {
			$question = &trim($1);
		}
		
		my $answerset = $xp->find('Answers/Answer',$node);
		my $answerList = 'list';
		foreach my $ans ($answerset->get_nodelist) {
			my $value = &trim($xp->find('@value',$ans)->string_value());
			my $msg = &trim($xp->find('@text',$ans)->string_value());
			
			$answerList .= "|$value|$msg";
		}
 		
  	print OUT "\tq$varName\t\t1\tq\t\t$question\t$answerList\n";
  }	
  
  close(OUT);
}

sub trim {
	my $arg = shift;
	if ($arg =~ /^\s*(.*)\s*$/) {
		return $1;
	}
	else {
		return $arg;
	}
}