package tesseract;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        try {
            //ͼƬ�ļ�����ͼƬ����Ҫ��ʶ���ͼƬ 
            File file = new File("D:/zhwang/Software/OCR/tesseract-4.1.1/2.png");
            String recognizeText = new OCRHelper().recognizeText(file);
            System.out.print(recognizeText + "\t");
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("���");
    }

}
