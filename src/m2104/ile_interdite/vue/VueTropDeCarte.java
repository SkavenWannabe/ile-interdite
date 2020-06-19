package m2104.ile_interdite.vue;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import m2104.ile_interdite.util.Message;

public class VueTropDeCarte implements MouseListener {
	private final IHM ihm;
    private final JFrame fenetre;
    
    private JPanel mainPanel;
    private JPanel panelNorth;
    private PanelCartes panelCartes;
    private JPanel panelSouth;
    
    private JTextArea indication;
    private JButton continuer;
    private ArrayList<String> main;
    private int idAventurier;
    
    public VueTropDeCarte(IHM ihm, int idAventurier, ArrayList<String> main) {
    	System.out.println("VUE : trop de carte");
        this.ihm = ihm;
        this.main = main;
        this.idAventurier = idAventurier;
        
        fenetre = new JFrame("Surplu de carte");
        fenetre.setLayout(new BorderLayout());
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setSize(500, 400);
        
        mainPanel = new JPanel(new BorderLayout());
        panelNorth = new JPanel();
        panelSouth = new JPanel(new GridLayout(3,1));
        
        indication = new JTextArea(""
                + "Au maximum un joueur peu avoir dans sa main que 5 cartes, \n"
                + "Veuillez donc cliquer sur les cartes dont vous souhaitez vous deffaussez, \n"
                + "Une fois que cela est fait cliquer sur continuer pour reprendre le jeu."); 
        panelNorth.add(indication);
        
        panelCartes = new PanelCartes(main);
        panelCartes.addMouseListener(this);
        
        continuer = new JButton ("Continuer");        
        continuer.setEnabled(false);				  
        continuer.addActionListener(new java.awt.event.ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		calculNouvelleMain();
        	}
        });
        panelSouth.add(new JLabel()); panelSouth.add(continuer); panelSouth.add(new JLabel());
        
        mainPanel.add(panelNorth, BorderLayout.NORTH);
        mainPanel.add(panelCartes, BorderLayout.CENTER);
        mainPanel.add(panelSouth, BorderLayout.SOUTH);
        
        fenetre.add(mainPanel);
        fenetre.setVisible(true);
    }
    
    public void desactiver() {
    	System.out.println("desactiver vue trop de carte");
    	fenetre.dispose();
    }
    
    public void calculNouvelleMain() {
    	System.out.println("Calcul nouvel main");
    	ArrayList<Integer> deffausseMain = new ArrayList<Integer>();
    	for (int i = 0; i < main.size(); i++) {
    		if (panelCartes.estSelectionner(i)) {
    			deffausseMain.add(i);
    		}
    	}
    	ihm.notifierObservateurs(Message.nvMain(idAventurier, deffausseMain));
    }
    
    
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("mouseClicked");
		if (panelCartes.getSelection() > 5 || panelCartes.dejaCliquer(panelCartes.getNumeroCarte(e.getX(), e.getY()))) {
			System.out.println("nbCarte > 5");
			panelCartes.setSelection(panelCartes.getNumeroCarte(e.getX(), e.getY()));
		} 
		if (panelCartes.getSelection() > 5 ) {
			continuer.setEnabled(false);
		} else {
			continuer.setEnabled(true);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
