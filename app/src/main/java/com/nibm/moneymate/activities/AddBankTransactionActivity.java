package com.nibm.moneymate.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.nibm.moneymate.R;
import com.nibm.moneymate.database.BankDatabaseHelper;
import com.nibm.moneymate.model.BankAccount;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddBankTransactionActivity extends AppCompatActivity {

    EditText etAmount, etNote;
    ImageView ivSlipPreview;
    RadioButton rbDeposit;
    BankDatabaseHelper dbHelper;
    int accountId;
    String slipImagePath = null;
    Uri cameraImageUri;

    ActivityResultLauncher<Intent> cameraLauncher;
    ActivityResultLauncher<Intent> galleryLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank_transaction);

        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        ivSlipPreview = findViewById(R.id.ivSlipPreview);
        rbDeposit = findViewById(R.id.rbDeposit);
        dbHelper = new BankDatabaseHelper(this);

        accountId = getIntent().getIntExtra("account_id", -1);

        findViewById(R.id.btnAttachSlip).setOnClickListener(v -> showPickerDialog());
        findViewById(R.id.btnSaveTransaction).setOnClickListener(v -> saveTransaction());

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        saveImageFromUri(cameraImageUri);
                    }
                });

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri selectedUri = result.getData().getData();
                        saveImageFromUri(selectedUri);
                    }
                });
    }

    private void showPickerDialog() {
        String[] options = {"Take Photo", "Choose from Gallery"};
        new android.app.AlertDialog.Builder(this)
                .setTitle("Attach Bank Slip")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) openCamera();
                    else openGallery();
                }).show();
    }

    private void openCamera() {
        File photoFile = new File(getExternalFilesDir("slips"),
                "slip_" + System.currentTimeMillis() + ".jpg");
        cameraImageUri = FileProvider.getUriForFile(this,
                getPackageName() + ".fileprovider", photoFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImageUri);
        slipImagePath = photoFile.getAbsolutePath();
        cameraLauncher.launch(intent);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(intent);
    }

    private void saveImageFromUri(Uri uri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            File file = new File(getExternalFilesDir("slips"),
                    "slip_" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
            fos.close();

            slipImagePath = file.getAbsolutePath();
            ivSlipPreview.setVisibility(ImageView.VISIBLE);
            ivSlipPreview.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(this, "Failed to save slip image", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveTransaction() {
        String amountStr = etAmount.getText().toString().trim();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Enter amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount = Double.parseDouble(amountStr);

        if (amount <= 0) {
            Toast.makeText(this, "Amount must be greater than zero", Toast.LENGTH_SHORT).show();
            return;
        }

        String type = rbDeposit.isChecked() ? "DEPOSIT" : "WITHDRAW";
        String note = etNote.getText().toString().trim();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date());

        // current account eka find karanawa balance check karanna
        BankAccount currentAccount = dbHelper.getAccountById(accountId);
        if (currentAccount == null) {
            Toast.makeText(this, "Account not found", Toast.LENGTH_SHORT).show();
            return;
        }

        // WITHDRAW validation - balance ekata wada wedi wenna ba
        if (type.equals("WITHDRAW") && amount > currentAccount.getBalance()) {
            Toast.makeText(this,
                    "Insufficient balance! Current balance: Rs. " +
                            String.format("%.2f", currentAccount.getBalance()),
                    Toast.LENGTH_LONG).show();
            return;
        }

        dbHelper.addTransaction(accountId, type, amount, date, note, slipImagePath);

        double newBalance = type.equals("DEPOSIT")
                ? currentAccount.getBalance() + amount
                : currentAccount.getBalance() - amount;
        dbHelper.updateBalance(accountId, newBalance);

        Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}