package com.consumer.service;

import com.consumer.CobarDao;



//使用 cobar访问 mysql 的方法类
public class Service {
	public void register(String sid)
	{
		CobarDao dao = new CobarDao();
		dao.conn();
		String sql = "insert into tb2 (id,val) values (?,?)";
		int id=-1;
		try {
			id=Integer.parseInt(sid);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String mobile="18611701625";
		//异常情况下不插入数据库
		if(id!=-1 )
		{
			dao.insert(sql,id,mobile);
		}
		dao.freeConn();
		
	}

}
