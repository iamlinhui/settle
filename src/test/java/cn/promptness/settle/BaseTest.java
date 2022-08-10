package cn.promptness.settle;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SettleApplication.class)
public class BaseTest {


    protected Logger logger = LoggerFactory.getLogger(BaseTest.class);

    private SerializeConfig getSerializeConfig() {
        //BigDecimal数据处理
        SerializeConfig serializeConfig = SerializeConfig.getGlobalInstance();
        serializeConfig.put(BigDecimal.class, new ContextObjectSerializer() {

            @Override
            public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
                BigDecimalCodec.instance.write(jsonSerializer, o, o1, type, i);
            }

            @Override
            public void write(JSONSerializer jsonSerializer, Object o, BeanContext beanContext) throws IOException {
                SerializeWriter out = jsonSerializer.out;
                if (o == null) {
                    out.writeString("");
                    return;
                }
                String format = beanContext.getFormat();
                DecimalFormat decimalFormat = new DecimalFormat(format);
                out.writeString(decimalFormat.format(o));
            }
        });

        serializeConfig.put(Date.class, new ContextObjectSerializer() {

            @Override
            public void write(JSONSerializer jsonSerializer, Object o, Object o1, Type type, int i) throws IOException {
                SerializeWriter out = jsonSerializer.out;
                if (o == null) {
                    out.writeString("");
                    return;
                }
                out.writeString(new SimpleDateFormat("yyyy-MM-dd").format(o));
            }

            @Override
            public void write(JSONSerializer jsonSerializer, Object o, BeanContext beanContext) throws IOException {

            }
        });

        return serializeConfig;
    }

    public String toJSON(Object object) {
        return JSON.toJSONString(object, getSerializeConfig());
    }

}
