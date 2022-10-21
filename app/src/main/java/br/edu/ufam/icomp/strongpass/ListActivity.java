package br.edu.ufam.icomp.strongpass;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PasswordsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PasswordsAdapter(this);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        adapter.update();
        adapter.notifyDataSetChanged();
    }

    class PasswordsAdapter extends RecyclerView.Adapter<PasswordsViewHolder> {
        private Context context;
        private ArrayList<Password> passwords;
        PasswordDAO passwordDAO;

        public PasswordsAdapter(Context context) {
            this.context = context;
            passwordDAO = new PasswordDAO(context);
            update();
        }

        public void update() { passwords = passwordDAO.getList(); }

        public PasswordsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ConstraintLayout v = (ConstraintLayout) LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            PasswordsViewHolder vh = new PasswordsViewHolder(v, context);
            return vh;
        }

        public void onBindViewHolder(PasswordsViewHolder holder, int position) {
            holder.password.setText(passwords.get(position).getPassword());
            holder.login.setText(passwords.get(position).getLogin());
            holder.id = passwords.get(position).getId();
        }

        public int getItemCount() { return passwords.size(); }
    }


    class PasswordsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Context context;
        public TextView login, password;
        public int id;

        public PasswordsViewHolder(ConstraintLayout v, Context context) {
            super(v);
            this.context = context;
            password = v.findViewById(R.id.itemName);
            login = v.findViewById(R.id.itemLogin);
            v.setOnClickListener(this);
        }

        public void onClick(View v) {
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("passwordId", this.id);
            context.startActivity(intent);
        }
    }

    public void buttonAddClick(View view) {
        Intent intent = new Intent(this, EditActivity.class);
        startActivity(intent);

    }

}



