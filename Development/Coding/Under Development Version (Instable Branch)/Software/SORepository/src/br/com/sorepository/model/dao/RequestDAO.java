/**
 * 
 */
package br.com.sorepository.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.sorepository.model.pojo.SmartObject;
import br.com.sorepository.model.pojo.SmartObjectList;

/**
 * @author Ercilio Nascimento
 */
public class RequestDAO {

	private final String user = "root";
	private final String password = "anelise";
	private final String jdbc = "com.mysql.jdbc.Driver";
	private final String url = "jdbc:mysql://localhost:3306/sodb";
	private Connection conn;

	public RequestDAO() throws ClassNotFoundException, SQLException {
		Class.forName(this.jdbc);
		conn = DriverManager.getConnection(this.url, this.user, this.password);
	}

	public void closeConnection() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean validateUser(String user, String password) throws SQLException {
		boolean validate = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT `U`.`USERNAME` FROM `SODB`.`USER` U ");
		sql.append("WHERE `U`.`USERNAME` = ? ");
		sql.append("AND `U`.`PASSWORD` = ? ");
		PreparedStatement prst = conn.prepareStatement(sql.toString());
		prst.setString(1, user);
		prst.setString(2, password);
		ResultSet rs = prst.executeQuery();

		if (rs.next()) {
			validate = true;
		}

		System.out.println("Validating user...");
		log("SQL - VALIDATE USER: ", sql, new Object[] { user, password });

		return validate;
	}

	private void log(String info, StringBuilder sql, Object... params) {
		for (int i = 0; i < params.length; i++) {
			int index = sql.indexOf("?");
			if (params[i] instanceof String) {
				sql = sql.replace(index, ++index, "'" + String.valueOf(params[i]) + "'");
			} else {
				sql = sql.replace(index, ++index, String.valueOf(params[i]));
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		System.out.println(sdf.format(Calendar.getInstance().getTime()) + " " + info + " " + sql.toString());
	}

	public SmartObjectList getSOList(String user) throws SQLException {
		SmartObjectList solist = new SmartObjectList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT  `SO`.`IDSMARTOBJECT`, `SO`.`IDSOMODBUS`, `SO`.`SERVERURL`, ");
		sql.append("`CA`.`NAME`, `SER`.`IDSERVICEMODBUS`, `SER`.`IDREGISTERMODBUS`, `SER`.`NAME`, `P`.`NAME`, `P`.`TYPE`, ");
		sql.append("`P`.`MINVALUE`, `P`.`MAXVALUE`, `P`.`OPTIONS` ");
		sql.append("FROM `SODB`.`USER` U ");
		sql.append("JOIN `SODB`.`SOUSERJOIN` SOUJ ON `SOUJ`.`IDUSER` = `U`.`IDUSER` ");
		sql.append("JOIN `SODB`.`SMARTOBJECT` SO ON `SO`.`IDSMARTOBJECT` = `SOUJ`.`IDSMARTOBJECT` ");
		sql.append("JOIN `SODB`.`CATEGORY` CA ON `CA`.`IDCATEGORY` = `SO`.`IDCATEGORY` ");
		sql.append("JOIN `SODB`.`SERVICE` SER ON `SER`.`IDCATEGORY` = `CA`.`IDCATEGORY` ");
		sql.append("LEFT OUTER JOIN `SODB`.`SERVICEPARAMETER` SERPAR ON `SERPAR`.`IDSERVICE` = `SER`.`IDSERVICE` ");
		sql.append("LEFT OUTER JOIN `SODB`.`PARAMETER` P ON `P`.`IDPARAMETER` = `SERPAR`.`IDPARAMETER` ");
		sql.append("WHERE `U`.`USERNAME` = ? ");
		sql.append("ORDER BY `SO`.`IDSMARTOBJECT`, ");
		sql.append("`SER`.`IDSERVICE`, ");
		sql.append("`P`.`IDPARAMETER` ");
		PreparedStatement prst = conn.prepareStatement(sql.toString());
		prst.setString(1, user);
		ResultSet rs = prst.executeQuery();

		while (rs.next()) {
			solist.getList().add(getConcatValues(rs));
		}

		System.out.println("Getting smart object list...");
		log("SQL - GET SO LIST: ", sql, new Object[] { user });

		return solist;

	}

	public String refreshSO(String command) throws SQLException {
		StringBuilder sql = new StringBuilder();
		SmartObject so = SmartObject.splitCommandToSmartObject(command);
		if (this.existsSO(so.getSoName())) {
			// TODO atualizar SO
		}
		return "objeto inteligente refrescado com sucesso!";
	}

	private boolean existsSO(String name) throws SQLException {
		boolean exists = false;
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT 1 FROM `SO` WHERE `SO`.`NAME`= ?");
		PreparedStatement prst = conn.prepareStatement(sql.toString());
		prst.setString(1, name);
		ResultSet rs = prst.executeQuery();

		if (rs.next()) {
			exists = true;
		}

		log("SQL - EXISTS SO: ", sql, new Object[] { name });

		return exists;
	}

	private String getConcatValues(ResultSet rs) throws SQLException {
		String s = "";
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			s += rs.getString(i) + ",";
		}
		s = s.substring(0, s.length() - 1);

		return s;
	}
}
