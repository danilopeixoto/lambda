package com.danilopeixoto.converter;

import com.danilopeixoto.model.StatusType;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.r2dbc.convert.EnumWriteSupport;

@WritingConverter
public class StatusTypeConverter extends EnumWriteSupport<StatusType> {
}
