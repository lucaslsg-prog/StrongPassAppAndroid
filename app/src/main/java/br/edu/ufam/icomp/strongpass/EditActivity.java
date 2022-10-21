package br.edu.ufam.icomp.strongpass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EditActivity extends AppCompatActivity {

    private PasswordDAO passwordDAO;
    private int passwordId;
    private TextView editName, editLogin, editPassword, editNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        editName = findViewById(R.id.addName);
        editLogin = findViewById(R.id.addLogin);
        editPassword = findViewById(R.id.addPassword);
        editNotes = findViewById(R.id.addNotes);
        passwordDAO = new PasswordDAO(this);

        Intent intent = getIntent();
        passwordId = intent.getIntExtra("passwordId", -1);

        // Verifica se uma senha foi passada como parâmetro
        if (passwordId != -1) {
            Password password = passwordDAO.get(passwordId);
            editName.setText(password.getName());
            editLogin.setText(password.getLogin());
            editPassword.setText(password.getPassword());
            editNotes.setText(password.getNotes());
        }
    }
    public void saveClicked(View view) {
        Password password = new Password(passwordId, editName.getText().toString(),
                editLogin.getText().toString(), editPassword.getText().toString(),
                editNotes.getText().toString());

        boolean result;
        if (passwordId == -1) result = passwordDAO.add(password);
        else result = passwordDAO.update(password);
        if (result) finish();
    }
    public void generateStrongPassword(View view){
        // String com diferentes tipos de caracteres
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "@!+/*-&$#"
                                    + "abcdefghijklmnopqrstuvxyz";

        // Foi instanciada a classe StringBuilder onde o sb se torna uma espécide buffer pra definir
        //o tamanho maximo da senha que no caso foi definida para ser de 8 caracteres
        StringBuilder sb = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {


            // Seleciona de forma randomica 8 caracteres dentre os que estão da string
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // adiciona cada caracter escolhido em sb no formato de lista que depois é reconvertido
            //para string usando o método toString()
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        //Foi gerado um AlertDialog para informar a sugestão de senha
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("Sugestão de senha forte: "+ sb.toString())
                .setNeutralButton("Ok",null)
                .show();

        //Em seguida a sugestão já foi inserida no campo de senha caso o usuário queira usar, basta salvar.
        EditText newPass = (EditText) findViewById(R.id.addPassword);
        newPass.setText(sb.toString());
        //Foi usado recurso de logcat para validar
        Log.i("Senha randomica",sb.toString());
    }
}