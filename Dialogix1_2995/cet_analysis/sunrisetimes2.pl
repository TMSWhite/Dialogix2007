# untransform sunrise times

use strict;

my @lines = (<>);
my $cols_line = shift(@lines);
chomp($cols_line);
if ($cols_line =~ /^lat.	long.(.*)$/) {
	$cols_line = $1;
}
my @cols = split(/\t/,$cols_line);
unshift(@cols,0);

foreach (@lines) {
	chomp;
	my @args = split(/\t/);
	foreach my $n (1 .. $#cols) {
		print "$args[0]\t$cols[$n]\t$args[$n]\n";
	}
}
