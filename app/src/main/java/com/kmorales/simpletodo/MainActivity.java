package com.kmorales.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;


    Button bttnAdd;
    EditText etitem;
    RecyclerView rvitems;
    ItemsAdapter ItemAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttnAdd = findViewById(R.id.bttnAdd);
        etitem = findViewById(R.id.etitem);
        rvitems = findViewById(R.id.rvitems);

       loadItems();

        ItemsAdapter.OnLongClicklistener OnLongClicklistener= new ItemsAdapter.OnLongClicklistener() {
            @Override
            public void onItemLongClick(int position) {
                //delete the item from the model
                items.remove(position);
                //Notify the adapter
                ItemAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item was removed!", Toast.LENGTH_SHORT).show();
                saveItem();
            }
        };

        final ItemsAdapter itemsAdapter= new ItemsAdapter(items, OnLongClicklistener);
        rvitems.setAdapter(itemsAdapter);
        rvitems.setLayoutManager(new LinearLayoutManager( this));

        bttnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String todoitem =etitem.getText().toString();
            //Add to the model
            items.add(todoitem);
            //Notify adapter that an item is inserted
            itemsAdapter.notifyItemInserted(items.size()-1);
            etitem.setText("");
                Toast.makeText(getApplicationContext(), "Item was added!", Toast.LENGTH_SHORT).show();
            saveItem();
            }
        });

    }
    private File getDatatFile() {
        return new File(getFilesDir(), "data.txt");
    }
        //This function will load items by reading lines of the data files
        private void loadItems(){
            try{
            items = new ArrayList<>(FileUtils.readLines(getDatatFile(), Charset.defaultCharset()));
        }  catch(IOException e){
                Log.e("MainActivity", "Error reading items", e);
                items=new ArrayList<>();
        }}
        //This function saves time by writing them into the data file
        private void saveItem(){
        try{
        org.apache.commons.io.FileUtils.writeLines(getDatatFile(), items);
            }
        catch(IOException e){
            Log.e("MainActivity", "Error reading items", e);
        }
        }
}