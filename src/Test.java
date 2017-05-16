import javax.swing.*;

/**
 *
 */

public class Test {
    private String toTextMes;
    private String toTextKeyMes;

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
        frame.setSize(550, 900);
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

        JTextArea keyArr = new JTextArea(16, 1);
        keyArr.setBounds(20, 130, 215, 295);
        panel.add(keyArr);
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
