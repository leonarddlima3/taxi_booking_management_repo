package com.fuber.taxi.config;

import java.sql.Types;

import org.hibernate.dialect.Dialect;
import org.hibernate.dialect.identity.IdentityColumnSupport;

public class SQLiteDialect extends Dialect {
	
	public SQLiteDialect() {
		registerColumnType(Types.BIT, "integer");
		registerColumnType(Types.TINYINT, "tinyint");
		registerColumnType(Types.SMALLINT, "smallint");
		registerColumnType(Types.INTEGER, "integer");
		
	}

	@Override
	public IdentityColumnSupport getIdentityColumnSupport() {
		// TODO Auto-generated method stub
		return new SQLiteIdentityColumnSupport();
	}

	@Override
	public boolean hasAlterTable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dropConstraints() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDropForeignKeyString() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getAddForeignKeyConstraintString(String constraintName, String[] foreignKey, String referencedTable,
			String[] primaryKey, boolean referencesPrimaryKey) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String getAddPrimaryKeyConstraintString(String constraintName) {
		// TODO Auto-generated method stub
		return "";
	}
	
	

}
