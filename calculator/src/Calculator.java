package calculator;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;


@SuppressWarnings("serial") // Warnning ����
public class Calculator   {
	/**���� GUI ���**/
	JFrame frame =new JFrame();
	JPanel jpButton, jpResult; // �г� �ʱ�ȭ
	
	JTextField jtResult;// �ؽ�Ʈâ
	private JLabel mLabel; //�޸�â�� ��Ÿ���� ����
	basicbutton[] basicButton = null; // ��ư�迭 �ʱ�ȭ
	String[] numStr = { "M+","M-","MR","MC",
			"��", "LR", "C", "/",
			"7", "8", "9", "*",
			"4", "5", "6", "-",
			"1", "2", "3", "+",
			".", "0", "add", "=" }; // ��ư�� �� ��(������� �� ���Դϴ�)
	/** ���� ���� ���� ����*/
	private BigDecimal resultValue;// ����� ����
	private JButton selectedOperBtn; // ���� ���õ� ���� ��ư
	private boolean isNewValue;//���� ǥ��� ������ ����� ���� �Է����� ����
	private static final String divideErrorText = "0���� ���� �� �����ϴ�."; //������ �����޼��� 
	private BigDecimal memoryValue; //�޸𸮱���� ����ϱ� ����  ����
	
	/* ȸ�� ���� ����*/
	private boolean regressionFlag; //ȸ�������� Ȯ���ϴ� ����
	private int numVar; //�� ������ ����
	private Double[] X; // x������ ����
	private Double[] Y;// y������ ����
	private Double x;// ������ x��
	private boolean addFlag;//������ ���� Ȯ���ϴ� ����
	private int numCnt;//���� ����
	private KeyListener keybordListener;
	Calculator(){
		init();
		createUI();
	}
	
	/** �⺻���� ��ư�� ���� Ŭ�����Դϴ�. (���� Ű�е�)*/
	class basicbutton extends JButton implements ActionListener{

		basicbutton(String name){
		super(name);
		this.setFont(new Font("1����ũǳ�� Regular", Font.BOLD, 20));
		if(name=="1" ||name=="2" ||name=="3" ||name=="4" ||name=="5" ||name=="6" ||name=="7" ||name=="8" ||name=="9" ||name==".")
			this.addActionListener(this);
		this.setBackground(new Color(240,231,216));
		jpButton.add(this);
		}
		

		@Override
		public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            String _name = b.getText();
            pressNumberPad(_name);
		}
		 
		
		
	}
	/** basicbutton�� ����Ͽ� �� ����ɿ� ���� ��ư Ŭ�����Դϴ�*/
	class operbutton extends basicbutton{

		operbutton(String name) {
			super(name);
			this.addActionListener(this);
			this.setBackground(new Color(142,166,166));
			
		}
	@Override
	public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        String _name = b.getText();
		switch(_name){
			case "/":
	        case "*":
	        case "+":
	        case "-":
	            inputOperate(b);
	            break;
	        case "LR":
	            inputRegression();
	            break;
	        case "add":
	            inputaddVar();
	            break;
	        case "��":
	            inputBackspace();
	            break;
	        case "=":
	            inputEnter();
	            break;
	
	        case "C":
	            inputEscape();
	            break;
		}
		
	}
	
    }
    /** basicbutton�� ����Ͽ����� �޸� ��ư�� ���� ��ư Ŭ�����Դϴ�*/
	class memorybutton extends basicbutton{

    	
    	memorybutton(String name){
    		super(name);
    		this.addActionListener(this);
    		this.setBackground(new Color(157,200,200));
			this.setForeground(Color.white);
    }
    	@Override
    	public void actionPerformed(ActionEvent e) {
			String txtValue = jtResult.getText();
        JButton b = (JButton) e.getSource();
        String _name = b.getText();
        switch (_name) {
        
        case "M+":
            if (txtValue.equals(divideErrorText))
                return;

            mLabel.setVisible(true);
            memoryValue = memoryValue.add(new BigDecimal(Float.valueOf(txtValue)));
            isNewValue = true;
            break;

        case "M-":
            if (txtValue.equals(divideErrorText))
                return;

            mLabel.setVisible(true);
            memoryValue = memoryValue.subtract(new BigDecimal(Float.valueOf(txtValue)));
            isNewValue = true;
            break;

        case "MR":
            Float f = memoryValue.floatValue();
            Integer i = memoryValue.intValue();
            if (f.equals(Float.valueOf(i)))
                jtResult.setText(String.valueOf(i));
            else
                jtResult.setText(String.valueOf(f));
            isNewValue = true;
            break;

        case "MC":
            mLabel.setVisible(false);
            memoryValue = new BigDecimal(0);
            isNewValue = true;
            break;
        }
    	}
    }
   /** ��� â�� ���� Ŭ�����Դϴ�*/
	class textResult extends JTextField{
    	textResult(){
    	this.setBorder(null);
        this.setText("0");
        this.setHorizontalAlignment(JTextField.RIGHT);
        this.setFocusable(false);
        this.setEditable(true);
        this.setFont(new Font("1����ũǳ�� Regular", Font.BOLD, 30));
    	}
    }
   /** �޷θ� ��� ǥ�ÿ� ���� Ŭ�����Դϴ�*/
	class memoryLabel extends JLabel{
    	memoryLabel(){
    	this.setText("M");
		this.setBounds(5, 12, 20, 20);
		this.setVisible(false);
    	}
    }
    private void init() {
        this.resultValue = new BigDecimal(0);
        this.selectedOperBtn = null;
        this.isNewValue = true;
        this.memoryValue = new BigDecimal(0); // �޸� ����� Ȱ���ϱ� ���� �����Դϴ�.
        
        keybordListener=new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                Integer code = e.getKeyCode();
                if (code >= 48 && code <= 57)
                    pressNumberPad(String.valueOf(e.getKeyChar()));
                else if (code == 43) {
                    inputOperate(basicButton[19]);//���ϱ�
                	}
                else if (code == 45)
                    inputOperate(basicButton[15]);//����
                else if (code == 42)
                    inputOperate(basicButton[11]);//���ϱ�
                else if (code == 47)
                    inputOperate(basicButton[7]);// ������
                else if (code == 10)
                    inputEnter();// Enter(=Return)
                else if (code == 27)
                    inputEscape();// Esc
                else if (code == 8) 
                    inputBackspace();// Backspace

            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
            }
        };
    }

    /** �����Է�*/
	public void pressNumberPad(String _name) {
		String jlbResult2Value = jtResult.getText();
        if(isNewValue == true)// ���ο� ���ڸ� �Է¹޾����� isNewValue�� false�� ��ü�� ������� �ٲ��ݴϴ�.
        {
            jtResult.setText(""); 
            isNewValue = false;
        }
        
        jlbResult2Value = jtResult.getText();
        if(jlbResult2Value.equals("0"))
        {
            jtResult.setText("");
            jlbResult2Value = "";
        }
        jtResult.setText(jlbResult2Value    + _name);//������ �ִ� ���� ���ε��� ���� ���� ���â�� ����ְ� �˴ϴ�.
        
        // ���� �� ���ο� value
        if(jtResult.getText().equals(".")) jtResult.setText("0.");//�Ҽ����� ������ ����ϴ� �Լ��Դϴ�.
        
        //ȸ�� �м��� ��쿡�� ������ �����մϴ�! X,Y ������ ũ�⸦ �����ϱ� ���� �۾�
        if(regressionFlag==true){
            numVar=2*Integer.parseInt(_name)+1; // �� ������ ���� ����( X����,Y���� �׸��� predict��)
            X= new Double[numVar/2]; //���� ����ְ� ���� �迭 2��. ���̰� numVar/2
            Y= new Double[numVar/2];
            regressionFlag=false;
        }
	}

	/** ��Ģ���� */
	public void inputOperate(JButton b)// �⺻ ��Ģ������ ������ �Լ��� ����ϰ� �˴ϴ�. ��ü������� calculate�Լ��� �̿��մϴ�.
    {
        String jlbResult2Value = jtResult.getText();
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        if(isNewValue == false) calculate(); //��ü���� ����� �ϰ� �˴ϴ�.
        
       
        b.setForeground(Color.white);
        selectedOperBtn = b;
        
    }
	/** ȸ�ͺм� �����ϱ�*/
    private void inputRegression(){
        String jlbResult2Value=jtResult.getText();
        if(jlbResult2Value.equals(divideErrorText)) return;
        regressionFlag=true;
        addFlag=true;
        
        jtResult.setText("��� ������ �Է��Ұǰ���?");//addVar�Լ��� ���ؼ� ������ �Է��ϰԵ˴ϴ�.
        selectedOperBtn=basicButton[22];//add��ư ����
        numCnt=0;
        }// inputRegression()
    /** ȸ�ͺм� �� �Է����ֱ� **/
    private void inputaddVar(){ //ȸ�ͺм��� �Ҷ� ������ ������ �߰����ִ� �Լ��Դϴ�.
        // jlbResult2�� textfield���� 
        String jlbResult2Value=jtResult.getText();
        
        selectedOperBtn=basicButton[22]; //selectedOperBtn �� add��ư���� �ٲ��ݴϴ�.
        isNewValue=true;
        
        //������ �����ϴ� �Լ��Դϴ� (X���Ϳ�, Y���� �׸��� ������ x���� ����)
        if(addFlag==true){
            if(numCnt<numVar/2){
                X[numCnt]=Double.valueOf(jtResult.getText());
                
            }
            else if(numCnt<numVar-1){
                Y[numCnt-numVar/2]=Double.valueOf(jtResult.getText());
                
            }
            else
                x=Double.valueOf(jtResult.getText());
                

            numCnt++; //numCnt�� ������Ű�� ���������� ���������� Ż��!

        }
        //������ �� �Է��Ͽ����� ������ ����
        if(numCnt==numVar+1){
        	selectedOperBtn=basicButton[5]; //LR��ư ����
            jtResult.setText("���͸� ������ ����� Ȯ�����ּ���!"); //enter�� ���� calculate�� �����ϰԵȴ�.
            numCnt=0;
            addFlag=false;
        }
     
    }// inputaddVar()
    
    /** �齺���̽� �Է� (������ �Է°� ����) */
    private void inputBackspace()
    {
        String jlbResult2Value = jtResult.getText();
        jtResult.setText(jlbResult2Value.substring(0, jlbResult2Value.length() - 1));//���� ���ڸ��� �����ִ°��Դϴ�.
        if(jtResult.getText().equals("")) jtResult.setText("0");
    }
    /** ���� �Է� (��� ���) */
    private void inputEnter()
    {
        String jlbResult2Value = jtResult.getText();// ���� ȭ�鿡 �ִ� �� �б�
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        calculate(); //����ϱ�
        
    }
    /** Esc �Է� (�ʱ�ȭ) */
    private void inputEscape()
    {
        jtResult.setText("0"); //0�����ʱ�ȭ�����ֱ�
        resultValue = new BigDecimal(0);
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        selectedOperBtn = null;
    }
    
    /** ����ϱ� */
    private void calculate()
    {
        
    if(selectedOperBtn==basicButton[5]){ //���� ȸ�Ͱ���̾��ٸ� �̰��� �����ϰԵ˴ϴ�.
    	LinearRegression solve = new LinearRegression(X,Y);
        resultValue=new BigDecimal(solve.predict(x));
        jtResult.setText(String.valueOf(resultValue));
        regressionFlag=false;
       return;
    }
        Double fValue = Double.valueOf(jtResult.getText()); //���� ȭ�鿡 �մ� ���� fvalue�� �����մϴ�.
        
        if(selectedOperBtn == null  )
        {
            resultValue = new BigDecimal(fValue); //reusltvalue�� fValue�� ���� ����
            isNewValue = true;
            return;
        }
        
        switch(selectedOperBtn.getText()) //���� ��ư�� �ִ� ���� �������� ������ ���̽��� �����մϴ�.
        {
            case "/"://�������ϱ�
                if(jtResult.getText().equals("0") || jtResult.getText().equals("0."))
                {
                    this.jtResult.setText(divideErrorText );
                    if(this.selectedOperBtn != null) this.selectedOperBtn.setForeground(Color.white);
                    this.selectedOperBtn = null;
                    this.isNewValue = true;
                    this.resultValue = new BigDecimal(0);
                    return;
                }
                resultValue = resultValue.divide(new BigDecimal(fValue));
                break;
                
            case "*": //�����ϱ�
                resultValue = resultValue.multiply(new BigDecimal(fValue));
                break;
                
            case "+"://�����ϱ�
                resultValue = resultValue.add(new BigDecimal(fValue));
                break;
                
            case "-"://�����ϱ�
                resultValue = resultValue.subtract(new BigDecimal(fValue));
                break;
            
        }
        Float f = resultValue.floatValue();
        Integer i = resultValue.intValue();
        if(f.equals(Float.valueOf(i))) jtResult.setText(String.valueOf(i));
        else jtResult.setText(String.valueOf(f));
        
        isNewValue = true;
    }
    
	public void createUI(){
		
		frame=new JFrame("Regression Calculator"); // Ÿ��Ʋ ��
		frame.addKeyListener(keybordListener); //Ű���� �����ʸ� �������ݴϴ�.
		frame.requestFocus();
		frame.setFocusable(true);
		frame.getContentPane().setLayout(new BorderLayout()); // ��ü ���̾ƿ��� BorderLayout
		/** ���̺� ���� */
		jpResult = new JPanel(new GridLayout(2,1));
                // jpResult��� �гο� GrideLayot�� ����, 2 x 1 �޸𸮱�� ���� Ȯ�ο�� �ؽ�Ʈâ���� �� 2���� ����
		
		jpResult.setBackground(Color.WHITE); // jpResult��� �гο� ��� ������� 
		jtResult = new textResult();	        
	    this.mLabel = new memoryLabel();
	    jpResult.add(this.mLabel); // �޸� ��� ���� jpResult�� �߰�    
		jpResult.add(jtResult); // jpResult �гο� jlbResult2 ���̺� �߰�
		//�޸� ����� ������ ���θ� ��Ÿ���� â�� ��Ÿ���ݴϴ�.
		/** ���̺� �� */

		/** ��ư ���� */
		jpButton = new JPanel(new GridLayout(6, 4, 0, 0)); 
                // jpButton��� �г�, GridLayout����, 6 x 4, ������ ���μ��� 0��
		jpButton.setBackground(Color.WHITE); // jpButton �г� ����� ���
		// basicButton�� ǥ���� ���� ����
		basicButton=new basicbutton[numStr.length];
		/** ��ư �� ���� */
		for(int j=0; j<numStr.length; j++) {
			if(j < 4) {
				basicButton[j]=new memorybutton(numStr[j]);
			}else if(j<7) {
				basicButton[j]=new operbutton(numStr[j]);
			}else if(j==22||j==23 || j==7 || j==11 || j==15 || j==19) {
				basicButton[j]=new operbutton(numStr[j]);
			}else {
				basicButton[j]=new basicbutton(numStr[j]);
			}
		}
		basicButton[4].setForeground(Color.RED);
		/** ��ư �� �� , ��ư ��*/
		frame.getContentPane().add("North", jpResult);// ���â add�ϱ�
		frame.getContentPane().add("Center", jpButton);// Ű�е� ��ư â add�ϱ�
		frame.setSize(350, 400);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(true);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.setVisible(true);
	}
    public static void main(String[] args) {
		new Calculator();
	}

}


		
	



