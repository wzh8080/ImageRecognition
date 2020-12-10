package tesseract;

import java.io.File;
import java.io.IOException;

public class App {

    public static void main(String[] args) {
        try {
            //图片文件：此图片是需要被识别的图片 
            File file = new File("D:/zhwang/Software/OCR/tesseract-4.1.1/2.png");
            String recognizeText = new OCRHelper().recognizeText(file);
            System.out.print(recognizeText + "\t");
 
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("完成");
    }

}
