cd /home/tmw/backup/data
find . -type f -name "*jar" -print0 | xargs -0 --replace=% cp -p  % /home/tmw/backup/tomdata

cd /home/tmw/backup/dialogix8080/
find . -type f -name "*jar" -print0 | xargs -0 --replace=% cp -p  % /home/tmw/backup/tomdata

cd /home/tmw/backup/tomdata
mkdir completed
mkdir instruments
mkdir analysis
mkdir unjar
mkdir working
find . -type f -name "[0-9]*.jar" -print0 | xargs -0 --replace=% mv  % /home/tmw/backup/tomdata/completed 1> mv.out 2> mv.err
find . -type f -name "user[0-9]*.jar" -print0 | xargs -0 --replace=% mv  % /home/tmw/backup/tomdata/completed
find . -type f -name "_[0-9]*.jar" -print0 | xargs -0 --replace=% mv  % /home/tmw/backup/tomdata/completed
mv *.jar ./instruments


#find . -type f -print0 | xargs -0 --replace=% dos2unix % 

#./configure --prefix=/home/tomwhite/usr_local

# --- Can I incrementally copy any instrument?  Essentially move one at a time, run unjar.pl on it (with sched2sas), then move to next?  ---

cd /home/tmw/
find . -type f -name "*jar" -print0 | xargs -0 --replace=% ls -l --time-style=long-iso % > /home/tmw/data_jar.lst
find . -type f -name "*.dat" -print0 | xargs -0 --replace=% ls -l --time-style=long-iso % > /home/tmw/data_dat.lst

## Code to testing ruby from its test directory

find . -name "*.rb" -print | xargs --replace=% ruby %