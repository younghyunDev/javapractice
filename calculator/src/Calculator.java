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
	
	JTextField jtResult;// 텍스트창
	private JLabel mLabel; //메모리창을 나타내기 위함
	basicbutton[] basicButton = null; // 버튼배열 초기화
	String[] numStr = { "M+","M-","MR","MC",
			"←", "LR", "C", "/",
			"7", "8", "9", "*",
			"4", "5", "6", "-",
			"1", "2", "3", "+",
			".", "0", "add", "=" }; // 버튼에 들어갈 값(순서대로 들어갈 값입니다)
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
	private KeyListener keybordListener;
	Calculator(){
		init();
		createUI();
	}
	
	/** 기본적인 버튼에 대한 클래스입니다. (숫자 키패드)*/
	class basicbutton extends JButton implements ActionListener{

		basicbutton(String name){
		super(name);
		this.setFont(new Font("1훈핑크풍차 Regular", Font.BOLD, 20));
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
	/** basicbutton을 상속하여 각 계산기능에 대한 버튼 클래스입니다*/
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
	        case "←":
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
    /** basicbutton을 상속하였으며 메모리 버튼에 대한 버튼 클래스입니다*/
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
   /** 결과 창에 대한 클래스입니다*/
	class textResult extends JTextField{
    	textResult(){
    	this.setBorder(null);
        this.setText("0");
        this.setHorizontalAlignment(JTextField.RIGHT);
        this.setFocusable(false);
        this.setEditable(true);
        this.setFont(new Font("1훈핑크풍차 Regular", Font.BOLD, 30));
    	}
    }
   /** 메로리 기능 표시에 대한 클래스입니다*/
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
        this.memoryValue = new BigDecimal(0); // 메모리 기능을 활용하기 위한 변수입니다.
        
        keybordListener=new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                Integer code = e.getKeyCode();
                if (code >= 48 && code <= 57)
                    pressNumberPad(String.valueOf(e.getKeyChar()));
                else if (code == 43) {
                    inputOperate(basicButton[19]);//더하기
                	}
                else if (code == 45)
                    inputOperate(basicButton[15]);//빼기
                else if (code == 42)
                    inputOperate(basicButton[11]);//곱하기
                else if (code == 47)
                    inputOperate(basicButton[7]);// 나누기
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

    /** 숫자입력*/
	public void pressNumberPad(String _name) {
		String jlbResult2Value = jtResult.getText();
        if(isNewValue == true)// 새로운 숫자를 입력받았을때 isNewValue를 false로 교체후 결과값을 바꿔줍니다.
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
        jtResult.setText(jlbResult2Value    + _name);//기존에 있던 수와 새로들어온 수를 같이 결과창에 띄어주게 됩니다.
        
        // 적용 후 새로운 value
        if(jtResult.getText().equals(".")) jtResult.setText("0.");//소수점이 있을때 사용하는 함수입니다.
        
        //회귀 분석의 경우에만 다음을 실행합니다! X,Y 벡터의 크기를 설정하기 위한 작업
        if(regressionFlag==true){
            numVar=2*Integer.parseInt(_name)+1; // 총 변수의 갯수 설정( X벡터,Y벡터 그리고 predict값)
            X= new Double[numVar/2]; //내가 집어넣고 싶은 배열 2개. 길이가 numVar/2
            Y= new Double[numVar/2];
            regressionFlag=false;
        }
	}

	/** 사칙연산 */
	public void inputOperate(JButton b)// 기본 사칙연산은 다음의 함수를 사용하게 됩니다. 구체적계산은 calculate함수를 이용합니다.
    {
        String jlbResult2Value = jtResult.getText();
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        if(isNewValue == false) calculate(); //구체적인 계산을 하게 됩니다.
        
       
        b.setForeground(Color.white);
        selectedOperBtn = b;
        
    }
	/** 회귀분석 시작하기*/
    private void inputRegression(){
        String jlbResult2Value=jtResult.getText();
        if(jlbResult2Value.equals(divideErrorText)) return;
        regressionFlag=true;
        addFlag=true;
        
        jtResult.setText("몇개의 변수를 입력할건가요?");//addVar함수를 통해서 변수를 입력하게됩니다.
        selectedOperBtn=basicButton[22];//add버튼 적용
        numCnt=0;
        }// inputRegression()
    /** 회귀분석 값 입력해주기 **/
    private void inputaddVar(){ //회귀분석을 할때 변수의 갯수를 추가해주는 함수입니다.
        // jlbResult2는 textfield변수 
        String jlbResult2Value=jtResult.getText();
        
        selectedOperBtn=basicButton[22]; //selectedOperBtn 은 add버튼으로 바꿔줍니다.
        isNewValue=true;
        
        //변수에 저장하는 함수입니다 (X벡터와, Y벡터 그리고 예측할 x값에 저장)
        if(addFlag==true){
            if(numCnt<numVar/2){
                X[numCnt]=Double.valueOf(jtResult.getText());
                
            }
            else if(numCnt<numVar-1){
                Y[numCnt-numVar/2]=Double.valueOf(jtResult.getText());
                
            }
            else
                x=Double.valueOf(jtResult.getText());
                

            numCnt++; //numCnt를 증가시키고 변수갯수와 동일해지면 탈출!

        }
        //변수를 다 입력하였을때 다음을 실행
        if(numCnt==numVar+1){
        	selectedOperBtn=basicButton[5]; //LR버튼 적용
            jtResult.setText("엔터를 눌러서 결과를 확인해주세요!"); //enter를 눌러 calculate를 진행하게된다.
            numCnt=0;
            addFlag=false;
        }
     
    }// inputaddVar()
    
    /** 백스페이스 입력 (마지막 입력값 삭제) */
    private void inputBackspace()
    {
        String jlbResult2Value = jtResult.getText();
        jtResult.setText(jlbResult2Value.substring(0, jlbResult2Value.length() - 1));//값을 한자리씩 지워주는것입니다.
        if(jtResult.getText().equals("")) jtResult.setText("0");
    }
    /** 엔터 입력 (결과 계산) */
    private void inputEnter()
    {
        String jlbResult2Value = jtResult.getText();// 현재 화면에 있는 값 읽기
        
        if(jlbResult2Value.equals(divideErrorText)) return;
        
        calculate(); //계산하기
        
    }
    /** Esc 입력 (초기화) */
    private void inputEscape()
    {
        jtResult.setText("0"); //0을로초기화시켜주기
        resultValue = new BigDecimal(0);
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.white);
        selectedOperBtn = null;
    }
    
    /** 계산하기 */
    private void calculate()
    {
        
    if(selectedOperBtn==basicButton[5]){ //만약 회귀계산이었다면 이것을 수행하게됩니다.
    	LinearRegression solve = new LinearRegression(X,Y);
        resultValue=new BigDecimal(solve.predict(x));
        jtResult.setText(String.valueOf(resultValue));
        regressionFlag=false;
       return;
    }
        Double fValue = Double.valueOf(jtResult.getText()); //현재 화면에 잇는 값을 fvalue로 설정합니다.
        
        if(selectedOperBtn == null  )
        {
            resultValue = new BigDecimal(fValue); //reusltvalue를 fValue로 생성 생성
            isNewValue = true;
            return;
        }
        
        switch(selectedOperBtn.getText()) //현재 버튼에 있는 값을 기준으로 다음의 케이스를 진행합니다.
        {
            case "/"://나눗셈하기
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
        if(f.equals(Float.valueOf(i))) jtResult.setText(String.valueOf(i));
        else jtResult.setText(String.valueOf(f));
        
        isNewValue = true;
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
		jtResult = new textResult();	        
	    this.mLabel = new memoryLabel();
	    jpResult.add(this.mLabel); // 메모리 기능 정보 jpResult에 추가    
		jpResult.add(jtResult); // jpResult 패널에 jlbResult2 레이블 추가
		//메모리 기능을 쓰는지 여부를 나타내는 창을 나타내줍니다.
		/** 레이블 끝 */

		/** 버튼 시작 */
		jpButton = new JPanel(new GridLayout(6, 4, 0, 0)); 
                // jpButton라는 패널, GridLayout적용, 6 x 4, 간격은 가로세로 0씩
		jpButton.setBackground(Color.WHITE); // jpButton 패널 배경은 흰색
		// basicButton에 표기할 값들 적용
		basicButton=new basicbutton[numStr.length];
		/** 버튼 색 시작 */
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
		/** 버튼 색 끝 , 버튼 끝*/
		frame.getContentPane().add("North", jpResult);// 결과창 add하기
		frame.getContentPane().add("Center", jpButton);// 키패드 버튼 창 add하기
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


		
	



