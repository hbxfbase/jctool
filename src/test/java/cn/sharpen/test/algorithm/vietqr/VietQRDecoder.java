package cn.sharpen.test.algorithm.vietqr;

import java.util.*;

public class VietQRDecoder {

    // TLV 解析器
    public static Map<String, String> parseTLV(String input) {
        Map<String, String> result = new LinkedHashMap<>();
        int index = 0;
        while (index + 4 <= input.length()) {
            String tag = input.substring(index, index + 2);
            int length = Integer.parseInt(input.substring(index + 2, index + 4));
            int valueStart = index + 4;
            int valueEnd = valueStart + length;
            if (valueEnd > input.length()) break;

            String value = input.substring(valueStart, valueEnd);
            result.put(tag, value);
            index = valueEnd;

            if (tag.equals("63")) break; // CRC 是最后字段
        }
        return result;
    }

    // 提取 VietQR 关键信息（包含子 TLV 的解码）
    public static void extractInfo(String qrData) {
        Map<String, String> tlv = parseTLV(qrData);

        // 解析 Tag 38: Merchant Account Info
        if (tlv.containsKey("38")) {
            Map<String, String> sub = parseTLV(tlv.get("38"));
            System.out.println("📌 ➤【收款信息】");
            System.out.println("银行 BIN（02）: " + sub.get("02"));
            System.out.println("收款账户（03）: " + sub.get("03"));
        }

        // 金额（54）
        if (tlv.containsKey("54")) {
            System.out.println("💰 金额: " + tlv.get("54") + " VND");
        }

        // 收款人名（59）
        if (tlv.containsKey("59")) {
            System.out.println("👤 收款人姓名: " + tlv.get("59"));
        }

        // 附言或订单号（Tag 62 子字段）
        if (tlv.containsKey("62")) {
            Map<String, String> addl = parseTLV(tlv.get("62"));
            if (addl.containsKey("05")) {
                System.out.println("📝 附言: " + addl.get("05"));
            }
        }

        // 国家代码
        if (tlv.containsKey("58")) {
            System.out.println("🌍 国家代码: " + tlv.get("58"));
        }

        System.out.println("✅ CRC 校验值: " + tlv.get("63"));
    }

    // 主函数示例
    public static void main(String[] args) {
        String vietQR = "00020101021138540010A0000007270123000697043601020209109704360201234567895303704540412345802VN5908NGUYENVNA62290506ORDER6304A13A";
        extractInfo(vietQR);
    }
}
