package fr.efontain.snack;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JFrame implements Constances{

    private ModelDuJeu modele;
    
    public Main() {
        // titre de la fenêtre
        super("Snake");
        // créer le modèle du jeu
        this.modele = new ModelDuJeu(); 
        // fermeture de l'application lorsque la fenêtre est fermée
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        // pas de redimensionnement possible de la fenêtre
        setResizable(false);
        // créer un conteneur qui affichera le jeu
        JPanel content = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                  super.paintComponent(g);
                  // affichage du modèle du jeu
                  Main.this.modele.affichage(g);
            }
        };
        // dimension de ce conteneur 
        content.setPreferredSize(
            new Dimension( 
                NBRE_DE_COLONNES * CASE_EN_PIXELS , NBRE_DE_LIGNES * CASE_EN_PIXELS
            )
        );    
        // ajouter le conteneur à la fenêtre
        setContentPane(content);
          // Créer un thread infini
        Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) { // boucle infinie
                // à chaque fois que la boucle est exécutée, la méthode de calcul du jeu est appelée.
                // Comme la boucle est infinie, la méthode de calcul sera appelée en cycle perpétuel.
                Main.this.modele.calcul();
                // demander à l'EDT de redessiner le conteneur , ce qui aura pour effet de dessiner notre jeu
                content.repaint();
                  // temporisation
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e) {
                
                }
            }
        }
        
    });
    // lancer le thread
    thread.start();
    // le listener gérant les entrées au clavier
    content.addKeyListener(new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            Main.this.modele.gestionDuClavier(e);
        }
    });
    // s'assurer du focus pour le listener clavier
    setFocusable(false);
    content.setFocusable(true);
}
  
  // Lancement du jeu 
    public static void main(String[] args) {
        // création de la fenêtre
        Main fenetre = new Main();
        // dimensionnement de la fenêre "au plus juste" suivant la taille des composants qu'elle contient
        fenetre.pack();
        // centrage sur l'écran
        fenetre.setLocationRelativeTo(null);
        // affichage
        fenetre.setVisible(true);
    }
    
}
