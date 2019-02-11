package com.yto.tech.catplugin.catfilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 加载过滤器
 */

@Configuration("catFilterConfigure")
public class CatFilterConfigure {
    private static final Logger logger = LoggerFactory.getLogger(CatFilterConfigure.class);
    private static String URL_PATTERN = "/*";
    private static String URL_EXCLUSIONS_PATTERN = "*.js,*.gif,*.jpg,*.png,*.css,*.ico";


    /**
     * 此属性指定匹配哪些url，多个用逗号隔开
     * 默认值: /* (全部)
     */
    @Value("${cat.filter.url.pattern:/*}")
    private String urlPattern;

    /**
     * cat.filter.url.exclusions.pattern
     * 此属性指定不匹配哪些url，常用的js,css都可用此属性来排除
     * 多个用逗号隔开
     * 默认值：*.js,*.gif,*.jpg,*.png,*.css,*.ico
     */
    @Value("${cat.filter.url.exclusions.pattern:*.js,*.gif,*.jpg,*.png,*.css,*.ico}")
    private String exclustionsPattern;


    public CatFilterConfigure(){
        logger.info("CatFilterConfigure init ...");
    }

    @Bean
    @DependsOn("catFilterConfigure")
    public FilterRegistrationBean catFilter() {
        logger.info("startting generate Bean (CatFilterConfigure.catFilter), urlPattern = {} ， exclustionsPattern = {}",urlPattern , exclustionsPattern);

        String[] ps = stringToArray(urlPattern);
        logger.info("real urlPattern is {} , exclusionsPattern is {}",Arrays.toString(ps) , exclustionsPattern);

        FilterRegistrationBean registration = new FilterRegistrationBean();
        YtoCatFilter filter = new YtoCatFilter();
        registration.setFilter(filter);
        registration.addUrlPatterns(ps);
        registration.addInitParameter("exclusions", exclustionsPattern);
        registration.setName("cat-filter");
        registration.setOrder(1);
        return registration;
    }

    public String[] stringToArray(String p){
        ArrayList<String> patternList = new ArrayList<String>();
        if(p != null && !"".equals(p.trim())) {
            StringTokenizer st = new StringTokenizer(p,",");
            while(st.hasMoreTokens()) {
                String e = st.nextToken();
                if(e!=null && !"".equals(e.trim())) {
                    patternList.add(e);
                }
            }
        }else{
            patternList.add(URL_PATTERN);
        }
        return patternList.toArray(new String[0]);
    }

    public static void main(String[] args) {
        String urlPattern = "";
        CatFilterConfigure ss = new CatFilterConfigure();
        String[] ps = ss.stringToArray(urlPattern);
        System.out.println(Arrays.toString(ps));
    }
}
