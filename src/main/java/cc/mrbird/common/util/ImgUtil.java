package cc.mrbird.common.util;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;




public class ImgUtil {
	
	public static final String URI="/home/server/tomcat-news-8080/webapps/img-uri/";
	
	//public static final String URI="C:\\";
	
	public static final String IP="http://mrsb.cqlianbei.com/img-uri/";
	
	public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString(); 
        String uuidStr=str.replace("-", "");
        return uuidStr;
      }
	
	 public static String today() {
			Date date=new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd");
			return dateFormat.format(date);
		}

	public static String decryptByBase64(String base64) {  
	    
	    try {  
	    	String imgeUri = getUUID(); 
	    	URL url = null;  
			base64 = base64.split(",")[1];
	    	byte[] b=org.apache.commons.codec.binary.Base64.decodeBase64(base64);
	    	 String fileUri=today()+"/";
             // 文件保存路径  
             String filePath = URI+fileUri;
             String imageName = imgeUri + ".jpg";
             File file = new File(filePath);
             if(!file.exists()) {
             	file.mkdirs();
             }
             FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath+imageName));
             fileOutputStream.write(b);
             fileOutputStream.close(); 
             return  IP+fileUri+imageName;
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    	return "";
	    }  
	}  
	
	
	public static void main(String[] args) {
		decryptByBase64("data:image/jpeg;base64,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");
	}
	
	public static String downloadPicture(String urlString) {  
        URL url = null;  
        String imgeUri = getUUID();  
          
           try {  
                url = new URL(urlString);
                DataInputStream dataInputStream = new DataInputStream(url.openStream());
                String fileUri=today()+"/";
                // 文件保存路径  
                String filePath = URI+fileUri;
                String imageName = imgeUri + ".jpg";
                File file = new File(filePath);
                if(!file.exists()) {
                	file.mkdirs();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath+imageName));
                
                byte[] buffer = new byte[1024];
                int length;
  
                while ((length = dataInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                
                dataInputStream.close();  
                fileOutputStream.close(); 
                
                
                
                return IP+fileUri+imageName;
            } catch (MalformedURLException e) {  
                e.printStackTrace();  
                return null;
            } catch (IOException e) {  
                e.printStackTrace();  
                return null;
            }  
    }  
	
	public static String uploadOneFile(MultipartFile file) {  
        // 判断文件是否为空  
        if (!file.isEmpty()) {  
            try {
            	String[] fileNameStr=file.getOriginalFilename().split("\\.");
            	String url=today()+"/"+getUUID()+".jpg";
                // 文件保存路径  
                String filePath = URI+url;
                if(CreateFile.createImageDir(filePath)) {
                	// 转存文件
                    file.transferTo(new File(filePath));  
                    return IP+url;  
                }else {
                	//转存失败
                	return "-1";
                }
            } catch (Exception e) {  
                e.printStackTrace();  
                return "-2"; 
            }  
        }  else {
        	return "-3"; 
        }
    } 
}
