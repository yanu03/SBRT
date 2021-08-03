package kr.tracom.cm.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.tracom.cm.domain.File.FileService;


public class FileController {
	
	@Autowired
	private FileService fileService;

/*	@Autowired
	private ExcelUtils utils;*/
	
	/*@RequestMapping("/filePreview")
	public void filePreview(RequestParam<Object> requestParams, HttpServletResponse response) {
		fileService.preview(requestParams, response);
	}*/
	
	/*
	@RequestMapping("/uplaodWavTemp")
	 public ApiResponse uploadWavTemp(@ModelAttribute VoiceInfoVO request) {
		fileService.uplaodWavTemp(request);
		return ok();
	}
	
	@RequestMapping("/downloadExcel")
	public void downloadExcel(RequestParams<Object> requestParams, HttpServletResponse response) throws IOException {
		utils.writeExcel(requestParams.getString("type"), response);
	}*/
}
