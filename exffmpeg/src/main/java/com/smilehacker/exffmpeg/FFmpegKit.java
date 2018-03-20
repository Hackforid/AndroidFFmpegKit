package com.smilehacker.exffmpeg;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.hzy.lib7z.Un7Zip;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kleist on 2017/5/17.
 */
public class FFmpegKit {
    private final static String TAG = "FFmpegKit";

    private final static String FFMPEG_ZIP = "ffmpeg.7z";
    private final static String FFMPEG = "ffmpeg";

    private static FFmpegKit mInstance;

    private Context mContext;

    public static FFmpegKit inst() {
        if (mInstance == null) {
            synchronized (FFmpegKit.class) {
                mInstance = new FFmpegKit();
            }
        }

        return mInstance;
    }

    private FFmpegKit() {
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    private File loadLib(Context context) {
        Config config = new Config(context);

        File exePath = new File(context.getFilesDir(), "exffmpeg");
        File exeFile = new File(exePath, FFMPEG);

        if (!exeFile.exists() || config.isVersionExpired()) {
            Log.d(TAG, "ffmpeg need recreate");
            if (exePath.exists()) {
                if (!exePath.isDirectory()) {
                    exePath.delete();
                    exePath.mkdirs();
                }
            } else {
                exePath.mkdirs();
            }

            Un7Zip.extractAssets(context, FFMPEG_ZIP, exePath.getAbsolutePath());
//            new UnLzma().extract7zDirectly(context.getAssets(), FFMPEG_ZIP, exePath.getAbsolutePath());
            config.refreshVersion();
            Log.d(TAG, "extract ffmpeg zip");
        }

        if (!exeFile.canExecute()) {
            exeFile.setExecutable(true);
        }

        return exeFile;
    }

    public int execute(String cmd) {
        if (TextUtils.isEmpty(cmd)) {
            return -1;
        }
        return run(cmd, null);
    }

    public int execute(String cmd, FFmpegListener listener) {
        if (TextUtils.isEmpty(cmd)) {
            return -1;
        }
        return run(cmd, listener);
    }

    public Process exec(String cmd) throws IOException {
        File ffmpeg = loadLib(mContext);
        String _cmd = String.format("%s %s", ffmpeg.getAbsolutePath(), cmd);
        return Runtime.getRuntime().exec(_cmd);
    }



    private int run(String cmd, FFmpegListener listener) {
        int ret = -1;
        Process process = null;
        try {
            process = exec(cmd);
            String line = null;
            BufferedReader std = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = std.readLine()) != null) {
                if (listener != null) {
                    listener.onStdOut(false, line);
                }
                Log.i(TAG, line);
            }

            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = stderr.readLine()) != null) {
                if (listener != null) {
                    listener.onStdOut(true, line);
                }
                Log.i(TAG, line);
            }

            ret = process.waitFor();

        } catch (Exception e) {
            Log.e(TAG, "exe error", e);
            if (listener != null) {
                listener.onStdOut(true, e.getMessage());
                listener.onComplete(false);
            }
        } finally {
            if (process != null) {
                process.destroy();
            }
        }

        if (listener != null) {
            listener.onComplete(ret == 0);
        }

        return ret;

    }


    public interface FFmpegListener {
        void onComplete(boolean success);
        void onStdOut(boolean error, String line);
    }
}
