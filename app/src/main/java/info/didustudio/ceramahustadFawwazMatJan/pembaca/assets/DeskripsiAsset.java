package info.didustudio.ceramahustadFawwazMatJan.pembaca.assets;

import android.content.Context;
import android.util.Base64;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DeskripsiAsset {
    private Context context;
    private String key = "";
    public DeskripsiAsset(Context context, String key){
        this.context = context;
        this.key = key;
    }
    synchronized public byte[] getByte(String namaFile){
        SecretKey baru =  new SecretKeySpec(Base64.decode(key, Base64.DEFAULT),
                0, Base64.decode(key, Base64.DEFAULT).length, "AES");

        byte[] byteResult = null;
        try {
            InputStream inputStream = context.getAssets().open("musik/"+namaFile);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
            inputStream.close();

            Cipher AesCipher = Cipher.getInstance("AES");
            AesCipher.init(Cipher.DECRYPT_MODE, baru);
            byteResult = AesCipher.doFinal(bytes);

        } catch (NoSuchAlgorithmException | IllegalBlockSizeException | InvalidKeyException | IOException | BadPaddingException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return byteResult;
    }
}
