package com.yaheen.geturlchange;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.yaheen.geturlchange.bean.AccountBean;
import com.yaheen.geturlchange.response.YTFResponse;
import com.yaheen.geturlchange.socket.ClientManager;
import com.yaheen.geturlchange.util.MD5Utils;
import com.yaheen.geturlchange.util.PathUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "ChooseFile";

    //以太坊地址https://api.etherscan.io/api?module=account&action=balance&address=0xe60e1501bd9bf2b50a6e1f25586757ebdc7f7882&tag=latest
    private static final String YTFURL = "https://api.etherscan.io/api";

    private static final String NORMALURL = "https://blockchain.info/q/addressbalance/";

    private static final int FILE_SELECT_CODE = 0;

    private final int max_connect_num = 5;

    private final int msg_normal_url_success = 1;

    private final int msg_ytf_url_success = 2;

    private TextView tvCopy, tvStart, tvStop, tvClear, tvHang, tvChose, tvNum;

    private TextView tvLog;

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

    private volatile int urlIndex = 0;

    /**
     * 1表示NORMALURL；2表示YTFURL
     */
    private int urlSelect = 1;

    private int cNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPermission();

        mClipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        tvNum = findViewById(R.id.tv_num);
        tvLog = findViewById(R.id.tv_log);
        tvHang = findViewById(R.id.tv_hang);
        tvCopy = findViewById(R.id.tv_copy);
        tvStop = findViewById(R.id.tv_stop);
        tvChose = findViewById(R.id.tv_chose);
        spinner = findViewById(R.id.spinner1);
        tvClear = findViewById(R.id.tv_clear);
        tvStart = findViewById(R.id.tv_start);

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
//
                if (map1 == null || map1.size() == 0) {
                    Toast.makeText(MainActivity.this, "请先选择一个文件", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cNum >= max_connect_num) {
                    Toast.makeText(MainActivity.this, "已达到最大请求数", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    cNum++;
                    tvNum.setText("当前线程数为" + cNum);
                }

                Toast.makeText(MainActivity.this, "开始请求网络", Toast.LENGTH_SHORT).show();
                isStarted = true;
                getNumber();
            }
        });

        tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isStarted = false;
                cNum = 0;
                tvNum.setText("当前线程数为" + cNum);
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

        //true为开始请求网络
        if (!isStarted) {
            return;
        }

        if (urlIndex >= map1.size() || urlIndex < 0) {
            urlIndex = 0;
        }

        if (urlSelect == 1) {
            baseUrl = NORMALURL + map1.get(urlIndex);
        } else if (urlSelect == 2) {
            baseUrl = YTFURL;
        } else {
            return;
        }

        final RequestParams params = new RequestParams(baseUrl);
        if (urlSelect == 2) {
            String address = "0x" + map1.get(urlIndex) + ",0x" + map1.get(urlIndex + 1) + ",0x" +
                    map1.get(urlIndex + 2) + ",0x" + map1.get(urlIndex + 3) + ",0x" + map1.get(urlIndex + 4);
            params.addQueryStringParameter("module", "account");
            params.addQueryStringParameter("action", "balancemulti");
            params.addQueryStringParameter("address", address);
            params.addQueryStringParameter("tag", "last");
            urlIndex = urlIndex + 5;
        } else {
            params.setHeader("url1", map1.get(urlIndex));
            urlIndex = urlIndex + 1;
        }

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Message mess = new Message();
                if (urlSelect == 1) {
                    mess.what = msg_normal_url_success;
                    AccountBean bean = new AccountBean();
                    bean.setAccount(params.getHeaders().get(0).getValueStr());
                    bean.setBalance(result);
                    mess.obj = bean;
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
                    AccountBean nBean = (AccountBean) msg.obj;
                    int num = Integer.parseInt(nBean.getBalance());
                    if (num > 0) {
                        data = data + "\n" + nBean.getAccount() + "：" + num;
                        if (num > 1000000) {
                            sendSMS(nBean.getAccount() + "：" + num);
                        }
                    }
                    tvHang.setText(urlIndex + "");
                    tvLog.setText(data);
                    getNumber();
                    break;
                case msg_ytf_url_success:
                    AccountBean accountBean;
                    int num1 = 0;
                    ArrayList<AccountBean> ytfList = (ArrayList<AccountBean>) msg.obj;
                    if (ytfList != null) {
                        for (int i = 0; i < ytfList.size(); i++) {
                            accountBean = ytfList.get(i);
                            try {
                                num1 = Integer.parseInt(accountBean.getBalance());
                            } catch (NumberFormatException e) {
                                num1 = 1;
                            }
                            if (num1 > 0) {
                                data = data + "\n" + accountBean.getAccount() + "：" + accountBean.getBalance();
                            }
                        }
                    }
                    tvHang.setText(urlIndex + "");
                    tvLog.setText(data);
                    getNumber();
                    break;
                default:
            }
        }
    };

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
                    Uri uri = data.getData();
                    String path = null;

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
