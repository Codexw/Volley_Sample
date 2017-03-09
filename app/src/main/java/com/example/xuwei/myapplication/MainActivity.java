package com.example.xuwei.myapplication;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

import static com.example.xuwei.myapplication.R.id.bt5;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private ImageView imageView;
    private Button bt1,bt2,bt3,bt4,bt5;
    private NetworkImageView net_iamge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView= (TextView) findViewById(R.id.text1);
        imageView= (ImageView) findViewById(R.id.image1);

        final RequestQueue requestQueue= Volley.newRequestQueue(this);

        //通过StringRequest发送网络请求，默认为get方式
//        StringRequest stringRequest=new StringRequest("https://www.baidu.com", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Toast.makeText(MainActivity.this,"true",Toast.LENGTH_SHORT).show();
//                textView.setText(s);
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
//            }
//        }){
        //重写getParams方法，修改post发送参数
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> map = new HashMap<String, String>();
//                map.put("params1", "value1");
//                map.put("params2", "value2");
//                return map;
//            }
//        };
//        requestQueue.add(stringRequest);

        //通过JsonObjectRequest访问url获取一段json数据，也可以通过JsonArrayRequest获取一组json数据
//        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest("https://route.showapi.com/9-2?area=丽江&areaid=101291401&need3HourForcast=0&needAlarm=0&needHourData=0&needIndex=0&needMoreDay=0&showapi_appid=26832&showapi_timestamp=20170309094011&showapi_sign=c3e1319692a07420661f12330c2744bd",
//                null,new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                textView.setText(jsonObject.toString().trim());
//                Toast.makeText(MainActivity.this,"true",Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                                Toast.makeText(MainActivity.this,"error",Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue.add(jsonObjectRequest);

        /**
         * 通过imagerequest访问url获取网络图片
         * Param url:网络图片地址
         * param 图片请求成功的回调
         * param 图片最大宽度，设置为0则不会进行压缩
         * param 图片最大高度，与上同理
         * param 图片颜色属性，Bitmap.config都能使用。其中ARGB_8888可以展示最好的颜色属性，每个图片像素占据4个字节的大小，而RGB_565则表示每个图片像素占据2个字节大小
         * param 请求失败的回调
         */
//        ImageRequest imageRequest=new ImageRequest("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3610898955,1689152510&fm=116&gp=0.jpgttps://www.showapi.com/images/apiLogo/20150609/5423acc973f41c03173a186a_8fd4f648188645d3ab2c06717b87a26a.jpg",
//                new Response.Listener<Bitmap>(){
//                    @Override
//                    public void onResponse(Bitmap bitmap) {
//                        imageView.setImageBitmap(bitmap);
//                    }
//                },0,0, Bitmap.Config.ARGB_8888,new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                    Toast.makeText(MainActivity.this,"image error",Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue.add(imageRequest);


        /**
         * 通过imageloader加载网络图片，不仅可以帮我们对图片进行缓存，还可以过滤掉重复的链接，避免重复发送请求
         */
        bt1= (Button) findViewById(R.id.bt1);
        final ImageLoader imageLoader=new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String s) {
                return null;
            }

            @Override
            public void putBitmap(String s, Bitmap bitmap) {

            }
        });
        //第一个参数指定用于显示图片的ImageView控件，第二个参数指定加载图片的过程中显示的图片，第三个参数指定加载图片失败的情况下显示的图片
        final ImageLoader.ImageListener listener=ImageLoader.getImageListener(imageView,
                R.drawable.ic_launch,R.drawable.ic_launch);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可以指定图片允许的最大宽度和高度，也可以直接去掉不写
                imageLoader.get("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3610898955,1689152510&fm=116&gp=0.jpg",
                        listener,200,200);
            }
        });

        //检测最大运行内存
        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        Log.d("TAG", "Max memory is " + maxMemory + "KB");


        /**
         *检测图片大小、类型
         */
        bt2= (Button) findViewById(R.id.bt2);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapFactory.Options options=new BitmapFactory.Options();
                options.inJustDecodeBounds=true;
                BitmapFactory.decodeResource(getResources(),R.drawable.timg,options);
                int h=options.outHeight;
                int w=options.outWidth;
                String type=options.outMimeType;
                Toast.makeText(MainActivity.this,"high:"+h+" ,weight:"+w+"type: "+type,Toast.LENGTH_LONG).show();
            }
        });

        /**
         * 将图片进行100*100进行压缩
         */
        bt3= (Button) findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageBitmap(decodeBitmap(getResources(),R.drawable.timg,100,100));
//                imageView.setImageResource(R.drawable.timg);//纯粹显示图片，由于图片像素过大，报OOM错误
            }
        });


        /**
         *imageloader加载图片时进行缓存
         */
        bt4= (Button) findViewById(R.id.bt4);
        final ImageLoader loadcache=new ImageLoader(requestQueue,new BitmapCache());
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadcache.get("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1489053587583&di=a9d4bef18a3abb3d2e7e0efcd6169a21&imgtype=0&src=http%3A%2F%2Feasyread.ph.126.net%2FgUh01doarnWXbwhOjHA5yA%3D%3D%2F7916748702291064359.jpg",
                        listener);
            }
        });


        //使用networkimageview组件进行加载图片。继承自ImageView的，具备ImageView控件的所有功能，并且在原生的基础之上加入了加载网络图片的功能
        bt5= (Button) findViewById(R.id.bt5);
        net_iamge= (NetworkImageView) findViewById(R.id.netimage);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                net_iamge.setDefaultImageResId(R.drawable.ic_launcher);
                net_iamge.setErrorImageResId(R.drawable.ic_launch);
                net_iamge.setImageUrl("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2380292818,787602871&fm=23&gp=0.jpg",
                        loadcache);
            }
        });

    }

    /**
     * 获取图片压缩比率
     * @param options
     * @param reqWidth 目标宽度
     * @param reqHeight 目标高度
     * @return 最小比率
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }


    /**
     *
     * @param res Resources
     * @param resId 图片id
     * @param reqW 目标宽度
     * @param reqH  目标高度
     * @return
     */
    public static Bitmap decodeBitmap(Resources res,int resId,int reqW,int reqH){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(res,resId,options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize=calculateInSampleSize(options,reqW,reqH);
        options.inJustDecodeBounds=false;
        // 使用获取到的inSampleSize值再次解析图片
        return BitmapFactory.decodeResource(res,resId,options);
    }

    /**
     *使用LruCache功能对图片进行缓存
     */
    public class BitmapCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapCache() {
            //缓存图片的大小设置为10M
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap bitmap) {
                    return bitmap.getRowBytes() * bitmap.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }
}
