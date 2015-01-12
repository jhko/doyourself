package kr.ac.pknu.doyourself;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendar.R;

public class UpdateSchedule extends Activity {

	private TextView mDate;
	private Button mPickDate;
	private Button addButton;
	private Button backButton;

	private int mYear;
	private int mMonth;
	private int mDay;
	private String yymmdd;

	private EditText title;
	private EditText content;



	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();

		}
	};

	protected Dialog onCreateDialog(int id){
		switch(id){
		case 0 :
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
		}
		return null;
	}

	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.addschedule2); 

		mDate =(TextView)findViewById(R.id.textview1);
		mPickDate = (Button)findViewById(R.id.pickDate);
		addButton = (Button)findViewById(R.id.add);
		backButton = (Button)findViewById(R.id.back);

		title = (EditText)findViewById(R.id.title);
		content = (EditText)findViewById(R.id.content);



		Intent intent = getIntent();

		mYear = Integer.parseInt(intent.getStringExtra("year"));
		mMonth = Integer.parseInt(intent.getStringExtra("month"));
		mDay = Integer.parseInt(intent.getStringExtra("day"));

		yymmdd = mYear + "." + mMonth + "." + mDay;

		mDate.setText(yymmdd);

		mPickDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				showDialog(0);
			}
		});

		updateDisplay();

		backButton.setOnClickListener(new OnClickListener(){ 
			public void onClick(View v){
				finish();
			}
		});

		addButton.setOnClickListener(new OnClickListener(){ 
			public void onClick(View v){

				DBOpenHelper helper = new DBOpenHelper(UpdateSchedule.this, "scheduler04.db", null, 1);
				SQLiteDatabase db = helper.getWritableDatabase();

				String stitle = title.getText().toString();
				String scontent = content.getText().toString();
				
				db.execSQL("INSERT INTO Schedule (S_Date, E_Date, Title, Content, Category) VALUES ('" + yymmdd + "','" + yymmdd + "','" + stitle + "', '" + scontent + "', '1');");

				Toast.makeText(getApplicationContext(),"added", Toast.LENGTH_SHORT).show();
				finish();

			}
		});

	}

	private void updateDisplay(){
		mDate.setText(new StringBuilder()
		.append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
	}

}