#! /bin/bash

cp -rp /usr/local/dialogix/webapps/Wave7/WEB-INF/completed/*.jar /usr/local/dialogix/webapps/Wave7/WEB-INF/data/completed
cp -rp /usr/local/dialogix/webapps/Wave7/WEB-INF/working/*.dat* /usr/local/dialogix/webapps/Wave7/WEB-INF/data/working
cd /home/tmw/cvs/Dialogix/perl

#perl unjar.pl wave7.conf unix