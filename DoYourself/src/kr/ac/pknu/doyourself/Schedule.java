package kr.ac.pknu.doyourself;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.calendar.R;

class CalData {
	int day;
	int dayofweek;
	
	public CalData(int d, int h) {
		day = d;
		dayofweek = h;
	}

	public int getDay() {
		return day;
	}

	public int getDayofweek() {
		return dayofweek;
	}
}


public class Schedule extends Activity implements OnClickListener{

	GridView mGridView;
	DateAdapter adapter;
	ArrayList<CalData> arrData;
	Calendar mCalToday;
	Calendar mCal;
	
	TextView mTextView;
	Button prevButton;
	Button nextButton;
	
	int thisMonth;
	int thisYear;
	int thisDay;
	

	DBOpenHelper helper;
	SQLiteDatabase db;
	EditText edit_name, edit_date;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
				
		// Calendar 객체 생성
		mCalToday = Calendar.getInstance();
		mCal = Calendar.getInstance();
		
		thisMonth = mCal.get(Calendar.MONTH)+1;
		thisYear = mCal.get(Calendar.YEAR);

		
		// 달력 세팅 , 1월은 0이니 +1
		setCalendarDate(thisYear, thisMonth);
		
		prevButton = (Button)findViewById(R.id.prev);
		nextButton = (Button)findViewById(R.id.next);
		mTextView = (TextView)findViewById(R.id.thisYYYYMM);
		
		mTextView.setText(Integer.toString(thisYear)+"."+Integer.toString(thisMonth));

		prevButton.setOnClickListener(this);
		nextButton.setOnClickListener(this);
	
		mGridView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id){
				//Toast.makeText(Schedule.this, ""+arrData.get(position).getDay(), Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Schedule.this, AddSchedule.class); 
								
				String dd = Integer.toString(arrData.get(position).getDay());
				String mm = mTextView.getText().toString().substring(5);
				String yy = mTextView.getText().toString().substring(0,4);
							
				intent.putExtra("year", yy);
				intent.putExtra("month",mm );
				intent.putExtra("day", dd);

				startActivity(intent);

			}
		});
		
		
		helper = new DBOpenHelper(this, "scheduler02.db", null, 1);
		db = helper.getWritableDatabase();
		
		Cursor cursor = db.rawQuery("Select * from Schedule", null);
		startManagingCursor(cursor);
		
		if(cursor.getCount() >0){
		String[] from = {"Title","S_Date"};
		int[] to = { android.R.id.text1, android.R.id.text2};
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
		ListView list = (ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
		}
	}
	
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.prev:
			if(thisMonth > 1)
			{
				thisMonth--;
				setCalendarDate(thisYear, thisMonth);
				mTextView.setText(Integer.toString(thisYear)+"."+Integer.toString(thisMonth));
			}
			else
			{
				thisYear--;
				thisMonth = 12;
				setCalendarDate(thisYear, thisMonth);
				mTextView.setText(Integer.toString(thisYear)+"."+Integer.toString(thisMonth));
			}
			break;
		case R.id.next:
			if(thisMonth < 12)
			{
				thisMonth++;
				setCalendarDate(thisYear, thisMonth);
				mTextView.setText(Integer.toString(thisYear)+"."+Integer.toString(thisMonth));
			}
			else
			{
				thisYear++;
				thisMonth = 1;
				setCalendarDate(thisYear, thisMonth);
				mTextView.setText(Integer.toString(thisYear)+"."+Integer.toString(thisMonth));
			}
			break;
		}
	}	
	
	public void setCalendarDate(int year, int month){
		arrData = new ArrayList<CalData>();
		
		// 1일에 맞는 요일을 세팅하기 위한 설정
		mCalToday.set(mCal.get(Calendar.YEAR), month-1, 1);
		// 시작요일이 일요일이아니면 그 만큰 빈 공백을 추가.
		int startday = mCalToday.get(Calendar.DAY_OF_WEEK); //현재 요일 (일요일은 1, 토요일은 7)
		if(startday != 1)
		{
			for(int i=0; i<startday-1; i++)
			{
				arrData.add(null);
			}
		}
		
		// 요일은 +1해야 되기때문에 달력에 요일을 세팅할때에는 -1 해준다.
		mCal.set(Calendar.MONTH, month-1);
		
		for (int i = 0; i < mCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
			mCalToday.set(mCal.get(Calendar.YEAR), month-1, (i+1));
			arrData.add(new CalData((i+1), mCalToday.get(Calendar.DAY_OF_WEEK)));
		}
				
		adapter = new DateAdapter(this, arrData);
		
		mGridView = (GridView)findViewById(R.id.calGrid);
		mGridView.setAdapter(adapter);
		

		
	}
}

// GridView와 연결해주기위한 어댑터 구성
class DateAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<CalData> arrData;
	private LayoutInflater inflater;
	
	public DateAdapter(Context c, ArrayList<CalData> arr) {
		this.context = c;
		this.arrData = arr;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return arrData.size();
	}
	
	public Object getItem(int position) {
		return arrData.get(position);
	}
	
	public long getItemId(int position) {
		return position;
	}

	//날짜별로 색상 맞추기
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.viewitem, parent, false);
		}
		
		TextView ViewText = (TextView)convertView.findViewById(R.id.ViewText);
		if(arrData.get(position) == null)
			ViewText.setText("");
		else
		{
			ViewText.setText(arrData.get(position).getDay()+"");
			if(arrData.get(position).getDayofweek() == 1)
			{
				ViewText.setTextColor(Color.RED);
			}
			else if(arrData.get(position).getDayofweek() == 7)
			{
				ViewText.setTextColor(Color.BLUE);
			}
			else
			{
				ViewText.setTextColor(Color.BLACK);
			}
		}
		
		return convertView;
		
	}

}