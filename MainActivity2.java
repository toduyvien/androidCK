package com.example.bai1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    private static final int MODE_CREATE = 1;
    private static final int MODE_EDIT = 2;
    static final String uri="content://com.example.bai1";

    private EditText et_sdt;
    private EditText et_ten;
    private EditText et_matkhau;
    private Button btn_them;
    private Button btn_huy;

    private User user;
    private boolean needRefresh;
    private int mode =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        et_sdt=findViewById(R.id.et_sdt);
        btn_huy=findViewById(R.id.btn_huy);
        et_ten=findViewById(R.id.et_ten);
        et_matkhau=findViewById(R.id.et_matkhau);
        btn_them=findViewById(R.id.btn_them);


        Intent intent = this.getIntent();
        user = (User) intent.getParcelableExtra("user");
        if(user!= null)  {
            mode = MODE_EDIT;
            et_ten.setText(user.getTen());
            et_sdt.setText(user.getSdt());
            et_matkhau.setText(user.getMatkhau());

        } else  {
            mode = MODE_CREATE;
        }

        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*


    */            xulythem();
            }
        });

    }
    public void xulythem(){
        Sqlite db = new Sqlite( this);

        String ten = et_ten.getText().toString();
        String sdt = et_sdt.getText().toString();
        String matkhau = et_matkhau.getText().toString();

        if(ten.equals("") || sdt.equals("") || matkhau.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please enter ten & sdt & matkhau", Toast.LENGTH_LONG).show();
        }
        if(mode == MODE_CREATE ) {

            ContentValues values = new ContentValues();
            values.put("sdt", et_sdt.getText().toString());
            values.put("ten", et_ten.getText().toString());
            values.put("matkhau", et_matkhau.getText().toString());

            Uri uri=getContentResolver().insert(MyContentProvider.CONTENT_URI,values);
            Toast.makeText(getBaseContext(), "Insert thanh cong", Toast.LENGTH_SHORT).show();

     //       user= new User(sdt,ten,matkhau);
     //       db.addUser(user);
        } else  {

            ContentValues values = new ContentValues();
            values.put("sdt", et_sdt.getText().toString());
            values.put("ten", et_ten.getText().toString());
            values.put("matkhau", et_matkhau.getText().toString());
            int row=getContentResolver().update(MyContentProvider.CONTENT_URI,values,"sdt" + " = ?",new String[]{String.valueOf(user.getSdt())});
            Toast.makeText(getBaseContext(),"Edit thanh cong", Toast.LENGTH_SHORT).show();

          //  user.setTen(ten);
          //  user.setMatkhau(matkhau);
          //  db.updateUser(user);
        }
        needRefresh = true;
        onBackPressed();
    }
    public void finish() {

        // Create Intent
        Intent data = new Intent();

        // Request MainActivity refresh its ListView (or not).
        data.putExtra("needRefresh", needRefresh);

        // Set Result
        this.setResult(Activity.RESULT_OK, data);
        super.finish();
    }
}