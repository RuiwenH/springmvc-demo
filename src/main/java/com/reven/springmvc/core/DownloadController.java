package com.reven.springmvc.core;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
@RequestMapping("/downloadFile")
public class DownloadController {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Model model;
	@ModelAttribute  
	public void setReqAndRes(HttpServletRequest request, HttpServletResponse response,Model model){
		this.request=request;
		this.response=response;
		this.model=model;
	}
	@RequestMapping("/download")    
    public ResponseEntity<byte[]> download() throws IOException {
    	String fileNameParam = request.getParameter("fileName");
    	if(fileNameParam==null||fileNameParam.equals(""))
    		return null;
    	String basePath="D:\\myeclipseworkspace\\fileForDownload\\";
    	String path = basePath+fileNameParam;
    	File file=new File(path);
    	HttpHeaders headers = new HttpHeaders();
        String fileName=new String(fileNameParam.getBytes("UTF-8"),"iso-8859-1");//为了解决中文名称乱码问题  
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.CREATED);
	}
}
