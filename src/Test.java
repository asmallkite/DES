import sun.swing.ImageIconUIResource;

import javax.swing.*;
import java.awt.*;
import java.io.*;

/**
 *
 */

public class Test {
    private String toTextMes;
    private String toTextFileMes;
    private String toTextKeyMes;
    private File mFile;
    private String dirPath;
    private String fileName;

    public static void main(String[] args) {
        Test test = new Test();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                test.createAndShowGUI();
            }
        });
    }


    private void createAndShowGUI() {
        JFrame frame = new JFrame("DES 加密/解密");
        frame.setSize(900, 900);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(null);

        JLabel ano = new JLabel("加密/解密十六进制数据（明文和密钥均为16进制）");
        ano.setBounds(20, 2, 330, 25);
        panel.add(ano);

        JLabel ano2 = new JLabel("加密/解密文件");
        ano2.setBounds(500, 2, 330, 25);
        panel.add(ano2);

        JLabel toTextLabel = new JLabel("明文");
        toTextLabel.setBounds(10, 30, 50, 25);
        panel.add(toTextLabel);

        JTextField toText = new JTextField(20);
        toText.setBounds(100, 30, 165, 25);
        panel.add(toText);

        JLabel toTextLabel2 = new JLabel("明文文件");
        toTextLabel2.setBounds(450, 30, 60, 25);
        panel.add(toTextLabel2);

        JTextField toText2 = new JTextField(20);
        toText2.setBounds(550, 30, 165, 25);
        panel.add(toText2);

        ImageIcon imageIcon = new ImageIcon("D:\\Users\\10648\\IdeaProjects\\DES\\resource\\image.png");
        imageIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(23, 23, Image.SCALE_SMOOTH));
        JButton icon = new JButton(imageIcon);
        icon.setBounds(720, 30, 25, 25);
        panel.add(icon);

        icon.addActionListener(e -> {
            File file;
            FileDialog dialog1 = new FileDialog(frame, "打开", FileDialog.LOAD);
            dialog1.setVisible(true);
            dirPath = dialog1.getDirectory();
            fileName = dialog1.getFile();
            if (dirPath == null || fileName == null) {
                return;
            } else {
                toText2.setText(dirPath);
                file = new File(dirPath, fileName);

                try {
                    BufferedReader bufferedReader = new BufferedReader(new
                            FileReader(file));
                    String line = null;
                    StringBuilder sb = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    toTextFileMes = sb.toString();
                    System.out.println(toTextFileMes);
                    bufferedReader.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        });

        JLabel toTextKeyLabel = new JLabel("密钥");
        toTextKeyLabel.setBounds(10, 60, 50, 25);
        panel.add(toTextKeyLabel);

        JTextField toTextKey = new JTextField(20);
        toTextKey.setBounds(100, 60, 165, 25);
        panel.add(toTextKey);

        JLabel toTextKeyLabel2 = new JLabel("密文文件");
        toTextKeyLabel2.setBounds(450, 60, 60, 25);
        panel.add(toTextKeyLabel2);

        JTextField toTextKey2 = new JTextField(20);
        toTextKey2.setBounds(550, 60, 165, 25);
        panel.add(toTextKey2);

        JButton icon2 = new JButton(imageIcon);
        icon2.setBounds(720, 60, 25, 25);
        panel.add(icon2);

        JLabel resLabel = new JLabel("密文");
        resLabel.setBounds(10, 90, 50, 25);
        panel.add(resLabel);

        JTextField resText = new JTextField(20);
        resText.setBounds(100, 90, 165, 25);
        panel.add(resText);
        frame.setVisible(true);

        JTextArea keyArr = new JTextArea(16, 1);
        keyArr.setBounds(20, 140, 215, 295);
        panel.add(keyArr);
        frame.setVisible(true);


        JButton enCrypt = new JButton("加密");
        enCrypt.setBounds(300, 30, 100, 25);
        panel.add(enCrypt);
        enCrypt.addActionListener(e -> {
            toTextKeyMes = toTextKey.getText();
            toTextMes = toText.getText();
            Crypt crypt= new Crypt();
            int[] b = Util.parseBytes(toTextKeyMes);
            int[] a= null;

            if (!toTextMes.isEmpty()) {
                 a = Util.parseBytes(toTextMes);
                int[] res = crypt.enCrypt(a, b);
                resText.setText(Util.bit2String(res));
            } else {
                a = Util.parseBytes(toTextFileMes);
                int[] res2 = crypt.enCrypt(a, b);
                String toFileWrite = Util.bit2String(res2);
                if (mFile == null) {
                    String dirPath2 = dirPath;
                    String fileName2 = "crypt of " + fileName;
                    toTextKey2.setText(dirPath2 + fileName2);
                    if (dirPath == null || fileName == null) {
                        return;
                    } else {
                        mFile = new File(dirPath2, fileName2);
                    }

                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(mFile));
                        bufferedWriter.write(toFileWrite);
                        bufferedWriter.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }

            }


                int[][] keys = KeyGenerate.keysGenerate(b); // 生成密钥数组
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < keys.length; i++) {
                    String temp ;
                    if (i >=0 && i <= 9) {
                        temp = "  " + i;
                    } else {
                        temp = i + "";
                    }
                    sb.append("Key ").append(temp).append(":      ").append(Util.bit2String(keys[i])).append("\r\n");
                }
                keyArr.setText(sb.toString());


        });

        JButton deCrypt = new JButton("解密");
        deCrypt.setBounds(300, 70, 100, 25);
        panel.add(deCrypt);
    }
}
