package br.com.appcadastro

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextTel = findViewById<EditText>(R.id.editTextTel)
        val spinnerTipoTeledone = findViewById<Spinner>(R.id.spinnerTipoTel)
        val checkBoxTelefone = findViewById<CheckBox>(R.id.checkBoxTel)
        val checkBoxEmail = findViewById<CheckBox>(R.id.checkBoxEmail)
        val buttonShowMsg = findViewById<Button>(R.id.buttonShowMsg)

        //Configurar o adapter para o Spinner de tipo de Telefone
        val tiposTelefone = arrayOf("Residencial", "Comercial")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, tiposTelefone)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerTipoTeledone.adapter = adapter

        buttonShowMsg.setOnClickListener(View.OnClickListener {
            val nome = editTextName.text.toString()
            val telefone = editTextTel.text.toString()
            val email = editTextEmail.text.toString()
            val tipoTelefone = spinnerTipoTeledone.selectedItem.toString()
            val contatoTelefone = checkBoxTelefone.isChecked
            val contatoEmail = checkBoxEmail.isChecked

            //Realizando validaçao dos campos
            if(nome.isEmpty()) {
                showAlert("Preencha o campo Nome.")
                return@OnClickListener
                //@OnClickListener é uma etiqueta que especifica qual função ou escopo deve ser
                //retornada. Isso é útil quando você tem várias funções anônimas em um mesmo escopo
                //e deseja retornar de uma específica.
            }
            if (!contatoTelefone && !contatoEmail) {
                showAlert("Selecione pelo menos uma preferência de contato (Telefone ou Email).")
                return@OnClickListener
            }
            if (contatoTelefone && telefone.isEmpty()) {
                showAlert("Preencha o campo Telefone ou desmarque a preferência de Telefone.")
                return@OnClickListener
            }
            if (contatoEmail && email.isEmpty()) {
                showAlert("Preencha o campo Email ou desmarque a preferência de Email.")
                return@OnClickListener
            }
            // Construir a mensagem com base nas preferências
            val mensagem = StringBuilder("Nome: $nome\n")

            if (contatoTelefone && !telefone.isEmpty()) {
                mensagem.append("Tipo: $tipoTelefone\n")
                mensagem.append("Telefone: $telefone\n")
            }

            if (contatoEmail && !email.isEmpty()) {
                mensagem.append("Email: $email\n")
            }
            if (checkBoxTelefone.isChecked) {
                mensagem.append("Contato Telefone")
            }
            if (checkBoxEmail.isChecked) {
                mensagem.append("\n Contato Email")
            }

            // Mostrar a mensagem em um Toast
            // Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()

            // Exibir informações em um AlertDialog
            showInfoDialog("Informações Digitadas", mensagem)


        })
    }
    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desculpe! Atenção")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    private fun showInfoDialog(title: String, message: StringBuilder) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}