package baidu;

public class App {
	public static void main(String[] args) {
//		String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic"; //ͨ������ʶ�𣨱�׼�棩 50000��/�����
      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic"; //ͨ������ʶ�𣨸߾��Ȱ棩500��/�����
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general"; //ͨ������ʶ�𣨱�׼��λ�ð棩500��/�����
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate"; //ͨ������ʶ�𣨸߾��Ⱥ�λ�ð棩 50��/�����
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/webimage"; //����ͼƬ����ʶ�� 500��/�����
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting"; //��д����ʶ�� 50��/�����
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/numbers"; //����ʶ�� 200��/�����
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/formula"; //��ʽʶ�� ʣ�����1000��
//      String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/doc_analysis"; //�Ծ������ʶ�� ʣ�����1000��
		
		BaiduAi.imageRecognition(url, "C:\\Users\\zhwang\\Desktop\\111.jpg");
		
		System.out.println("���");
	}
}
