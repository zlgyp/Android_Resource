package com.vivo.test.systemui.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.util.Log;

public class BaseTools {

	public static String getAppVersion(Context context) throws Exception {
		PackageManager packageManager = context.getPackageManager();
		PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
		String versionName = packInfo.versionName;
		return versionName;
	}
	
	public static int getSystemVersion(){
		int version= android.os.Build.VERSION.SDK_INT;
		return version;
	}
	
    public static int dip2px(Context context, float dpValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (dpValue * scale + 0.5f);  
    }

    public static int px2dip(Context context, float pxValue) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return (int) (pxValue / scale + 0.5f);  
    }  
	
    public static float getDensity(Context context) {  
        final float scale = context.getResources().getDisplayMetrics().density;  
        return scale;  
    } 
	
    public static int getWidth(Context context) {  
        final int scale = context.getResources().getDisplayMetrics().widthPixels;  
        return scale;  
    }
	
    public static int getHeight(Context context) {  
        final int scale = context.getResources().getDisplayMetrics().heightPixels;  
        return scale;  
    }
    
    public static int getDpiDensity(Context context) {
    	final int scale = context.getResources().getDisplayMetrics().densityDpi;  
        return scale;  
    }    
    
    public static String getResDpi(Context context) {
    	
    	int density = getDpiDensity(context);
    	int width = getWidth(context);
    	String res = " ";
        
    	if(density >= 640) {
    		res = "xxxhpi";     		
    	} else if (density >= 480 &&  width>= 1440) {
    		res = "xxxhpi";
    	} else if(density >= 480  &&  width>= 1080) {
    		res = "xxhpi";
    	} else if(density >= 320  &&  width>= 720) {
    		res = "xxhpi";
    	} else if(density >= 320  &&  width <= 720) {
    		res = "xhpi";
    	} else if(density >= 160  &&  width >= 720) {
    		res = "xhpi";
    	} else if(density >= 160  && width >= 480) {
    		res = "hpi";
    	} else if(width <= 480 ){
    		res = "hpi";
    	}
    	
    	int resdp =  px2dip(context,width);
    	if(resdp < 360) {
    	   res = res;
    	} else{
    	   res = "sw-" + resdp + "dp-" + res;
    	}
    	return res;
    }
    
    public static String getModel()
    {
    	return android.os.Build.MODEL;
    }
    
    
    public static String getTopActivityName(Context context) {
        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE) ;
    	List<RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1) ;
    	if(runningTaskInfos != null) {
    		ComponentName cn = runningTaskInfos.get(0).topActivity ;
    		return cn.getClassName();
    	}
    	else
    		return null ;
    }
    
	public static String getInternalSdPath(Context context) throws NoSuchMethodException, Exception, IllegalArgumentException, InvocationTargetException {
    	StorageManager sm = (StorageManager) context.getSystemService("storage");
    	
    	Method getVolumePathsMethod = StorageManager.class.getMethod("getVolumePaths", null);
        String[] paths = (String[]) getVolumePathsMethod.invoke(sm, null);
		for (String string : paths) {
			if(getStorageType(string).compareTo(StorageType.InternalStorage) == 0) {
				return string;
			}	
		}
		return "/sdcard";
    }
    
	public static StorageType getStorageType(String path) {
		if (path.contains("/external_sd") || path.contains("/sdcard1")) {
			return StorageType.ExternalStorage;
		}
		if (path.contains("/sdcard0") || path.contains("/sdcard")) {
			return StorageType.InternalStorage;
		}
		if (path.contains("/otg")) {
			return StorageType.UsbStorage;
		}
		return StorageType.InternalStorage;

	}
	
    public static Drawable getIcon(Context context,  String iconPackage ,int iconId) {
//        Resources r = null;
//
//        if (iconPackage != null) {
//            try {
//                int userId = UserHandle.USER_OWNER;
//                r = context.getPackageManager().getResourcesForApplicationAsUser(iconPackage, userId);
//            } catch (PackageManager.NameNotFoundException ex) {
//                Log.e("BaseTools", "Icon package not found: " + iconPackage);
//                return null;
//            }
//        } else {
//            r = context.getResources();
//        }
//
//        if (iconId == 0) {
//            return null;
//        }
//
//        try {
//            return r.getDrawable(iconId);
//        } catch (RuntimeException e) {
//            Log.w("BaseTools", "Icon not found in "
//                  + (iconPackage != null ? iconId : "<system>")
//                  + ": " + Integer.toHexString(iconId));
//        }
        return null;
    }

    public static void sendBroadcast(Context context,Bundle bundle) {
        Intent intent = new Intent(Constants.Action.ACTION_DEMO);
        intent.putExtras(bundle);
        context.sendBroadcast(intent);
    }



}
