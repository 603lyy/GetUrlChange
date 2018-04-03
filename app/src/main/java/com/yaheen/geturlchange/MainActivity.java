package com.yaheen.geturlchange;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yaheen.geturlchange.bean.YTFBean;
import com.yaheen.geturlchange.response.YTFResponse;
import com.yaheen.geturlchange.util.MD5Utils;
import com.yaheen.geturlchange.util.PathUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ChooseFile";

    //以太坊地址https://api.etherscan.io/api?module=account&action=balance&address=0xe60e1501bd9bf2b50a6e1f25586757ebdc7f7882&tag=latest
    private static final String YTFURL = "https://api.etherscan.io/api";

    private static final String NORMALURL = "https://blockchain.info/q/addressbalance/";

    private final int msg_normal_url_success = 1;

    private final int msg_ytf_url_success = 2;

    private TextView tvCopy, tvStart, tvStop, tvClear, tvHang, tvChose;

    private EditText tvLog;

    private Spinner spinner;

    private Gson gson = new Gson();

    //剪切板管理工具类
    private ClipboardManager mClipboardManager;
    //剪切板Data对象
    private ClipData mClipData;

    private Map<Integer, String> map1;

    private String needPath = "";

    private String data = "";

    private String strPsd = "EjEk1IjlaO3YDOq1";

    //请求链接前面部分
    private String baseUrl = "";

    boolean isStarted = false;

    private int urlIndex = 0;

    /**
     * 1表示NORMALURL；2表示YTFURL
     */
    private int urlSelect = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        tvCopy = findViewById(R.id.tv_copy);
        tvStart = findViewById(R.id.tv_start);
        tvStop = findViewById(R.id.tv_stop);
        tvClear = findViewById(R.id.tv_clear);
        tvLog = findViewById(R.id.tv_log);
        tvHang = findViewById(R.id.tv_hang);
        tvChose = findViewById(R.id.tv_chose);
        spinner = findViewById(R.id.spinner1);

        strPsd = MD5Utils.encrypt(strPsd);

        tvChose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        tvStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStarted) {
                    return;
                }
                isStarted = true;
                tvHang.setText(urlIndex + 1 + "");
                getNumber();
                Toast.makeText(MainActivity.this, "开始请求网络", Toast.LENGTH_SHORT).show();
            }
        });

        tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStarted = false;
            }
        });

        tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClipData = ClipData.newPlainText("Simple test", tvLog.getText());
                //把clip对象放在剪贴板中
                mClipboardManager.setPrimaryClip(mClipData);
                Toast.makeText(MainActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                urlIndex = 0;
//                map1.clear();
//                isStarted = false;
//                needPath = "";
                tvLog.setText("");
                data = "";
            }
        });

        spinner.setOnItemSelectedListener(this);
    }

    private void initPermission() {
        int sdkVersion = Build.VERSION.SDK_INT;
        if (sdkVersion > 22) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android
                    .Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            default:
        }
    }

    private void getNumber() {

        if (!isStarted || map1 == null || map1.size() == 0 || urlIndex >= map1.size() || urlIndex < 0) {
            Toast.makeText(MainActivity.this, "请先选择一个文件", Toast.LENGTH_SHORT).show();
            return;
        }

        if (urlSelect == 1) {
            baseUrl = NORMALURL + map1.get(urlIndex);
        } else if (urlSelect == 2) {
            baseUrl = YTFURL;
        } else {
            return;
        }

        RequestParams params = new RequestParams(baseUrl);
        if (urlSelect == 2) {
            String address = "0x" + map1.get(urlIndex) + ",0x" + map1.get(urlIndex + 1) + ",0x" +
                    map1.get(urlIndex + 2) + ",0x" + map1.get(urlIndex + 3) + ",0x" + map1.get(urlIndex + 4);
            params.addQueryStringParameter("module", "account");
            params.addQueryStringParameter("action", "balancemulti");
            params.addQueryStringParameter("address", address);
            params.addQueryStringParameter("tag", "last");
        }
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Message mess = new Message();
                if (urlSelect == 1) {
                    mess.what = msg_normal_url_success;
                    mess.obj = result;
                } else if (urlSelect == 2) {
                    YTFResponse ytfResponse = gson.fromJson(result, YTFResponse.class);
                    if (ytfResponse.result.size() > 0) {
                        mess.what = msg_ytf_url_success;
                        mess.obj = ytfResponse.result;
                    }
                }
                mHandler.sendMessage(mess);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                urlIndex++;
                getNumber();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {

            }
        });
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case msg_normal_url_success:
                    String str = (String) msg.obj;
                    int num = Integer.parseInt(str);
                    if (num > 0) {
                        data = data + "\n" + map1.get(urlIndex) + "：" + num;
                        if (num > 1000000) {
                            sendSMS(map1.get(urlIndex) + "：" + num);
                        }
                    }
                    tvHang.setText(urlIndex + 1 + "");
                    urlIndex = urlIndex + 1;
                    tvLog.setText(data);
                    getNumber();
                    break;
                case msg_ytf_url_success:
                    YTFBean ytfBean;
                    int num1 = 0;
                    ArrayList<YTFBean> ytfList = (ArrayList<YTFBean>) msg.obj;
                    if (ytfList != null) {
                        for (int i = 0; i < ytfList.size(); i++) {
                            ytfBean = ytfList.get(i);
                            try {
                                num1 = Integer.parseInt(ytfBean.getBalance());
                            } catch (NumberFormatException e) {
                                num1 = 1;
                            }
                            if (num1 > 0) {
                                data = data + "\n" + ytfBean.getAccount() + "：" + ytfBean.getBalance();
                            }
                        }
                    }
                    tvHang.setText(urlIndex + 5 + "");
                    urlIndex = urlIndex + 5;
                    tvLog.setText(data);
                    getNumber();
                    break;
                default:
            }
        }
    };

    private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    String path = null;
//                    try {
//                        path = getPath(this, uri);
//                    } catch (URISyntaxException e) {
//                        e.printStackTrace();
//                    }
//                    Log.d(TAG, "File Path: " + path);


                    if ("file".equalsIgnoreCase(uri.getScheme())) {//使用第三方应用打开
                        path = uri.getPath();
                    } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {//4.4以后
                        path = PathUtils.getPath(this, uri);
                    } else {//4.4以下下系统调用方法
                        path = getRealPathFromURI(uri);
                    }

                    reset();
                    needPath = path;
                    map1 = Txt();
                    Toast.makeText(MainActivity.this, "已选择文件", Toast.LENGTH_SHORT).show();
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (null != cursor && cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
            cursor.close();
        }
        return res;
    }

//    public static String getPath(Context context, Uri uri) throws URISyntaxException {
//        if ("content".equalsIgnoreCase(uri.getScheme())) {
//            String[] projection = {"_data"};
//            Cursor cursor = null;
//            try {
//                cursor = context.getContentResolver().query(uri, projection, null, null, null);
//                int column_index = cursor.getColumnIndexOrThrow("_data");
//                if (cursor.moveToFirst()) {
//                    return cursor.getString(column_index);
//                }
//            } catch (Exception e) {
//                // Eat it  Or Log it.
//            }
//        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
//            return uri.getPath();
//        }
//        return null;
//    }

    public Map<Integer, String> Txt() {
        //将读出来的一行行数据使用Map存储
        String filePath = needPath;//手机上地址
        if (filePath == null) {
            return null;
        }
        Map<Integer, String> map = new HashMap<Integer, String>();
        try {
            File file = new File(filePath);
            int count = 0;//初始化 key值
            if (file.isFile() && file.exists()) {       //文件存在的前提
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file));
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {     //
                    if (!"".equals(lineTxt)) {
                        String reds = lineTxt.split("\\+")[0];      //java 正则表达式
                        map.put(count, reds);//依次放到map  0，value0;1,value2
                        count++;
                    }
                }
                isr.close();
                br.close();
            } else {
                Toast.makeText(getApplicationContext(), "can not find file", Toast.LENGTH_SHORT).show();//找不到文件情况下
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private void sendSMS(String content) {
        //大于1000000的地址发送短信13570723681

        RequestParams params = new RequestParams("http://big.smsbao.com/api/orange/sms.action");
        params.addQueryStringParameter("u", "yaheensms");
        params.addQueryStringParameter("p", strPsd);
        params.addQueryStringParameter("m", "13570723681，18620026724");
        params.addQueryStringParameter("c", "【yaheen】您的加密串为 " + content);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                mHandler.sendEmptyMessage(20);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner1:
                if (position == 1) {
                    urlSelect = 2;
                } else {
                    urlSelect = 1;
                }
                reset();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void reset() {
        isStarted = false;
        needPath = "";
        urlIndex = 0;
        data = "";
        if (map1 != null) {
            map1.clear();
        }
        tvHang.setText("1");
    }
}
