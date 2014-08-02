package com.tsweb.jdbc.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.tsweb.bean.BaseObject;

public class BaseDao {

	public static List<BaseObject> getIndexedObject() {
		List<BaseObject> indexObjs = new ArrayList<BaseObject>();
		try {
			Connection connection = Database.getConnection();
			Statement stmt = connection.createStatement();
			ResultSet resultSet = stmt.executeQuery(SQL.find_objs);
			while (resultSet.next()) {
				BaseObject obj = new BaseObject();
				obj.setId(resultSet.getInt("application_id"));
				obj.setName(resultSet.getString("name"));
				obj.setDescription(resultSet.getString("description"));
				indexObjs.add(obj);
			}
			connection.close();
			resultSet.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return indexObjs;
	}
}
