package info.didustudio.ceramahustadFawwazMatJan.utilitas;


import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.didustudio.ceramahustadFawwazMatJan.model.KontenModel;

public class Constant {


    private static String namaUstad = "Fawwaz Mat Jan";





    public static KontenModel[] models(Context context) throws IOException {
        List<KontenModel> modelist = new ArrayList<>();
        String[] fileNames = context.getAssets().list("musik");
        for(String name:fileNames){
            char[] temp = new char[name.toCharArray().length-8];
            for(int i = 0; i<name.toCharArray().length-8; i++){
                temp[i] = name.toCharArray()[i];
            }
            modelist.add(new KontenModel(namaUstad, String.valueOf(temp), name));
        }

        KontenModel[] dap = new KontenModel[modelist.size()];
        for(int i = 0; i<modelist.size(); i++){
            dap[i] = modelist.get(i);
        }
        return dap;
    }

    public static String developerID = "Didu+Studio+Muslim";


    //Jangan diubah
    public static String gagalLoadIklan = "gagal";
    public static String berhasilLoadIklan = "berhasil";
    public static String kodeWebview = "webview";
    public static String kodeWebviewAsmaulHusna = "asmaulhusna";
    public static String kodeWebviewNabiRasul = "nabirasul";
    public static String kodeNamaFile = "namafile";
    public static String kodeBroadcastDariList = "kode_broadcast_dari_list";
    public static String kodeBroadCastDariFavorit = "kode_broadcast_dari_favorit";
}
