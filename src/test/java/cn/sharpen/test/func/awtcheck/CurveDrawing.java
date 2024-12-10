package cn.sharpen.test.func.awtcheck;

import java.awt.*;
import javax.swing.*;

public class CurveDrawing extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 设置抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 画坐标轴
        g2.drawLine(50, 250, 450, 250); // X轴
        g2.drawLine(50, 50, 50, 250);   // Y轴

        // 画曲线
        g2.setColor(Color.RED);
        for (int x = 0; x <= 400; x++) {
            int y = (int) (100 * Math.sin(x * 0.02));
            g2.drawLine(50 + x, 150 - y, 50 + x, 150 - y);
        }
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("曲线图示例");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.add(new CurveDrawing());
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CurveDrawing::createAndShowGUI);
    }
}

