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
	 * ͼ��ʶ��
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
		// ����url
//        url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //ͨ������ʶ�𣨱�׼�棩 50000��/�����
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general"; //ͨ������ʶ�𣨱�׼��λ�ð棩500��/�����
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic"; //ͨ������ʶ�𣨸߾��Ȱ棩500��/�����
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate"; //ͨ������ʶ�𣨸߾��Ⱥ�λ�ð棩 50��/�����
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage"; //����ͼƬ����ʶ�� 500��/�����
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting"; //��д����ʶ�� 50��/�����
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/numbers"; //����ʶ�� 200��/�����
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/formula"; //��ʽʶ�� ʣ�����1000��
//        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/doc_analysis"; //�Ծ������ʶ�� ʣ�����1000��
        
        try {
            // �����ļ�·��
        	File file = new File(filePath);
        	FileInputStream  fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            //���ļ�����д���ַ����飺
            fis.read(bytes);
            fis.close();
            
          //���ֽ�����Base64����
            BASE64Encoder encoder = new BASE64Encoder();
            String imgStr = encoder.encode(bytes);//����Base64��������ֽ������ַ���
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            //String imgParam = imgStr;
            // ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
            //String accessToken = Token.getAuth();
            String accessToken = token;
            String param ="access_token=" + accessToken + "&image=" + imgParam;
            String result = sendPost(url, param);
            System.out.println(result);
            // ��ʽ��������
            System.out.println(format(result));
            System.out.println("ͼƬ�������");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("��ȡ����");
            return "ͼƬ��񲻷���";
        }
	}
	
	private static String sendPost(String url, String param) {
		 PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // �򿪺�URL֮�������
	            URLConnection conn = realUrl.openConnection();
	            // ����ͨ�õ���������
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // ����POST�������������������
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // ��ȡURLConnection�����Ӧ�������
	            out = new PrintWriter(conn.getOutputStream());
	            // �����������
	            out.print(param);
	            // flush������Ļ���
	            out.flush();
	            // ����BufferedReader����������ȡURL����Ӧ
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream(),"UTF-8"));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("���� POST ��������쳣��"+e);
	            e.printStackTrace();
	        }
	        //ʹ��finally�����ر��������������
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
	 * �������ʶ�����Ƿ���� check
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
//			System.out.println("row��"+row);
			String words = row.getString("words");
//			System.out.println("ͼƬ�ϵ����֣�"+words);
			for(int j=0;j<length;j++){
				if(words.contains(String.valueOf(check.charAt(j)))){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * ��ͼ����ʶ���ж��Ƿ��Ѿ��򿪣�
	 * ��һ��ʱ�䣬������ѭ���򷵻�false
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
