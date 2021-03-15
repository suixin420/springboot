package com.example.springboot;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class AppletPay {

    private static final String mch_id="";//商户号
    private static final String trade_type="JSAPI";//支付方式JSAPI--JSAPI支付（或小程序支付）、NATIVE--Native支付、APP--app支付，MWEB--H5支付，不同trade_type决定了调起支付的方式，请根据支付产品正确上传 MICROPAY--付款码支付，付款码支付有单独的支付接口，所以接口不需要上传，该字段在对账单中会出现
    private static final String MD5_KEY="";
    private static final String appId="";//小程序appId
    private String notify_url;//支付成功后的回调地址
    private String openid;//支付用户opendId"
    private String out_trade_no;//商户订单号
    private String spbill_create_ip;//终端IP(服务器IP)
    private BigDecimal payMoney;//支付金额

    public AppletPay(String notify_url, String openid, String out_trade_no, String spbill_create_ip, BigDecimal payMoney) {
        this.notify_url = notify_url;
        this.openid = openid;
        this.out_trade_no = out_trade_no;
        this.spbill_create_ip = spbill_create_ip;
        this.payMoney = payMoney;
    }

    public Map<String, String> goPay() {
        Map<String, String> result = new HashMap<String,String>();
        //商品名称
        String body = "";
        //金额元=paymentPo.getTotal_fee()*100
        //支付金额，这边需要转成字符串类型，否则后面的签名会失败
        String total_fee = String.valueOf(payMoney.multiply(new BigDecimal(100)).intValue());
        String nonce_str = UUIDHexGenerator.generate();
        //组装参数，用户生成统一下单接口的签名
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("appid", appId);
        packageParams.put("body", body);
        packageParams.put("mch_id", mch_id);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("notify_url", notify_url);
        packageParams.put("openid", openid);
        packageParams.put("out_trade_no", out_trade_no);//
        packageParams.put("spbill_create_ip", spbill_create_ip);

        packageParams.put("total_fee", total_fee);
        packageParams.put("trade_type", trade_type);

        String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

        //MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
        String mysign = PayUtil.sign(prestr, "秘钥", "utf-8").toUpperCase();

        //拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
        String xml = "<xml>" + "<appid>" + "小程序id" + "</appid>"
                + "<body>" + body + "</body>"
                + "<mch_id>" + mch_id + "</mch_id>"
                + "<nonce_str>" + nonce_str + "</nonce_str>"
                + "<notify_url>" + notify_url + "</notify_url>"
                + "<openid>" + openid + "</openid>"
                + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
                + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                + "<total_fee>" + total_fee + "</total_fee>"
                + "<trade_type>" + trade_type + "</trade_type>"
                + "<sign>" + mysign + "</sign>"
                + "</xml>";

        String res = PayUtil.httpRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", xml);

        // 将解析结果存储在HashMap中
        Map map = null;
        try {
            map = PayUtil.doXMLParse(res);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String return_code = (String) map.get("return_code");//返回状态码
        String return_msg = (String) map.get("return_msg");//返回状态码
        String prepay_id = null;
        if(return_code=="SUCCESS"||return_code.equals(return_code)){//成功
            result.put("code", "1");

            prepay_id = (String) map.get("prepay_id");//返回的预付单信息
            result.put("nonceStr", nonce_str);
            result.put("package", "prepay_id=" + prepay_id);
            Long timeStamp = System.currentTimeMillis() / 1000;
            result.put("timeStamp", timeStamp + "");//这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
            //拼接签名需要的参数
            String stringSignTemp = "appId=" + appId + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id+ "&signType=MD5&timeStamp=" + timeStamp;
            //再次签名，这个签名用于小程序端调用wx.requesetPayment方法
            String paySign = PayUtil.sign(stringSignTemp, MD5_KEY, "utf-8").toUpperCase();
            result.put("signType", "MD5");
            result.put("paySign", paySign);

            String result_code = (String) map.get("result_code");/** 业务结果;SUCCESS/FAIL */

        }else{//失败
            String err_code = (String) map.get("err_code");
            String err_code_des = (String) map.get("err_code_des");
            result.put("code", "0");
        }
        return result;
    }

    /*
    回调
     */
    public Map<String, String> doXMLParse(HttpServletRequest request) throws Exception {
        Map<String, String> result = new HashMap<String,String>();
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine()) != null){
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";

        Map map = PayUtil.doXMLParse(notityXml);

        String returnCode = (String) map.get("return_code");
        if("SUCCESS".equals(returnCode)){
            //验证签名是否正确
            Map<String, String> validParams = PayUtil.paraFilter(map);  //回调验签时需要去除sign和空值参数
            String validStr = PayUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            String sign = PayUtil.sign(validStr, MD5_KEY, "utf-8").toUpperCase();//拼装生成服务器端验证的签名
            //根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
            if(sign.equals(map.get("sign"))){
                /**此处添加自己的业务逻辑代码start**/
                String payOrderNo = (String) map.get("out_trade_no");
                String transactionId = (String) map.get("transaction_id");


                /**此处添加自己的业务逻辑代码end**/
                //通知微信服务器已经支付成功
                resXml = this.getXml();

            }

        }else{
            resXml = this.getFailXml();
        }

        return result;
    }

    private String getFailXml() {
        return "<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[签名失败]]></return_msg></xml>";
    }

    private String getXml() {
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
    }
}
