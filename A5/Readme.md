# How to

Create a new Android Application Project.  Let's say your Application Name is "Calculator", your Project Name is "Calculator", and your Package Name is "com.example.calculator".  Setup the project with the default settings.  Now replace MainActivity.java, activity_main.xml_ (in res/layout), & strings.xml  (in res/values) with the code below.  Next, create a new class "CalculatorBrain" and replace CalculatorBrain.java with the code below.  

The calculator should run without errors! 
MainActivity.java
``` java 
package example.com.calculator;
import java.io.IOException;
import java.text.DecimalFormat;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
/**
 * Created by neeraj on 19-03-2016.
 */
public class MainActivity extends Activity implements OnClickListener {
    private TextView mCalculatorDisplay;
    private Boolean userIsInTheMiddleOfTypingANumber = false;
    private CalculatorBrain mCalculatorBrain;
    private static final String DIGITS = "0123456789.";
    DecimalFormat df = new DecimalFormat("@###########");
    
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCalculatorBrain = new CalculatorBrain();
        mCalculatorDisplay = (TextView) findViewById(R.id.textView1);
        
        df.setMinimumFractionDigits(0);
        df.setMinimumIntegerDigits(1);
        df.setMaximumIntegerDigits(8);

        findViewById(R.id.button0).setOnClickListener(this);
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.button2).setOnClickListener(this);
        findViewById(R.id.button3).setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.button5).setOnClickListener(this);
        findViewById(R.id.button6).setOnClickListener(this);
        findViewById(R.id.button7).setOnClickListener(this);
        findViewById(R.id.button8).setOnClickListener(this);
        findViewById(R.id.button9).setOnClickListener(this);

        findViewById(R.id.buttonAdd).setOnClickListener(this);
        findViewById(R.id.buttonSubtract).setOnClickListener(this);
        findViewById(R.id.buttonMultiply).setOnClickListener(this);
        findViewById(R.id.buttonDivide).setOnClickListener(this);
        findViewById(R.id.buttonToggleSign).setOnClickListener(this);
        findViewById(R.id.buttonDecimalPoint).setOnClickListener(this);
        findViewById(R.id.buttonEquals).setOnClickListener(this);
        findViewById(R.id.buttonClear).setOnClickListener(this);
        findViewById(R.id.buttonClearMemory).setOnClickListener(this);
        findViewById(R.id.buttonAddToMemory).setOnClickListener(this);
        findViewById(R.id.buttonSubtractFromMemory).setOnClickListener(this);
        findViewById(R.id.buttonRecallMemory).setOnClickListener(this);

        // The following buttons only exist in layout-land (Landscape mode) and require extra attention.
        // The messier option is to place the buttons in the regular layout too and set android:visibility="invisible".
        if (findViewById(R.id.buttonSquareRoot) != null) {
            findViewById(R.id.buttonSquareRoot).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonSquared) != null) {
            findViewById(R.id.buttonSquared).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonInvert) != null) {
            findViewById(R.id.buttonInvert).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonSine) != null) {
            findViewById(R.id.buttonSine).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonCosine) != null) {
            findViewById(R.id.buttonCosine).setOnClickListener(this);
        }
        if (findViewById(R.id.buttonTangent) != null) {
            findViewById(R.id.buttonTangent).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        String buttonPressed = ((Button) v).getText().toString();
        if (DIGITS.contains(buttonPressed)) {
            // digit was pressed
            if (userIsInTheMiddleOfTypingANumber) {
                if (buttonPressed.equals(".") && mCalculatorDisplay.getText().toString().contains(".")) {
                    // ERROR PREVENTION
                    // Eliminate entering multiple decimals
                } else {
                    mCalculatorDisplay.append(buttonPressed);
                }
            } else {
                if (buttonPressed.equals(".")) {
                    // ERROR PREVENTION
                    // This will avoid error if only the decimal is hit before an operator, by placing a leading zero
                    // before the decimal
                    mCalculatorDisplay.setText(0 + buttonPressed);
                } else {
                    mCalculatorDisplay.setText(buttonPressed);
                }
                userIsInTheMiddleOfTypingANumber = true;
            }
        } else {
            // operation was pressed
            if (userIsInTheMiddleOfTypingANumber) {
                mCalculatorBrain.setOperand(Double.parseDouble(mCalculatorDisplay.getText().toString()));
                userIsInTheMiddleOfTypingANumber = false;
            }
            try {
                mCalculatorBrain.performOperation(buttonPressed);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save variables on screen orientation change
        outState.putDouble("OPERAND", mCalculatorBrain.getResult());
        outState.putDouble("MEMORY", mCalculatorBrain.getMemory());
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore variables on screen orientation change
        mCalculatorBrain.setOperand(savedInstanceState.getDouble("OPERAND"));
        mCalculatorBrain.setMemory(savedInstanceState.getDouble("MEMORY"));
        mCalculatorDisplay.setText(df.format(mCalculatorBrain.getResult()));
    }

}
```
CalculatorBrain.java
``` java
package example.com.calculator;
import android.content.Context;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
/**
 * Created by neeraj on 19-03-2016.
 */
public class CalculatorBrain {
    // 3 + 6 = 9
    // 3 & 6 are called the operand.
    // The + is called the operator.
    // 9 is the result of the operation.
    private double mOperand;
    private double mWaitingOperand;
    private String mWaitingOperator;
    private double mCalculatorMemory;

    // operator types
    public static final String ADD = "+";
    public static final String SUBTRACT = "-";
    public static final String MULTIPLY = "*";
    public static final String DIVIDE = "/";

    public static final String CLEAR = "C" ;
    public static final String CLEARMEMORY = "MC";
    public static final String ADDTOMEMORY = "M+";
    public static final String SUBTRACTFROMMEMORY = "M-";
    public static final String RECALLMEMORY = "MR";
    public static final String SQUAREROOT = "√";
    public static final String SQUARED = "x²";
    public static final String INVERT = "1/x";
    public static final String TOGGLESIGN = "+/-";
    public static final String SINE = "sin";
    public static final String COSINE = "cos";
    public static final String TANGENT = "tan";

    // public static final String EQUALS = "=";

    // constructor
    public CalculatorBrain() {
        // initialize variables upon start
        mOperand = 0;
        mWaitingOperand = 0;
        mWaitingOperator = "";
        mCalculatorMemory = 0;
    }
    public void setOperand(double operand) {
        mOperand = operand;
    }
    public double getResult() {
        return mOperand;
    }
    // used on screen orientation change
    public void setMemory(double calculatorMemory) {
        mCalculatorMemory = calculatorMemory;
    }
    // used on screen orientation change
    public double getMemory() {
        return mCalculatorMemory;
    }
    public String toString() {
        return Double.toString(mOperand);
    }
    protected double performOperation(String operator) throws IOException {
        if (operator.equals(CLEAR)) {
            mOperand = 0;
            mWaitingOperator = "";
            mWaitingOperand = 0;
            // mCalculatorMemory = 0;
        } else if (operator.equals(CLEARMEMORY)) {
            mCalculatorMemory = 0;
        } else if (operator.equals(ADDTOMEMORY)) {
            mCalculatorMemory = mCalculatorMemory + mOperand;
        } else if (operator.equals(SUBTRACTFROMMEMORY)) {
            mCalculatorMemory = mCalculatorMemory - mOperand;
        } else if (operator.equals(RECALLMEMORY)) {
            mOperand = mCalculatorMemory;
        } else if (operator.equals(SQUAREROOT)) {
            mOperand = Math.sqrt(mOperand);
        } else if (operator.equals(SQUARED)) {
            mOperand = mOperand * mOperand;
        } else if (operator.equals(INVERT)) {
            if (mOperand != 0) {
                mOperand = 1 / mOperand;
            }
        } else if (operator.equals(TOGGLESIGN)) {
            mOperand = -mOperand;
        } else if (operator.equals(SINE)) {
            mOperand = Math.sin(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees
        } else if (operator.equals(COSINE)) {
            mOperand = Math.cos(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees
        } else if (operator.equals(TANGENT)) {
            mOperand = Math.tan(Math.toRadians(mOperand)); // Math.toRadians(mOperand) converts result to degrees
        } else {
            performWaitingOperation();
            mWaitingOperator = operator;
            mWaitingOperand = mOperand;
        }
        return mOperand;
    }

    protected void performWaitingOperation() {
        if (mWaitingOperator.equals(ADD)) {
            mOperand = mWaitingOperand + mOperand;
        } else if (mWaitingOperator.equals(SUBTRACT)) {
            mOperand = mWaitingOperand - mOperand;
        } else if (mWaitingOperator.equals(MULTIPLY)) {
            mOperand = mWaitingOperand * mOperand;
        } else if (mWaitingOperator.equals(DIVIDE)) {
            if (mOperand != 0) {
                mOperand = mWaitingOperand / mOperand;
            }
        }
    }
}
```
activity_main.xml  (in res/layout)
``` xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/functionPad"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:layout_gravity="center"
    android:orientation="vertical"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin" >

    <LinearLayout
        android:id="@+id/row1"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <TextView
            android:id="@+id/textView1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:gravity="right"
            android:maxLines="1"
            android:paddingLeft="10dp"
            android:paddingRight="10dp"
            android:text="0"
            android:textAppearance="?android:attr/textAppearanceLarge"
            android:textSize="40sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/row2"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/buttonClearMemory"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonClearMemory"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonAddToMemory"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonAddToMemory"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonSubtractFromMemory"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSubtractFromMemory"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonRecallMemory"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonRecallMemory"
            android:textSize="25sp" />
    </LinearLayout>
    <LinearLayout
        android:id="@+id/row7"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/buttonSquareRoot"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSquareRoot"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonSquared"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSquared"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonInvert"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonInvert"
            android:textSize="17sp" />

        <Button
            android:id="@+id/buttonSine"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSine"
            android:textSize="17sp" />
        <Button
            android:id="@+id/buttonCosine"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonCosine"
            android:textSize="17sp" />
        <Button
            android:id="@+id/buttonTangent"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonTangent"
            android:textSize="17sp" />
    </LinearLayout>
    <LinearLayout
        android:id="@+id/row3"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/buttonClear"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonClear"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonToggleSign"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonToggleSign"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonDivide"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonDivide"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonMultiply"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonMultiply"
            android:textSize="25sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/row4"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/button7"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button7"
            android:textSize="25sp" />

        <Button
            android:id="@+id/button8"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button8"
            android:textSize="25sp" />

        <Button
            android:id="@+id/button9"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button9"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonSubtract"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonSubtract"
            android:textSize="25sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/row5"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".12" >

        <Button
            android:id="@+id/button4"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button4"
            android:textSize="25sp" />

        <Button
            android:id="@+id/button5"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button5"
            android:textSize="25sp" />

        <Button
            android:id="@+id/button6"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/button6"
            android:textSize="25sp" />

        <Button
            android:id="@+id/buttonAdd"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonAdd"
            android:textSize="25sp" />
    </LinearLayout>

    <LinearLayout
        android:id="@+id/row6"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight=".24"
        android:baselineAligned="false" >

        <LinearLayout
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".75"
            android:orientation="vertical" >

            <LinearLayout
                android:id="@+id/linearLayout1"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight=".50"
                android:textSize="25sp" >

                <Button
                    android:id="@+id/button1"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".33"
                    android:text="@string/button1"
                    android:textSize="25sp" />

                <Button
                    android:id="@+id/button2"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".33"
                    android:text="@string/button2"
                    android:textSize="25sp" />

                <Button
                    android:id="@+id/button3"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".34"
                    android:text="@string/button3"
                    android:textSize="25sp" />
            </LinearLayout>

            <LinearLayout
                android:id="@+id/linearLayout2"
                android:layout_width="match_parent"
                android:layout_height="0dp"
                android:layout_weight=".50" >

                <Button
                    android:id="@+id/button0"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".66"
                    android:text="@string/button0"
                    android:textSize="25sp" />

                <Button
                    android:id="@+id/buttonDecimalPoint"
                    android:layout_width="0dp"
                    android:layout_height="match_parent"
                    android:layout_weight=".34"
                    android:text="@string/buttonDecimalPoint"
                    android:textSize="25sp" />
            </LinearLayout>
        </LinearLayout>

        <Button
            android:id="@+id/buttonEquals"
            android:layout_width="0dp"
            android:layout_height="match_parent"
            android:layout_weight=".25"
            android:text="@string/buttonEquals"
            android:textSize="25sp" />
    </LinearLayout>


</LinearLayout>
```
strings.xml  (in res/values)
``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">Calculator</string>
    <string name="menu_settings">Settings</string>
    <string name="action_settings">Settings</string>
    <string name="button0">0</string>
    <string name="button1">1</string>
    <string name="button2">2</string>
    <string name="button3">3</string>
    <string name="button4">4</string>
    <string name="button5">5</string>
    <string name="button6">6</string>
    <string name="button7">7</string>
    <string name="button8">8</string>
    <string name="button9">9</string>
    <string name="buttonAdd">+</string>
    <string name="buttonSubtract">-</string>
    <string name="buttonMultiply">*</string>
    <string name="buttonDivide">/</string>
    <string name="buttonToggleSign">+/-</string>
    <string name="buttonDecimalPoint">.</string>
    <string name="buttonEquals">=</string>
    <string name="buttonClear">C</string>
    <string name="buttonClearMemory">MC</string>
    <string name="buttonAddToMemory">M+</string>
    <string name="buttonSubtractFromMemory">M-</string>
    <string name="buttonRecallMemory">MR</string>
    <string name="buttonSquareRoot">√</string>
    <string name="buttonSquared">x²</string>
    <string name="buttonInvert">1/x</string>
    <string name="buttonSine">sin</string>
    <string name="buttonCosine">cos</string>
    <string name="buttonTangent">tan</string>

</resources>
```
Screenshot: 

![alt text](https://raw.githubusercontent.com/neerajvashistha/be-2/master/A5/Screenshot%20(7).png "Screenshot")
