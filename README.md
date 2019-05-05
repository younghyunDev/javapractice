# Javapractice

## Week1
- Introduction (JAVA,Git,MarkdDown)

## Week2
  - JAVA   
    * inheritnce
      > Inheritance is a comprehensive succession of property and status by the death of a person. It is stated in Wikipedia. If so, is the meaning of inheritance in Java much different from what is stated in the encyclopedia? no. It is similar. Just as a child inherits physical property or intellectual property inherited from a parent in the real world, a child class inherited from a parent class in Java has all the resources and methods of the parent class You can use it as your own.


    
  - Git&GitHub
    * stage
    * git init 
    * git add
    * commit
    * push
    * pull
    ![cheat Sheat](https://zeroturnaround.com/wp-content/uploads/2016/05/Git-Cheat-Sheet-by-RebelLabs.png)
  - MarkDown
    * 기본 사용법
    ![Cheat Sheat](https://i.pinimg.com/originals/33/19/81/3319813c4fd34c1e5d8663ea3a632329.jpg)
    * 직접 readme파일 만들어보기
## Week3
  - Java
    * Abstraction (추상화) 
      > OOP is a programming paradigm that uses abstraction to model real-world.     
      Abstraction is literally ignoring detailed information and constructing it with only the information that is needed by necessity.

    * Practice
    
|           Class          | Variable   | method              |
|:------------------------:|------------|---------------------|
| Animal(super)            | picture    | sleep               |
|                          | food       | makeNoise(abstract) |
|                          | hunger     | eat(abstract)       |
|                          | boundaries | roam(abstract)      |
|                          | location   |                     |
| Canine[Animal] (abstract) |            | roam                |
| Feline[Animal] (abstract) |            | roam                |
| Hippo[Animal]            |            | roam                |
|                          |            | makenoise           |
|                          |            | eat                 |
| Dog[Canine]              |            | makenoise           |
|                          |            | eat                 |
| Wolf[Canine]             |            | makenoise           |
|                          |            | eat                 |
| Lion[Feline]             |            | makenoise           |
|                          |            | eat                 |
| Tiger[Feline]            |            | makenoise           |
|                          |            | eat                 |
| Cat[Feline]              |            | makenoise           |
|                          |            | eat                 |
    

  - GitHub
    * readme파일 수정 및 [git pages](https://younghyundev.github.io/javapractice/) 만들어보기
    

## Week4~6 GUI
  - Swing
    > swing은 자바 영역에서 사용하는 look &feel을 적용 받아 모든 운영체제에서 동일한 느낌을 제공한다.
    * JButton
    * JFrame
    * JTextField
  - 용어정리
    * Container
      >JAVA에서 Container는 창의 역할을 한다. 한 개 이상의 Container 위에 Component들이 올려질 영역
      Container는 실제로 Component 보다 작은 개념이다.
      (ex) Frame,Window,Panel,Dialog,Applet
    * Component
      > 실제로 Container 위에 올려져서 화면 구성을 담당하는 요소들
      (ex)Button,TextField,TextAread 등등
    * LayoutManager
      > Container 위에 Component들을 올릴 때 자리 배치방법
      (ex) FlowLayout,BorderLayout,GridLatout
  - Event 처리하기
    >   1.이벤트를 발생시키는 주체 찾기  
        2. 이벤트 주체에 해당하는 이벤트 종류 선택하기  
        3. 이벤트를 등록하기(이벤트주체.add해당Listener(...);)    
        4. 해당 이벤트를 처리하는 핸들러를 구현한다.
## Week 7-8 계산기 구현하기
  [계산기 보러가기](https://github.com/younghyunDev/javapractice/tree/master/calculator)
    
     