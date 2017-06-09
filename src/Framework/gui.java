package Framework;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicArrowButton;

import Tetris.Tetris;

//https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html


public class gui implements ActionListener{
	static protected TextField mutatetxt,replacetxt;
    static protected JButton mutate,replace,train;
    static protected BasicArrowButton leftarrow,rightarrow;
    static protected JLabel mutatelab,numproplab,avgfitlab,bestfitlab,bestfitprop,tst,errorlab,replacelab;
    static Population popu;
    static Trainer trainer;
    
    static void resetcons (GridBagConstraints cons){
    	cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.ipadx = 0;
		cons.ipady = 0;
		cons.anchor = GridBagConstraints.FIRST_LINE_START;
		cons.insets = new Insets(0,0,0,0);
    }
    
    static public void addComponentsToPane(JPanel pane) {

    	/*
    	cons.fill = GridBagConstraints.HORIZONTAL;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.ipadx = 0;
		cons.ipady = 0;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.FIRST_LINE_START;
		cons.insets = new Insets(0,0,0,0);
		
		FIRST_LINE_START	PAGE_START		FIRST_LINE_END
		LINE_START			CENTER			LINE_END
		LAST_LINE_START		PAGE_END		LAST_LINE_END

    	 */
      
		GridBagConstraints cons = new GridBagConstraints();
		
		mutatetxt = new TextField();
		mutatetxt.setColumns(5);
		mutatetxt.setFont(new Font("Arial", Font.PLAIN, 20));
		mutatetxt.addActionListener(new gui());
		cons.weightx = 0.2;
		cons.ipady = 0;
		cons.gridx = 0;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.LINE_END;
		cons.insets = new Insets(0,0,0,0);
		pane.add(mutatetxt, cons);
		
		mutate = new JButton("Set Mutation Rate");
		mutate.setFont(new Font("Arial", Font.PLAIN, 20));
		mutate.setActionCommand("mutate");
		mutate.addActionListener(new gui());
		mutate.setToolTipText("Value must be between 0 and 1");
		cons.weightx = 0.2;
		cons.ipady = 0;
		cons.gridx = 1;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.LINE_START;
		cons.insets = new Insets(0,10,0,0);
		pane.add(mutate, cons);
		
		replacetxt = new TextField();
		replacetxt.setColumns(5);
		replacetxt.setFont(new Font("Arial", Font.PLAIN, 20));
		cons.weightx = 0.2;
		cons.ipady = 0;
		cons.gridx = 2;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.LINE_END;
		cons.insets = new Insets(0,10,0,0);
		pane.add(replacetxt, cons);
	
		replace = new JButton("Set Replacement Rate");
		replace.setFont(new Font("Arial", Font.PLAIN, 20));
		replace.setActionCommand("replace");
		replace.addActionListener(new gui());
		cons.weightx = 0.2;
		cons.ipady = 0;
		cons.gridx = 3;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.LINE_START;
		cons.insets = new Insets(0,10,0,0);
		pane.add(replace, cons);
		
		train = new JButton("TRAIN!");
		train.setFont(new Font("Arial", Font.PLAIN, 50));
		train.setActionCommand("train");
		train.addActionListener(new gui());
		cons.weightx = 0.2;
		cons.gridx = 4;
		cons.gridy = 0;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(0,0,0,0);
		pane.add(train, cons);
	
		
		JPanel pretty = new JPanel(new GridBagLayout());
		pretty.setBackground(Color.RED);		
		
		for (int i=0;i<100;i++){
			cons.fill = GridBagConstraints.NONE;
			cons.weightx = 0;
			cons.weighty = 0;
			cons.ipadx = 45;
			cons.ipady = 45;
			cons.gridx = i%10;
			cons.gridy = i/10;
			cons.anchor = GridBagConstraints.FIRST_LINE_START;
			cons.insets = new Insets(10,10,0,0);
			pretty.add(new JButton(),cons);
		}
		
		
		cons.fill = GridBagConstraints.BOTH;
		cons.gridx = 0;
		cons.gridy = 1;
		cons.weighty = 1;
		cons.gridwidth = 4;
		cons.ipady = 400;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(0,0,0,0);
		pane.add(pretty,cons);
		
		
		
		JPanel stats = new JPanel();
		stats.setLayout(new BoxLayout(stats,BoxLayout.Y_AXIS));
		
		stats.add(Box.createVerticalStrut(20));
		
		mutatelab = new JLabel("Mutation Rate: "+trainer.mutation);
		mutatelab.setFont(new Font("Arial",Font.PLAIN, 20));
		mutatelab.setForeground(Color.WHITE);
		mutatelab.setAlignmentX(Component.CENTER_ALIGNMENT);
		stats.add(mutatelab);
		stats.add(Box.createVerticalStrut(20));
		
		replacelab = new JLabel("Replacement Rate: "+trainer.percentbreed);
		replacelab.setFont(new Font("Arial",Font.PLAIN, 20));
		replacelab.setForeground(Color.WHITE);
		replacelab.setAlignmentX(Component.CENTER_ALIGNMENT);
		stats.add(replacelab);
		stats.add(Box.createVerticalStrut(20));
		
		numproplab = new JLabel("Number of Properties: "+popu.pop[0].numprop);
		numproplab.setFont(new Font("Arial",Font.PLAIN, 20));
		numproplab.setForeground(Color.WHITE);
		numproplab.setAlignmentX(Component.CENTER_ALIGNMENT);
		stats.add(numproplab);
		stats.add(Box.createVerticalStrut(20));
		
		avgfitlab = new JLabel("Average Fitness: ");
		avgfitlab.setFont(new Font("Arial",Font.PLAIN, 20));
		avgfitlab.setForeground(Color.WHITE);
		avgfitlab.setAlignmentX(Component.CENTER_ALIGNMENT);
		stats.add(avgfitlab);
		stats.add(Box.createVerticalStrut(20));
		
		bestfitlab = new JLabel("Best Fitness: ");
		bestfitlab.setFont(new Font("Arial",Font.PLAIN, 20));
		bestfitlab.setForeground(Color.WHITE);
		bestfitlab.setAlignmentX(Component.CENTER_ALIGNMENT);
		stats.add(bestfitlab);
		stats.add(Box.createVerticalStrut(20));
		
		bestfitprop = new JLabel("Best Fitness Properties: ");
		bestfitprop.setFont(new Font("Arial",Font.PLAIN, 20));
		bestfitprop.setForeground(Color.WHITE);
		bestfitprop.setAlignmentX(Component.CENTER_ALIGNMENT);
		stats.add(bestfitprop);
		
		
		cons.fill = GridBagConstraints.BOTH;
		cons.gridx = 4;
		cons.gridy = 1;
		cons.weighty = 1;
		cons.ipady = 400;
		stats.setBackground(Color.BLACK);
		pane.add(stats,cons);
		
		
		
		JPanel scroll = new JPanel(new FlowLayout());
		scroll.setBackground(Color.PINK);
	
		leftarrow = new BasicArrowButton(BasicArrowButton.WEST);
		leftarrow.setActionCommand("left");
		leftarrow.addActionListener(new gui());
		rightarrow = new BasicArrowButton(BasicArrowButton.EAST);
		rightarrow.setActionCommand("right");
		rightarrow.addActionListener(new gui());
		
		
		
		scroll.add(leftarrow);
		scroll.add(rightarrow);
		
		cons.fill = GridBagConstraints.BOTH;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.ipadx = 0;
		cons.ipady = 15;
		cons.gridwidth = 4;
		cons.gridx = 0;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.FIRST_LINE_START;
		cons.insets = new Insets(0,0,0,0);
		pane.add(scroll,cons);
		
		JPanel error = new JPanel();
		error.setBackground(Color.CYAN);
		
		errorlab = new JLabel("Error Reporting");
		errorlab.setFont(new Font("Arial",Font.PLAIN, 20));
		errorlab.setForeground(Color.RED);
		
		
		cons.fill = GridBagConstraints.NONE;
		cons.weightx = 0;
		cons.weighty = 0;
		cons.ipadx = 0;
		cons.ipady = 15;
		cons.gridwidth = 1;
		cons.gridx = 4;
		cons.gridy = 2;
		cons.anchor = GridBagConstraints.CENTER;
		cons.insets = new Insets(0,0,0,0);
		
		pane.add(errorlab,cons);

		
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("EVOLUTION");
        frame.setSize(1200,800);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon("chromosome.jpg").getImage());
        
        JPanel pane = new JPanel(new GridBagLayout());
        addComponentsToPane(pane);
        frame.add(pane);
        frame.setVisible(true);
    }

    /*
    static protected TextField mutatetxt,replacetxt;
    static protected JButton mutate,replace,train;
    static protected BasicArrowButton leftarrow,rightarrow;
    static protected JLabel mutatelab,numproplab,avgfitlab,bestfitlab,bestfitprop,tst,errorlab;
     */
    
	public void actionPerformed(ActionEvent e) {
        if ("left".equals(e.getActionCommand())) {
            
        } 
        else if ("right".equals(e.getActionCommand())) {
            
        }
        else if ("mutate".equals(e.getActionCommand())){
        	try {
        	    double newmutate = Double.parseDouble(mutatetxt.getText());
        	    if (newmutate<0||newmutate>1){
        	    	errorlab.setText("Mutation Out of Range");
        	    }
        	    else {
        	    	trainer.mutation = newmutate;
        	    	mutatelab.setText("Mutation Rate: "+trainer.mutation);
        	    	errorlab.setText("Mutation Rate Changed!");
        	    }
        	} catch (NumberFormatException error) {
        	    errorlab.setText("Parameter Invalid!");
        	}
        }
        else if ("replace".equals(e.getActionCommand())){
        	try {
        	    double newreplace = Double.parseDouble(replacetxt.getText());
        	    if (newreplace<0||newreplace>1){
        	    	errorlab.setText("Replace Out of Range");
        	    }
        	    else {
        	    	trainer.percentbreed = newreplace;
        	    	replacelab.setText("Replacement Rate: "+trainer.percentbreed);
        	    	errorlab.setText("Replacement Rate Changed!");
        	    }
        	} catch (NumberFormatException error) {
        	    errorlab.setText("Parameter Invalid!");
        	}
        }
		else if ("train".equals(e.getActionCommand())){
			errorlab.setText("TRAINING!");
			
			
			double avgfit = 0;
			int bestchrom=-1;
			for (int i=0;i<popu.size;i++){
				if (i%(popu.size*0.1)==0){
					System.out.println("Chromosome: "+i);
				}
				popu.pop[i].fitness=0;
				for (int k=0;k<5;k++){
					// ONLY LINE THAT NEEDS CHANGING
					popu.pop[i].fitness +=new Tetris().go(popu.pop[i].prop,false);
				}
				avgfit += popu.pop[i].fitness;
				if (bestchrom==-1||popu.pop[i].fitness>popu.pop[bestchrom].fitness){
					bestchrom=i;
				}
				
			}
			avgfitlab.setText("Average Fitness: "+avgfit/popu.size);
			bestfitlab.setText("Best Fitness: "+popu.pop[bestchrom].fitness+" ("+popu.pop[bestchrom].fitness/popu.pop[bestchrom].numprop+")");
			String bestprop = "<html>Best Fitness Properties: <br>";
			for (int i=0;i<popu.pop[bestchrom].numprop;i++){
				bestprop+=popu.pop[bestchrom].prop[i]+" ";
			}
			bestprop+="<html>";
			trainer.learn(popu);
			errorlab.setText("DONE!");
			bestfitprop.setText(bestprop);
        }
    }
    	
    

	public static void main(String[] args) {
    	popu = new Population(20);
    	popu.randomize(5, -10, 10);
    	trainer = new Trainer();
    	createAndShowGUI();
    }
}