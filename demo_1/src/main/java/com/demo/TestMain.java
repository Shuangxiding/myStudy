package com.demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.demo.proxy.TestService;
import com.demo.proxy.impl.TestServiceImpl;
import com.demo.thread.CallableThread;
import com.demo.thread.CountThread;

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by 程祥 on 16/1/23.
 * 思路 ：第一个数字最大的往前排
 */
public class TestMain {

    public static void main(String[] args){

        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        Socket socket = null;
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            socket = new Socket("127.0.0.1", port);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println("QUERY TIME ORDER");
            System.out.println("Send order 2 server succeed.");
            String resp = in.readLine();
            System.out.println("Now is : " + resp);
        } catch (Exception e) {
            //不需要处理
        } finally {
            if (out != null) {
                out.close();
                out = null;
            }

            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                socket = null;
            }
        }

    }

    public void testThreadPool(){
        BlockingQueue queue = new SynchronousQueue();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 1, TimeUnit.MINUTES, queue,new ThreadPoolExecutor.AbortPolicy());

        try {
//            List<Future<String>> results =  executor.invokeAll(Arrays.asList(new CallableThread("1"),new CallableThread("2"),new CallableThread("3"),new CallableThread("4")));
//            for (Future<String> result : results) {
//                System.out.println(result.get());
//            }
            for (int i = 0; i < 13; i++) {

                executor.execute(new Runnable() {

                    public void run() {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(String.format("thread %d finished",Thread.currentThread().getId()));
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        executor.shutdown();
        System.out.println("over~");
    }

    public void testPattern(){
        String data_1 = "data[12][name]";
        String p_str = "data\\[(\\w+)\\]\\[(\\w+)\\]";
        Pattern pattern = Pattern.compile(p_str);
        Matcher matcher = pattern.matcher(data_1);
        if(matcher.matches()){
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));

        }else {
            System.out.println("NO MATCH");
        }
    }

    public static String getBigestNum(String[] array) {
        if (array == null || array.length < 1) {
            throw new IllegalArgumentException("参数不许为空");
        }
        StringComparator comparator = new StringComparator();
        quickSort(array, 0, array.length - 1, comparator);
        StringBuilder builder = new StringBuilder(256);
        for (String s : array) {
            builder.append(s);
        }
        return builder.toString();
    }

    private static void quickSort(String[] array, int start, int end, Comparator<String> comparator) {
        if (start < end) {
            String pivot = array[start];
            int left = start;
            int right = end;
            while (start < end) {
                while (start < end && comparator.compare(array[end], pivot) >= 0) {
                    end--;
                }
                array[start] = array[end];
                while (start < end && comparator.compare(array[start], pivot) <= 0) {
                    start++;
                }
                array[end] = array[start];
            }
            array[start] = pivot;
            quickSort(array, left, start - 1, comparator);
            quickSort(array, start + 1, right, comparator);
        }
    }


    private static class StringComparator implements Comparator<String> {

        public int compare(String o1, String o2) {
            if (o1 == null || o2 == null) {
                throw new IllegalArgumentException("参数不能为空~");
            }
            String s1 = o1 + o2;
            String s2 = o2 + o1;
            return s2.compareTo(s1);
        }
    }

}


