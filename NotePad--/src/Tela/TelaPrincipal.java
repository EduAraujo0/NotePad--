package Tela;

/*@Author: Raimundo Eduardo de Araujo Filho 
 * Ra: 1410402
 * Turma: ADS 2°B
 * Data: 22/09/2014
 * Objetivo: Bloco de notas - NotaPad-- 
 */

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;

public class TelaPrincipal extends JFrame {
		
	private JMenuBar menuBar;
	private JMenu mnuArquivo;
	private JMenu mnuFormatar;
	private JMenuItem mnuItemNovo;
	private JMenuItem mnuItemSair;
	private JMenuItem mnuItemAbrir;
	private JMenuItem mnuItemSalvar;
	private JMenuItem mnuItemSalvarComo;
	private JMenuItem mnuItemQuebraLinha;
	private JSeparator separadorMenuArquivo;
	private JTextArea areaTexto;
	private JScrollPane scroll;
	
	private boolean quebraLinha = false;
	private boolean textoAlterado = false;
	private boolean textoSalvo = false;
	private String tituloCabec = "Notepad--";
	private String nomeArquivo = "";
	
	public TelaPrincipal(){
		initComponents();
		initListeners();
	}	
		
	private void initListeners(){	
		addWindowListener(new WindowListener() {			
			@Override
			public void windowOpened(WindowEvent e) {
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				sair();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {

			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				
			}
		});
		
		areaTexto.addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent e) {
				
			}			
			@Override
			public void keyReleased(KeyEvent e) {
			}			
			@Override
			public void keyPressed(KeyEvent e) {
				textoAlterado();							
			}
		});		
		
		mnuItemSair.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {				
				sair();
			}
		});
		
		mnuItemAbrir.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				abrir();
			}
		});
		
		mnuItemSalvar.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				salvar();
			}
		});
		
		mnuItemSalvarComo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				salvarComo();
			}
		});
		
		mnuItemNovo.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				novo();				
			}
		});
		
		mnuItemQuebraLinha.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(quebraLinha == false){
					areaTexto.setLineWrap(true);
					quebraLinha = true;
				} else {
					areaTexto.setLineWrap(false);
					quebraLinha = false;
				}
			}
		});
	}	
		
	private void textoAlterado(){
		if(textoAlterado == false){
			textoAlterado = true;
			if(!nomeArquivo.equals("")  && textoSalvo == true){
				this.setTitle("*" + tituloCabec + " | " + "("+ nomeArquivo +")");
			} else {
				this.setTitle("*" + tituloCabec);
			}
		}		
	}

	private void novo(){
		if(textoAlterado == true){
			salvar();
		}
		areaTexto.setText(null);
		this.setTitle(tituloCabec);
		textoAlterado = false;
		textoSalvo = false;
		nomeArquivo = "";
	}
	
	private void abrir() {
		JFileChooser fileChooser = new JFileChooser();
		int actionDialog = fileChooser.showOpenDialog(this);
		
		if (actionDialog != JFileChooser.APPROVE_OPTION) {
			return;
		}
		
		File file = fileChooser.getSelectedFile();
		FileReader fr;		
		try {
			nomeArquivo = String.valueOf(file);
			this.setTitle(tituloCabec + " | " + "("+ nomeArquivo +")");			
			fr = new FileReader(file);
			BufferedReader bfr = new BufferedReader(fr);
			String linha = "";
			areaTexto.setText(null);
			while((linha = bfr.readLine()) != null){
				areaTexto.append(linha+"\n");
			}
			bfr.close();
			textoSalvo = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void sair() {
		if(textoAlterado == true){
			int resposta = JOptionPane.showConfirmDialog(null, "Deseja salvar?");
				
			if(resposta == JOptionPane.YES_OPTION){
				if(!new File(nomeArquivo).exists()){				
					salvarComo();
				} else {
					salvar();
				}
				System.exit(0);
			} else if(resposta == JOptionPane.NO_OPTION) {					
				System.exit(0);
			}
		} else {
			System.exit(0);
		}			
	}
	
	private void salvar() {
		if(nomeArquivo.equals("") && !new File(nomeArquivo).exists()){
			salvarComo();
		} else {			
			File file = new File(nomeArquivo);
			BufferedWriter bfWriter = null;
			
			try {
				bfWriter = new BufferedWriter(new FileWriter(file));
				areaTexto.write(bfWriter);
				textoSalvo = true;
				textoAlterado = false;
				nomeArquivo = String.valueOf(file);
				this.setTitle(tituloCabec + " | " + "("+ nomeArquivo +")");
				bfWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	private void salvarComo(){		
		final JFileChooser fileChooser = new JFileChooser();
		fileChooser.setApproveButtonText("Save");
		fileChooser.setDialogTitle("Salvar como...");
		int actionDialog = fileChooser.showOpenDialog(this);
		
		if (actionDialog != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File file = new File(fileChooser.getSelectedFile() + ".txt");
		BufferedWriter bfWriter = null;
		
		if(new File(String.valueOf(file)).exists()){
			int resposta = JOptionPane.showConfirmDialog(null, "O arquivo já existe, deseja sobrescrever?", "", JOptionPane.YES_NO_OPTION);
			
			if(resposta == JOptionPane.NO_OPTION){
				return;
			}
		}
		
		try {
			bfWriter = new BufferedWriter(new FileWriter(file));
			areaTexto.write(bfWriter);
			textoSalvo = true;
			textoAlterado = false;
			nomeArquivo = String.valueOf(file);			
			this.setTitle(tituloCabec + " | " + "("+ nomeArquivo +")");
			bfWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	     
	}	
	
	private void initComponents(){
		this.setSize(800, 600);//define o tamanho da tela
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);//define o que fazer quando clica no 'X'
		this.setResizable(false);//não deixa redimencionar
		this.setTitle("Notepad--");//Define o titulo da tela
		this.setLocationRelativeTo(null);//centraliza
		
		menuBar = new JMenuBar();//criando a barra de menus
		
		mnuArquivo = new JMenu();//criando o menu arquivo
		mnuArquivo.setText("Arquivo");//add um titulo no menu
		
		mnuFormatar = new JMenu();//Criando o menu formatar
		mnuFormatar.setText("Formatar");//add um titulo no menu
		
		mnuItemNovo = new JMenuItem();
		mnuItemNovo.setText("Novo");
		
		mnuItemAbrir = new JMenuItem();
		mnuItemAbrir.setText("Abrir...");
		
		mnuItemSalvar = new JMenuItem();
		mnuItemSalvar.setText("Salvar");
		
		mnuItemSalvarComo = new JMenuItem();
		mnuItemSalvarComo.setText("Salvar como...");
		
		mnuItemSair = new JMenuItem();
		mnuItemSair.setText("Sair");
		
		mnuItemQuebraLinha = new JMenuItem();
		mnuItemQuebraLinha.setText("Quebra automática de linha");
		
		separadorMenuArquivo = new JSeparator();
		
		mnuArquivo.add(mnuItemNovo);
		mnuArquivo.add(mnuItemAbrir);
		mnuArquivo.add(mnuItemSalvar);
		mnuArquivo.add(mnuItemSalvarComo);		
		mnuArquivo.add(separadorMenuArquivo);
		mnuArquivo.add(mnuItemSair);
		
		mnuFormatar.add(mnuItemQuebraLinha);
		
		areaTexto = new JTextArea();		
		
		menuBar.add(mnuArquivo);//adicionando o menu na barra
		menuBar.add(mnuFormatar);//adicionando o menu na barra
		this.setJMenuBar(menuBar);//adicionando a barra na tela				
		this.add(areaTexto);		
		
		scroll = new JScrollPane(areaTexto);		
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		this.add(scroll);			
	}	
}