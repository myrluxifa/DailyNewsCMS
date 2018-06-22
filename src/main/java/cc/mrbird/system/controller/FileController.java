package cc.mrbird.system.controller;

import java.io.File;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cc.mrbird.common.util.ImgUtil;


@Controller
public class FileController {
	@RequestMapping(value="file/uploadFile")
	@ResponseBody
    public String uploadOneFile(@RequestParam("file") MultipartFile file) {  
		return ImgUtil.uploadOneFile(file);
	}
}
