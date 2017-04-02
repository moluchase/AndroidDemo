package com.example.administrator.mushroom.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.example.administrator.mushroom.bean.DataBean;
import com.example.administrator.mushroom.sql.SqliteOperate;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/19.
 */

public class GetDataFromURL {

    private static ArrayList<DataBean> mDataBeens;
    private static String str=" ";

    //从易菌网中获取数据，填到数据库中(包括国内动态，栽培技术，栽培研究）
    synchronized public static void importDataIntoDB(final String str, final String title, final Context context){

        mDataBeens=new ArrayList<>();



        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {

                Document doc= null;
                try {
                    doc = Jsoup.connect(str).timeout(8000).get();
                    Elements elements=doc.getElementsByClass("catlist_li");
                    for (Element e:elements) {
                        Document docsub=Jsoup.connect(e.getElementsByTag("a").attr("href")).timeout(8000).get();
                        Elements elementssub1=docsub.getElementsByClass("introduce");
                        if (!elementssub1.isEmpty()){
                            DataBean dataBean=new DataBean();
                            dataBean.setHref(e.getElementsByTag("a").attr("href"));//<a href="">
                            dataBean.setTitle(e.getElementsByTag("a").text());//<a>text</a>
                            dataBean.setIntroduction(elementssub1.get(0).text().substring(5));//第一个匹配的元素
                            Elements elementssub2=docsub.getElementsByClass("info");//class=info的
                            dataBean.setDate(elementssub2.get(0).text().substring(0,18));
                            dataBean.setType(title);
                            mDataBeens.add(dataBean);
                        }else if(!title.equals("国内动态")){
                            DataBean dataBean=new DataBean();
                            dataBean.setHref(e.getElementsByTag("a").attr("href"));//<a href="">
                            dataBean.setTitle(e.getElementsByTag("a").text());//<a>text</a>
                            Elements elementssub2=docsub.getElementsByClass("info");//class=info的
                            dataBean.setDate(elementssub2.get(0).text().substring(0,18));
                            dataBean.setType(title);
                            mDataBeens.add(dataBean);
                        }

                    }
                    SqliteOperate.addData(context,mDataBeens);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
    //对于获取到的url进行访问，获取全文，填到EssayActivity中
    public  static String importEssayfromDB(final String href){
                try {
                    Document document=Jsoup.connect(href).timeout(8000).get();
                    str =document.getElementsByClass("content").get(0).html();//获取标签内的全部内容，包括子标签
                } catch (IOException e) {
                    e.printStackTrace();
                }
        return str;
    }

    //判断是否能访问网络
    public static boolean isConnectNetwork(Context context){
        boolean isConnected=false;
        try {
            //参考https://developer.android.com/training/monitoring-device-state/connectivity-monitoring.html
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnected();
/*
            //参考https://segmentfault.com/q/1010000003890964
            //http://blog.csdn.net/never_cxb/article/details/47658257
            if(!isConnected){
               // return false;
            }else{
                isConnected=false;
                Runtime runtime=Runtime.getRuntime();
                Process ipProcess=runtime.exec("ping -c 1 www.baidu.com");
                int exitValue=ipProcess.waitFor();
                isConnected= (exitValue==0);
            }
*/
        }catch (Exception e){
            Log.e("network",e.toString());
        }finally {
            return isConnected;
        }


    }

    //将湖北专版导入数据库
     synchronized public static void importDataIntoDB2(final String str, final String title, final Context context){
        mDataBeens=new ArrayList<>();
         Thread thread2=new Thread(new Runnable() {
             @Override
             public void run() {
                 Document doc2=null;
                 try {
                     doc2=Jsoup.connect(str).timeout(8000).get();
                     Elements elements1doc2=doc2.getElementsByClass("li_dot");
                     Elements elements2doc2=elements1doc2.get(0).getElementsByTag("a");
                     Elements elements3doc2=elements1doc2.get(0).getElementsByTag("span");
                     for (int i = 0; i <elements2doc2.size(); i++) {
                         DataBean dataBean=new DataBean();
                         dataBean.setDate("发布日期："+elements3doc2.get(i).text());
                         dataBean.setType(title);
                         dataBean.setTitle(elements2doc2.get(i).attr("title"));
                         dataBean.setHref(elements2doc2.get(i).attr("href"));
                         mDataBeens.add(dataBean);
                     }
                     SqliteOperate.addData(context,mDataBeens);
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         });
         thread2.start();
         try {
             thread2.join();//thread2执行完后才开始执行当前线程
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
     }

    //将菇菌营养，蘑菇食谱导入数据库
    synchronized public static void importDataIntoDB3(final String str, final String title, final Context context){
        mDataBeens=new ArrayList<>();
        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc3=null;
                try {
                    doc3=Jsoup.connect(str).timeout(8000).get();
                    Elements elements1doc3=doc3.getElementsByClass("catlist");
                    Elements elements2doc3=elements1doc3.get(0).getElementsByTag("ul");
                    Elements elements3doc3=elements2doc3.get(0).getElementsByTag("span");
                    Elements elements4doc3=elements2doc3.get(0).getElementsByTag("a");
                    for (int i = 0; i <elements3doc3.size(); i++) {
                        DataBean dataBean=new DataBean();
                        dataBean.setDate("发布日期："+elements3doc3.get(i).text());
                        dataBean.setType(title);
                        dataBean.setTitle(elements4doc3.get(i).attr("title"));
                        dataBean.setHref(elements4doc3.get(i).attr("href"));
                        mDataBeens.add(dataBean);
                    }
                    SqliteOperate.addData(context,mDataBeens);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread2.start();
        try {
            thread2.join();//thread2执行完后才开始执行当前线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



//    public  static String importEssayfromDB(final String href){
//        //
//        Thread thread2=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Document doc2=Jsoup.connect(href).timeout(8000).get();
//                    str =doc2.getElementsByClass("content").get(0).text();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread2.start();
//
//        try {
//            thread2.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }

}
