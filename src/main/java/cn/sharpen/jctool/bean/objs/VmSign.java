package cn.sharpen.jctool.bean.objs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class VmSign {

    private String b4 = "    ";
    private String blank = "";

}
