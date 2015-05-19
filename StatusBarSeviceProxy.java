

import android.os.RemoteException;
import android.os.ServiceManager;
import java.lang.reflect.Method;

import com.android.internal.statusbar.IStatusBarService;

Class StatusBarSeviceProxy{
	private final static String TAG = "StatusBarSeviceProxy";
	private static StatusBarSeviceProxy mInstance = null;
	private IStatusBarService mService;
	private boolean mReflect = true;
	
	private void StatusBarSeviceProxy( ) {
		mService = IStatusBarService.Stub.asInterface(
            ServiceManager.getService(Context.STATUS_BAR_SERVICE));
	}
	
	public StatusBarSeviceProxy getInstance() {
		if (mInstance == null) {
			return new StatusBarSeviceProxy();
		}
		return mInstance;
	}
	
	private IStatusBarService getService() {
       return mService;
	}
	
	public void setSystemUiVisibility(int vis, int mask){
		
		if (mReflect) {
			Class<?>  service = null;
			try {
				service = Class.forName("com.android.internal.statusbar.IStatusBarService");
			} catch (Exception e) {
			}
			Object instance = service.newInstance()
			try {
				Method method = service.getMethod("setSystemUiVisibility",new Class[]{int.class, int.class});
				method.setAccessible(true);
				method..invoke(instance, new Object[]{new Integer(vis), new Integer(mask)});
			} catch (Exception e) {
			}
		} else {
			mService.setSystemUiVisibility(vis,mask);
		}
		
	} 
	
}



