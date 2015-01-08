package kr.ac.pknu.doyourself;

import com.example.calendar.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class Todo extends Activity implements OnClickListener {

   Button plus1; 
   Button plus2;
   Button plus3;

   @Override 
   protected void onCreate(Bundle savedInstanceState) 
   { super.onCreate(savedInstanceState); 
   setContentView(R.layout.todo); 
   
   plus1 = (Button)findViewById(R.id.plus1);
   plus2 = (Button)findViewById(R.id.plus2);
   plus3 = (Button)findViewById(R.id.plus3);
   
   plus1.setOnClickListener(this);
   plus2.setOnClickListener(this);
   plus3.setOnClickListener(this);
   
   }

   @Override
   public void onClick(View arg0) {
      // TODO Auto-generated method stub
      
      LinearLayout layout=(LinearLayout)View.inflate(Todo.this,R.layout.insert_todo,null);   //layoutÀ» ºä·Î °®´Â ÆË¾÷Ã¢¸¸µé±â
      final PopupWindow popup = new PopupWindow(layout,600, 500, true);            //È­¸é Á¤°¡¿îµ¥ ÆË¾÷Ã¢ ¶ç¿ì±â

      popup.showAtLocation(layout, Gravity.CENTER,0,0);
      Button Insert = (Button)layout.findViewById(R.id.Insert);
      Button Cancel = (Button)layout.findViewById(R.id.Cancel);
      final EditText edttodo=(EditText)layout.findViewById(R.id.edttodo);

      //Insert ´­·¶À»¶§
      Insert.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v){
            String msg = edttodo.getText().toString();
            Toast.makeText(Todo.this,msg,Toast.LENGTH_SHORT).show();
            //ÆË¾÷Ã¢ ´Ý±â
            popup.dismiss();
         }
      });
      Cancel.setOnClickListener(new View.OnClickListener(){
         @Override
         public void onClick(View v){
            //ÆË¾÷Ã¢ ´Ý±â
            popup.dismiss();
         }
      });
   }



}