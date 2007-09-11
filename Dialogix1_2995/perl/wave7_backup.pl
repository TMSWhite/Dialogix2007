#! /usr/bin/perl
#
# Daily full-backups of Wave7, including database
# SCP them to a safe place

use strict;

my $datefmt = "%04d%02d%02d";
my @tm= localtime(time() - (60*60*24));
my $date = sprintf($datefmt,(1900+$tm[5]),(1+$tm[4]),$tm[3]);
my $val;

chdir "/usr/local/dialogix/webapps";

$val = system("tar czvf /home/tmw/backup/Wave7_$date.tgz Wave7/ 1> /home/tmw/backup/Wave7_backup.stdout 2> /home/tmw/backup/Wave7_backup.stderr");
if ($val != 0) {
        print "Wave7 backup failed -- err code " . ($val/256) . "\n";
        return;
}
else {
        print "Wave7 backup succeeded\n";
}

chdir "/opt/lampp/var/mysql/";

$val = system("tar czvf /home/tmw/backup/Wave7_$date.db.tgz dialogix2994/wave6users* 1>> /home/tmw/backup/Wave7_backup.stdout 2>> /home/tmw/backup/Wave7_backup.stderr");
if ($val != 0) {
        print "Wave7 db backup failed -- err code " . ($val/256) . "\n";
        return;
}
else {
        print "Wave7 db backup succeeded\n";
}

system("chown tmw:cvs Wave7_$date.*");

$val = system("scp -2q /home/tmw/backup/Wave7_$date.* tomwhite\@tomwhitemd.com:/home/tomwhite/Wave7_backup/");
if ($val != 0) {
        print "Unable to scp incremental backup to tomwhitemd.com -- err code " . ($val/256) . "\n";
}
else {
        print "scp succeeded\n";
}

