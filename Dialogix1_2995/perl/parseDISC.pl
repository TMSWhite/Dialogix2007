# perl program to to parse DISC files 

use strict;

my @lines = (<>);
my @nodes;

foreach (@lines) {
	chomp;
	my @arg = split(/\t/);
	
	# concept varname varlabel relevance qoreval readback question answeroptions
	
	my $node = {
		qnum => $arg[0],
		mod => $arg[1],
		sect => $arg[2],
		varname => "$arg[1]_$arg[2]_$arg[0]",
		varlabel => "$arg[1].$arg[2].$arg[0]",
		relevance => 1,
		question => &fixExcelisms($arg[7]),
		readback => "?",
		answeroptions => &parseAnsOps(&fixExcelisms($arg[10]),&fixExcelisms($arg[13]),&fixExcelisms($arg[14])),
	};
	push @nodes, $node;
	
	print "\t$node->{varname}\t$node->{varlabel}\t$node->{relevance}\tq\t\t$node->{question}\t$node->{answeroptions}\n";
}

sub parseAnsOps {
	my ($answers, $_values, $_keys) = @_;
	
	my $result = "list";
	
	my @answers = split(/\~/,$answers);
	my @_values = split(/\~/,$_values);
	my @_keys = split(/\~/,$_keys);
	
	my $count = 0;
	if ($#answers-1 <= 0) {
		$result = "nothing";
	}
	else {
		for (0 .. ($#answers-1)) {
			my $value = $_values[$count];
			if ($value =~ /^\s*(\S.*?)\s*$/) { $value = $1; }
			my $answer = $answers[$count];
			if ($answer =~ /^\s*(\S.*?)\s*$/) { $answer = $1; }
			my $key = $_keys[$count];
			if ($key =~ /^\s*(\S.*?)\s*$/) { $key = $1; }
			
			$result .= "|$value|$answer";
			++$count;
		}
	}
	return $result;
}

# Excel randomly surrounds strings with quotes, and replaces internal double quotes with a pair of
# double quotes.  This function reverses that process, without replacing escaped double quotes.
sub fixExcelisms {
	my $arg = shift;

	if ($arg =~ /^"(.*?)"$/) {
		$arg = $1;
		$arg =~ s/(?<!\\)""/"/g;
	}
	
	# Fix bad HTML
	$arg =~ s/<r>/<b>/ig;
	$arg =~ s|</r>|</b>|ig;
	return $arg;
}