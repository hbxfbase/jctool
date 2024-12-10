package cn.sharpen.jctool.bean.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ColumnValue {
    String columnName;
    String columnCondition;
    String columnValue;
}
