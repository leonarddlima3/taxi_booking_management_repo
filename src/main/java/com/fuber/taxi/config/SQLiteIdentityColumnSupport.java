package com.fuber.taxi.config;

import org.hibernate.MappingException;
import org.hibernate.dialect.identity.IdentityColumnSupportImpl;

public class SQLiteIdentityColumnSupport extends IdentityColumnSupportImpl{

	@Override
	public boolean supportsIdentityColumns() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String getIdentitySelectString(String table, String column, int type) throws MappingException {
		// TODO Auto-generated method stub
		return "select last_insert_rowid()";
	}

	@Override
	public String getIdentityColumnString(int type) throws MappingException {
		// TODO Auto-generated method stub
		return "integer";
	}
	
}
