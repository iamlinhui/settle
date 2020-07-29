package cn.promptness.settle.spring;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;

/**
 * 注册类型转换器
 *
 * @author lynn
 * @date 2020/5/26 18:08
 * @since v1.0.0
 */
@Configuration
public class ConverterConfiguration {

    @Bean
    public ConversionService conversionService() {
        ApplicationConversionService conversionService = (ApplicationConversionService) ApplicationConversionService.getSharedInstance();
        conversionService.addConverter(new GenericSettleDecimalConverter());
        return conversionService;
    }

//    @Bean(name = "conversionService")
//    public ConversionServiceFactoryBean conversionServiceFactory() {
//        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
//        //实现自定义的类型转换器
//        conversionServiceFactoryBean.setConverters(Sets.newHashSet(new GenericSettleDecimalConverter()));
//        return conversionServiceFactoryBean;
//    }

}
