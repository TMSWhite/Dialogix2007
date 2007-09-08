
/**********************************************/
/* SAS Module __MAIN__-summary */
/**********************************************/

data WORK.AutoMEQ3; set cet3.AutoMEQ3;
	format
		UniqueID
		Finished
		StartDat
		StopDate
		Title
		Version
		d_who
		d_study
		d_age
		_ask
		d_sex
		d_eye
		d_country
		d_state
		_state_name
		d_los
		d_zip
		d_eth_ai
		d_eth_as
		d_eth_bl
		d_eth_his
		d_eth_hw
		d_eth_wh
		d_working_days
		d_sleep_workday
		d_sleep_nonworkday
		d_awake_workday
		d_awake_nonworkday
		q1
		q2
		d_abnl_sleep
		d_abnl_sleep_cont
		_ask2
		q3
		q4
		q5
		q6
		q7
		q8
		q9
		q10
		q11
		q12
		q13
		q14
		q15
		q16
		q17
		q18
		q19
		MEQ
		MEQstd
		DLMO
		DLMO_h
		DLMO_m
		DLMO_time0
		DLMO_time
		SL_ONSET
		SL_ONSET_h
		SL_ONSET_m
		SL_ONSET_time0
		SL_ONSET_time
		LIGHTS_ON
		LIGHTS_ON_h
		LIGHTS_ON_m
		LIGHTS_ON_time0
		LIGHTS_ON_time
		MEQ_type
		Feedback0
		Feedback_us
		;
run;

data WORK.AutoMEQ4; set cet4.Automeq4;
	format
		UniqueID
		Finished
		StartDat
		StopDate
		Title
		Version
		d_who
		d_study
		d_age
		_ask
		d_sex
		d_eye
		d_country
		d_state
		_state_name
		d_los
		d_zip
		d_eth_ai
		d_eth_as
		d_eth_bl
		d_eth_his
		d_eth_hw
		d_eth_wh
		d_working_days
		d_sleep_workday
		d_sleep_nonworkday
		d_awake_workday
		d_awake_nonworkday
		d_sleep_darkroom
		d_wake_withlight
		d_outsidelight_work
		d_outsidelight_nonwork
		d_height_units
		d_weight_units
		d_height_feet
		d_height_inches
		d_height_meters
		d_weight_pounds
		d_weight_kilograms
		d_height_m
		d_weight_kg
		d_BMI
		askPIDS
		CompletionDate
		B1
		B2
		B3
		B4
		B5
		B6
		B7
		Bscore
		C1A_Non
		C1A_Jan
		C1A_Feb
		C1A_Mar
		C1A_Apr
		C1A_May
		C1A_Jun
		C1A_Jul
		C1A_Aug
		C1A_Sep
		C1A_Oct
		C1A_Nov
		C1A_Dec
		C1A_Score
		C1A_Err
		C1A_Err2
		C1B_Non
		C1B_Jan
		C1B_Feb
		C1B_Mar
		C1B_Apr
		C1B_May
		C1B_Jun
		C1B_Jul
		C1B_Aug
		C1B_Sep
		C1B_Oct
		C1B_Nov
		C1B_Dec
		C1B_Score
		C1B_Err
		C1B_Err2
		C1_Jan_Err
		C1_Feb_Err
		C1_Mar_Err
		C1_Apr_Err
		C1_May_Err
		C1_Jun_Err
		C1_Jul_Err
		C1_Aug_Err
		C1_Sep_Err
		C1_Oct_Err
		C1_Nov_Err
		C1_Dec_Err
		C1_Err_Num
		C1_Err_List
		C1_Err
		C2A_Non
		C2A_Jan
		C2A_Feb
		C2A_Mar
		C2A_Apr
		C2A_May
		C2A_Jun
		C2A_Jul
		C2A_Aug
		C2A_Sep
		C2A_Oct
		C2A_Nov
		C2A_Dec
		C2A_Score
		C2A_Err
		C2A_Err2
		C2B_Non
		C2B_Jan
		C2B_Feb
		C2B_Mar
		C2B_Apr
		C2B_May
		C2B_Jun
		C2B_Jul
		C2B_Aug
		C2B_Sep
		C2B_Oct
		C2B_Nov
		C2B_Dec
		C2B_Score
		C2B_Err
		C2B_Err2
		C2_Jan_Err
		C2_Feb_Err
		C2_Mar_Err
		C2_Apr_Err
		C2_May_Err
		C2_Jun_Err
		C2_Jul_Err
		C2_Aug_Err
		C2_Sep_Err
		C2_Oct_Err
		C2_Nov_Err
		C2_Dec_Err
		C2_Err_Num
		C2_Err_List
		C2_Err
		C3A_Non
		C3A_Jan
		C3A_Feb
		C3A_Mar
		C3A_Apr
		C3A_May
		C3A_Jun
		C3A_Jul
		C3A_Aug
		C3A_Sep
		C3A_Oct
		C3A_Nov
		C3A_Dec
		C3A_Score
		C3A_Err
		C3A_Err2
		C3B_Non
		C3B_Jan
		C3B_Feb
		C3B_Mar
		C3B_Apr
		C3B_May
		C3B_Jun
		C3B_Jul
		C3B_Aug
		C3B_Sep
		C3B_Oct
		C3B_Nov
		C3B_Dec
		C3B_Score
		C3B_Err
		C3B_Err2
		C3_Jan_Err
		C3_Feb_Err
		C3_Mar_Err
		C3_Apr_Err
		C3_May_Err
		C3_Jun_Err
		C3_Jul_Err
		C3_Aug_Err
		C3_Sep_Err
		C3_Oct_Err
		C3_Nov_Err
		C3_Dec_Err
		C3_Err_Num
		C3_Err_List
		C3_Err
		C4A_Non
		C4A_Jan
		C4A_Feb
		C4A_Mar
		C4A_Apr
		C4A_May
		C4A_Jun
		C4A_Jul
		C4A_Aug
		C4A_Sep
		C4A_Oct
		C4A_Nov
		C4A_Dec
		C4A_Score
		C4A_Err
		C4A_Err2
		C4B_Non
		C4B_Jan
		C4B_Feb
		C4B_Mar
		C4B_Apr
		C4B_May
		C4B_Jun
		C4B_Jul
		C4B_Aug
		C4B_Sep
		C4B_Oct
		C4B_Nov
		C4B_Dec
		C4B_Score
		C4B_Err
		C4B_Err2
		C4_Jan_Err
		C4_Feb_Err
		C4_Mar_Err
		C4_Apr_Err
		C4_May_Err
		C4_Jun_Err
		C4_Jul_Err
		C4_Aug_Err
		C4_Sep_Err
		C4_Oct_Err
		C4_Nov_Err
		C4_Dec_Err
		C4_Err_Num
		C4_Err_List
		C4_Err
		C5A_Non
		C5A_Jan
		C5A_Feb
		C5A_Mar
		C5A_Apr
		C5A_May
		C5A_Jun
		C5A_Jul
		C5A_Aug
		C5A_Sep
		C5A_Oct
		C5A_Nov
		C5A_Dec
		C5A_Score
		C5A_Err
		C5A_Err2
		C5B_Non
		C5B_Jan
		C5B_Feb
		C5B_Mar
		C5B_Apr
		C5B_May
		C5B_Jun
		C5B_Jul
		C5B_Aug
		C5B_Sep
		C5B_Oct
		C5B_Nov
		C5B_Dec
		C5B_Score
		C5B_Err
		C5B_Err2
		C5_Jan_Err
		C5_Feb_Err
		C5_Mar_Err
		C5_Apr_Err
		C5_May_Err
		C5_Jun_Err
		C5_Jul_Err
		C5_Aug_Err
		C5_Sep_Err
		C5_Oct_Err
		C5_Nov_Err
		C5_Dec_Err
		C5_Err_Num
		C5_Err_List
		C5_Err
		C6A_Non
		C6A_Jan
		C6A_Feb
		C6A_Mar
		C6A_Apr
		C6A_May
		C6A_Jun
		C6A_Jul
		C6A_Aug
		C6A_Sep
		C6A_Oct
		C6A_Nov
		C6A_Dec
		C6A_Score
		C6A_Err
		C6A_Err2
		C6B_Non
		C6B_Jan
		C6B_Feb
		C6B_Mar
		C6B_Apr
		C6B_May
		C6B_Jun
		C6B_Jul
		C6B_Aug
		C6B_Sep
		C6B_Oct
		C6B_Nov
		C6B_Dec
		C6B_Score
		C6B_Err
		C6_Jan_Err
		C6_Feb_Err
		C6_Mar_Err
		C6_Apr_Err
		C6_May_Err
		C6_Jun_Err
		C6_Jul_Err
		C6_Aug_Err
		C6_Sep_Err
		C6_Oct_Err
		C6_Nov_Err
		C6_Dec_Err
		C6_Err_Num
		C6_Err_List
		C6_Err
		C6B_Err2
		CscrJanA
		CscrJanB
		CscrFebA
		CscrFebB
		CscrMarA
		CscrMarB
		CscrAprA
		CscrAprB
		CscrMayA
		CscrMayB
		CscrJunA
		CscrJunB
		CscrJulA
		CscrJulB
		CscrAugA
		CscrAugB
		CscrSepA
		CscrSepB
		CscrOctA
		CscrOctB
		CscrNovA
		CscrNovB
		CscrDecA
		CscrDecB
		CscrNonA
		CscrNonB
		D1
		D2
		D3
		D4
		D5
		D6
		D7
		D8
		D9
		Dscore
		q1
		q2
		d_abnl_sleep
		d_abnl_sleep_cont
		_ask2
		q3
		q4
		q5
		q6
		q7
		q8
		q9
		q10
		q11
		q12
		q13
		q14
		q15
		q16
		q17
		q18
		q19
		MEQ
		MEQstd
		DLMO
		DLMO_h
		DLMO_m
		DLMO_time0
		DLMO_time 
		SL_ONSET
		SL_ONSET_h
		SL_ONSET_m
		SL_ONSET_time0
		SL_ONSET_time
		LIGHTS_ON
		LIGHTS_ON_h
		LIGHTS_ON_m
		LIGHTS_ON_time0
		LIGHTS_ON_time
		MEQ_type
		Feedback0
		Feedback_us
		;
run;


data WORK.AutoMEQ5; set cet5.automeq5;
	format
		UniqueID
		Finished
		StartDat
		StopDate
		Title
		Version
		d_who
		d_study
		d_age
		_ask
		d_sex
		d_eye
		d_country
		d_state
		_state_name
		d_los
		d_zip
		d_eth_ai
		d_eth_as
		d_eth_bl
		d_eth_his
		d_eth_hw
		d_eth_wh
		d_working_days
		d_sleep_workday
		d_sleep_nonworkday
		d_awake_workday
		d_awake_nonworkday
		d_sleep_darkroom
		d_wake_withlight
		d_outsidelight_work
		d_outsidelight_nonwork
		d_height_units
		d_weight_units
		d_height_feet
		d_height_inches
		d_height_meters
		d_weight_pounds
		d_weight_kilograms
		d_height_m
		d_weight_kg
		d_BMI
		askPIDS
		CompletionDate
		A1
		A2
		A3
		A4
		A5
		A6
		A7
		A8
		A9
		Ascore
		B1
		B2
		B3
		B4
		B5
		B6
		B7
		Bscore
		C1A_Non
		C1A_Jan
		C1A_Feb
		C1A_Mar
		C1A_Apr
		C1A_May
		C1A_Jun
		C1A_Jul
		C1A_Aug
		C1A_Sep
		C1A_Oct
		C1A_Nov
		C1A_Dec
		C1A_Score
		C1A_Err
		C1A_Err2
		C1B_Non
		C1B_Jan
		C1B_Feb
		C1B_Mar
		C1B_Apr
		C1B_May
		C1B_Jun
		C1B_Jul
		C1B_Aug
		C1B_Sep
		C1B_Oct
		C1B_Nov
		C1B_Dec
		C1B_Score
		C1B_Err
		C1B_Err2
		C1_Jan_Err
		C1_Feb_Err
		C1_Mar_Err
		C1_Apr_Err
		C1_May_Err
		C1_Jun_Err
		C1_Jul_Err
		C1_Aug_Err
		C1_Sep_Err
		C1_Oct_Err
		C1_Nov_Err
		C1_Dec_Err
		C1_Err_Num
		C1_Err_List
		C1_Err
		C2A_Non
		C2A_Jan
		C2A_Feb
		C2A_Mar
		C2A_Apr
		C2A_May
		C2A_Jun
		C2A_Jul
		C2A_Aug
		C2A_Sep
		C2A_Oct
		C2A_Nov
		C2A_Dec
		C2A_Score
		C2A_Err
		C2A_Err2
		C2B_Non
		C2B_Jan
		C2B_Feb
		C2B_Mar
		C2B_Apr
		C2B_May
		C2B_Jun
		C2B_Jul
		C2B_Aug
		C2B_Sep
		C2B_Oct
		C2B_Nov
		C2B_Dec
		C2B_Score
		C2B_Err
		C2B_Err2
		C2_Jan_Err
		C2_Feb_Err
		C2_Mar_Err
		C2_Apr_Err
		C2_May_Err
		C2_Jun_Err
		C2_Jul_Err
		C2_Aug_Err
		C2_Sep_Err
		C2_Oct_Err
		C2_Nov_Err
		C2_Dec_Err
		C2_Err_Num
		C2_Err_List
		C2_Err
		C3A_Non
		C3A_Jan
		C3A_Feb
		C3A_Mar
		C3A_Apr
		C3A_May
		C3A_Jun
		C3A_Jul
		C3A_Aug
		C3A_Sep
		C3A_Oct
		C3A_Nov
		C3A_Dec
		C3A_Score
		C3A_Err
		C3A_Err2
		C3B_Non
		C3B_Jan
		C3B_Feb
		C3B_Mar
		C3B_Apr
		C3B_May
		C3B_Jun
		C3B_Jul
		C3B_Aug
		C3B_Sep
		C3B_Oct
		C3B_Nov
		C3B_Dec
		C3B_Score
		C3B_Err
		C3B_Err2
		C3_Jan_Err
		C3_Feb_Err
		C3_Mar_Err
		C3_Apr_Err
		C3_May_Err
		C3_Jun_Err
		C3_Jul_Err
		C3_Aug_Err
		C3_Sep_Err
		C3_Oct_Err
		C3_Nov_Err
		C3_Dec_Err
		C3_Err_Num
		C3_Err_List
		C3_Err
		C4A_Non
		C4A_Jan
		C4A_Feb
		C4A_Mar
		C4A_Apr
		C4A_May
		C4A_Jun
		C4A_Jul
		C4A_Aug
		C4A_Sep
		C4A_Oct
		C4A_Nov
		C4A_Dec
		C4A_Score
		C4A_Err
		C4A_Err2
		C4B_Non
		C4B_Jan
		C4B_Feb
		C4B_Mar
		C4B_Apr
		C4B_May
		C4B_Jun
		C4B_Jul
		C4B_Aug
		C4B_Sep
		C4B_Oct
		C4B_Nov
		C4B_Dec
		C4B_Score
		C4B_Err
		C4B_Err2
		C4_Jan_Err
		C4_Feb_Err
		C4_Mar_Err
		C4_Apr_Err
		C4_May_Err
		C4_Jun_Err
		C4_Jul_Err
		C4_Aug_Err
		C4_Sep_Err
		C4_Oct_Err
		C4_Nov_Err
		C4_Dec_Err
		C4_Err_Num
		C4_Err_List
		C4_Err
		C5A_Non
		C5A_Jan
		C5A_Feb
		C5A_Mar
		C5A_Apr
		C5A_May
		C5A_Jun
		C5A_Jul
		C5A_Aug
		C5A_Sep
		C5A_Oct
		C5A_Nov
		C5A_Dec
		C5A_Score
		C5A_Err
		C5A_Err2
		C5B_Non
		C5B_Jan
		C5B_Feb
		C5B_Mar
		C5B_Apr
		C5B_May
		C5B_Jun
		C5B_Jul
		C5B_Aug
		C5B_Sep
		C5B_Oct
		C5B_Nov
		C5B_Dec
		C5B_Score
		C5B_Err
		C5B_Err2
		C5_Jan_Err
		C5_Feb_Err
		C5_Mar_Err
		C5_Apr_Err
		C5_May_Err
		C5_Jun_Err
		C5_Jul_Err
		C5_Aug_Err
		C5_Sep_Err
		C5_Oct_Err
		C5_Nov_Err
		C5_Dec_Err
		C5_Err_Num
		C5_Err_List
		C5_Err
		C6A_Non
		C6A_Jan
		C6A_Feb
		C6A_Mar
		C6A_Apr
		C6A_May
		C6A_Jun
		C6A_Jul
		C6A_Aug
		C6A_Sep
		C6A_Oct
		C6A_Nov
		C6A_Dec
		C6A_Score
		C6A_Err
		C6A_Err2
		C6B_Non
		C6B_Jan
		C6B_Feb
		C6B_Mar
		C6B_Apr
		C6B_May
		C6B_Jun
		C6B_Jul
		C6B_Aug
		C6B_Sep
		C6B_Oct
		C6B_Nov
		C6B_Dec
		C6B_Score
		C6B_Err
		C6_Jan_Err
		C6_Feb_Err
		C6_Mar_Err
		C6_Apr_Err
		C6_May_Err
		C6_Jun_Err
		C6_Jul_Err
		C6_Aug_Err
		C6_Sep_Err
		C6_Oct_Err
		C6_Nov_Err
		C6_Dec_Err
		C6_Err_Num
		C6_Err_List
		C6_Err
		C6B_Err2
		CscrJanA
		CscrJanB
		CscrFebA
		CscrFebB
		CscrMarA
		CscrMarB
		CscrAprA
		CscrAprB
		CscrMayA
		CscrMayB
		CscrJunA
		CscrJunB
		CscrJulA
		CscrJulB
		CscrAugA
		CscrAugB
		CscrSepA
		CscrSepB
		CscrOctA
		CscrOctB
		CscrNovA
		CscrNovB
		CscrDecA
		CscrDecB
		CscrNonA
		CscrNonB
		D1
		D2
		D3
		D4
		D5
		D6
		D7
		D8
		D9
		Dscore
		MDcrit1
		MDcrit2
		MDcrit3
		MDcrit4
		MDcrit5
		MDcrit6
		q1
		q2
		d_abnl_sleep
		d_abnl_sleep_cont
		_ask2
		q3
		q4
		q5
		q6
		q7
		q8
		q9
		q10
		q11
		q12
		q13
		q14
		q15
		q16
		q17
		q18
		q19
		MEQ
		MEQstd
		DLMO
		DLMO_h
		DLMO_m
		DLMO_time0
		DLMO_time
		SL_ONSET
		SL_ONSET_h
		SL_ONSET_m
		SL_ONSET_time0
		SL_ONSET_time
		LIGHTS_ON
		LIGHTS_ON_h
		LIGHTS_ON_m
		LIGHTS_ON_time0
		LIGHTS_ON_time
		MEQ_type
		Feedback0
		Feedback_us
		;
run;

data cet7.summary; set automeq3 automeq4 automeq5; run;

%include "&cet7_lib\automeq_formats.sas";

