<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.io.*" %>
<%@page import="com.oreilly.servlet.ServletUtils" %>

<%
	String fileName = request.getParameter("file_name"); //파일이름 가져오기

	String savePath = "upload"; //저장위치의 폴더명
	ServletContext context = getServletContext();
	String sDownloadPath = context.getRealPath(savePath); //upload폴더의 실제경로를 가져온다
	String sFilePath = sDownloadPath + "\\" + fileName; //그 경로에 +다운받을 파일이름을 붙임
	
	byte b[] = new byte[4096];
	File oFile = new File(sFilePath);
	
	//stream을 통해서..
	FileInputStream in = new FileInputStream(sFilePath); //읽어올 파일명가져오기
	
	String sMimeType = getServletContext().getMimeType(sFilePath); 
	System.out.println("sMimeType >>> " + sMimeType);
	
	if(sMimeType == null){ sMimeType = "application/octet-stream"; } //8비트 형식으로 처리
	
	response.setContentType(sMimeType);
	
	String sEncoding = new String(fileName.getBytes("UTF-8"),"ISO-8859-1");
	
	//파일 다운로드 창 실행 
	response.setHeader("Content-Disposition","attachment; filename = " + sEncoding);
	
	ServletOutputStream out2 = response.getOutputStream(); //보낼 준비하기
	
	int numRead;
	while((numRead = in.read(b,0,b.length)) != -1){
		out2.write(b,0,numRead);
	}
	
	out2.flush(); //버퍼에 있는 모든 파일을 읽어보냄
	out2.close();
	in.close();
%>