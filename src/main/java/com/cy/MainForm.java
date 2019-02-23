package com.cy;

import com.alee.laf.WebLookAndFeel;
import com.cy.common.*;
import com.cy.core.Main;
import com.cy.manager.DiskManager;
import com.cy.util.UKeyBoard;
import com.cy.util.UUI;
import com.cy.util.UtilPlugin;
import com.google.common.eventbus.Subscribe;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import static com.cy.common.FinalConstants.logger;

/**
 * Created by cy on 2016/1/25.
 */
public class MainForm {
    private JButton mBtnRun;
    private JTextPane mJTPProgress;
    private JPanel mRootForm;
    private JScrollPane mJScrollPane;
    private JButton mBtnClear;
    private JCheckBox mcb_eclilpse_path;

    public MainForm() {
        Main.doWork(UtilPlugin.getCurrFilePath(Constants.getAnActionEvent()), mcb_eclilpse_path.isSelected());
    }

    public static void main(String[] args) {
        FinalConstants.logger.trace("程序启动");
        System.out.println("程序启动");
        setLookAndFeel();

    }

    private static MainForm mainForm;

    private static void initUI() {


        //init frame start
        JFrame frame = new JFrame("MainForm");
        frame.setTitle(FinalConstants.TITLE);
        MenuBar mb = new MenuBar();
        Menu menuHelp = new Menu("帮助");
        menuHelp.setFont(UUI.preferFont());
        MenuItem menuItemHelp = new MenuItem("帮助");
        menuHelp.add(menuItemHelp);
        MenuItem menuItemChangeLog = new MenuItem("更新日志");
        menuHelp.add(menuItemChangeLog);
        menuItemChangeLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, FinalConstants.MENU_CHANGELOG,
                        "版本历史", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menuItemHelp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, FinalConstants.HELP_CONTENT,
                        "使用帮助", JOptionPane.PLAIN_MESSAGE);
            }
        });
        MenuItem menuAbout = new MenuItem("关于");
        menuAbout.setFont(UUI.preferFont());
        menuAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, FinalConstants.MENU_AUTHOR_CONTENT,
                        "联系作者", JOptionPane.PLAIN_MESSAGE);
            }
        });
        menuHelp.add(menuAbout);
        mb.add(menuHelp);
        frame.setMenuBar(mb);
        UUI.setUIFont(UUI.preferFont(), frame);
        mainForm = new MainForm();
        UUI.initFrame(frame, mainForm.mRootForm);
        //init frame end
        BusEvents.getBus().register(mainForm);

    }

    @Subscribe
    public void onEvent(BusEvents.Progress event) {
        UUI.appendTextNewLine(mainForm.mJTPProgress, event.message);
    }

    private static void setLookAndFeel() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
//                WebLookAndFeel.install();
                initUI();
            }
        });
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mRootForm = new JPanel();
        mRootForm.setLayout(new GridLayoutManager(6, 1, new Insets(20, 20, 20, 20), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setFont(new Font(label1.getFont().getName(), label1.getFont().getStyle(), 50));
        label1.setText("");
        mRootForm.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mJScrollPane = new JScrollPane();
        mRootForm.add(mJScrollPane, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mJTPProgress = new JTextPane();
        mJTPProgress.setText("progress:");
        mJScrollPane.setViewportView(mJTPProgress);
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        mcb_eclilpse_path = new JCheckBox();
        mcb_eclilpse_path.setSelected(false);
        mcb_eclilpse_path.setText("eclipse目录结构");
        mRootForm.add(mcb_eclilpse_path, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 5, new Insets(0, 0, 0, 0), -1, -1));
        mRootForm.add(panel1, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        mBtnRun = new JButton();
        mBtnRun.setMargin(new Insets(15, 28, 15, 28));
        mBtnRun.setText("执行");
        panel1.add(mBtnRun, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), null, null, 0, false));
        mBtnClear = new JButton();
        mBtnClear.setMargin(new Insets(15, 28, 15, 28));
        mBtnClear.setText("清空");
        panel1.add(mBtnClear, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(200, -1), null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mRootForm;
    }
}
