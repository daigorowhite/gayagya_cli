package GayaGaya;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class GayagayaFrame extends Frame implements WindowListener {
    Applet myApplet;

    GayagayaFrame(String title, Applet applet) {
        super(title);
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);

        addWindowListener(this);
        myApplet = applet;
        add("Center", myApplet);

        pack();
        myApplet.init();
        myApplet.start();
    }

    public void windowClosing(WindowEvent e) {
        myApplet.stop();
        myApplet.destroy();
        dispose();
        System.exit(0);
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowOpened(WindowEvent e) {
    }
}