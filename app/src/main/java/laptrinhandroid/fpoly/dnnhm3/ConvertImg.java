package laptrinhandroid.fpoly.dnnhm3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;

public class ConvertImg {
    public static String convertBitmapToBaseString(Bitmap bitmap) {
        ByteArrayOutputStream fileInputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, fileInputStream);
         return Base64.encodeToString(fileInputStream.toByteArray(), Base64.DEFAULT);
    }
    public static Bitmap convertBaseStringToBitmap(String baseString) {
        byte b[]=Base64.decode(baseString,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(b,0,b.length);
    }
}
