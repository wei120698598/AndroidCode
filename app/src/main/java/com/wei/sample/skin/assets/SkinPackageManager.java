package com.wei.sample.skin.assets;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.wei.sample.MyApplication;

import java.io.File;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * 皮肤包管理器
 *
 * @author devilwwj
 */
public class SkinPackageManager {
    private static SkinPackageManager mInstance;

    public static final String SKIN_DRAWABLE = "drawable";
    public static final String SKIN_STRING = "string";
    public static final String SKIN_COLOR = "color";
    public static final String SKIN_DIMEN = "dimen";
    public static final String SKIN_LAYOUT = "layout";


    private String mPackageName;

    private Resources mResources;

    public Resources getmResources() {
        return mResources;
    }

    public String getmPackageName() {
        return mPackageName;
    }

    private SkinPackageManager() {
        super();
    }

    public static SkinPackageManager getInstance() {
        if (mInstance == null) {
            mInstance = new SkinPackageManager();

        }
        return mInstance;
    }

    /**
     * 异步加载皮肤资源
     */
    public Observable<Boolean> loadSkinAsync() {
        ViewTarget<ImageView, Drawable> target = Glide.with(MyApplication.getInstance()).load("").into(new ImageView(MyApplication.getInstance()));

        return Observable
                .fromCallable(new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        File file = new File(Environment.getExternalStorageDirectory(), "skin.apk");
                        String skinPath = file.getAbsolutePath();
                        // 得到包管理器
                        PackageManager mpm = MyApplication.getInstance().getPackageManager();
                        // 得到包信息
                        PackageInfo mInfo = mpm.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES);
                        mPackageName = mInfo.packageName;

                        AssetManager assetManager = AssetManager.class.newInstance();
                        Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                        addAssetPath.invoke(assetManager, skinPath);

                        Resources superRes = MyApplication.getInstance().getResources();
                        mResources = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());
                        return true;
                    }
                })
                .subscribeOn(Schedulers.newThread());
    }

    //测试加载Drawable
    public static int getDrawable(String name) {
        return getInstance().getmResources().getIdentifier(name, SKIN_DRAWABLE, "com.wei.skin");
    }
}
