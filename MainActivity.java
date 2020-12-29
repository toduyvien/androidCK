package com.example.bai1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private final ArrayList<User> userList = new ArrayList<User>();
    private Adapter listViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=findViewById(R.id.lv_user);

        Sqlite db = new Sqlite( this);

        List<User> list=db.getAllUsers();
        userList.addAll(list);



        listViewAdapter = new Adapter(this, R.layout.adapter_layout,userList);
        listView.setAdapter(listViewAdapter);

        registerForContextMenu(listView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.munu_create:
                Intent intent = new Intent(this, MainActivity2.class);

                // Start AddEditNoteActivity, (with feedback).
                this.startActivityForResult(intent, 1000);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select action");

        menu.add(0, 111 , 0, "View Note");
        menu.add(0, 222 , 1, "Create Note");
        menu.add(0, 333 , 2, "Edit Note");
        menu.add(0, 444, 3, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        View v =listView.getChildAt(info.position);
        TextView et_ten=v.findViewById(R.id.et_tenadapter);
        TextView et_sdt=v.findViewById(R.id.et_sdtadapter);
        TextView et_matkhau=v.findViewById(R.id.et_matkhauadapter);
        String ten =et_ten.getText().toString();
        String sdt=et_sdt.getText().toString();
        String matkhau=et_matkhau.getText().toString();
        final User selecteduser=new User();
        selecteduser.setTen(ten);
        selecteduser.setSdt(sdt);
        selecteduser.setMatkhau(matkhau);


        if(item.getItemId() == 111){
            Toast.makeText(getApplicationContext(),selecteduser.getTen(), Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == 222){
            Intent intent = new Intent(this, MainActivity2.class);

            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent, 1000);
        }
        else if(item.getItemId() == 333 ){
            Intent intent = new Intent(this, MainActivity2.class);
            intent.putExtra("user",  selecteduser);

            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent,1000);
        }
        else if(item.getItemId() == 444){
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    .setMessage(selecteduser.getTen()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteuser(selecteduser);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }
    private void deleteuser(User user)  {


        int row =getContentResolver().delete(MyContentProvider.CONTENT_URI, "sdt" + " = ?",
                new String[] {String.valueOf(user.getSdt())});
        Toast.makeText(this, "Delete thanh cong", Toast.LENGTH_SHORT).show();

        // Refresh ListView.
        userList.remove(user);
        listViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == 1000) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            if (needRefresh) {
                userList.clear();
                Sqlite db = new Sqlite( this);
                List<User> list = db.getAllUsers();
                userList.addAll(list);


                // Notify the data change (To refresh the ListView).
                this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }
}