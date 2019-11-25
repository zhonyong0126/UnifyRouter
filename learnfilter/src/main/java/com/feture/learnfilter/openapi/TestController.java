package com.feture.learnfilter.openapi;

import com.feture.learnfilter.model.HelloReq;
import com.feture.learnfilter.model.HelloResp;
import com.feture.learnfilter.model.TestCacheModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/openapi/Test")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(method = RequestMethod.POST)
    public HelloResp hello(@RequestBody HelloReq testReq) {
        logger.info("请求到hello了哈");
        logger.info(testReq.toString());

        return new HelloResp(0);
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.POST)
    public HelloResp hello1(@RequestBody HelloReq testReq) {
        logger.info("请求到hello-----1了哈");
        logger.info(testReq.toString());

        return new HelloResp(0);
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.POST)
    public HelloResp hello2(@RequestBody HelloReq testReq) {
        logger.info("请求到hello-----2了哈");
        logger.info(testReq.toString());

        return new HelloResp(0);
    }

    @RequestMapping(value = "/AddCache", method = RequestMethod.POST)
    public HelloResp hello3(@RequestBody TestCacheModel testReq) {
        logger.info("请求到AddCache-----3了哈");
        logger.info(testReq.toString());

        return new HelloResp(0);
    }

    @RequestMapping(value = "/MockException", method = RequestMethod.POST)
    public HelloResp hello4(@RequestBody TestCacheModel testReq) {
        logger.info("请求到mockException-----4了哈");
        logger.info(testReq.toString());
        throw new IllegalArgumentException("哎呀，又出异常了");
    }

    public static void main(String[] args) {

    }
}
