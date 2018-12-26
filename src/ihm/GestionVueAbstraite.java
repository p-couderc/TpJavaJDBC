/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ihm;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import data.DataTransac;
import data.ProgrammeurBean;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author pierre
 */
public abstract class GestionVueAbstraite extends JFrame implements ActionListener{
    
    //déclaration des widgets pour le menu
    private JMenuBar barreMenu;
    private JMenu menuProg;
    private JMenu menuAction;
    private JPopupMenu ssMenuAfficher;
    private JMenuItem ssMenuAjouter;
    private JMenuItem ssMenuModifier;
    private JMenuItem ssMenuSupprimer;

    private JButton btnAfficherTous;
    private JButton btnRechercher;
    private JLabel labelNom;
    private JTextField champNom;
    private JTextArea zoneAffichageProgrammeurs;
    private JPanel pane;
    private JScrollPane scroll;
    private ProgrammeurBean progrBean;
    private String contenuTextArea;
    private DataTransac dt;

    public void init() {
        //défini le look and feel 
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(GestionVueAbstraite.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pane = new JPanel(); // Créantion d'un panel pour gérer les widgets
        btnAfficherTous = new JButton("Afficher Tous");
        btnRechercher = new JButton("Rechercher");
        labelNom = new JLabel("Nom");
        champNom = new JTextField();
        champNom.setColumns(10);
        zoneAffichageProgrammeurs = new JTextArea();

        /** Tous les widgets sont placés sur le panel
         * Et après le panel est "posé" sur notre frame de base
         */        
        pane.add(btnAfficherTous);
        pane.add(labelNom);
        pane.add(champNom);
        pane.add(btnRechercher);

        zoneAffichageProgrammeurs = new JTextArea(10, 50);
        scroll = new JScrollPane(zoneAffichageProgrammeurs);
        pane.add(scroll);

        btnAfficherTous.addActionListener(this);
        btnRechercher.addActionListener(this);

        /**
         * Par défaut, notre frame n'est pas visible
         * Il faut donc forcer la visibilité à "true"
         */
        this.setVisible(true);
        this.setTitle("TP 3");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Fermeture fenêtre = arrêt de l'application 
        setBounds(10, 10, 600, 300);

        this.add(pane); // Ajout du panel à notre frame de base
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnAfficherTous) {
            dt = new DataTransac();
            contenuTextArea = dt.afficherProgrammeurs();
            zoneAffichageProgrammeurs.setText(contenuTextArea);
            dt.fermerRessources();
        } else if (event.getSource() == btnRechercher) {
            dt = new DataTransac();
            progrBean = dt.getProgrammeur(this.champNom.getText());
            if (progrBean == null) {
                JOptionPane.showMessageDialog(this, "Programmeur introuvable", "Echec", JOptionPane.ERROR_MESSAGE);
            } else {
                contenuTextArea = progrBean.toString();
                zoneAffichageProgrammeurs.setText(contenuTextArea);
            }

            dt.fermerRessources();
        }
    }
}