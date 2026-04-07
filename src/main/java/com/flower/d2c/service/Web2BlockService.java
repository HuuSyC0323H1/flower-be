package com.flower.d2c.service;

import com.flower.d2c.model.Web2Block;

import java.util.List;

public interface Web2BlockService {
    List<Web2Block> findAllBlockWeb();

    Boolean deleteBlock(Long id);
}
