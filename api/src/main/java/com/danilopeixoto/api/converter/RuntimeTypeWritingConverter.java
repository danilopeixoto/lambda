package com.danilopeixoto.api.converter;

import com.danilopeixoto.api.model.RuntimeType;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.EnumWriteSupport;

@WritingConverter
public class RuntimeTypeWritingConverter extends EnumWriteSupport<RuntimeType> {
}
