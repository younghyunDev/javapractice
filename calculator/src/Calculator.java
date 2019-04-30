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
	/**계산기 GUI 요소**/
	JFrame frame =new JFrame();
	JPanel jpButton, jpResult; // 패널 초기화
	
	JTextField jlbReulst2;// 텍스트창
	private JLabel mLabel; //메모리창을 나타내기 위함
	JButton[] jbButton = null; // 버튼배열 초기화
	String[] numStr = { "M+","M-","MR","MC",
			"←", "LR", "C", "/",
			"7", "8", "9", "*",
			"4", "5", "6", "-",
			"1", "2", "3", "+",
			".", "0", "add", "=" }; // 버튼에 들어갈 값(순서대로 들어갈 값입니다)
	private Font buttonFont; //버튼 폰트 설정
	private ActionListener btnClickListener; //버튼을 눌렀을 때 리스너
	private KeyListener keybordListener;// 키보드를 입력하였을때 리스너
	
	/** 계산기 관련 변수 설정*/
	private BigDecimal resultValue;// 결과값 변수
	private JButton selectedOperBtn; // 현재 선택된 연산 버튼
	private boolean isNewValue;//기존 표기된 내용을 지우고 새로 입력할지 여부
	private static final String divideErrorText = "0으로 나눌 수 없습니다."; //나누기 에러메세지 
	private BigDecimal memoryValue; //메모리기능을 사용하기 위한  변수
	
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
		this.createUI(); // GUI요소들을 설정해줍니다.
	}
	 /** 초기화 */
    private void init() {
        this.buttonFont = new Font("Arial", 0, 16);
        this.resultValue = new BigDecimal(0);
        this.selectedOperBtn = null;
        this.isNewValue = true;
        this.memoryValue = new BigDecimal(0); // 메모리 기능을 활용하기 위한 변수입니다.
        // 버튼 클릭 리스너에 대한 정의
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
                case "←":
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
        //키보드 리스너 (기본적인 사칙연산만 구현하고 회귀분석의 경우는 버튼으로만 가능하도록 하였습니다)
        this.keybordListener=new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                Integer code = e.getKeyCode();
                if (code >= 48 && code <= 57)
                    pressNumberPad(String.valueOf(e.getKeyChar()));
                else if (code == 43) {
                    inputOperate(jbButton[19]);//더하기
                	}
                else if (code == 45)
                    inputOperate(jbButton[15]);//빼기
                else if (code == 42)
                    inputOperate(jbButton[11]);//곱하기
                else if (code == 47)
                    inputOperate(jbButton[7]);// 나누기
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
		
		frame=new JFrame("Regression Calculator"); // 타이틀 바
		frame.addKeyListener(keybordListener); //키보드 리스너를 연결해줍니다.
		frame.requestFocus();
		frame.setFocusable(true);
		frame.getContentPane().setLayout(new BorderLayout()); // 전체 레이아웃을 BorderLayout
		/** 레이블 시작 */
		jpResult = new JPanel(new GridLayout(2,1));
                // jpResult라는 패널에 GrideLayot을 적용, 2 x 1 메모리기능 상태 확인용과 텍스트창으로 총 2개를 만듬
		
		jpResult.setBackground(Color.WHITE); // jpResult라는 패널에 흰색 배경적용 
		 jlbReulst2 = new JTextField();
	        jlbReulst2.setBorder(null);
	        jlbReulst2.setText("0");
	        jlbReulst2.setHorizontalAlignment(JTextField.RIGHT);
	        jlbReulst2.setFocusable(false);
	        jlbReulst2.setEditable(true);
	        jlbReulst2.setFont(new Font("1훈핑크풍차 Regular", Font.BOLD, 30));;
	        this.mLabel = new JLabel();
			this.mLabel.setText("M");
			this.mLabel.setBounds(5, 12, 20, 20);
			this.mLabel.setVisible(false);
	    jpResult.add(this.mLabel); // 메모리 기능 정보 jpResult에 추가    
		jpResult.add(jlbReulst2); // jpResult 패널에 jlbResult2 레이블 추가
		//메모리 기능을 쓰는지 여부를 나타내는 창을 나타내줍니다.
		
		
		/** 레이블 끝 */

		/** 버튼 시작 */
		jpButton = new JPanel(new GridLayout(6, 4, 0, 0)); 
                // jpButton라는 패널, GridLayout적용, 6 x 4, 간격은 가로세로 0씩
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
		/** 버튼 색 끝 , 버튼 끝*/
		
		
		frame.getContentPane().add("North", jpResult);// 결과창 add하기
		frame.getContentPane().add("Center", jpButton);// 키패드 버튼 창 add하기
		frame.setSize(350, 400);
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setResizable(true);
	
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		frame.setVisible(true);
	}

	
   
    /** 숫자 입력**/
    private void pressNumberPad(String numStr) //숫자를 입력받았을때 사용하는 함수입니다.
    {   String jlbResult2Value = jlbReulst2.getText();
        if(isNewValue == true)// 새로운 숫자를 입력받았을때 isNewValue를 false로 교체후 결과값을 바꿔줍니다.
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
        jlbReulst2.setText(jlbResult2Value    + numStr);//기존에 있던 수와 새로들어온 수를 같이 결과창에 띄어주게 됩니다.
        
        // 적용 후 새로운 value
        if(jlbReulst2.getText().equals(".")) jlbReulst2.setText("0.");//소수점이 있을때 사용하는 함수입니다.
        
        //회귀 분석의 경우에만 다음을 실행합니다! X,Y 벡터의 크기를 설정하기 위한 작업
        if(regressionFlag==true){
            numVar=2*Integer.parseInt(numStr)+1; // 총 변수의 갯수 설정( X벡터,Y벡터 그리고 predict값)
            X= new Double[numVar/2]; //내가 집어넣고 싶은 배열 2개. 길이가 numVar/2
            Y= new Double[numVar/2];
            regressionFlag=false;
        }
        
    }

    /** 연산자 입력 */
    private void inputOperate(JButton b)// 기본 사칙연산은 다음의 함수를 사용하게 됩니다. 구체적계산은 calculate함수를 이용합니다.
    {
        String jlbResult2Value = jlbReulst2.getText();
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        if(isNewValue == false) calculate(); //구체적인 계산을 하게 됩니다.
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        b.setForeground(Color.red);
        selectedOperBtn = b;
        
    }
    /** 회귀분석 값 입력해주기 **/
    private void inputaddVar(){ //회귀분석을 할때 변수의 갯수를 추가해주는 함수입니다.
        // jlbResult2는 textfield변수 
        String jlbResult2Value=jlbReulst2.getText();
        
        selectedOperBtn=jbButton[22]; //selectedOperBtn 은 add버튼으로 바꿔줍니다.
        isNewValue=true;
        
        //변수에 저장하는 함수입니다 (X벡터와, Y벡터 그리고 예측할 x값에 저장)
        if(addFlag==true){
            if(numCnt<numVar/2){
                X[numCnt]=Double.valueOf(jlbReulst2.getText());
                
            }
            else if(numCnt<numVar-1){
                Y[numCnt-numVar/2]=Double.valueOf(jlbReulst2.getText());
                
            }
            else
                x=Double.valueOf(jlbReulst2.getText());
                

            numCnt++; //numCnt를 증가시키고 변수갯수와 동일해지면 탈출!

        }
        //변수를 다 입력하였을때 다음을 실행
        if(numCnt==numVar+1){
        	selectedOperBtn=jbButton[5]; //LR버튼 적용
            jlbReulst2.setText("엔터를 눌러서 결과를 확인해주세요!"); //enter를 눌러 calculate를 진행하게된다.
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
        
        jlbReulst2.setText("몇개의 변수를 입력할건가요?");//addVar함수를 통해서 변수를 입력하게됩니다.
        selectedOperBtn=jbButton[22];//add버튼 적용
        }// inputRegression()
          
    /** 엔터 입력 (결과 계산) */
    private void inputEnter()
    {
        String jlbResult2Value = jlbReulst2.getText();// 현재 화면에 있는 값 읽기
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        calculate(); //계산하기
        
    }
    
    /** Esc 입력 (초기화) */
    private void inputEscape()
    {
        jlbReulst2.setText("0"); //0을로초기화시켜주기
        resultValue = new BigDecimal(0);
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        selectedOperBtn = null;
    }
    
    /** 백스페이스 입력 (마지막 입력값 삭제) */
    private void inputBackspace()
    {
        String jlbResult2Value = jlbReulst2.getText();
        jlbReulst2.setText(jlbResult2Value.substring(0, jlbResult2Value.length() - 1));//값을 한자리씩 지워주는것입니다.
        if(jlbReulst2.getText().equals("")) jlbReulst2.setText("0");
    }
    
    /** 계산하기 */
    private void calculate()
    {
        
    if(selectedOperBtn==jbButton[5]){ //만약 회귀계산이었다면 이것을 수행하게됩니다.
    	LinearRegression solve = new LinearRegression(X,Y);
        resultValue=new BigDecimal(solve.predict(x));
        jlbReulst2.setText(String.valueOf(resultValue));
        regressionFlag=false;
       return;
    }
        Double fValue = Double.valueOf(jlbReulst2.getText()); //현재 화면에 잇는 값을 fvalue로 설정합니다.
        
        if(selectedOperBtn == null  )
        {
            resultValue = new BigDecimal(fValue); //reusltvalue를 fValue로 생성 생성
            isNewValue = true;
            return;
        }
        
        switch(selectedOperBtn.getText()) //현재 버튼에 있는 값을 기준으로 다음의 케이스를 진행합니다.
        {
            case "/"://나눗셈하기
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
                
            case "*": //곱셈하기
                resultValue = resultValue.multiply(new BigDecimal(fValue));
                break;
                
            case "+"://덧셈하기
                resultValue = resultValue.add(new BigDecimal(fValue));
                break;
                
            case "-"://뺄셈하기
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


