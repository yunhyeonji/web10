package com.saeyan.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/upload2.do")
public class UploadServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public UploadServlet2() {
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
			int i = 1;
			while(files.hasMoreElements()) {
				String file = (String) files.nextElement();
				String file_name = multi.getFilesystemName(file);	
				//중복된 파일을 업로드할 경우 파일명이 바뀐다
				String ori_file_name = multi.getOriginalFileName(file);
								
				request.setAttribute("file_name"+i, file_name);
				request.setAttribute("ori_file_name"+i, ori_file_name);
//				out.println("<br>파일명: " + file_name);
//				out.println("<br>파일명_Origin: " + ori_file_name);
//				out.print("<hr>");
				i++;
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher("fileList.jsp");
		dispatcher.forward(request, response);
	}

}
