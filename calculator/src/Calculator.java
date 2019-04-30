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
	
	JTextField jlbReulst2;// �ؽ�Ʈâ
	private JLabel mLabel; //�޸�â�� ��Ÿ���� ����
	JButton[] jbButton = null; // ��ư�迭 �ʱ�ȭ
	String[] numStr = { "M+","M-","MR","MC",
			"��", "LR", "C", "/",
			"7", "8", "9", "*",
			"4", "5", "6", "-",
			"1", "2", "3", "+",
			".", "0", "add", "=" }; // ��ư�� �� ��(������� �� ���Դϴ�)
	private Font buttonFont; //��ư ��Ʈ ����
	private ActionListener btnClickListener; //��ư�� ������ �� ������
	private KeyListener keybordListener;// Ű���带 �Է��Ͽ����� ������
	
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
	
	// UI�����Լ�
	public Calculator(){ //createUI
		this.init();//�����ʵ��� ���� �������ݴϴ�!
		this.createUI(); // GUI��ҵ��� �������ݴϴ�.
	}
	 /** �ʱ�ȭ */
    private void init() {
        this.buttonFont = new Font("Arial", 0, 16);
        this.resultValue = new BigDecimal(0);
        this.selectedOperBtn = null;
        this.isNewValue = true;
        this.memoryValue = new BigDecimal(0); // �޸� ����� Ȱ���ϱ� ���� �����Դϴ�.
        // ��ư Ŭ�� �����ʿ� ���� ����
        this.btnClickListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String txtValue = jlbReulst2.getText();
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
                        jlbReulst2.setText(String.valueOf(i));
                    else
                        jlbReulst2.setText(String.valueOf(f));
                    isNewValue = true;
                    break;

                case "MC":
                    mLabel.setVisible(false);
                    memoryValue = new BigDecimal(0);
                    isNewValue = true;
                    break;
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

                default:
                    pressNumberPad(_name);
                    break;
                }
            }
        };
        //Ű���� ������ (�⺻���� ��Ģ���길 �����ϰ� ȸ�ͺм��� ���� ��ư���θ� �����ϵ��� �Ͽ����ϴ�)
        this.keybordListener=new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                Integer code = e.getKeyCode();
                if (code >= 48 && code <= 57)
                    pressNumberPad(String.valueOf(e.getKeyChar()));
                else if (code == 43) {
                    inputOperate(jbButton[19]);//���ϱ�
                	}
                else if (code == 45)
                    inputOperate(jbButton[15]);//����
                else if (code == 42)
                    inputOperate(jbButton[11]);//���ϱ�
                else if (code == 47)
                    inputOperate(jbButton[7]);// ������
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
		 jlbReulst2 = new JTextField();
	        jlbReulst2.setBorder(null);
	        jlbReulst2.setText("0");
	        jlbReulst2.setHorizontalAlignment(JTextField.RIGHT);
	        jlbReulst2.setFocusable(false);
	        jlbReulst2.setEditable(true);
	        jlbReulst2.setFont(new Font("1����ũǳ�� Regular", Font.BOLD, 30));;
	        this.mLabel = new JLabel();
			this.mLabel.setText("M");
			this.mLabel.setBounds(5, 12, 20, 20);
			this.mLabel.setVisible(false);
	    jpResult.add(this.mLabel); // �޸� ��� ���� jpResult�� �߰�    
		jpResult.add(jlbReulst2); // jpResult �гο� jlbResult2 ���̺� �߰�
		//�޸� ����� ������ ���θ� ��Ÿ���� â�� ��Ÿ���ݴϴ�.
		
		
		/** ���̺� �� */

		/** ��ư ���� */
		jpButton = new JPanel(new GridLayout(6, 4, 0, 0)); 
                // jpButton��� �г�, GridLayout����, 6 x 4, ������ ���μ��� 0��
		jpButton.setBackground(Color.WHITE); // jpButton �г� ����� ���
		jbButton = new JButton[numStr.length]; // jbButton ��ư �迭 �ʱ�ȭ

		// jbButton�� ǥ���� ���� ����
		for(int i=0; i<numStr.length; i++) {
			jbButton[i] = new JButton(numStr[i]);
			jbButton[i].setFont(new Font("1����ũǳ�� Regular", Font.BOLD, 20));
			jpButton.add(jbButton[i]);
			jbButton[i].addActionListener(btnClickListener);
		}

		/** ��ư �� ���� */
		for(int j=0; j<numStr.length; j++) {
			if(j < 4) {
				jbButton[j].setBackground(new Color(157,200,200));
				jbButton[j].setForeground(Color.white);
			}else if(j<7) {
				jbButton[j].setBackground(new Color(142,166,166));
			}else if(j==23 || j==7 || j==11 || j==15 || j==19) {
				jbButton[j].setForeground(Color.white);
				jbButton[j].setBackground(new Color(81,157,158));
			}else {
				jbButton[j].setBackground(new Color(240,231,216));
			}
		}
		jbButton[4].setForeground(Color.RED);
		/** ��ư �� �� , ��ư ��*/
		
		
		frame.getContentPane().add("North", jpResult);// ���â add�ϱ�
		frame.getContentPane().add("Center", jpButton);// Ű�е� ��ư â add�ϱ�
		frame.setSize(350, 400);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(true);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.setVisible(true);
	}

	
   
    /** ���� �Է�**/
    private void pressNumberPad(String numStr) //���ڸ� �Է¹޾����� ����ϴ� �Լ��Դϴ�.
    {   String jlbResult2Value = jlbReulst2.getText();
        if(isNewValue == true)// ���ο� ���ڸ� �Է¹޾����� isNewValue�� false�� ��ü�� ������� �ٲ��ݴϴ�.
        {
            
            jlbReulst2.setText(""); 
            isNewValue = false;
        }
        
        jlbResult2Value = jlbReulst2.getText();
        if(jlbResult2Value.equals("0"))
        {
            jlbReulst2.setText("");
            jlbResult2Value = "";
        }
        jlbReulst2.setText(jlbResult2Value    + numStr);//������ �ִ� ���� ���ε��� ���� ���� ���â�� ����ְ� �˴ϴ�.
        
        // ���� �� ���ο� value
        if(jlbReulst2.getText().equals(".")) jlbReulst2.setText("0.");//�Ҽ����� ������ ����ϴ� �Լ��Դϴ�.
        
        //ȸ�� �м��� ��쿡�� ������ �����մϴ�! X,Y ������ ũ�⸦ �����ϱ� ���� �۾�
        if(regressionFlag==true){
            numVar=2*Integer.parseInt(numStr)+1; // �� ������ ���� ����( X����,Y���� �׸��� predict��)
            X= new Double[numVar/2]; //���� ����ְ� ���� �迭 2��. ���̰� numVar/2
            Y= new Double[numVar/2];
            regressionFlag=false;
        }
        
    }

    /** ������ �Է� */
    private void inputOperate(JButton b)// �⺻ ��Ģ������ ������ �Լ��� ����ϰ� �˴ϴ�. ��ü������� calculate�Լ��� �̿��մϴ�.
    {
        String jlbResult2Value = jlbReulst2.getText();
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        if(isNewValue == false) calculate(); //��ü���� ����� �ϰ� �˴ϴ�.
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        b.setForeground(Color.red);
        selectedOperBtn = b;
        
    }
    /** ȸ�ͺм� �� �Է����ֱ� **/
    private void inputaddVar(){ //ȸ�ͺм��� �Ҷ� ������ ������ �߰����ִ� �Լ��Դϴ�.
        // jlbResult2�� textfield���� 
        String jlbResult2Value=jlbReulst2.getText();
        
        selectedOperBtn=jbButton[22]; //selectedOperBtn �� add��ư���� �ٲ��ݴϴ�.
        isNewValue=true;
        
        //������ �����ϴ� �Լ��Դϴ� (X���Ϳ�, Y���� �׸��� ������ x���� ����)
        if(addFlag==true){
            if(numCnt<numVar/2){
                X[numCnt]=Double.valueOf(jlbReulst2.getText());
                
            }
            else if(numCnt<numVar-1){
                Y[numCnt-numVar/2]=Double.valueOf(jlbReulst2.getText());
                
            }
            else
                x=Double.valueOf(jlbReulst2.getText());
                

            numCnt++; //numCnt�� ������Ű�� ���������� ���������� Ż��!

        }
        //������ �� �Է��Ͽ����� ������ ����
        if(numCnt==numVar+1){
        	selectedOperBtn=jbButton[5]; //LR��ư ����
            jlbReulst2.setText("���͸� ������ ����� Ȯ�����ּ���!"); //enter�� ���� calculate�� �����ϰԵȴ�.
            numCnt=0;
            addFlag=false;
        }
     
    }// inputaddVar()

    /** ȸ�ͺм� �����ϱ�*/
    private void inputRegression(){
        String jlbResult2Value=jlbReulst2.getText();
        if(jlbResult2Value.equals(divideErrorText)) return;
        regressionFlag=true;
        addFlag=true;
        
        jlbReulst2.setText("��� ������ �Է��Ұǰ���?");//addVar�Լ��� ���ؼ� ������ �Է��ϰԵ˴ϴ�.
        selectedOperBtn=jbButton[22];//add��ư ����
        }// inputRegression()
          
    /** ���� �Է� (��� ���) */
    private void inputEnter()
    {
        String jlbResult2Value = jlbReulst2.getText();// ���� ȭ�鿡 �ִ� �� �б�
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        calculate(); //����ϱ�
        
    }
    
    /** Esc �Է� (�ʱ�ȭ) */
    private void inputEscape()
    {
        jlbReulst2.setText("0"); //0�����ʱ�ȭ�����ֱ�
        resultValue = new BigDecimal(0);
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        selectedOperBtn = null;
    }
    
    /** �齺���̽� �Է� (������ �Է°� ����) */
    private void inputBackspace()
    {
        String jlbResult2Value = jlbReulst2.getText();
        jlbReulst2.setText(jlbResult2Value.substring(0, jlbResult2Value.length() - 1));//���� ���ڸ��� �����ִ°��Դϴ�.
        if(jlbReulst2.getText().equals("")) jlbReulst2.setText("0");
    }
    
    /** ����ϱ� */
    private void calculate()
    {
        
    if(selectedOperBtn==jbButton[5]){ //���� ȸ�Ͱ���̾��ٸ� �̰��� �����ϰԵ˴ϴ�.
    	LinearRegression solve = new LinearRegression(X,Y);
        resultValue=new BigDecimal(solve.predict(x));
        jlbReulst2.setText(String.valueOf(resultValue));
        regressionFlag=false;
       return;
    }
        Double fValue = Double.valueOf(jlbReulst2.getText()); //���� ȭ�鿡 �մ� ���� fvalue�� �����մϴ�.
        
        if(selectedOperBtn == null  )
        {
            resultValue = new BigDecimal(fValue); //reusltvalue�� fValue�� ���� ����
            isNewValue = true;
            return;
        }
        
        switch(selectedOperBtn.getText()) //���� ��ư�� �ִ� ���� �������� ������ ���̽��� �����մϴ�.
        {
            case "/"://�������ϱ�
                if(jlbReulst2.getText().equals("0") || jlbReulst2.getText().equals("0."))
                {
                    this.jlbReulst2.setText(this.divideErrorText );
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
        if(f.equals(Float.valueOf(i))) jlbReulst2.setText(String.valueOf(i));
        else jlbReulst2.setText(String.valueOf(f));
        
        isNewValue = true;
    }
    
	public static void main(String[] args) {
		new Calculator();
	}
 
	
}


