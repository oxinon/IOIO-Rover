package com.example.robotcontrol;

import ioio.lib.api.DigitalOutput;
import ioio.lib.api.PwmOutput;
import ioio.lib.api.exception.ConnectionLostException;
import ioio.lib.util.AbstractIOIOActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.example.robotcontrol.JoyStickClass;


@SuppressWarnings("deprecation")
public class RobotControll extends AbstractIOIOActivity {

	private SeekBar seekBar_;
	private TextView textView_;   
	private String direction_state = "STOP";
	private String strSpeed;
	private Integer jsSpeed;
	
	
    //Button buttonUp, buttonDown, buttonLeft, buttonRight;
    //ImageView imageView1;
    //CheckBox cbFlash;
    RelativeLayout layout_joystick;
	JoyStickClass js;
    int screenWidth, screenHeight;
	//Boolean task_state = true;
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN 
        		| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); 
        setContentView(R.layout.activity_robot_controll);
       
        seekBar_ = (SeekBar)findViewById(R.id.seekBar1);
        textView_ = (TextView)findViewById(R.id.textView1);
        textView_.setText("PWM Frequenz: " + String.valueOf(3100 - 650 - seekBar_.getProgress()));
      
        seekBar_.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {       

            @Override       
            public void onStopTrackingTouch(SeekBar seekBar) {           
            }       

            @Override       
            public void onStartTrackingTouch(SeekBar seekBar) {         
            }       

            @Override       
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {     
            	textView_.setText("PWM Frequenz: " + String.valueOf(3100 - 650 - seekBar_.getProgress()));	
            }       
        });   

        Display display = getWindowManager().getDefaultDisplay(); 
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        
		layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);
	    js = new JoyStickClass(getApplicationContext(), layout_joystick, R.drawable.image_button);
	    js.setStickSize(screenHeight / 7, screenHeight / 7);
	    js.setLayoutSize(screenHeight / 2, screenHeight / 2);
	    js.setLayoutAlpha(50);
	    js.setStickAlpha(255);
	    //js.setOffset((int)((screenHeight / 9) * 0.6));
	    //js.setMinimumDistance((int)((screenHeight / 9) * 0.6));
	    js.setOffset((int)(0));
	    js.setMinimumDistance((int)(40));    
	    
	    layout_joystick.setOnTouchListener(new OnTouchListener() {
	    	long time = System.currentTimeMillis();
			public boolean onTouch(View arg0, MotionEvent arg1) {
				js.drawStick(arg1);
				if(arg1.getAction() == MotionEvent.ACTION_DOWN) {
					command();
				} else if(arg1.getAction() == MotionEvent.ACTION_MOVE) {
					if(System.currentTimeMillis() - time > 10) {
						command();
						time = System.currentTimeMillis(); 
					}
				} else if(arg1.getAction() == MotionEvent.ACTION_UP) {
					textView_.setText("STOP");
					direction_state = "STOP";
					direction_state = "STOP";
				}
				return true;
			}
			
			public void command() {
				int direction = js.get8Direction();
				int speed = (int)(((js.getDistance() - 40)/3)*2);
			    if(speed > 100)	speed = 100;
			    //speed += 10;
			    strSpeed = String.valueOf(speed);
			    jsSpeed = speed;
			    
				if(direction == JoyStickClass.STICK_UP) {
					textView_.setText("UU " + strSpeed);
					direction_state = "UU";
				} else if(direction == JoyStickClass.STICK_UPRIGHT) {
					textView_.setText("UR " + strSpeed);
					direction_state = "UR";
				} else if(direction == JoyStickClass.STICK_RIGHT) {
					textView_.setText("RR " + strSpeed);
					direction_state = "RR";
				}  else if(direction == JoyStickClass.STICK_DOWNRIGHT) {
					textView_.setText("DR " + strSpeed);
					direction_state = "DR";
				} else if(direction == JoyStickClass.STICK_DOWN) {
					textView_.setText("DD " + strSpeed);
					direction_state = "DD";
				} else if(direction == JoyStickClass.STICK_DOWNLEFT) {
					textView_.setText("DL " + strSpeed);
					direction_state = "DL";
				} else if(direction == JoyStickClass.STICK_LEFT) {
					textView_.setText("LL " + strSpeed);
					direction_state = "LL";
				}  else if(direction == JoyStickClass.STICK_UPLEFT) {
					textView_.setText("UL " + strSpeed);
					direction_state = "UL";
				} else if(direction == JoyStickClass.STICK_NONE) {
					textView_.setText("STOP");
					direction_state = "STOP";
					direction_state = "STOP";
				}
				 
			}
        });

	}
	

	class IOIOThread extends AbstractIOIOActivity.IOIOThread {
		

		private DigitalOutput D1A, D1B, D2A, D2B, D3A, D3B, D4A, D4B;
		private PwmOutput PWM1, PWM2, PWM3, PWM4, PWM10, statLed;	

		public void setup() throws ConnectionLostException {
			try {
				
	        	D1A = ioio_.openDigitalOutput(1, false);
	        	D1B = ioio_.openDigitalOutput(2, false);
	        	D2A = ioio_.openDigitalOutput(4, false);
	        	D2B = ioio_.openDigitalOutput(5, false);
	        	D3A = ioio_.openDigitalOutput(16,false);
	        	D3B = ioio_.openDigitalOutput(17,false);
	        	D4A = ioio_.openDigitalOutput(18,false);
	        	D4B = ioio_.openDigitalOutput(19,false);
	        	
	        	PWM1 = ioio_.openPwmOutput(3, 100);
	        	PWM1.setDutyCycle(0);
	        	PWM2 = ioio_.openPwmOutput(6, 100);
	        	PWM2.setDutyCycle(0);
	        	PWM3 = ioio_.openPwmOutput(13, 100);
	        	PWM3.setDutyCycle(0);
	        	PWM4 = ioio_.openPwmOutput(14, 100);
	        	PWM4.setDutyCycle(0);
				PWM10 = ioio_.openPwmOutput(10, 100);
				
				PWM10.setPulseWidth(3100 - 650 - seekBar_.getProgress());
				statLed = ioio_.openPwmOutput(0, 100);
	
			
			} catch (ConnectionLostException e) {
				throw e;
				
			}
		}
		
  
		public void loop() throws ConnectionLostException, InterruptedException {
			try {

				PWM10.setPulseWidth(3100 - 650 - seekBar_.getProgress());
				statLed.setPulseWidth(seekBar_.getProgress()*6);

				if(direction_state == "UU") {
	        		PWM1.setDutyCycle((float)jsSpeed / 100);
	        		PWM2.setDutyCycle((float)jsSpeed / 100);
	        		PWM3.setDutyCycle((float)jsSpeed / 100);
	        		PWM4.setDutyCycle((float)jsSpeed / 100);
	        		D1A.write(true);
	        		D1B.write(false);
	        		D2A.write(true);
					D2B.write(false);
	        		D3A.write(true);
					D3B.write(false);
	        		D4A.write(true);
					D4B.write(false);
	        	} else if(direction_state == "DD") {
	        		PWM1.setDutyCycle((float)jsSpeed / 100);
	        		PWM2.setDutyCycle((float)jsSpeed / 100);
	        		PWM3.setDutyCycle((float)jsSpeed / 100);
	        		PWM4.setDutyCycle((float)jsSpeed / 100);
	    			D1A.write(false);
	    			D1B.write(true);
	    			D2A.write(false);
					D2B.write(true);
	    			D3A.write(false);
					D3B.write(true);
	    			D4A.write(false);
					D4B.write(true);
	        	} else if(direction_state == "LL") {
	        		PWM1.setDutyCycle((float)jsSpeed / 100);
	        		PWM2.setDutyCycle((float)jsSpeed / 100);
	        		PWM3.setDutyCycle((float)jsSpeed / 100);
	        		PWM4.setDutyCycle((float)jsSpeed / 100);
	    			D1A.write(false);
	    			D1B.write(true);
	    			D2A.write(false);
	    			D2B.write(true);
	    			D3A.write(true);
					D3B.write(false);
	    			D4A.write(true);
					D4B.write(false);
	        	} else if(direction_state == "RR") {
	        		PWM1.setDutyCycle((float)jsSpeed / 100);
	        		PWM2.setDutyCycle((float)jsSpeed / 100);
	        		PWM3.setDutyCycle((float)jsSpeed / 100);
	        		PWM4.setDutyCycle((float)jsSpeed / 100);
	    			D1A.write(true);
	    			D1B.write(false);
	    			D2A.write(true);
	    			D2B.write(false);
	    			D3A.write(false);
					D3B.write(true);
	    			D4A.write(false);
					D4B.write(true);
	        	} else if(direction_state == "UR") {
	        		PWM1.setDutyCycle((((float)jsSpeed / (float)1.5) + 20) / 100);
	        		PWM2.setDutyCycle((((float)jsSpeed / (float)1.5) + 20) / 100);
	        		PWM3.setDutyCycle((((float)jsSpeed / (float)1.5) - 20) / 100);
	        		PWM4.setDutyCycle((((float)jsSpeed / (float)1.5) - 20) / 100);
	        		D1A.write(true);
	        		D1B.write(false);
	        		D2A.write(true);
	        		D2B.write(false);
	        		D3A.write(true);
					D3B.write(false);
	        		D4A.write(true);
					D4B.write(false);
	        	}  else if(direction_state == "UL") {
	        		PWM1.setDutyCycle((((float)jsSpeed / (float)1.5) - 20) / 100);
	        		PWM2.setDutyCycle((((float)jsSpeed / (float)1.5) - 20) / 100);
	        		PWM3.setDutyCycle((((float)jsSpeed / (float)1.5) + 20) / 100);
	        		PWM4.setDutyCycle((((float)jsSpeed / (float)1.5) + 20) / 100);
	        		D1A.write(true);
	        		D1B.write(false);
	        		D2A.write(true);
	        		D2B.write(false);
	        		D3A.write(true);
					D3B.write(false);
	        		D4A.write(true);
					D4B.write(false);
	        	} else if(direction_state == "DR") {
	        		PWM1.setDutyCycle((((float)jsSpeed / (float)1.5) + 20) / 100);
	        		PWM2.setDutyCycle((((float)jsSpeed / (float)1.5) + 20) / 100);
	        		PWM3.setDutyCycle((((float)jsSpeed / (float)1.5) - 20) / 100);
	        		PWM4.setDutyCycle((((float)jsSpeed / (float)1.5) - 20) / 100);
	    			D1A.write(false);
	    			D1B.write(true);
	    			D2A.write(false);
	    			D2B.write(true);
	    			D3A.write(false);
					D3B.write(true);
	    			D4A.write(false);
					D4B.write(true);
	        	} else if(direction_state == "DL") {
	        		PWM1.setDutyCycle((((float)jsSpeed / (float)1.5) - 20) / 100);
	        		PWM2.setDutyCycle((((float)jsSpeed / (float)1.5) - 20) / 100);
	        		PWM3.setDutyCycle((((float)jsSpeed / (float)1.5) + 20) / 100);
	        		PWM4.setDutyCycle((((float)jsSpeed / (float)1.5) + 20) / 100);
	    			D1A.write(false);
	    			D1B.write(true);
	    			D2A.write(false);
	    			D2B.write(true);
	    			D3A.write(false);
					D3B.write(true);
	    			D4A.write(false);
					D4B.write(true);
	        	} else if(direction_state == "STOP") {
	        		PWM1.setDutyCycle(0);
	        		PWM2.setDutyCycle(0);
	        		PWM3.setDutyCycle(0);
	        		PWM4.setDutyCycle(0);
	    			D1A.write(false);
	    			D1B.write(false);
	    			D2A.write(false);
					D2B.write(false);
	    			D3A.write(false);
	    			D3B.write(false);
	    			D4A.write(false);
					D4B.write(false);
	        	}
				
			
					sleep(10);
		
			} catch (ConnectionLostException e) {
				throw e;
				
			}
		}
	
	}
	

	@Override
	protected AbstractIOIOActivity.IOIOThread createIOIOThread() {
		return new IOIOThread();
	}


}