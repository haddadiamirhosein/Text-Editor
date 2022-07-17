import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class Editor implements ActionListener , MouseListener {
    JFrame frame;
    JMenuBar jmb;
    JMenu jmfile,jmedit,Size,color;
    JMenuItem selectall,copy,cut,paste,save,open,New,bold,large,normal,small,undo, redo;
    JMenuItem red,black,green,yellow;
    JTextArea tarea;
    JScrollPane scrol;
    Font font,font1,font2;
    UndoManager undoManager;
    boolean bol = true;

    public Editor() {
        frame = new JFrame("Text Editor");
        frame.setSize(500,500);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        jmb = new JMenuBar();
        jmfile = new JMenu("File");
        jmedit = new JMenu("Edit");
        Size = new JMenu("Size");
        color = new JMenu("Color");
        selectall = new JMenuItem("SelectAll");
        copy = new JMenuItem("Copy");
        cut = new JMenuItem("Cut");
        paste = new JMenuItem("Paste");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");
        save = new JMenuItem("Save");
        open =new JMenuItem("Open");
        New = new JMenuItem("New");
        bold = new JMenuItem("Bold");
        large = new JMenuItem("Large");
        normal = new JMenuItem("Normal");
        small = new JMenuItem("Small");
        red = new JMenuItem("red");
        black = new JMenuItem("Black");
        green = new JMenuItem("Green");
        yellow = new JMenuItem("Yellow");
        font = new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,30);
        font1 = new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,15);
        font2 = new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,10);
        tarea = new JTextArea();
        tarea.setFont(font1);
        scrol = new JScrollPane(tarea,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        undoManager = new UndoManager();

        selectall.addActionListener(this::actionPerformed);
        cut.addActionListener(this::actionPerformed);
        copy.addActionListener(this::actionPerformed);
        paste.addActionListener(this::actionPerformed);
        large.addActionListener(this::actionPerformed);
        normal.addActionListener(this::actionPerformed);
        small.addActionListener(this::actionPerformed);
        bold.addActionListener(this::actionPerformed);
        red.addActionListener(this::actionPerformed);
        black.addActionListener(this::actionPerformed);
        green.addActionListener(this::actionPerformed);
        yellow.addActionListener(this::actionPerformed);
        open.addActionListener(this::actionPerformed);
        save.addActionListener(this::actionPerformed);
        New.addActionListener(this::actionPerformed);
        undo.addActionListener(this::actionPerformed);
        redo.addActionListener(this::actionPerformed);
        tarea.addMouseListener(this);

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.undo();
                } catch (Exception ex) {
                }
            }
        });

        redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    undoManager.redo();
                } catch (Exception ex) {
                }
            }
        });

        tarea.getDocument().addUndoableEditListener(new UndoableEditListener() {
            public void undoableEditHappened(UndoableEditEvent e) {
                undoManager.addEdit(e.getEdit());
            }
        });


        copy.setMnemonic('c');
        cut.setMnemonic('x');
        paste.setMnemonic('v');
        selectall.setMnemonic('a');
        open.setMnemonic('o');
        save.setMnemonic('s');
        undo.setMnemonic('z');
        redo.setMnemonic('y');

        frame.setJMenuBar(jmb);
        frame.add(scrol);
        jmb.add(jmfile);
        jmb.add(jmedit);
        jmfile.add(New);
        jmfile.add(save);
        jmfile.add(open);
        jmfile.add(selectall);
        jmfile.add(copy);
        jmfile.add(cut);
        jmfile.add(paste);
        jmfile.add(undo);
        jmfile.add(redo);
        jmedit.add(bold);
        jmedit.add(Size);
        jmedit.add(color);

        Size.add(large);
        Size.add(normal);
        Size.add(small);
        color.add(red);
        color.add(black);
        color.add(green);
        color.add(yellow);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectall)
            tarea.selectAll();

        if(e.getSource() == copy)
            tarea.copy();

        if(e.getSource() == cut)
            tarea.cut();

        if(e.getSource() == paste)
            tarea.paste();

        if(e.getSource() == large)
            tarea.setFont(font);

        if(e.getSource() == normal)
            tarea.setFont(font1);

        if(e.getSource() == small)
            tarea.setFont(font2);

        if(e.getSource() == bold) {
            Font fboal;
            if(bol == true) {
                fboal = new Font("Tahoma",Font.BOLD,tarea.getFont().getSize());
                tarea.setFont(fboal);
                bol = false;
            } else {
                fboal = new Font("Tahoma",Font.LAYOUT_LEFT_TO_RIGHT,tarea.getFont().getSize());
                tarea.setFont(fboal);
                bol = true;
            }

        }

        if(e.getSource() == open) {
            JFileChooser fc = new JFileChooser();
            int i = fc.showOpenDialog(frame);
            if(i == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                String filepath = file.getPath();
                try(BufferedReader br = new BufferedReader(new FileReader(filepath))) {
                    String s1 = "";
                    String line = br.readLine();
                    while (line != null) {
                        s1 = line + "\n";
                        line = br.readLine();
                    }
                    String s2 = s1.replace("\\r\\n","\\n");
                    String s3 = s2.replace("\\n","\n");
                    tarea.setText(s3);
                    br.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        }

        if(e.getSource() == save) {
            JFileChooser fc = new JFileChooser();
            int i = fc.showSaveDialog(frame);
            if(i == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fc.getSelectedFile();
                    PrintWriter out = new PrintWriter(file);
                    tarea.selectAll();
                    String s = tarea.getSelectedText();
                    String s1 = s.replace("\n","\\n");
                    out.print(s1);
                    out.close();
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }

        if(e.getSource() == New) {
            Thread thread = new Thread(new NewWindowThread());
            thread.start();

        }

        if(e.getSource() == red) {
            tarea.setForeground(Color.red);
        }

        if(e.getSource() == black) {
            tarea.setForeground(Color.BLACK);
        }

        if(e.getSource() == green) {
            tarea.setForeground(Color.GREEN);
        }

        if(e.getSource() == yellow) {
            tarea.setForeground(Color.YELLOW);
        }

    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == tarea) {

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
