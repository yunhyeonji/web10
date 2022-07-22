package com.saeyan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/UploadServlet3")
public class UploadServlet3 extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public UploadServlet3() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		HashMap<String, String> map = new HashMap<String,String>();
		
		String savePath = "upload";
		int uploadFileSizeLimit = 5 * 1024 * 1024;
		String endType = "UTF-8";
		
		ServletContext context = getServletContext();
		String uploadFilePath = context.getRealPath(savePath);
		
		try {
			//파일 업로드가 가능한 매개변수5개
			MultipartRequest multi = new MultipartRequest(
					request,
					uploadFilePath,
					uploadFileSizeLimit,
					endType,
					new DefaultFileRenamePolicy()
			);
			
			Enumeration files = multi.getFileNames();
			while(files.hasMoreElements()) {
				String file = (String) files.nextElement();
				String file_name = multi.getFilesystemName(file);	
				//중복된 파일을 업로드할 경우 파일명이 바뀐다
				String ori_file_name = multi.getOriginalFileName(file);
								

				map.put(file_name,ori_file_name);
//				out.println("<br>파일명: " + file_name);
//				out.println("<br>파일명_Origin: " + ori_file_name);
//				out.print("<hr>");
			}
			request.setAttribute("map", map);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("fileList3.jsp");
		dispatcher.forward(request, response);
	}

}
