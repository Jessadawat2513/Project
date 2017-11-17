package com.java.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.ArrayList;

import com.java.controller.*;
import com.java.model.*;

/**
 * Servlet implementation class ScheduleView
 */
@WebServlet("/ScheduleView")
public class ScheduleView extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Semester semester;
		ArrayList<Semester> sem_list = new ArrayList<>();
		
		Building building;
		ArrayList<Building> b_list = new ArrayList<>();
		
		Room room;
		ArrayList<Room> room_list = new ArrayList<>();
		
		Connection conn;
		Statement st;
		ResultSet rs;
		
		try{
			conn = ConnectionManager.getConnection();
			
			String qr = "select s.sem_id,d.sem_name,s.year from semester s,d_semester d where s.sem_id = d.sem_id order by s.sem_id,s.year asc";
			
			st = conn.createStatement();
			rs = st.executeQuery(qr);
			
			while(rs.next()){
				semester = new Semester();
				semester.setSemesterId(rs.getString("sem_id"));
				semester.setSemesterName(rs.getString("sem_name"));
				semester.setYear(rs.getString("year"));
				sem_list.add(semester);
			}
			
			String qr1 = "select b_id,b_name from building order by b_id asc";
			
			st = conn.createStatement();
			rs = st.executeQuery(qr1);
			
			while(rs.next()){
				building = new Building();
				building.setBuildId(rs.getString("b_id"));
				building.setBuildName(rs.getString("b_name"));
				b_list.add(building);
			}
			
			String qr2 = "select rm_num from room order by rm_num asc";
			
			st = conn.createStatement();
			rs = st.executeQuery(qr2);
			
			while(rs.next()){
				room = new Room();
				room.setRoomNum(rs.getString("rm_num"));
				room_list.add(room);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("sem_list", sem_list);
			session.setAttribute("b_list", b_list);
			session.setAttribute("room_list", room_list);
			
			response.setContentType("text/html");
			response.getWriter().write("scheduleview.jsp");
			response.getWriter().close();
			
		}catch(SQLException ex){
			
		}
	}

}
