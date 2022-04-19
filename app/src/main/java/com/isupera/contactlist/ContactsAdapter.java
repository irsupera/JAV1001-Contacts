package com.isupera.contactlist;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

        import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

    // create a variable for array list and context
    private ArrayList<Contacts> contactsArrayList;
    private Context context;

    private EditText edt_name, edt_mobile, edt_email;
    private TextView txv_dlg;
    private Button btn_update;

    // create a constructor for our variables
    public ContactsAdapter(ArrayList<Contacts> contactsArrayList, Context context) {
        this.contactsArrayList = contactsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ContactsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsAdapter.ViewHolder holder, int position) {
        // set data to our views of recycler view
        Contacts model = contactsArrayList.get(position);
        holder.name.setText(model.getName());
        holder.mobile.setText(model.getMobile());
        holder.email.setText(model.getEmail());

        // set on click listener for view item
        holder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.add_update);

                edt_name = dialog.findViewById(R.id.edt_name);
                edt_mobile = dialog.findViewById(R.id.edt_mobile);
                edt_email = dialog.findViewById(R.id.edt_email);
                txv_dlg = dialog.findViewById(R.id.txv_dlg);

                btn_update = dialog.findViewById(R.id.btn_action);

                edt_name.setText(contactsArrayList.get(position).getName());
                edt_mobile.setText(contactsArrayList.get(position).getMobile());
                edt_email.setText(contactsArrayList.get(position).getEmail());

                txv_dlg.setText("Update Contact");
                btn_update.setText("Update");

                // set on click listener for update button
                btn_update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (edt_name.getText().toString().equals("")) {
                            Toast.makeText(context, "Enter name", Toast.LENGTH_SHORT).show();
                        }else if (edt_mobile.getText().toString().equals("")) {
                            Toast.makeText(context, "Enter mobile", Toast.LENGTH_SHORT).show();
                        }else if (edt_email.getText().toString().equals("")) {
                            Toast.makeText(context, "Enter email", Toast.LENGTH_SHORT).show();
                        }else {
                            // below line is use to change data to array list
                            contactsArrayList.set(position, new Contacts(edt_name.getText().toString(), edt_mobile.getText().toString(), edt_email.getText().toString()));
                            // notifying adapter when data changed
                            notifyItemChanged(position);
                            dialog.dismiss();
                        }
                    }
                });

                dialog.show();
            }
        });

        // set on long click event for view item
        holder.row.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // create alert dialog for deletion
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle("Delete Contact")
                        .setMessage("Are you sure you want to delete?")
                        .setIcon(R.drawable.ic_baseline_delete_24)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // remove the item from the array list
                                contactsArrayList.remove(position);
                                notifyItemRemoved(position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        // return the size of array list
        return contactsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // create variables for our views
        private TextView name, mobile, email;
        private RelativeLayout row;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initialize our views with their ids
            name = itemView.findViewById(R.id.txv_name);
            mobile = itemView.findViewById(R.id.txv_mobile);
            email = itemView.findViewById(R.id.txv_email);
            row = itemView.findViewById(R.id.row);
        }
    }
}