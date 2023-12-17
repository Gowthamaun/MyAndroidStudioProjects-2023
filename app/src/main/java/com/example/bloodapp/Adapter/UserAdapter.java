package com.example.bloodapp.Adapter;

import android.content.Context;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.PermissionChecker;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bloodapp.Model.User;
import com.example.bloodapp.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private Context context;

    private List<User> list;

    public UserAdapter(Context context,List<User> list)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType)
    {
        return new UserViewHolder(LayoutInflater.from(context).inflate(R.layout.user_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.UserViewHolder holder,int position)
    {
        holder.name.setText(list.get(position).getName());
        holder.email.setText(list.get(position).getEmail());
        holder.phoneNumber.setText(list.get(position).getPhone());
        holder.blood.setText(list.get(position).getBlood());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void FilterList(ArrayList<User> filterList)
    {
        list=filterList;
        notifyDataSetChanged();
    }




    public class UserViewHolder extends RecyclerView.ViewHolder{
        TextView name,email,phoneNumber,blood;

        Button sendSms;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.Name);
            email=itemView.findViewById(R.id.Email);
            phoneNumber=itemView.findViewById(R.id.phoneNumber);
            blood=itemView.findViewById(R.id.blood);
            sendSms=itemView.findViewById(R.id.msgBtn);
            String phno=phoneNumber.getText().toString();
            sendSms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isValid(phoneNumber.getText().toString()))
                    {
                        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
                        {
                            if(PermissionChecker.checkSelfPermission(context.getApplicationContext(), android.Manifest.permission.SEND_SMS)== PermissionChecker.PERMISSION_GRANTED)
                            {
                                sendMessage(phoneNumber.getText().toString());
                            }
                            else {
                                Toast.makeText(context.getApplicationContext(),"Please allow the permission for this app in the settings",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        Toast.makeText(context.getApplicationContext(),"The user Number is not valid",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        private void sendMessage(String phoneNo)
        {
            String sms="There is a need for blood,Please contact "+name.getText().toString()+" and the contact Number is :"+phoneNumber.getText().toString();

            try{
                SmsManager smsManager=SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo,null,sms,null,null);
                Toast.makeText(context.getApplicationContext(),"Message sent Successfully",Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                e.printStackTrace();
                Toast.makeText(context.getApplicationContext(),"Failed to sent message",Toast.LENGTH_SHORT).show();
            }
        }

        private boolean isValid(String number)
        {
            boolean length=number.length()==10;
            boolean letter=true;
            for (int i = 0; i < number.length(); i++) {
                letter=Character.isDigit(number.charAt(i));
            }
            return letter && length;
        }
    }



}
