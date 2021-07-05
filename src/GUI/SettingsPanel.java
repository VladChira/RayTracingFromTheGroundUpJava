package GUI;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    public SettingsPanel(Viewport viewport) {
        JButton renderButton;

        GridBagLayout gbPanel0 = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(gbPanel0);

        renderButton = new JButton("Render Scene");
        gbPanel0.setConstraints(renderButton, gbc);
        this.add(renderButton);
    }
}
