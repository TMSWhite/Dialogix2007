
%let pi = 3.14159265358979323;

%macro radToDeg(angleRad);
	(180.0 * &angleRad / &pi)
%mend radToDeg;

%macro degToRad(angleDeg);
	(&pi * &angleDeg / 180.0)
%mend degToRad; 
	
/***********************************************************************/
/* Name:    calcTimeJulianCent							*/
/* Purpose: convert Julian Day to centuries since J2000.0.			*/
/* Arguments:										*/
/*   jd : the Julian Day to convert						*/
/* Return value:										*/
/*   the T value corresponding to the Julian Day				*/
/***********************************************************************/

%macro calcTimeJulianCent(jd);
	(&jd - 2451545.0)/36525.0
%mend calcTimeJulianCent;

/***********************************************************************/
/* Name:    calcJDFromJulianCent							*/
/* Purpose: convert centuries since J2000.0 to Julian Day.			*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   the Julian Day corresponding to the t value				*/
/***********************************************************************/

%macro calcJDFromJulianCent(t);
	&t * 36525.0 + 2451545.0
%mend calcJDFromJulianCent;


/***********************************************************************/
/* Name:    calGeomMeanLongSun							*/
/* Purpose: calculate the Geometric Mean Longitude of the Sun		*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   the Geometric Mean Longitude of the Sun in degrees			*/
/***********************************************************************/

%macro calcGeomMeanLongSun(t);
	L0 = 280.46646 + &t * (36000.76983 + 0.0003032 * &t);
	do while(L0 > 360.0);
		L0 = L0 - 360.0;
	end;
	do while(L0 < 0.0);
		L0 = L0 + 360.0;
	end;
%mend calcGeomMeanLongSun;

/***********************************************************************/
/* Name:    calGeomAnomalySun							*/
/* Purpose: calculate the Geometric Mean Anomaly of the Sun		*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   the Geometric Mean Anomaly of the Sun in degrees			*/
/***********************************************************************/

%macro calcGeomMeanAnomalySun(t);
		357.52911 + &t * (35999.05029 - 0.0001537 * &t)
%mend calcGeomMeanAnomalySun;

/***********************************************************************/
/* Name:    calcEccentricityEarthOrbit						*/
/* Purpose: calculate the eccentricity of earth's orbit			*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   the unitless eccentricity							*/
/***********************************************************************/

%macro calcEccentricityEarthOrbit(t);
		0.016708634 - &t * (0.000042037 + 0.0000001267 * &t)
%mend calcEccentricityEarthOrbit;

/***********************************************************************/
/* Name:    calcSunEqOfCenter							*/
/* Purpose: calculate the equation of center for the sun			*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   in degrees										*/
/***********************************************************************/

%macro calcSunEqOfCenter(t);
	m = %calcGeomMeanAnomalySun(&t);

	mrad = %degToRad(m);
	sinm = sin(mrad);
	sin2m = sin(mrad+mrad);
	sin3m = sin(mrad+mrad+mrad);

	C = sinm * (1.914602 - &t * (0.004817 + 0.000014 * &t)) + sin2m * (0.019993 - 0.000101 * &t) + sin3m * 0.000289;
%mend calcSunEqOfCenter;

/***********************************************************************/
/* Name:    calcSunTrueLong								*/
/* Purpose: calculate the true longitude of the sun				*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   sun's true longitude in degrees						*/
/***********************************************************************/

%macro calcSunTrueLong(t);
	%calcGeomMeanLongSun(&t);	/* => L0 */
	%calcSunEqOfCenter(&t); /* => C */

	O = l0 + c;
%mend calcSunTrueLong;

/***********************************************************************/
/* Name:    calcSunTrueAnomaly							*/
/* Purpose: calculate the true anamoly of the sun				*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   sun's true anamoly in degrees							*/
/***********************************************************************/

%macro calcSunTrueAnomaly(t);
	m = %calcGeomMeanAnomalySun(&t);
	%calcSunEqOfCenter(&t);	/* => C */

	v = m + c;
%mend calcSunTrueAnomaly;

/***********************************************************************/
/* Name:    calcSunRadVector								*/
/* Purpose: calculate the distance to the sun in AU				*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   sun radius vector in AUs							*/
/***********************************************************************/

%macro calcSunRadVector(t);
	%calcSunTrueAnomaly(&t);	/* => V */
	e = %calcEccentricityEarthOrbit(&t);

	R = (1.000001018 * (1 - e * e)) / (1 + e * cos(%degToRad(v)));
%mend calcSunRadVector;

/***********************************************************************/
/* Name:    calcSunApparentLong							*/
/* Purpose: calculate the apparent longitude of the sun			*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   sun's apparent longitude in degrees						*/
/***********************************************************************/

%macro calcSunApparentLong(t);
	%calcSunTrueLong(&t);	/* creates o */

	omega = 125.04 - 1934.136 * &t;
	lambda = o - 0.00569 - 0.00478 * sin(%degToRad(omega));
%mend calcSunApparentLong;

/***********************************************************************/
/* Name:    calcMeanObliquityOfEcliptic						*/
/* Purpose: calculate the mean obliquity of the ecliptic			*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   mean obliquity in degrees							*/
/***********************************************************************/

%macro calcMeanObliquityOfEcliptic(t);
	seconds = 21.448 - &t*(46.8150 + &t*(0.00059 - &t*(0.001813)));
	e0 = 23.0 + (26.0 + (seconds/60.0))/60.0;
%mend calcMeanObliquityOfEcliptic;

/***********************************************************************/
/* Name:    calcObliquityCorrection						*/
/* Purpose: calculate the corrected obliquity of the ecliptic		*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   corrected obliquity in degrees						*/
/***********************************************************************/

%macro calcObliquityCorrection(t);
	%calcMeanObliquityOfEcliptic(&t);	/* creates e0 */

	omega = 125.04 - 1934.136 * &t;
	epsilon = e0 + 0.00256 * cos(%degToRad(omega));
%mend calcObliquityCorrection;

/***********************************************************************/
/* Name:    calcSunRtAscension							*/
/* Purpose: calculate the right ascension of the sun				*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   sun's right ascension in degrees						*/
/***********************************************************************/

%macro calcSunRtAscension(t);
	%calcObliquityCorrection(&t);	/* creates e */
	%calcSunApparentLong(&t);	/* creates lambda */

	tananum = (cos(%degToRad(e)) * sin(%degToRad(lambda)));
	tanadenom = (cos(%degToRad(lambda)));
	atanadenom = atan2(tananum, tanadenom);
	alpha = %radToDeg(atanadenom);
%mend calcSunRtAscension;

/***********************************************************************/
/* Name:    calcSunDeclination							*/
/* Purpose: calculate the declination of the sun				*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   sun's declination in degrees							*/
/***********************************************************************/

%macro calcSunDeclination(t);
	%calcObliquityCorrection(&t);	/* creates e */
	%calcSunApparentLong(&t); /* creates lambda */

	sint = sin(%degToRad(e)) * sin(%degToRad(lambda));
	asinint = arsin(sint);
	theta = %radToDeg(asinint);
%mend calcSunDeclination;

/***********************************************************************/
/* Name:    calcEquationOfTime							*/
/* Purpose: calculate the difference between true solar time and mean	*/
/*		solar time									*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/* Return value:										*/
/*   equation of time in minutes of time						*/
/***********************************************************************/

%macro calcEquationOfTime(t);
	%calcObliquityCorrection(&t); /* => epsilon */
	%calcGeomMeanLongSun(&t);	/* => L0 */
	e = %calcEccentricityEarthOrbit(&t);
	m = %calcGeomMeanAnomalySun(&t);

	y = tan(%degToRad(epsilon)/2.0);
	y = y * y;

	sin2l0 = sin(2.0 * %degToRad(l0));
	sinm   = sin(%degToRad(m));
	cos2l0 = cos(2.0 * %degToRad(l0));
	sin4l0 = sin(4.0 * %degToRad(l0));
	sin2m  = sin(2.0 * %degToRad(m));

	Etime = y * sin2l0 - 2.0 * e * sinm + 4.0 * e * y * sinm * cos2l0
			- 0.5 * y * y * sin4l0 - 1.25 * e * e * sin2m;
			
	eqtime = %radToDeg(Etime)*4.0;
%mend calcEquationOfTime;

/***********************************************************************/
/* Name:    calcHourAngleSunrise							*/
/* Purpose: calculate the hour angle of the sun at sunrise for the	*/
/*			latitude								*/
/* Arguments:										*/
/*   lat : latitude of observer in degrees					*/
/*	solarDec : declination angle of sun in degrees				*/
/* Return value:										*/
/*   hour angle of sunrise in radians						*/
/***********************************************************************/

%macro calcHourAngleSunrise(lat, solarDec);
	latRad = %degToRad(&lat);
	sdRad  = %degToRad(&solarDec);

	HAarg = (cos(%degToRad(90.833))/(cos(latRad)*cos(sdRad))-tan(latRad) * tan(sdRad));

	hourAngle = (arcos(cos(%degToRad(90.833))/(cos(latRad)*cos(sdRad))-tan(latRad) * tan(sdRad)));
%mend calcHourAngleSunrise;


/***********************************************************************/
/* Name:    calcSolNoonUTC								*/
/* Purpose: calculate the Universal Coordinated Time (UTC) of solar	*/
/*		noon for the given day at the given location on earth		*/
/* Arguments:										*/
/*   t : number of Julian centuries since J2000.0				*/
/*   longitude : longitude of observer in degrees				*/
/* Return value:										*/
/*   time in minutes from zero Z							*/
/***********************************************************************/

%macro calcSolNoonUTC(t, longitude);
	tnoon_arg = %calcJDFromJulianCent(&t) + &longitude/360.0;
	tnoon = %calcTimeJulianCent(tnoon_arg);
	%calcEquationOfTime(tnoon);	/* => eqTime */
	solNoonUTC = 720 + (&longitude * 4) - eqTime;

	newt_arg = %calcJDFromJulianCent(&t) -0.5 + solNoonUTC/1440.0;
	newt = %calcTimeJulianCent(newt_arg); 

	%calcEquationOfTime(newt); /* => eqTime */
	solNoonUTC = 720 + (&longitude * 4) - eqTime;
	
	noonmin = solNoonUTC;
%mend calcSolNoonUTC;

/***********************************************************************/
/* Name:    calcSunriseUTC								*/
/* Purpose: calculate the Universal Coordinated Time (UTC) of sunrise	*/
/*			for the given day at the given location on earth	*/
/* Arguments:										*/
/*   JD  : julian day									*/
/*   latitude : latitude of observer in degrees				*/
/*   longitude : longitude of observer in degrees				*/
/* Return value:										*/
/*   time in minutes from zero Z							*/
/***********************************************************************/

%macro calcSunriseUTC(JD, latitude, longitude);
	t = %calcTimeJulianCent(&JD);

	%calcSolNoonUTC(t, &longitude);	/* => noonmin */
	tnoon_arg = &JD+noonmin/1440.0;
	tnoon = %calcTimeJulianCent (tnoon_arg);

	%calcEquationOfTime(tnoon); /* => eqTime */
	%calcSunDeclination(tnoon);	/* => theta */
	solarDec = theta;
	%calcHourAngleSunrise(&latitude, solarDec);	/* => hourAngle */

	delta = &longitude - %radToDeg(hourAngle);
	timeDiff = 4 * delta;	
	timeUTC = 720 + timeDiff - eqTime;	

	newt_arg = %calcJDFromJulianCent(t) + timeUTC/1440.0;
	newt = %calcTimeJulianCent(newt_arg); 
	%calcEquationOfTime(newt); /* => eqTime */
	%calcSunDeclination(newt);	/* => theta */
	solarDec = theta;
	%calcHourAngleSunrise(&latitude, solarDec);	/* => hourAngle */
	delta = &longitude - %radToDeg(hourAngle);
	timeDiff = 4 * delta;
	timeUTC = 720 + timeDiff - eqTime; /* This is the desired result - in minutes */
	
	timeUTC_hours = floor(timeUTC / 60);
	timeUTC_min = floor(timeUTC - (timeUTC_hours * 60));
	timeUTC_sec = (timeUTC - timeUTC_hours * 60 - timeUTC_min) * 60;

%mend calcSunriseUTC;

data test;
	date = '13Aug2005'd;
	jd = juldate7(date);
	lat = 40 + 43/60;
	long = 74 + 1/60;
	%calcSunriseUTC(jd,lat,long);
run;