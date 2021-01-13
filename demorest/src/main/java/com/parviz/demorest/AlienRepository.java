package com.parviz.demorest;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class AlienRepository {
	
	Connection con = null;
	
	public AlienRepository() {
		
		String url = "jdbc:mysql://localhost:3306/restdb?serverTimezone=UTC";
		String username = "root";
		String password = "12061";
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Connection successful");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection failed");
		}
	}
	
	public List<Alien> getAliens() {
		
		List<Alien> aliens = new ArrayList<>();
		String sql = "select * from alien";
		
		try {
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			while(rs.next()) {
				Alien a = new Alien();
				a.setId(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setPoints(rs.getInt(3));
				
				aliens.add(a);
			}
			
			System.out.println("get aliens successful");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return aliens;
	}
	
	public Alien getAlien(int id) {
		
		String sql = "select * from alien where id="+id;
		Alien a = null;
		
		try {
			
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			
			if(rs.next()) {
				a = new Alien();
				a.setId(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setPoints(rs.getInt(3));
					
				System.out.println("get alien successful");
				
				return a;
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Alien not found");
		return null;
	}

	public boolean create(Alien alien) {
		
		String sql = "insert into alien values(?,?,?)";
		
		try {
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, alien.getId());
			st.setString(2, alien.getName());
			st.setInt(3, alien.getPoints());
			int rowsAffected = st.executeUpdate();
			
			System.out.println("Alien created successfully\n "+rowsAffected+" row(s) affected");
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Failed to save alien");
		return false;
	}
	
	public boolean update(Alien alien) {
		
		String sql = "update alien set name=?, points=? where id=?";
		
		try {
			
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, alien.getName());
			st.setInt(2, alien.getPoints());
			st.setInt(3, alien.getId());
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected == 0) {
				create(alien);
				System.out.println("Alien not found. New alien created instead");
				return true;
			}
			System.out.println("Alien updated successfully\n "+rowsAffected+" row(s) affected");
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Failed to update alien");
		return false;
	}
	
	public boolean delete(int id) {
		
		String sql = "delete from alien where id="+id;
		
		try {
			
			PreparedStatement st = con.prepareStatement(sql);
			int rowsAffected = st.executeUpdate();
			
			if(rowsAffected == 0) {
				System.out.println("Alien not found.");
				return false;
			}
			System.out.println("Alien deleted successfully\n "+rowsAffected+" row(s) affected");
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Failed to delete alien");
		return false;
	}
}
