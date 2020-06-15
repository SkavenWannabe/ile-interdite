package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class VueReglesDuJeu {
    
    private final JFrame fenetre;
    private JButton continuer;
    private JLabel titre;
    private JPanel centre;
    private JTextArea regles;
    
    private JScrollPane scroll;
    
    public VueReglesDuJeu(){
    	fenetre = new JFrame("Regles Du Jeu");
        fenetre.setResizable(false);
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(610, 500);
        
        titre = new JLabel("Règles du jeu", JLabel.CENTER);
        titre.setFont(new Font("Arial", Font.PLAIN, 20));
        titre.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        centre = new JPanel(new BorderLayout());
        
        regles = new JTextArea(""
                + "L'Île Interdite était le cœur mystique de l’ancien empire des Atlantes.\n"
                + "La légende raconte qu’ils avaient le pouvoir de contrôler les quatre éléments\n"
                + "– le feu, le vent, l’eau et la terre – au moyen de trésors à la valeur\n"
                + "inestimable : le Cristal ardent, la Statue du zéphyr, le Calice de l’onde et\n"
                + "la Pierre sacrée. Ainsi, les Atlantes gardaient les trésors cachés sur l’Île\n"
                + "Interdite qui était conçue pour s’enfoncer dans les flots si des intrus y\n"
                + "mettaient les pieds. Durant les longs siècles qui nous séparent de la\n"
                + "mystérieuse disparition de leur empire, l’Île Interdite demeurait\n"
                + "introuvable … jusqu’à aujourd’hui.\n"
                + "\n"
                + "Vous êtes des aventuriers à la recherche des quatre (4) trésors antiques\n"
                + "A votre arrivée sur l'île, le mécanisme de défense se met en place et vous inonde\n"
                + "petit à petit !!\n"
                + "Votre but ? S'échapper de l'Île Interdite en équipe avec ses quatre (4) trésors via\n"
                + "l'héliport de fortune présent, sans que l'un de vous ne sombre des les abysses !\n"
                + "Pour se faire vous pourrez :\n"
                + "- Vous déplacer sur une tuile adjacente;\n"
                + "- Assécher une tuile adjacente, une tuile qui a sombré dans les abysses ne peut pas\n"
                + "  être assécher !\n"
                + "- Donner une carte Trésor à un autre joueur si vos deux pions sont sur la même tuile !\n"
                + "  Attention vous ne pouvez pas donner une carte Action Spéciale (Hélicoptère et\n"
                + "  Sacs de sable);\n"
                + "- Vous pouvez aussi Gagner un Trésor en désaussant de votre main 4 cartes Trésor\n"
                + "  indentique, a condition que votre pion soit sur la tuile correspondante au\n"
                + "  Trésor en question !\n"
                + "\n"
                + "Quelques execptions :\n"
                + "L'Explorateur peut se déplacer et assécher diagonalement !\n"
                + "Le Pilote peut aller une fois par tour sur n'importe quelle tuilee pour une (1) action !\n"
                + "Le Navigateur peut déplacer d'autres joueurs d'une ou deux tuile adjacente par action !\n"
                + "Le Plongeur peut se déplacer au travers d'une ou plusieurs tuiles adjacentes\n"
                + "manquante et/ou innondée pour une (1) action !\n"
                + "L'Ingénieur peut assécher deux (2) tuiles adjacente a lui pour une (1) action !\n"
                + "Vous pouvez effectuer trois (3) actions par tour !\n"
                + "\n"
                + "Attention, vous perdrez si :\n"
                + "- Une des tuile Trésor sombre dans les abysses avant que vous ayez pû récuperer\n"
                + "  son Trésor.\n"
                + "- L'Héliport sombre dans les abysses.\n"
                + "- Un aventurier est sur une tuile qui sombre et qu'il n'a pas de tuile adjacente où nager.\n"
                + "- Si le niveau de l'eau atteind dix (10).\n"
                + "\n"
                + "Bonne chance !");
        
        regles.setEnabled(false);
        regles.setDisabledTextColor(Color.BLACK);
        regles.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        regles.setFont(new Font("Arial", Font.PLAIN, 14));
        scroll = new JScrollPane(regles,VERTICAL_SCROLLBAR_AS_NEEDED,HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centre.add(scroll, BorderLayout.CENTER);
        
        JPanel panelBas = new JPanel(new GridLayout(1,3));
        continuer = new JButton ("Continuer");
        continuer.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                fenetre.setVisible(false);
        	}
        });
        panelBas.add(new JLabel()); panelBas.add(continuer); panelBas.add(new JLabel());
        panelBas.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        fenetre.add(titre, BorderLayout.NORTH);
        fenetre.add(centre, BorderLayout.CENTER);
        fenetre.add(panelBas, BorderLayout.SOUTH);
        
        fenetre.setVisible(true);
    }
}
