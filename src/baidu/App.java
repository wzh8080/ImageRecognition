package baidu;

public class App {
	public static void main(String[] args) {
//		String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //通用文字识别（标准版） 50000次/天免费
      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic"; //通用文字识别（高精度版）500次/天免费
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general"; //通用文字识别（标准含位置版）500次/天免费
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate"; //通用文字识别（高精度含位置版） 50次/天免费
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage"; //网络图片文字识别 500次/天免费
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting"; //手写文字识别 50次/天免费
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/numbers"; //数字识别 200次/天免费
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/formula"; //公式识别 剩余免费1000次
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/doc_analysis"; //试卷分析与识别 剩余免费1000次
		
		BaiduAi.imageRecognition(url, "C:\\Users\\zhwang\\Desktop\\111.jpg");
		
		System.out.println("完成");
	}
}
