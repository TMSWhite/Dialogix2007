

<jsp:useBean id="tricepsEngine" scope="session" class="org.dianexus.triceps.TricepsEngine"/>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%
    if (session.isNew()) {
        if (request.getMethod().equals("POST")) {
            org.dianexus.triceps.Triceps triceps = tricepsEngine.getTriceps();
            if (triceps != null) {
                org.dialogix.timing.DialogixTimingCalculator dtc = triceps.getDtc();
                if (dtc != null) {
                    dtc.setStatusMsg("EXPIRED SESSION");
                }
            }              
        }
        java.util.HashMap<String,String> initParams = new java.util.HashMap<String,String>();
        java.util.Enumeration<String> keys =  config.getInitParameterNames();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            initParams.put(key, config.getInitParameter(key));
        }
        tricepsEngine.init(initParams);
        org.dianexus.triceps.Triceps triceps = tricepsEngine.getTriceps();
        if (triceps != null) {
            org.dialogix.timing.DialogixTimingCalculator dtc = triceps.getDtc();
            if (dtc != null) {
                dtc.setStatusMsg("OK");
            }
        }        
    }
    
    java.util.HashMap<String,String> requestParameters = new java.util.HashMap<String,String>();
    java.util.Enumeration<String> params = request.getParameterNames();
    while (params.hasMoreElements()) {
        String key = params.nextElement();
        requestParameters.put(key, request.getParameter(key));
    }    
    
    org.dianexus.triceps.Triceps triceps = tricepsEngine.getTriceps();
    if (triceps != null) {
        org.dialogix.timing.DialogixTimingCalculator dtc = triceps.getDtc();
        if (dtc != null) {
            dtc.setStatusMsg("OK");
        }
    }
                   
    tricepsEngine.doPost(requestParameters, 
        response.encodeURL(request.getRequestURL().toString()), 
        new java.io.PrintWriter(out), 
        null,
        ((request == null) ? null : request.getRemoteAddr()),
        request.isSecure(),
        request.getHeader("User-Agent"),
        null);
    
    if (tricepsEngine.isFinished()) {
        triceps = tricepsEngine.getTriceps();
        if (triceps != null) {
            org.dialogix.timing.DialogixTimingCalculator dtc = triceps.getDtc();
            if (dtc != null) {
                dtc.setStatusMsg("FINISHED");
            }
        }             
    }
%>
