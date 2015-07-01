package com.consumer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * 
 * 之所以要使用下面这条语句，是因为要使用MySQL的驱动，
 * 所以我们要把它驱动起来，可以通过Class.forName把它加载进去，
 * 也可以通过初始化来驱动起来，下面三种形式都可以
 * Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
 * */
public class CobarDao {	
	Connection conn = null;	 
	public void conn()
	{
		int result=0;
        String url = "jdbc:mysql://127.0.0.1:8066/dbtest?user=test&password=test";
       // String url = "jdbc:cobar://127.0.0.1:8066/dbtest?user=root&password=root";
        try 
        {
            Class.forName("com.alibaba.cobar.jdbc.Driver");
            System.out.println("成功加载MySQL驱动程序");
            conn = DriverManager.getConnection(url);
         }
        catch(Exception e)
        {
        	e.printStackTrace();
        	try {
				conn.close();
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
        }
       
	}
	public void delete(String sql)
	{
		   //
           PreparedStatement pps = null;
		try {
			pps = conn.prepareStatement(sql);
			boolean re=pps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
	        {
	        	try {
	        		pps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	}
	
//按照表分别入库
	public void insert(String sql,int id,String mobile)
	{
		 PreparedStatement pst = null;
	   	 try {
			pst = conn.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	//int i=5000; 
	   	try {
			pst.setInt(1, id);
			pst.setString(2,mobile);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   	try {
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	   	try {
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void insertBatch(String sql)
	{
		 int j,k=0;
	     int m=3072;
	     int n=1024;
	     int[] tt=null;
         PreparedStatement pst = null;
		try {
			pst = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
         for(int i=0;i<n;i++)
         {
         	k=m-i;
         	j=m-1024-i;
         	try 
         	{
				pst.setInt(1, i);
				pst.setString(2,"part2");
				pst.setInt(3, j);
				pst.setString(4,"part3");
				pst.setInt(5, k);
				pst.setString(6,"part4");
				pst.addBatch();			
				
         	}
         	catch(Exception e)
         	{
         		e.printStackTrace();
         	}        	
         	
         }
         try {
			tt = pst.executeBatch();
			System.out.println("update : " + tt.length);  
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
         
        
            try {
				conn.commit();
				conn.setAutoCommit(true);  
			        pst.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	        finally
	        {
	        	
					try {
						pst.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		System.out.println(conn);
				
//	        	catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
	        }
         
		
     
	}
	public void query()
	{
		 String query="select * from tb2";
         PreparedStatement pps = null;
		try {
			pps = conn.prepareStatement(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         ResultSet rs = null;
		try {
			rs = pps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         try {
			while(rs.next())
			 {
			 	System.out.println(rs.getString(1)+" "+rs.getString(2));
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	public void freeConn(){
		//cpm.freeConnection(conn);
		try{
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
