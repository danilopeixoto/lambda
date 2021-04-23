package com.danilopeixoto.converter;

import com.danilopeixoto.model.RuntimeType;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.EnumWriteSupport;

@WritingConverter
public class RuntimeTypeConverter extends EnumWriteSupport<RuntimeType> {
}
