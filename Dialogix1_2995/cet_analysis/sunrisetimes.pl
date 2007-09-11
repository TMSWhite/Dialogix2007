# untransform sunrise times

use strict;

my @lines = (<>);
foreach (@lines) {
	chomp;
	my @args = split(/\t/);
	my $long = 59;
	foreach my $n (1 .. $#args) {
		++$long;
		print "$args[0]\t$long\t$args[$n]\n";
	}
}
