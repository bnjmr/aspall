package ir.jahanmir.araxx.classes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import de.greenrobot.event.EventBus;
import ir.jahanmir.araxx.G;
import ir.jahanmir.araxx.enums.EnumInternetErrorType;
import ir.jahanmir.araxx.events.EventOnChangedDownloadPercent;
import ir.jahanmir.araxx.events.EventOnDownloadedFileCompleted;
import ir.jahanmir.araxx.events.EventOnGetErrorOnDownloadFile;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Downloader {
    OkHttpClient httpClient;
    boolean loop = true;
    public void requestDownload(final String url, final int downloadId){
        httpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
//                .url("http://192.168.1.200:8080/node.msi")
                .build();
        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                EventBus.getDefault().post(new EventOnGetErrorOnDownloadFile(downloadId, EnumInternetErrorType.NO_INTERNET_ACCESS));
                Logger.d("Downloader : not internet access");
            }
            @Override
            public void onResponse(Call call, Response response) {
                if (!response.isSuccessful()){
                    Logger.d("Downloader : download not succeeded");
                    EventBus.getDefault().post(new EventOnGetErrorOnDownloadFile(downloadId, EnumInternetErrorType.REQUEST_CODE_NOT_SUCCEEDED));
                    return;
                }
                String fileName = url.substring(url.lastIndexOf("/"));
                Logger.d("Downloader : fileName is "  + fileName);
                File folder = new File(G.DIR_APP_DOWNLOAD_FOLDER);
                if(!folder.exists())
                    folder.mkdirs();
                FileOutputStream out = null;
                try {
                    out = new FileOutputStream(folder + "/UpdateApp.apk");
                    InputStream in = response.body().byteStream();
                    long fileSize = response.body().contentLength();
                    Logger.d("Downloader : file size is "  + fileSize);
                    byte[] buffer = new byte[1024];
                    int readCount;
                    int bytesBuffered = 0; // baraye inke hafezeye gerefte shode bad az 1MB download azad shavad.
                    int totalFileDownload = 0;
                    while ((readCount = in.read(buffer)) > 0 && loop) {
                        out.write(buffer, 0, readCount);
                        totalFileDownload += readCount;
                        float percent = ((float)totalFileDownload  / fileSize);
                        Logger.d("Downloader : percent file downloaded is " + percent * 100);
                        EventBus.getDefault().post(new EventOnChangedDownloadPercent(downloadId, percent));
                        bytesBuffered += readCount;
                        if (bytesBuffered > 1024 * 1024) { //flush after 1MB
                            bytesBuffered = 0;
                            out.flush();
                        }
                    }
                    if(!loop){
                        /** dar surati ke download file cancel shavad.*/
                        File rawFile = new File(G.DIR_APP_DOWNLOAD_FOLDER + "/UpdateApp.apk");
                        rawFile.delete();
                        Logger.d("Downloader : downloaded raw file deleted.");
                    }else{
                        EventBus.getDefault().post(new EventOnDownloadedFileCompleted(downloadId));
                    }
                    out.flush();
                    out.close();
                    in.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public void cancelDownload(){
        this.loop = false;
    }
}
