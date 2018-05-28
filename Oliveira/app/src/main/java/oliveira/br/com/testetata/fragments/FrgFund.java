package oliveira.br.com.testetata.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import oliveira.br.com.testetata.webrequests.RequestFund;
import oliveira.br.com.testetata.R;
import oliveira.br.com.testetata.model.Fund;

public class FrgFund extends Fragment {

    private ProgressDialog pDialog;

    public FrgFund(){}

    String jsonStr;

    private TextView txtTitle;
    private TextView txtFundName;
    private TextView txtWhatIs;
    private TextView txtDefinition;
    private TextView txtRiskTitle;
    private TextView txtRisk;
    private TextView txtInfoTitle;
    private LinearLayout lnlInfo;
    private LinearLayout lnlDownInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fund, container, false);
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtFundName = (TextView) view.findViewById(R.id.txtFundName);
        txtWhatIs = (TextView) view.findViewById(R.id.txtWhatIs);
        txtDefinition = (TextView) view.findViewById(R.id.txtDefinition);
        txtRiskTitle = (TextView) view.findViewById(R.id.txtRiskTitle);
        txtRisk = (TextView) view.findViewById(R.id.txtRisk);
        txtInfoTitle = (TextView) view.findViewById(R.id.txtInfoTitle);
        lnlInfo = (LinearLayout) view.findViewById(R.id.lnlInfo);
        lnlDownInfo = (LinearLayout) view.findViewById(R.id.lnlDownInfo);

        new getFund().execute();

        return view;
    }

    private class getFund extends AsyncTask<Void, Void, Void> {

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
            RequestFund rt = new RequestFund(getContext());
            jsonStr = rt.getFund();
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

        Fund fund = RequestFund.readJSONPositionsSync(jsonStr);

        txtTitle.setText(fund.getTitle());
        txtFundName.setText(fund.getFundName());
        txtWhatIs.setText(fund.getWhatIs());
        txtDefinition.setText(fund.getDefinition());
        txtRiskTitle.setText(fund.getRiskTitle());
        txtRisk.setText(fund.getRisk());
        txtInfoTitle.setText(fund.getInfoTitle());

        List<String> lstInfo = new ArrayList<>();
        lstInfo = fund.getInfo();

        if (lstInfo != null){
            for (int i=0; i<lstInfo.size(); i++) {
                String[] infoData = lstInfo.get(i).toString().split(";");

                View layout_info = getActivity().getLayoutInflater().inflate(R.layout.layout_fund_info, null);
                TextView txtName = (TextView)layout_info.findViewById(R.id.txtName);
                TextView txtData = (TextView)layout_info.findViewById(R.id.txtData);

                txtName.setText(infoData[0].toString());
                txtData.setText(infoData[1].toString());

                lnlInfo.addView(layout_info);

             }
        }

        List<String> lstDownInfo = new ArrayList<>();
        lstDownInfo = fund.getDownInfo();

        if (lstDownInfo != null){
            for (int i=0; i<lstDownInfo.size(); i++) {
                String[] infoDownData = lstDownInfo.get(i).toString().split(";");

                View layout_info = getActivity().getLayoutInflater().inflate(R.layout.layout_fund_info, null);
                TextView txtName = (TextView)layout_info.findViewById(R.id.txtName);
                TextView txtData = (TextView)layout_info.findViewById(R.id.txtData);

                txtName.setText(infoDownData[0].toString());
                txtData.setText(infoDownData[1].toString());

                lnlDownInfo.addView(layout_info);

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