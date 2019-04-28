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
	JFrame frame =new JFrame();
	JPanel jpButton, jpResult; // �г� �ʱ�ȭ
	
	JTextField jlbReulst2;// �ؽ�Ʈâ
	JButton[] jbButton = null; // ��ư�迭 �ʱ�ȭ
	String[] numStr = { "��", "LR", "C", "/",
			"7", "8", "9", "*",
			"4", "5", "6", "-",
			"1", "2", "3", "+",
			".", "0", "add", "=" }; // ��ư�� �� ��
	JMenuBar mb = new JMenuBar(); //�޴��� �����
	private Font buttonFont; //��ư ��Ʈ ����
	private BigDecimal resultValue;// ����� ����
	private JButton selectedOperBtn; // ���� ���õ� ���� ��ư
	private boolean isNewValue;//���� ǥ��� ������ ����� ���� �Է����� ����
	private ActionListener btnClickListener;
	private KeyListener keybordListener;
	private static final String divideErrorText = "0���� ���� �� �����ϴ�."; //������ �����޼��� 
	
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
		
		frame=new JFrame("Regression Calculator"); // Ÿ��Ʋ ��
		frame.getContentPane().setLayout(new BorderLayout()); // ��ü ���̾ƿ��� BorderLayout
		/** ���̺� ���� */
		jpResult = new JPanel(new GridLayout(1,2));
                // jpResult��� �гο� GrideLayot�� ����, 2 x 1
		jpResult.setBackground(Color.WHITE); // jpResult��� �гο� ��� ������� 
		 jlbReulst2 = new JTextField();
	        jlbReulst2.setBorder(null);
	        jlbReulst2.setText("0");
	        jlbReulst2.setHorizontalAlignment(JTextField.RIGHT);
	        jlbReulst2.setFocusable(false);
	        jlbReulst2.setEditable(true);
	        jlbReulst2.setFont(new Font("1����ũǳ�� Regular", Font.BOLD, 30));;

		jpResult.add(jlbReulst2); // jpResult �гο� jlbResult1 ���̺� �߰�
		/** ���̺� �� */

		/** ��ư ���� */
		jpButton = new JPanel(new GridLayout(5, 4, 0, 0)); 
                // jpButton��� �г�, GridLayout����, 5 x 4, ������ ���μ��� 2��
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
			if(j < 3) {
				jbButton[j].setBackground(new Color(142,166,166));
			}else if(j==3 || j==7 || j==11 || j==15 || j==19) {
				jbButton[j].setForeground(Color.white);
				jbButton[j].setBackground(new Color(88,97,116));
			}else {
				jbButton[j].setBackground(new Color(240,231,216));
			}
		}
		jbButton[0].setForeground(Color.RED);
		/** ��ư �� �� */
		
		frame.getContentPane().add("North", jpResult);
		frame.getContentPane().add("Center", jpButton);
		frame.setSize(350, 400);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(true);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.addKeyListener(keybordListener);
		frame.requestFocus();
		frame.setFocusable(true);
		frame.setVisible(true);
	}


	
    /** �ʱ�ȭ */
    private void init() {
        this.buttonFont = new Font("Arial", 0, 16);
        this.resultValue = new BigDecimal(0);
        this.selectedOperBtn = null;
        this.isNewValue = true;
        
        // ��ư Ŭ�� ������
        this.btnClickListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                JButton b = (JButton) e.getSource();
                String _name = b.getText();
                switch (_name) {
                
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

                case "��":
                    calculateSquareRoot();
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
                    inputOperate(jbButton[15]);
                    System.out.println("43");
                	}
                else if (code == 45)
                    inputOperate(jbButton[11]);
                else if (code == 42)
                    inputOperate(jbButton[7]);
                else if (code == 47)
                    inputOperate(jbButton[3]);
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
    
    /** ���� �Է�**/
    private void pressNumberPad(String numStr)
    {   String jlbResult2Value = jlbReulst2.getText();
        if(isNewValue == true)
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
        jlbReulst2.setText(jlbResult2Value    + numStr);
        
        // ���� �� ���ο� value
        if(jlbReulst2.getText().equals(".")) jlbReulst2.setText("0.");
        
        //ȸ�� �м��� ��쿡�� ������ �����մϴ�! X,Y ������ ũ�⸦ �����ϱ� ���� �۾�
        if(regressionFlag==true){
            numVar=2*Integer.parseInt(numStr)+1; // �� ������ ���� ����( X����,Y���� �׸��� predict��)
            X= new Double[numVar/2]; //���� ����ְ� ���� �迭 2��. ���̰� numVar/2
            Y= new Double[numVar/2];
            regressionFlag=false;
        }
        
    }

    /** ������ �Է� */
    private void inputOperate(JButton b)
    {
        String jlbResult2Value = jlbReulst2.getText();
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        if(isNewValue == false) calculate();
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        b.setForeground(Color.red);
        selectedOperBtn = b;
        
    }
    /** ȸ�ͺм� �� �Է����ֱ� **/
    private void inputaddVar(){
        // jlbResult2�� textfield���� 
        String jlbResult2Value=jlbReulst2.getText();
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        
        
        selectedOperBtn=jbButton[13];
        isNewValue=true;
        
        if(addFlag==true){
            if(numCnt<numVar/2){
                X[numCnt]=Double.valueOf(jlbReulst2.getText());
                
            }
            else if(numCnt<numVar-1){
                Y[numCnt-numVar/2]=Double.valueOf(jlbReulst2.getText());
                
            }
            else
                x=Double.valueOf(jlbReulst2.getText());
                

            numCnt++; 

        }
        if(numCnt==numVar+1){
        	selectedOperBtn=jbButton[14];
            jlbReulst2.setText("���͸� ������ ����� Ȯ�����ּ���!");
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
        
        jlbReulst2.setText("��� ������ �Է��Ұǰ���?");
        selectedOperBtn=jbButton[14];
        }// inputRegression()
        
    

    

    /** Square Root ��� */
    private void calculateSquareRoot()
    {
        Float fValue = Float.valueOf(jlbReulst2.getText());
        
        BigDecimal tmp = new BigDecimal(Math.sqrt(fValue));
        
        Float f = tmp.floatValue();
        Integer i = tmp.intValue();
        if(f.equals(Float.valueOf(i))) jlbReulst2.setText(String.valueOf(i));
        else jlbReulst2.setText(String.valueOf(f));
        
        this.isNewValue = true;
    }
    
    /** ���� �Է� (��� ���) */
    private void inputEnter()
    {
        String jlbResult2Value = jlbReulst2.getText();
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        calculate();
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
    }
    
    /** Esc �Է� (�ʱ�ȭ) */
    private void inputEscape()
    {
        jlbReulst2.setText("0");
        resultValue = new BigDecimal(0);
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        selectedOperBtn = null;
    }
    
    /** �齺���̽� �Է� (������ �Է°� ����) */
    private void inputBackspace()
    {
        String jlbResult2Value = jlbReulst2.getText();
        jlbReulst2.setText(jlbResult2Value.substring(0, jlbResult2Value.length() - 1));
        if(jlbReulst2.getText().equals("")) jlbReulst2.setText("0");
    }
    
    /** ����ϱ� */
    private void calculate()
    {
        
    if(selectedOperBtn==jbButton[14]){
    	LinearRegression solve = new LinearRegression(X,Y);
        resultValue=new BigDecimal(solve.predict(x));
        jlbReulst2.setText(String.valueOf(resultValue));
        regressionFlag=false;
       return;
    }
        Double fValue = Double.valueOf(jlbReulst2.getText());
        
        if(selectedOperBtn == null  )
        {
            resultValue = new BigDecimal(fValue);
            isNewValue = true;
            return;
        }
        
        switch(selectedOperBtn.getText())
        {
            case "/":
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
                
            case "*":
                resultValue = resultValue.multiply(new BigDecimal(fValue));
                break;
                
            case "+":
                resultValue = resultValue.add(new BigDecimal(fValue));
                break;
                
            case "-":
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


