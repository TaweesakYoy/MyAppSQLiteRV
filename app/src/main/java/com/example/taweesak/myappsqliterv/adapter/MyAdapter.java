package com.example.taweesak.myappsqliterv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taweesak.myappsqliterv.Data.Model;
import com.example.taweesak.myappsqliterv.Data.SqliteDatabase;
import com.example.taweesak.myappsqliterv.R;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH>{

    private Context context;
    private List<Model> listModel;

    private SqliteDatabase mDatabase;

    public MyAdapter(Context context, List<Model> listModel) {
        this.context = context;
        this.listModel = listModel;
        mDatabase = new SqliteDatabase(context);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout, parent, false);
        VH vh = new VH(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final Model modelAlarm = listModel.get(position);

        holder.title.setText(modelAlarm.getTitle());
        holder.content.setText(modelAlarm.getContent());

        // Edit Item

        holder.editProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(modelAlarm);
            }
        });

        // Delete Item
        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteAlarm(modelAlarm.getId());

                //refresh the activity page.
                ((Activity)context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });


    }

    @Override
    public int getItemCount() {
        return listModel.size();
    }

    // Edit ---- Start
    private void editTaskDialog(final Model model){
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_alarm_layout, null);

        final EditText titleField = (EditText)subView.findViewById(R.id.enter_title);
        final EditText contentField = (EditText)subView.findViewById(R.id.enter_content);

        if(model != null){
            titleField.setText(model.getTitle());
            contentField.setText(String.valueOf(model.getContent()));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Edit product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT ALARM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String title = titleField.getText().toString();
                final String content = contentField.getText().toString();


                if(TextUtils.isEmpty(title) || TextUtils.isEmpty(content)){
                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                }
                else{
                    mDatabase.updateAlarm(new Model(model.getId(), title, content));
                    //refresh the activity
                    ((Activity)context).finish();
                    context.startActivity(((Activity)context).getIntent());
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    // Edit ---- Finish


    public class VH extends RecyclerView.ViewHolder{

        public TextView title,content;
        public ImageView deleteProduct;
        public  ImageView editProduct;

        public VH(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
            editProduct = (ImageView)itemView.findViewById(R.id.edit_product);
        }
    }
}
