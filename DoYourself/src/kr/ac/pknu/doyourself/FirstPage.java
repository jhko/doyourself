package kr.ac.pknu.doyourself;

import com.example.calendar.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstPage extends Activity{
	Button s_btn1; 
	Button s_btn2;
	Button t_btn;

	@Override 
	protected void onCreate(Bundle savedInstanceState) 
	{ super.onCreate(savedInstanceState); 
	setContentView(R.layout.firstpage); 


	s_btn1 = (Button)findViewById(R.id.schedule); 
	s_btn2= (Button)findViewById(R.id.study); 
	t_btn = (Button)findViewById(R.id.todo);  

	s_btn1.setOnClickListener(new OnClickListener(){ 
		public void onClick(View v){
			Intent intent1 = new Intent(FirstPage.this, Schedule.class); 
			startActivity(intent1);
		}
	});

	//s_btn2.setOnClickListener(new OnClickListener(){
	//	public void onClick(View v){
	//		Intent intent2 = new Intent(firstpage.this, .class);
	//		startActivity(intent2);
	//	}
//	});
	
	
	t_btn.setOnClickListener(new OnClickListener(){
		public void onClick(View v){
			Intent intent3 = new Intent(FirstPage.this, Todo.class);
			startActivity(intent3);
		}
	});
	}
}

