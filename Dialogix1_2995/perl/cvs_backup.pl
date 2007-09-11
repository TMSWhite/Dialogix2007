#! /usr/bin/perl
#
# Daily full-backups of Dialogix CVS
# SCP them to a safe place

use strict;

my $datefmt = "%04d%02d%02d";
my @tm= localtime(time() - (60*60*24));
my $date = sprintf($datefmt,(1900+$tm[5]),(1+$tm[4]),$tm[3]);
my $val;

chdir "/other/projects";

$val = system("tar czvf /home/tmw/cvs_backup/cvs_Dialogix2007_$date.tgz Dialogix2007/ 1> /home/tmw/cvs_backup/cvs_Dialogix2007_backup.stdout 2> /home/tmw/cvs_backup/cvs_Dialogix2007_backup.stderr");
if ($val != 0) {
        print "cvs_Dialogix2007 backup failed -- err code " . ($val/256) . "\n";
        return;
}
else {
        print "cvs_Dialogix2007 backup succeeded\n";
}

system("chown tmw:cvs /home/tmw/cvs_backup/cvs_Dialogix2007_$date.tgz");

$val = system("scp -2q /home/tmw/cvs_backup/cvs_Dialogix2007_$date.* tomwhite\@tomwhitemd.com:/home/tomwhite/cvs_backup/");
if ($val != 0) {
        print "Unable to scp cvs_Dialogix2007 backup to tomwhitemd.com -- err code " . ($val/256) . "\n";
}
else {
        print "scp succeeded\n";
}

# 1 3 * * * /home/tmw/cvs_backup.pl > home/tmw/cvs_backup/crontab_cvs_backup.log 2> /home/tmw/cvs_backup/crontab_cvs_backup.err
