package com.example.wsdlg.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础的工具类 包含一些常用的方法
 *
 * @author lgx 2015年10月17日
 */
public class BaseUtility {

    public static final TimeZone tz = TimeZone.getTimeZone("GMT+8:00");
    public static boolean showImage = true;
    public static final String CachePath = "/cashmere/Cache";

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static Long transferStringDateToLong(String formatDate, String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(formatDate);
        Date dt = sdf.parse(date);
        return dt.getTime();
    }

    public static String hidePhone(String phone) {
        return phone.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double dR = 6371229; // 地球的半径

    public static double getDistance(double longt1, double lat1, double longt2, double lat2) {
        double x, y, distance;
        x = (longt2 - longt1) * PI * dR * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * dR / 180;
        distance = Math.hypot(x, y);
        return distance;
    }

    public static void toastNetError(Context context) {
        Toast(context, "网络连接错误，请稍候重试");
    }

    public static boolean success(Map<String, String> map) {
        boolean bool = false;
        if (!TextUtils.isEmpty(map.get("type"))) {
            if (map.get("type").equals("success")) {
                bool = true;
            }
        }
        return bool;
    }

    public static boolean isNull(Object obj) {
        return null == obj || "".equalsIgnoreCase(obj.toString()) || "null".equalsIgnoreCase(obj.toString());
    }

    /**
     * 打印toast
     */
    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 拼接修改信息的字符串
     */
    public static String pingString(Map<String, String> map) {
        String message = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (isNull(message)) {
                message = entry.getKey() + ".::." + entry.getValue();
            } else {
                message = message + ",,," + entry.getKey() + ".::." + entry.getValue();
            }
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
        return message;

    }

    public static boolean isPhoneNo(String phoneNumber) {
        String expression = "((^(13|15|16|18|17)[0-9]{9}$)|(^0[1," +
                "2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        return matcher.matches();
    }

    public static boolean isPersonID(String pid) {
        String regx = "[0-9]{17}x";
        String reg1 = "[0-9]{15}";
        String regex = "[0-9]{18}";
        boolean flag = pid.matches(regx) || pid.matches(reg1) || pid.matches(regex);
        return flag;
    }

    public static boolean isEmail(String email) {
        String regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        boolean flag = email.matches(regex);
        return flag;
    }

    /*
     * 获得固件版本
     */
    public static String getRelease() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 跳转页面
     *
     * @param <T>
     * @return
     */
    public static <T> void ToIntent(Context context, Class<T> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static String toUTF(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void getIntent(Context context, Class<T> c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }

    public static <T> void getIntent(Context context, Class<T> c, String... strings) {
        Intent intent = new Intent(context, c);
        for (int i = 0; i < strings.length; i++) {
            intent.putExtra(strings[i], strings[i]);
        }
        context.startActivity(intent);
    }

    /**
     * @Description: 加载网页
     */
    public static void loadingWeb(WebView web, String content) {
        WebSettings webSettings = web.getSettings();
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true); // 显示放大缩小 controler
        webSettings.setSupportZoom(true); // 可以缩放
        webSettings.setJavaScriptEnabled(true);
        web.loadUrl(content);
    }

    /**
     * 改变控件的大小
     */
    public static void changeViewWidthAndHeight(int state, View view, int width, int height) {
        switch (state) {
            case 1:// linearLayout
                LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) view.getLayoutParams(); // 取控件textView当前的布局参数
                linearParams.width = width;
                if (height == 0) {

                } else {
                    linearParams.height = height;
                }
                view.getLayoutParams();
                break;

            case 2:// RelativeLayout
                RelativeLayout.LayoutParams re = (RelativeLayout.LayoutParams) view.getLayoutParams(); // 取控件textView当前的布局参数
                re.width = width;
                if (height == 0) {

                } else {
                    re.height = height;
                }

                view.getLayoutParams();
                break;
            case 3:// FrameLayout
                FrameLayout.LayoutParams fr = (FrameLayout.LayoutParams) view.getLayoutParams(); // 取控件textView当前的布局参数
                fr.width = width;
                if (height != 0) {
                    fr.height = height;
                }
                view.getLayoutParams();
                break;
        }
    }

    /**
     * 改变顶部图标
     */
    public static void chanegeDrawableTop(Context context, int imageid, TextView view) {
        Drawable icon = context.getResources().getDrawable(imageid);
        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
        view.setCompoundDrawables(null, icon, null, null);
    }

    /**
     * 改变左侧图标
     */
    public static void chanegeDrawableLeft(Context context, int imageid, TextView view) {
        Drawable icon = context.getResources().getDrawable(imageid);
        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
        view.setCompoundDrawables(icon, null, null, null);
    }

    /**
     * 改变右侧图标
     */
    public static void chanegeDrawableRight(Context context, int imageid, TextView view) {
        Drawable icon = context.getResources().getDrawable(imageid);
        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
        view.setCompoundDrawables(null, null, icon, null);
    }

    public static void chanegeDrawableLeftRight(Context context, int leftImageid, int rightImageId, TextView view) {
        Drawable leftIcon = context.getResources().getDrawable(leftImageid);
        leftIcon.setBounds(0, 0, leftIcon.getMinimumWidth(), leftIcon.getMinimumHeight());
        Drawable rightIcon = context.getResources().getDrawable(rightImageId);
        rightIcon.setBounds(0, 0, rightIcon.getMinimumWidth(), rightIcon.getMinimumHeight());
        view.setCompoundDrawables(leftIcon, null, rightIcon, null);
    }


    /**
     * 改变底部图标
     */
    public static void chanegeDrawableButton(Context context, int imageid, TextView view) {
        Drawable icon = context.getResources().getDrawable(imageid);
        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
        view.setCompoundDrawables(null, null, null, icon);
    }

    public static String getCurrentNetType(Context context) {
        String type = "";
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            type = "null";
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = "wifi";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = "2g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = "3g";
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = "4g";
            }
        }
        return type;
    }

    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dpToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int pxToDp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 加载本地缩略图
     *
     * @param path
     * @param showWidth
     * @param showHeight
     * @return
     */
    public static Bitmap getLocationThmub(String path, int showWidth, int showHeight) {
        // 对于图片的二次采样,主要得到图片的宽与高
        int width = 0;
        int height = 0;
        int sampleSize = 1; // 默认缩放为1
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true; // 仅仅解码边缘区域
        // 如果指定了inJustDecodeBounds，decodeByteArray将返回为空
        BitmapFactory.decodeFile(path, options);
        // 得到宽与高
        height = options.outHeight;
        width = options.outWidth;
        // 图片实际的宽与高，根据默认最大大小值，得到图片实际的缩放比例
        while ((height / sampleSize > showHeight) || (width / sampleSize > showWidth)) {
            sampleSize += 1;
        }
        // 不再只加载图片实际边缘
        options.inJustDecodeBounds = false;
        // 并且制定缩放比例
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Config.ARGB_8888;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getLocalBitmap(String path, int width, int height) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inSampleSize = 2;
        opts.inJustDecodeBounds = false;
        opts.inPreferredConfig = Config.ARGB_8888;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        opts.outWidth = width;
        opts.outHeight = height;
        Bitmap bm = getLocalBitmap(path, opts);
        return zoomBitmap(bm, width, height);
    }

    public static Bitmap getLocalBitmap(String path, BitmapFactory.Options options) {
        if (TextUtils.isEmpty(path))
            return null;
        Bitmap temBitmap = null;
        try {
            temBitmap = BitmapFactory.decodeFile(path, options);
        } catch (Throwable t) {
            t.printStackTrace();

        }
        return temBitmap;
    }

    /**
     * 放大缩小图片
     *
     * @param bitmap
     * @param w
     * @param h
     * @return
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        Bitmap newbmp = null;
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            float scaleWidht = ((float) w / width);
            float scaleHeight = ((float) h / height);
            matrix.postScale(scaleWidht, scaleHeight);
            newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        }
        return newbmp;
    }

    /**
     * 生成图片上传路径
     */
    public static String getImageUploadFile(String name) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM/dd");
        return name + "/" + sdf.format(date);
    }

    public static int getWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static String getCode() {
        int[] array = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random rand = new Random();
        for (int i = 10; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = array[index];
            array[index] = array[i - 1];
            array[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < 6; i++)
            result = result * 10 + array[i];
        return result + "";
    }

    public static void doPhone(String phone, Context context) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(phoneIntent);
    }

    /**
     * 字符串的日期格式的计算
     */
    public static int daysBetween(String smdate, String bdate) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static boolean checZH(String paramString) {
        return Pattern.compile("[\u4e00-\u9fa5]").matcher(paramString).find();
    }

    /**
     * 获得时间
     */
    public static String getTime(String string) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(string);
        return sdf.format(date);
    }

    public static String getTime(Date date, String string) {
        SimpleDateFormat sdf = new SimpleDateFormat(string);
        return sdf.format(date);
    }

    /**
     * 毫秒转日期
     */
    public static String millToTime(long mill, String smdate) {
        Date date = new Date();
        date.setTime(mill);
        if (isNull(smdate)) {
            smdate = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(smdate);
        return simpleDateFormat.format(date);

    }

    /**
     * 绑定控件
     *
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T bingView(Activity activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    /**
     * 绑定控件
     *
     * @param <T>
     */
    @SuppressWarnings("unchecked")
    public static <T> T bingView(View activity, int viewId) {
        return (T) activity.findViewById(viewId);
    }

    public static boolean isInMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();// 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565;// 取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);// 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);// 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);// 把drawable内容画到画布中
        return bitmap;
    }


    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getBottomStatusHeight(Context context) {
        int totalHeight = getDpi(context);
        int contentHeight = getScreenHeight(context);
        return totalHeight - contentHeight;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    public static void hindSoft(Activity activity) {
        InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 通过反射，获取包含虚拟键的整体屏幕高度
     *
     * @return
     */
    public static int getHasVirtualKey(Activity activity) {
        int dpi = 0;
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, dm);
            dpi = dm.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * 获取手机IMSI号
     */
    public static String getIMSI(Context context) {
        TelephonyManager mTelephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = mTelephonyMgr.getSubscriberId();

        return imsi;
    }

    public static Bitmap stringtoBitmap(String string) {
        //将字符串转换成Bitmap类型
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray;
            bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    public static Bitmap getBitMBitmap(String urlpath) {
        Bitmap map = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            map = BitmapFactory.decodeStream(in);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /**
     * 生成6位随机数
     *
     * @return
     */
    public static String getRandomNum() {
        return (int) ((Math.random() * 9 + 1) * 100000) + "";
    }

    public static String getdecimalDouble(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(price);
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public static void callPhone(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        context.startActivity(intent);
    }

    public static String distanceToString(double distance) {
        StringBuilder stringBuilder = new StringBuilder();
        DecimalFormat df = new DecimalFormat("#.00");
        if (distance > 1000) {

            stringBuilder.append(df.format(distance / 1000));
            stringBuilder.append("公里");
        } else {
            stringBuilder.append(df.format(distance));
            stringBuilder.append("米");
        }

        return stringBuilder.toString();
    }


    public static String bigNumToString(int number) {
        StringBuilder stringBuilder = new StringBuilder();

        DecimalFormat df = new DecimalFormat("#.0");
        if (number >= 10000) {
            stringBuilder.append(df.format(number / 10000.0));
            stringBuilder.append("W");
        } else {
            stringBuilder.append(number);
        }

        return stringBuilder.toString();
    }


    public static DisplayMetrics getDisplayMetrics(Context context) {
        Resources resources = context.getResources();
        return resources.getDisplayMetrics();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * (getDisplayMetrics(context).densityDpi / 160f);
    }

    public static int convertDpToPixelSize(float dp, Context context) {
        float pixels = convertDpToPixel(dp, context);
        final int res = (int) (pixels + 0.5f);
        if (res != 0) {
            return res;
        } else if (pixels == 0) {
            return 0;
        } else if (pixels > 0) {
            return 1;
        }
        return -1;
    }

    public static float getTextWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    /**
     * 根据时间戳来判断当前的时间是几天前,几分钟,刚刚
     *
     * @param long_time
     * @return
     */
    public static String getTimeStateNew(String long_time) {
        String long_by_13 = "1000000000000";
        String long_by_10 = "1000000000";
        if (Long.valueOf(long_time) / Long.valueOf(long_by_13) < 1) {
            if (Long.valueOf(long_time) / Long.valueOf(long_by_10) >= 1) {
                long_time = long_time + "000";
            }
        }
        Timestamp time = new Timestamp(Long.valueOf(long_time));
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//    System.out.println("传递过来的时间:"+format.format(time));
//    System.out.println("现在的时间:"+format.format(now));
        long day_conver = 1000 * 60 * 60 * 24;
        long hour_conver = 1000 * 60 * 60;
        long min_conver = 1000 * 60;
        long time_conver = now.getTime() - time.getTime();
        long temp_conver;
//    System.out.println("天数:"+time_conver/day_conver);
        if ((time_conver / day_conver) < 3) {
            temp_conver = time_conver / day_conver;
            if (temp_conver <= 2 && temp_conver >= 1) {
                return temp_conver + "天前";
            } else {
                temp_conver = (time_conver / hour_conver);
                if (temp_conver >= 1) {
                    return temp_conver + "小时前";
                } else {
                    temp_conver = (time_conver / min_conver);
                    if (temp_conver >= 1) {
                        return temp_conver + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            }
        } else {
            return format.format(time);
        }
    }

    public static boolean isNull(String checkStr) {

        boolean result = false;

        if (null == checkStr) {

            result = true;
        } else {
            if (checkStr.length() == 0) {

                result = true;
            }
        }
        return result;
    }

    public static boolean isNull(List<?> list) {

        boolean result = false;

        if (null == list) {

            result = true;
        } else {
            if (list.size() == 0) {

                result = true;
            }
        }
        return result;
    }

    public static List<String> getRegEx(String input, String regex) {
        List<String> stringList = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(input);
        while (m.find()) {
            stringList.add(m.group());
        }

        return stringList;
    }


}
