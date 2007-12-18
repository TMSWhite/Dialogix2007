#! /usr/bin/perl
#
# Archive new dialogix-related files (not including database)
# SCP them to a safe place

use strict;

my $datefmt = "%04d%02d%02d";
my @tm= localtime(time() - (60*60*24));
my $date = sprintf($datefmt,(1900+$tm[5]),(1+$tm[4]),$tm[3]);
my $touched = "/home/tmw/backup/last_incremental.txt";
my $val;

$val = system("touch -t ${date}0000 $touched");
$val = system("tar czvf /home/tmw/backup/dialogix$date.tgz `find /usr/local/dialogix -newer $touched -type f -print` 1>/tmp/incremental_backup.stdout 2>/tmp/incremental_backup.stderr");	
if ($val != 0) {
	print "incremental backup failed -- err code " . ($val/256) . "\n";
	return;
}
else {
	print "incremental backup succeeded\n";
}

$val = system("scp -2q /home/tmw/backup/dialogix$date.tgz tomwhite\@tomwhitemd.com:/home/tomwhite/dialogix_backup/");
if ($val != 0) {
	print "Unable to scp incremental backup to tomwhitemd.com -- err code " . ($val/256) . "\n";
}
else {
	print "scp succeeded\n";
}

# 1 2 * * * /home/tmw/backup/incremental_backup.pl