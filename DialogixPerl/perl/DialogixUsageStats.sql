/* SQL for MySql to measure current Dialogix utilization */

drop table if exists  test.DialogixUsageUniqueUsers;
create table test.DialogixUsageUniqueUsers as
select distinct 
	instrumentName as instrument,
	year(timestamp) as year, 
	month(timestamp) as month, 
	dayofmonth(timestamp) as day, 
	dayofyear(timestamp) as dayofyear, 
	floor(timestamp / 1000000) as date, 
	sessionID, 
	max(currentStep) as lastStep,
	count(*) as numSteps
from dialogix2994.pageHits
group by sessionID
order by instrument, year, month, day;

drop table if exists  test.DialogixUsageStats;
create table test.DialogixUsageStats as
	select instrument, 
		year,
		month,
		count(sessionID) as Started,
		sum(if(numSteps>10,1,0)) as ge11,
		sum(if(numSteps=1,1,0)) as s1,
		sum(if(numSteps=2,1,0)) as s2,
		sum(if(numSteps=3,1,0)) as s3,
		sum(if(numSteps=4,1,0)) as s4,
		sum(if(numSteps=5,1,0)) as s5,
		sum(if(numSteps=6,1,0)) as s6,
		sum(if(numSteps=7,1,0)) as s7,
		sum(if(numSteps=8,1,0)) as s8,
		sum(if(numSteps=9,1,0)) as s9,
		sum(if(numSteps=10,1,0)) as s10
	from test.DialogixUsageUniqueUsers
	group by instrument, year, month
	order by instrument, year, month;
	
select * from test.DialogixUsageStats limit 0,120;