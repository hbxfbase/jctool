package cn.sharpen.jctool.bean.base;

import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

public class RandomGeneratorExt extends RandomGenerator {
    private static final long serialVersionUID = -7802758587765561876L;
    private String code;

    public void setCode(String code) {
        this.code = code;
    }

    public RandomGeneratorExt(int count) {
        super(count);
    }
    public RandomGeneratorExt(int count, String codeStr) {
        super(count);
        code = codeStr;
    }

    public RandomGeneratorExt(String baseStr, int length) {
        super(baseStr, length);
    }

    public String generate() {
        if(StrUtil.isNotBlank(code)) {
            return code;
        }
        return RandomUtil.randomString(this.baseStr, this.length);
    }

    public boolean verify(String code, String userInputCode) {
        return StrUtil.isNotBlank(userInputCode) ? StrUtil.equalsIgnoreCase(code, userInputCode) : false;
    }

}
