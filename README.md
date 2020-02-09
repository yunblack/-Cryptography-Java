# Boss
Basic Online Secret Sharing Proect

Executive File Updated - Please check the file named Prototype_487.exe
Just double click this file to run the program

When you import this, you have to follow this steps.(Requirement)
1. Download three .jar files
  - mail 1.4.7.jar (URL : https://repo1.maven.org/maven2/javax/mail/mail/1.4.7/mail-1.4.7.jar)
  - activation 1.1.1.jar (URL : https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar)
  - javaFX SDK13.jar (URL : http://gluonhq.com/download/javafx-13.0.1-sdk-windows/)
 
2. After download these .jar files, you have to import this .jar files to the project by doing this process.
<For mail-1.4.7.jar and activation-1.1.1.jar>
  - When you right-click on your project you can see this page.
 <img src="https://user-images.githubusercontent.com/52875025/67953170-f7a69f80-fc31-11e9-8594-c0b71c941a42.png" width="90%"></img>
 
  - Click Build Path -> Configure Build Path
 <img src="https://user-images.githubusercontent.com/52875025/67953455-84515d80-fc32-11e9-905b-c1a356c3bceb.png" width="90%"></img>
  - Then move to the Libraries tab.
  - Click Classpath and click Add External JARs.
 
 <img src="https://user-images.githubusercontent.com/52875025/67953793-26714580-fc33-11e9-889d-24352a48aa9b.png" width="90%"></img>
  - This page will pop-up then you can choose JAR files which you want to add on your project.
 
<For JavaFX SDK13.jar>
  - Unlike other JAR files, you need to add JavaFX SDK13 in other ways.
 <img src="https://user-images.githubusercontent.com/52875025/67953455-84515d80-fc32-11e9-905b-c1a356c3bceb.png" width="90%"></img>
  - Click Add Library button.
 
  - Then you can see this page.
 <img src="https://user-images.githubusercontent.com/52875025/67954055-a0a1ca00-fc33-11e9-9d1c-c554b4f580bf.png" width="90%"></img>
  - Choose User Libaray and click Next>
 
<img src="https://user-images.githubusercontent.com/52875025/67954196-dc3c9400-fc33-11e9-8a5e-37339a4434ed.png" width="90%"></img>
  - After click Next> you can see Add Library pop-up with nothing.
  - Click User Libraries.
 
  - Click New, set User library name as JavaFX or something you want. Then click OK
<img src="https://user-images.githubusercontent.com/52875025/67954482-684ebb80-fc34-11e9-9092-250390ad3731.png" width="90%"></img>
  - Add External JARs button then add JavaFX Jar file which are located in 'JavaFX SDK path\libs'
 
3. When you are done with these steps, Click Run - Run Configurations - Arguments
  - Now you can see two boxes, one is Program Arguments: the other one is VM Arguments:
<img src="https://user-images.githubusercontent.com/52875025/67954853-f7f46a00-fc34-11e9-82aa-a37849a1eaf7.png" width="90%"></img>
  - Add this arguments in VM Arguments box
  
  '--module-path "JavaFX SDK path\lib" --add-modules=javafx.fxml,javafx.controls' (you have to remove ' ' and we need " " for path)

After finish this, you can run the project.
  
  
