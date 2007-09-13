package org.dianexus.triceps.modules.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.apache.log4j.Logger;

public class MysqlMappingItemDAO implements MappingItemDAO{
  static Logger logger = Logger.getLogger(MysqlMappingItemDAO.class);

	private static final String SQL_GET_LAST_INSERT_ID = "SELECT LAST_INSERT_ID() from mapping_items";
	private static final String SQL_MAPPING_ITEM_NEW = "INSERT INTO mapping_items SET mapping_def_id= ? ,source_col = ?, source_col_name = ?," +
			"dest_col = ?, dest_col_name = ?, table_name =?, description = ? ";
	private static final String SQL_MAPPING_ITEM_DELETE = "DELETE FROM mapping_items WHERE id = ?";
	private static final String SQL_MAPPING_ITEM_UPDATE = "UPDATE mapping_items SET mapping_def_id= ? ,source_col = ?, source_col_name = ?," +
			"dest_col = ?, dest_col_name = ?, table_name=?, description = ? WHERE id = ?";
	private static final String SQL_MAPPING_ITEM_GET = "SELECT * FROM mapping_items WHERE id = ?";
	private static final String SQL_MAPPING_GET_INDEX = "SELECT id  FROM mapping_items WHERE mapping_def_id = ?";
	private static final String SQL_MAPPING_GET_TABLE_INDEX="SELECT id FROM mapping_items WHERE mapping_def_id = ? AND table_name =? ";
	
	private int id;
	private int mappingId;
	private String description;
	private int destinationColumn;
	private int sourceColumn;
	private String destinationColumnName;
	private String sourceColumnName;
	private String tableName;

	public boolean deleteMappingItem(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_MAPPING_ITEM_DELETE);
			ps.clearParameters();

			ps.setInt(1, id);
			ps.executeUpdate();
			rtn = true;
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return rtn;
	}
	
	public boolean readMappingItem(int id) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_MAPPING_ITEM_GET);
			ps.clearParameters();

			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				rtn = true;
				setMappingId(rs.getInt(2));
				setSourceColumn(rs.getInt(3));
				setSourceColumnName(rs.getString(4));
				setDestinationColumn(rs.getInt(5));
				setDestinationColumnName(rs.getString(6));
				setTableName(rs.getString(7));
				setDescription(rs.getString(8));
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return rtn;
	}
	
	public boolean updateMappingItem(int id) {
		
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_MAPPING_ITEM_UPDATE);
			ps.clearParameters();

			ps.setInt(1,getMappingId());
			ps.setInt(2,getSourceColumn());
			ps.setString(3,getSourceColumnName());
			ps.setInt(4,getDestinationColumn());
			ps.setString(5,getDestinationColumnName());
			ps.setString(6,getTableName());
			ps.setString(7,getDescription());
			ps.setInt(8,id);
			ps.executeUpdate();
			rtn = true;
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return rtn;
	}

	public boolean writeMappingItem() {

		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean rtn = false;
		try {
			ps = con.prepareStatement(SQL_MAPPING_ITEM_NEW);
			ps.clearParameters();

			ps.setInt(1,getMappingId());
			ps.setInt(2,getSourceColumn());
			ps.setString(3,getSourceColumnName());
			ps.setInt(4,getDestinationColumn());
			ps.setString(5,getDestinationColumnName());
			ps.setString(6,getTableName());
			ps.setString(7,getDescription());
			ps.executeUpdate();
			
			ps = con.prepareStatement(SQL_GET_LAST_INSERT_ID);
			rs = ps.executeQuery();
			if(rs.next()){
				setId(rs.getInt(1));
				rtn=true;
			}		
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return false;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if(rs != null){
					rs.close();																																																																																																																					
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return rtn;
	}
	
	public ArrayList getItemsIndex(int defId) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList itemList = new ArrayList();
		try {
			ps = con.prepareStatement(SQL_MAPPING_GET_INDEX);
			ps.clearParameters();

			ps.setInt(1, defId);
			rs = ps.executeQuery();
			while (rs.next()) {	
				itemList.add(new Integer(rs.getInt(1)));		
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return null;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		return itemList;
	}

	public ArrayList getTableItemsIndex(int defId, String table_name) {
		Connection con = DialogixMysqlDAOFactory.createConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList itemList = new ArrayList();
		try {
			ps = con.prepareStatement(SQL_MAPPING_GET_TABLE_INDEX);
			ps.clearParameters();

			ps.setInt(1, defId);
			ps.setString(2,table_name);
			rs = ps.executeQuery();
			while (rs.next()) {	
				itemList.add(new Integer(rs.getInt(1)));
			}
		} catch (Exception e) {
			logger.error(ps.toString(), e);
			return null;
		} finally {
			try {
				if (ps != null) {
					ps.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (Exception ee) {
				logger.error(ps.toString(), ee);
			}
		}
		// check itemList contents
		for(int i=0;i<itemList.size();i++){
			logger.debug("Contents of itemList before return are: item"+i+" is "+itemList.get(i));
		}
		return itemList;
	}
	
	public String getDescription() {
		return description;
	}

	public int getDestinationColumn() {
		return destinationColumn;
        }
	
	public String getDestinationColumnName() {
		return destinationColumnName;
	}

	public int getId() {
		return id;
	}

	public int getMappingId() {
		return mappingId;
	}

	public int getSourceColumn() {
		return sourceColumn;
	}

	public String getSourceColumnName() {
		return sourceColumnName;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public void setDestinationColumn(int destinationCol) {
		destinationColumn = destinationCol;
	}

	public void setDestinationColumnName(String destinationColName) {
		this.destinationColumnName = destinationColName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}

	public void setSourceColumn(int sourceCol) {
		this.sourceColumn = sourceCol;
	}

	public void setSourceColumnName(String sourColName) {
		sourceColumnName = sourColName;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName= tableName;
	}
}
