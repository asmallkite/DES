import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 可以封装构造方法 传入界面的参数，去执行功能
 */

public class Test {
    String toTextMes;
    String toTextKeyMes;
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
        frame.setSize(550, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(null);

        JLabel toTextLabel = new JLabel("明文");
        toTextLabel.setBounds(10, 20, 50, 25);
        panel.add(toTextLabel);

        JTextField toText = new JTextField(20);
        toText.setBounds(100, 20, 165, 25);
        panel.add(toText);
        frame.setVisible(true);

        JLabel toTextKeyLabel = new JLabel("密钥");
        toTextKeyLabel.setBounds(10, 50, 50, 25);
        panel.add(toTextKeyLabel);

        JTextField toTextKey = new JTextField(20);
        toTextKey.setBounds(100, 50, 165, 25);
        panel.add(toTextKey);
        frame.setVisible(true);





        JLabel resLabel = new JLabel("密文");
        resLabel.setBounds(10, 80, 50, 25);
        panel.add(resLabel);

        JTextField resText = new JTextField(20);
        resText.setBounds(100, 80, 165, 25);
        panel.add(resText);
        frame.setVisible(true);

        JButton enCrypt = new JButton("加密");
        enCrypt.setBounds(300, 30, 100, 25);
        panel.add(enCrypt);
        enCrypt.addActionListener(e -> {
            toTextKeyMes = toTextKey.getText();
            toTextMes = toText.getText();


            Crypt crypt= new Crypt();
            int[] a = Util.parseBytes(toTextMes);
            int[] b = Util.parseBytes(toTextKeyMes);
            int[] res = crypt.enCrypt(a, b);
            resText.setText(Util.bit2String(res));
        });

        JButton deCrypt = new JButton("解密");
        deCrypt.setBounds(300, 70, 100, 25);
        panel.add(deCrypt);



    }
}
