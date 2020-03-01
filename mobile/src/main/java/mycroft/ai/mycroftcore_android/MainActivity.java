package mycroft.ai.mycroftcore_android;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        loadExtension(this, "wikipedia", "wikipedia");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void loadExtension(Context context, String apkFile, String packageName) {
        Log.i(TAG, "Trying to load new class from apk.");
        final File dexInternalStoragePath = new File(context.getDir("extensions", Context.MODE_PRIVATE),
                apkFile + ".apk");
        final File optimizedDexOutputPath = context.getDir("outdex", Context.MODE_PRIVATE);
        Log.i(TAG, "dexInternalStoragePath: " + dexInternalStoragePath.getAbsolutePath());
        if (dexInternalStoragePath.exists()) {
            Log.i(TAG, "New apk found! " + apkFile);
            DexClassLoader dexLoader = new DexClassLoader(dexInternalStoragePath.getAbsolutePath(),
                    optimizedDexOutputPath.getAbsolutePath(),
                    null,
                    ClassLoader.getSystemClassLoader().getParent());
            try {
                Class klazz = dexLoader.loadClass("ai.mycroft.extension." + packageName);
                Constructor constructor = klazz.getConstructor(String.class);
                Method method = klazz.getDeclaredMethod("getInfo");
                Object newObject = constructor.newInstance("New object info");
                Log.i(TAG, "New object has class: " + newObject.getClass().getName());
                Log.i(TAG, "Invoking getInfo on new object: " + method.invoke(newObject));
            } catch (Exception e) {
                Log.e(TAG, "Exception:", e);
            }
        } else {
            Log.i(TAG, "Sorry new apk doesn't exist.");
        }
    }
}
