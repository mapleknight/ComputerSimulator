package mvc;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.TitledBorder;

import memory.MainMemory;
import common.Message;
import common.Util;
import cpu.CPU;
import cpu.element.Word;
import exception.IllegalMemoryAddressException;
import exception.RegisterNotFoundException;

public class MainFrame extends JFrame implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5470389048053832497L;
	
	/**
	 * display registers in cpu
	 */
	private RegisterItemViewPanel registerItemPanel;
	
	/**
	 * display memory in cpu
	 */
	private MemoryViewPanel memoryViewPanel;
	
	/**
	 * display function to change value in memory
	 */
	private LoadMemoryPanel loadMemoryPanel;

	/**
	 * display different running mode to run cpu
	 */
	private JPanel actionPanel;
	private FlashButton runSingleStepButton;
	private JButton presetButton;
	private JRadioButton radioModeRun;
    private JRadioButton radioModeDebugInstr;
    private JRadioButton radioModeDebugCycle;
    
    private CachePanel cachePanel;
    private PrinterPanel printerPanel;
    private KeyboardPanel keyboardPanel;
    
    private CPU cpu = CPU.getInstance();
	
	public MainFrame()
	{
		super("Computer Simulator");
		//this.setSize(730, 305);
		this.setSize(730, 650);
		this.setLocation(200, 120);
		this.setLayout(null);
		
		actionPanel = new JPanel();
		actionPanel.setBorder(new TitledBorder("Action"));
		runSingleStepButton = new FlashButton("RunSingleStep");
		runSingleStepButton.setPreferredSize(new Dimension(120,30));
		presetButton = new JButton("PreSetProgram1");
		presetButton.setPreferredSize(new Dimension(140,30));
		Box box = Box.createHorizontalBox();
		radioModeRun = new JRadioButton("Run");
		radioModeRun.setSelected(true); 
		radioModeDebugInstr = new JRadioButton("Debug(SingleInstruction)");
		radioModeDebugCycle = new JRadioButton("Debug(SingleCycle)");
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioModeRun);
		group.add(radioModeDebugInstr);
		group.add(radioModeDebugCycle);
		box.add(radioModeRun);
		box.add(radioModeDebugInstr);
		box.add(radioModeDebugCycle);
		
		actionPanel.add(box);
		actionPanel.add(runSingleStepButton);
		actionPanel.add(presetButton);
		
		registerItemPanel = new RegisterItemViewPanel();
		memoryViewPanel = MemoryViewPanel.getInstance();
		loadMemoryPanel = new LoadMemoryPanel();
		
		actionPanel.setBounds(5, 220, 725, 65);
		registerItemPanel.setBounds(445, 5, 275, 210);
		memoryViewPanel.setBounds(5, 5, 270, 210);
		loadMemoryPanel.setBounds(275, 5, 170, 140);
		
		cachePanel=new CachePanel();
		cachePanel.setBounds(5, 285, 260, 243);
		
		
		printerPanel = new PrinterPanel();
    	printerPanel.setBounds(270, 285, 260, 220);
    	
		
    	keyboardPanel = new KeyboardPanel();
		keyboardPanel.setBounds(270, 510, 260, 50);
		keyboardPanel.setPreferredSize(new Dimension(211,200));
    	

		
		this.add(actionPanel);
		this.add(registerItemPanel);
		this.add(memoryViewPanel);
		this.add(loadMemoryPanel);
		this.add(cachePanel);
		this.add(printerPanel);
		this.add(keyboardPanel);
		
		this.setVisible(true);
		this.setResizable(false);
		this.addListener();
		
		//observe CPU
		cpu.addObserver(this);
	}
	
	/**
	 * add listeners to buttons and radios
	 */
	private void addListener(){
		runSingleStepButton.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						
						if(!CPU.POWER_STATE)
						{
							cpu.execute();
							CPU.POWER_STATE = true;
						}
						cpu.continueRun(CPU.SIGNAL_STEP);
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
					
				});
		presetButton.addMouseListener(
				new MouseListener(){

					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							cpu.getRegisterByRealName("PC").write(30);
							cpu.getRegisterByRealName("X1").write(50);
							MainMemory.getInstance().write(51, Util.getBinaryArrayFromIntValue(51, 16));
							MainMemory.getInstance().write(54, Util.getBinaryArrayFromIntValue(55, 16));
							MainMemory.getInstance().write(55, Util.getBinaryArrayFromIntValue(15, 16));
							
							cpu.getMemory().write(30, new Word(Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("10101000001"), 16)));
							MainMemory.getInstance().write(31, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("100101000010"), 16));
							MainMemory.getInstance().write(32, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("111101000001"), 16));
							MainMemory.getInstance().write(33, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("1010011001000001"), 16));
							MainMemory.getInstance().write(34, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("1010101001000011"), 16));
							MainMemory.getInstance().write(35, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("1000101000001"), 16));
							MainMemory.getInstance().write(36, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("1010101000001"), 16));
							MainMemory.getInstance().write(37, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("1100101000001"), 16));
							MainMemory.getInstance().write(38, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("1110101000001"), 16));
							MainMemory.getInstance().write(39, Util.getBinaryArrayFromIntValue(Util.getIntValueFromBinaryString("10101100100"), 16));
							registerItemPanel.update();
							memoryViewPanel.update();
						} catch (NumberFormatException
								| IllegalMemoryAddressException | RegisterNotFoundException e1) {
							e1.printStackTrace();
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
					
				});
		radioModeRun.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(radioModeRun.isSelected())
							cpu.setMode(CPU.MODE_RUN);
					}
				});
		radioModeDebugInstr.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(radioModeDebugInstr.isSelected())
							cpu.setMode(CPU.MODE_INSTRUCTION);
					}
				});
		radioModeDebugCycle.addActionListener(
				new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						if(radioModeDebugCycle.isSelected())
							cpu.setMode(CPU.MODE_CYCLE);
					}
				});
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg == null){
			registerItemPanel.update();
			memoryViewPanel.update();
		}else{
			int msg = (Integer)arg;
			
			switch(msg){
			
				case(Message.MSG_CPU_STEP_WAITING):
					runSingleStepButton.startFlash();
					registerItemPanel.update();
					memoryViewPanel.update();
					break;
				
				case(Message.MSG_CPU_STEP_CONTINUE):
					runSingleStepButton.stopFlash();
					registerItemPanel.update();
					memoryViewPanel.update();
					break;
				
				case(Message.MSG_CPU_CIRCLE_FINISH):
					registerItemPanel.updateState();
					break;
				default:
					
					break;
			}
		}
	}
	
	/**
	 * main function of this simulator
	 * @param args
	 */
	public static void main(String[] args){
		try {
			
			if(System.getProperty("os.name").contains("Windows")){
				String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
				
				UIManager.setLookAndFeel(lookAndFeel);
			}
			
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
