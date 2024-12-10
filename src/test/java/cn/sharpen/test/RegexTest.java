package cn.sharpen.test;

import cn.sharpen.jctool.consts.RegexConst;
import cn.sharpen.jctool.util.RegexUtils;
import cn.sharpen.jctool.util.StrTool;
import org.junit.Test;

public class RegexTest {

    @Test
    public void regMatches(){
//        StrTool.pl("email 1 false "+"a1234asdfEewwE".matches(RegexConst.REG_EMAIL));
//        StrTool.pl("email 2 false "+"%$A1234asdf@a.EewwE".matches(RegexConst.REG_EMAIL));
//        StrTool.pl("email 3 false "+"_-A1234asdf@a.".matches(RegexConst.REG_EMAIL));
//        StrTool.pl("email 4 false "+"_-A1234asdf@a".matches(RegexConst.REG_EMAIL));
//        StrTool.pl("email 5 true "+"asdf@ab.com".matches(RegexConst.REG_EMAIL));
//        StrTool.pl("email 6 true "+"asdf123-_@ab.com".matches(RegexConst.REG_EMAIL));
//
//        StrTool.pl("account 1 true "+"_1234asdfEewwE".matches(RegexConst.REG_ACCOUNT));
//        StrTool.pl("account 2 true "+"-1234asdfEewwE".matches(RegexConst.REG_ACCOUNT));
//        StrTool.pl("account 3 false "+"1234asdfEewwE$".matches(RegexConst.REG_ACCOUNT));
//        StrTool.pl("account 4 true "+"justinaa".matches(RegexConst.REG_ACCOUNT));
//
//        StrTool.pl("cellphone 1 true "+"+86-12341234".matches(RegexConst.REG_CELLPHONE));
//        StrTool.pl("cellphone 2 true "+"1234-1234".matches(RegexConst.REG_CELLPHONE));
//        StrTool.pl("cellphone 3 true "+"12341234".matches(RegexConst.REG_CELLPHONE));
//        StrTool.pl("cellphone 4 false "+"1234asdfEewwE".matches(RegexConst.REG_CELLPHONE));
        StrTool.pl("".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//f
        StrTool.pl("aaabbbcccddd".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//t
        StrTool.pl("aaa bbb 22".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//t
        StrTool.pl("aaa  bbb".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//f
        StrTool.pl("aaa bbb ccc".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//t
        StrTool.pl("aaa bbb  ccc".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//f
        StrTool.pl(" aaa bbb".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//f
        StrTool.pl("aaa bbb ".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//f
        StrTool.pl(" bbb ".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//f
        StrTool.pl("   aaa +-&   bbb ".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//f
        StrTool.pl("aaa bbb   中文   ".matches(RegexConst.VIRTUAL_CARD_APPLY_NAME));//f

    }

    @Test
    public void matchStrTest(){
        StrTool.pl( RegexUtils.matchStr("127.0.0.1",RegexConst.IPADDRESS_PATTERN));
        StrTool.pl( RegexUtils.matchStr("192.168.0.1",RegexConst.IPADDRESS_PATTERN));
        StrTool.pl( RegexUtils.matchStr("255.255.255.255",RegexConst.IPADDRESS_PATTERN));
        StrTool.pl( RegexUtils.matchStr("0.0.0.1",RegexConst.IPADDRESS_PATTERN));
        StrTool.pl( RegexUtils.matchStr("256.0.0.0",RegexConst.IPADDRESS_PATTERN));
        StrTool.pl( RegexUtils.matchStr("255.256.0.1",RegexConst.IPADDRESS_PATTERN));
        StrTool.pl( RegexUtils.matchStr("25.0.256.",RegexConst.IPADDRESS_PATTERN));
    }
}
