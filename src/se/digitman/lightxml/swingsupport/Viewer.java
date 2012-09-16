/*
 * Viewer.java
 *
 * Created on den 3 januari 2007, 17:23
 *
 */

package se.digitman.lightxml.swingsupport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

/**
 * Usable when testing XML structures (or tables) by viewing them
 * in a small swing window.
 * @author Thomas Boqvist, Digit Man
 */
public class Viewer {
    
    public Viewer(Component c) {
        JFrame frame=new JFrame("Swing viewer");
        JScrollPane pane=new JScrollPane(c);
        frame.getContentPane().add(pane, BorderLayout.CENTER);
        frame.setSize(800, 450);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static void viewTree(TreeNode root) {
        new Viewer(new JTree(new DefaultTreeModel(root)));
    }
    
    public static void viewTable(TableModel table) {
        JTable tableView=new JTable(table);
        tableView.setAutoCreateColumnsFromModel(true);
        new Viewer(tableView);
    }
    
    private static void main(String [] args) {
        DefaultTableModel table=new DefaultTableModel();
        Vector contents=new Vector();
        contents.add("ett");
        contents.add("två");
        contents.add("tre");
        table.addColumn("Supmojäng", contents);
        viewTable(table);
    }
}