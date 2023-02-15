package com.amu.home.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.amu.home.dto.BDto;

public class BDao {

	DataSource dataSource;
	
	
	
	public BDao() {
		super();
		// TODO Auto-generated constructor stub
		
		try {
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup("java:comp/env/jdbc/Oracle11g");
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
	}



	public ArrayList<BDto> list(){
		
		ArrayList<BDto> dtos = new ArrayList<BDto>();
		
		Connection conn= null; //DB 연결 생성
		PreparedStatement pstmt =  null; //sql 실행 
		ResultSet  rs = null; //select 일때 결과 받아주는 객체
		
		try {
			
			conn = dataSource.getConnection(); //dataSource에서 connection 생성
			
			String sql = "select * from mvc_board order by bid desc";
			//게시글 번호의 내림차순 정렬로 모든 글 목록 가져오기(최근글이 가장 위에 오도록 함)
			
			pstmt = conn.prepareStatement(sql); // sql 실행
			rs = pstmt.executeQuery(); // select 는 query 나머지는 update
			System.out.println(rs.next());
			while(rs.next()) { //rs에 들어있는 글들의 수만큼 반복
				int bid = rs.getInt("bid");
				String bname = rs.getString("bname");
				String btitle = rs.getString("btitle");
				String bcontent = rs.getString("bcontent");
				Timestamp bdate = rs.getTimestamp("bdate");
				int bhit = rs.getInt("bhit");
				int bgroup = rs.getInt("bgroup");
				int bstep = rs.getInt("bstep");
				int bindent = rs.getInt("bindent");
				
			    System.out.println(btitle);
	           BDto dto = new BDto(bid, bname,   bcontent, btitle, bdate, bhit, bgroup, bstep, bindent);
	            dtos.add(dto);

			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return dtos;
	}
}
