package calculator;
import java.awt.*;
import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;


@SuppressWarnings("serial") // Warnning 무시
public class Calculator   {
	JFrame frame =new JFrame();
	JPanel jpButton, jpResult; // 패널 초기화
	
	JTextField jlbReulst2;// 텍스트창
	JButton[] jbButton = null; // 버튼배열 초기화
	String[] numStr = { "←", "LR", "C", "/",
			"7", "8", "9", "*",
			"4", "5", "6", "-",
			"1", "2", "3", "+",
			".", "0", "add", "=" }; // 버튼에 들어갈 값
	JMenuBar mb = new JMenuBar(); //메뉴바 만들기
	private Font buttonFont; //버튼 폰트 설정
	private BigDecimal resultValue;// 결과값 변수
	private JButton selectedOperBtn; // 현재 선택된 연산 버튼
	private boolean isNewValue;//기존 표기된 내용을 지우고 새로 입력할지 여부
	private ActionListener btnClickListener;
	private KeyListener keybordListener;
	private static final String divideErrorText = "0으로 나눌 수 없습니다."; //나누기 에러메세지 
	
	/* 회귀 관련 변수*/
	private boolean regressionFlag; //회귀중인지 확인하는 변수
	private int numVar; //총 변수의 갯수
	private Double[] X; // x변수의 벡터
	private Double[] Y;// y변수의 벡터
	private Double x;// 예측할 x값
	private boolean addFlag;//더할지 말지 확인하는 변수
	private int numCnt;//현재 갯수
	

	
	
	
	// UI생성함수
	public Calculator(){ //createUI
		this.init();//리스너들을 먼저 정의해줍니다!
		
		frame=new JFrame("Regression Calculator"); // 타이틀 바
		frame.getContentPane().setLayout(new BorderLayout()); // 전체 레이아웃을 BorderLayout
		/** 레이블 시작 */
		jpResult = new JPanel(new GridLayout(1,2));
                // jpResult라는 패널에 GrideLayot을 적용, 2 x 1
		jpResult.setBackground(Color.WHITE); // jpResult라는 패널에 흰색 배경적용 
		 jlbReulst2 = new JTextField();
	        jlbReulst2.setBorder(null);
	        jlbReulst2.setText("0");
	        jlbReulst2.setHorizontalAlignment(JTextField.RIGHT);
	        jlbReulst2.setFocusable(false);
	        jlbReulst2.setEditable(true);
	        jlbReulst2.setFont(new Font("1훈핑크풍차 Regular", Font.BOLD, 30));;

		jpResult.add(jlbReulst2); // jpResult 패널에 jlbResult1 레이블 추가
		/** 레이블 끝 */

		/** 버튼 시작 */
		jpButton = new JPanel(new GridLayout(5, 4, 0, 0)); 
                // jpButton라는 패널, GridLayout적용, 5 x 4, 간격은 가로세로 2씩
		jpButton.setBackground(Color.WHITE); // jpButton 패널 배경은 흰색
		jbButton = new JButton[numStr.length]; // jbButton 버튼 배열 초기화

		// jbButton에 표기할 값들 적용
		for(int i=0; i<numStr.length; i++) {
			jbButton[i] = new JButton(numStr[i]);
			jbButton[i].setFont(new Font("1훈핑크풍차 Regular", Font.BOLD, 20));
			jpButton.add(jbButton[i]);
			jbButton[i].addActionListener(btnClickListener);
		}

		/** 버튼 색 시작 */
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
		/** 버튼 색 끝 */
		
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


	
    /** 초기화 */
    private void init() {
        this.buttonFont = new Font("Arial", 0, 16);
        this.resultValue = new BigDecimal(0);
        this.selectedOperBtn = null;
        this.isNewValue = true;
        
        // 버튼 클릭 리스너
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
                case "←":
                    inputBackspace();
                    break;

                case "√":
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
        //키보드 리스너 (기본적인 사칙연산만 구현하고 회귀분석의 경우는 버튼으로만 가능하도록 하였습니다)
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
    
    /** 숫자 입력**/
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
        
        // 적용 후 새로운 value
        if(jlbReulst2.getText().equals(".")) jlbReulst2.setText("0.");
        
        //회귀 분석의 경우에만 다음을 실행합니다! X,Y 벡터의 크기를 설정하기 위한 작업
        if(regressionFlag==true){
            numVar=2*Integer.parseInt(numStr)+1; // 총 변수의 갯수 설정( X벡터,Y벡터 그리고 predict값)
            X= new Double[numVar/2]; //내가 집어넣고 싶은 배열 2개. 길이가 numVar/2
            Y= new Double[numVar/2];
            regressionFlag=false;
        }
        
    }

    /** 연산자 입력 */
    private void inputOperate(JButton b)
    {
        String jlbResult2Value = jlbReulst2.getText();
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        if(isNewValue == false) calculate();
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        b.setForeground(Color.red);
        selectedOperBtn = b;
        
    }
    /** 회귀분석 값 입력해주기 **/
    private void inputaddVar(){
        // jlbResult2는 textfield변수 
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
            jlbReulst2.setText("엔터를 눌러서 결과를 확인해주세요!");
            numCnt=0;
            addFlag=false;
        }
     
    }// inputaddVar()

    /** 회귀분석 시작하기*/
    private void inputRegression(){
        String jlbResult2Value=jlbReulst2.getText();
        if(jlbResult2Value.equals(divideErrorText)) return;
        regressionFlag=true;
        addFlag=true;
        
        jlbReulst2.setText("몇개의 변수를 입력할건가요?");
        selectedOperBtn=jbButton[14];
        }// inputRegression()
        
    

    

    /** Square Root 계산 */
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
    
    /** 엔터 입력 (결과 계산) */
    private void inputEnter()
    {
        String jlbResult2Value = jlbReulst2.getText();
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        calculate();
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
    }
    
    /** Esc 입력 (초기화) */
    private void inputEscape()
    {
        jlbReulst2.setText("0");
        resultValue = new BigDecimal(0);
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        selectedOperBtn = null;
    }
    
    /** 백스페이스 입력 (마지막 입력값 삭제) */
    private void inputBackspace()
    {
        String jlbResult2Value = jlbReulst2.getText();
        jlbReulst2.setText(jlbResult2Value.substring(0, jlbResult2Value.length() - 1));
        if(jlbReulst2.getText().equals("")) jlbReulst2.setText("0");
    }
    
    /** 계산하기 */
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


