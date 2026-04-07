package com.flower.d2c.service;

import com.flower.d2c.controller.form.SiteConfigForm;
import com.flower.d2c.controller.view.dto.SiteConfigView;

public interface SiteConfigService {

    SiteConfigView getSiteConfig();

    Boolean updateConfig(SiteConfigForm config);
}
