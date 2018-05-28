package oliveira.br.com.testetata.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import oliveira.br.com.testetata.webrequests.RequestForm;
import oliveira.br.com.testetata.model.Form;
import oliveira.br.com.testetata.utils.MaskEditUtil;
import oliveira.br.com.testetata.R;

public class FrgRegister extends Fragment {

    private ProgressDialog pDialog;

    public FrgRegister(){}

    private LinearLayout lnlAvaliacao;
    private LinearLayout lnlButton;
    String jsonStr;
    ArrayList<EditText> arr_editText;
    ArrayList<Form> arr_form;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        lnlAvaliacao = (LinearLayout) view.findViewById(R.id.lnlAvaliacao);
        lnlButton = (LinearLayout) view.findViewById(R.id.lnlButton);

        new getForm().execute();

        return view;
    }

    private class getForm extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage(getResources().getString(R.string.aguarde));
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            RequestForm rt = new RequestForm(getContext());
            jsonStr = rt.getCells();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
                setFields();
        }
    }

    public void setFields() {

        Context ctx = getActivity();

        List<Form> list = RequestForm.readJSONPositionsSync(jsonStr);
        Form c;
        arr_editText = new ArrayList<EditText>(20);
        arr_form = new ArrayList<Form>();
        arr_editText = insertArray(arr_editText,20);

        for (int i = 0; i < list.size(); i++) {
            c = new Form();
            c = list.get(i);
            arr_form.add(c);

            int type = c.getType();
            switch(type) {
                case 1: //Field
                    lnlAvaliacao.addView(editText(ctx,c.getId(),c.getMessage(),c.getTopSpacing(),c.getHidden(),c.getTypefield()));
                    break;
                case 2: //Text
                    lnlAvaliacao.addView(textview(ctx,c.getId(),c.getMessage(),c.getTopSpacing(),c.getHidden()));
                     break;
                case 3: //Image

                    break;
                case 4: //Checkbox
                    lnlAvaliacao.addView(checkbox(ctx,c.getId(),c.getMessage(),c.getTopSpacing(),c.getHidden(),c.getShow()));
                    break;
                case 5: //Send
                    lnlButton.addView(button(ctx,c.getId(),c.getMessage(),c.getTopSpacing(),c.getHidden(),c.getShow()));
                    break;
                default:

            }

        }

    }

    public ArrayList insertArray(ArrayList arr,Integer size){
        for (int i = 0; i < size; i++) {
            arr.add(null);
        }
        return arr;
    }
    public void activeField(EditText et){
        if (et.getVisibility() == View.GONE){
            et.setVisibility(View.VISIBLE);
            et.setFocusable(true);
        }else{
            et.setVisibility(View.GONE);
        }
    }

    //EditText
    public EditText editText(Context ctx,int id, String message, int topSpacing, String hidden, String TypeField){
        final EditText campo = new EditText(ctx);
        campo.setTag(id);
        campo.setHint(message);
        campo.setTextSize(16);

        if(TypeField.equals("telnumber")){
            campo.addTextChangedListener(MaskEditUtil.mask(campo, "(##)#####-####"));
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        params.topMargin = topSpacing;
        campo.setLayoutParams(params);

        if(hidden=="true"){
            campo.setVisibility(View.GONE);
        }
        arr_editText.add(id,campo);

        return campo;
    }

    //Textview
    public TextView textview(Context ctx,int id,String message, int topSpacing,String hidden){
        TextView label_txt = new TextView(ctx);
        label_txt.setTag(id);
        label_txt.setTextColor(getResources().getColor(R.color.colorGreen));
        label_txt.setTextSize(16);
        label_txt.setTypeface(null, Typeface.BOLD);
        label_txt.setText(message);

        LinearLayout.LayoutParams params_label = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
        params_label.topMargin = topSpacing;
        label_txt.setLayoutParams(params_label);

        if(hidden=="true"){
            label_txt.setVisibility(View.GONE);
        }
        return label_txt;
    }

    //Checkbox
    public CheckBox checkbox(Context ctx,int id,String message, int topSpacing,String hidden, String show){
        CheckBox ch = new CheckBox(ctx);
        ch.setText(message);
        ch.setTag(id);

        LinearLayout.LayoutParams params_checkbox = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);

        params_checkbox.topMargin = topSpacing;
        ch.setLayoutParams(params_checkbox);

        if(hidden=="true"){
            ch.setVisibility(View.GONE);
        }

        final EditText show_field = arr_editText.get(Integer.parseInt(show));
        if (!show.equals("null")) {
            ch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activeField(show_field);
                }
            });
        }
        return ch;
    }

    //Button
    public Button button(Context ctx,int id,String message, int topSpacing,String hidden, String show){
        Button btn = new Button(ctx);

        LinearLayout.LayoutParams params_btn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);

        params_btn.topMargin = topSpacing;
        btn.setLayoutParams(params_btn);

        btn.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        btn.setTextColor(getResources().getColor(R.color.colorWhite));

        btn.setText(message);
        btn.setTag(id);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Boolean valid = validateForm(arr_form, arr_editText);
                if(valid){
                    Toast.makeText(getActivity(), "Formulário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                    clearFields(arr_form, arr_editText);
                }
            }
        });

        if(hidden=="true"){
            btn.setVisibility(View.GONE);
        }
        return btn;
    }

    //Metódo para validação dos campos.
    public boolean validateForm(ArrayList<Form> frm, ArrayList<EditText> arr_editText){

        boolean valida = true;

        for(int i=0; i < frm.size(); i++){
            Form form = new Form();
            form = frm.get(i);
            Integer id = form.getId();

            EditText editText = arr_editText.get(id);

            String value = "";
            String TypeField = form.getTypefield();

            if(form.getType()==1){
                if (editText.getVisibility() == View.VISIBLE){
                    switch(TypeField) {
                        case "1": //Text
                            value = editText.getText().toString();
                            if (value.equals("")){
                                valida = false;
                                editText.setError("Campo não preenchido");
                            }
                            break;
                        case "telnumber": //Tel Number
                            value = editText.getText().toString();
                            if (value.equals("")){
                                valida = false;
                                editText.setError("Campo não preenchido");
                            }
                            break;
                        case "3": //E-mail
                            value = editText.getText().toString();
                            if (value.equals("")){
                                valida = false;
                                editText.setError("Campo não preenchido");
                            }
                            if(!isEmail(value)){
                                valida = false;
                                editText.setError("e-mail inválido");
                            }
                            break;

                    }
                }

            }

        }

        return valida;

    }

    private boolean isEmail(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }

    private void clearFields(ArrayList<Form> frm, ArrayList<EditText> arr_editText){
        for(int i=0; i < frm.size(); i++) {
            Form form = new Form();
            form = frm.get(i);
            Integer id = form.getId();
            EditText editText = arr_editText.get(id);
            if(form.getType()==1){
                editText.setText("");
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}