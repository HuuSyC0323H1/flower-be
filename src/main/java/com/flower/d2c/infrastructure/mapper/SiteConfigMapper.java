package com.flower.d2c.infrastructure.mapper;

import com.flower.d2c.controller.form.SiteConfigForm;
import com.flower.d2c.model.SiteConfig;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SiteConfigMapper {

    void updateFromForm(SiteConfigForm form, @MappingTarget SiteConfig entity);
}