// FONTE DAS IMAGENS: https://en.wikipedia.org/wiki/Logic_gate (domínio público)

package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ItemListener, MouseListener {
    private static final int BORDER = 10;
    private static final int SWITCH_SIZE = 18;
    private static final int LIGHT_SIZE = 12;
    private static final int GATE_WIDTH = 90;
    private static final int GATE_HEIGHT = 60;

    private final Switch[] switches;
    private final Gate gate;
    private final JCheckBox[] inputBoxes;
    private final Image image;
    private Color color;
    // --Commented out by Inspection (19/06/2019 09:31):private Color color2;


    public GateView(Gate gate) {
        super(GATE_HEIGHT);

        this.gate = gate;

        int inputSize = gate.getInputSize();

        switches = new Switch[inputSize];
        inputBoxes = new JCheckBox[inputSize];

        for (int i = 0; i < inputSize; i++) {
            switches[i] = new Switch();
            inputBoxes[i] = new JCheckBox();

            gate.connect(i, switches[i]);
        }

        int x, y, step;

        x = BORDER;
        y = -(SWITCH_SIZE / 2);
        step = (GATE_HEIGHT / (inputSize + 1));
        for (JCheckBox inputBox : inputBoxes) {
            y += step;
            add(inputBox, x, y, SWITCH_SIZE, SWITCH_SIZE);
        }

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        for (JCheckBox inputBox : inputBoxes) {
            inputBox.addItemListener(this);
        }

        update();

        this.addMouseListener(this);
    }

    private void update() {
        for (int i = 0; i < gate.getInputSize(); i++) {
            if (inputBoxes[i].isSelected()) {
                switches[i].turnOn();
            } else {
                switches[i].turnOff();
            }
        }

        repaint();
    }

    @Override
    public void itemStateChanged(ItemEvent event) {
        update();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, BORDER + SWITCH_SIZE, 0, GATE_WIDTH, GATE_HEIGHT, this);

        int outputSize = gate.getOutputSize();

        // Espaçamento
        int y, step;
        y = -(SWITCH_SIZE / 2);
        step = (GATE_HEIGHT / (outputSize + 1));

        for (int i = 0; i < outputSize; i++) {
            y += step;
            boolean result = gate.read(i);

            if (result) {
                g.setColor(color);
            } else {
                g.setColor(Color.BLACK);
            }
            g.fillOval(BORDER + SWITCH_SIZE + GATE_WIDTH, y, LIGHT_SIZE, LIGHT_SIZE);
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        int x = BORDER + SWITCH_SIZE + GATE_WIDTH + LIGHT_SIZE / 2;

        int outputSize = gate.getOutputSize();

        // Espaçamento
        int y, step;
        y = -(SWITCH_SIZE / 2);
        step = (GATE_HEIGHT / (outputSize + 1));

        for (int i = 0; i < outputSize; i++) {
            y += step;
            boolean result = gate.read(i);

            if (Math.sqrt(Math.pow(x - e.getX(), 2) + Math.pow(y - e.getY(), 2)) < LIGHT_SIZE / 2) {
                Color color = JColorChooser.showDialog(this, null, this.color);

                if (color != null) {
                    this.color = color;
                }

                repaint();
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
