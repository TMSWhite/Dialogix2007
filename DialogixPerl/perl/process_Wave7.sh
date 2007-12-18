# Shell file to extract all wave7 data and process it - designed to be re-producable

cd ~/Wave7
rm -rf *

mkdir ~/Wave7/untar
tar xzf ~/Wave7_backup/Wave7_20070605.tgz

mkdir ~/Wave7/analysis
mkdir ~/Wave7/unjar
mkdir ~/Wave7/completed
mkdir ~/Wave7/instrument
mkdir ~/Wave7/working

# copy working files, starting with user-completed ones, then adding ones Jeffrey has partly completed
cp ~/Wave7/untar/Wave7/WEB-INF/working/* ~/Wave7/working
cp ~/Wave7/untar/Wave7/WEB-INF/archive/suspended/* ~/Wave7/working
cp -rp ~/Wave7/untar/Wave7/WEB-INF/schedules/* ~/Wave7/instrument
cp ~/Wave7/untar/Wave7/WEB-INF/completed/* ~/Wave7/completed

# may need to pre-compute instruments
cd ~/Wave7/perl
perl unjar.pl Wave7unix.conf unix > Wave7unix.log 2> Wave7unix.err