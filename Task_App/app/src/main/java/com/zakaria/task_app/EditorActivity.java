package com.zakaria.task_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.thebluealliance.spectrum.SpectrumPalette;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorActivity extends AppCompatActivity{

        EditorView view;
        EditText et_title, et_task;
        Menu actionMenu;
        SpectrumPalette palette;
        ProgressDialog progressDialog;
        ApiInterface apiInterface;
        int color, id;
        String title,task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        et_title = findViewById(R.id.title);
        et_task = findViewById(R.id.task);
        palette = findViewById(R.id.palette);

        palette.setOnColorSelectedListener(
                clr -> color = clr
        );

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        //presenter = new EditorPresenter(this);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        title = intent.getStringExtra("title");
        task = intent.getStringExtra("task");
        color = intent.getIntExtra("color",0);

        setDataFromIntentExtra();
    }

        public void  setDataFromIntentExtra(){
            if (id != 0) {
                et_title.setText(title);
                et_task.setText(task);
                palette.setSelectedColor(color);

                getSupportActionBar().setTitle("Update Task");
                readMode();
            } else {
                palette.setSelectedColor(getResources().getColor(R.color.white));
                color = getResources().getColor(R.color.white);
                editMode();
            }

        }

    private void editMode() {
        et_title.setFocusableInTouchMode(true);
        et_task.setFocusableInTouchMode(true);
        palette.setEnabled(true);
    }

    private void readMode() {
        et_title.setFocusableInTouchMode(false);
        et_task.setFocusableInTouchMode(false);
        et_title.setFocusable(false);
        et_task.setFocusable(false);
        palette.setEnabled(false);
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
        String title = et_title.getText().toString().trim();
        String task = et_task.getText().toString().trim();
        int color = this.color;

        switch (item.getItemId()) {
            case R.id.save:
                //Save

                if(title.isEmpty()){
                    et_title.setError("please enter a title");
                }else if(task.isEmpty()){
                    et_task.setError("please enter content of task");
                }else{
                    saveTask(title,task,color);
                }
               return true;
                //edit :
            case R.id.edit:

                editMode();
                actionMenu.findItem(R.id.edit).setVisible(false);
                actionMenu.findItem(R.id.delete).setVisible(false);
                actionMenu.findItem(R.id.save).setVisible(false);
                actionMenu.findItem(R.id.update).setVisible(true);

                return true;

            //Update
            case R.id.update:

                if (title.isEmpty()) {
                    et_title.setError("Please enter a title");
                } else if (task.isEmpty()) {
                    et_task.setError("Please enter content of task");
                } else {
                    updateTask(id, title, task, color);
                }
                return true;
            //delete :
           case R.id.delete:

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Confirm !");
                alertDialog.setMessage("Are you sure?");
                alertDialog.setNegativeButton("Yes", (dialog, which) -> {
                    dialog.dismiss();
                    deleteTask(id);
                });
                alertDialog.setPositiveButton("Cancel",
                        (dialog, which) -> dialog.dismiss());

                alertDialog.show();

                //return true;
            default:
                return super.onOptionsItemSelected(item);
                }
    }

    private void deleteTask(int id) {
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Task> call = apiInterface.deleteTask(id);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NonNull Call<Task> call,@NonNull Response<Task> response) {
                hideProgress();
                if(response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if(success){
                        onRequestSuccess(response.body().getMessage());

                    }else{
                       onRequestError(response.body().getMessage());

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Task> call,@NonNull Throwable t) {
                hideProgress();
                onRequestError(t.getLocalizedMessage());
            }
        });
    }

    public void showProgress() {
        progressDialog.show();
    }


    public void hideProgress() {
        progressDialog.hide();
    }


    public void onRequestSuccess(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish(); //back to main activity
    }


    public void onRequestError(String message) {
        Toast.makeText(EditorActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    private void saveTask(final String title ,final String task ,final int color) {
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Task> call = apiInterface.saveTask(title,task,color);

        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NonNull Call<Task> call,@NonNull Response<Task> response) {
                progressDialog.dismiss();
                if(response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if(success){
                       // view.onAddSuccess(response.body().getMessage());
                        Toast.makeText(EditorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish(); //back to main activity*/
                    }else{
                        //view.onAddError(response.body().getMessage());
                        Toast.makeText(EditorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        // if error , it will stay at the same page
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Task> call,@NonNull Throwable t) {
                progressDialog.dismiss();
                //view.onAddError(response.body().getMessage());*/
                Toast.makeText(EditorActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

      private void updateTask(int id,String title,String task,int color) {
        progressDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Task> call = apiInterface.updateTask(id, title, task, color);
        call.enqueue(new Callback<Task>() {
            @Override
            public void onResponse(@NonNull Call<Task> call, @NonNull Response<Task> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();
                    if (success) {
                        Toast.makeText(EditorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(EditorActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
            @Override
            public void onFailure(@NonNull Call<Task> call, @NonNull Throwable t) {
                progressDialog.dismiss();
                //view.onAddError(response.body().getMessage());*/
                Toast.makeText(EditorActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
