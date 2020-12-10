package tesseract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
 
/**
 * ͼ��ʶ�������
 *
 * @author Felix Li
 * @create 2017-12-19-9:12
 */
public class OCRHelper {
 
    private final String LANG_OPTION = "-l";
    private final String EOL = System.getProperty("line.separator");
 
    /**
     *  Tesseract-OCR�İ�װ·��
     */
    private String tessPath = "D:/zhwang/Software/OCR/tesseract-4.1.1";
    //private String tessPath = new File("tesseract").getAbsolutePath();
 
    /**
     * @param imageFile   �����ͼ���ļ�
     * @param imageFormat �����ͼ���ʽ
     * @return ʶ�����ַ���
     */
    public String recognizeText(File imageFile) throws Exception {
        /**
         * ��������ļ��ı�����ļ�Ŀ¼
         */
        File outputFile = new File(imageFile.getParentFile(), "output");
 
        StringBuffer strB = new StringBuffer();
        List<String> cmd = new ArrayList<String>();
 
        cmd.add(tessPath + "\\tesseract");
 
        cmd.add("");
        cmd.add(outputFile.getName());
        cmd.add(LANG_OPTION);
        cmd.add("chi_sim");
        //cmd.add("eng");
 
        ProcessBuilder pb = new ProcessBuilder();
        /**
         *Sets this process builder's working directory.
         */
        pb.directory(imageFile.getParentFile());
        cmd.set(1, imageFile.getName());
        pb.command(cmd);
        pb.redirectErrorStream(true);
        long startTime = System.currentTimeMillis();
        System.out.println("��ʼʱ�䣺" + startTime);
        Process process = pb.start();
        // tesseract.exe 1.jpg 1 -l chi_sim
        //��ϰ��ʹ��ProcessBuilder�ģ�Ҳ����ʹ��Runtime��Ч��һ��
        // Runtime.getRuntime().exec("tesseract.exe 1.jpg 1 -l chi_sim");
        /**
         * the exit value of the process. By convention, 0 indicates normal
         * termination.
         */
//      System.out.println(cmd.toString());
        int w = process.waitFor();
        if (w == 0)// 0���������˳�
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
                    "UTF-8"));
            String str;
 
            while ((str = in.readLine()) != null) {
                strB.append(str).append(EOL);
            }
            in.close();
 
            long endTime = System.currentTimeMillis();
            System.out.println("����ʱ�䣺" + endTime);
            System.out.println("��ʱ��" + (endTime - startTime) + "����");
        } else {
            String msg;
            switch (w) {
                case 1:
                    msg = "Errors accessing files. There may be spaces in your image's filename.";
                    break;
                case 29:
                    msg = "Cannot recognize the image or its selected region.";
                    break;
                case 31:
                    msg = "Unsupported image format.";
                    break;
                default:
                    msg = "Errors occurred.";
            }
            throw new RuntimeException(msg);
        }
        new File(outputFile.getAbsolutePath() + ".txt").delete();
        return strB.toString().replaceAll("\\s*", "");
    }
}