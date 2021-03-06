package baidu;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import sun.misc.BASE64Encoder;

public class BaiduAi {
	/**
	 * 图像识别
	 * @param token
	 * @param url
	 * @param filePath
	 * @return
	 */
	public static String imageRecognition(String url, String filePath){
		String token = AuthService.getAuth();
		return imageRecognition(token,url,filePath);
	}
	private static String imageRecognition(String token, String url, String filePath) {
		// 请求url
//        url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //通用文字识别（标准版） 50000次/天免费
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general"; //通用文字识别（标准含位置版）500次/天免费
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic"; //通用文字识别（高精度版）500次/天免费
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate"; //通用文字识别（高精度含位置版） 50次/天免费
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage"; //网络图片文字识别 500次/天免费
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting"; //手写文字识别 50次/天免费
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/numbers"; //数字识别 200次/天免费
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/formula"; //公式识别 剩余免费1000次
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/doc_analysis"; //试卷分析与识别 剩余免费1000次
        
        try {
            // 本地文件路径
        	File file = new File(filePath);
        	FileInputStream  fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            //将文件内容写进字符数组：
            fis.read(bytes);
            fis.close();
            
          //对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            String imgStr = encoder.encode(bytes);//返回Base64编码过的字节数组字符串
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            //String imgParam = imgStr;
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            //String accessToken = Token.getAuth();
            String accessToken = token;
            String param ="access_token=" + accessToken + "&image=" + imgParam;
            String result = sendPost(url, param);
            System.out.println(result);
            // 格式化输出结果
            System.out.println(format(result));
            System.out.println("图片解析完成");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取不到");
            return "图片规格不符合";
        }
	}
	
	private static String sendPost(String url, String param) {
		 PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	}
	
	public static String format(String str) {
		JSONObject r = JSONObject.parseObject(str);
		JSONArray jsonArray = r.getJSONArray("words_result");
		String result = "";
		for(int i=0;i<jsonArray.size();i++){
			JSONObject row = jsonArray.getJSONObject(i);
			String words = row.getString("words");
			result += words + "\r\n";
		}
		return result;
	}
	/**
	 * 辨别文字识别结果是否存在 check
	 * @param str
	 * @param check 
	 * @return
	 */
	public static Boolean checkWords(String str, String check) {
		JSONObject r = JSONObject.parseObject(str);
		JSONArray jsonArray = r.getJSONArray("words_result");
		int length = check.length();
		for(int i=0;i<jsonArray.size();i++){
			JSONObject row = jsonArray.getJSONObject(i);
//			System.out.println("row："+row);
			String words = row.getString("words");
//			System.out.println("图片上的文字："+words);
			for(int j=0;j<length;j++){
				if(words.contains(String.valueOf(check.charAt(j)))){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 截图文字识别，判断是否已经打开，
	 * 过一段时间，跳不出循环则返回false
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @param filePath
	 * @param check
	 */
//	public static void checkIsOpen(int x, int y, int w, int h, String filePath, String check) {
//		String isOpen = "";
//		do{
//			String fileName = ImageHandler.getScreen(x, y, w, h,filePath);
//			isOpen = BaiduAi.imageRecognition("", fileName);
//		}while(!BaiduAi.checkWords(isOpen,check));
//	}
	
	
}
