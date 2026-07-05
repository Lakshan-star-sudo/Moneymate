package com.nibm.moneymate.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.widget.Toast;

import com.nibm.moneymate.model.BankAccount;
import com.nibm.moneymate.model.BankTransaction;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class PdfReportGenerator {

    public static String generateStatement(Context context, BankAccount account,
                                           List<BankTransaction> transactions) {
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(595, 842, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

        int y = 40;
        paint.setTextSize(16);
        paint.setFakeBoldText(true);
        canvas.drawText("MoneyMate - Bank Account Statement", 40, y, paint);

        y += 30;
        paint.setTextSize(12);
        paint.setFakeBoldText(false);
        canvas.drawText("Bank: " + account.getBankName(), 40, y, paint); y += 20;
        canvas.drawText("Account No: " + account.getAccountNumber(), 40, y, paint); y += 20;
        canvas.drawText("Holder: " + account.getHolderName(), 40, y, paint); y += 20;
        canvas.drawText("Current Balance: Rs. " + String.format("%.2f", account.getBalance()), 40, y, paint);

        y += 40;
        paint.setFakeBoldText(true);
        canvas.drawText("Date", 40, y, paint);
        canvas.drawText("Type", 200, y, paint);
        canvas.drawText("Amount", 320, y, paint);
        canvas.drawText("Note", 430, y, paint);
        paint.setFakeBoldText(false);
        y += 15;

        double totalDeposit = 0, totalWithdraw = 0;

        for (BankTransaction t : transactions) {
            if (y > 800) break; // simple single-page limit for this example
            canvas.drawText(t.getDate(), 40, y, paint);
            canvas.drawText(t.getType(), 200, y, paint);
            canvas.drawText(String.format("%.2f", t.getAmount()), 320, y, paint);
            canvas.drawText(t.getNote() == null ? "-" : t.getNote(), 430, y, paint);
            y += 18;

            if (t.getType().equals("DEPOSIT")) totalDeposit += t.getAmount();
            else totalWithdraw += t.getAmount();
        }

        y += 20;
        paint.setFakeBoldText(true);
        canvas.drawText("Total Deposits: Rs. " + String.format("%.2f", totalDeposit), 40, y, paint); y += 18;
        canvas.drawText("Total Withdrawals: Rs. " + String.format("%.2f", totalWithdraw), 40, y, paint);

        document.finishPage(page);

        File file = new File(context.getExternalFilesDir("reports"),
                "statement_" + account.getAccountNumber() + ".pdf");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            fos.close();
            Toast.makeText(context, "Report saved: " + file.getName(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(context, "Failed to create report", Toast.LENGTH_SHORT).show();
        } finally {
            document.close();
        }

        return file.getAbsolutePath();
    }
}