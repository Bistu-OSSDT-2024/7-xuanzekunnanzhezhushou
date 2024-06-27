package 选择困难症;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DecisionMaker {

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JButton eatButton, playButton, customButton;
    private List<CustomPanel> customPanels; // 存储所有自定义面板
    private JFrame foodFrame;
    private JPanel foodPanel;
    private JList<String> foodList;
    private JButton addButton, removeButton, randomButton, returnButton; // 添加返回按钮
    private DefaultListModel<String> foodListModel;

    public DecisionMaker() {
        initMainFrame();
        initFoodFrame();
        customPanels = new ArrayList<>();
    }

    private void initMainFrame() {
        mainFrame = new JFrame("选择困难者助手");
        mainPanel = new JPanel();

        eatButton = new JButton("吃什么");
        eatButton.addActionListener(e -> {
        	//打开主界面
            foodFrame.setVisible(true);
            mainFrame.setVisible(false);
        });
        

        playButton = new JButton("玩什么");
        playButton.addActionListener(e -> {
            // 打开玩什么界面
            JFrame playWhatFrame = new JFrame("玩什么？");
            PlayWhatPanel playWhatPanel = new PlayWhatPanel(playWhatFrame);
            playWhatFrame.setContentPane(playWhatPanel);
            playWhatFrame.pack();
            playWhatFrame.setVisible(true);
            mainFrame.setVisible(false);
        });

        customButton = new JButton("自定义");
        customButton.addActionListener(e -> {
            // 弹出对话框让用户输入名字创建新的自定义按钮
            String customName = JOptionPane.showInputDialog("请输入自定义按钮的名字：");
            if (customName != null && !customName.isEmpty()) {
                addCustomButton(customName);
            }
        });

        mainPanel.add(eatButton);
        mainPanel.add(playButton);
        mainPanel.add(customButton);

        mainFrame.add(mainPanel);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);

        // 添加返回主界面的按钮
        returnButton = new JButton("返回主界面");
        returnButton.addActionListener(e -> {
            mainFrame.setVisible(true);
        });
    }

    private void addCustomButton(String customName) {
        JButton customButton = new JButton(customName);
        customButton.addActionListener(e -> {
            // 打开自定义面板
            JFrame customFrame = new JFrame(customName);
            CustomPanel customPanel = new CustomPanel(customFrame);
            customPanels.add(customPanel);
            customFrame.setContentPane(customPanel);
            customFrame.pack();
            customFrame.setVisible(true);
            mainFrame.setVisible(false);
        });
        mainPanel.add(customButton);
        mainFrame.pack();
    }

    private void initFoodFrame() {
        foodFrame = new JFrame("吃什么？");

        foodPanel = new JPanel();

        foodListModel = new DefaultListModel<>();
        foodListModel.addElement("米饭");
        foodListModel.addElement("面条");
        foodListModel.addElement("饺子");

        foodList = new JList<>(foodListModel);
        addButton = new JButton("添加");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newFood = JOptionPane.showInputDialog("请输入新的食物：");
                if (newFood != null && !newFood.isEmpty()) {
                    foodListModel.addElement(newFood);
                }
            }
        });
        removeButton = new JButton("删除");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = foodList.getSelectedIndex();
                if (selectedIndex != -1) {
                    foodListModel.removeElementAt(selectedIndex);
                } else {
                    JOptionPane.showMessageDialog(null, "请选择一个食物进行删除！");
                }
            }
        });

        randomButton = new JButton("开始随机抽选");
        randomButton.addActionListener(e -> {
            if (!foodListModel.isEmpty()) {
                Random rand = new Random();
                int index = rand.nextInt(foodListModel.size());
                JOptionPane.showMessageDialog(null, "抽选结果：" + foodListModel.elementAt(index));
            } else {
                JOptionPane.showMessageDialog(null, "列表为空，请先添加食物！");
            }
        });

        foodPanel.add(new JScrollPane(foodList));
        foodPanel.add(addButton);
        foodPanel.add(removeButton);
        foodPanel.add(randomButton);
        foodPanel.add(returnButton); // 将返回按钮添加到食物选择界面

        foodFrame.add(foodPanel);
        foodFrame.pack();
        foodFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // 初始时不显示食物选择窗口
        foodFrame.setVisible(false);
    }

    class PlayWhatPanel extends JPanel {

        private JFrame parentFrame;
        private JList<String> playList;
        private DefaultListModel<String> playListModel;

        public PlayWhatPanel(JFrame parentFrame) {
            this.parentFrame = parentFrame;
            setLayout(new BorderLayout());

            playListModel = new DefaultListModel<>();
            playListModel.addElement("游泳");
            playListModel.addElement("跑步");
            playListModel.addElement("打篮球");

            playList = new JList<>(playListModel);
            add(new JScrollPane(playList), BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton addButton = new JButton("添加");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newPlay = JOptionPane.showInputDialog("请输入新的玩法：");
                    if (newPlay != null && !newPlay.isEmpty()) {
                        playListModel.addElement(newPlay);
                    }
                }
            });
            JButton removeButton = new JButton("删除");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = playList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        playListModel.removeElementAt(selectedIndex);
                    } else {
                        JOptionPane.showMessageDialog(null, "请选择一个玩法进行删除！");
                    }
                }
            });
            JButton randomButton = new JButton("开始随机选择");
            randomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!playListModel.isEmpty()) {
                        Random rand = new Random();
                        int index = rand.nextInt(playListModel.size());
                        JOptionPane.showMessageDialog(null, "随机选择的玩法是：" + playListModel.elementAt(index));
                    } else {
                        JOptionPane.showMessageDialog(null, "列表为空，请先添加玩法！");
                    }
                }
            });
            JButton returnButton = new JButton("返回主界面");
            returnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parentFrame.setVisible(false);
                    mainFrame.setVisible(true);
                }
            });

            buttonPanel.add(addButton);
            buttonPanel.add(removeButton);
            buttonPanel.add(randomButton);
            buttonPanel.add(returnButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    class CustomPanel extends JPanel {

        private JFrame parentFrame;
        private String customName;
        private JList<String> itemList;
        private DefaultListModel<String> itemListModel;

        public CustomPanel(JFrame parentFrame) {
            this.parentFrame = parentFrame;
            this.customName = parentFrame.getTitle();
            setLayout(new BorderLayout());

            itemListModel = new DefaultListModel<>();
            
            itemList = new JList<>(itemListModel);
            add(new JScrollPane(itemList), BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton addButton = new JButton("添加");
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String newItem = JOptionPane.showInputDialog("请输入新的" + customName + "：");
                    if (newItem != null && !newItem.isEmpty()) {
                        itemListModel.addElement(newItem);
                    }
                }
            });
            JButton removeButton = new JButton("删除");
            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = itemList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        itemListModel.removeElementAt(selectedIndex);
                    } else {
                        JOptionPane.showMessageDialog(null, "请选择一个" + customName + "进行删除！");
                    }
                }
            });
            JButton randomButton = new JButton("开始随机选择");
            randomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!itemListModel.isEmpty()) {
                        Random rand = new Random();
                        int index = rand.nextInt(itemListModel.size());
                        JOptionPane.showMessageDialog(null, "随机选择的" + customName + "是：" + itemListModel.elementAt(index));
                    } else {
                        JOptionPane.showMessageDialog(null, "列表为空，请先添加" + customName + "！");
                    }
                }
            });
            JButton returnButton = new JButton("返回主界面");
            returnButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    parentFrame.setVisible(false);
                    mainFrame.setVisible(true);
                }
            });

            buttonPanel.add(addButton);
            buttonPanel.add(removeButton);
            buttonPanel.add(randomButton);
            buttonPanel.add(returnButton);

            add(buttonPanel, BorderLayout.SOUTH);
        }
    }

    public static void main(String[] args) {
        new DecisionMaker();
    }
}