package ru.rendaxx.lab8client.frame;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.rendaxx.lab8client.client.UpdateHandler;
import ru.rendaxx.lab8client.forms.AnimationForm;
import ru.rendaxx.lab8client.forms.CanvasForm;
import ru.rendaxx.lab8client.forms.DefaultMenuBar;
import ru.rendaxx.lab8client.forms.MainForm;
import ru.rendaxx.lab8client.util.LocalePublisher;
import ru.rendaxx.lab8client.util.SetTextListener;
import ru.rendaxx.lab8client.util.UpdateListener;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class CanvasFrame extends JFrame implements SetTextListener, UpdateListener {

    ApplicationContext applicationContext;
    JLayeredPane layeredPane;
    JPanel panel1;
    JPanel panel2;
    @Autowired
    public CanvasFrame(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        initUI();
    }

    private void initUI() {
        applicationContext.getBean(UpdateHandler.class).addListener(this);
        applicationContext.getBean(LocalePublisher.class).addSubscriber(this);

        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/icon.png")));

        layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(10000, 10000));

        panel1 = applicationContext.getBean(AnimationForm.class).getPanel1();
        panel1.setBackground(Color.BLUE);
        panel1.setBounds(0, 0, 10000, 10000);

        panel2 = applicationContext.getBean(CanvasForm.class).getPanel1();
        panel2.setBounds(0, 0, 10000, 10000);

        layeredPane.add(panel1, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(panel2, JLayeredPane.PALETTE_LAYER);


        add(layeredPane);
//        add(applicationContext.getBean(AnimationForm.class).getPanel1());
//        add(applicationContext.getBean(CanvasForm.class).getPanel1());

        JMenuBar menuBar = applicationContext.getBean(DefaultMenuBar.class);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    @Override
    public void setText() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("bundles/main");
        setTitle(resourceBundle.getString("main.frame.title"));
    }

    @Override
    public void update() {
        layeredPane.setLayer(panel1, JLayeredPane.MODAL_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        layeredPane.setLayer(panel1, JLayeredPane.DEFAULT_LAYER);
        SwingUtilities.updateComponentTreeUI(this);
    }
}
