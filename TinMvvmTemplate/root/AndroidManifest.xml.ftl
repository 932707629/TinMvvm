<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <application>
            <#if isNewModule>
         	    <activity android:name="${ativityPackageName}.${activityClass}"
         	              android:configChanges="orientation|keyboardHidden|screenSize"
                          android:screenOrientation="portrait">
         	        <intent-filter>
         	            <action android:name="android.intent.action.MAIN" />
         	            <category android:name="android.intent.category.LAUNCHER" />
         	        </intent-filter>
         	    </activity>
         	<#else>
         	    <activity android:name="${ativityPackageName}.${activityClass}"
         	              android:configChanges="orientation|keyboardHidden|screenSize"
                          android:screenOrientation="portrait"/>
         	</#if>
    </application>
</manifest>
