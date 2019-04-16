package com.example.hassannaqvi.fas.ui.tool1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.hassannaqvi.fas.JSON.GeneratorClass;
import com.example.hassannaqvi.fas.R;
import com.example.hassannaqvi.fas.RMOperations.crudOperations;
import com.example.hassannaqvi.fas.core.CONSTANTS;
import com.example.hassannaqvi.fas.core.MainApp;
import com.example.hassannaqvi.fas.data.DAO.FormsDAO;
import com.example.hassannaqvi.fas.data.entities.Forms;
import com.example.hassannaqvi.fas.databinding.ActivitySectionFBinding;
import com.example.hassannaqvi.fas.ui.EndingActivity;
import com.example.hassannaqvi.fas.validation.ClearClass;
import com.example.hassannaqvi.fas.validation.ValidatorClass;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SectionGActivity extends AppCompatActivity {

    private ActivitySectionFBinding bi;
    private Forms fc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bi = DataBindingUtil.setContentView(this, R.layout.activity_section_g);
        bi.setCallback(this);

        setContentUI();
    }

    private void setContentUI() {
        this.setTitle(R.string.section7);
        fc = (Forms) getIntent().getSerializableExtra(CONSTANTS._URI_FC_OBJ);


        String getSurvey = MainApp.getParamValue(this, CONSTANTS._URI_DATAMAP_SURVEY_TYPE);
        if (!getSurvey.equals("0"))
            bi.fas01f00.check(bi.fas01f00.getChildAt(Integer.valueOf(getSurvey) - 1).getId());

        bi.fas01f001.setText(MainApp.getParamValue(this, CONSTANTS._URI_DATAMAP_HF_NO));
    }

    public void BtnContinue() {

        if (!formValidation())
            return;

        SaveDraft();
        if (UpdateDB()) {
            MainApp.endActivitySetRouting(this, this, EndingActivity.class, true, fc);
        } else {
            Toast.makeText(this, "Error in updating db!!", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean UpdateDB() {
        try {

            Long longID = new crudOperations(this, FormsDAO.class.getName(), "formsDao", "updateForm", fc).execute().get();
            return longID == 1;

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void SaveDraft() {
        JSONObject Json = GeneratorClass.getContainerJSON(bi.fldGrpllSecF, true);
        fc.setSa6(String.valueOf(Json));
    }

    private boolean formValidation() {
        return ValidatorClass.EmptyCheckingContainer(this, bi.fldGrpllSecF);
    }

    public void BtnEnd() {
        MainApp.endActivityDirectRouting(this, this, EndingActivity.class, false, fc);
    }
}
