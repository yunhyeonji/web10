package com.saeyan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.saeyan.dto.ProductVO;

import util.DBManager;

public class ProductDAO {
	private ProductDAO() {
	}
	
	private static ProductDAO instance = new ProductDAO();
	
	public static ProductDAO getInstance() {
		return instance;
	}
	

	public List<ProductVO> selectAllProducts() {
		/* sql에 맞는 정보를 mVo객체 안에 넣어주기
		 * list안에 mVo객체를 넣고 mVo에 새로운 객체 넣기(반복)
		 * list를 리턴한다. */
		List<ProductVO> list = new ArrayList();
		
		String sql = "select * from product order by desc";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = DBManager.getConnection(); 
			//getConnection메소드가 DBManager에서 static메소드이므로 변수 생성없이 사용가능
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ProductVO pVo = new ProductVO();
				pVo.setCode(rs.getInt("code"));
				pVo.setName(rs.getString("name"));
				pVo.setPrice(rs.getInt("price"));
				pVo.setPictureUrl(rs.getString("pictureUrl"));
				pVo.setDescription(rs.getString("description"));
				list.add(pVo);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
			//close메소드가 DBManager에서 static메소드이므로 변수 생성없이 사용가능
		}
		return list;
	}
}
