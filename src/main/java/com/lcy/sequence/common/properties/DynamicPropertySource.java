package com.lcy.sequence.common.properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.MapPropertySource;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DynamicPropertySource extends MapPropertySource {


    private static Logger log = LoggerFactory.getLogger(DynamicPropertySource.class);
    private static Map<String, Object> map = new ConcurrentHashMap<String, Object>(64);

//    private static ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(1);
//    static {
//        scheduled.scheduleAtFixedRate(new Runnable() {
//            @Override
//            public void run() {
//                map = dynamicLoadMapInfo();
//            }
//        }, 1, 10, TimeUnit.SECONDS);
//    }

    public static void init(){
        map = dynamicLoadMapInfo();
    }

    /**
     * @param name
     * @param source
     */
    public DynamicPropertySource(String name, Map<String, Object> source) {
        super(name, map);
        //初始化参数
        init();
    }


    @Override
    public Object getProperty(String name) {
        return map.get(name);
    }

    /**
     * //动态获取资源信息
     * @return
     */
    protected static Map<String, Object> dynamicLoadMapInfo() {
        return mockMapInfo();
    }

    /**
     * @return
     */
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    private static Map<String, Object> mockMapInfo() {
        Map<String, Object> map = new HashMap<String, Object>();
        map = getProperties();
        log.info("data{};currentTime:{}", map.get("spring.datasource.url"), sdf.format(new Date()));
        return map;
    }

    /**
     * 获取配置文件信息
     * @return
     */
    public static Map<String, Object> getProperties() {

        Properties props = new Properties();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            InputStream in = ClassLoader.getSystemResourceAsStream("application.properties");
            props.load(in);
            Enumeration<?> en = props.propertyNames();
            while (en.hasMoreElements()) {
                String key = (String) en.nextElement();
                String property = props.getProperty(key);
                map.put(key, property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
    public static Object getValue(String name) {
        return map.get(name);
    }
}
