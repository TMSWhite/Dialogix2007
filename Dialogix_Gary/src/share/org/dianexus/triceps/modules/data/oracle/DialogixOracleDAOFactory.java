package org.dianexus.triceps.modules.data.oracle;

import java.sql.Connection;
import oracle.jdbc.pool.OracleDataSource;
import org.dianexus.triceps.modules.data.DialogixDAOFactory;
import org.dianexus.triceps.modules.data.HL7OBX3DBO;
import org.dianexus.triceps.modules.data.HL7OBX5DBO;
import org.dianexus.triceps.modules.data.InstanceDataTable;
import org.dianexus.triceps.modules.data.InstrumentContentsDAO;
import org.dianexus.triceps.modules.data.InstrumentDAO;
import org.dianexus.triceps.modules.data.InstrumentHeadersDAO;
import org.dianexus.triceps.modules.data.InstrumentMetaDAO;
import org.dianexus.triceps.modules.data.InstrumentSessionDAO;
import org.dianexus.triceps.modules.data.InstrumentSessionDataDAO;
import org.dianexus.triceps.modules.data.InstrumentTranslationsDAO;
import org.dianexus.triceps.modules.data.InstrumentVersionDAO;
import org.dianexus.triceps.modules.data.MappingDAO;
import org.dianexus.triceps.modules.data.MappingItemDAO;
import org.dianexus.triceps.modules.data.PageHitEventsDAO;
import org.dianexus.triceps.modules.data.PageHitsDAO;
import org.dianexus.triceps.modules.data.RawDataDAO;
import org.dianexus.triceps.modules.data.ReportQueryDAO;
import org.dianexus.triceps.modules.data.SandBoxDAO;
import org.dianexus.triceps.modules.data.SandBoxItemDAO;
import org.dianexus.triceps.modules.data.SandBoxUserDAO;
import org.dianexus.triceps.modules.data.SessionDataDAO;
import org.dianexus.triceps.modules.data.UserDAO;
import org.dianexus.triceps.modules.data.UserPermissionDAO;
import org.dianexus.triceps.modules.data.UserSessionDAO;
import org.dianexus.triceps.modules.data.InstrumentInfoDAO;

public class DialogixOracleDAOFactory extends DialogixDAOFactory {
	public static Connection createConnection() {
		Connection conn = null;
		try{
			
			OracleDataSource ods = new OracleDataSource();
			String URL = "jdbc:oracle:thin:@//omhex1:1521/alaya.omh.state.ny.us";
			ods.setURL(URL);
			ods.setUser("istcgxl");
			ods.setPassword("istcgxl1");
			conn = ods.getConnection();
			
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	public InstanceDataTable getInstanceDataTable() {
		
		return new OracleInstanceDataTable();
	}

	public InstrumentContentsDAO getInstrumentContentsDAO() {
		
		return new OracleInstrumentContentsDAO();
	}

	public InstrumentDAO getInstrumentDAO() {
		
		return new OracleInstrumentDAO();
	}

	public InstrumentHeadersDAO getInstrumentHeadersDAO() {
		
		return new OracleInstrumentHeadersDAO();
	}	

	public InstrumentMetaDAO getInstrumentMetaDAO() {
		
		return new OracleInstrumentMetaDAO();
	}

	public InstrumentSessionDAO getInstrumentSessionDAO() {
		
		return new OracleInstrumentSessionDAO();
	}

	public InstrumentSessionDataDAO getInstrumentSessionDataDAO() {
		return new OracleInstrumentSessionDataDAO();
	}

	public InstrumentTranslationsDAO getInstrumentTranslationsDAO() {
		return new OracleInstrumentTranslationsDAO();
	}

	public InstrumentVersionDAO getInstrumentVersionDAO() {
		return new OracleInstrumentVersionDAO();
	}

	public PageHitEventsDAO getPageHitEventsDAO() {
		return new OraclePageHitEventsDAO();
	}

	public PageHitsDAO getPageHitsDAO() {
		return new OraclePageHitsDAO();
	}

	public RawDataDAO getRawDataDAO() {
		return new OracleRawDataDAO();
	}

	public SessionDataDAO getSessionDataDAO() {
		return new OracleSessionDataDAO();
	}

	public UserSessionDAO getUserSessionDAO() {
		return new OracleUserSessionDAO();
		
	}
	public MappingDAO getMappingDAO() {
		return new OracleMappingDAO();
	}
	public MappingItemDAO getMappingItemDAO() {
		return new OracleMappingItemDAO();
	}
	public UserDAO getUserDAO() {
		return new OracleUserDAO();
	}
	public HL7OBX3DBO getHL7OBX3DBO() {
		return new OracleHL7OBX3DBO();
	}
	public HL7OBX5DBO getHL7OBX5DBO() {
		return new OracleHL7OBX5DBO();
	}
	public UserPermissionDAO getUserPermissionDAO() {
		return new OracleUserPermissionDAO();
	}
	public SandBoxDAO getSandBoxDAO() {
		return new OracleSandBoxDAO();
	}
	public SandBoxItemDAO getSandBoxItemDAO() {
		return new OracleSandBoxItemDAO();
	}
	public SandBoxUserDAO getSandBoxUserDAO() {
		return new OracleSandBoxUserDAO();
	}
	public ReportQueryDAO getReportQueryDAO() {
		return new OracleReportQueryDAO();
	}
	public InstrumentInfoDAO getInstrumentInfoDAO() {
		return new OracleInstrumentInfoDAO();
	}
	
}
