package com.robottitto.tarefapmdm04.ui.core;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.robottitto.tarefapmdm04.R;

import java.io.File;

import static android.Manifest.permission.CAMERA;

public class CameraUtil extends AppCompatActivity {

    private final static String IMAGE_PATH = "media";
    private final static String IMAGE_NAME = "dummy.jpg";
    private final static int WRITE_STORAGE = 1;
    private final static int PICK_IMAGE = 10;
    private final static int CAPTURE_IMAGE = 11;
    private static String path;

    /*
     *  Solicitud de permisos
     */
    public void pedirPermiso() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, CAMERA}, WRITE_STORAGE);
        }
    }

    public void loadImageFromSD() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent, PICK_IMAGE);
    }

    public void loadImageFromCamera() {
        File imageFile = new File(this.getExternalFilesDir(null), IMAGE_PATH);
        path = this.getExternalFilesDir(null) +
                File.separator + IMAGE_PATH +
                File.separator + IMAGE_NAME;
        if (imageFile.exists() == false)
            imageFile.mkdirs();
        File image = new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = getApplicationContext().getPackageName() + ".provider";
            Uri imageUri = FileProvider.getUriForFile(this, authorities, image);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
        }
        startActivityForResult(intent, CAPTURE_IMAGE);
    }

    /*
     *  Resultado de la solicitud de permisos
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == WRITE_STORAGE) {
            Button btn = findViewById(R.id.btLoadImage);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permisos concedidos, habilitamos botón
                btn.setEnabled(true);
                //Toast.makeText(this,"PERMISOS CONCEDIDOS",Toast.LENGTH_SHORT).show();
            } else {
                // Permisos denegados, deshabilitamos botón
                btn.setEnabled(false);
                Toast.makeText(this, "NECESITAS PERMISOS", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            ImageView image = findViewById(R.id.ivImage);
            switch (requestCode) {
                case PICK_IMAGE:
                    Uri miPath = data.getData();
                    image.setImageURI(miPath);
                    break;
                case CAPTURE_IMAGE:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                }
                            });
                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    image.setImageBitmap(bitmap);
                    break;
                default:
                    // Default
            }
        }
    }

}
