package com.example.employees.activity.editor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.employees.R;
import com.thebluealliance.spectrum.SpectrumPalette;

public class EditorActivity extends AppCompatActivity implements EditorView {

    EditText et_name, et_age, et_gender;
    ProgressDialog progressDialog;
    SpectrumPalette palette;

    EditorPresenter presenter;

    int color, id, age;
    String name, gender;

    Menu actionMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        et_name = findViewById(R.id.name);
        et_age = findViewById(R.id.age);
        et_gender = findViewById(R.id.gender);
        palette = findViewById(R.id.palette);

        palette.setOnColorSelectedListener(
                clr -> color = clr
        );


//      Progress Dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        presenter = new EditorPresenter(this);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        age = intent.getIntExtra("age", 0);
        color = intent.getIntExtra("color", 0);
        gender = intent.getStringExtra("gender");

        setDataFromIntentExtra();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        actionMenu = menu;

        if (id != 0) {
            actionMenu.findItem(R.id.edit).setVisible(true);
            actionMenu.findItem(R.id.delete).setVisible(true);
            actionMenu.findItem(R.id.save).setVisible(false);
            actionMenu.findItem(R.id.update).setVisible(false);
        } else {
            actionMenu.findItem(R.id.edit).setVisible(false);
            actionMenu.findItem(R.id.delete).setVisible(false);
            actionMenu.findItem(R.id.save).setVisible(true);
            actionMenu.findItem(R.id.update).setVisible(false);
        }


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String name = et_name.getText().toString().trim();
        int age = this.age;
        String gender = et_gender.getText().toString().trim();
        int color = this.color;

        switch (item.getItemId()) {
            case R.id.save:
                //Save
                if (name.isEmpty()) {
                    et_name.setError("Please enter a name");
                } else if (age == 0) {
                    et_age.setError("Please enter age");
                } else if (gender.isEmpty()){
                    et_gender.setError("Please enter gender");
                } else {
                    presenter.saveEmployee(name, age, color, gender);
                }
                return true;

            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            case R.id.update:
                //Update

                if (name.isEmpty()) {
                    et_name.setError("Please enter a name");
                } else if (age == 0) {
                    et_age.setError("Please enter age");
                } else if (gender.isEmpty()){
                    et_gender.setError("Please enter gender");
                } else {
                    presenter.updateEmployee(id, name, age, color);
                }

                return true;

            case R.id.delete:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirm !");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setNegativeButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    presenter.deleteEmployee(id);
                });
                alertDialog.setPositiveButton("Cancel",
                        (dialog, which) -> dialog.dismiss());

                alertDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }


    @Override
    public void onRequestSuccess(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish(); //back to main activity
    }

    @Override
    public void onRequestError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    private void setDataFromIntentExtra() {

        if (id != 0) {
            et_name.setText(name);
            et_age.setText(""+age);
            et_gender.setText(gender);
            palette.setSelectedColor(color);

            getSupportActionBar().setTitle("Update");
            readMode();
        } else {
            palette.setSelectedColor(getResources().getColor(R.color.white));
            color = getResources().getColor(R.color.white);
            editMode();
        }

    }

    private void editMode() {
        et_name.setFocusableInTouchMode(true);
        et_age.setFocusableInTouchMode(true);
        et_gender.setFocusableInTouchMode(true);
        palette.setEnabled(true);
    }

    private void readMode() {
        et_name.setFocusableInTouchMode(false);
        et_age.setFocusableInTouchMode(false);
        et_gender.setFocusableInTouchMode(false);
        et_name.setFocusable(false);
        et_age.setFocusable(false);
        et_gender.setFocusable(false);
        palette.setEnabled(false);
    }
}