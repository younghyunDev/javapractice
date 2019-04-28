
package calculator;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.swing.*;

public class Calculator {
    private JFrame frame;
    private ActionListener btnClickListener;

    /** 결과값 변수 */
    private BigDecimal resultValue;

    /** 현재 입력된 값을 저장해 놓는 변수 */
    private BigDecimal memoryValue;

    /** 기존 표기된 내용을 지우고 새로 입력할지 여부 */
    private Boolean isNewValue;

    /** 현재 선택된 연산 버튼 */
    private JButton selectedOperBtn;

    // 회귀 변수 갯수
    private int numVar = 0;
    private int numCnt = 0;
    private double[] X;
    private double[] Y;
    private double x;
    private static final String divideErrorText = "0으로 나눌 수 없습니다.";

    private Font buttonFont;
    private Container container;
    private JTextField txt;
    private JLabel mLabel;
    private JButton plusBtn;
    private JButton minusBtn;
    private JButton multBtn;
    private JButton divBtn;
    private JButton enterBtn;
    private JButton LinearRegression;
    private JButton addVar;
    private boolean regressionFlag = false;
    private boolean addFlag = false;
    // =============================
    //
    // Start Point
    //
    // =============================

    public void create() {
        this.init();
        this.createUI();
        this.setEventListener();
    }

    // =============================
    //
    // Private Methods
    //
    // =============================

    /** 초기화 */
    private void init() {
        this.buttonFont = new Font("Arial", 0, 16);
        this.resultValue = new BigDecimal(0);
        this.memoryValue = new BigDecimal(0);
        this.selectedOperBtn = null;
        this.isNewValue = true;

        // 버튼 클릭 리스너
        this.btnClickListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String txtValue = txt.getText();

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
                        txt.setText(String.valueOf(i));
                    else
                        txt.setText(String.valueOf(f));
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
                case "LinearRegression":
                    inputRegression();
                    break;
                case "addVar":
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
    }

    /** UI 생성 */
    private void createUI() {
        frame = new JFrame("Regression calculator");

        container = new Container();
        frame.getContentPane().add(container);

        this.mLabel = new JLabel();
        this.mLabel.setText("M");
        this.mLabel.setBounds(5, 12, 20, 20);
        this.mLabel.setVisible(false);
        container.add(this.mLabel);

        // result text field
        txt = new JTextField();
        txt.setBorder(null);
        txt.setText("0");
        txt.setHorizontalAlignment(JTextField.RIGHT);
        txt.setBounds(25, 0, 215, 40);
        txt.setFocusable(false);
        txt.setEditable(false);
        container.add(txt);

        // functions for memory
        int _scaleNum = 60;

        createButton("MC", 0, 40, _scaleNum, 40);
        createButton("MR", _scaleNum + 6, 40, _scaleNum, 40);
        createButton("M+", _scaleNum * 2 + 12, 40, _scaleNum, 40);
        createButton("M-", _scaleNum * 3 + 18, 40, _scaleNum, 40);

        // number
        ArrayList<Character> types = new ArrayList<Character>();
        types.add('7');
        types.add('8');
        types.add('9');
        types.add('4');
        types.add('5');
        types.add('6');
        types.add('1');
        types.add('2');
        types.add('3');
        types.add('0');
        types.add('.');
        types.add('C');

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                String _name = String.valueOf(types.remove(0));

                createButton(_name, 9 + j * _scaleNum, 130 + (i * _scaleNum), _scaleNum, _scaleNum);
            }
        }

        // operator
        divBtn = createButton("/", 0, 80, _scaleNum, 43);
        multBtn = createButton("*", _scaleNum + 6, 80, _scaleNum, 43);
        minusBtn = createButton("-", _scaleNum * 2 + 12, 80, _scaleNum, 43);
        plusBtn = createButton("+", _scaleNum * 3 + 18, 80, _scaleNum, 43);
        enterBtn = createButton("=", _scaleNum * 3 + 18, 310, _scaleNum, 62);
        LinearRegression = createButton("LinearRegression", _scaleNum * 3 + 18, 246, _scaleNum, 30);
        addVar = createButton("addVar", _scaleNum * 3 + 18, 276, _scaleNum, 30);
        createButton("←", _scaleNum * 3 + 18, 123 + 6, _scaleNum, 55);
        createButton("√", _scaleNum * 3 + 18, 178 + 10, _scaleNum, 55);

        frame.pack();
        frame.setSize(274, 408);
        frame.setVisible(true);
    }

    /** 버튼 생성 */
    private JButton createButton(String name, int x, int y, int width, int height) {
        JButton btn = new JButton(name);
        btn.setName(name);
        btn.setBounds(x, y, width, height);
        btn.setFocusable(false);
        btn.setFont(this.buttonFont);
        btn.addActionListener(this.btnClickListener);
        this.container.add(btn);

        return btn;
    }

    /** 이벤트 리스너 설정 */
    private void setEventListener() {
        this.frame.addKeyListener((KeyListener) new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                Integer code = Integer.valueOf(e.getKeyChar());

                if (code >= 48 && code <= 57)
                    pressNumberPad(String.valueOf(e.getKeyChar()));
                else if (code == 43)
                    inputOperate(plusBtn);
                else if (code == 45)
                    inputOperate(minusBtn);
                else if (code == 42)
                    inputOperate(multBtn);
                else if (code == 47)
                    inputOperate(divBtn);
                else if (code == 10)
                    inputEnter();// Enter(=Return)
                else if (code == 27)
                    inputEscape();// Esc
                else if (code == 8)
                    inputBackspace();// Backspace
                else
                    inputRegression();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }
        });
    }
    
    /** 키패드 입력 */
    private void pressNumberPad(String numStr)
    {   
        String txtValue = txt.getText();
        if(regressionFlag==true){
            numVar=2*Integer.parseInt(numStr)+1;
            X= new double[numVar/2]; //내가 집어넣고 싶은 배열 2개. 길이가 numVar
            Y= new double[numVar/2];
            regressionFlag=false;
        }
        if(addFlag==true){
            if(numCnt<numVar/2){
                X[numCnt]=Double.valueOf(numStr);
                System.out.println(X[numCnt]);
            }
            else if(numCnt<numVar-1){
                Y[numCnt-numVar/2]=Double.valueOf(numStr);
                System.out.println(Y[numCnt-numVar/2]);
            }
            else
                x=Double.valueOf(numStr);
                System.out.println(x);

            numCnt++; 

        }
        if(isNewValue == true)
        {
            if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.red);
            txt.setText("");
            isNewValue = false;
        }
        
        txtValue = txt.getText();
        if(txtValue.equals("0"))
        {
            txt.setText("");
            txtValue = "";
        }
        txt.setText(txtValue    + numStr);
        
        // 적용 후 새로운 value
        if(txt.getText().equals(".")) txt.setText("0.");
    }
    
    /** 연산자 입력 */
    private void inputOperate(JButton b)
    {
        String txtValue = txt.getText();
        
        if(txtValue.equals(divideErrorText)) return;
        
        if(isNewValue == false) calculate();
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.black);
        b.setForeground(Color.red);
        selectedOperBtn = b;
    }
    
    /** 회귀로 계산하기  **/

    private void inputaddVar(){
        // txt는 textfield변수 
        String txtValue=txt.getText();
        if(txtValue.equals(divideErrorText)) return;
        
        addFlag=true;
        //  numCnt : 지금 변수를 몇개째 받았는지 확인하는 애
                  //  numVar : 내가 총 받을 변수의 갯수  둘다 클래스안에 변수로 선언함.
        selectedOperBtn=addVar;
        isNewValue=true;
        if(numCnt==numVar){
            
            LinearRegression solve = new LinearRegression(X,Y);
            resultValue=new BigDecimal(solve.predict(x));
            calculate();
        }
     
    }// inputaddVar()


    private void inputRegression(){
        String txtValue=txt.getText();
        if(txtValue.equals(divideErrorText)) return;
        regressionFlag=true;
        txt.setText("몇개의 변수를 입력할건가요?");
        selectedOperBtn=LinearRegression;
        }// inputRegression()
        
    

    

    /** Square Root 계산 */
    private void calculateSquareRoot()
    {
        Float fValue = Float.valueOf(txt.getText());
        
        BigDecimal tmp = new BigDecimal(Math.sqrt(fValue));
        
        Float f = tmp.floatValue();
        Integer i = tmp.intValue();
        if(f.equals(Float.valueOf(i))) txt.setText(String.valueOf(i));
        else txt.setText(String.valueOf(f));
        
        this.isNewValue = true;
    }
    
    /** 엔터 입력 (결과 계산) */
    private void inputEnter()
    {
        String txtValue = txt.getText();
        
        if(txtValue.equals(divideErrorText)) return;
        
        calculate();
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.black);
    }
    
    /** Esc 입력 (초기화) */
    private void inputEscape()
    {
        txt.setText("0");
        resultValue = new BigDecimal(0);
        
        if(selectedOperBtn != null) selectedOperBtn.setForeground(Color.black);
        selectedOperBtn = null;
    }
    
    /** 백스페이스 입력 (마지막 입력값 삭제) */
    private void inputBackspace()
    {
        String txtValue = txt.getText();
        txt.setText(txtValue.substring(0, txtValue.length() - 1));
        if(txt.getText().equals("")) txt.setText("0");
    }
    
    /** 계산하기 */
    private void calculate()
    {
        
        
        Float fValue = Float.valueOf(txt.getText());
        
        if(selectedOperBtn == null  )
        {
            resultValue = new BigDecimal(fValue);
            isNewValue = true;
            return;
        }
        
        switch(selectedOperBtn.getName())
        {
            case "/":
                if(txt.getText().equals("0") || txt.getText().equals("0."))
                {
                    this.txt.setText(this.divideErrorText );
                    if(this.selectedOperBtn != null) this.selectedOperBtn.setForeground(Color.black);
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
        if(f.equals(Float.valueOf(i))) txt.setText(String.valueOf(i));
        else txt.setText(String.valueOf(f));
        
        isNewValue = true;
    }
    
    /** 콘솔에 출력하기 */
    private void trace(String value)
    {
        System.out.println(value);
    }
    
    /** main 함수 */
    public static void main(String[] args)
    {
        new Calculator().create();

    }
}